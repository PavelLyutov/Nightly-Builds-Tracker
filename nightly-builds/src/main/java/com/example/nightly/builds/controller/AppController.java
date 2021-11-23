package com.example.nightly.builds.controller;



import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.nightly.builds.service.AppService;
import com.example.nightly.builds.service.SettingsService;


@Controller
public class AppController {

    @Autowired
    private AppService appService;

    @Autowired
    private SettingsService settingsService;

    @GetMapping(value = "/builds")
    public String getAllBuilds(Model model)
    {
        model.addAttribute("data",
                           appService.getAllJobsInfo(settingsService.loadProperties(),
                                                     settingsService.loadShowLastBuildsSize()));
        return "builds";
    }


    @GetMapping(value = "/")
    public String homepage()
    {
        return "redirect:/builds";
    }


    @GetMapping(value = "/info")
    public String info(Model model) {
        return "info";
    }

    @GetMapping(value = "/statistics")
    public String stats(Model model) {
        model.addAttribute("chartData",
                           appService.getStatistics(settingsService.loadProperties(),
                                                     settingsService.loadShowLastBuildsSize()));
        return "statistics";
    }


}
