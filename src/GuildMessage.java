import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMessage extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		if(event.getMessage().getContentRaw().startsWith("!")
				|| event.getMessage().getContentRaw().startsWith(">")
				|| event.getMessage().getContentRaw().startsWith("*")
				|| event.getMessage().getAuthor().isBot())  {
			if(!event.getChannel().getId().equals("731230423262167211"))    {
				try {
					delete(event.getMessage());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void delete(Message msg) throws InterruptedException {
		Thread.sleep(2000);
		msg.delete().queue();
	}
}