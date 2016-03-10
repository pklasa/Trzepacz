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



public class HandleJSON5 {


    private List<GameE> wynik;
    public int ilosc=0;
    public int moja_pos=0;
    public int moje_pkt=0;

    private String urlString = null;

    public volatile boolean parsingComplete = true;
    public volatile int complete=0;
    public HandleJSON5(String url){
        this.urlString = url;
    }
    public List<GameE> getWynik(){
        return wynik;
    }

    public int getIlosc() {return ilosc;}
    public int getPkt() {return moje_pkt;}
    public int getPos() {return moja_pos;}

    @SuppressLint("NewApi")
    public void readAndParseJSON(String in) {
        try {
            JSONObject main = new JSONObject(in);
            ilosc = main.getInt("Ilosc");
            moja_pos = main.getInt("Moja_pos");
            moje_pkt = main.getInt("Moje_pkt");

            wynik = new ArrayList<GameE>();

            for(int i=0;i<ilosc;i++) {
                GameE dana = new GameE();

                dana.setId(main.getJSONArray("Id").getInt(i));
                dana.setIlosc_gier(main.getJSONArray("Ilosc_gier").getInt(i));
                dana.setIlosc_wygranych(main.getJSONArray("Ilosc_wygranych").getInt(i));
                dana.setPozycja(main.getJSONArray("Pozycja").getInt(i));
                dana.setPunkty(main.getJSONArray("Punkty").getInt(i));

                dana.setData(main.getJSONArray("Data2").getString(i));
                dana.setTelefon(main.getJSONArray("Telefon").getString(i));
                dana.setNazwa(main.getJSONArray("Nazwa").getString(i));

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
                    nameValuePairs.add(new BasicNameValuePair("moje_id", idd+""));

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