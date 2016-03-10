package pl.cba.pklasa.trzepacz2;

/**
 * Created by Pioter on 2016-01-09.
 */
public class GameE {

    private int id;
    private String nazwa;
    private String telefon;
    private String data;
    private int ilosc_gier;
    private int ilosc_wygranych;
    private int punkty;
    private int pozycja;

    public GameE(int id, String nazwa, String data, String telefon, int ilosc_gier, int ilosc_wygranych, int punkty, int pozycja) {
        this.id = id;
        this.nazwa = nazwa;
        this.data = data;
        this.telefon = telefon;
        this.ilosc_gier = ilosc_gier;
        this.ilosc_wygranych = ilosc_wygranych;
        this.punkty = punkty;
        this.pozycja = pozycja;
    }

    public GameE() {
        this.id =0;
        this.nazwa = "";
        this.data = "";
        this.telefon = "";
        this.ilosc_gier = 0;
        this.ilosc_wygranych = 0;
        this.punkty = 0;
        this.pozycja =0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getIlosc_gier() {
        return ilosc_gier;
    }

    public void setIlosc_gier(int ilosc_gier) {
        this.ilosc_gier = ilosc_gier;
    }

    public int getIlosc_wygranych() {
        return ilosc_wygranych;
    }

    public void setIlosc_wygranych(int ilosc_wygranych) {
        this.ilosc_wygranych = ilosc_wygranych;
    }

    public int getPunkty() {
        return punkty;
    }

    public void setPunkty(int punkty) {
        this.punkty = punkty;
    }

    public int getPozycja() {
        return pozycja;
    }

    public void setPozycja(int pozycja) {
        this.pozycja = pozycja;
    }
}
