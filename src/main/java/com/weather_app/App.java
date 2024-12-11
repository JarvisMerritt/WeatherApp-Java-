package com.weather_app;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;





/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String city;
        System.out.println(""" 
            ----------------------------------------------------
            Welcome! Please enter a city to see its weather!
                """);

        Scanner scanner = new Scanner(System.in);
        city = scanner.nextLine();
        scanner.close();
        getWeather(city);
        System.out.println("----------------------------------------------------");
    }

    private static void getWeather(String city){
        String apiKey = "f5351a3fcb9b136ae1f680a228f85a16";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=" + apiKey)).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
                JsonObject weatherObject = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
                JsonObject mainObject = jsonObject.getAsJsonObject("main");
                JsonObject sysObject = jsonObject.getAsJsonObject("sys");
                JsonObject windObject = jsonObject.getAsJsonObject("wind");
                
                String description = weatherObject.get("description").getAsString();
                String temp = mainObject.get("temp").getAsString();
                String humidity = mainObject.get("humidity").getAsString();
                String windSpeed = windObject.get("speed").getAsString();
                String country = sysObject.get("country").getAsString();

                System.out.println("\nDescription: " + description);
                System.out.println("Temperature: " + String.format("%.2f", toFahrenheit(Double.valueOf(temp))) + "°F");
                System.out.println("Humidity: "+humidity+"%");
                System.out.println("Wind Speed: "+windSpeed+" mph");
                System.out.println("Country: "+country);
            }
            else{
                System.out.println("sorry please try again");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Sorry something went wrong.");
        }
    }

    private static double toFahrenheit(double kelvin){
        return (kelvin - 273.15) * 9/5 + 32;
        
    }

}
