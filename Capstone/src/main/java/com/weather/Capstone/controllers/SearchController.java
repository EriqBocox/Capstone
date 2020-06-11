package com.weather.Capstone.controllers;

import com.weather.Capstone.controllers.HomeController;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String showSearchForm(HttpServletRequest request, Model model) {
        return "search";
    }

    @PostMapping("/search")
    public String processSearchForm(@RequestParam String city, HttpServletRequest request,
                                    Model model){
        JSONObject apiData = HomeController.accessApi(city);
        model.addAttribute("city", apiData.getString("name"));
        model.addAttribute("country", apiData.getJSONObject("sys").getString("country"));
        model.addAttribute("icon", ((JSONObject) apiData.getJSONArray("weather").get(0)).getString("icon"));
        model.addAttribute("weatherStatus", ((JSONObject) apiData.getJSONArray("weather").get(0)).getString("main"));
        model.addAttribute("description", ((JSONObject) apiData.getJSONArray("weather").get(0)).getString("description"));
        model.addAttribute("feelsLike", tempConversion(apiData.getJSONObject("main").getFloat("feels_like")));
        model.addAttribute("temp", tempConversion(apiData.getJSONObject("main").getFloat("temp")));
        model.addAttribute("tempMin", tempConversion(apiData.getJSONObject("main").getFloat("temp_min")));
        model.addAttribute("tempMax", tempConversion(apiData.getJSONObject("main").getFloat("temp_max")));
        model.addAttribute("F", "°F");
        return "/search";
    }

    public static double tempConversion(double degreesKelvin){
       double f = Math.floor(((degreesKelvin - 273) * 9d/5) + 32);
        return f;
    }

}
