package org.com.Models;

import java.util.ArrayList;
import java.util.List;


public class Continent {
    private int d_id;
    private String d_name;
    private int d_value;
    private List<Country> d_countries;


    public Continent(int p_id, int p_value){
        d_id = p_id;
        d_value = p_value;
        d_countries = new ArrayList<>();
    }

    public int getId(){
        return d_id;
    }

    public void setId(int p_id){
        d_id = p_id;
    }

    public void setName(String p_name){
        d_name = p_name;
    }

    public String getName(){
        return d_name;
    }

    public void addCountry(Country p_country){
        d_countries.add(p_country);
    }

    public void removeCountry(Country p_country){
        d_countries.remove(p_country);
    }

    public List<Country> getCountries(){
        return d_countries;
    }

    public void setValue(int p_value){
        d_value = p_value;
    }

    public int getValue(){
        return d_value;
    }

}



