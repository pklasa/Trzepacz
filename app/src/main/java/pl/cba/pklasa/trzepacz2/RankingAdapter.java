package pl.cba.pklasa.trzepacz2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;



public class RankingAdapter extends ArrayAdapter<GameE> {

    private Context mContext;
    public SharedPreferences preferences;
    public int idd;
    private String url4 = "http://pklasa.cba.pl/Trzepacz/android4_trzepacz.php";
    ArrayAdapter<GameE> tenadapter;

    public RankingAdapter(Context context, List<GameE> items,int idd) {
        super(context, R.layout.ranking_item, items);
        this.idd=idd;
        mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        int pozyca=0;

        // inflate the GridView item layout
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.ranking_item, parent, false);

        // initialize the view holder
       viewHolder = new ViewHolder();
        viewHolder.pozycja = (TextView) convertView.findViewById(R.id.textView4);
        viewHolder.punkty = (TextView) convertView.findViewById(R.id.textView7);
        viewHolder.nazwa = (TextView) convertView.findViewById(R.id.textView5);
        viewHolder.telefon = (TextView) convertView.findViewById(R.id.textView6);
        viewHolder.calosc = (RelativeLayout) convertView.findViewById(R.id.calosc2);

        convertView.setTag(viewHolder);

        final int cmt = position;
        final View tenView = convertView;

        tenadapter = this;

        if(getItem(cmt).getId()==idd){
            viewHolder.calosc.setBackgroundColor(0xFF00CCFF);
        } else {
        viewHolder.calosc.setBackgroundColor(0x0000CCFF);}

        viewHolder.pozycja.setText(getItem(cmt).getPozycja()+"");
        viewHolder.punkty.setText(getItem(cmt).getPunkty()+"");
        viewHolder.telefon.setText(getItem(cmt).getTelefon());
        viewHolder.nazwa.setText(getItem(cmt).getNazwa());

        return convertView;
    }



    private static class ViewHolder {
        TextView pozycja;
        TextView nazwa;
        TextView telefon;
        TextView punkty;
        RelativeLayout calosc;
    }
}

