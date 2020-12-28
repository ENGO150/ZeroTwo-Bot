package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Spin extends ListenerAdapter {

    public static String alias = "wof";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
        Config c = new Config();
        String[] messageSent = Context.getMessage().getContentDisplay().split(" ");
        if (Context.getAuthor().isBot()) return;

        if (messageSent[0].equalsIgnoreCase(c.prefix + "spin") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

            try {
                String language;
                File languages = new File("Database/Language/" + Context.getAuthor().getId());
                if (languages.exists()) {
                    File[] languages_ = languages.listFiles();
                    assert languages_ != null;
                    language = languages_[0].getName();
                } else {
                    language = "english_en";
                }

                if (messageSent.length < 2) {
                    File spined = new File("Database/Pb/" + Context.getAuthor().getId() + "/spined");
                    File account = new File("Database/Pb/" + Context.getAuthor().getId());
                    if (!account.exists()){
                        String text1 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_1").getAsString();
                        String text2 = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("pb_profile_false_2").getAsString();
                        Context.getChannel().sendMessage(text1 + c.prefix + text2).queue();
                        return;
                    }
                    if (spined.exists()){
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("spin_true").getAsString();
                        Context.getChannel().sendMessage(text).queue();
                        return;
                    }
                    File money = new File("Database/Pb/" + Context.getAuthor().getId() + "/money");

                    Random rnd = new Random();
                    int rndm = rnd.nextInt(1000);
                    int wait = rnd.nextInt(3);

                    EmbedBuilder em = new EmbedBuilder();
                    em.setDescription(Context.getAuthor().getName() + " is spinning wheel of fortune");
                    em.setImage("https://cdn.zerotwo.dev/INTERNAL/WOF.gif");
                    em.setFooter(c.footer1, c.footer2);
                    em.setColor(c.Color);

                    EmbedBuilder e = new EmbedBuilder();
                    e.setDescription(Context.getAuthor().getName() + " won " + rndm + " credits");
                    e.setImage("https://cdn.zerotwo.dev/INTERNAL/MONEY.gif");
                    e.setFooter(c.footer1, c.footer2);
                    e.setColor(c.Color);

                    Context.getChannel().sendMessage(em.build()).queue((fight1 -> {
                        try {
                            Papasbattlefield.addMoney(rndm, money);
                            Files.createFile(spined.toPath());
                            fight1.editMessage(e.build()).queueAfter((wait + 5), TimeUnit.SECONDS);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }));
                } else {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

}
