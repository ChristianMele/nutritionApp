package edu.quinnipiac.ser210.nutritionapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
Christian Mele
February 29, 2020
Handler Class for parsing JSON data from the API
 */

public class Handler {

    public String getCalories(String str) throws JSONException {
        JSONArray jarray = (JSONArray) new JSONObject(str).get("hints");
        JSONObject foodObj = (JSONObject) jarray.get(0);
        JSONObject foodArray = (JSONObject) foodObj.get("food");
        JSONObject nutObj = (JSONObject) foodArray.get("nutrients");
        double strNum = (double) nutObj.get("ENERC_KCAL");
        String out = String.valueOf(strNum);
        return out;
    }

    public String getFat(String str) throws JSONException {
        JSONArray jarray = (JSONArray) new JSONObject(str).get("hints");
        JSONObject foodObj = (JSONObject) jarray.get(0);
        JSONObject foodArray = (JSONObject) foodObj.get("food");
        JSONObject nutObj = (JSONObject) foodArray.get("nutrients");
        double strNum = (double) nutObj.get("FAT");
        String out = String.valueOf(strNum);
        return out;
    }

    public String getPro(String str) throws JSONException {
        JSONArray jarray = (JSONArray) new JSONObject(str).get("hints");
        JSONObject foodObj = (JSONObject) jarray.get(0);
        JSONObject foodArray = (JSONObject) foodObj.get("food");
        JSONObject nutObj = (JSONObject) foodArray.get("nutrients");
        double strNum = (double) nutObj.get("PROCNT");
        String out = String.valueOf(strNum);
        return out;
    }

    public String getCarb(String str) throws JSONException {
        JSONArray jarray = (JSONArray) new JSONObject(str).get("hints");
        JSONObject foodObj = (JSONObject) jarray.get(0);
        JSONObject foodArray = (JSONObject) foodObj.get("food");
        JSONObject nutObj = (JSONObject) foodArray.get("nutrients");
        double strNum = (double) nutObj.get("CHOCDF");
        String out = String.valueOf(strNum);
        return out;
    }

    public String getFoodName(String nameStr) throws JSONException {
        return new JSONObject(nameStr).getString("text");
    }
}