package com.example.nightly.builds.model.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONObject;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Job {
    private String title;
    private String jobLink;
    private List<Build> builds;
    private Map<String,Integer> statistics;



    public Job(String title, String jobLink, List<Build> builds)
    {
        this.title = title;
        this.jobLink = jobLink;
        this.builds = builds;
        statistics = new HashMap<>();
        statistics.put("SUCCESS", 0);
        statistics.put("UNSTABLE", 0);
        statistics.put("FAILURE", 0);
        statistics.put("ABORTED", 0);
        statistics.put("RUNNING", 0);
        for (Build b : builds)
        {
            if (b.getStatus().equals("null"))
            {
                statistics.put("RUNNING", statistics.get("RUNNING") + 1);
            }
            else
            {
                statistics.put(b.getStatus(), statistics.get(b.getStatus()) + 1);
            }

        }


    }
}
