package me.engo.zerotwo.listeners;

import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Dm extends ListenerAdapter {

    public static String alias = "This isn't command.";

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Config c = new Config();
        File image = new File("assets/Bouncing.gif");
        event.getAuthor().openPrivateChannel().queue((privateChannel -> privateChannel.sendMessage("Hello, I am ZeroTwo.\nYou can use my commands only on guilds.\nMy prefix is `" + c.prefix + "`.\nDeveloped by ENGO_150#4264").addFile(image).queue()));
    }
}
