package pl.cba.pklasa.trzepacz2;

/**
 * Created by Pioter on 2016-01-08.
 */

import android.annotation.SuppressLint;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;






public class HandleJSON2 {


    private List<GameD> wynik;
    public int ilosc=0;
    public int mojepkt=0;

    private String urlString = null;

    private float online_co = (float)1.0;

    public volatile boolean parsingComplete = true;
    public volatile int complete=0;
    public HandleJSON2(String url,float online_co){
        this.online_co = online_co;
        this.urlString = url;
    }
    public List<GameD> getWynik(){
        return wynik;
    }
    public int getIlosc() {return ilosc;}
    public int getMojepkt() {return mojepkt;}

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject main = new JSONObject(in);
            ilosc = main.getInt("Ilosc");
            mojepkt = main.getInt("Mojepkt");

            wynik = new ArrayList<GameD>();

            for(int i=0;i<ilosc;i++) {
                GameD dana = new GameD();

                dana.setId(main.getJSONArray("Id").getInt(i));
                dana.setId1(main.getJSONArray("Id1").getInt(i));
                dana.setId2(main.getJSONArray("Id2").getInt(i));
                dana.setKoniec_gry(main.getJSONArray("Koniec_gry").getInt(i));
                dana.setPoinformowany(main.getJSONArray("Poinformowany").getInt(i));
                dana.setRunda(main.getJSONArray("Runda").getInt(i));
                dana.setGracz(main.getJSONArray("Gracz").getInt(i));
                dana.setData(main.getJSONArray("Data").getString(i));
                dana.setNazwa1(main.getJSONArray("Nazwa1").getString(i));
                dana.setNazwa2(main.getJSONArray("Nazwa2").getString(i));

                dana.setRzut0_0((int)(online_co*main.getJSONArray("rzut0_0").getInt(i)));
                dana.setRzut1_0((int)(online_co*main.getJSONArray("rzut1_0").getInt(i)));
                dana.setRzut2_0((int)(online_co*main.getJSONArray("rzut2_0").getInt(i)));
                dana.setRzut3_0((int)(online_co*main.getJSONArray("rzut3_0").getInt(i)));
                dana.setRzut4_0((int)(online_co*main.getJSONArray("rzut4_0").getInt(i)));

                dana.setRzut0_1((int)(online_co*main.getJSONArray("rzut0_1").getInt(i)));
                dana.setRzut1_1((int)(online_co*main.getJSONArray("rzut1_1").getInt(i)));
                dana.setRzut2_1((int)(online_co*main.getJSONArray("rzut2_1").getInt(i)));
                dana.setRzut3_1((int)(online_co*main.getJSONArray("rzut3_1").getInt(i)));
                dana.setRzut4_1((int)(online_co*main.getJSONArray("rzut4_1").getInt(i)));

                dana.setRanga1((main.getJSONArray("Ranga1").getInt(i)));
                dana.setRanga2((main.getJSONArray("Ranga2").getInt(i)));

                wynik.add(dana);
            }

            parsingComplete = false;
            complete=1;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            complete=2;
            //e.printStackTrace();
        }

    }
    public void fetchJSON(final int idd){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(urlString);


                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("idd", idd+""));

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