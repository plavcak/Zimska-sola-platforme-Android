package um.feri.uporabniskivmesniki.rest;

import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

import um.feri.uporabniskivmesniki.model.Client;

public class RestClient {

    private static final String TAG = "RestClient";

    public void sendAsync(Client client, final OnRestResponseListener listener) {

        AsyncTask<Client, Void, Boolean> task = new AsyncTask<Client, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Client... params) {
                return send(params[0]);
            }

            @Override
            protected void onPostExecute(Boolean successful) {

                if(listener != null) {
                    if(successful) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure();
                    }
                }

            }
        };

        task.execute(client);
    }

    public void receiveAsync(final OnRestResponseListener listener) {

        AsyncTask<Void, Void, List<Client>> task = new AsyncTask<Void, Void, List<Client>>() {

            @Override
            protected List<Client> doInBackground(Void... params) {
                return receiveAll();
            }

            @Override
            protected void onPostExecute(List<Client> clients) {
                if(listener != null) {
                    if(clients == null || clients.isEmpty()) {
                        listener.onFailure();
                    } else {
                        listener.onSuccess();
                    }
                }
            }
        };

        task.execute();
    }

    public boolean send(Client client) {

        try {
            HttpURLConnection connection = new RestFactory().getPostRequest();

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(connection.getOutputStream(), client);

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i(TAG, client.toString());
                return true;
            }

        } catch (JsonProcessingException e) {
            Log.e(TAG, "Cannot process Client object!");
        } catch (IOException e) {
            Log.e(TAG, "Cannot send object! REST exception.");
        }

        return false;
    }

    public List<Client> receiveAll() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpURLConnection connection = new RestFactory().getListRequest();

            Log.d(TAG, "receiveAll() message=" + connection.getResponseMessage());

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Client.class);
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                List <Client> result = mapper.readValue(inputStream, type);


                if(result != null) {
                    for(Client c : result) {
                        Log.i(TAG, c.toString());
                    }
                }

                return result;
            }

        } catch (JsonProcessingException e) {
            Log.e(TAG, "Cannot process Client object!");
        } catch (IOException e) {
            Log.e(TAG, "REST exception.");
        }
        return null;
    }

    public void gcmRegister(String regId) {

        try {

            HttpURLConnection connection = new RestFactory().getGcmRegister(regId);

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "Registration succesfull.");
            } else {
                Log.d(TAG, "gcmRegister() " + connection.getResponseMessage());

            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static interface OnRestResponseListener {
        void onSuccess();
        void onFailure();
    }

}
