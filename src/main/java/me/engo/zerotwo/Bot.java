package me.engo.zerotwo;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

import me.engo.zerotwo.commands.*;
import me.engo.zerotwo.listeners.Automatically;
import me.engo.zerotwo.listeners.Dm;
import me.engo.zerotwo.listeners.Filtering;
import me.engo.zerotwo.listeners.Helping;
import me.engo.zerotwo.listeners.Mention;
import me.engo.zerotwo.listeners.Rockpaperscissors;
import me.engo.zerotwo.listeners.Update;
import me.engo.zerotwo.listeners.Welcoming;
import me.engo.zerotwo.reactions.America;
import me.engo.zerotwo.reactions.Creeper;
import me.engo.zerotwo.reactions.Eo;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {

    public static String secret = null;
    public static JDA jda;
    public static String token_weather;
    public static String token_url;
    public static String token_youtube;

    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {

        File token_file = new File("token");
        if (!token_file.exists()){
            System.err.println("Token file not found.");
            Thread.sleep(5000);
            System.exit(0);
        }

        File api_file = new File("api");
        if (!api_file.exists()){
            System.err.println("API file not found.");
            Thread.sleep(5000);
            System.exit(0);
        }

        List<String> tokens = Files.readAllLines(token_file.toPath());
        List<String> apis = Files.readAllLines(api_file.toPath());

        if (tokens.isEmpty()){
            System.err.println("Token not found.");
            Thread.sleep(5000);
            System.exit(0);
        }

        if (apis.isEmpty()){
            System.err.println("Api not found.");
            Thread.sleep(5000);
            System.exit(0);
        }

        String token = tokens.get(0);
        token_weather = apis.get(0);
        token_url = apis.get(1);
        token_youtube = apis.get(2);

        jda = new JDABuilder(AccountType.BOT)
                .setToken(token)
                .setStatus(OnlineStatus.ONLINE)
                .build()
                .awaitReady();
                
        System.out.println("I am online!");

        //LISTENERS
        jda.addEventListener(new Automatically());
        	//jda.addEventListener(new Chatting());
        jda.addEventListener(new Dm());
        jda.addEventListener(new Filtering());
        jda.addEventListener(new Helping());
        jda.addEventListener(new Mention());
        jda.addEventListener(new Rockpaperscissors());
        jda.addEventListener(new Update());
        jda.addEventListener(new Welcoming());

        //REACTIONS
        jda.addEventListener(new America());
        jda.addEventListener(new Creeper());
        jda.addEventListener(new Eo());

        //COMMANDS
        jda.addEventListener(new Achievement());
        jda.addEventListener(new Alert());
        jda.addEventListener(new Avatar());
        jda.addEventListener(new Bad());
        jda.addEventListener(new Blowjob());
        jda.addEventListener(new Boobs());
        	//jda.addEventListener(new Botchat());
        jda.addEventListener(new Botinfo());
        jda.addEventListener(new Butt());
        jda.addEventListener(new Calling());
        jda.addEventListener(new Captcha());
        jda.addEventListener(new Count());
        jda.addEventListener(new Covid());
        jda.addEventListener(new Deepfry());
        jda.addEventListener(new Dice());
        jda.addEventListener(new Didyoumean());
        jda.addEventListener(new Die());
        jda.addEventListener(new Divide());
        jda.addEventListener(new Drake());
        jda.addEventListener(new Drunk());
        	//jda.addEventListener(new Event());
        jda.addEventListener(new Exit());
        jda.addEventListener(new Facts());
        jda.addEventListener(new Fakesay());
        jda.addEventListener(new Fakesaydm());
        jda.addEventListener(new Fakesaywebhook());
        jda.addEventListener(new Fight());
        jda.addEventListener(new Filter());
        jda.addEventListener(new Flip());
        jda.addEventListener(new Fluffy());
        jda.addEventListener(new Foot());
        jda.addEventListener(new Github());
        	//jda.addEventListener(new Giveaway());
        jda.addEventListener(new Guildinfo());
        jda.addEventListener(new Guildinvite());
        jda.addEventListener(new Guildlist());
        jda.addEventListener(new Guilds());
        jda.addEventListener(new Hack());
        jda.addEventListener(new Help());
        jda.addEventListener(new Hentai());
        jda.addEventListener(new Hug());
        jda.addEventListener(new Challenge());
        jda.addEventListener(new Channelcreate());
        jda.addEventListener(new Channeldelete());
        jda.addEventListener(new Checkfor());
        jda.addEventListener(new Invite());
        jda.addEventListener(new Kick());
        jda.addEventListener(new Kill());
        jda.addEventListener(new Kiss());
        jda.addEventListener(new Language());
        jda.addEventListener(new Laugh());
        jda.addEventListener(new Leave());
        jda.addEventListener(new Lick());
        jda.addEventListener(new Marry());
        jda.addEventListener(new Mcserver());
        jda.addEventListener(new Meme());
        jda.addEventListener(new Minesweeper());
        jda.addEventListener(new Minus());
        jda.addEventListener(new Moan());
        jda.addEventListener(new Module());
        jda.addEventListener(new Modules());
        jda.addEventListener(new Multiply());
        jda.addEventListener(new Neko());
        jda.addEventListener(new Nudes());
        jda.addEventListener(new Papasbattlefield());
        jda.addEventListener(new Pat());
        jda.addEventListener(new Pin());
        jda.addEventListener(new Ping());
        jda.addEventListener(new Play());
        jda.addEventListener(new Plus());
        jda.addEventListener(new Png());
        jda.addEventListener(new Pornhub());
        jda.addEventListener(new Prefix());
        jda.addEventListener(new Premium());
        jda.addEventListener(new Profile());
        jda.addEventListener(new Pussy());
        jda.addEventListener(new Qr());
        jda.addEventListener(new Radio());
        jda.addEventListener(new Reddit());
        jda.addEventListener(new Redeem());
        jda.addEventListener(new Reminder());
        jda.addEventListener(new Report());
        jda.addEventListener(new Restart());
        jda.addEventListener(new Rps());
        jda.addEventListener(new Say());
        jda.addEventListener(new Saydm());
        jda.addEventListener(new Scroll());
        jda.addEventListener(new Skin());
        jda.addEventListener(new Skip());
        jda.addEventListener(new Slap());
        jda.addEventListener(new Source());
        jda.addEventListener(new Spin());
        jda.addEventListener(new Stop());
        jda.addEventListener(new Support());
        jda.addEventListener(new Supreme());
        jda.addEventListener(new Tag());
        jda.addEventListener(new Test());
        jda.addEventListener(new Thigh());
        jda.addEventListener(new Trash());
        jda.addEventListener(new Updates());
        jda.addEventListener(new Uptime());
        jda.addEventListener(new Upvotes());
        jda.addEventListener(new Urlshortener());
        jda.addEventListener(new Userinfo());
        	//jda.addEventListener(new Vote());
        jda.addEventListener(new Voting());
        jda.addEventListener(new Warn());
        jda.addEventListener(new Weather());
        	//jda.addEventListener(new Websiteinfo());
        jda.addEventListener(new Welcome());
        jda.addEventListener(new Zerotwo());

        System.out.println("Classes added.");

        //SERVERS.setCount(Bot.token_3, jda.getGuilds().size());

        if (args.length > 0 && args[0].equalsIgnoreCase("gui")){
            new Chat();
        }

        Random rnd = new Random();
        int a;
        int b;
        int ce;

        char d;
        char e;

        int de;
        int ee;

        Config c = new Config();

        while (true) {
            jda.getPresence().setActivity(Activity.playing("ZeroTwo | " + c.prefix + "help"));
            Thread.sleep((rnd.nextInt(30) + 30) * 1000);
            jda.getPresence().setActivity(Activity.playing("with darlings <3"));
            Thread.sleep((rnd.nextInt(30) + 30) * 1000);
            jda.getPresence().setActivity(Activity.watching("to " + jda.getGuilds().size() + " servers"));
            Thread.sleep((rnd.nextInt(30) + 30) * 1000);
            jda.getPresence().setActivity(Activity.listening("to " + jda.getUsers().size() + " users"));
            Thread.sleep((rnd.nextInt(30) + 30) * 1000);
            jda.getPresence().setActivity(Activity.playing("Developed by ENGO_150#4264"));
            Thread.sleep((rnd.nextInt(30) + 30) * 1000);

            a = rnd.nextInt(9);
            b = rnd.nextInt(9);
            ce = rnd.nextInt(9);

            de = rnd.nextInt(25) + 65;
            ee = rnd.nextInt(25) + 65;

            d = (char) de;
            e = (char) ee;

            secret = String.valueOf(a) + d + b + e + ce;

            jda.getPresence().setActivity(Activity.streaming("Redeem this: " + secret, "https:/discord.io/ekse"));
            Thread.sleep((rnd.nextInt(3) + 7) * 1000);
        }

    }

}
