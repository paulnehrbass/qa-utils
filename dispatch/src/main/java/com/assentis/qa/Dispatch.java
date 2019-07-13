package com.assentis.qa;

import com.assentis.qa.gson.config.DispatchConfig;
import com.assentis.qa.gson.config.Job;
import com.assentis.qa.gson.config.JobsConfig;
import com.assentis.qa.gson.issue.*;
import com.assentis.qa.info.Info;
import com.assentis.qa.library.Const;
import com.assentis.qa.library.cli.QACLIParser;
import com.assentis.qa.library.http.HttpJson;
import com.assentis.qa.library.http.response.Post;
import com.assentis.qa.library.tools.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Dispatch {
    //-ju ralf.nowicki -jp testpassword -jf /default-dispatch-jobs.json -mus root -mpa pass

    final static Logger logger = Logger.getLogger(Dispatch.class);

    private DispatchConfig config = new DispatchConfig();

    public static void main(String args[]){

        Dispatch client = new Dispatch();
        Options options = client.getOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(160);
        QACLIParser parser = new QACLIParser();
        Gson gson;
        Set<String> setFixVersions = new HashSet<String>();
        Info infoIssues = new Info("ISSUES");
        Info infoTests = new Info("TESTS");

        try {
            CommandLine cmd = parser.parse( options, args);

            if(cmd.hasOption("h")) {
                formatter.printHelp("example usage -> java -jar dispatch.jar -js testser.html -ju user.name " +
                        "-jp testpassword -jf default-dispatch-jobs.json", options);
            }else{
                //parse command line params
                client.parseCmdParams(cmd);
                //read the jobs config file (json)
                Reader reader = new FileReader(new File(client.config.getJobsFile()));
                gson = new GsonBuilder().create();
                JobsConfig jobsConfig = gson.fromJson(reader, JobsConfig.class);
                logger.debug(client.config.toString());
                logger.debug(jobsConfig.toString());

                //dispatch all jobs from jobsConfig
                for (Job job : jobsConfig.getJobs()) {
                    String jql = job.getJql();
                    String url = client.getRequestUrl(client.config.getJiraServer(), jql, 1000);
                    logger.debug(String.format("Loading issues for job %s ...", job.getId()));
                    Response response = client.getJiraIssues(
                            url, client.config.getJiraUser(), client.config.getJiraPassword());
                    logger.debug(String.format("Issues loaded: %s", response.getTotal()));
                    //foreach issue...
                    int i=0;
                    for (Issue issue:response.getIssues()) {
                        Integer index = i % Arrays.asList(Const.QA_SHORTCUTS).size();
                        String msg;
                        String shortcut = client.getShortcut(
                                issue, index, Arrays.asList(Const.QA_SHORTCUTS));
                        // only if issue could not be ignored because of status
                        if (!Arrays.asList(Const.IGNORED_STATUS)
                                .contains(issue.getFields().getStatus().getName())) {
                            //handle issue -> get the index for the shortcut to be used for this issue
                            //get all fixVersion of the actual issue
                            List<FixVersion> fixVersions = issue.getFields().getFixVersions();
                            //foreach fixVersion
                            for (FixVersion fixVersion : fixVersions) {
                                Boolean testAvailable = false;
                                String testkey=null;
                                setFixVersions.add(fixVersion.getName());
                                //is there already a testcase for given issue and fixVersion combination
                                Issue testcase = client.isTestAvailable(issue, fixVersion,
                                        client.config.getJiraUser(), client.config.getJiraPassword());
                                if (testcase != null) {//then
                                    testAvailable = true;
                                    testkey = testcase.getKey();
                                }else{
                                    if(!client.config.getDebug()){
                                        //create payload for the testcase
                                        if(Arrays.asList(Const.TESTCASE_TYPES).contains(
                                                issue.getFields().getIssuetype().getName())){

                                            Post response4CreateTest = client.createTestcase(issue, fixVersion, shortcut,
                                                    job, issue.getFields().getDescription());
                                            infoTests.addIssue(fixVersion.getName(), response4CreateTest.getKey());
                                            logger.info(String.format("RESPONSE {testkey=%s, type=testcase_create, " +
                                                    "created=true}", response4CreateTest.toString()));

                                        }else if(Arrays.asList(Const.TESTTASK_TYPES).contains(
                                                issue.getFields().getIssuetype().getName())){

                                            Post response4CreateTask = client.createTesttask(
                                                    issue, fixVersion, shortcut, job);
                                            infoTests.addIssue(fixVersion.getName(), response4CreateTask.getKey());
                                            logger.info(String.format("RESPONSE {taskkey=%s, type=testtask_create, " +
                                                            "created=true}", response4CreateTask.toString()));
                                        }
                                    }else{
                                        infoIssues.addIssue(fixVersion.getName(), issue.getKey());
                                    }
                                }
                                logger.debug(String.format("Issue handled! key: %s, shortcut: %s, status: %s, " +
                                                "fixVersion: %s, testAvailable: %s, testkey: %s", issue.getKey(),
                                        shortcut, issue.getFields().getStatus().getName(), fixVersion.getName(),
                                        testAvailable.toString(), testkey));
                            }
                        }else{
                            logger.debug(String.format("Issue ignored! key: %s, shortcut: %s, status: %s",
                                    issue.getKey(), shortcut, issue.getFields().getStatus().getName()));
                        }
                    }
                }
                if(client.config.getDebug())
                    infoIssues.debug();
                else {
                    infoTests.debug();
                    //infoTests.persist(connection);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseCmdParams(CommandLine cmd){
        if (cmd.hasOption("d")) config.setDebug(true);
            else config.setDebug(false);
        //jira
        config.setJiraServer(cmd.getOptionValue( "js", Const.DEFAULT_JIRA_SERVER ));
        config.setJiraUser(cmd.getOptionValue( "ju"));
        config.setJiraPassword(cmd.getOptionValue( "jp"));
        //mysql-options
        config.setMysqlDatabase(cmd.getOptionValue("mda", Const.DEFAULT_MYSQL_JDBC_DATABASE));
        config.setMysqlUrl(cmd.getOptionValue("mur", Const.DEFAULT_MYSQL_JDBC_URL));
        config.setMysqlPort(cmd.getOptionValue("mpo", Const.DEFAULT_MYSQL_JDBC_PORT));
        config.setMysqlDriver(cmd.getOptionValue("mdr", Const.DEFAULT_MYSQL_JDBC_DRIVER));
        config.setMySqlUser(cmd.getOptionValue( "mus"));
        config.setMySqlPassword(cmd.getOptionValue( "mpa"));
        //jobs
        config.setJobsFile(cmd.getOptionValue( "jf"));
    }

    private Post createTesttask(Issue issue, FixVersion fixVersion, String shortcut, Job job) throws Exception {
        Post result;
        String payloadTask = createPayload4Testtask(issue, fixVersion, shortcut,
                job.getDueDate(), job.getPlannedStart(), job.getPlannedEnd() );
        String payloadTest = createPayload4Testcase("Testcase Template",
                job.getTestcaseTemplate(), issue, fixVersion, shortcut, job );

        Post response4CreateTask = postPayload(
                payloadTask,
                config.getJiraServer() + Const.URL_CREATE_ISSUE,
                config.getJiraUser(),
                config.getJiraPassword()
        );
        if(response4CreateTask.getErrors().size() > 0) {
            HttpJson.logErrors(response4CreateTask.getErrors());
            throw new Exception(String.format("Exception creating testcase. " +
                            "See logfile for error messages. {issuekey=%s}",
                    issue.getKey(), response4CreateTask.getErrors().toString()));
        }else{
            //create test
            Post response4CreateTest = postPayload(
                    payloadTest,
                    config.getJiraServer() + Const.URL_CREATE_ISSUE,
                    config.getJiraUser(),
                    config.getJiraPassword());
            if(response4CreateTest.getErrors().size() > 0) {
                HttpJson.logErrors(response4CreateTest.getErrors());
                throw new Exception(String.format("Exception creating testcase. " +
                                "See logfile for error messages. {issuekey=%s}",
                        issue.getKey(), response4CreateTest.getErrors().toString()));
            }else{
                //create link child
                String payloadLinkChild = createPayload4IssueLink(response4CreateTest.getKey(),
                        response4CreateTask.getKey(), "Child-Issue",
                        String.format(job.getLinkComment(), shortcut, response4CreateTest.getKey(), issue.getKey())
                );
                Post response4CreateLinkChild = postPayload(
                        payloadLinkChild,
                        config.getJiraServer() + Const.URL_CREATE_LINK,
                        config.getJiraUser(),
                        config.getJiraPassword()
                );
                if(response4CreateLinkChild.getErrors().size() > 0) {
                    HttpJson.logErrors(response4CreateLinkChild.getErrors());
                    throw new Exception(String.format("Exception creating link 'child-issue'. See logfile for error messages"));
                }else{
                    //create link tests
                    String payloadLinkTest = createPayload4IssueLink(issue.getKey(), response4CreateTask.getKey(),
                            "Tests", "Test comment!");
                    Post response4CreateLinkTest = postPayload(
                            payloadLinkTest,
                            config.getJiraServer() + Const.URL_CREATE_LINK,
                            config.getJiraUser(),
                            config.getJiraPassword()
                    );
                    if(response4CreateLinkTest.getErrors().size() > 0) {
                        HttpJson.logErrors(response4CreateLinkChild.getErrors());
                        throw new Exception(String.format("Exception creating link 'tests'. See logfile for error messages"));
                    }else{
                        result = response4CreateTask;
                    }
                }
            }
        }
        return result;
    }

    private Post createTestcase(Issue issue, FixVersion fixVersion, String shortcut, Job job, String description)
            throws Exception {
        String payloadTest = createPayload4Testcase(issue.getFields().getSummary(), description, issue, fixVersion,
                shortcut, job );

        Post response4CreateTest = postPayload(payloadTest, config.getJiraServer() + Const.URL_CREATE_ISSUE,
                config.getJiraUser(), config.getJiraPassword());

        if(response4CreateTest.getErrors().size() > 0) {
            HttpJson.logErrors(response4CreateTest.getErrors());
            throw new Exception(String.format("Exception creating testcase. " +
                            "See logfile for error messages. {issuekey=%s}",
                    issue.getKey(), response4CreateTest.getErrors().toString()));
        }else {
            //link testcase to jira
            logger.info(
                    String.format("TESTCASE created {issuekey=%s, testcasekey=%s}",
                            issue.getKey(), response4CreateTest.getKey()));
            String payloadLink = createPayload4IssueLink(issue.getKey(),
                    response4CreateTest.getKey(), "Tests", job.getLinkComment().toString());
            Post response4CreateLink = postPayload(
                    payloadLink,
                    config.getJiraServer() + Const.URL_CREATE_LINK,
                    config.getJiraUser(),
                    config.getJiraPassword()
            );
            if(response4CreateLink.getErrors().size() > 0) {
                HttpJson.logErrors(response4CreateLink.getErrors());
                throw new Exception(String.format("Exception creating testcase. See logfile for error messages"));
            }
        }

        return response4CreateTest;
    }

    private String createPayload4IssueLink(String outwardKey, String inwardKey, String linkType, String linkComment) {
        Payload payload = new Payload();
        String jsonPayload = null;
        //outwardIssue
        OutwardIssue outwardIssue = new OutwardIssue();
        outwardIssue.setKey(outwardKey);
        //inwardIssue
        InwardIssue inwardIssue = new InwardIssue();
        inwardIssue.setKey(inwardKey);
        //Type
        Type type = new Type();
        type.setName(linkType);
        //Comment
        if(linkComment != null){
            Comment comment = new Comment();
            comment.setBody(linkComment);
            payload.setComment(comment);
        }
        payload.setInwardIssue(inwardIssue);
        payload.setOutwardIssue(outwardIssue);
        payload.setType(type);


        jsonPayload = new Gson().toJson(payload);
        return jsonPayload;
    }

    private Post postPayload(String payload, String url, String user, String password) throws Exception {
        Object result = HttpJson.post(url, payload, user, password);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Post response = gson.fromJson((String)result, Post.class);
        return response;
    }

    private Assignee getAssignee(String shortcut)
            throws FileNotFoundException, IOException {

        Assignee result = null;
        final InputStream assigneesIS = this.getClass().getResourceAsStream("/assignees.json");
        JsonReader reader = new JsonReader(new InputStreamReader(assigneesIS, "UTF-8"));
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Assignees assignees = gson.fromJson(reader, Assignees.class);

        for (Assignee assignee : assignees.getAssignees()) {
            String tmpShortcut = assignee.getShortcut();
            if (shortcut.equals(tmpShortcut)) {

                result = assignee;
                break;
            }
        }
        return result;
    }

    private String createPayload4Testcase(String summary, String description, Issue issue, FixVersion fixVersion,
                                          String shortcut, Job job)
            throws Exception {
        String jsonPayload = null;
        //project
        Project project = new Project();
        project.setKey("QAR");
        //issuetype
        Issuetype issuetype = new Issuetype();
        issuetype.setName("Test");
        issuetype.setSubtask(false);
        //components
        List<Component> components = new ArrayList<Component>();
        Component component = new Component();
        component.setName("UNKNOWN");
        components.add(component);
        //labels
        List<String> labels = new ArrayList<String>();
        labels.add("DISPATCH-" + Tools.getActDateAsString("YYYY-MM-dd"));
        labels.add(shortcut);
        //labels.addAll(issue.getFields().getLabels());
        for (Component comp : issue.getFields().getComponents()) {
            String sLabel = comp.getName();
            labels.add(sLabel.replace(" ", "-"));
        }
        //priority
        Priority priority = issue.getFields().getPriority();
        //workpackage -> customfield_13290
        String workpackage = issue.getFields().getIssuetype().getName();
        //versions (affectsVersions)
        List<Version> versions = new ArrayList<Version>();
        for(Version origVersion:issue.getFields().getVersions()) {
            Version version = new Version();
            version.setName(origVersion.getName());
            versions.add(version);
        }
        //fixVersions
        List<FixVersion> fixVersions = new ArrayList<FixVersion>();
        FixVersion currentFixVersion = new FixVersion();
        currentFixVersion.setName(fixVersion.getName());
        fixVersions.add(currentFixVersion);
        //description
        /*String description;
        if(descriptionFromIssue){
            description = issue.getFields().getDescription();
        }else{
            description = "*{color:#d04437}SEE " + issue.getKey() + "{color}*";
        }*/

        //duedate
        //plannedStart 	-> 	customfield_10690
        //planned End	->	customfield_10691
        //assignee
        Assignee assignee = this.getAssignee(shortcut);

        //timetracking
        Timetracking timetracking = new Timetracking();
        timetracking.setOriginalEstimate(job.getOriginalEstimate());

        //set fields
        Fields fields = new Fields();
        fields.setProject(project);
        fields.setSummary(summary);
        fields.setIssuetype(issuetype);
        fields.setComponents(components);
        fields.setLabels(labels);
        fields.setPriority(priority);
        fields.setWorkpackage(workpackage);
        fields.setVersions(versions);
        fields.setFixVersions(fixVersions);
        fields.setDescription(description);
        fields.setDuedate(job.getDueDate());
        fields.setPlannedStart(job.getPlannedStart());
        fields.setPlannedEnd(job.getPlannedEnd());
        fields.setAssignee(assignee);
        fields.setTimetracking(timetracking);

        Payload payload = new Payload(fields);
        jsonPayload = new Gson().toJson(payload);

        return jsonPayload;
    }

    private String createPayload4Testtask(Issue issue, FixVersion fixVersion, String shortcut,
                                          String dueDate, String plannedStart, String plannedEnd)
            throws Exception {
        String jsonPayload = null;
        //project
        Project project = new Project();
        project.setKey("QAR");
        //summary
        String summary = issue.getFields().getSummary();
        //issuetype
        Issuetype issuetype = new Issuetype();
        issuetype.setName("Task");
        issuetype.setSubtask(false);
        //components
        List<Component> components = new ArrayList<Component>();
        Component component = new Component();
        component.setName("UNKNOWN");
        components.add(component);
        //labels
        List<String> labels = new ArrayList<String>();
        //labels.add("DISPATCH-" + Tools.getActDateAsString("YYYY-MM-dd"));
        labels.add(shortcut);
        //labels.addAll(issue.getFields().getLabels());
        for (Component comp : issue.getFields().getComponents()) {
            String sLabel = comp.getName();
            labels.add(sLabel.replace(" ", "-"));
        }
        //priority
        Priority priority = issue.getFields().getPriority();
        //workpackage -> customfield_13290
        String workpackage = issue.getFields().getIssuetype().getName();
        //versions (affectsVersions)
        List<Version> versions = new ArrayList<Version>();
        for(Version origVersion:issue.getFields().getVersions()) {
            Version version = new Version();
            version.setName(origVersion.getName());
            versions.add(version);
        }
        //fixVersions
        List<FixVersion> fixVersions = new ArrayList<FixVersion>();
        FixVersion currentFixVersion = new FixVersion();
        currentFixVersion.setName(fixVersion.getName());
        fixVersions.add(currentFixVersion);
        //description
        //String description = "*{color:#d04437}SEE " + issue.getKey() + "{color}*";
        String description = issue.getFields().getDescription();
        //duedate
        //plannedStart 	-> 	customfield_10690
        //planned End	->	customfield_10691
        //assignee
        Assignee assignee = this.getAssignee(shortcut);

        //timetracking
        /*Timetracking timetracking = new Timetracking();
        timetracking.setOriginalEstimate(originalEstimate);*/

        //set fields
        Fields fields = new Fields();
        fields.setProject(project);
        fields.setSummary(summary);
        fields.setIssuetype(issuetype);
        fields.setComponents(components);
        fields.setLabels(labels);
        fields.setPriority(priority);
        fields.setWorkpackage(workpackage);
        fields.setVersions(versions);
        fields.setFixVersions(fixVersions);
        fields.setDescription(description);
        fields.setDuedate(dueDate);
        fields.setPlannedStart(plannedStart);
        fields.setPlannedEnd(plannedEnd);
        fields.setAssignee(assignee);
        //fields.setTimetracking(timetracking);

        Payload payload = new Payload(fields);
        jsonPayload = new Gson().toJson(payload);

        return jsonPayload;
    }

    private Issue getTest(String self, String user, String password) throws IOException {
        String jsonString = HttpJson.get(self, user, password);
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Issue test = gson.fromJson(jsonString, Issue.class);
        return test;
    }

    private Issue isTestAvailable(Issue issue, FixVersion fixVersion, String user, String password) throws IOException {
        Issue test = null;
        List<Issuelink> issueLinks = issue.getFields().getIssuelinks();

        for (Issuelink issueLink : issueLinks) {
            //does this issue already has a test from QAR project?
            if(issueLink.getInwardIssue() != null
                    && issueLink.getInwardIssue().getKey().contains("QAR")
                    && issueLink.getType().getName().equalsIgnoreCase("Tests")
                    && (issueLink.getInwardIssue().getFields().getIssuetype().getName().equalsIgnoreCase("Test")
                    || issueLink.getInwardIssue().getFields().getIssuetype().getName().equalsIgnoreCase("Task"))
            ) {
                //yes, read it
                InwardIssue inwardIssue = issueLink.getInwardIssue();
                test = this.getTest(inwardIssue.getSelf(), user, password);
                for(FixVersion testFixVersion:test.getFields().getFixVersions()) {
                    if(testFixVersion.getName().equalsIgnoreCase(fixVersion.getName())) {
                        return test;
                    }
                }
            }
            test=null;
        }
        return test;
    }

    private String getShortcut(Issue issue, Integer index, List<String> shortcuts) {
        String shortcut = null;
        if(getShortcutFromIssue(issue, shortcuts) != null) {
            shortcut = getShortcutFromIssue(issue, shortcuts);
        }else {
            shortcut = shortcuts.get(index);
        }
        return shortcut;
    }

    private String getShortcutFromIssue(Issue issue, List<String> shortcuts) {
        String shortcut = null;
        Fields fields = issue.getFields();
        String[] labels = fields.getLabels().toArray(new String[0]);
        for (String label : labels) {

            for(int i=0; i< shortcuts.size();i++) {
                if(label.equalsIgnoreCase(shortcuts.get(i))) {
                    shortcut = label;
                    break;
                }
            }
        }
        return shortcut;
    }

    private Response getJiraIssues(String url, String user, String password) throws IOException {
        Response result ;
        JsonReader issueReader;
        Gson gson = new GsonBuilder().create();
        String jsonString = HttpJson.get(url, user, password);
        issueReader = new JsonReader(new StringReader(jsonString));
        result = gson.fromJson(issueReader, Response.class);
        return result;
    }

    private String getRequestUrl(String jira, String jql, Integer maxResults) throws UnsupportedEncodingException {
        String url = jira + "rest/api/2/search" + "?jql="
                + URLEncoder.encode(jql, StandardCharsets.UTF_8.name())
                + "&maxResults=" + maxResults
                + "&expand=renderedFields";
        return url;
    }

    private Options getOptions(){
        Options options = new Options();
        //help
        Option help = new Option("h", "help", false,"Get help for 'dispatch'!" );
        //jira
        Option jiraServer = new Option("js", "jira-server", true,
                String.format("Jira server to use for dispatch. Optional. Default: %s", Const.DEFAULT_JIRA_SERVER));
        Option jiraUser = new Option("ju", "jira-user", true,
                "Jira user to use for dispatch. MANDATORY. ");
        jiraUser.setRequired(true);
        Option jiraPassword = new Option("jp", "jira-password", true,
                "Jira password to use for dispatch. MANDATORY");
        jiraPassword.setRequired(true);

        //mysql
        Option mysqlUser = new Option("mus", "mysql-user", true,
                "Mysql user to use for dispatch. MANDATORY. ");
        mysqlUser.setRequired(true);
        Option mysqlPassword = new Option("mpa", "mysql-password", true,
                "Mysql password to use for dispatch. MANDATORY");
        mysqlPassword.setRequired(true);
        Option mysqlDatabase = new Option("mda", "mysql-database", true,
                String.format("Mysql database to use for dispatch. Optional. Default: %s", Const.DEFAULT_MYSQL_JDBC_DATABASE));
        Option mysqlUrl = new Option("mur", "mysql-url", true,
                String.format("Mysql connection-url to use for dispatch. Optional. Default: %s", Const.DEFAULT_MYSQL_JDBC_URL));
        Option mysqlPort = new Option("mpo", "mysql-port", true,
                String.format("Mysql port to use for dispatch. Optional. Default: %s", Const.DEFAULT_MYSQL_JDBC_PORT));
        Option mysqlDriver = new Option("mdr", "mysql-driver", true,
                String.format("Mysql jdbc-driver to use for dispatch. Optional. Default: %s", Const.DEFAULT_MYSQL_JDBC_DRIVER));
        //jobs
        Option jobsFile = new Option("jf", "jobs-file", true,
                "Gson jobs-configuration file. MANDATORY.");
        jobsFile.setRequired(true);
        //debug
        Option debug = new Option("d", "debug", false,
                String.format("Debug mode. No test cases will be dispatched. Optional. Default: %s", Const.DEFAULT_DEBUG));

        options.addOption(help);
        options.addOption(jiraServer);
        options.addOption(jiraUser);
        options.addOption(jiraPassword);

        options.addOption(mysqlUser);
        options.addOption(mysqlPassword);
        options.addOption(mysqlDatabase);
        options.addOption(mysqlUrl);
        options.addOption(mysqlPort);
        options.addOption(mysqlDriver);

        options.addOption(jobsFile);
        options.addOption(debug);

        return options;
    }
//                                            payloadTest = client.createPayload4Testcase(true, issue,
//                                                    fixVersion, shortcut, job.getOriginalEstimate(), job.getDueDate(),
//                                                    job.getPlannedStart(), job.getPlannedEnd() );
//                                            Post response4CreateTest = client.postPayload(
//                                                    payloadTest,
//                                                    client.config.getJiraServer() + Const.URL_CREATE_ISSUE,
//                                                    client.config.getJiraUser(),
//                                                    client.config.getJiraPassword()
//                                            );
//                                            if(response4CreateTest.getErrors().size() > 0) {
//                                                HttpJson.logErrors(response4CreateTest.getErrors());
//                                                throw new Exception(String.format("Exception creating testcase. " +
//                                                                        "See logfile for error messages. {issuekey=%s}",
//                                                        issue.getKey(), response4CreateTest.getErrors().toString()));
//                                            }else {
//                                                //link testcase to jira
//                                                logger.info(
//                                                        String.format("TESTCASE created {issuekey=%s, testcasekey=%s}",
//                                                        issue.getKey(), response4CreateTest.getKey()));
//                                                String payloadLink = client.createPayload4IssueLink(issue,
//                                                        response4CreateTest.getKey(), job.getLinkComment().toString());
//                                                Post response4CreateLink = client.postPayload(
//                                                        payloadLink,
//                                                        client.config.getJiraServer() + Const.URL_CREATE_LINK,
//                                                        client.config.getJiraUser(),
//                                                        client.config.getJiraPassword()
//                                                );
//                                                if(response4CreateLink.getErrors().size() > 0) {
//                                                    HttpJson.logErrors(response4CreateLink.getErrors());
//                                                    throw new Exception(String.format("Exception creating testcase. " +
//                                                                    "See logfile for error messages. {testkey=%s}",
//                                                            testcase.getKey()));
//                                                }
//                                                infoTests.addIssue(fixVersion.getName(), response4CreateTest.getKey());
//                                            }
}