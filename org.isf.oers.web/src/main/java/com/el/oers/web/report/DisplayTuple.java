package com.el.oers.web.report;

import org.isf.commons.data.Tuple;

public class DisplayTuple extends Tuple {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2581124613785547008L;

	private String[] styles;
	
	public DisplayTuple() {
		super();
		styles = new String[0];
	}
	
	public int styleCount() { return styles.length; }
	
	public String[] getStyles(int ... indexes) {
		if (indexes.length == 0) return styles;
		String[] ret = new String[indexes.length];
		for (int i=0;i<indexes.length;i++)
			ret[i] = getStyle(indexes[i]);
		return ret;
	}
	public String getStyle(int index) { return styles[index]; }
	
	public void setStyles(String ... styles) {
		for (int i=0;i<styles.length;i++)
			setData(i, styles[i]);
	}
	public void setStyle(int index, String style) {
		if (index >= styleCount()) {
			int nsize = index+1;
			String[] nldata = new String[nsize];
			System.arraycopy(styles, 0, nldata, 0, styles.length);
			styles = nldata;
		}
		if (styles[index] == null && style == null) return;
		else if (styles[index] != null && styles[index].equals(style)) return;
		styles[index] = style;
	}
	public void addStyle(String style) {
		setStyle(styleCount(), style);
	}
	
	public void clear() {
		super.clear();
		styles = new String[0];
	}
	
}
