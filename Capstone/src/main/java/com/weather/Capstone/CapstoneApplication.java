package com.weather.Capstone;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import org.hibernate.mapping.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;


@SpringBootApplication
public class CapstoneApplication {

	private static HttpURLConnection connection;

	public static void main(String[] args) {
		SpringApplication.run(CapstoneApplication.class, args);
		accessApi().getJSONObject("main").getFloat("temp_min");
	}

	public static JSONObject parse (String responseBody){
		JSONObject albums = new JSONObject(responseBody);
		return albums;
	}

	public static JSONObject accessApi () {
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("http://api.openweathermap.org/data/2.5/weather?zip=08081," +
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
