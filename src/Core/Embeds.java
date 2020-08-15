package Core;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds {
	////////////////////////////////////////////////////////////////////////////
	//* Building a Discord Embed with all specified Infos from arguments     *//
	////////////////////////////////////////////////////////////////////////////
	public static void createEmbed(TextChannel ch, String title, String desc, String footer, String imgUrl, String postUrl) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(title, postUrl);
		eb.setDescription(desc);
		eb.setFooter(footer);
		eb.setImage(imgUrl);
		ch.sendMessage(eb.build()).queue();
	}
}
