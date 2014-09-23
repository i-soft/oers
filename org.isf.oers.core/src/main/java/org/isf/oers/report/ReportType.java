package org.isf.oers.report;

public enum ReportType {

	UNKNOWN(""),
	FOLDER("folder"),
	XHTML("xhtml"),
	MDX("mdx")
	;
	
	private final String type;
	
	private ReportType(String type) {
		this.type = type;
	}
	
	public String type() { return type; }
	
	public static ReportType perType(String type) {
		for (ReportType rt : ReportType.values())
			if (type.equals(rt.type()))
				return rt;
		return UNKNOWN;
	}
	
}
