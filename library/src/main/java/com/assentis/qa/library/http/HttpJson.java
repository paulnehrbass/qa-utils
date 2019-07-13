package com.assentis.qa.library.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;


public class HttpJson {
    final static Logger logger = Logger.getLogger(HttpJson.class);


    public static void logErrors(Map<String,String> errors) {
        Iterator<Map.Entry<String, String>> it = errors.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            logger.error(String.format("ERROR {key=%s}, value=%s", pair.getKey(), pair.getValue()));
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public static Object post(String sUrl, String sJson, String user, String password) throws Exception {
        //new JSONParser();
        String encoded = Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost(sUrl);
        postRequest.addHeader("authorization", "Basic " + encoded);
        postRequest.addHeader("content-type", "application/json");
        StringEntity input = new StringEntity(sJson, StandardCharsets.UTF_8);
        postRequest.setEntity(input);
        HttpResponse response = httpClient.execute(postRequest);
        String result = null;
        if (response.getEntity() == null) {
            result = "{\"responseCode\": \"" + response.getStatusLine().getStatusCode() + "\"}";
        } else {
            result = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
            if (result.equalsIgnoreCase("")) {
                result = "{\"responseCode\": \"" + response.getStatusLine().getStatusCode() + "\"}";
            }
        }

        return result;
    }

    public static int put(String sUrl, String sJson, String user, String password) throws Exception {
        //new JSONParser();
        String encoded = Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPut putRequest = new HttpPut(sUrl);
        putRequest.addHeader("authorization", "Basic " + encoded);
        putRequest.addHeader("content-type", "application/json");
        StringEntity input = new StringEntity(sJson, StandardCharsets.UTF_8);
        putRequest.setEntity(input);
        HttpResponse response = httpClient.execute(putRequest);
        int status = response.getStatusLine().getStatusCode();
        if (status != 204) {
            throw new Exception("Error in UpdateFixVersion!");
        } else {
            return response.getStatusLine().getStatusCode();
        }
    }

    public static String get(String sUrl, String user, String password) throws IOException {
        StringBuffer result = new StringBuffer();
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(sUrl);
        String encoded = Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
        request.addHeader("Accept", "application/json");
        request.addHeader("Authorization", "Basic " + encoded);
        HttpResponse response = client.execute(request);
        BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String output;
        while((output = br.readLine()) != null) {
            result.append(output);
        }

        return result.toString();
    }

    public static int delete(String sUrl, String user, String password) throws Exception {
        new StringBuffer();
        HttpClient client = HttpClientBuilder.create().build();
        HttpDelete request = new HttpDelete(sUrl);
        String encoded = Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
        request.addHeader("Accept", "application/json");
        request.addHeader("Authorization", "Basic " + encoded);
        HttpResponse response = client.execute(request);
        int status = response.getStatusLine().getStatusCode();
        if (status != 204) {
            throw new Exception("Error in DeleteWorklogEntry!");
        } else {
            return response.getStatusLine().getStatusCode();
        }
    }
}
