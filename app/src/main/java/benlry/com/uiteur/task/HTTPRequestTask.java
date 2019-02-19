package benlry.com.uiteur.task;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.CookieManager;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import benlry.com.uiteur.services.MediaPlayerService;

public class HTTPRequestTask extends AsyncTask<URL, Void, Long> {

    private MediaPlayerService myService = null;
    private Document myDocument = null;

    public HTTPRequestTask(MediaPlayerService service) {
        this.myService = service;
    }

    protected Long doInBackground(URL... urls) {

        URL serviceURL = urls[0];

        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) serviceURL.openConnection();

            Log.d("connection", "Connection OK");
        } catch (IOException ex) {
            Log.e("connection", ex.getMessage());
        }

        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException ex) {
            Log.e("error", ex.getMessage());
        }

        connection.setDoOutput(true);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(5000);

        CookieManager cookieManager = CookieManager.getInstance();
        String cookieUiteur = cookieManager.getCookie("http://uiteur.struct-it.fr/");
        if (cookieUiteur != null) {
            connection.setRequestProperty("Cookie", cookieUiteur);
        }

        try {
            connection.connect();
        } catch (IOException ex) {
            Log.e("error", ex.getMessage());
        }

        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Map<String, List<String>> headerFields = connection.getHeaderFields();
                List<String> myCookie = headerFields.get("Set-Cookie");

                if (myCookie != null) {
                    for (String cookie : myCookie) {
                        cookieManager.setCookie("http://uiteur.struct-it.fr/", cookie);
                    }
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String inputLine;

                while ((inputLine = reader.readLine()) != null) {
                    buffer.append(inputLine);
                }

                reader.close();

                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
                InputSource inputSource = new InputSource(new StringReader(buffer.toString()));
                this.myDocument = documentBuilder.parse(inputSource);
            }
        } catch (Exception ex) {
            Log.e("error", ex.getMessage());
        }

        return (long) 0;
    }

    protected void onPostExecute(Long result) {
        this.myService.playlist(this.myDocument);
    }
}
