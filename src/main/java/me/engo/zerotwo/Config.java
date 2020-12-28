package me.engo.zerotwo;

import net.dv8tion.jda.api.entities.User;

import java.util.Objects;

public class Config {
    private User u = Bot.jda.getUserById("634385503956893737");

    public String prefix = ".zt";

    public int Color = 0xff00ff;
    public String footer1 = "Bot developed by " + Objects.requireNonNull(u).getAsTag();
    public String footer2 = u.getAvatarUrl();

    public boolean nsfw = true;
    public boolean roleplay = true;
    public boolean moderation = true;

}
