package me.engo.zerotwo.commands;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Welcome extends ListenerAdapter {

    public static String alias = "There's no aliases.";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "welcome")) {

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

                    if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_CHANNEL)) {

                        File a = new File("Database/Welcome/" + event.getGuild().getId());
                        File aa = new File("Database/Welcome/" + event.getGuild().getId() + "/Channel");

                        switch (args[1].toLowerCase()) {
                            case "on":
                                if (args.length == 2) {
                                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                                    event.getChannel().sendMessage(text).queue();
                                } else {
                                    if (a.exists() && aa.exists()) {
                                        File[] b = a.listFiles();
                                        assert b != null;

                                        TextChannel ch = event.getMessage().getMentionedChannels().get(0);

                                        if (!ch.getGuild().getSelfMember().getPermissions(ch).contains(Permission.MESSAGE_WRITE)) {
                                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("bot_permissions_false").getAsString();
                                            event.getChannel().sendMessage(text).queue();
                                        } else {

                                            File[] kanaly = aa.listFiles();
                                            assert kanaly != null;
                                            if (kanaly.length > 0) {
                                                for (File file : kanaly) {
                                                    Files.delete(file.toPath());
                                                }
                                            }

                                            Files.createFile(new File(aa + "/" + ch.getId()).toPath());

                                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("welcome_enabled").getAsString();
                                            event.getChannel().sendMessage(text).queue();
                                        }
                                    } else {
                                        Files.createDirectory(a.toPath());
                                        Files.createDirectory(aa.toPath());

                                        TextChannel ch = event.getMessage().getMentionedChannels().get(0);
                                        Files.createFile(new File(aa + "/" + ch.getId()).toPath());

                                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("welcome_enabled").getAsString();
                                        event.getChannel().sendMessage(text).queue();
                                    }
                                }
                                break;

                            case "off":
                                if (args.length == 3) {
                                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                                    event.getChannel().sendMessage(text).queue();
                                } else {
                                    if (a.exists() && aa.exists()) {
                                        File[] kanaly = aa.listFiles();
                                        assert kanaly != null;
                                        Files.delete(kanaly[0].toPath());
                                        Files.delete(aa.toPath());

                                        Files.delete(a.toPath());

                                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("welcome_disabled").getAsString();
                                        event.getChannel().sendMessage(text).queue();
                                    } else {
                                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("welcome_err").getAsString();
                                        event.getChannel().sendMessage(text).queue();
                                    }
                                }
                                break;

                            default:
                                String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("invalid_parameter").getAsString();
                                event.getChannel().sendMessage("'" + args[1] + text).queue();
                                break;
                        }
                    } else {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("permissions_false").getAsString();
                        event.getChannel().sendMessage(text).queue();
                    }
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
