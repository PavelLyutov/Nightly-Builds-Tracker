package com.example.nightly.builds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.nightly.builds.model.entities.Settings;
import com.example.nightly.builds.service.AppService;
import com.example.nightly.builds.service.SettingsService;

@Controller
public class SettingsController {

    @Autowired
    private AppService appService;

    @Autowired
    private SettingsService settingsService;

    @GetMapping(value = "/settings")
    public String showSettings(Model model)
    {
        model.addAttribute("jobs",settingsService.loadProperties());
        model.addAttribute("buildNumbers",settingsService.loadShowLastBuildsSize());
        return "settings";
    }


    @PostMapping(value = "/settings")
    public String editSettings(@ModelAttribute Settings settings)
    {
        settingsService.updateSettings(settings);
        return "redirect:/settings";
    }

}
