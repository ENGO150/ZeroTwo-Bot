package me.engo.zerotwo.listeners;

import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;

public class Mention extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Config c = new Config();
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (event.getAuthor().isBot()) return;

        if (args[0].equalsIgnoreCase("<@!668847135860719639>") && args.length < 2){
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_ATTACH_FILES)){
                File image = new File("assets/Bouncing.gif");
                event.getChannel().sendMessage("Hello, I am ZeroTwo.\nYou can use my commands only on guilds.\nMy prefix is `" + c.prefix + "`.\nDeveloped by ENGO_150#4264").addFile(image).queue();
            } else {
                event.getChannel().sendMessage("Hello, I am ZeroTwo.\nYou can use my commands only on guilds.\nMy prefix is `" + c.prefix + "`.\nDeveloped by ENGO_150#4264").queue();
            }
        }
    }
}
