package com.assentis.qa.gson.issue;

public class Payload {
	
	private Fields fields;
	private OutwardIssue outwardIssue;
	private InwardIssue inwardIssue;
	private Comment comment;
	private Type type;
	
	/**
	 * {
	"outwardIssue": {
		"key": "CLASSIC-17701"
	},
	"comment": {
		"body": "Linked related issue!"
	},
	"inwardIssue": {
		"key": "QAR-2059"
	},
	"type": {
		"name": "Tests"
	}
}
	 * @param fields
	 */

	public Payload(Fields fields) {
		super();
		this.fields = fields;
	}

	public Payload() {
		super();
	}

	public Fields getFields() {
		return fields;
	}

	public void setFields(Fields fields) {
		this.fields = fields;
	}

	public OutwardIssue getOutwardIssue() {
		return outwardIssue;
	}

	public void setOutwardIssue(OutwardIssue outwardIssue) {
		this.outwardIssue = outwardIssue;
	}

	public InwardIssue getInwardIssue() {
		return inwardIssue;
	}

	public void setInwardIssue(InwardIssue inwardIssue) {
		this.inwardIssue = inwardIssue;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Payload [fields=" + fields + ", outwardIssue=" + outwardIssue + ", inwardIssue=" + inwardIssue
				+ ", comment=" + comment + ", type=" + type + "]";
	}
}