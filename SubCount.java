package YTFloatingSubCount;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.List;
import java.io.*;


class SubCount {


    // UCW3XollrczLP3rCotTuZdqg
    // api key AIzaSyAat1eWJKKkHGed_B0KWDDediDF4rqADTo
    //        String sUrl = "https://www.googleapis.com/youtube/v3/channels?part=statistics&id=&key=";

    private String subscriberCount = "";

    public SubCount(String channelId, String apiKey) throws Exception {
        String sUrl = "https://www.googleapis.com/youtube/v3/channels?part=statistics&id=" + channelId + "&key=" + apiKey;
        // grab json from url
        String jsonStr = jsonGetRequest(sUrl);
        subscriberCount = findSubCount(jsonStr);

    }

    public String getSubscriberCount() {
        return subscriberCount;
    }

    private String findSubCount(String json) {
        String result = null;

        int start = json.indexOf("subscriberCount") + 15;
        int colon = json.indexOf(":", start) + 1;
        int mid = json.indexOf('"', colon) + 1;
        int end = json.indexOf('"', mid);

        if (start > 0 && mid > 0 && end > 0) {
            result = json.substring(mid, end);
        } else {
            result = "Channel ID not recognised";
        }

        return result;

    }

    private String jsonGetRequest(String urlQueryString) {
        String json = null;
        try {
            URL url = new URL(urlQueryString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.connect();
            InputStream inStream = connection.getInputStream();
            json = streamToString(inStream); // input stream to string
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private static String streamToString(InputStream inputStream) {
        String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
        return text;
    }
}



