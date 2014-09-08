package org.isf.oers.images;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public enum ImageResource {

	CONNECTOR_CONFIGURATION,
	SESSION_PROPERTIES;
	
	
	public Image getImage(ImageSize size) {
		return ImageFactory.getImage(this.name(), size);
	}
	
	public ImageDescriptor getImageDescriptor(ImageSize size) {
		return ImageFactory.getImageDescriptor(this.name(), size);
	}
	
	public InputStream getInputStream(ImageSize size) throws IOException {
		return ImageFactory.getImageAsInputStream(this.name(), size);
	}
	
}
