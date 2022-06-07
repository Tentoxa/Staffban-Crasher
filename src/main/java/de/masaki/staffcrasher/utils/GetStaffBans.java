package de.masaki.staffcrasher.utils;

import de.masaki.staffcrasher.Staffcrasher;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetStaffBans {
    public static String sendGETRequestGetString(final String urlString) throws IOException {
        final URL url = new URL(urlString);
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("User-Agent","Mod");
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setConnectTimeout(6000);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        final StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            sb.append((char)cp);
        }
        connection.disconnect();
        return sb.toString();
    }

    public static int get() {
        JsonObject response;
        try {
            response = new JsonParser().parse(sendGETRequestGetString("https://api.snipes.wtf/banstats")).getAsJsonObject();
        }
        catch (IOException e) {
            ChatUtils.sendMessage("Error: Unable to check staffbans.");
            e.printStackTrace();
            return 100000;
        }
        JsonObject record = response.get("record").getAsJsonObject().get("staff").getAsJsonObject();
        int staff = record.get("staff_latest_15m").getAsInt();
        Staffcrasher.current_staffbans = staff;
        return staff;
    }
}
