package pl.cba.pklasa.trzepacz2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Ranking extends Activity {


    public SharedPreferences preferences;

    private String url5 = "http://pklasa.cba.pl/Trzepacz/android5_trzepacz.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


        preferences = this.getSharedPreferences("user_data", Activity.MODE_PRIVATE);
        int idd = preferences.getInt("Id", 0);
        List<GameE> wynikii = new ArrayList<GameE>();

        String finalUrl5 = url5;
        HandleJSON5 obj5 = new HandleJSON5(finalUrl5);
        obj5.fetchJSON(idd);
        long od_czas = 0;
        long teraz_czas = 0;
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        teraz_czas = (today.toMillis(true)) / 1000;
        od_czas = (today.toMillis(true)) / 1000;
        while (obj5.complete == 0 && (teraz_czas - od_czas) < 6) {
            today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            teraz_czas = (today.toMillis(true)) / 1000;
        }
        int ilosc = 0;

        if (obj5.complete == 1) {
            ilosc = obj5.getIlosc();
            wynikii = obj5.getWynik();
        }

       // TextView napis = (TextView) findViewById(R.id.textView3);
       // napis.setText("asda"+ilosc+obj5.getPkt()+wynikii.get(2).getTelefon());
        ListView lista_rank = (ListView) findViewById(R.id.listView2);
        lista_rank.setAdapter(new RankingAdapter(getApplicationContext(),wynikii,idd));

    }




    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

