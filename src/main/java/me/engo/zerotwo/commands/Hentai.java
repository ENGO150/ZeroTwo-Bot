package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONTokener;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Hentai extends ListenerAdapter {
	
public static String alias = "porn";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "hentai") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

			String language;
			File languages = new File("Database/Language/" + Context.getAuthor().getId());
			if (languages.exists()) {
				File[] languages_ = languages.listFiles();
				assert languages_ != null;
				language = languages_[0].getName();
			} else {
				language = "english_en";
			}

			if (messageSent.length < 3) {

				if (c.nsfw) {
					if (Context.getChannel().isNSFW()) {

						EmbedBuilder em = new EmbedBuilder();
						Random rnd = new Random();
						int rndm = rnd.nextInt(65);

						if (messageSent.length == 2) {
							User m = Context.getMessage().getMentionedUsers().get(0);

							if (m == null){
								String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
								Context.getChannel().sendMessage(text).queue();
								return;
							}

							em.setDescription( Context.getAuthor().getName() + " fucks " + m.getName());
						}

						if (rndm < 42) {
							em.setImage("http://commentseduire.net/wp-content/uploads/2017/06/hentai-gif-" + rndm + ".gif");
						} else {
							int rndm_2 = rnd.nextInt(8) - 1;
							if (rndm_2 < 0){ rndm_2 = rndm_2 + 1; }
							String[] zt = new String[4];
							zt[0] = "https://cdn.discordapp.com/attachments/609425123291693056/670568512514949120/1b8.png";
							zt[1] = "https://cdn.discordapp.com/attachments/609425123291693056/670026192165928960/jeanne_d_arc_jeanne_d_arc_and_lahmu_fate_and_1_more_drawn_by_hikichi_sakuya__sample-8cf5b3f85771f730.jpg";
							zt[2] = "https://cdn.discordapp.com/attachments/609425123291693056/688997725424975883/image0.gif";
							zt[3] = "https://cdn.discordapp.com/attachments/609425123291693056/688997411003170846/image0.gif";

							if (rndm_2 >= 4){
								try {
								URL url = new URL("https://nekobot.xyz/api/image?type=hentai");
								JSONTokener tokener = new JSONTokener(url.openStream());
								JSONObject obj = new JSONObject(tokener);
								String message = obj.getString("message");

								em.setImage(message);
								} catch (IOException ex){
									ex.printStackTrace();
								}
							} else {
								em.setImage(zt[rndm_2]);
							}
						}
						em.setFooter(c.footer1, c.footer2);
						em.setColor(new Color(c.Color));

						Context.getChannel().sendMessage(em.build()).queue();

					} else {
						String text = Translate.getTranslate(language, "basic_warnings", "nsfwchannel_false");
						Context.getChannel().sendMessage(text).queue();
					}
				} else {
					String text = Translate.getTranslate(language, "basic_warnings", "nsfwmodule_false");
					Context.getChannel().sendMessage(text).queue();
				}

			} else {

				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");

				Context.getChannel().sendMessage(text).queue();

			}
		}
	}
}
