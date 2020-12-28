package me.engo.zerotwo.handlers;

import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Translate {

    public static String getTranslate(String language, String pos1, String pos2){
        String text = null;
        try {
            text =  JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get(pos1).getAsJsonObject().get(pos2).getAsString();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return text;
    }
}
