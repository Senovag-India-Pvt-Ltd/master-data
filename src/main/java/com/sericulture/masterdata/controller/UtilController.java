package com.sericulture.masterdata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@RestController
@RequestMapping("/v1/util")
public class UtilController {

    @GetMapping("/current-time")
    public String getCurrentTime() {
        // Return the current server time in a formatted string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC")); // Set the desired timezone
        return sdf.format(new Date());
    }

    @GetMapping("/current-time-millisec")
    public Long getCurrentTimeMilliSec() {
        // Return the current server time in milliseconds
        return System.currentTimeMillis();
    }

}