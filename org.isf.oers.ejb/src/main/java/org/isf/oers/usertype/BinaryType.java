package org.isf.oers.usertype;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.isf.commons.type.Binary;

public class BinaryType implements UserType, ParameterizedType {

	//private String storagePath = System.getProperty("user.home");
	public static final String STORAGE_BASE_PATH = System.getProperties().containsKey("oers.storage.dir") ? System.getProperty("oers.storage.dir") : System.getProperty("user.home")+File.separator+"oers"+File.separator+"storage";
	public static final String STORAGE_FOLDER = "storage.folder";
	public static final String DEFAULT_STORAGE_FOLDER = STORAGE_BASE_PATH+File.separator+"default";
	
	private String folder = DEFAULT_STORAGE_FOLDER;
	
	public String getFolder() { return folder; }
	
	@Override
	public void setParameterValues(Properties props) {
		if (props.containsKey(STORAGE_FOLDER)) this.folder = STORAGE_BASE_PATH+File.separator+props.getProperty(STORAGE_FOLDER);
	}

	@Override
	public Object assemble(final Serializable cached, final Object owner)
			throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		final Binary bin = (Binary)value;
		final Binary bout = new Binary();
		try {
			bout.loadFromStream(bin.openStream());
		} catch(IOException e) {
			throw new HibernateException("Error while deepCopy "+returnedClass().getName(), e);
		}
		return bout;
	}

	@Override
	public Serializable disassemble(final Object value) throws HibernateException {
		return (Serializable)value;
	}

	@Override
	public boolean equals(final Object o1, final Object o2) throws HibernateException {
		if (o1 == o2) return true;
		else if (o1 == null || o2 == null) return false;
		else return o1.equals(o2);
	}

	@Override
	public int hashCode(final Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() { return true; }

	@Override
	public Object nullSafeGet(final ResultSet res, String[] names,
			SessionImplementor implementor, final Object owner) throws HibernateException,
			SQLException {
		Binary binary = null;
		if (!res.wasNull()) {
			binary = new Binary();
			String filename = res.getString(names[0]);
			try {
				binary.loadFromFile(filename);
			} catch(IOException e) {
				throw new HibernateException("Error while load BinaryContent from '"+filename+"' for "+returnedClass().getName(), e);
			}
		}
		return binary;
	}

	@Override
	public void nullSafeSet(final PreparedStatement pstmt, final Object value, final int index,
			SessionImplementor implementor) throws HibernateException, SQLException {
		if (value == null) {
			pstmt.setNull(index, Types.VARCHAR);
		} else {
			final Binary b = (Binary)value;
			File file = new File(getFolder()+File.separator+UUID.randomUUID().toString()+".dat");
			if (file.exists()) throw new HibernateException("File '"+file.getAbsolutePath()+"' already exists.");
			try {
				b.saveToFile(file);
			} catch(IOException e) {
				throw new HibernateException("Error while save BinaryContent to '"+file.getAbsolutePath()+"' for "+returnedClass().getName(), e);
			}
			pstmt.setString(index, file.getAbsolutePath());
		}
	}

	@Override
	public Object replace(final Object original, final Object target, final Object owner) throws HibernateException {
		return this.deepCopy(original);
	}

	@Override
	public Class<?> returnedClass() {
		return Binary.class;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

}
