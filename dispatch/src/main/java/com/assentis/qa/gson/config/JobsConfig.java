package com.assentis.qa.gson.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class JobsConfig {

    private List<Job> jobs;

    public JobsConfig(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return "JobsConfig{" +
                "jobs=" + gson.toJson(jobs) +
                '}';
    }
}