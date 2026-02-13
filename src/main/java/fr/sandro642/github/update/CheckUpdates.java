package fr.sandro642.github.update;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * CheckUpdates is a class that retrieves the latest version of the software.
 * It contains a field to store the latest version and can be extended to include methods for fetching
 */

public class CheckUpdates {

    /**
     * Fetches the latest version from the GitHub API.
     * It sends a GET request to the tags endpoint of the OreRatio repository
     * and retrieves the name of the first tag, which represents the latest version.
     */
    public String fetchVersion() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/repos/Sandro642/OreRatio/tags"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonArray data = gson.fromJson(response.body(), JsonArray.class);

            if (data != null && !data.isEmpty()) {
                JsonObject object = data.get(0).getAsJsonObject();
                return object.get("name").getAsString();
            } else {
                return "No tags found";
            }

        } catch (Exception e) {
            return "Error";
        }
    }

    /**
     * Compares the fetched version with the current version.
     * If they are different, it prints a message indicating that a new version is available.
     */
    public void isLatestVersion() {
        String OreRatio = "\u001B[94m[OreRatio] \u001B[0m";

        String fetchedVersion = fetchVersion();
        if (fetchedVersion.equals("Error") || fetchedVersion.equals("No tags found")) {
            System.out.println(OreRatio + "Could not fetch the latest version.");
        } else if (!fetchedVersion.equals(Version.VERSION)) {
            System.out.println(OreRatio + "A new version is available: " + fetchedVersion);
        }
    }

}