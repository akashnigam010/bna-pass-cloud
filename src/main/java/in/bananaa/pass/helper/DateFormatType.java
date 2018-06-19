package in.bananaa.pass.helper;

public enum DateFormatType {
	DATE_FORMAT_24_HOUR("HHmm"),
	TIME_FORMAT_AM_PM("hh:mm a"),
	DATE_FORMAT_DD_MM_YYYY("dd-MM-yyyy"),
	ISO_DATE_FORMAT("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	private String value;

	private DateFormatType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
