package com.assentis.qa.gson.issue;

import java.util.List;

public class Response {
    private Integer maxResults;
    private Integer total;
    private List<Issue> issues;

    public Integer getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "Response{" +
                "maxResults=" + maxResults +
                ", total=" + total +
                ", issues=" + issues +
                '}';
    }
}