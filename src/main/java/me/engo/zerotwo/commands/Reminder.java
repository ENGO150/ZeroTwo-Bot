package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class Reminder extends ListenerAdapter {

    public static String alias = "timer";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "reminder") || args[0].equalsIgnoreCase(c.prefix + alias)) {

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
                } else if (args.length == 2 || args.length == 3){
                    File f = new File("Cooldown/Reminder/" + event.getAuthor().getId());

                    if (f.exists()){
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("cooldown").getAsString();
                        event.getChannel().sendMessage(text).queue();
                        return;
                    }

                    try {
                        String unit;
                        File gif = new File("assets/Wakeup.gif");
                        if (args.length == 2) {
                            unit = "s";

                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("reminder_wakeup").getAsString();
                            event.getChannel().sendMessage(text + event.getAuthor().getAsMention() + "!").addFile(gif).queueAfter(Integer.parseInt(args[1]), TimeUnit.SECONDS, (message -> {
                                try {
                                    Files.delete(f.toPath());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }));
                        } else {
                            switch (args[2].toLowerCase()) {
                                case "s":
                                    unit = "s";
                                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("reminder_wakeup").getAsString();
                                    event.getChannel().sendMessage(text + event.getAuthor().getAsMention() + "!").addFile(gif).queueAfter(Integer.parseInt(args[1]), TimeUnit.SECONDS, (message -> {
                                        try {
                                            Files.delete(f.toPath());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }));
                                    break;

                                case "m":
                                    unit = "m";
                                    String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("reminder_wakeup").getAsString();
                                    event.getChannel().sendMessage(text2 + event.getAuthor().getAsMention() + "!").addFile(gif).queueAfter(Integer.parseInt(args[1]), TimeUnit.MINUTES, (message -> {
                                        try {
                                            Files.delete(f.toPath());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }));
                                    break;

                                case "h":
                                    unit = "h";
                                    String text3 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("reminder_wakeup").getAsString();
                                    event.getChannel().sendMessage(text3 + event.getAuthor().getAsMention() + "!").addFile(gif).queueAfter(Integer.parseInt(args[1]), TimeUnit.HOURS, (message -> {
                                        try {
                                            Files.delete(f.toPath());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }));
                                    break;

                                case "d":
                                    unit = "d";
                                    String text4 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("reminder_wakeup").getAsString();
                                    event.getChannel().sendMessage(text4 + event.getAuthor().getAsMention() + "!").addFile(gif).queueAfter(Integer.parseInt(args[1]), TimeUnit.DAYS, (message -> {
                                        try {
                                            Files.delete(f.toPath());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }));
                                    break;

                                default:
                                    String text5 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("invalid_unit").getAsString();
                                    event.getChannel().sendMessage("'" + args[2] + text5).queue();
                                    return;
                            }
                        }

                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("reminder").getAsString();
                        event.getChannel().sendMessage(text + args[1] + unit + ".").queue();
                    } catch (Exception ex){
                        event.getChannel().sendMessage(ex.getMessage()).queue();
                    }

                    Files.createFile(f.toPath());
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    event.getChannel().sendMessage(text).queue();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
