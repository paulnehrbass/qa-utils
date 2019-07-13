package com.assentis.qa.info;


import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Info {

    final static Logger logger = Logger.getLogger(Info.class);
    private String type;
    private Map<String, List<String>> issues4FixVersion;


    public Info(String type) {
        this.type = type;
        this.issues4FixVersion = new HashMap<String, List<String>>();
    }

    public String getJql4FixVersion(String fixVersion){
        StringBuilder builder = new StringBuilder();
        List<String> list =  issues4FixVersion.get(fixVersion);
        if(list == null){
            builder.append(String.format("JQL4FIXVERSION {fixVersion=%s, issuesFound=%s}", fixVersion, "false"));
        }else{
            builder.append("key in (");
            int i=1;
            for (String key:list) {
                builder.append(key);
                if(i!=list.size())
                    builder.append(",");
                i++;
            }
            builder.append(")");
            builder.append(String.format(" and fixVersion in (%s)", fixVersion));
        }
       return builder.toString();
    }

    public void debug(){
        if(issues4FixVersion.size() > 0){
            for (Map.Entry<String, List<String>> entry : issues4FixVersion.entrySet()) {
                //System.out.println();
                logger.info(String.format("JQL {type=%s fixVersion=%s, jql=fixVersion in (\"%s\") and key in %s",
                        getType(),
                        entry.getKey(),
                        entry.getKey(),
                        entry.getValue().toString()
                                .replace("[","(").replace("]",")")));
            }
        }else{
            logger.info(String.format("LOG {message=Nothing to debug for info of type '%s'", getType()));
        }
    }

   /* public void persist(Connection connection) throws SQLException {
        if(issues4FixVersion.size() > 0){
            for (Map.Entry<String, List<String>> entry : issues4FixVersion.entrySet()) {
                String fixVersion = entry.getKey();
                for(String key : entry.getValue()) {
                    com.assentis.qa.cockpit.db.Dispatch dispatch = new Dispatch("", "");
                    DispatchDAO daoDispatch = new DispatchDAO(connection, dispatch);
                    daoDispatch.insertDispatch();
                }
            }
        }else{
            logger.info(String.format("LOG {message=Nothing to persist for info of type '%s'", getType()));
        }

    }*/


    public void addIssue(String fixVersion, String testkey){
        List<String> keyList = issues4FixVersion.get(fixVersion);
        if(keyList == null){
            keyList = new ArrayList<String>();
        }
        keyList.add(testkey);
        issues4FixVersion.put(fixVersion, keyList);
    }

    public Map<String, List<String>> getIssues4FixVersion() {
        return issues4FixVersion;
    }

    public void setIssues4FixVersion(Map<String, List<String>> issues4FixVersion) {
        this.issues4FixVersion = issues4FixVersion;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Info{" +
                "type='" + type + '\'' +
                ", issues4FixVersion=" + issues4FixVersion +
                '}';
    }
}