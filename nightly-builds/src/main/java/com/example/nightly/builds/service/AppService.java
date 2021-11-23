package com.example.nightly.builds.service;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.example.nightly.builds.Constants;
import com.example.nightly.builds.model.entities.Build;
import com.example.nightly.builds.model.entities.Job;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class AppService
{


    public Job getAllBuilds(String jobTitle,String numberOfBuilds)
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

        String url = JENKINS_URL + jobTitle + Constants.API_URL + "{0," + numberOfBuilds + "}";
        JsonNode jsonNode = null;
        Job jenkinsJob = null;
        try
        {

            final ObjectMapper om = new ObjectMapper();
            if (!getStringInfoFromUrl(url).toString().equals(""))
            {
                jsonNode = om.readTree(getStringInfoFromUrl(url).toString());
            }
            else
            {
                return jenkinsJob;
            }
            ArrayList<Build> builds = new ArrayList<Build>();

            for (JsonNode buildNode : jsonNode.get("builds"))
            {
                builds.add(new Build(buildNode, jobTitle));
            }
            jenkinsJob = new Job(jobTitle, JENKINS_URL + jobTitle + "/", builds);

        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        return jenkinsJob;
    }


    public List<Job> getAllJobsInfo(Map<String, String> jobsNames, String numberOfBuilds)
    {

        ArrayList<Job> jobs = new ArrayList<>();
        for (Map.Entry jobTitle : jobsNames.entrySet())
        {
            if (jobTitle.getValue() == null || jobTitle.getValue().equals(""))
            {
                continue;
            }
            else
            {
                if(getAllBuilds(jobTitle.getValue().toString(), numberOfBuilds)!=null) {
                    jobs.add(getAllBuilds(jobTitle.getValue().toString(), numberOfBuilds));
                }
            }

        }
        return jobs;
    }

    
    private StringBuilder getStringInfoFromUrl(final String url)
    {
        StringBuilder response = new StringBuilder();
        try
        {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            int responseCode = connection.getResponseCode();
            InputStream inputStream;
            if (200 <= responseCode && responseCode <= 299)
            {
                inputStream = connection.getInputStream();
            }
            else
            {
                inputStream = connection.getErrorStream();
                return response;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String currentLine;

            while ((currentLine = in.readLine()) != null)
                response.append(currentLine);

            in.close();

        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

        return response;
    }


    public Map<String, List<List<Object>>> getStatistics(Map<String, String> jobsNames, String numberOfBuilds)
    {

        List<Job> jobs = getAllJobsInfo(jobsNames, numberOfBuilds);
        Map<String, List<List<Object>>> statistics = new HashMap<>();
        for (Job j : jobs)
        {
            Map<String, Integer> map = j.getStatistics();
            List<List<Object>> jobStat = new ArrayList<>();
            map.forEach((key, value) -> {
                List<Object> inside = new ArrayList<>();
                inside.add(key);
                inside.add(value);
                jobStat.add(inside);
            });
            statistics.put(j.getTitle(),jobStat);
        }
        return statistics;
    }
}
