package org.com.Models;

import java.util.ArrayList;
import java.util.List;


public class Continent {
    private int d_id;
    private String d_name;
    private int d_value;
    private List<Country> d_countries;


    public Continent(int p_id, int p_value){
        this.d_id = p_id;
        this.d_value = p_value;
        this.d_countries = new ArrayList<>();
    }

    public void setName(String p_name){
        this.d_name = p_name;
    }

    public String getName(){
        return this.d_name;
    }

    public void addCountry(Country p_country){
        this.d_countries.add(p_country);
    }

    public void removeCountry(Country p_country){
        this.d_countries.remove(p_country);
    }

    public List<Country> getCountries(){
        return this.d_countries;
    }

    public void setValue(int p_value){
        this.d_value = p_value;
    }

    public int getValue(){
        return this.d_value;
    }

}



