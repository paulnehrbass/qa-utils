package com.assentis.qa.library;

public class Const {

    public static final String DEFAULT_DOCBASE_SERVER = "http://localhost:14002/DocBase2/";
    public static final String DEFAULT_JIRA_SERVER = "http://jira.assentis.info/";

    public static final String DEFAULT_MYSQL_JDBC_DRIVER="com.mysql.jdbc.Driver";
    public static final String DEFAULT_MYSQL_JDBC_PORT="3306";
    public static final String DEFAULT_MYSQL_JDBC_DATABASE="qa-statistics";
    public static final String DEFAULT_MYSQL_JDBC_URL="jdbc:mysql://localhost";
    public static final String[] IGNORED_STATUS = {"Closed", "Passed", "Rejected"};
    public static final String[] TESTCASE_TYPES = {"Bug", "Unreleased Bug", "Security Issue"};
    public static final String[] TESTTASK_TYPES = {"Improvement", "Change Request"};
    public static final String[] QA_SHORTCUTS = {"QA-RNO", "QA-OSP", "QA-TNA", "QA-CWA", "QA-ASU", "QA-CZU"};
    public static final Boolean DEFAULT_DEBUG = false;
    //public static final String DEFAULT_MYSQL_JDBC_USER ="root";
    //public static final String DEFAULT_MYSQL_JDBC_PASSWORD ="";
    public static final String URL_CREATE_ISSUE = "rest/api/2/issue/";
    public static final String URL_CREATE_LINK = "rest/api/2/issueLink/";

}
