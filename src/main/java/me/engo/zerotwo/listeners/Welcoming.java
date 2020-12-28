package me.engo.zerotwo.listeners;

import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Welcoming extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        File a = new File("Database/Welcome/" + event.getGuild().getId());

        if (a.exists()){
            File aa = new File("Database/Welcome/" + event.getGuild().getId() + "/Channel");

            File[] channels = aa.listFiles();
            assert channels != null;
            String name = channels[0].getName();

            try {
                BufferedImage source = ImageIO.read(new File("assets/Wallpaper.png"));
                BufferedImage logo;

                if (!(event.getUser().getAvatarUrl() == null)){
                    logo = ImageIO.read(new URL(Objects.requireNonNull(event.getMember().getUser().getAvatarUrl())));
                } else {
                    logo = ImageIO.read(new URL(Objects.requireNonNull(event.getMember().getUser().getDefaultAvatarUrl())));
                }

                File f = new File("assets/Welcome.png");
                source.getGraphics().drawImage(logo, (int) (source.getWidth() / 2.7), (int) (source.getHeight() / 2.8), null);
                ImageIO.write(source, "png", f);

                Objects.requireNonNull(event.getGuild().getTextChannelById(name)).sendMessage("My darling " + event.getMember().getAsMention() + " just joined this server!").addFile(f).queue();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //@SuppressWarnings("deprecation")
    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        File a = new File("Database/Welcome/" + event.getGuild().getId());

        if (a.exists()){
            File aa = new File("Database/Welcome/" + event.getGuild().getId() + "/Channel");

            File[] channels = aa.listFiles();
            assert channels != null;
            String name = channels[0].getName();

            Objects.requireNonNull(event.getGuild().getTextChannelById(name)).sendMessage("My darling **" + Objects.requireNonNull(event.getMember()).getUser().getName() + "** just left this server. Bye!").queue();
        }
    }

}
