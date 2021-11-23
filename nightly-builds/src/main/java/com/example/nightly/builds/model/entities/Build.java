package com.example.nightly.builds.model.entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.example.nightly.builds.Constants;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Build
{
    private String status;
    private String id;
    private String description;
    private String buildLink;
    private String allureLink;
    private String consoleLink;
    private String timestamp;


    public Build(JsonNode jsonNode, String jobTitle)
    {
        this.id = jsonNode.get("id").asText().trim();
        this.status = jsonNode.get("result").asText().trim();
        this.description = jsonNode.get("description").asText().trim().replaceAll("<br>"," , ");
        this.buildLink = prepareUrl(jobTitle, jsonNode.get("id").asText().trim(), "");

        this.consoleLink = prepareUrl(jobTitle, jsonNode.get("id").asText().trim(), Constants.FULL_CONSOLE);
        this.allureLink = prepareUrl(jobTitle, jsonNode.get("id").asText().trim(), Constants.ALLURE);

        Date date = new Date(Long.parseLong(jsonNode.get("timestamp").asText().trim()));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        this.timestamp = dateFormat.format(date);
     }


    private String prepareUrl(final String jobTitle, final String id, final String end)
    {
        String JENKINS_URL=null;
        Properties p = new Properties();
        try (FileInputStream fr = new FileInputStream(new File("jenkins.properties"));)
        {
            p.load(fr);
            JENKINS_URL = p.getProperty("showLastBuilds");

        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

        return JENKINS_URL.concat(jobTitle).concat("/").concat(id).concat(end);

    }
}
