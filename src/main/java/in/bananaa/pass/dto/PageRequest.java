package in.bananaa.pass.dto;

import java.io.Serializable;

public class PageRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer pageNumber = 0;
	private Integer resultsPerPage = 10;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(Integer resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
}
