package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Fight extends ListenerAdapter {
	
	public static String alias = "There's no aliases.";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentDisplay().split(" ");
		if (Context.getAuthor().isBot()) return;
		
		if (messageSent[0].equalsIgnoreCase(c.prefix + "fight")) {

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
			} else if (messageSent.length == 2) {
				try {
					User u = Context.getMessage().getMentionedUsers().get(0);

					if (u == null){
						String text = Translate.getTranslate(language, "basic_warnings", "low_parameters");
						Context.getChannel().sendMessage(text).queue();
						return;
					}

					Random rnd = new Random();
					int rndm = rnd.nextInt(100);
					int wait = rnd.nextInt(3);

					EmbedBuilder em = new EmbedBuilder();
					em.setDescription(Context.getAuthor().getName() + " fights with " + u.getName());
					em.setImage("https://media2.giphy.com/media/ZpwP4FoHKE6pq/giphy.gif");
					em.setFooter(c.footer1, c.footer2);
					em.setColor(c.Color);

					EmbedBuilder e = new EmbedBuilder();
					if (rndm <= 50) {
						e.setDescription(Context.getAuthor().getName() + " won");
					}
					if (rndm > 50) {
						e.setDescription(u.getName() + " won");
					}
					e.setImage("https://66.media.tumblr.com/7dc827ce9d9895f8830a5522ce15dd64/tumblr_p2wgprDjin1u86t2qo1_500.gif");
					e.setFooter(c.footer1, c.footer2);
					e.setColor(c.Color);

					Context.getChannel().sendMessage(em.build()).queue((fight1 -> {
						try {
							Thread.sleep((wait + 5) * 1000);
							fight1.editMessage(e.build()).queueAfter(wait + 5, TimeUnit.SECONDS);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				String text = Translate.getTranslate(language, "basic_warnings", "high_parameters");
				Context.getChannel().sendMessage(text).queue();
			}
		}
	}

}
