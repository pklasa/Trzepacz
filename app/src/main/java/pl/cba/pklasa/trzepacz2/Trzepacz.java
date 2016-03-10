package pl.cba.pklasa.trzepacz2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


public class Trzepacz extends Activity {


    int mode=0;

    public SharedPreferences preferences;
    public long all_dist=0;
    public long record=0;
    public  boolean wibracje = false;
    public  ListView mGridView;
    public int szerokosc=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trzepacz);

        preferences = this.getSharedPreferences("user_data", Activity.MODE_PRIVATE);
        //wibracje = preferences.getBoolean("Wibracje",false);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        szerokosc = metrics.widthPixels;

        Button b_op0 = (Button) findViewById(R.id.opcja_0);
        b_op0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getBaseContext(), Gra0.class);
                startActivity(intent);

            }
        });
        Button b_op1 = (Button) findViewById(R.id.opcja_1);
        b_op1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
               show_multiplayer();
            }
        });
        Button b_op2 = (Button) findViewById(R.id.opcja_2);
        b_op2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                show_osiagniecia();
            }
        });
        Button b_op3 = (Button) findViewById(R.id.opcja_3);
        b_op3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getBaseContext(), AppPreferences.class);
                startActivityForResult(intent,1337);
            }
        });


        Button b_op4 = (Button) findViewById(R.id.opcja_4);
        b_op4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getBaseContext(), Gra1.class);
                startActivityForResult(intent,1337);
            }
        });
        Button b_op5 = (Button) findViewById(R.id.opcja_5);
        b_op5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

            }
        });
        Button b_op6 = (Button) findViewById(R.id.opcja_6);
        b_op6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(getBaseContext(), Online.class);
                startActivityForResult(intent,1337);
            }
        });

        List<String> lista = new LinkedList<String>();
        lista.add("Domyślny");lista.add("Kamień");
        lista.add("Brąz");lista.add("Srebro");
        lista.add("Złoto");lista.add("Diament");


       mGridView =(ListView) findViewById(R.id.gridview);
       mGridView.setDivider(null);
       mGridView.setAdapter(new GripAdapter(this,lista,record,all_dist,szerokosc));


        show_menu();

       int intro = preferences.getInt("intro_1",0);
        if(intro==0) {
            Intent intent = new Intent(getBaseContext(), Intro.class);
            startActivityForResult(intent, 1337);
        }

    }


    @Override
    protected void onStart(){
        super.onStart();

    }

    public void show_menu(){
        mode=0;


        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setVisibility(View.VISIBLE);

        Button b_op0 = (Button) findViewById(R.id.opcja_0);
        b_op0.setVisibility(View.VISIBLE);
        Button b_op1 = (Button) findViewById(R.id.opcja_1);
        b_op1.setVisibility(View.VISIBLE);
        Button b_op2 = (Button) findViewById(R.id.opcja_2);
        b_op2.setVisibility(View.VISIBLE);
        Button b_op3 = (Button) findViewById(R.id.opcja_3);
        b_op3.setVisibility(View.VISIBLE);

        ListView gridview = (ListView) findViewById(R.id.gridview);
        gridview.setVisibility(View.GONE);
       TableLayout tabelka = (TableLayout) findViewById(R.id.tabelka);
        tabelka.setVisibility(View.GONE);

        Button b_op4 = (Button) findViewById(R.id.opcja_4);
        b_op4.setVisibility(View.GONE);
        Button b_op5 = (Button) findViewById(R.id.opcja_5);
        b_op5.setVisibility(View.GONE);
        Button b_op6 = (Button) findViewById(R.id.opcja_6);
        b_op6.setVisibility(View.GONE);

    }



    public void show_osiagniecia(){
        mode=1;


         ImageView logo = (ImageView) findViewById(R.id.logo);
         logo.setVisibility(View.VISIBLE);
        record = preferences.getLong("Record",0);
        all_dist = preferences.getLong("All_dist",0);
        ((TextView) findViewById(R.id.tab_2)).setText("  "+(int)(record/10000)+"m "+((record/10)%1000)+"mm");





        ((TextView) findViewById(R.id.tab_4)).setText("  "+(long)(all_dist/10000)+"m "+((all_dist/10)%1000)+"mm");
        List<String> lista = new LinkedList<String>();
        lista.add(getString(R.string.o_rybka__));lista.add(getString(R.string.o_wielory));
        lista.add(getString(R.string.o_kaszalo));lista.add(getString(R.string.o_orka___));
        lista.add(getString(R.string.o_narwal_));lista.add(getString(R.string.o_walen__));
        mGridView.setAdapter(new GripAdapter(this,lista,record,all_dist,szerokosc));


        Button b_op0 = (Button) findViewById(R.id.opcja_0);
        b_op0.setVisibility(View.GONE);
        Button b_op1 = (Button) findViewById(R.id.opcja_1);
        b_op1.setVisibility(View.GONE);
        Button b_op2 = (Button) findViewById(R.id.opcja_2);
        b_op2.setVisibility(View.GONE);
        Button b_op3 = (Button) findViewById(R.id.opcja_3);
        b_op3.setVisibility(View.GONE);

        ListView gridview = (ListView) findViewById(R.id.gridview);
        gridview.setVisibility(View.VISIBLE);
        TableLayout tabelka = (TableLayout) findViewById(R.id.tabelka);
        tabelka.setVisibility(View.VISIBLE);

    }


    public void show_multiplayer(){
        mode=1;

        Button b_op0 = (Button) findViewById(R.id.opcja_0);
        b_op0.setVisibility(View.GONE);
        Button b_op1 = (Button) findViewById(R.id.opcja_1);
        b_op1.setVisibility(View.GONE);
        Button b_op2 = (Button) findViewById(R.id.opcja_2);
        b_op2.setVisibility(View.GONE);
        Button b_op3 = (Button) findViewById(R.id.opcja_3);
        b_op3.setVisibility(View.GONE);

        Button b_op4 = (Button) findViewById(R.id.opcja_4);
        b_op4.setVisibility(View.VISIBLE);
        Button b_op5 = (Button) findViewById(R.id.opcja_5);
        b_op5.setVisibility(View.GONE); //nie mamay dla 3 graczy jeszcze
        Button b_op6 = (Button) findViewById(R.id.opcja_6);
        b_op6.setVisibility(View.VISIBLE);
    }


    @Override
    public void onBackPressed() {
       if(mode>0){ show_menu();}else{
           finish();
       }

    }

}

