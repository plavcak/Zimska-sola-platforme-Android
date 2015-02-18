package um.feri.uporabniskivmesniki.rest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestFactory {

    private static final String BASE_URL = "http://164.8.161.37:8080/zimska-sola-android-rest/resources/";

    public HttpURLConnection getListRequest() throws IOException {
        HttpURLConnection connection = getGetConnection("client");

        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    public HttpURLConnection getPostRequest() throws IOException {
        HttpURLConnection connection = getPostConnection("client");
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    public HttpURLConnection getClientSendRequest() throws IOException {
        java.net.URL url = getUrl("http://164.8.161.37:8080/zimska-sola-android-rest/resources/client");
        java.net.HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    /**
     * GCM register device
     * @param regId
     * @return
     * @throws IOException
     */
    public HttpURLConnection getGcmRegister(String regId) throws IOException {
        HttpURLConnection connection = getGetConnection("gcm/register?regId=" + regId);
        return connection;
    }

    /**
     * GCM unregister device
     * @param regId
     * @return
     * @throws IOException
     */
    public HttpURLConnection getGcmUnregister(String regId) throws IOException {
        HttpURLConnection connection = getGetConnection("gcm/unregister?regId=" + regId);
        return connection;
    }

    private URL getUrl(String path) throws MalformedURLException {
        return new URL(BASE_URL + path);
    }

    private HttpURLConnection getGetConnection(String path) throws IOException {
        URL url = getUrl(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        return  connection;
    }

    private HttpURLConnection getPostConnection(String path) throws IOException {
        URL url = getUrl(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        return  connection;
    }

}
