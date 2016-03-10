package pl.cba.pklasa.trzepacz2;

/**
 * Created by Pioter on 2016-01-08.
 package com.example.pioter.pkalendarz;

 /**
 * Created by Pioter on 2015-04-02.
 */
public class GameD {


    private int id;
    private int id1;
    private int id2;
    private int koniec_gry;
    private int poinformowany;
    private int runda;
    private int gracz;
    private String data;
    private String nazwa1;
    private String nazwa2;
    private int rzut0_0;
    private int rzut1_0;
    private int rzut2_0;
    private int rzut3_0;
    private int rzut4_0;
    private int rzut0_1;
    private int rzut1_1;
    private int rzut2_1;
    private int rzut3_1;
    private int rzut4_1;

    private int ranga1;

    public int getRanga1() {
        return ranga1;
    }

    public void setRanga1(int ranga1) {
        this.ranga1 = ranga1;
    }

    public int getRanga2() {
        return ranga2;
    }

    public void setRanga2(int ranga2) {
        this.ranga2 = ranga2;
    }

    private int ranga2;

    public GameD(int id, int id1, int id2, int koniec_gry, int poinformowany, int runda, int gracz, String data, String nazwa1, String nazwa2, int rzut1_0, int rzut0_0, int rzut2_0, int rzut3_0, int rzut4_0, int rzut0_1, int rzut1_1, int rzut2_1, int rzut3_1, int rzut4_1) {
        this.id = id;
        this.id1 = id1;
        this.id2 = id2;
        this.koniec_gry = koniec_gry;
        this.poinformowany = poinformowany;
        this.runda = runda;
        this.gracz = gracz;
        this.data = data;
        this.nazwa1 = nazwa1;
        this.nazwa2 = nazwa2;
        this.rzut1_0 = rzut1_0;
        this.rzut0_0 = rzut0_0;
        this.rzut2_0 = rzut2_0;
        this.rzut3_0 = rzut3_0;
        this.rzut4_0 = rzut4_0;
        this.rzut0_1 = rzut0_1;
        this.rzut1_1 = rzut1_1;
        this.rzut2_1 = rzut2_1;
        this.rzut3_1 = rzut3_1;
        this.rzut4_1 = rzut4_1;
        this.ranga1 = 0;
        this.ranga2 = 0;
    }

    public GameD() {
        this.id = 0;
        this.id1 = 0;
        this.id2 = 0;
        this.koniec_gry = 0;
        this.poinformowany = 0;
        this.runda = 0;
        this.gracz = 0;
        this.data = "";
        this.nazwa1 = "";
        this.nazwa2 = "";
        this.rzut1_0 = 0;
        this.rzut0_0 = 0;
        this.rzut2_0 = 0;
        this.rzut3_0 = 0;
        this.rzut4_0 = 0;
        this.rzut0_1 = 0;
        this.rzut1_1 = 0;
        this.rzut2_1 = 0;
        this.rzut3_1 = 0;
        this.rzut4_1 = 0;
        this.ranga1 = 0;
        this.ranga2 = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRzut4_1() {
        return rzut4_1;
    }

    public void setRzut4_1(int rzut4_1) {
        this.rzut4_1 = rzut4_1;
    }

    public int getRzut3_1() {
        return rzut3_1;
    }

    public void setRzut3_1(int rzut3_1) {
        this.rzut3_1 = rzut3_1;
    }

    public int getRzut2_1() {
        return rzut2_1;
    }

    public void setRzut2_1(int rzut2_1) {
        this.rzut2_1 = rzut2_1;
    }

    public int getRzut1_1() {
        return rzut1_1;
    }

    public void setRzut1_1(int rzut1_1) {
        this.rzut1_1 = rzut1_1;
    }

    public int getRzut0_1() {
        return rzut0_1;
    }

    public void setRzut0_1(int rzut0_1) {
        this.rzut0_1 = rzut0_1;
    }

    public int getRzut4_0() {
        return rzut4_0;
    }

    public void setRzut4_0(int rzut4_0) {
        this.rzut4_0 = rzut4_0;
    }

    public int getRzut3_0() {
        return rzut3_0;
    }

    public void setRzut3_0(int rzut3_0) {
        this.rzut3_0 = rzut3_0;
    }

    public int getRzut2_0() {
        return rzut2_0;
    }

    public void setRzut2_0(int rzut2_0) {
        this.rzut2_0 = rzut2_0;
    }

    public int getRzut1_0() {
        return rzut1_0;
    }

    public void setRzut1_0(int rzut1_0) {
        this.rzut1_0 = rzut1_0;
    }

    public int getRzut0_0() {
        return rzut0_0;
    }

    public void setRzut0_0(int rzut0_0) {
        this.rzut0_0 = rzut0_0;
    }

    public String getNazwa2() {
        return nazwa2;
    }

    public void setNazwa2(String nazwa2) {
        this.nazwa2 = nazwa2;
    }

    public String getNazwa1() {
        return nazwa1;
    }

    public void setNazwa1(String nazwa1) {
        this.nazwa1 = nazwa1;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getGracz() {
        return gracz;
    }

    public void setGracz(int gracz) {
        this.gracz = gracz;
    }

    public int getRunda() {
        return runda;
    }

    public void setRunda(int runda) {
        this.runda = runda;
    }

    public int getPoinformowany() {
        return poinformowany;
    }

    public void setPoinformowany(int poinformowany) {
        this.poinformowany = poinformowany;
    }

    public int getKoniec_gry() {
        return koniec_gry;
    }

    public void setKoniec_gry(int koniec_gry) {
        this.koniec_gry = koniec_gry;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }



    public int getWynik0(){
        return rzut0_0+rzut1_0+rzut2_0+rzut3_0+rzut4_0;
    }

    public int getWynik1(){
        return rzut0_1+rzut1_1+rzut2_1+rzut3_1+rzut4_1;
    }
}
