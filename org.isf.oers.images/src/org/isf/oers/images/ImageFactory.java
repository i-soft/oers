package org.isf.oers.images;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.isf.oers.images.activator.Activator;

public class ImageFactory {

	public static Image getImage(String name, ImageSize size) {
		Image image = JFaceResources.getImageRegistry().get(name+"_"+size.name);
		if (image == null) {
			addImageDescriptor(name, size);
			image = JFaceResources.getImageRegistry().get(name+"_"+size.name);
		}
		return image;
	}
	
	public static ImageDescriptor getImageDescriptor(String name, ImageSize size) {
		ImageDescriptor id = JFaceResources.getImageRegistry().getDescriptor(name+"_"+size.name);
		if (id == null) {
			addImageDescriptor(name, size);
			id = JFaceResources.getImageRegistry().getDescriptor(name+"_"+size.name);
		}
		return id;
	}
	
	public static InputStream getImageAsInputStream(String name, ImageSize size) throws IOException {
		ResourceBundle iconset = ResourceBundle.getBundle("iconset");
		String fileName = iconset.containsKey(name) ? iconset.getString(name) : name;
		URL filelocation = FileLocator.find(Activator.getDefault().getBundle(), new Path("icons/"+size.name+"/"+fileName), null);
		return filelocation.openConnection().getInputStream();
	}
	
	public static boolean addImageDescriptor(String name, ImageSize size) {
		try {
			ResourceBundle iconset = ResourceBundle.getBundle("iconset");
			String fileName = iconset.containsKey(name) ? iconset.getString(name) : name;
			URL filelocation = FileLocator.find(Activator.getDefault().getBundle(), new Path("icons/"+size.name+"/"+fileName), null);
			ImageDescriptor id = ImageDescriptor.createFromURL(filelocation);
			JFaceResources.getImageRegistry().put(name+"_"+size.name, id);
		} catch(MissingResourceException | IllegalArgumentException e) {
			return false;
		}
		return true;
	}
	
}
