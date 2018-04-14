package com.example.panorama.model.low_level;

import android.os.AsyncTask;
import android.os.Bundle;

import com.example.panorama.Mediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by IvanGlez on 30/03/2018.
 */

public class WeatherAPIConnection extends AsyncTask<String, Void, Void> {
    private String data;
    private boolean error;
    private Mediator mediator;

    public WeatherAPIConnection() {
        data = "";
        error = false;
        mediator = Mediator.getInstance();
    }

    // This method is called after onExecute
    protected Void doInBackground(String... urls) {
        BufferedReader reader = null;
        // Data is send
        try {
            // Current data is retrieved
            URL url = new URL(urls[0]);
            data = retrieveData(url);

            ArrayList<String> data = getWeatherInfo(this.data);


            Bundle extras = new Bundle();
            extras.putSerializable(Mediator.WEATHER_DATA, data);

            mediator.sendBroadcast(Mediator.WEATHER_INFO, extras);


            // Retrieved info is sent to the PresenterPanoramaPreview

        } catch (Exception ex) {
            mediator.sendBroadcast(Mediator.WEATHER_INFO, null);
        }
        return null;
    }

    private String retrieveData(URL url) {
        BufferedReader reader = null;
        // Data is sent
        try {
            // POST request is sent to RESTful web server
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.connect();
            // Answer is retrieved
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + " ");
            }
            // Append Server Response To Content String
            return sb.toString();
        } catch (Exception ex) {
            error = true;
        } finally {
            try {
                reader.close();
            } catch (Exception ex) {
                error = true;
            }
        }
        return null;
    }


    private ArrayList<String> getWeatherInfo(String data) throws JSONException {
        // A new JSON object is created from data retrieved from server
        // Steps described in the following link are followed: https://openweathermap.org/current#current_JSON
        ArrayList<String> list = new ArrayList<>();
        JSONObject JSONAnswer = new JSONObject(data);
        String city = JSONAnswer.optString("name"); // city name
        JSONArray nodoWeather = JSONAnswer.optJSONArray("weather");
        JSONObject itemWeather = nodoWeather.getJSONObject(0);
        String condition = itemWeather.optString("main"); // weather condition of the city
        JSONObject nodoMain = JSONAnswer.getJSONObject("main");
        String temperature = nodoMain.optString("temp"); // medium temperature (unit depends on the city)
        String pressure = nodoMain.optString("pressure"); // atmospheric pressure (hPa)
        String humidity = nodoMain.optString("humidity"); // humidity (%)
        JSONObject nodoWind = JSONAnswer.getJSONObject("wind");
        String windSpeed = nodoWind.optString("speed"); // wind speed (m/s)
        String windDir = nodoWind.optString("deg"); // wind direction (degrees)

        list.add(city);
        list.add(temperature);
        list.add(pressure);
        list.add(humidity);
        list.add(condition);
        list.add(windSpeed);
        list.add(windDir);
        return list;
    }
}
