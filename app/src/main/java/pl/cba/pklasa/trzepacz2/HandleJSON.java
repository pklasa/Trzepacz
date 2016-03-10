package pl.cba.pklasa.trzepacz2;

/**
 * Created by Pioter on 2016-01-07.
*/

import android.annotation.SuppressLint;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class HandleJSON {

    private String wynik="Nie moze polaczyc";
    private int wynik2=0;

    private String urlString = null;

    public volatile boolean parsingComplete = true;
    public volatile int complete=0;
    public HandleJSON(String url){
        this.urlString = url;
    }
    public int getWynik(){
        return wynik2;
    }


    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject main = new JSONObject(in);

            //wynik = main.getString("twoje_id");
            wynik2 = main.getInt("twoje_id");
            parsingComplete = false;
            complete=1;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            complete=2;
            //e.printStackTrace();
        }

    }
    public void fetchJSON(final String nazwa, final String telefon,final int ranga){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(urlString);


                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("telefon", telefon));
                    nameValuePairs.add(new BasicNameValuePair("nazwa",nazwa));
                    nameValuePairs.add(new BasicNameValuePair("ranga", ranga+""));

                    httpPost.setEntity((new UrlEncodedFormEntity(nameValuePairs)));

                    HttpResponse httpResponse = httpclient.execute(httpPost);
                    InputStream stream = httpResponse.getEntity().getContent();


                    //InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    readAndParseJSON(data);
                    stream.close();

                } catch (Exception e) {
                    complete=2;
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}