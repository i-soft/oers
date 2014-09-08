package org.isf.oers.usertype;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.isf.commons.Util;
import org.isf.commons.io.BeanReader;
import org.isf.commons.io.BeanWriter;

public abstract class AbstractObjectStorageType extends BinaryType  implements UserType, ParameterizedType {

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		try {
			byte[] buf = Util.objectToByteArray(value);
			return Util.byteArrayToObject(buf);
		} catch(IOException ie) {
			throw new HibernateException(ie);
		} catch(ClassNotFoundException cnfe) {
			throw new HibernateException(cnfe);
		}
	}
	
	@Override
	public Object nullSafeGet(final ResultSet res, String[] names,
			SessionImplementor implementor, final Object owner) throws HibernateException,
			SQLException {
		Object obj = null;
		if (!res.wasNull()) {
			String filename = res.getString(names[0]);
			try {
				BeanReader reader = new BeanReader(filename);
				obj = reader.readBean()[0];
			} catch(IOException e) {
				throw new HibernateException("Error while load Bean '"+returnedClass().getName()+"' from '"+filename+"'.", e);
			}
		}
		return obj;
	}
	
	public void nullSafeSet(final PreparedStatement pstmt, final Object value, final int index,
			SessionImplementor implementor) throws HibernateException, SQLException {
		if (value == null) {
			pstmt.setNull(index, Types.VARCHAR);
		} else {
			File file = new File(getFolder()+File.separator+UUID.randomUUID().toString()+".xml");
			if (file.exists()) throw new HibernateException("File '"+file.getAbsolutePath()+"' already exists.");
			try {
				BeanWriter writer = new BeanWriter(file);
				writer.writeBean(value);
			} catch(Exception e) {
				throw new HibernateException("Error while save Bean '"+returnedClass().getName()+"' to '"+file.getAbsolutePath()+"'.", e);
			}
			pstmt.setString(index, file.getAbsolutePath());
		}
	}
	
	@Override
	public abstract Class<?> returnedClass();	

}
