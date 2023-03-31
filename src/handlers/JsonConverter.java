package handlers;

import com.google.gson.Gson;

public class JsonConverter {

    public static String toJson(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
    public static Object fromJson(String json, Class<model.Car> classType){
        Gson gson = new Gson();
        return gson.fromJson(json, classType);
    }



}
