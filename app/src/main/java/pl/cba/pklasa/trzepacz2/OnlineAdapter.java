package pl.cba.pklasa.trzepacz2;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



public class OnlineAdapter extends ArrayAdapter<GameD> {

 public Context mContext;
 public SharedPreferences preferences;
 public int idd;
 private String url3 = "http://pklasa.cba.pl/Trzepacz/android3_trzepacz.php";
 private String url4 = "http://pklasa.cba.pl/Trzepacz/android4_trzepacz.php";
 private String url6 = "http://pklasa.cba.pl/Trzepacz/android6_trzepacz.php";
 ArrayAdapter<GameD> tenadapter;
float online_co=1;
   public  int ranga = 0;

    private Integer[] male = {
            R.drawable.fish,
            R.drawable.whale0,
            R.drawable.cach0,
            R.drawable.orca0,
            R.drawable.narw0,
            R.drawable.walen0,
    };



 public OnlineAdapter(Context context, List<GameD> items,int idd,int ranga) {
    super(context, R.layout.online_item, items);
     this.idd=idd;
     mContext=context;
     this.online_co = online_co;
     this.ranga=ranga;
    }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder viewHolder;
    int pozyca=0;

    // inflate the GridView item layout
    LayoutInflater inflater = LayoutInflater.from(getContext());
    convertView = inflater.inflate(R.layout.online_item, parent, false);

    // initialize the view holder
    viewHolder = new ViewHolder();
    viewHolder.status = (TextView) convertView.findViewById(R.id.status);

     viewHolder.graj = (Button) convertView.findViewById(R.id.graj);
     viewHolder.rewanz = (Button) convertView.findViewById(R.id.rewanz);
     viewHolder.usun = (Button) convertView.findViewById(R.id.usun);
     viewHolder.calosc = (RelativeLayout) convertView.findViewById(R.id.calosc7);
     viewHolder.lewe = (TextView) convertView.findViewById(R.id.textView8);
     viewHolder.prawe = (TextView) convertView.findViewById(R.id.textView9);
     viewHolder.tabela = (TableRow) convertView.findViewById(R.id.szczegoly);

    viewHolder.graczLeft = (TextView) convertView.findViewById(R.id.imieLeft);
    viewHolder.graczRight = (TextView) convertView.findViewById(R.id.imieRight);
    viewHolder.pktLeft = (TextView) convertView.findViewById(R.id.punktyLeft);
     viewHolder.pktRight = (TextView) convertView.findViewById(R.id.punktyRight);

     viewHolder.obrazekLeft = (ImageView) convertView.findViewById(R.id.lewyObraz);
     viewHolder.obrazekRight = (ImageView) convertView.findViewById(R.id.prawyObraz);



    convertView.setTag(viewHolder);

    final int cmt = position;
    final View tenView = convertView;

     tenadapter = this;

     if(getItem(cmt).getPoinformowany()==7){
         viewHolder.tabela.setVisibility(View.VISIBLE);
     }else{
         viewHolder.tabela.setVisibility(View.GONE);
     }

     viewHolder.obrazekLeft.setImageResource(male[getItem(position).getRanga1()]);
     viewHolder.obrazekRight.setImageResource(male[getItem(position).getRanga2()]);


    viewHolder.lewe.setText(getItem(position).getRzut0_0()+"\n"+getItem(position).getRzut1_0()+"\n"+getItem(position).getRzut2_0()+"\n"+getItem(position).getRzut3_0()+"\n"+getItem(position).getRzut4_0());
     viewHolder.prawe.setText(getItem(position).getRzut0_1()+"\n"+getItem(position).getRzut1_1()+"\n"+getItem(position).getRzut2_1()+"\n"+getItem(position).getRzut3_1()+"\n"+getItem(position).getRzut4_1());

     if(getItem(cmt).getKoniec_gry()==1){
         viewHolder.status.setText(mContext.getString(R.string.koniec_gr));
         if ((getItem(cmt).getId1() == idd && getItem(cmt).getGracz() == 0) || (getItem(cmt).getId2() == idd && getItem(cmt).getGracz() == 1)) {
             viewHolder.graj.setVisibility(View.VISIBLE);
             viewHolder.usun.setVisibility(View.GONE);
             viewHolder.graj.setText("OK");
         }else{
             viewHolder.graj.setVisibility(View.GONE);
             viewHolder.usun.setVisibility(View.VISIBLE);
         }
         viewHolder.rewanz.setVisibility(View.VISIBLE);
        }else {
         viewHolder.graj.setText(mContext.getString(R.string.graj_____));
         viewHolder.rewanz.setVisibility(View.GONE);
         if ((getItem(cmt).getId1() == idd && getItem(cmt).getGracz() == 0) || (getItem(cmt).getId2() == idd && getItem(cmt).getGracz() == 1)) {
             viewHolder.status.setText(mContext.getString(R.string.runda_num)+" "+getItem(cmt).getRunda()+". "+mContext.getString(R.string.twoj_ruch));
             viewHolder.graj.setVisibility(View.VISIBLE);
             viewHolder.usun.setVisibility(View.GONE);
         } else {
             viewHolder.status.setText(mContext.getString(R.string.runda_num)+" "+getItem(cmt).getRunda()+". "+mContext.getString(R.string.czekaj_ru));
             viewHolder.usun.setVisibility(View.VISIBLE);
         }
     }


    //NIE POKAZUJ USUN JEZELI
     // -nie rozwinales menu - to jednak juz nie
     // -data jest nowsza niz 24h wstecz
    //if(getItem(cmt).getPoinformowany()!=7){viewHolder.usun.setVisibility(View.GONE);}

     Calendar calendar = Calendar.getInstance();
     calendar.add(Calendar.DATE,-1); //jeden dzien wstecz!
     int t_dzien = calendar.get(Calendar.DAY_OF_MONTH);
     int t_miesiac = (calendar.get(Calendar.MONTH)+1);
     int t_rok = calendar.get(Calendar.YEAR);
     int t_godzina = (calendar.get(Calendar.HOUR_OF_DAY));
     int t_minuta = calendar.get(Calendar.MINUTE);
     String s_dzien= ""+t_dzien;
     if(t_dzien<10){s_dzien="0"+s_dzien;}
     String s_rok= ""+t_rok;
     String s_miesiac= ""+t_miesiac;
     if(t_miesiac<10){s_miesiac="0"+s_miesiac;}
     String s_godzina= ""+t_godzina;
     if(t_godzina<10){s_godzina="0"+s_godzina;}
     String s_minuta= ""+t_minuta;
     if(t_minuta<10){s_minuta="0"+s_minuta;}
     String teraz = s_rok+"-"+s_miesiac+"-"+s_dzien+" "+s_godzina+":"+s_minuta+":00";

     if(getItem(cmt).getData().compareTo(teraz)>0){viewHolder.usun.setVisibility(View.GONE);}


    viewHolder.graczLeft.setText(getItem(cmt).getNazwa1()+" ");
    viewHolder.graczRight.setText(" "+getItem(cmt).getNazwa2());
    viewHolder.pktLeft.setText(getItem(cmt).getWynik0()+" ");
    viewHolder.pktRight.setText(" "+getItem(cmt).getWynik1());

     if(cmt%2==1) {
         viewHolder.calosc.setBackgroundColor(0xff237ea0);
     }else{
         viewHolder.calosc.setBackgroundColor(0xff338eb0);
     }

     viewHolder.graj.setOnClickListener(new View.OnClickListener() {
         public void onClick(View arg1) {
             if(getItem(cmt).getKoniec_gry()==1){
                 String finalUrl4 = url4;
                 HandleJSON4 obj4 = new HandleJSON4(finalUrl4,online_co);
                 obj4.fetchJSON(getItem(cmt).getId(), getItem(cmt).getId1(), getItem(cmt).getId2(),idd, getItem(cmt).getGracz(),getItem(cmt).getRunda(),0,0);

                long od_czas=0;
                long teraz_czas=0;
                 Time today = new Time(Time.getCurrentTimezone());
                 today.setToNow();
                 teraz_czas = (today.toMillis(true))/1000;
                 od_czas = (today.toMillis(true))/1000;
                 while (obj4.complete == 0 && (teraz_czas-od_czas)<6) {
                     today = new Time(Time.getCurrentTimezone());
                     today.setToNow();
                     teraz_czas = (today.toMillis(true))/1000;
                 }


                 GameD wynik = obj4.getWynik();
                 tenadapter.remove(getItem(cmt));
                 tenadapter.notifyDataSetChanged();

             }else{
                 Intent intent = new Intent(mContext, GraOnline.class);
                 Bundle bundle = new Bundle();
                 bundle.putInt("id", getItem(cmt).getId());
                 bundle.putInt("id1", getItem(cmt).getId1());
                 bundle.putInt("id2",  getItem(cmt).getId2());
                 bundle.putInt("gracz",  getItem(cmt).getGracz());
                 bundle.putInt("runda",  getItem(cmt).getRunda());
                 bundle.putInt("moje_id", idd);
                 intent.putExtras(bundle);

                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 mContext.startActivity(intent);

             }}
         });


     viewHolder.rewanz.setOnClickListener(new View.OnClickListener() {
         public void onClick(View arg1) {
             if(getItem(cmt).getKoniec_gry()==1){
                 if ((getItem(cmt).getId1() == idd && getItem(cmt).getGracz() == 0) || (getItem(cmt).getId2() == idd && getItem(cmt).getGracz() == 1)) {
                    //JEZELI USUWAMY GRE Z PAMIECI
                     String finalUrl4 = url4;
                     HandleJSON4 obj4 = new HandleJSON4(finalUrl4,online_co);
                     obj4.fetchJSON(getItem(cmt).getId(), getItem(cmt).getId1(), getItem(cmt).getId2(),idd, getItem(cmt).getGracz(),getItem(cmt).getRunda(),0,0);
                     long od_czas=0;
                     long teraz_czas=0;
                     Time today = new Time(Time.getCurrentTimezone());
                     today.setToNow();
                     teraz_czas = (today.toMillis(true))/1000;
                     od_czas = (today.toMillis(true))/1000;
                     while (obj4.complete == 0 && (teraz_czas-od_czas)<6) {
                         today = new Time(Time.getCurrentTimezone());
                         today.setToNow();
                         teraz_czas = (today.toMillis(true))/1000;
                     }
                     GameD wynik = obj4.getWynik();
                     tenadapter.remove(getItem(cmt));
                     tenadapter.notifyDataSetChanged();
                 }

                 //TWORZYMY NOWA GRE Z KONKRETNYM GRACZEM
                 String finalUrl3 = url3;
                 HandleJSON3 obj3 = new HandleJSON3(finalUrl3, 1);
                    //pobieramy moja nazwe i idd_opponenta
                 String moja_nazwa = getItem(cmt).getNazwa1();
                 int idd_opp =  getItem(cmt).getId2();

                 if(getItem(cmt).getId2()==idd){
                     moja_nazwa=getItem(cmt).getNazwa2();
                     idd_opp = getItem(cmt).getId1();
                 }

                 obj3.fetchJSON(idd, moja_nazwa,idd_opp,ranga);
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

                        /*Intent intent = new Intent(getBaseContext(), Gra0.class);
                        startActivityForResult(intent, 1337);*/


                 Intent intent = new Intent(mContext, GraOnline.class);
                 Bundle bundle = new Bundle();
                 bundle.putInt("id", wynik.getId());
                 bundle.putInt("id1", wynik.getId1());
                 bundle.putInt("id2", wynik.getId2());
                 bundle.putInt("gracz", wynik.getGracz());
                 bundle.putInt("runda", wynik.getRunda());
                 bundle.putInt("moje_id", idd);
                 intent.putExtras(bundle);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 mContext.startActivity(intent);

             }}
     });


     viewHolder.usun.setOnClickListener(new View.OnClickListener() {
         public void onClick(View arg1) {

             String finalUrl6 = url6;
             HandleJSON6 obj6 = new HandleJSON6(finalUrl6);
             obj6.fetchJSON(idd,getItem(cmt).getId(), getItem(cmt).getId1(), getItem(cmt).getId2());

             long od_czas=0;
             long teraz_czas=0;
             Time today = new Time(Time.getCurrentTimezone());
             today.setToNow();
             teraz_czas = (today.toMillis(true))/1000;
             od_czas = (today.toMillis(true))/1000;
             while (obj6.complete == 0 && (teraz_czas-od_czas)<6) {
                 today = new Time(Time.getCurrentTimezone());
                 today.setToNow();
                 teraz_czas = (today.toMillis(true))/1000;
             }
             //int wynik = obj6.getWynik();
             tenadapter.remove(getItem(cmt));
             tenadapter.notifyDataSetChanged();
         }
     });

     viewHolder.calosc.setOnClickListener(new View.OnClickListener() {
         public void onClick(View arg1) {
             getItem(cmt).setPoinformowany(7 -  getItem(cmt).getPoinformowany());
             notifyDataSetChanged();
           }
     });

 return convertView;
 }



private static class ViewHolder {
    TextView status;

    Button graj;
    Button rewanz;
    Button usun;
    RelativeLayout calosc;
    TextView lewe;
    TextView prawe;
    TableRow tabela;

    TextView graczLeft;
    TextView graczRight;
    TextView pktLeft;
    TextView pktRight;

    ImageView obrazekLeft;
    ImageView obrazekRight;
    }
}

