package me.engo.zerotwo.handlers;

import com.google.gson.JsonParser;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.engo.zerotwo.Bot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.managers.AudioManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Music {

    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    public static String language;

    public static Member member = null;
    public static String id = null;
    public static String id2 = null;

    public String music1 = JsonParser.parseReader(new FileReader("languages/" + Music.language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_adding").getAsString();
    public String music2 = JsonParser.parseReader(new FileReader("languages/" + Music.language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_first").getAsString();
    public String music3 = JsonParser.parseReader(new FileReader("languages/" + Music.language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_nothing").getAsString();
    public String music4 = JsonParser.parseReader(new FileReader("languages/" + Music.language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_failed").getAsString();
    public String music5 = JsonParser.parseReader(new FileReader("languages/" + Music.language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_skipped").getAsString();

    public Music() throws FileNotFoundException {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(final TextChannel channel, final String trackUrl) throws FileNotFoundException {

        if (!Objects.requireNonNull(member.getVoiceState()).inVoiceChannel()){
            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_false").getAsString();
            Objects.requireNonNull(Bot.jda.getTextChannelById(id)).sendMessage(text).queue();
            return;
        }

        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                if (trackUrl.startsWith("http://icecast") || trackUrl.equalsIgnoreCase("https://ice.actve.net/fm-evropa2-128") || trackUrl.equalsIgnoreCase("http://ice.actve.net/fm-frekvence1-128") || trackUrl.startsWith("https://ice.abradio.cz/")){
                    switch (trackUrl){
                        case "http://icecast2.play.cz/radiobeat128.mp3":
                            channel.sendMessage(music1 + "Radio Beat").queue();
                            break;

                        case "http://icecast4.play.cz/kiss128.mp3":
                            channel.sendMessage(music1 + "Radio Kiss").queue();
                            break;

                        case "http://ice.actve.net/fm-frekvence1-128":
                            channel.sendMessage(music1 + "Frekvence 1").queue();
                            break;

                        case "https://ice.actve.net/fm-evropa2-128":
                            channel.sendMessage(music1 + "Evropa 2").queue();
                            break;

                        case "http://icecast5.play.cz:8000/cro1-32.mp3":
                            channel.sendMessage(music1 + "Radiožurnál").queue();
                            break;

                        case "http://icecast5.play.cz:8000/impuls128.mp3":
                            channel.sendMessage(music1 + "Radio Impuls").queue();
                            break;

                        case "https://ice.abradio.cz/blanikfm128.mp3":
                            channel.sendMessage(music1 + "Radio Blaník").queue();
                            break;

                        case "http://icecast1.play.cz/Proglas96aac":
                            channel.sendMessage(music1 + "Proglas").queue();
                            break;

                        case "https://ice.abradio.cz/sumava128.mp3":
                            channel.sendMessage(music1 + "Rock Radio").queue();
                            break;

                        case "http://icecast5.play.cz:8000/expres128mp3":
                            channel.sendMessage(music1 + "Expres Radio").queue();
                            break;

                        default:
                            System.out.println(trackUrl);
                            break;
                    }
                } else if (trackUrl.startsWith("https://raw.githubusercontent.com/ZeroTwobt/")) {
                    try {
                        String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("moan").getAsString();
                        channel.sendMessage(text).queue();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    long length = track.getDuration();

                    if (length > 600000 && !Objects.requireNonNull(channel.getGuild().getMemberById(id2)).hasPermission(Permission.ADMINISTRATOR)) {
                        try {
                            String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_long").getAsString();
                            channel.sendMessage(text).queue();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    }

                    if (!track.getInfo().isStream) {

                        long hour = TimeUnit.MILLISECONDS.toHours(length);
                        long minute = TimeUnit.MILLISECONDS.toMinutes(length);
                        long second = (length / 1000) % 60;

                        channel.sendMessage(music1 + track.getInfo().title + " [" + hour + "h:" + minute + "m:" + second + "s]").queue(message -> {
                            if (trackUrl.equalsIgnoreCase("https://www.youtube.com/watch?v=Jxbu80okI_U")){
                                message.addReaction("\uD83D\uDDFB").queue();
                                message.addReaction("\uD83D\uDCC4").queue();
                                message.addReaction("✂").queue();
                            } else if (trackUrl.equalsIgnoreCase("https://www.youtube.com/watch?v=TL470fJMi7w")){
                                message.addReaction("➡").queue();
                                message.addReaction("\uD83C\uDDF8").queue();
                                message.addReaction("\uD83C\uDDEE").queue();
                                message.addReaction("\uD83C\uDDF2").queue();
                                message.addReaction("\uD83C\uDDF5").queue();
                                message.addReaction("⬅").queue();
                            }
                        });
                    } else {
                        channel.sendMessage(music1 + track.getInfo().title).queue();
                    }
                }

                try {
                    play(channel.getGuild(), musicManager, track);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage(music1 + firstTrack.getInfo().title +  music2 + playlist.getName() + ")").queue();

                try {
                    play(channel.getGuild(), musicManager, firstTrack);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void noMatches() {
                try {
                    String urlko = trackUrl.replace(" ", "%20");
                    JSONTokener tokener = new JSONTokener(new URL("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=20&q=" + urlko + "&type=video&key=" + Bot.token_youtube).openStream());
                    JSONObject obj = new JSONObject(tokener);

                    JSONArray array = obj.getJSONArray("items");
                    JSONObject first = (JSONObject) array.get(0);
                    JSONObject obj_2 = first.getJSONObject("snippet");
                    JSONObject thumbnails = obj_2.getJSONObject("thumbnails");
                    JSONObject default_ =  thumbnails.getJSONObject("default");
                    String url_ = default_.getString("url");

                    url_ = url_.substring(23);
                    url_ = url_.substring(0, url_.length() - 10);
                    url_ = "https://www.youtube.com/watch?v=" + url_;

                    loadAndPlay(channel, url_);
                } catch (IOException e){
                    channel.sendMessage(music3 + trackUrl).queue();
                }
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage(music4 + exception.getMessage()).queue();
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) throws FileNotFoundException {
        connectToFirstVoiceChannel(guild.getAudioManager());

        musicManager.scheduler.queue(track);

        track.setUserData(id2);
    }

    public void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();
        channel.sendMessage(music5).queue();
    }

    @SuppressWarnings("deprecation")
	private static void connectToFirstVoiceChannel(AudioManager audioManager) throws FileNotFoundException {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {

            if (Objects.requireNonNull(member.getVoiceState()).inVoiceChannel()){
                audioManager.openAudioConnection(member.getVoiceState().getChannel());
            } else {

                String text = JsonParser.parseReader(new FileReader("languages/" + language + ".json")).getAsJsonObject().get("logs").getAsJsonObject().get("music_false").getAsString();
                Objects.requireNonNull(Bot.jda.getTextChannelById(id)).sendMessage(text).queue();

            }
        }
    }
}
