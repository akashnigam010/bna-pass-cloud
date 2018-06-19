package in.bananaa.pass.dto;

import java.io.Serializable;

public class GenericRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum RequestType {
		NEW, UPDATE;
	};

	private RequestType type;

	public RequestType getType() {
		return type;
	}

	public void setType(RequestType type) {
		this.type = type;
	}
}
