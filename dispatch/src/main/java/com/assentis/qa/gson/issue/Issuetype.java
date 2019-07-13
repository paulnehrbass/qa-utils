package com.assentis.qa.gson.issue;

public class Issuetype {
    private String name;
    private Boolean subtask;

    public Boolean getSubtask() {
        return subtask;
    }

    public void setSubtask(Boolean subtask) {
        this.subtask = subtask;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Issuetype{" +
                "name='" + name + '\'' +
                ", subtask=" + subtask +
                '}';
    }
}
