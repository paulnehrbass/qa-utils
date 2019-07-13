
package com.assentis.qa.gson.issue;


public class Issuelink {

    private String id;
    private String self;
    private Type type;
    private InwardIssue inwardIssue;
    private OutwardIssue outwardIssue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public InwardIssue getInwardIssue() {
        return inwardIssue;
    }

    public void setInwardIssue(InwardIssue inwardIssue) {
        this.inwardIssue = inwardIssue;
    }

    public OutwardIssue getOutwardIssue() {
        return outwardIssue;
    }

    public void setOutwardIssue(OutwardIssue outwardIssue) {
        this.outwardIssue = outwardIssue;
    }

}
