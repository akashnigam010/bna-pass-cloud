package in.bananaa.pass.dto;

import java.io.Serializable;

public class GenericResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	public boolean result;
	public ArrayOfStatusCode statusCodes;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public ArrayOfStatusCode getStatusCodes() {
		return statusCodes;
	}

	public void setStatusCodes(ArrayOfStatusCode statusCodes) {
		this.statusCodes = statusCodes;
	}
}
