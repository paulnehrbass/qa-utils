package com.assentis.qa.gson.issue;

public class Timetracking {

	private String originalEstimate;
	/**
	 * "timetracking": {
			"originalEstimate": "2h"
		},
	 */

	public Timetracking() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOriginalEstimate() {
		return originalEstimate;
	}
	public void setOriginalEstimate(String originalEstimate) {
		this.originalEstimate = originalEstimate;
	}
	@Override
	public String toString() {
		return "Timetracking [originalEstimate=" + originalEstimate + "]";
	}
}