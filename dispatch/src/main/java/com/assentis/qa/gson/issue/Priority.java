package com.assentis.qa.gson.issue;

public class Priority {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Priority{" +
                "name='" + name + '\'' +
                '}';
    }
}
