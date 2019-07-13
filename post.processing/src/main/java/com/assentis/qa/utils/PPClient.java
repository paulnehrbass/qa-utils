package com.assentis.qa.utils;

import com.assentis.db.common.AuthenticationCredentials;
import com.assentis.db.iocworkflow.AsyncProcessorInterface;
import com.assentis.db.iocworkflow.client.AsyncProcessorProxyFactory;
import com.assentis.db.iocworkflow.client.ProcessorProxyFactory;
import com.assentis.db.iocworkflow.impl.BeanRuntimeProperty;
import com.assentis.db.service.FileSplitterRule;
import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;


import java.io.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PPClient {

    final static Logger logger = Logger.getLogger(PPClient.class);

    final static String httpInvoker = "/http-invoker.xml";
    final static String processName = "qa-postprocessing-processor";
    final static String fileSplitter = "adb_fileSplitterTask";
    final static String sourceFileName = "/massProductionTestData.xml";

    final static String sourceFolder= "input";
    final static String docbaseServer = "http://localhost:14002/DocBase2";
    final static String amountInputFiles = "10";
    final static String defaultChunkSize = "1";
    //final static String applicationPath = "c:\\QA\\Tools\\PostProcessing";
    final static String applicationPath = System.getProperty("user.dir");

    public static void main(String args[]){
        System.setProperty("docbase.remoting.context", httpInvoker);

        PPClient client = new PPClient();
        Options options = client.getOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(160);
        String server;
        Integer amount;
        Integer chunksize;
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse( options, args);
            if(cmd.hasOption("h")) {
                formatter.printHelp( "PPClient", options );
            }else{
                server = cmd.getOptionValue( "s", docbaseServer );
                amount = Integer.parseInt(cmd.getOptionValue("a", amountInputFiles));
                chunksize = Integer.parseInt(cmd.getOptionValue("c", defaultChunkSize));
                logger.debug(String.format("server: %s, amount: %d", server, amount));

                InputStream inputStream = PPClient.class.getResourceAsStream(sourceFileName);
                File srcFile = client.getFileFromInputStream(sourceFileName, inputStream);
                logger.debug(String.format("srcFile: %s", srcFile.getPath()));
                List<String> listFiles = new ArrayList<String>();
                File inputDir = new File(sourceFolder);
                if(!inputDir.isDirectory()){
                    inputDir.mkdir();
                }

                for(int i=0; i<amount; i++) {
                    //String path = new File(".").getCanonicalPath();
                    String destFileName = applicationPath + "\\" + sourceFolder + "\\" + "input-" + i + ".xml";
                    File destFile = new File(destFileName);

                    FileUtils.copyFile(srcFile, destFile);

                    logger.debug(String.format("File created: %s", destFileName));
                    listFiles.add(destFileName);
                }

                ArrayList<BeanRuntimeProperty> seedData = new ArrayList<BeanRuntimeProperty>();
                //seedData.add(new BeanRuntimeProperty(fileSplitter, "ignoreDuplicateFiles", true));
                seedData.add(new BeanRuntimeProperty(fileSplitter, "encoding", "UTF-8"));
                seedData.add(new BeanRuntimeProperty(fileSplitter,"fileSplitterRule",
                        client.defineFileSplitterRules(chunksize)));
                seedData.add(new BeanRuntimeProperty(fileSplitter,"sourceFiles", listFiles.toArray(new String[0])));

                AuthenticationCredentials credentials = new AuthenticationCredentials("system", "system");
                AsyncProcessorInterface svc = AsyncProcessorProxyFactory.create(
                        processName, ProcessorProxyFactory.REMOTE_HTTP, server, credentials);


                try{
                    String jobId = svc.doSeededTasks(seedData);
                    logger.debug(String.format("Job-Id: %s", jobId));
                } catch(Exception e){
                    logger.error(e.toString());
                    e.printStackTrace();
                }

            }


        } catch (ParseException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }


    }

    private static FileSplitterRule[] defineFileSplitterRules(Integer chunksize) {
        FileSplitterRule[] result = new FileSplitterRule[1];

        result[0] = new FileSplitterRule();
        result[0].setXmlSplitXPath("/docs/doc");
        result[0].setMaxChunkSize(chunksize);
        result[0].setParentBegin("<docs>");
        result[0].setTemplateAlias("sample_massProduction");
        result[0].setOutputFormat("XEP");
        result[0].setContentId("application/xml");
        result[0].setDelegateBeanRef("adb_inDataDatabaseSegmenter");
        result[0].setElementCountPath("/docs/doc");
        //result[0].setMaxElementsSize(10);
        //result[0].setBigDocThreshold(100);
        return result;
    }

    private File getFileFromInputStream(String filename, InputStream inputStream)
            throws IOException {
        File file = new File(filename.replace("/", ""));

        OutputStream outputStream = new FileOutputStream(file);

        IOUtils.copy(inputStream, outputStream);
        return file;
    }

    // get file from classpath, resources folder
    private File getFileFromResources(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException(String.format("File not found: %s", fileName));
        } else {
            return new File(resource.getFile());
        }
    }

    private Options getOptions(){
        Options options = new Options();

        Option help = new Option("h", "help", false,"print this message" );
        Option server = new Option ("s", "server",true,
                "DocBase server url -> default: http://localhost:14002/DocBase2");
        Option amount = new Option("a", "amount", true,
                String.format("Amount of input files to be processed by filesplitter task. Default: %s",
                        amountInputFiles));
        Option chunksize = new Option("c", "chunksize", true,
                String.format("Default chunksize for docbase filesplitter task. Default: %s", defaultChunkSize));

        Option username = new Option("u", "username", true,
                String.format("Mandatory: DocBase username to execute postprocessing"));
        username.setRequired(true);

        Option password = new Option("p", "password", true,
                String.format("Mandatory: DocBase password to execute postprocessing"));
        password.setRequired(true);


        options.addOption(help);
        options.addOption(server);
        options.addOption(amount);
        options.addOption(chunksize);
        options.addOption(username);
        options.addOption(password);
        return options;
    }


}
