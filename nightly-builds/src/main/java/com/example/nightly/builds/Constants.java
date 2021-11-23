package com.example.nightly.builds;

public class Constants {
  //  public static final String JENKINS_URL ; get it from jenkins.properties
    public static final String API_URL = "/api/json?tree=builds[id,result,description,timestamp,actions[parameters[name,value]]]";
    public static final String ALLURE = "/allure/";
    public static final String FULL_CONSOLE = "/consoleFull";
}
