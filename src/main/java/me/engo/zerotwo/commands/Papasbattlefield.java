package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonParser;

import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Papasbattlefield extends ListenerAdapter {

    public static String alias = "pb";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Config c = new Config();
        if (event.getAuthor().isBot()) return;
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(c.prefix + "papasbattlefield") || args[0].equalsIgnoreCase(c.prefix + alias)){

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

                EmbedBuilder em = new EmbedBuilder();
                EmbedBuilder emb = new EmbedBuilder();
                Random rnd = new Random();
                //ÚČET
                File account = new File("Database/Pb/" + event.getAuthor().getId());
                //PODSLOŽKY
                File money = new File("Database/Pb/" + event.getAuthor().getId() + "/money");
                File level = new File("Database/Pb/" + event.getAuthor().getId() + "/level");
                File stamina = new File("Database/Pb/" + event.getAuthor().getId() + "/stamina");
                //PODSOUBORY
                File moneyf = new File("Database/Pb/" + event.getAuthor().getId() + "/money/0");
                File levelf = new File("Database/Pb/" + event.getAuthor().getId() + "/level/1");
                File staminaf = new File("Database/Pb/" + event.getAuthor().getId() + "/stamina/1");

                File spined = new File("Database/Pb/" + event.getAuthor().getId() + "/spined");

                if (args.length < 2) {
                    if (account.mkdir()) {
                        money.mkdir();
                        level.mkdir();
                        stamina.mkdir();
                        try {
                            moneyf.createNewFile();
                            levelf.createNewFile();
                            staminaf.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        em.setTitle("Hi, " + event.getAuthor().getName() + "!");
                        em.setDescription("Welcome to Papa's Battlefield!\nYou now have a profile!\nUse `" + c.prefix + "papasbattlefield list` for an activity list or `" + c.prefix + "papasbattlefield actions` for an actions list.\nHave a nice day!");
                        em.setColor(new Color(c.Color));
                        em.setFooter(c.footer1, c.footer2);
                        em.setThumbnail("https://i.pinimg.com/736x/f8/98/8f/f8988fd3917424e3de13268ff01770c5.jpg");
                        event.getChannel().sendMessage(em.build()).queue();
                        event.getChannel().sendMessage("***This is just a BETA version. Your profile can be deleted. ***").queue();                                                                                                                                         //ODEBRAT
                    } else {
                        String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_true_1").getAsString();
                        String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_true_2").getAsString();
                        String text3 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_true_3").getAsString();

                        event.getChannel().sendMessage(text1 + c.prefix + text2 + c.prefix + text3).queue();
                    }
                } else if (args.length == 2) {
                    //LIST
                    if (args[1].equalsIgnoreCase("list")) {
                        em.setTitle("Activity List");
                        em.setDescription("Run (MONEY+)\nSwim (STAMINA++)");
                        em.setColor(new Color(c.Color));
                        em.setFooter(c.footer1, c.footer2);
                        event.getChannel().sendMessage(em.build()).queue();
                    } else if (args[1].equalsIgnoreCase("delete") || args[1].equalsIgnoreCase("del")) { //DELETE
                        if (account.exists()) {
                            File[] a = level.listFiles();
                            assert a != null;
                            a[0].delete();
                            level.delete();

                            File[] b = money.listFiles();
                            assert b != null;
                            b[0].delete();
                            money.delete();

                            File[] ce = stamina.listFiles();
                            assert ce != null;
                            ce[0].delete();
                            stamina.delete();

                            if (spined.exists()){ spined.delete(); }

                            account.delete();

                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_deleted").getAsString();
                            event.getChannel().sendMessage(text).queue();
                        } else {
                            String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_1").getAsString();
                            String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_2").getAsString();
                            event.getChannel().sendMessage(text1 + c.prefix + text2).queue();
                        }
                    } else if (args[1].equalsIgnoreCase("run")) { //RUN
                        if (account.exists()) {
                            em.setTitle(event.getAuthor().getName() + " went jogging");
                            em.setImage("https://data.whicdn.com/images/309114345/original.gif");
                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));

                            int vydelek = rnd.nextInt(29) + 1;
                            emb.setTitle(event.getAuthor().getName() + " found " + vydelek + " ¥");
                            emb.setImage("https://cdn.zerotwo.dev/INTERNAL/MONEY.gif");
                            emb.setFooter(c.footer1, c.footer2);
                            emb.setColor(new Color(c.Color));

                            event.getChannel().sendMessage(em.build()).queue((message -> {
                                try {

                                    addMoney(vydelek, money);
                                    message.editMessage(emb.build()).queueAfter(rnd.nextInt(3) + 4, TimeUnit.SECONDS);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }));

                        } else {
                            String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_1").getAsString();
                            String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_2").getAsString();
                            event.getChannel().sendMessage(text1 + c.prefix + text2).queue();
                        }
                    } else if (args[1].equalsIgnoreCase("swim")) {
                        if (account.exists()) {
                            em.setTitle(event.getAuthor().getName() + " went swimming");
                            em.setImage("https://i.pinimg.com/originals/d3/4d/09/d34d096dcc353904428e2a4be4507f75.gif");
                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));

                            int vydelek = rnd.nextInt(5) + 5;
                            emb.setTitle(event.getAuthor().getName() + " increased his stamina by " + vydelek + " \uD83D\uDCAA");
                            emb.setImage("https://media1.tenor.com/images/05eb3ffe46f5a422d7fade747f27a280/tenor.gif?itemid=15416883");
                            emb.setFooter(c.footer1, c.footer2);
                            emb.setColor(new Color(c.Color));

                            event.getChannel().sendMessage(em.build()).queue((message -> {
                                try {

                                    addStamina(vydelek, stamina);

                                    message.editMessage(emb.build()).queueAfter(rnd.nextInt(3) + 4, TimeUnit.SECONDS);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }));

                        } else {
                            String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_1").getAsString();
                            String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_2").getAsString();
                            event.getChannel().sendMessage(text1 + c.prefix + text2).queue();
                        }
                    } else if (args[1].equalsIgnoreCase("actions")) {
                        em.setTitle("Actions List");
                        em.setDescription("Level\nMoney\nStamina");
                        em.setColor(new Color(c.Color));
                        em.setFooter(c.footer1, c.footer2);
                        event.getChannel().sendMessage(em.build()).queue();

                    } else if (args[1].equalsIgnoreCase("level")) {
                        if (account.exists()) {
                            File[] levels = level.listFiles();
                            assert levels != null;
                            String name = levels[0].getName();
                            event.getChannel().sendMessage("Your level is " + name + ".").queue();
                        } else {
                            String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_1").getAsString();
                            String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_2").getAsString();
                            event.getChannel().sendMessage(text1 + c.prefix + text2).queue();
                        }
                    } else if (args[1].equalsIgnoreCase("money")) {
                        if (account.exists()) {
                            File[] moneys = money.listFiles();
                            assert moneys != null;
                            String name = moneys[0].getName();
                            event.getChannel().sendMessage("Your balance is " + name + ".").queue();
                        } else {
                            String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_1").getAsString();
                            String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_2").getAsString();
                            event.getChannel().sendMessage(text1 + c.prefix + text2).queue();
                        }
                    } else if (args[1].equalsIgnoreCase("stamina")) {
                        if (account.exists()) {
                            File[] staminas = stamina.listFiles();
                            assert staminas != null;
                            String name = staminas[0].getName();
                            event.getChannel().sendMessage("Your stamina is " + name + ".").queue();
                        } else {
                            String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_1").getAsString();
                            String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_2").getAsString();
                            event.getChannel().sendMessage(text1 + c.prefix + text2).queue();
                        }
                    } else {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_invalid_action").getAsString();
                        event.getChannel().sendMessage(text + args[1] + "'.").queue();
                    }
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

    }

    public static void addStamina(int value, File file){
        File[] staminas = file.listFiles();
        assert staminas != null;
        String b = staminas[0].getName();
        int a = Integer.parseInt(b);
        staminas[0].renameTo(new File(file + "/" + (value + a)));
    }

    public static void addMoney(int value, File file){
        File[] moneys = file.listFiles();
        assert moneys != null;
        String b = moneys[0].getName();
        int a = Integer.parseInt(b);
        moneys[0].renameTo(new File(file + "/" + (value + a)));
    }
}
