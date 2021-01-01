package com.example.airquality;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FatchData extends AsyncTask<Void,Void,Void> {
    String data = "";
    public static  String value;
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("https://api.thingspeak.com/channels/1124845/fields/1.json?api_key=54C6QJNZ7EI9I3J2&results=2");
            HttpURLConnection httpurlconnection = (HttpURLConnection) url.openConnection() ;
            InputStream inputStream = httpurlconnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "" ;
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line ;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            JSONObject jo = new JSONObject(data);
            JSONArray sys  = jo.getJSONArray("feeds");

            this.value= sys.getJSONObject(1).getString("field1");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        float val = Float.parseFloat(value);
        val = val*10 ;
        MainActivity.gauge.setValue((int)val);
        MainActivity.value_ppm.setText(Float.toString(val));
        MainActivity.click.setVisibility(View.VISIBLE);

        set_values(this.value);

    }



    public void  set_values(String Value){
        float val = Float.parseFloat(Value) * 10 ;
        if (val < 1000){
            MainActivity.gauge.setEndValue(3000);
            MainActivity.txt_end.setText("3000");
            MainActivity.condi.setText("Fresh Air");
            MainActivity.condi.setTextColor(Color.GREEN);
        }
        else if (val < 2000){
            MainActivity.gauge.setEndValue(3000);
            MainActivity.txt_end.setText("3000");
            MainActivity.condi.setText("Modarte Air");
            MainActivity.condi.setTextColor(Color.YELLOW);
        }
        else {
            if (val  >= 2000){
                MainActivity.gauge.setEndValue(7000);
                MainActivity.txt_end.setText("7000");
            }
            else {
                MainActivity.gauge.setEndValue(3000);
                MainActivity.txt_end.setText("3000");
            }
            MainActivity.condi.setText("Harmful Air");
            MainActivity.condi.setTextColor(Color.RED);
        }
    }
}
