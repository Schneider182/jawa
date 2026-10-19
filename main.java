
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class Main {
    public static void main(String[] args) {

        String apiKey = "84d62e9777e4e706e3771c9a4561ada0";

        String url =
            "https://api.openweathermap.org/data/2.5/weather"
            + "?q=Jakarta"
            + "&appid=" + apiKey
            + "&units=metric"
            + "&lang=id";

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

            HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("HTTP Status: "
                + response.statusCode());

JsonObject data = JsonParser
    .parseString(response.body())
    .getAsJsonObject();

String kota = data.get("name").getAsString();

JsonObject main = data.getAsJsonObject("main");

double suhu = main.get("temp").getAsDouble();
double terasa = main.get("feels_like").getAsDouble();
int kelembapan = main.get("humidity").getAsInt();

JsonArray weather = data.getAsJsonArray("weather");

String kondisi = weather
    .get(0)
    .getAsJsonObject()
    .get("description")
    .getAsString();

System.out.println("=== CUACA HARI INI ===");
System.out.println("Kota       : " + kota);
System.out.println("Suhu       : " + suhu + " °C");
System.out.println("Terasa     : " + terasa + " °C");
System.out.println("Kelembapan : " + kelembapan + "%");
System.out.println("Kondisi    : " + kondisi);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
3-