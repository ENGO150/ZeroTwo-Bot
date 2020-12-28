package me.engo.zerotwo.commands;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

import com.google.gson.JsonParser;
import me.engo.zerotwo.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Pat extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "pat")) {

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
					if (c.roleplay) {
						Random rnd = new Random();
						int rndm = rnd.nextInt(3) - 1;
						if (rndm < 0){ rndm = rndm + 1; }

						String[] zt = new String[3];
						zt[0] = "https://cdn.zerotwo.dev/PAT/91d42571-417b-4130-98b7-c5e653ea6cc4.gif";
						zt[1] = "https://cdn.zerotwo.dev/PAT/d6f2894c-a59c-4f1c-986e-1b0491130f31.gif";
						zt[2] = "https://cdn.zerotwo.dev/PAT/18eb4077-a133-4865-9c2d-e2c5e42b908e.gif";

						User u = Context.getMessage().getMentionedUsers().get(0);

						if (u == null){
							try {
								String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("low_parameters").getAsString();
								Context.getChannel().sendMessage(text).queue();
								return;
							} catch (FileNotFoundException ex) {
								ex.printStackTrace();
							}
						}

						assert u != null;

						EmbedBuilder em = new EmbedBuilder();
						em.setDescription(Context.getAuthor().getName() + " pats " + u.getName());
						em.setImage(zt[rndm]);
						em.setFooter(c.footer1, c.footer2);
						em.setColor(new Color(c.Color));
						Context.getChannel().sendMessage(em.build()).queue();
					} else {
						String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("basic_warnings").getAsJsonObject().get("roleplaymodule_false").getAsString();
						Context.getChannel().sendMessage(text).queue();
					}
				}
			} catch (FileNotFoundException e){
				e.printStackTrace();
			}
		}
	}

}
