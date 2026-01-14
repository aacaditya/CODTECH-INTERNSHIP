import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

public class WeatherApp {

    public static void main(String[] args) {
        try {
            // API URL (Pune location)
            String apiUrl = "https://api.open-meteo.com/v1/forecast"
                    + "?latitude=18.52"
                    + "&longitude=73.85"
                    + "&current_weather=true";

            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create HTTP Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            // Send Request and get Response
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            // Parse JSON response
            JSONObject jsonObject = new JSONObject(response.body());
            JSONObject currentWeather = jsonObject.getJSONObject("current_weather");

            // Extract data
            double temperature = currentWeather.getDouble("temperature");
            double windSpeed = currentWeather.getDouble("windspeed");
            int weatherCode = currentWeather.getInt("weathercode");
            String time = currentWeather.getString("time");

            // Display structured output
            System.out.println("===== Current Weather Report =====");
            System.out.println("Location       : Pune, India");
            System.out.println("Temperature    : " + temperature + " °C");
            System.out.println("Wind Speed     : " + windSpeed + " km/h");
            System.out.println("Weather Code   : " + weatherCode);
            System.out.println("Observation Time: " + time);
            System.out.println("=================================");

        } catch (Exception e) {
            System.out.println("Error fetching weather data");
            e.printStackTrace();
        }
    }
}
