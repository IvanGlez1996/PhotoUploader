package com.example.panorama.model;

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
    private String datosRecuperados;
    private boolean error;
    private Mediator appMediador;

    public WeatherAPIConnection() {
        datosRecuperados = "";
        error = false;
        appMediador = Mediator.getInstance();
    }

    // Se llama después del método onPreExecute
    protected Void doInBackground(String... urls) {
        BufferedReader reader = null;
        // Se envían los datos
        try {
            // Se recuperan los datos del momento actual
            URL url = new URL(urls[0]);
            datosRecuperados = recuperarDatos(url);

            ArrayList<String> data = getActual(datosRecuperados);


            Bundle extras = new Bundle();
            extras.putSerializable(Mediator.CLAVE_INFORMACION, data);

            Mediator.getInstance().sendBroadcast(Mediator.AVISO_NUEVA_INFORMACION, extras);


            // Se envía al presentador la información recopilada

        } catch (Exception ex) {
            Mediator.getInstance().sendBroadcast(Mediator.AVISO_NUEVA_INFORMACION, null);
        }
        return null;
    }

    private String recuperarDatos(URL url) {
        BufferedReader reader = null;
        // Se envían los datos
        try {
            // Se envía petición POST al servidor web RESTful
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.connect();
            // Se obtiene la respuesta
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


    private ArrayList<String> getActual(String datosRecuperados) throws JSONException {
        // Crea un nuevo objeto JSON a partir de los datos recuperados desde el servidor
        // Se sigue la información presentada en: https://openweathermap.org/current#current_JSON
        ArrayList<String> data = new ArrayList<>();
        JSONObject respuestaJSON = new JSONObject(datosRecuperados);
        String ciudad = respuestaJSON.optString("name"); // nombre de la ciudad
        JSONArray nodoWeather = (JSONArray) respuestaJSON.optJSONArray("weather");
        JSONObject itemWeather = (JSONObject) nodoWeather.getJSONObject(0);
        String condicion = itemWeather.optString("main"); // tipo de tiempo de la ciudad
        JSONObject nodoMain = respuestaJSON.getJSONObject("main");
        String temperatura = nodoMain.optString("temp"); // temperatura media (según unidad) de la ciudad
        String presion = nodoMain.optString("pressure"); // presión atmosférica (en hPa) de la ciudad
        String humedad = nodoMain.optString("humidity"); // humedad (en %) de la ciudad
        JSONObject nodoWind = respuestaJSON.getJSONObject("wind");
        String velViento = nodoWind.optString("speed"); // velocidad del viento (en m/s) de la ciudad
        String dirViento = nodoWind.optString("deg"); // dirección del viento (en grados) de la ciudad

        data.add(ciudad);
        data.add(temperatura);
        data.add(presion);
        data.add(humedad);
        data.add(condicion);
        data.add(velViento);
        data.add(dirViento);
        return data;
    }
}
