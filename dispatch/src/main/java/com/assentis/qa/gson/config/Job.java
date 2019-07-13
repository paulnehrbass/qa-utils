package com.assentis.qa.gson.config;

import java.util.List;

public class Job {

    private String id;
    private String jql;
    private String plannedStart;
    private String plannedEnd;
    private String dueDate;
    private String originalEstimate;
    private List<String> linkComment;
    private String testcaseTemplate;

    public Job(String id, String jql, String plannedStart, String plannedEnd, String dueDate, String originalEstimate, List<String> linkComment) {
        this.id = id;
        this.jql = jql;
        this.plannedStart = plannedStart;
        this.plannedEnd = plannedEnd;
        this.dueDate = dueDate;
        this.originalEstimate = originalEstimate;
        this.linkComment = linkComment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJql() {
        return jql;
    }

    public void setJql(String jql) {
        this.jql = jql;
    }

    public String getPlannedStart() {
        return plannedStart;
    }

    public void setPlannedStart(String plannedStart) {
        this.plannedStart = plannedStart;
    }

    public String getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(String plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getOriginalEstimate() {
        return originalEstimate;
    }

    public void setOriginalEstimate(String originalEstimate) {
        this.originalEstimate = originalEstimate;
    }

    public String getLinkComment() {
        StringBuilder comment = new StringBuilder();

        for (String msg:linkComment) {
            comment.append(msg).append("\n");
        }

        return comment.toString();
    }

    public void setLinkComment(List<String> linkComment) {
        this.linkComment = linkComment;
    }

    public String getTestcaseTemplate() {
        return testcaseTemplate;
    }

    public void setTestcaseTemplate(String testcaseTemplate) {
        this.testcaseTemplate = testcaseTemplate;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id='" + id + '\'' +
                ", jql='" + jql + '\'' +
                ", plannedStart='" + plannedStart + '\'' +
                ", plannedEnd='" + plannedEnd + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", originalEstimate='" + originalEstimate + '\'' +
                ", linkComment=" + linkComment +
                '}';
    }
}