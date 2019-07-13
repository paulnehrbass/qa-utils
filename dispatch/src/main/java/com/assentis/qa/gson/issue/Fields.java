package com.assentis.qa.gson.issue;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fields {

    private Issuetype issuetype;
    private Status status;
    private String summary;
    private Integer timespent;
    private Project project;
    private List<FixVersion> fixVersions;
    @SerializedName("customfield_13290")
    private String workpackage;
    @SerializedName("created")
    private String issuecreated;
    private Priority priority;
    private List<String> labels;
    private Integer timeestimate;
    private List<Version> versions;
    private Assignee assignee;
    @SerializedName("updated")
    private String issueupdated;
    private List<Component> components;
    private Integer timeoriginalestimate;
    @SerializedName("customfield_10690")
    private String plannedStart;
    private String description;
    @SerializedName("customfield_10691")
    private String plannedEnd;
    private Creator creator;
    private Reporter reporter;
    private String duedate;
    private Progress progress;
    private List<Issuelink> issuelinks;
    private Timetracking timetracking;

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }

    public Integer getTimeoriginalestimate() {
        return timeoriginalestimate;
    }

    public void setTimeoriginalestimate(Integer timeoriginalestimate) {
        this.timeoriginalestimate = timeoriginalestimate;
    }

    public String getPlannedStart() {
        return plannedStart;
    }

    public void setPlannedStart(String plannedStart) {
        this.plannedStart = plannedStart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(String plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public String getIssueupdated() {
        return issueupdated;
    }

    public void setIssueupdated(String issueupdated) {
        this.issueupdated = issueupdated;
    }

    public Integer getTimeestimate() {
        return timeestimate;
    }

    public void setTimeestimate(Integer timeestimate) {
        this.timeestimate = timeestimate;
    }

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(List<Version> versions) {
        this.versions = versions;
    }

    public Assignee getAssignee() {
        return assignee;
    }

    public void setAssignee(Assignee assignee) {
        this.assignee = assignee;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getIssuecreated() {
        return issuecreated;
    }

    public void setIssuecreated(String issuecreated) {
        this.issuecreated = issuecreated;
    }

    public String getWorkpackage() {
        return workpackage;
    }

    public void setWorkpackage(String workpackage) {
        this.workpackage = workpackage;
    }

    public Integer getTimespent() {
        return timespent;
    }

    public void setTimespent(Integer timespent) {
        this.timespent = timespent;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<FixVersion> getFixVersions() {
        return fixVersions;
    }

    public void setFixVersions(List<FixVersion> fixVersions) {
        this.fixVersions = fixVersions;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Issuetype getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(Issuetype issuetype) {
        this.issuetype = issuetype;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Issuelink> getIssuelinks() {
        return issuelinks;
    }

    public void setIssuelinks(List<Issuelink> issuelinks) {
        this.issuelinks = issuelinks;
    }

    public Timetracking getTimetracking() {
        return timetracking;
    }

    public void setTimetracking(Timetracking timetracking) {
        this.timetracking = timetracking;
    }

    @Override
    public String toString() {
        return "Fields{" +
                "issuetype=" + issuetype +
                ", status=" + status +
                ", summary='" + summary + '\'' +
                ", timespent=" + timespent +
                ", project=" + project +
                ", fixVersions=" + fixVersions +
                ", workpackage='" + workpackage + '\'' +
                ", issuecreated='" + issuecreated + '\'' +
                ", priority=" + priority +
                ", labels=" + labels +
                ", timeestimate=" + timeestimate +
                ", versions=" + versions +
                ", assignee=" + assignee +
                ", issueupdated='" + issueupdated + '\'' +
                ", components=" + components +
                ", timeoriginalestimate=" + timeoriginalestimate +
                ", plannedStart='" + plannedStart + '\'' +
                ", description='" + description + '\'' +
                ", plannedEnd='" + plannedEnd + '\'' +
                ", creator=" + creator +
                ", reporter=" + reporter +
                ", duedate='" + duedate + '\'' +
                ", progress=" + progress +
                ", issuelinks=" + issuelinks +
                ", timetracking=" + timetracking +
                '}';
    }
}