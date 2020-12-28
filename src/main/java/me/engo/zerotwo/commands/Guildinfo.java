package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.io.File;
import java.util.Objects;

public class Guildinfo extends ListenerAdapter {
	
	public static String alias = "gi";
	public String month;
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "guildinfo") ||  messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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
				Guild g = Context.getGuild();

				if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("january")) {
					month = "January";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("february")) {
					month = "February";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("march")) {
					month = "March";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("april")) {
					month = "April";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("may")) {
					month = "May";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("june")) {
					month = "June";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("july")) {
					month = "July";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("august")) {
					month = "August";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("september")) {
					month = "September";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("october")) {
					month = "October";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("november")) {
					month = "November";
				} else if (g.getTimeCreated().getMonth().toString().toLowerCase().equals("december")) {
					month = "December";
				} else {
					month = g.getTimeCreated().getMonth().toString().toLowerCase();
				}

				/*List<ListedEmote> emotes = Context.getGuild().retrieveEmotes().complete();
				StringBuilder a = new StringBuilder();

				for (Emote e : emotes){
					a.append(e.getAsMention());
				}*/

				EmbedBuilder em = new EmbedBuilder();
				em.setTitle("Guild Info");
				em.addField("Name", g.getName(), true);
				em.addField("Owner", Objects.requireNonNull(g.getOwner()).getAsMention(), true);
				em.addField("Region", g.getRegion().getName(), true);
				em.addField("Members", String.valueOf(g.getMembers().size()), true);
				em.addField("Channels", String.valueOf(g.getChannels().size()), true);
				em.addField("Roles", String.valueOf(g.getRoles().size()), true);
				em.addField("Boosts", String.valueOf(g.getBoostCount()), true);
				em.addField("Created", g.getTimeCreated().getDayOfMonth() + "." + month + "." + g.getTimeCreated().getYear(), true);
				em.addField("ID", g.getId(), false);
				//em.addField("Emojis", a.toString(), false);
				em.setImage(g.getIconUrl());
				em.setColor(new Color(c.Color));
				em.setFooter(c.footer1, c.footer2);

				Context.getChannel().sendMessage(em.build()).queue();
			} else {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			}
		}
	}

}
