package pl.cba.pklasa.trzepacz2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pioter on 2016-01-07.
 */


public class Online extends Activity {


    public SharedPreferences preferences;
    public int idd=0;
    private String url1 = "http://pklasa.cba.pl/Trzepacz/android_trzepacz.php";
    private String url2 = "http://pklasa.cba.pl/Trzepacz/android2_trzepacz.php";
    private String url3 = "http://pklasa.cba.pl/Trzepacz/android3_trzepacz.php";
    private String moja_nazwa="beznazwy";
    SharedPreferences sp2;
    SharedPreferences.Editor preferencesEditor;
    int szerokosc=0;
    int mojepkt = 0;
    public  int ranga = 0;
    //float online_co = (float)1.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);

        preferences = this.getSharedPreferences("user_data", Activity.MODE_PRIVATE);
        idd = preferences.getInt("Id", 0);
        ranga = preferences.getInt("Wybor",0);
        preferencesEditor = preferences.edit();

        sp2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        moja_nazwa = sp2.getString("username", "no name");

        //wymiary telefonu:
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        szerokosc = metrics.widthPixels;


        Button refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                odswiez();
            }
        });

        Button rank = (Button) findViewById(R.id.rank);
        rank.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getBaseContext(), Ranking.class);
                startActivityForResult(intent,1337);
            }
        });

        int intro = preferences.getInt("intro_2",0);
        if(intro==0) {
            Intent intent = new Intent(getBaseContext(), IntroOnline.class);
            startActivityForResult(intent, 1337);
        }

    }

        @Override
        protected void onResume (){
            super.onResume();
            odswiez();
        }


    private void odswiez(){
        if (isNetworkAvailable()) {
            if (idd == 0) {
                String finalUrl = url1;
                HandleJSON obj = new HandleJSON(finalUrl);
                obj.fetchJSON(moja_nazwa, getDeviceName(), 0);
                while (obj.complete == 0) {
                }
                idd = obj.getWynik();

                preferencesEditor.putInt("Id", idd);
                preferencesEditor.commit();
            }


            //pobieramy dane o moich grach- bo juz mamy fajne id ;)

            String finalUrl2 = url2;
            HandleJSON2 obj2 = new HandleJSON2(finalUrl2, 1);
            obj2.fetchJSON(idd);
            long od_czas = 0;
            long teraz_czas = 0;
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            teraz_czas = (today.toMillis(true)) / 1000;
            od_czas = (today.toMillis(true)) / 1000;
            while (obj2.complete == 0 && (teraz_czas - od_czas) < 6) {
                today = new Time(Time.getCurrentTimezone());
                today.setToNow();
                teraz_czas = (today.toMillis(true)) / 1000;
            }
            int ilosc = 0;


            List<GameD> wynikii = new ArrayList<GameD>();
            if (obj2.complete == 1) {
                ilosc = obj2.getIlosc();
                wynikii = obj2.getWynik();
                mojepkt = obj2.getMojepkt();
            }


            if (moja_nazwa == "no name") {
                ((TextView) findViewById(R.id.textView2)).setText(getString(R.string.wpro_imie));
            } else {
                ((TextView) findViewById(R.id.textView2)).setText(moja_nazwa+" : "+(500+mojepkt)+"pkt");
            }
            ListView lista_online = (ListView) findViewById(R.id.listView);
            lista_online.setAdapter(new OnlineAdapter(getApplicationContext(), wynikii, idd, 1));


            Button b_online = (Button) findViewById(R.id.button_online);

            if (ilosc < 15) {
                b_online.setText(getString(R.string.nowa_gra_));

                b_online.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {

                        // get prompts.xml view
                        LayoutInflater layoutInflater = LayoutInflater.from(Online.this);
                        View promptView = layoutInflater.inflate(R.layout.wpisz, null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Online.this);
                        alertDialogBuilder.setView(promptView);

                        final EditText editText = (EditText) promptView.findViewById(R.id.gracz_id);


                        // setup a dialog window
                        alertDialogBuilder.setCancelable(false)
                                .setPositiveButton(getString(R.string.graj_____), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String finalUrl3 = url3;
                                        HandleJSON3 obj3 = new HandleJSON3(finalUrl3, 1);
                                        int liczba=-1;
                                        try{
                                            liczba = Integer.parseInt( editText.getText().toString());
                                        }catch(Exception e){
                                            liczba=-1;
                                        }
                                        obj3.fetchJSON(idd, moja_nazwa,liczba,ranga);
                                        long od_czas = 0;
                                        long teraz_czas = 0;
                                        Time today = new Time(Time.getCurrentTimezone());
                                        today.setToNow();
                                        teraz_czas = (today.toMillis(true)) / 1000;
                                        od_czas = (today.toMillis(true)) / 1000;
                                        while (obj3.complete == 0 && (teraz_czas - od_czas) < 6) {
                                            today = new Time(Time.getCurrentTimezone());
                                            today.setToNow();
                                            teraz_czas = (today.toMillis(true)) / 1000;
                                        }
                                        GameD wynik = obj3.getWynik();

                                        Intent intent = new Intent(getBaseContext(), GraOnline.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("id", wynik.getId());
                                        bundle.putInt("id1", wynik.getId1());
                                        bundle.putInt("id2", wynik.getId2());
                                        bundle.putInt("gracz", wynik.getGracz());
                                        bundle.putInt("runda", wynik.getRunda());
                                        bundle.putInt("moje_id", idd);


                                        intent.putExtras(bundle);
                                        startActivityForResult(intent, 1337);
                                    }
                                })
                                .setNegativeButton(getString(R.string.anuluj___),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.cancel();
                                            }
                                        });

                        // create an alert dialog
                        AlertDialog alert = alertDialogBuilder.create();
                        alert.show();

                    }
                });

            } else {
                b_online.setText(getString(R.string.aby_rozpo));

                b_online.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                    }
                });


            }


        } else {

            ((TextView) findViewById(R.id.textView2)).setText(getString(R.string.wlacz_dan));
        }
    }






    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

}

