package org.isf.oers.images;

public enum ImageSize {

	_8x8("8x8"),
	_16x16("16x16"),
	_24x24("24x24"),
	_32x32("32x32"),
	_SVG("svg"),
	_CUSTOM("custom");
	
	public final String name;
	
	private ImageSize(String name) {
		this.name = name;
	}
	
}
