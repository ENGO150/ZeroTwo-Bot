package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Module extends ListenerAdapter {
	
	public static String alias = "cmds";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "module") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

            try {
                String language;
                File languages = new File("Database/Language/" + Context.getAuthor().getId());
                if (languages.exists()) {
                    File[] languages_ = languages.listFiles();
                    assert languages_ != null;
                    language = languages_[0].getName();
                } else {
                    language = "english_en";
                }

                if (messageSent.length < 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                } else if (messageSent.length > 2) {
                    String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("high_parameters").getAsString();
                    Context.getChannel().sendMessage(text).queue();
                } else {
                    EmbedBuilder em = new EmbedBuilder();
                    String module_select = messageSent[1].toLowerCase();
                    switch (module_select) {
                        case "help":
                            em.setTitle("Help Module");
                            em.addField("Help", "`.zthelp (command)`", false);
                            em.addField("Support Server", "`.ztsupport`", false);
                            em.addField("GitHub", "`.ztgithub`", false);
                            em.addField("Premium", "`.ztpremium`", false);
                            em.addField("Invite Me", "`.ztinvite`", false);
                            	//em.addField("Vote For Me", "`.ztvote (website)`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "settings":
                            em.setTitle("Settings Module");
                            em.addField("List of Prefixes", "`.ztprefix`", false);
                            em.addField("List of Languages", "`.ztlanguage`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "games":
                            em.setTitle("Games Module");
                            em.addField("Minesweeper", "`.ztminesweeper`", false);
                            em.addField("Papa's Battlefield", "`.ztpapasbattlefield`", false);
                            em.addField("Spin Wheel of Fortune", "`.ztspin`", false);
                            em.addField("Show a Minecraft Skin", "`.ztskin [nick]`", false);
                            em.addField("Show a Minecraft Server", "`.ztmcserver [serverAddress] (serverName) (serverPort)`", false);
                            em.addField("Fight with User", "`.ztfight [user]`", false);
                            em.addField("Rock Paper Scissors", "`.ztrps [r/p/s]`", false);
                            em.addField("Roll a Dice", "`.ztdice`", false);
                            em.addField("Flip a Coin", "`.ztflip [head/tail]`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "random":
                            em.setTitle("Random Module");
                            em.addField("Avatar", "`.ztavatar (user)`", false);
                            em.addField("Shorten Url", "`.ztshorten [longUrl] [name]`", false);
                            em.addField("Start a Voting", "`.ztvoting [text]`", false);
                            em.addField("Create an Invite", "`.ztguildinvite`", false);
                            em.addField("Meme", "`.ztmeme`", false);
                            em.addField("Reddit", "`.ztreddit [subreddit]`", false);
                            em.addField("My Upvotes", "`.ztupvotes`", false);
                            em.addField("Reminder", "`.ztreminder [time] (s/m/h/d)`", false);
                            em.addField("Get a Source of Command", "`.ztsource [command]`", false);
                            em.addField("Profile", "`.ztprofile (user)`", false);
                            em.addField("Report a Bug", "`.ztreport [bug]`", false);
                            em.addField("Check for User", "`.ztcheckfor [userId]`", false);
                            em.addField("'Hack' User", "`.zthack [user]`", false);
                            em.addField("Redeem", "`.ztredeem [code]`", false);

                            em.addField("ZeroTwo", "`.ztzerotwo`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "moderation":
                            em.setTitle("Moderation Module");
                            em.addField("Create a Channel", "`.ztchannelcreate [text/voice] [channelName]`", false);
                            em.addField("Delete a Channel", "`.ztchanneldelete [channel]`", false);
                            em.addField("Warn a User", "`.ztwarn [user] [reason]`", false);
                            em.addField("Kick a User", "`.ztkick [user] (reason)`", false);
                            em.addField("Pin a Message", "`.ztpin [message]`", false);
                            em.addField("Kick Me", "`.ztleave`", false);
                            em.addField("Message Filter", "`.ztfilter [on/off/set/del] (word)`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "roleplay":
                            em.setTitle("Roleplay Module");
                            em.addField("Marry a User", "`.ztmarry [send/accept/decline/end] [user]`", false);
                            em.addField("Hug", "`.zthug [user]`", false);
                            em.addField("Kiss", "`.ztkiss [user]`", false);
                            em.addField("Lick", "`.ztlick [user]`", false);
                            em.addField("Kill", "`.ztkill [user]`", false);
                            em.addField("Pat", "`.ztpat [user]`", false);
                            em.addField("Slap", "`.ztslap [user]`", false);
                            em.addField("Laugh", "`.ztlaugh`", false);
                            em.addField("Die", "`.ztdie`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "fonts":
                            em.setTitle("Fonts Module");
                            em.addField("Supreme Font", "`.ztsupreme [text] (dark/red)`", false);
                            em.addField("Fluffy Font", "`.ztfluffy [text]`", false);
                            em.addField("PNG Font", "`.ztpng [text]`", false);
                            em.addField("Minecraft Achievement", "`.ztachievement [text]`", false);
                            em.addField("Minecraft Challenge", "`.ztachallenge [text]`", false);
                            em.addField("Facts Meme", "`.ztfacts [text]`", false);
                            em.addField("QR Code", "`.ztqr [text]`", false);
                            em.addField("Scroll Meme", "`.ztscroll [text]`", false);
                            em.addField("Facts Meme", "`.ztfacts [text]`", false);
                            em.addField("Calling Meme", "`.ztcalling [text]`", false);
                            em.addField("Pornhub Font", "`.ztpornhub [text1] ; [text2]`", false);
                            em.addField("Drake Meme", "`.ztdrake [text1] ; [text2]`", false);
                            em.addField("Did you Mean", "`.ztdidyoumean [text1] ; [text2]`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "math":
                            em.setTitle("Math Module");
                            em.addField("Plus", "`.ztplus [num1] [num2]`", false);
                            em.addField("Minus", "`.ztminus [num1] [num2]`", false);
                            em.addField("Multiply", "`.ztmultiply [num1] [num2]`", false);
                            em.addField("Divide", "`.ztdivide [num1] [num2]`", false);
                            em.addField("Count from Number to Number", "`.ztcount [num1] [num2]`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "nsfw":
                            em.setTitle("NSFW Module");
                            em.addField("Hentai", "`.zthentai (user)`", false);
                            em.addField("Foot", "`.ztfoot`", false);
                            em.addField("Boobs", "`.ztboobs (user)`", false);
                            em.addField("Butt", "`.ztbutt (user)`", false);
                            em.addField("Blowjob", "`.ztblowjob (user)`", false);
                            em.addField("Pussy", "`.ztpussy`", false);
                            em.addField("Nudes", "`.ztnudes`", false);
                            em.addField("Neko", "`.ztneko`", false);
                            em.addField("Thigh", "`.ztthigh`", false);

                            em.addField("Moan", "`.ztmoan`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "say":
                            em.setTitle("Say Module");
                            em.addField("Say to the Chat", "`.ztsay [text]`", false);
                            em.addField("Say to User", "`.ztsaydm [user] [text]`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        /*case "supportserver":
                            em.setTitle("SupportServer Module");
                            em.addField("Giveaways", "`.ztgiveaway`", false);
                            em.addField("Events", "`.ztevent`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;*/

                        case "premium":
                            em.setTitle("Premium Module");
                            em.addField("Fake Say", "`.ztfakesay [from] [text]`", false);
                            em.addField("Webhook Fake Say", "`.ztfakesaywebhook [from] [text]`", false);
                            em.addField("Fake Say to DM", "`.ztfakesaydm [from] [to] [text]`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "developer":
                            em.setTitle("Developer Module");
                            em.addField("Test Cmd", "`.zttest`", false);
                            em.addField("Restart Bot", "`.ztrestart`", false);
                            em.addField("Stop Bot", "`.ztexit`", false);
                            em.addField("List of My Guilds", "`.ztguildlist (num)`", false);
                            em.addField("Add / Remove Premium for User", "`.ztpremium [user]`", false);
                            em.addField("Send Alert to All Guilds", "`.ztalert [alertMessage]`", false);
                            em.addField("Get Invite to Guild With Specific User", "`.ztguilds [userId]`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "filters":
                            em.setTitle("Filters Module");
                            em.addField("Deepfry", "`.ztdeepfry (user)`", false);
                            em.addField("Drunk", "`.ztdrunk (user)`", false);
                            em.addField("Bad", "`.ztbad (user)`", false);
                            em.addField("Trash Meme", "`.zttrash [user]`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "logs":
                            em.setTitle("Logs Module");
                            em.addField("Manage Welcome Messages", "`.ztwelcome [on/off] (channel)`", false);
                            em.addField("Manage Update Messages", "`.ztupdates [on/off] (channel)`", false);
                            	//em.addField("Manage Botchat", "`.ztbotchat [on/off] (channel)`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "music":
                            em.setTitle("Music Module");
                            em.addField("Play a Song", "`.ztplay [songName/songUrl]`", false);
                            em.addField("Radio", "`.ztradio (num)`", false);
                            em.addField("Stop", "`.ztstop`", false);
                            em.addField("Skip", "`.ztskip`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "news":
                            em.setTitle("News Module");
                            em.addField("Covid-19 Stats", "`.ztcovid`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        case "info":
                            em.setTitle("Info Module");
                            em.addField("Weather", "`.ztweather [city]`", false);
                            em.addField("My Ping", "`.ztping`", false);
                            em.addField("Uptime", "`.ztuptime`", false);
                            em.addField("My Guilds", "`.ztguilds`", false);
                            em.addField("User's Tag", "`.zttag [user]`", false);
                            em.addField("Guild Info", "`.ztguildinfo`", false);
                            em.addField("User Info", "`.ztuserinfo (user)`", false);
                            em.addField("Bot Info", "`.ztbotinfo [bot]`", false);
                            	//em.addField("Website Info", "`.ztwebsiteinfo [url]`", false);

                            em.setFooter(c.footer1, c.footer2);
                            em.setColor(new Color(c.Color));
                            Context.getChannel().sendMessage(em.build()).queue();
                            break;

                        default:
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("advanced_warnings").getAsJsonObject().get("invalid_module").getAsString();
                            Context.getChannel().sendMessage(text).queue();
                            break;
                    }
                }
            } catch (FileNotFoundException e){
                e.printStackTrace();
            }
		}
	}

}
