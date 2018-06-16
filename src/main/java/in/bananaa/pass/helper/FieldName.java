package in.bananaa.pass.helper;

public enum FieldName {
	RESULT("result"), STATUS_CODES("statusCodes"), STATUS_CODE("statusCode"), CODE("code"), DESCRIPTION("description");

	private final String name;

	FieldName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
