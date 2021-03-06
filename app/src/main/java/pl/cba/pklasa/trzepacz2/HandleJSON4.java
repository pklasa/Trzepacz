package pl.cba.pklasa.trzepacz2;

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




public class HandleJSON4 {


    private GameD wynik;


    private String urlString = null;

    public volatile boolean parsingComplete = true;
    public volatile int complete=0;
    private float online_co = (float)1.0;
    public HandleJSON4(String url,float online_co){
        this.online_co = online_co;
        this.urlString = url;
    }
    public GameD getWynik(){
        return wynik;
    }

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject main = new JSONObject(in);

            wynik = new GameD();

            wynik.setId(main.getInt("Id"));
            wynik.setId1(main.getInt("Id1"));
            wynik.setId2(main.getInt("Id2"));
            wynik.setKoniec_gry(main.getInt("Koniec_gry"));
            wynik.setPoinformowany(main.getInt("Poinformowany"));
            wynik.setRunda(main.getInt("Runda"));
            wynik.setGracz(main.getInt("Gracz"));
            wynik.setData(main.getString("Data"));
            wynik.setNazwa1(main.getString("Nazwa1"));
            wynik.setNazwa2(main.getString("Nazwa2"));

            wynik.setRzut0_0((int)(online_co*main.getInt("rzut0_0")));
            wynik.setRzut1_0((int)(online_co*main.getInt("rzut1_0")));
            wynik.setRzut2_0((int)(online_co*main.getInt("rzut2_0")));
            wynik.setRzut3_0((int)(online_co*main.getInt("rzut3_0")));
            wynik.setRzut4_0((int)(online_co*main.getInt("rzut4_0")));

            wynik.setRzut0_1((int)(online_co*main.getInt("rzut0_1")));
            wynik.setRzut1_1((int)(online_co*main.getInt("rzut1_1")));
            wynik.setRzut2_1((int)(online_co*main.getInt("rzut2_1")));
            wynik.setRzut3_1((int)(online_co*main.getInt("rzut3_1")));
            wynik.setRzut4_1((int)(online_co*main.getInt("rzut4_1")));

            parsingComplete = false;
            complete=1;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            complete=2;
            //e.printStackTrace();
        }

    }
    public void fetchJSON(final int id,final int id1,final int id2,final int moje_id,final int gracz,final int runda,final int rzut1,final int rzut2){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(urlString);


                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("id", id+""));
                    nameValuePairs.add(new BasicNameValuePair("id1", id1+""));
                    nameValuePairs.add(new BasicNameValuePair("id2", id2+""));
                    nameValuePairs.add(new BasicNameValuePair("moje_id", moje_id+""));
                    nameValuePairs.add(new BasicNameValuePair("gracz", gracz+""));
                    nameValuePairs.add(new BasicNameValuePair("runda", runda+""));
                    nameValuePairs.add(new BasicNameValuePair("rzut1", rzut1+""));
                    nameValuePairs.add(new BasicNameValuePair("rzut2", rzut2+""));

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