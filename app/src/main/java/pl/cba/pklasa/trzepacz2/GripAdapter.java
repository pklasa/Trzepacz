package pl.cba.pklasa.trzepacz2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.List;

public class GripAdapter extends ArrayAdapter<String> {
    private Context mContext;
    int wybrany=0;
    public long record0=0;
    public long all_dist0=0;
    public int szerokosc0=0;

    public SharedPreferences preferences;

    private Integer[] unlocked1 = {
            R.drawable.nic,
            R.drawable.whale,
            R.drawable.cach,
            R.drawable.orca,
            R.drawable.narw,
            R.drawable.walen,
    };
    private int[] progi1 = {
            0,
            6000,
            7500,
            9000,
            10500,
            12000,
    };

    private int[] progi2 = {
            0,
            200000,
            500000,
            1000000,
            2000000,
            5000000,
    };

    private Integer[] unlocked2 = {
            R.drawable.nico,
            R.drawable.whale,
            R.drawable.cach,
            R.drawable.orca,
            R.drawable.narw,
            R.drawable.walen,
    };
    private Integer[] unlocked3 = {
            R.drawable.cc,
            R.drawable.c0,
            R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4,
    };

    private Integer[] unlockedD = {
            R.drawable.nico,
            R.drawable.whale,
            R.drawable.cach,
            R.drawable.orca,
            R.drawable.narw,
            R.drawable.walen,
    };


    int locked = R.drawable.block;

    public GripAdapter(Context context, List<String> items,long record,long all_dist,int szeroskosc) {
        super(context, R.layout.grid_item, items);
        record0=record;
        mContext = context;
        all_dist0=all_dist;
        preferences = context.getSharedPreferences("user_data", Activity.MODE_PRIVATE);
        wybrany = preferences.getInt("Wybor",0);
        szerokosc0=szeroskosc;

        //for(int i=0;i<6;i++)progi1[i]=(int)(realWynik(progi1[i],szerokosc0));
        //for(int i=0;i<6;i++)progi2[i]=(int)(realWynik(progi2[i],szerokosc0));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        int pozyca=0;

        // inflate the GridView item layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.grid_item, parent, false);

        // initialize the view holder
        viewHolder = new ViewHolder();
        viewHolder.img1 = (ImageView) convertView.findViewById(R.id.imageView1);
        viewHolder.img2 = (ImageView) convertView.findViewById(R.id.imageView2);
        viewHolder.img3 = (ImageView) convertView.findViewById(R.id.imageView3);
        viewHolder.txt1 = (TextView) convertView.findViewById(R.id.textView);
        viewHolder.calosc = (RelativeLayout) convertView.findViewById(R.id.calosc);
        viewHolder.pod1 = (TextView) convertView.findViewById(R.id.pod_1);
        viewHolder.pod2 = (TextView) convertView.findViewById(R.id.pod_2);

        convertView.setTag(viewHolder);


        final int cmt = position;
        final View tenView = convertView;
        // update the item view




        viewHolder.txt1.setText(getItem(cmt));
        viewHolder.img1.setImageResource((progi1[cmt]<record0)?unlockedD[cmt]:locked);
        viewHolder.img2.setImageResource((progi2[cmt]<all_dist0)?unlockedD[cmt]:locked);
        viewHolder.img3.setImageResource((progi1[cmt]<record0 && progi2[cmt]<all_dist0)?unlocked3[cmt]:locked);


        viewHolder.pod1.setText(mContext.getString(R.string.rzut_rzut)+" "+(int)(progi1[cmt]/100)+"cm");
        viewHolder.pod2.setText(mContext.getString(R.string.razem_raz)+" "+(int)(progi2[cmt]/10000)+"m");

        viewHolder.calosc.setOnClickListener(new View.OnClickListener() {
            public TimePicker picker;

            public void onClick(View arg1) {
                if((progi1[cmt]<record0 && progi2[cmt]<all_dist0)){
                    wybrany = cmt;
                    SharedPreferences.Editor preferencesEditor = preferences.edit();
                    preferencesEditor.putInt("Wybor", wybrany);
                    preferencesEditor.commit();
                notifyDataSetChanged();}else{
                    Toast.makeText(getContext(), mContext.getString(R.string.zablokowa) , Toast.LENGTH_SHORT).show();
                }
            }});


        if(position==wybrany){ viewHolder.calosc.setBackgroundColor(0xff015c80);}else{ viewHolder.calosc.setBackgroundColor(0x00ffffff);}

        // viewHolder.wiew.setBackgroundColor(kolorytla[(position)%6]);


        return convertView;
    }

    /**
     * The view holder design pattern prevents using findViewById()
     * repeatedly in the getView() method of the adapter.
     *
     * asdasd@see htsctp://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder
     */
    private static class ViewHolder {
        ImageView img1;
        ImageView img2;
        ImageView img3;
        TextView txt1;
        RelativeLayout calosc;

        TextView pod1;
        TextView pod2;
    }

    public long realWynik(int rzut,int szerokosc_px){
        return Math.round((float)rzut/(float)szerokosc_px*480);
    }
}

