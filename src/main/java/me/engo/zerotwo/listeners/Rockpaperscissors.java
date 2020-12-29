package me.engo.zerotwo.listeners;

import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Random;

public class Rockpaperscissors extends ListenerAdapter {

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {
        if (event.getReactionEmote().isEmoji()) {
            if (event.getReactionEmote().getEmoji().equals("\uD83D\uDDFB") || event.getReactionEmote().getEmoji().equals("\uD83D\uDCC4") || event.getReactionEmote().getEmoji().equals("âœ‚")) {
                event.getChannel().retrieveMessageById(event.getMessageIdLong()).queue(message -> {
                    String id = message.getAuthor().getId();
                    if (id.equals("668847135860719639")) {
                        if (event.getMember().getUser().isBot()) return;
                        if (message.getContentRaw().equalsIgnoreCase("Adding to queue LITTLE BIG - ROCK–PAPER–SCISSORS (Official Music Video) [0h:3m:18s]")){
                            final int i = new Random().nextInt(100);

                            if (i <= 50){
                                event.getChannel().sendMessage("Hey five-head, you won!").queue();
                            } else {
                                event.getChannel().sendMessage("Hey five-head, you lost!").queue();
                            }

                            message.delete().queue();
                        }
                    }
                });
            }
        }
    }

}
