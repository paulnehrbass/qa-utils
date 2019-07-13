package com.assentis.qa.gson.issue;

import java.util.HashMap;
import java.util.Map;

public class PostResponse {
/**
 * {
	"id": "111228",
    "key": "QAR-2802",
    "self": "http://jira.assentis.info/rest/api/2/issue/111228",
	"errorMessages": [],
    "errors": {
        "customfield13290": "Field 'customfield13290' cannot be set. It is not on the appropriate screen, or unknown."
	}
}
 */
	private String id;
	private String key;
	private String self;
	private Map<String, String> errors;

	public PostResponse() {
		super();
		errors = new HashMap<String, String>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSelf() {
		return self;
	}

	public void setSelf(String self) {
		this.self = self;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "PostResponse [id=" + id + ", key=" + key + ", self=" + self + ", errors=" + errors + "]";
	}
}