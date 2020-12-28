package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;

public class Avatar extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "avatar")) {

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

				EmbedBuilder em = new EmbedBuilder();
				em.setImage(Context.getMessage().getAuthor().getAvatarUrl());
				em.setFooter(c.footer1, c.footer2);
				em.setColor(new Color(c.Color));

				Context.getChannel().sendMessage(em.build()).queue();

			} else if (messageSent.length == 2) {

				User u = Context.getMessage().getMentionedUsers().get(0);

				if (u == null){
					String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
					Context.getChannel().sendMessage(text).queue();
					return;
				}

				EmbedBuilder em = new EmbedBuilder();
				em.setImage(u.getAvatarUrl());
				em.setFooter(c.footer1, c.footer2);
				em.setColor(new Color(c.Color));

				Context.getChannel().sendMessage(em.build()).queue();

			} else {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");

				Context.getChannel().sendMessage(text).queue();
			}
		}
	}
}
