package com.assentis.qa.gson.config;

public class DispatchConfig {

    private String jiraServer;
    private String jiraUser;
    private String jiraPassword;

    private String mysqlPort;
    private String mysqlDatabase;
    private String mysqlDriver;
    private String mysqlUrl;
    private String mySqlUser;
    private String mySqlPassword;
    private Boolean debug;


    /* public static final String DEFAULT_MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DEFAULT_MYSQL_JDBC_PORT = "3306";
    public static final String DEFAULT_MYSQL_JDBC_DATABASE = "qa-statistics";
    public static final String DEFAULT_MYSQL_JDBC_URL = "jdbc:mysql://localhost";*/

    private String jobsFile;

    public DispatchConfig() {
    }

    public String getJiraServer() {
        return jiraServer;
    }

    public void setJiraServer(String jiraServer) {
        this.jiraServer = jiraServer;
    }

    public String getJiraUser() {
        return jiraUser;
    }

    public void setJiraUser(String jiraUser) {
        this.jiraUser = jiraUser;
    }

    public String getJiraPassword() {
        return jiraPassword;
    }

    public void setJiraPassword(String jiraPassword) {
        this.jiraPassword = jiraPassword;
    }

    public String getJobsFile() {
        return jobsFile;
    }

    public void setJobsFile(String jobsFile) {
        this.jobsFile = jobsFile;
    }

    public String getMysqlPort() {
        return mysqlPort;
    }

    public void setMysqlPort(String mysqlPort) {
        this.mysqlPort = mysqlPort;
    }

    public String getMysqlDatabase() {
        return mysqlDatabase;
    }

    public void setMysqlDatabase(String mysqlDatabase) {
        this.mysqlDatabase = mysqlDatabase;
    }

    public String getMysqlDriver() {
        return mysqlDriver;
    }

    public void setMysqlDriver(String mysqlDriver) {
        this.mysqlDriver = mysqlDriver;
    }

    public String getMysqlUrl() {
        return mysqlUrl;
    }

    public void setMysqlUrl(String mysqlUrl) {
        this.mysqlUrl = mysqlUrl;
    }

    public String getMySqlUser() {
        return mySqlUser;
    }

    public void setMySqlUser(String mySqlUser) {
        this.mySqlUser = mySqlUser;
    }

    public String getMySqlPassword() {
        return mySqlPassword;
    }

    public void setMySqlPassword(String mySqlPassword) {
        this.mySqlPassword = mySqlPassword;
    }

    public Boolean getDebug() {
        return debug;
    }

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return "DispatchConfig{" +
                "\n -> jiraServer='" + jiraServer + '\'' +
                "\n -> jiraUser='" + jiraUser + '\'' +
                "\n -> jiraPassword='" + jiraPassword + '\'' +
                "\n -> mysqlPort='" + mysqlPort + '\'' +
                "\n -> mysqlDatabase='" + mysqlDatabase + '\'' +
                "\n -> mysqlDriver='" + mysqlDriver + '\'' +
                "\n -> mysqlUrl='" + mysqlUrl + '\'' +
                "\n -> mySqlUser='" + mySqlUser + '\'' +
                "\n -> mySqlPassword='" + mySqlPassword + '\'' +
                "\n -> jobsFile='" + jobsFile + '\'' +
                "\n -> debug='" + debug.toString() + '\'' +
                "\n}";
    }
}
