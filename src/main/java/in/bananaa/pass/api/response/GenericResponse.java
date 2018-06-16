package in.bananaa.pass.api.response;

import java.io.Serializable;

import in.bananaa.pass.api.login.ArrayOfStatusCode;

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
