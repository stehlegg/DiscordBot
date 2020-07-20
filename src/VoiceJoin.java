import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.HashMap;
import java.util.Map;

public class VoiceJoin extends ListenerAdapter {
	private static Guild guild;

	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event)  {
		String id = event.getMember().getId();
		Guild guild = event.getGuild();
		this.guild = guild;
		if(config.isDa(id) && !guild.getAudioManager().isConnected())    {
			String TrackUrl = config.getValue(id);
			//String TrackUrl = "https://www.youtube.com/watch?v=L-dNSnANKAI";
			skipTrack(guild);
			loadAndPlay(guild, TrackUrl, event.getChannelJoined());
		}
	}

	private final AudioPlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;

	public VoiceJoin()  {
		this.musicManagers = new HashMap<>();

		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerLocalSource(playerManager);
		AudioSourceManagers.registerRemoteSources(playerManager);
	}

	private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		GuildMusicManager musicManager = musicManagers.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			musicManagers.put(guildId, musicManager);
		}
		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}
	private void loadAndPlay(final Guild guild, final String trackUrl, final VoiceChannel vc) {
		final GuildMusicManager musicManager = getGuildAudioPlayer(guild);

		playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				try {
					play(guild, musicManager, track, vc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();

				if (firstTrack == null) {
					firstTrack = playlist.getTracks().get(0);
				}

				try {
					play(guild, musicManager, firstTrack, vc);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void noMatches() {
				System.out.println("no matches");
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				System.out.println("load failed");
			}
		});
	}

	private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track, VoiceChannel vc) throws InterruptedException {
		connectToFirstVoiceChannel(guild.getAudioManager(), vc);

		musicManager.scheduler.queue(track);
	}

	private void skipTrack(Guild guild) {
		GuildMusicManager musicManager = getGuildAudioPlayer(guild);
		musicManager.scheduler.nextTrack();
	}

	private static void connectToFirstVoiceChannel(AudioManager audioManager, VoiceChannel vc) throws InterruptedException {
		if (!audioManager.isConnected()) {
			audioManager.openAudioConnection(vc);
		}
	}

	public static void disconnect() {
		guild.getAudioManager().closeAudioConnection();
	}
}
