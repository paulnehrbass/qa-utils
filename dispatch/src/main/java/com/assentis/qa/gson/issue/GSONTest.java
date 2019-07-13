package com.assentis.qa.gson.issue;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class GSONTest {

    public static void main(String[] args){
        String jsonFile = "C:\\DEV\\workspaces\\QA_COCKPIT\\src\\main\\resources\\test.json";
        Gson gson = new Gson();

        try {
            JsonReader reader = new JsonReader(new FileReader(jsonFile));
            Response response = gson.fromJson(reader, Response.class);
            System.out.println(response.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}