package Events;

import Core.config;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class GuildMessage extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		Message msg = event.getMessage();
		String content = msg.getContentRaw();
		TextChannel ch = event.getMessage().getTextChannel();

		try {
			config.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean notCmds;
		boolean notChs;

		String[] cmds = config.getValue("dcmds").split(",");
		String[] chs = config.getValue("mchannels").split(",");

		if(config.getValue("dcmds").isEmpty())  {
			notCmds = false;
		}   else    {
			notCmds = Arrays.stream(cmds).anyMatch(msg.getContentRaw()::contains);
		}

		if(config.getValue("mchannels").isEmpty())  {
			notChs = false;
		}   else    {
			notChs = Arrays.stream(chs).anyMatch(ch.getId()::equalsIgnoreCase);
		}

		if(content.startsWith("!")
				|| content.startsWith(">")
				|| content.startsWith("*")
				|| msg.getAuthor().getName().contains("Rythm")) {
			if(content.startsWith("!purge"))    {       //Kommando zum Löschen mehrerer Nachricht
				String[] cmd = content.split(" ");
				int n = Integer.parseInt(cmd[1]);
				if(n > 50) {
					ch.sendMessage("Bruder mach nich diesen, ich kann nur 50 liften alla").queue();
				}   else {
					clear(ch, n);
				}
			}   else  if(!notChs && !notCmds)  {                               //Kriterien zum Löschen der Nachricht zutreffend
				try {
					handleDelete(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
		}
		Thread.sleep(n);
		msg.delete().queue();
	}
}