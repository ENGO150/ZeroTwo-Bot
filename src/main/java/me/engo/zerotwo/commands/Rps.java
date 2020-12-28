package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class Rps extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "rps")){

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
                } else if (args.length == 2) {

                    Random rnd = new Random();
                    String choice = args[1];
                    int rndm = rnd.nextInt(150);
                    String choice_full;
                    String choice_bot = null;
                    switch (choice) {
                        case "r":
                            choice_full = "rock";
                            break;
                        case "p":
                            choice_full = "paper";
                            break;
                        case "s":
                            choice_full = "scissors";
                            break;
                        default:
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_must_specify").getAsString();
                            event.getChannel().sendMessage(text).queue();
                            return;
                    }

                    if (rndm <= 50) {
                        if (choice_full.equals("rock")) {
                            choice_bot = "scissors";
                        }
                        if (choice_full.equals("paper")) {
                            choice_bot = "rock";
                        }
                        if (choice_full.equals("scissors")) {
                            choice_bot = "paper";
                        }

                        String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_won").getAsString();
                        String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_template_1").getAsString();
                        String text3 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_template_2").getAsString();
                        event.getChannel().sendMessage(text1 + text2 + choice_full + text3 + choice_bot + ".").queue();
                    }
                    if (rndm > 50 && rndm <= 100) {
                        if (choice_full.equals("rock")) {
                            choice_bot = "paper";
                        }
                        if (choice_full.equals("paper")) {
                            choice_bot = "scissors";
                        }
                        if (choice_full.equals("scissors")) {
                            choice_bot = "rock";
                        }

                        String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_lost").getAsString();
                        String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_template_1").getAsString();
                        String text3 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_template_2").getAsString();
                        event.getChannel().sendMessage(text1 + text2 + choice_full + text3 + choice_bot + ".").queue();
                    }
                    if (rndm > 100) {
                        if (choice_full.equals("rock")) {
                            choice_bot = "rock";
                        }
                        if (choice_full.equals("paper")) {
                            choice_bot = "paper";
                        }
                        if (choice_full.equals("scissors")) {
                            choice_bot = "scissors";
                        }

                        String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_match").getAsString();
                        String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_template_1").getAsString();
                        String text3 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("rps_template_2").getAsString();
                        event.getChannel().sendMessage(text1 + text2 + choice_full + text3 + choice_bot + ".").queue();
                    }

                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
}
