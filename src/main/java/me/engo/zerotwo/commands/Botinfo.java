package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;

public class Botinfo extends ListenerAdapter {
	
	public static String alias = "bi";
	public String month;
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "botinfo") || messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
				String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else if (messageSent.length > 2) {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			} else {
				User u = Context.getMessage().getMentionedUsers().get(0);

				if (u == null){
					String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
					Context.getChannel().sendMessage(text).queue();
					return;
				}

				if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("january")) {
					month = "January";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("february")) {
					month = "February";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("march")) {
					month = "March";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("april")) {
					month = "April";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("may")) {
					month = "May";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("june")) {
					month = "June";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("july")) {
					month = "July";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("august")) {
					month = "August";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("september")) {
					month = "September";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("october")) {
					month = "October";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("november")) {
					month = "November";
				} else if (u.getTimeCreated().getMonth().toString().toLowerCase().equals("december")) {
					month = "December";
				} else {
					month = u.getTimeCreated().getMonth().toString().toLowerCase();
				}

				if (u.isBot()) {
					EmbedBuilder em = new EmbedBuilder();
					em.setTitle("User Info");
					em.addField("Name", u.getName(), true);
					em.addField("Tag", u.getAsTag().replace(u.getName() + "#", ""), true);
					em.addField("ID", u.getId(), false);
					em.addField("Created", u.getTimeCreated().getDayOfMonth() + "." + month + "." + u.getTimeCreated().getYear(), false);
					em.setThumbnail(u.getAvatarUrl());
					em.setColor(new Color(c.Color));
					em.setFooter(c.footer1, c.footer2);
					Context.getChannel().sendMessage(em.build()).queue();
				} else {
					String text = Translate.getTranslate(language, "advanced_warnings", "bot_false");
					Context.getChannel().sendMessage(text + c.prefix + "userinfo`").queue();
				}
			}
		}
	}
}
