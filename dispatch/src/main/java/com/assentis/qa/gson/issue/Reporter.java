package com.assentis.qa.gson.issue;

public class Reporter {

    private String name;
    private String key;
    private String emailAddress;

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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Reporter{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
