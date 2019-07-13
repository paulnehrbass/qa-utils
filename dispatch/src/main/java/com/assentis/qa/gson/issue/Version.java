package com.assentis.qa.gson.issue;

public class Version {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Version{" +
                "name='" + name + '\'' +
                '}';
    }
}
