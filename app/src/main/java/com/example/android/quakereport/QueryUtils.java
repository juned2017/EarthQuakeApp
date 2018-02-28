package com.example.android.quakereport;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by junedahmed on 2/19/18.
 */

public final class QueryUtils {

    /**
     * Sample JSON response for a USGS query
     */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {

    }

    //Query the USGS dataset and return a list of Earthquake objects

    public static List<Earthquake> fetchEarthquakeDate(String requestUrl) {
        //create URL object
        URL url = createUrl(requestUrl);

        //perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);

        }
        //Extract relevant fields from the JSON response and create a list of Earthquake
        List<Earthquake> earthquakes = extractFeatureFromJson(jsonResponse);

        return earthquakes;


    }
    //Returns new URL object from the given string url

    private static URL createUrl(String stringurl) {
        URL url = null;
        try {
            url = new URL(stringurl);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Erro with creating Url ", e);

        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = " ";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results." + httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);


        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line=reader.readLine();
            }


        }

        //return string representaiton of the builder
        return stringBuilder.toString();
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static List<Earthquake> extractFeatureFromJson(String earthquakeJSON) {

        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;

        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the json response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            //parses the whole geo resoponse that we got above, this is the root json
            //creating a jsonobject from the json response string
            JSONObject rootJsonResponse = new JSONObject(earthquakeJSON);
            //within the root jason is the features key
            //which represent a list of features(or earthquakes)
            JSONArray earthQuakeArray = rootJsonResponse.optJSONArray("features");

            //for each earthquake in the qarthquakeArray, create an Earthquake object

            for (int i = 0; i <= earthQuakeArray.length(); i++) {
                //get a single earthquake at position i within the lkist of earthqakes
                JSONObject currentEarthQuake = earthQuakeArray.getJSONObject(i);
                //once we have the currentEarthquake object, we can extract out the "properties" object
                JSONObject properties = currentEarthQuake.getJSONObject("properties");

                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String urlLocation = properties.getString("url");


                Earthquake earthquake = new Earthquake(magnitude, location, time, urlLocation);

                //add the new Earthquake ot the list of earthquakes
                earthquakes.add(earthquake);


            }


            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}
