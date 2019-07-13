package com.assentis.qa.gson.issue;

public class Project {

    private String name;
    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
