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

public class Butt extends ListenerAdapter {
	
public static String alias = "ass";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "butt") || messageSent[0].equalsIgnoreCase(c.prefix + "ass")) {

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

						Random rnd = new Random();
						int rndm = rnd.nextInt(10) - 1;
						if (rndm < 0){ rndm = rndm + 1; }

						String[] zt = new String[6];
						zt[0] = "https://cdn.discordapp.com/attachments/609425123291693056/668930229342961665/downloadfile.jpg";
						zt[1] = "https://cdn.discordapp.com/attachments/609425123291693056/668930228965343252/download.jpg";
						zt[2] = "https://cdn.discordapp.com/attachments/609425123291693056/668930228244054016/download-3.jpg";
						zt[3] = "https://cdn.discordapp.com/attachments/609425123291693056/692337455285927976/external-content.duckduckgo.com.jpg";
						zt[4] = "https://cdn.discordapp.com/attachments/609425123291693056/692188869600346203/327842_-_Darling_in_the_FranXX_Zero_Two.jpg";
						zt[5] = "https://cdn.discordapp.com/attachments/609425123291693056/692290438211698748/02-00-57-darling-in-the-franxx-76_01CGGJ0QH9MERNWV78K4FMWPKR.1024x0.jpg";

						EmbedBuilder em = new EmbedBuilder();

						if (messageSent.length == 2) {
							User m = Context.getMessage().getMentionedUsers().get(0);

							if (m == null){
								String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
								Context.getChannel().sendMessage(text).queue();
								return;
							}

							em.setDescription( Context.getAuthor().getName() + " fucks " + m.getName());
						}

						if (rndm >= 6){
							try {
								URL url = new URL("https://nekobot.xyz/api/image?type=hentai_anal");
								JSONTokener tokener = new JSONTokener(url.openStream());
								JSONObject obj = new JSONObject(tokener);
								String message = obj.getString("message");

								em.setImage(message);
							} catch (IOException ex){
								ex.printStackTrace();
							}
						} else {
							em.setImage(zt[rndm]);
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
