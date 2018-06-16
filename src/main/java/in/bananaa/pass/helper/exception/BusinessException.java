package in.bananaa.pass.helper.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

import in.bananaa.pass.helper.BusinessErrorCode;

/**
 * BusinessException extends RuntimeException because
 * springframework.Transactional is rolledback only for unchecked exceptions
 */
public class BusinessException extends Exception implements ErrorCodesGettable {
	private static final long serialVersionUID = 1L;
	private final List<Integer> errorCodes;

	public BusinessException() {
		this.errorCodes = Collections.emptyList();
	}

	public BusinessException(String message) {
		super(message);
		this.errorCodes = Collections.emptyList();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.errorCodes = Collections.emptyList();
	}

	public BusinessException(Throwable cause) {
		super(cause);
		this.errorCodes = Collections.emptyList();
	}

	public BusinessException(BusinessErrorCode businessErrorCode) {
		this.errorCodes = Collections.singletonList(businessErrorCode.getBusinessErrorCode());
	}

	public BusinessException(BusinessErrorCode businessErrorCode, String message) {
		super(message);
		this.errorCodes = Collections.singletonList(businessErrorCode.getBusinessErrorCode());
	}

	public BusinessException(BusinessErrorCode businessErrorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCodes = Collections.singletonList(businessErrorCode.getBusinessErrorCode());
	}

	public BusinessException(BusinessErrorCode businessErrorCode, Throwable cause) {
		super(cause);
		this.errorCodes = Collections.singletonList(businessErrorCode.getBusinessErrorCode());
	}

	public BusinessException(Iterable<BusinessErrorCode> businessErrorCodes) {
		this.errorCodes = init(businessErrorCodes);
	}

	public BusinessException(Iterable<BusinessErrorCode> businessErrorCodes, String message) {
		super(message);
		this.errorCodes = init(businessErrorCodes);
	}

	public BusinessException(Iterable<BusinessErrorCode> businessErrorCodes, String message, Throwable cause) {
		super(message, cause);
		this.errorCodes = init(businessErrorCodes);
	}

	public BusinessException(Iterable<BusinessErrorCode> businessErrorCodes, Throwable cause) {
		super(cause);
		this.errorCodes = init(businessErrorCodes);
	}

	private static List<Integer> init(Iterable<BusinessErrorCode> businessErrorCodes) {
		ArrayList<Integer> codes = new ArrayList<>();
		for (BusinessErrorCode businessErrorCode : businessErrorCodes) {
			codes.add(businessErrorCode.getBusinessErrorCode());
		}
		codes.trimToSize();
		return Collections.unmodifiableList(codes);
	}

	@Override
	public Integer getErrorCode() {
		List<Integer> codes = getErrorCodes();
		return (!codes.isEmpty()) ? codes.get(0) : null;
	}

	@Override
	public List<Integer> getErrorCodes() {
		return errorCodes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getName());
		Iterator<Integer> iterator = getErrorCodes().iterator();
		if (iterator.hasNext()) {
			sb.append(" [");
			for (;;) {
				sb.append(iterator.next());
				if (!iterator.hasNext()) {
					break;
				}
				sb.append(", ");
			}
			sb.append("] ");
		}
		String message = getLocalizedMessage();
		if (message != null) {
			sb.append(": ").append(message);
		}
		return sb.toString();
	}

	public List<Integer> getChainedErrorCodes() {
		Throwable cause = getCause();
		if ((cause == null) || (cause == this)) {
			return getErrorCodes();
		}
		List<Integer> codes = new ArrayList<>(getErrorCodes());
		IdentityHashMap<Throwable, Boolean> chain = new IdentityHashMap<>();
		do {
			if (cause instanceof BusinessException) {
				codes.addAll(((BusinessException) cause).getErrorCodes());
			}
			chain.put(cause, Boolean.TRUE);
			cause = cause.getCause();
		} while ((cause != null) && !chain.containsKey(cause));
		return Collections.unmodifiableList(codes);
	}
}
