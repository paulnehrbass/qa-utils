
package com.assentis.qa.gson.issue;


public class OutwardIssue {

    private String id;
    private String key;
    private String self;
    private Fields fields;

    public OutwardIssue() {
    }

    public OutwardIssue(String id, String key, String self, Fields fields) {
        this.id = id;
        this.key = key;
        this.self = self;
        this.fields = fields;
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

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "OutwardIssue{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", self='" + self + '\'' +
                ", fields=" + fields +
                '}';
    }
}