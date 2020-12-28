package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Bot;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Weather extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "weather")) {

            try {
                String language;
                File languages = new File("Database/Language/" + event.getAuthor().getId());
                if (languages.exists()) {
                    File[] languages_ = languages.listFiles();
                    assert languages_ != null;
                    language = languages_[0].getName();
                } else {
                    language = "english_en";
                }

                if (args.length < 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                } else {
                    try {
                        EmbedBuilder em = new EmbedBuilder();
                        URL url;
                        if (args.length == 2) {
                            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + args[1] + "&appid=" + Bot.token_weather);
                            em.setTitle("Weather in " + args[1]);
                        } else {
                            int a = args.length;

                            String[] slova = new String[a];

                            System.arraycopy(args, 1, slova, 0, a - 1);

                            StringBuilder done = new StringBuilder();

                            for (int i = 0; i < slova.length - 1; i++) {
                                done.append(" ").append(slova[i]);
                            }

                            done = new StringBuilder(done.substring(1));
                            em.setTitle("Weather in " + done.toString());
                            done = new StringBuilder(done.toString().replace(" ", "%20"));

                            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + done.toString() + "&appid=" + Bot.token_weather);
                        }

                        JSONTokener tokener = new JSONTokener(url.openStream());
                        JSONObject obj = new JSONObject(tokener);

                        JSONArray weatherArray = obj.getJSONArray("weather");
                        JSONObject weather = (JSONObject) weatherArray.get(0);
                        String main = weather.getString("main");
                        String desc = weather.getString("description");

                        JSONObject wind = obj.getJSONObject("wind");
                        String speed = String.valueOf(wind.getDouble("speed"));

                        JSONObject main_ = obj.getJSONObject("main");
                        double temp_c =  main_.getDouble("temp");
                        temp_c -= 273.15;
                        double temp_f = 1.8 * temp_c + 32;
                        temp_c = Math.round(temp_c);
                        temp_f = Math.round(temp_f);

                        int timezone = obj.getInt("timezone");
                        boolean minus;
                        if (String.valueOf(timezone).startsWith("-")){
                            minus = true;
                            timezone = Integer.parseInt(String.valueOf(timezone).substring(1));
                        } else {
                            minus = false;
                        }
                        timezone /= 60;
                        timezone /= 60;
                        String timezone_s;
                        if (minus){
                            timezone_s = "-" + timezone;
                        } else {
                            timezone_s = "+" + timezone;
                        }

                        em.addField(main, desc, false);

                        em.addField("Wind", speed + " m/s", false);

                        em.addField("Temperature", temp_c + " °C, " + temp_f + " °F", false);

                        em.addField("Timezone", timezone_s, false);

                        em.setColor(new Color(c.Color));
                        em.setFooter(c.footer1, c.footer2);

                        event.getChannel().sendMessage(em.build()).queue();
                    } catch (IOException e){
                        try {
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("city_false").getAsString();
                            event.getChannel().sendMessage(text).queue();
                        } catch (FileNotFoundException ex){
                            ex.printStackTrace();
                        }
                    }
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
