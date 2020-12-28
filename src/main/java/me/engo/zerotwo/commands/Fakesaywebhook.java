package me.engo.zerotwo.commands;

import java.io.File;
import java.util.List;
import java.util.Objects;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.WebhookCluster;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.OkHttpClient;

public class Fakesaywebhook extends ListenerAdapter {

    public static String alias = "fsw";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase(c.prefix + "fakesaywebhook") || args[0].equalsIgnoreCase(c.prefix + alias)){

            String language;
            File languages = new File("Database/Language/" + event.getAuthor().getId());
            if (languages.exists()) {
                File[] languages_ = languages.listFiles();
                assert languages_ != null;
                language = languages_[0].getName();
            } else {
                language = "english_en";
            }
            File f = new File("Database/Premium/" + event.getAuthor().getId());

            if (f.exists()) {
                event.getMessage().delete().queue();

                if (args.length < 3) {
                    String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
                    event.getChannel().sendMessage(text).queue();
                } else {
                    int a = args.length;

                    String[] slova = new String[a];

                    System.arraycopy(args, 1, slova, 0, a - 1);

                    StringBuilder done = new StringBuilder();

                    for (int i = 1; i < slova.length - 1; i++) {
                        done.append(" ").append(slova[i]);
                    }

                    done = new StringBuilder(done.substring(1));
                    final String done_ = done.toString();

                    if (event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_WEBHOOKS)) {
                        List<Webhook> webhooks = event.getGuild().retrieveWebhooks().complete();
                        for (Webhook webhook : webhooks){
                            if (Objects.requireNonNull(webhook.getOwner()).getId().equalsIgnoreCase("619548121311215621")){
                                webhook.delete().queue();
                            }
                        }

                        User u = event.getMessage().getMentionedUsers().get(0);

                        event.getChannel().createWebhook("fakesaywebhook").queue(webhook -> {
                            final String url = webhook.getUrl();
                            WebhookClientBuilder builder = new WebhookClientBuilder(url);
                            builder.setThreadFactory((job) -> {
                                Thread thread = new Thread(job);
                                thread.setName("fsw");
                                thread.setDaemon(true);
                                return thread;
                            });
                            builder.setWait(true);
                            WebhookClient client = builder.build();

                            WebhookCluster cluster = new WebhookCluster(5);
                            cluster.setDefaultHttpClient(new OkHttpClient());
                            cluster.setDefaultDaemon(true);
                            cluster.addWebhooks(client);
                            cluster.close();

                            WebhookMessageBuilder builder_ = new WebhookMessageBuilder();
                            builder_.setUsername(u.getName());
                            builder_.setAvatarUrl(u.getAvatarUrl());
                            builder_.setContent(done_);
                            client.send(builder_.build());
                        });
                    }
                }
            } else {
                String text = Translate.getTranslate(language, "basic_warnings", "permissions_false");
                event.getChannel().sendMessage(text).queue();
            }
        }
    }
}
