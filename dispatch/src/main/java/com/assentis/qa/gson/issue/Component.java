package com.assentis.qa.gson.issue;

public class Component {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Component{" +
                "name='" + name + '\'' +
                '}';
    }
}
