package com.ababilweb.ababilchat.networking;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.transform.sax.SAXResult;

public class ServerApi extends AsyncTask<String,Integer,String> {
    String serverAddress,data;
    ServerApiListener listener;
    String TAG= "ServerApi";

    public ServerApi(String url, String data,ServerApiListener listener){
        this.serverAddress=url;
        this.data=data;
        this.listener = listener;
        execute();

    }


    @Override
    protected String doInBackground(String... strings) {
        //Log.d(TAG, "doInBackground: "+strings[0]+"\n"+strings[1]);
        try {
            //Todo : preparing connection
                URL url = new URL (serverAddress);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                //con.setRequestMethod("POST");
//                if (basicAuth!=null)con.setRequestProperty ("Authorization", "key="+ basicAuth);
                OutputStream outputStream = null;
                BufferedWriter bufferedWriter = null;

                    con.setRequestProperty("Content-Type", "application/json; utf-8");
                    con.setRequestProperty("Accept", "application/json");
                    con.setRequestMethod("POST");
                    con.setInstanceFollowRedirects(true);
                    con.setDoOutput(true);
                    outputStream = con.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                  //Todo : Writing post request json data
                Log.d(TAG, "doInBackground: "+url+"----Data ->"+data);
                //---------- todo : Now Read
                int statusCode = con.getResponseCode();
                Log.d(TAG, "doInBackground: "+statusCode);
                con.connect();
                StringBuilder sb = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String result;
                while ((result = bufferedReader.readLine()) != null) {
                    sb.append(result + "\n");
                }
                System.out.println("Response : \n"+sb.toString());
            Log.d(TAG, "doInBackground: Response : "+sb);

                if(outputStream != null) outputStream.close();
                if(bufferedWriter!=null)  bufferedWriter.close();
                if(bufferedReader!=null)  bufferedReader.close();
                if(con!=null) con.disconnect();
                listener.onSuccess(sb.toString());
                return sb.toString();

        }catch (Exception e){
            Log.e(TAG, "doInBackground: Exception --> ",e.getCause());
           listener.onFailure("Server Failed");

        }
        return null;
    }

    public interface ServerApiListener{
        void onSuccess(String response);
        void onFailure(String errorMessage);
    }



}
