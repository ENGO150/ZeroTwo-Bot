package me.engo.zerotwo.listeners;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Objects;

public class Update extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getChannel().getId().equalsIgnoreCase("622351337606348811")){
            File a = new File("Database/Updates/");
            int i = Objects.requireNonNull(a.listFiles()).length;

            String[] files = new String[i];

            File[] file = a.listFiles();

            for (int i1 = 0; i1 < i; i1++){
                assert file != null;
                File[] file1 = file[i1].listFiles();
                assert file1 != null;
                File[] file2 = file1[0].listFiles();
                assert file2 != null;
                files[i1] = file2[0].getName();
            }

            for (int i1 = 0; i1 < i; i1++){
                TextChannel tc = event.getJDA().getTextChannelById(files[i1]);
                if (tc != null) {
                    tc.sendMessage(event.getMessage()).queue();
                }
            }
        }
    }
}
