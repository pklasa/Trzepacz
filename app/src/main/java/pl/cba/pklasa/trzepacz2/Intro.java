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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Intro extends Activity {


    public SharedPreferences preferences;
    public  int liczniki=0;
    SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        preferences = this.getSharedPreferences("user_data", Activity.MODE_PRIVATE);

        ((TextView) findViewById(R.id.textView12)).setText(getString(R.string.intr_0_01));
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(R.drawable.w1);

     final int[] teksty = {
             R.string.intr_0_02,
             R.string.intr_0_03,
             R.string.intr_0_04,
             R.string.intr_0_05,
        };

     final int[] obrazki = {
                R.drawable.w2,
                R.drawable.w3,
                R.drawable.q1,
                R.drawable.w9,
        };

      liczniki=0;

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.intro_tlo);
        rl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (liczniki > 3) {
                    preferencesEditor = preferences.edit();
                    preferencesEditor.putInt("intro_1", 1);
                    preferencesEditor.commit();
                    finish();
                } else {
                    ((TextView) findViewById(R.id.textView12)).setText(getString(teksty[liczniki]));
                    ((ImageView) findViewById(R.id.imageView4)).setImageResource(obrazki[liczniki]);
                }
                liczniki++;

            }
        });

    }


}

