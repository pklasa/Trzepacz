package pl.cba.pklasa.trzepacz2;

/**
 * Created by Pioter on 2016-01-05.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.View;


/**
 * Created by Pioter on 2015-08-17.
 */

import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;


public class Gra0 extends Activity implements SensorEventListener {
    Obraz2d grafika;
    SensorManager mSensorManager;
    Sensor mAccelerometer;
    Double ax=0.0,ay=0.0;
    public int puszczone=0;
    public int moge_puscic=0;
    public SharedPreferences preferences;
    public long all_dist=0;
    public long record=0;
    public  int wynik=0;
    public  boolean wibracje = false;
    public  int wybrane = 0;
    public int widthh=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        grafika = new Obraz2d(this);
        setContentView(grafika);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);

        preferences = this.getSharedPreferences("user_data", Activity.MODE_PRIVATE);
        SharedPreferences sp2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        record = preferences.getLong("Record",0);
        all_dist = preferences.getLong("All_dist",0);
        wibracje = sp2.getBoolean("Wibracje",false);
        wybrane = preferences.getInt("Wybor",0);

        grafika.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg1) {
                if(puszczone==0 && moge_puscic==0){
                    puszczone=1;
                    moge_puscic=3;
                    wynik=0;

                    if(wibracje==true){
                    Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    // Vibrate for 500 milliseconds
                    v.vibrate(100);}
                }}
        });
    }



    public class Obraz2d extends View {
        Handler m_handler;
        Runnable m_handlerTask;
        int pozx, pozy, krok, srednica;
        Double kat, kat_v;
        int x,y;
        int dlugosc_wahadla;

        int k_x;
        double k_y;
        int k_x_v;
        double k_y_v;


        //Nowe zmienne
        double s = 1; //skala
        int p_x = 0;
        int p_y = 0; //wektor przesuniecia

        int jeszczelecialo=0;
        public double ax_text=0;


        int high_wynik=0;

        int szerokosc_px = 10;

        public Obraz2d(Context context) {
            super(context);

            kat = 0.0;
            kat_v=0.0;
            x=0;
            y=0;
            k_x=0;
            k_y=0;
            k_x_v=0;
            k_y_v=0;
            dlugosc_wahadla=100;

            m_handler = new Handler();
            move();

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            szerokosc_px = dm.widthPixels;

        }



        public void move()
        {
            m_handlerTask = new Runnable()
            {
                @Override
                public void run() {
                    dlugosc_wahadla=(int)(getWidth()*0.35);
                    int width = getWidth();
                    int height= getHeight();
                    //widthh=width;

                    double ax2 = ax;
                    double ay2 = ay;

                    int ogran=13;
                    int opor=20;

                    if(ax2>ogran)ax2=ogran;
                    if(ay2>ogran)ay2=ogran;
                    if(ax2<-ogran)ax2=-ogran;
                    if(ay2<-ogran)ay2=-ogran;
                    ax_text=ax2;

                    kat_v-=Math.sin(kat / 180 * Math.PI)/opor*ay2;
                    kat_v-=Math.cos(kat / 180 * Math.PI)/opor*ax2;
                    kat+=kat_v;
                    kat_v = kat_v*0.994;

                    int old_x = k_x;
                    int old_y = (int) k_y;

                    x = (int)(Math.sin(kat/180*Math.PI)*dlugosc_wahadla);
                    y = (int)(Math.cos(kat/180*Math.PI)*dlugosc_wahadla);

                    if(puszczone==0){
                        k_x = x;
                        k_y=y;
                        k_x_v = old_x - k_x;
                        k_y_v = old_y - y;}
                    else{
                        k_x-= k_x_v;
                        k_y-= k_y_v;
                        k_y_v=k_y_v-(double)(1*width/480);
                    }

                    if(puszczone==0 && moge_puscic==1){
                        moge_puscic=0;
                    }
                    if(puszczone==0 && moge_puscic==2){
                        moge_puscic=1;
                    }
                    if(puszczone==0 && moge_puscic==3){
                        moge_puscic=2;
                    }


                    if(puszczone==1){
                        if(k_y>height){
                            puszczone=0;}
                        if(k_y<0){wynik=Math.abs(k_x);
                            if(high_wynik<wynik)high_wynik=wynik;
                            jeszczelecialo=1;
                        }else{
                            if(jeszczelecialo==1) {
                                jeszczelecialo=0;

                                if(wibracje==true){
                                Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                // Vibrate for 500 milliseconds
                                v.vibrate(100);}

                                SharedPreferences.Editor preferencesEditor = preferences.edit();

                               // long true_wynik = Math.round((float)wynik/(float)gestosc*2.54*100);
                                 long true_wynik = realWynik(wynik,szerokosc_px);


                                all_dist+=true_wynik;
                                preferencesEditor.putLong("All_dist", all_dist);
                                if(true_wynik>record) {
                                    record = (int)true_wynik;
                                    Toast.makeText(getContext(), "REKORD!\n"+record+" mm" , Toast.LENGTH_LONG).show();
                                    preferencesEditor.putLong("Record", record);
                                }
                                preferencesEditor.commit();
                            }
                        };
                    }


                       /* m_handler.removeCallbacks(m_handlerTask);*/
                    invalidate();
                    m_handler.postDelayed(m_handlerTask, 30);

                }
            };
            m_handlerTask.run();
        }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        int h=getHeight();
        int w=getWidth();
        paint.setTextAlign(Paint.Align.CENTER);

        if(puszczone==1){

        /* 1 - WSPÓŁCZYNNIKI PRZESUNIECIA */
            s = 1.0;
            if(0.8*w/Math.abs(k_x)<s)s=0.8*w/Math.abs(k_x);
            if(0.8*h/Math.abs(k_y)<s)s=0.8*h/Math.abs(k_y);
            p_x = (int) (0.5*w - k_x*s/2);
            p_y = (int) (0.5*h - k_y*s/2);

        /* 2 - TŁA */
            paint.setStrokeWidth(0);
            paint.setColor(Color.parseColor("#015c80"));
            canvas.drawRect( 0,0, w, h, paint);
            paint.setColor(Color.parseColor("#237ea0"));
            canvas.drawRect( (int)(p_x - w/2*s), (int)(p_y - h/2*s), (int)(p_x + w/2*s), (int)(p_y + h/2*s), paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.parseColor("#00CCFF"));
            canvas.drawRect( (int)(p_x - w/2*s), (int)(p_y - h/2*s), (int)(p_x + w/2*s), (int)(p_y + h/2*s), paint);
            paint.setStyle(Paint.Style.FILL);
        /* 4 - NAPISY */
            paint.setTextSize((int)(h/6.9*s));
            canvas.drawText(""+realWynik(wynik,szerokosc_px),p_x,(int)(p_y-h*0.355*s), paint);
            paint.setTextSize((int)(h/23*s));
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("HIGH SCORE: "+realWynik(high_wynik,szerokosc_px),p_x,(int)(p_y-0.312*h*s), paint);
            paint.setStrokeWidth(2);

            paint.setColor(Color.parseColor("#237ea0"));
            if(k_x>0){
                paint.setTextAlign(Paint.Align.RIGHT);
                paint.setTextSize((int)(h/20));
                canvas.drawText(""+realWynik((int)(-k_y),szerokosc_px),(int) (p_x + k_x*s- 0.1*w),(int) (p_y + k_y*s + h/40), paint);
                canvas.drawLine((int) (p_x + k_x*s - 0.09*w),(int) (p_y + k_y*s),(int) (p_x + k_x*s - 0.01*w - 0.07*w*s),(int) (p_y + k_y*s),paint);
                canvas.drawLine((int) (p_x + k_x*s ),(int) (p_y + k_y*s - 0.09*w),(int) (p_x + k_x*s),(int) (p_y + k_y*s - 0.01*w - 0.07*w*s),paint);
                canvas.drawText(""+realWynik(wynik,szerokosc_px),(int) (p_x + k_x*s),(int) (p_y + k_y*s - 0.1*w), paint);
            }else{
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setTextSize((int)(h/20));
                canvas.drawText(""+realWynik((int)(-k_y),szerokosc_px),(int) (p_x + k_x*s + 0.1*w),(int) (p_y + k_y*s +h/40), paint);
                canvas.drawLine((int) (p_x + k_x*s + 0.09*w),(int) (p_y + k_y*s),(int) (p_x + k_x*s + 0.01*w + 0.07*w*s),(int) (p_y + k_y*s),paint);
                canvas.drawLine((int) (p_x + k_x*s ),(int) (p_y + k_y*s - 0.09*w),(int) (p_x + k_x*s),(int) (p_y + k_y*s - 0.01*w - 0.07*w*s),paint);
                canvas.drawText(""+realWynik(wynik,szerokosc_px),(int) (p_x + k_x*s),(int) (p_y + k_y*s - 0.1*w), paint);
            }
            paint.setColor(Color.parseColor("#00CCFF"));
            paint.setTextAlign(Paint.Align.CENTER);
        /* 3 - ELEMENTY PO PRZESKALOWANIU */

            //canvas.drawCircle((int) (p_x + k_x*s) ,(int) (p_y + k_y*s), (int) (h * 0.05 * s), paint);


            paint.setStrokeWidth((int) (h * 0.01 * s));
            canvas.drawLine(p_x,  p_y,(int)( x*s + p_x), (int)( y*s + p_y), paint);
            paint.setStrokeWidth(2);
            canvas.drawLine( 0,(int)(p_y),w,(int)(p_y),paint);

            if(k_x>0) {
                canvas.drawLine( (int)(p_x + wynik*s),(int)(p_y+0.05*h),(int)(p_x + wynik*s),(int)((int)(p_y-0.05*h)),paint);
                canvas.drawLine( (int)(p_x + high_wynik*s),(int)(p_y+0.08*h),(int)(p_x + high_wynik*s),(int)((int)(p_y)),paint);
            }else{
                canvas.drawLine( (int)(p_x - wynik*s),(int)(p_y+0.05*h),(int)(p_x - wynik*s),(int)((int)(p_y-0.05*h)),paint);
                canvas.drawLine( (int)(p_x - high_wynik*s),(int)(p_y+0.08*h),(int)(p_x - high_wynik*s),(int)((int)(p_y)),paint);
            }
            rysuj_kule(canvas,paint,(int) (p_x + k_x*s),(int) (p_y + k_y*s),s,h);

        }else{

            paint.setColor(Color.parseColor("#237ea0"));
            canvas.drawPaint(paint);


            paint.setColor(Color.parseColor("#00CCFF"));
            paint.setTextSize((int)(h/6.9));
            canvas.drawText(""+realWynik(wynik,szerokosc_px),w/2,(int)(h*0.145), paint);
            paint.setTextSize((int)(h/23));
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("HIGH SCORE: "+realWynik(high_wynik,szerokosc_px),w/2,(int)(h*0.188), paint);

            paint.setStrokeWidth(1);
            canvas.drawLine( (int)(0),(int)(0),(int)(0),(int)(h-1),paint);
            canvas.drawLine( (int)(0),(int)(0),(int)(w-1),(int)(0),paint);
            canvas.drawLine( (int)(0),(int)(h-1),(int)(w-1),(int)(h-1),paint);
            canvas.drawLine( (int)(w-1),(int)(0),(int)(w-1),(int)(h-1),paint);

            paint.setStrokeWidth(2);
            canvas.drawLine( 0,(int)(h/2),w,(int)(h/2),paint);
            paint.setStrokeWidth((int) (h * 0.01));
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeJoin(Paint.Join.ROUND);
            canvas.drawLine(w / 2, h / 2, x+w / 2, y+h / 2, paint);
            rysuj_kule(canvas,paint,(int)(k_x + w / 2),(int)(k_y + h / 2),1,h);

        }

        //JAKIS NAPIS DLA TESTOW
        //paint.setTextSize((int)(h/29));
        //canvas.drawText(szerokosc_px+"."+widthh+"."+ax_text,(int)(w/2),(int)(h/29), paint);

        }
    }

    private void rysuj_kule(Canvas canvas,Paint paint,int x,int y,double s,int h){

        paint.setStrokeWidth((float)(4*s));

        switch (wybrane) {
            case 0:
                paint.setColor(Color.parseColor("#00CCFF"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                break;
            case 1:
                paint.setColor(Color.parseColor("#7192bf"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.parseColor("#263b56"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.FILL);
                break;
            case 2:
                paint.setColor(Color.parseColor("#aeaeae"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.parseColor("#666666"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.FILL);
                break;
            case 3:
                paint.setColor(Color.parseColor("#ffffff"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.parseColor("#000000"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.FILL);
                break;
            case 4:
                paint.setColor(Color.parseColor("#ffffff"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.parseColor("#cccccc"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.FILL);
                break;
            case 5:
                paint.setColor(Color.parseColor("#99D8EA"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.parseColor("#00CCFF"));
                canvas.drawCircle(x,y, (int) (h * 0.05*s), paint);
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(Color.parseColor("#FFFFFF"));
                canvas.drawCircle(x,y, (int) (h * 0.02*s), paint);
                break;
            default:
                break;

        }

        paint.setColor(Color.parseColor("#00CCFF"));


    }




    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
// can be safely ignored for this demo
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        ax = (double)x;
        ay = (double)y;
    }

  /*  @Override
    public void onBackPressed() {
     //   super.onBackPressed();
        //this.finish();
        finish();
    }*/


    public long realWynik(int rzut,int szerokosc_px){
        return Math.round((float)rzut/(float)szerokosc_px*480);
    }
}