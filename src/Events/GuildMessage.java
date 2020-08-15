package Events;

import Core.Config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import pr0.Captcha;
import pr0.Inspect;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class GuildMessage extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		Message msg = event.getMessage();
		String content = msg.getContentRaw();
		TextChannel ch = event.getMessage().getTextChannel();

		if(event.getMessage().getContentRaw().equalsIgnoreCase("captcha"))   {
			try {
				Captcha.getCaptcha(ch);
				handleDelete(event.getMessage());
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		else if(event.getMessage().getContentRaw().startsWith("!login")) {
			String[] values = event.getMessage().getContentRaw().split(" ");
			try {
				pr0.Login.pr0Login(values[1], event.getChannel());
				handleDelete(event.getMessage());

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		else if(event.getAuthor().isBot() && !event.getMessage().getAttachments().isEmpty()) {
			try {
				handleDelete(event.getMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			Config.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean notCmds;
		boolean notChs;

		String[] cmds = Config.getValue("dcmds").split(",");
		String[] chs = Config.getValue("mchannels").split(",");

		if(Config.getValue("dcmds").isEmpty())  {
			notCmds = false;
		}   else    {
			notCmds = Arrays.stream(cmds).anyMatch(msg.getContentRaw()::contains);
		}

		if(Config.getValue("mchannels").isEmpty())  {
			notChs = false;
		}   else    {
			notChs = Arrays.stream(chs).anyMatch(ch.getId()::equalsIgnoreCase);
		}

		if(content.startsWith("!")
				|| content.startsWith(">")
				|| content.startsWith("*")
				|| msg.getAuthor().getName().contains("Rythm")) {
			if(content.startsWith("!delete"))    {       //Kommando zum Löschen mehrerer Nachricht
				String[] cmd = content.split(" ");
				int n = Integer.parseInt(cmd[1]);
				if(n > 50) {
					ch.sendMessage("Bruder mach nich diesen, ich kann nur 50 liften alla").queue();
				}   else {
					clear(ch, n);
				}
			}   else if(content.startsWith("!login"))   {
				System.out.println("Versuche pr0 Login");
			} else  if(!notChs && !notCmds)  {                               //Kriterien zum Löschen der Nachricht zutreffend
				try {
					handleDelete(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		if(!event.getAuthor().isBot() && event.getMessage().getContentRaw().contains("https://pr0gramm.com/"))  {
			try {
				Inspect.inspectMsg(event.getMessage());
				handleDelete(event.getMessage());
			} catch (IOException | URISyntaxException | InterruptedException e) {
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

	public void handleDelete( Message msg) throws InterruptedException {
		delete(msg);
	}

	public void delete(Message msg) throws InterruptedException {
		int n = 2000;
		if(msg.getContentRaw().startsWith("!q"))    {
			n += 8000;
		} else if (msg.getContentRaw().contains("https://pr0")) {
			n = 10;
		} else if(msg.getAuthor().isBot() && !msg.getAttachments().isEmpty()) {
			n += 28000;
		}
		Thread.sleep(n);
		msg.delete().queue();
	}
}