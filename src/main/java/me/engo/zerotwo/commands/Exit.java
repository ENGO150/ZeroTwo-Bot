package me.engo.zerotwo.commands;

import me.engo.zerotwo.Config;
import me.engo.zerotwo.handlers.Translate;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Exit extends ListenerAdapter {

	public static String alias = "shutdown";

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent Context) {
		Config c = new Config();
		String[] messageSent = Context.getMessage().getContentRaw().split(" ");
		if (Context.getAuthor().isBot()) return;

		if (messageSent[0].equalsIgnoreCase(c.prefix + "exit") ||messageSent[0].equalsIgnoreCase(c.prefix + alias)) {

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

				if (Context.getAuthor().getId().equals("634385503956893737")) {
					Context.getChannel().sendMessage("OK").queue();

					//REMINDER
					File f = new File("Cooldown/Reminder");
					File[] files = f.listFiles();
					assert files != null;
					assert files.length != 0;
						for (File file : files) {
							User u = Context.getJDA().getUserById(file.getName());
							assert u != null;
							System.out.println(u.getAsTag());
							u.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Your reminder was stopped, because I am now shutting down.").queue());
							Files.delete(Paths.get(file.getPath()));
						}

					Context.getJDA().shutdown();

					System.exit(0);
				} else {
					String text = Translate.getTranslate(language, "basic_warnings", "developer_false");
					Context.getChannel().sendMessage(text).queue();
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
}
