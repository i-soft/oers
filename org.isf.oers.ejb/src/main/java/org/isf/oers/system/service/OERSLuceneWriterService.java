package org.isf.oers.system.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Asynchronous;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.isf.oers.CONSTANTS;
import org.isf.oers.indexing.EntityIndexWriterService;
import org.isf.oers.indexing.EntityIndexedData;
import org.isf.oers.indexing.IndexBuilder;
import org.isf.oers.indexing.IndexedBean;
import org.isf.oers.jpa.EntityData;
import org.isf.oers.jpa.EntityDataEvent;
import org.isf.oers.jpa.Inserted;
import org.isf.oers.jpa.Removed;
import org.isf.oers.jpa.Updated;
import org.jboss.logging.Logger;

//@Stateless
@Singleton
@Local(EntityIndexWriterService.class)
public class OERSLuceneWriterService implements EntityIndexWriterService {

	@Inject
	private Logger log;
	
	private IndexWriter writer;
	
	@PostConstruct
	public void create() {
		File file = new File(System.getProperties().containsKey(CONSTANTS.LUCENE_DIRECTORY) ? System.getProperty(CONSTANTS.LUCENE_DIRECTORY) : System.getProperty("user.home")+File.separator+"oers"+File.separator+"lucene");
		if (!file.exists()) file.mkdirs();
		Directory dir = null;
		try {
			dir = FSDirectory.open(file);
		} catch(IOException e) {
			log.warn("Could not establish lucene-index-directory...", e);
			log.warn("Use Lucene RAMDirectory.");
			dir = new RAMDirectory();
		}
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);
		iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		try {
			this.writer = new IndexWriter(dir, iwc);
		} catch(IOException e) {
			log.error("Could not create lucene-index-writer...", e);
		}
	}
	
	@PreDestroy
	public void destroy() {
		try {
			getWriter().close();
		} catch(Exception e) {
			log.warn("Could not close lucene-index-writer...", e);
		}
	}
	
	protected IndexWriter getWriter() { return writer; }

	protected boolean isIndexable(Object data) {
		return data.getClass().isAnnotationPresent(IndexedBean.class);
	}
	
	protected IndexBuilder createIndexBuilder(Object data) {
		if (isIndexable(data)) {
			IndexedBean ib = data.getClass().getAnnotation(IndexedBean.class);
			try {
				IndexBuilder builder = ib.builder().newInstance();
				return builder;
			} catch(Exception e) {
				log.error("Error while create IndexBuilder-Instance for BeanClass "+data.getClass(), e);
			}
		}
		return null;
	}
	
	protected Document createDocument(EntityIndexedData data) {
		Document d = new Document();
		d.add(new StringField("uuid", data.getUuid(), Field.Store.YES));
		d.add(new StringField("beanclass", data.getBeanClass(), Field.Store.YES));
		d.add(new StringField("subject", data.getSubject(), Field.Store.YES));
		d.add(new TextField("content", data.getContent(), Field.Store.YES));
		// TODO Attributes
		return d;
	}
	
	@Override
	@Asynchronous
	public void indexData(@Observes @EntityDataEvent @Inserted EntityData data) {
		if (isIndexable(data)) {
			log.info("create index for "+data.getClass());
			IndexBuilder builder = createIndexBuilder(data);
			EntityIndexedData idxd = builder.buildIndex(data);
			Document doc = createDocument(idxd);
			try {
				getWriter().addDocument(doc);
			} catch(IOException ie) {
				log.error("Could not write index ... "+idxd, ie);
			}
		}
	}
	
	@Override
	@Asynchronous
	public void reindexData(@Observes @EntityDataEvent @Updated EntityData data) {
		if (isIndexable(data)) {
			log.info("update index for "+data.getClass());
			IndexBuilder builder = createIndexBuilder(data);
			EntityIndexedData idxd = builder.buildIndex(data);
			Document doc = createDocument(idxd);
			try {
				getWriter().updateDocument(new Term("uuid", idxd.getUuid()), doc);
			} catch(IOException ie) {
				log.error("Could not update index ... "+idxd, ie);
			}
		}
	}
	
	@Override
	@Asynchronous
	public void removeData(@Observes @EntityDataEvent @Removed EntityData data) {
		if (isIndexable(data)) {
			log.info("delete index for "+data.getClass());
			IndexBuilder builder = createIndexBuilder(data);
			EntityIndexedData idxd = builder.buildIndex(data);
			try {
				getWriter().deleteDocuments(new Term("uuid", idxd.getUuid()));
			} catch(IOException ie) {
				log.error("Could not delete index ... "+idxd, ie);
			}
		}
	}
	
}
