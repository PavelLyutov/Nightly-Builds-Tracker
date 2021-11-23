package com.example.nightly.builds.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.nightly.builds.model.entities.Settings;

@Service
public class SettingsService {


    public void updateSettings(Settings model){
        saveProperties(model);
    }

    public static void saveProperties(Settings settings)
    {
        try (FileOutputStream fr = new FileOutputStream(new File("nightly.properties"));)
        {
            Properties p = new Properties();
            if(settings.getShowLastBuilds() != null){
                p.setProperty("showLastBuilds",settings.getShowLastBuilds());
            }

            p.setProperty("job1", settings.getJob1());
            p.setProperty("job2", settings.getJob2());
            p.setProperty("job3", settings.getJob3());
            p.setProperty("job4", settings.getJob4());
            p.setProperty("job5", settings.getJob5());
            p.setProperty("job6", settings.getJob6());
            p.setProperty("job7", settings.getJob7());
            p.setProperty("job8", settings.getJob8());
            p.setProperty("job9", settings.getJob9());
            p.setProperty("job10", settings.getJob10());
            p.store(fr,"");
            
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


    public Map<String, String> loadProperties()
    {
        Properties p = new Properties();
        Map<String, String> jobs = new HashMap();
        try (FileInputStream fr = new FileInputStream(new File("nightly.properties"));)
        {
            p.load(fr);
            for (int i = 1; i <= 10; i++)
            {
                if ((p.getProperty("job".concat(String.valueOf(i)))) == null )
                {
                    jobs.put("job".concat(String.valueOf(i)), "");
                }
                else
                {
                    jobs.put("job".concat(String.valueOf(i)), p.getProperty("job".concat(String.valueOf(i))));
                }

            }

        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        return jobs;
    }


    public String loadShowLastBuildsSize()
    {
        Properties p = new Properties();
        String showLastBuilds = null;
        try (FileInputStream fr = new FileInputStream(new File("nightly.properties"));)
        {
            p.load(fr);
            showLastBuilds = p.getProperty("showLastBuilds");

        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }
        return showLastBuilds;
    }
}
