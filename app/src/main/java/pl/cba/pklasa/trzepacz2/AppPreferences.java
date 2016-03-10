package pl.cba.pklasa.trzepacz2;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.TextView;

import java.util.Locale;


public class AppPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int szerokosc = metrics.widthPixels;

        SharedPreferences preferences = this.getSharedPreferences("user_data", Activity.MODE_PRIVATE);
        int idd  = preferences.getInt("Id",0);
        Preference pref3 = findPreference("idd");
        pref3.setSummary(idd+"");

        Preference pref0 = findPreference("sz_0");
        pref0.setSummary("" + szerokosc);

        Preference pref2 = findPreference("sz_2");
        pref2.setSummary(getDeviceName());

        Preference pref7 = findPreference("lang");
        pref7.setOnPreferenceChangeListener(  new Preference.OnPreferenceChangeListener(){
             @Override
            public boolean onPreferenceChange(Preference preference,Object newValue) {
                setLocale((String) newValue);
                return true;
            }
        });
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

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }



}