package Events;

import Core.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pr0.Captcha;
import pr0.Inspect;
import pr0.Login;

import java.io.IOException;
import java.util.List;

public class GuildMessage extends ListenerAdapter {
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		Message msg = event.getMessage();
		String content = msg.getContentRaw();
		TextChannel ch = event.getMessage().getTextChannel();

		try {
			Config.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(Captcha.token != null && msg.getContentRaw().contains(Captcha.token)) {
			Captcha.capMsg = msg;
		}

		if(event.getMessage().getContentRaw().equalsIgnoreCase("captcha"))   {
			try {
				if(Captcha.capMsg != null)  {
					Captcha.capMsg.delete().queue();
				}
				if(!Login.loggedIn)  {
					Captcha.getCaptcha(ch);
				}
				msg.delete().queue();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(event.getMessage().getContentRaw().startsWith("!login")) {
			String[] values = event.getMessage().getContentRaw().split(" ");
			try {
				if (values[1] != null && Captcha.capMsg != null && !Login.loggedIn && values[1].length() == 5) {
					Captcha.capMsg.delete().queue();
					try {
						Log.pr0gramm.getLogger().info("Request pr0 login with valid arguments");
						pr0.Login.pr0Login(values[1]);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}   else if(Captcha.capMsg == null) {
					Log.pr0gramm.getLogger().error("Request pr0 login WITHOUT CAPTCHA");
				}   else if(values[1].length() != 5)   {
					Log.pr0gramm.getLogger().error("Request pr0 login with INVALID ARGUMENTS");
				}
			} catch (IndexOutOfBoundsException e)   {
				Log.pr0gramm.getLogger().error("Requested login without arguments");
			}
			msg.delete().queue();
		} else if(event.getMessage().getContentRaw().startsWith("!me")) {
			try {
				if(event.getMessage().getContentRaw().endsWith("!me"))  {
					if(event.getMember().getNickname() == null)    {
						pr0.GetNewest.inspectUser(event.getChannel(), event.getAuthor().getName(), event.getAuthor().getName());
					}   else    {
						pr0.GetNewest.inspectUser(event.getChannel(), event.getMember().getNickname(), event.getMember().getNickname());
					}
				}   else    {
					String arg = event.getMessage().getContentRaw().split(" ")[1];
					if(event.getMember().getNickname() == null)    {
						pr0.GetNewest.inspectUser(event.getChannel(), arg, event.getAuthor().getName());
					}   else    {
						pr0.GetNewest.inspectUser(event.getChannel(), arg, event.getMember().getNickname());
					}
				}
				msg.delete().queue();
			}   catch (IOException e)   {
				e.printStackTrace();
			}
		}  else if(msg.getContentRaw().startsWith("!log"))  {
			Log.LogFIle.getLogFile(ch);
			msg.delete().queue();
		} else if(!event.getGuild().getId().equals("690654418869420162") &&
				(content.startsWith("!")
				|| content.startsWith(">")
				|| content.startsWith("*")
				|| msg.getAuthor().getName().contains("Rythm"))) {
			if(content.startsWith("!delete"))    {       //Kommando zum Löschen mehrerer Nachricht
				String[] cmd = content.split(" ");
				int n = Integer.parseInt(cmd[1]);
				if(n > 50) {
					ch.sendMessage("Bruder mach nich diesen, ich kann nur 50 liften alla").queue();
				}   else {
					clear(ch, n);
				}
			}    else    {                               //Kriterien zum Löschen der Nachricht zutreffend
					delete(msg);
			}
		}
		if(!event.getAuthor().isBot() && msg.getContentRaw().contains("https://pr0gramm.com/"))  {
			try {
				Inspect.inspectMsg(event.getMessage());
				delete(event.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void clear(TextChannel ch, int n)   {
		new Thread(() -> {
			List<Message> msgs = ch.getHistory().retrievePast(n + 1).complete();
			ch.deleteMessages(msgs).complete();
		}).start();
	}

	public void delete(Message msg) {
		new Thread(() -> {
			int n = 2000;
			if(msg.getContentRaw().startsWith("!q"))    {
				n += 8000;
			} else if (msg.getContentRaw().contains("https://pr0")) {
				n = 10;
			}
			try {
				Thread.sleep(n);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			msg.delete().queue();
		}).start();
	}
}