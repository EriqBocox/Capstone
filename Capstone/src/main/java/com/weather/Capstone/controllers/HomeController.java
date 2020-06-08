package com.weather.Capstone.controllers;

import com.weather.Capstone.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private static HttpURLConnection connection;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Welcome To my Weather Radar!");
        model.addAttribute("city", accessApi().getString("name"));
        model.addAttribute("country", accessApi().getJSONObject("sys").getString("country"));
        model.addAttribute("weatherStatus", ((JSONObject) accessApi().getJSONArray("weather").get(0)).getString("main"));
        model.addAttribute("description", ((JSONObject) accessApi().getJSONArray("weather").get(0)).getString(
                "description"));
        model.addAttribute("feelsLike", accessApi().getJSONObject("main").getFloat("feels_like"));
        model.addAttribute("tempMin", accessApi().getJSONObject("main").getFloat("temp_min"));
        model.addAttribute("tempMax", accessApi().getJSONObject("main").getFloat("temp_max"));
        return "home";
    }

    public static JSONObject parse(String responseBody) {
        JSONObject albums = new JSONObject(responseBody);
//        System.out.println(albums.getJSONObject("main").getFloat("temp_min"));

        return albums;

    }

    public static JSONObject accessApi () {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=90005," +
                    "us&appid=f863c4ab54416c6ba8d0fa855c404a3f");
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            return parse(responseContent.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }
}
