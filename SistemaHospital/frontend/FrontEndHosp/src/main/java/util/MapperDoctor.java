/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author Carlos Orozco
 */


import Main.LocalDateTimeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.ModelDoctores;

import java.time.LocalDateTime;
import java.util.List;

public class MapperDoctor {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static List<ModelDoctores> fromJsonToList(String json) {
        return gson.fromJson(json, new TypeToken<List<ModelDoctores>>(){}.getType());
    }
    
    public static Gson getGson(){
    return gson;
    }
}