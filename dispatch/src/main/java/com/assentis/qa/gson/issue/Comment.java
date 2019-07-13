package com.assentis.qa.gson.issue;

public class Comment {

	private String body;

	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Comment [body=" + body + "]";
	}
}