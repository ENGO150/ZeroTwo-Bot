package me.engo.zerotwo.listeners;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Objects;

public class Filtering extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        File database = new File("Database/Filter/" + event.getGuild().getId());

        File[] words = database.listFiles();

        if (database.exists()){
            if (Objects.requireNonNull(event.getMember()).hasPermission(Permission.MESSAGE_MANAGE)) return;
            assert words != null;
            if (words.length != 0) {
                String message = event.getMessage().getContentRaw();
                for (File word : words) {
                    String character = ".";
                    if (!word.getName().contains(character) && message.contains(character)){
                        message = message.replace(character, "");
                    }

                    if (message.toLowerCase().contains(word.getName())) {
                        event.getMessage().delete().queue();
                        break;
                    }
                }
            }
        }
    }
}
