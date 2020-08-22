package Core;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds {

	static EmbedBuilder eb = new EmbedBuilder();
	////////////////////////////////////////////////////////////////////////////
	//* Building a Discord Embed with all specified Infos from arguments     *//
	////////////////////////////////////////////////////////////////////////////
	public static void createImage(TextChannel ch, String[] infos, int benis, String imgUrl) {
		eb.clear();
		eb.setTitle(infos[2], infos[1])
				.addField("Uploader", "["+infos[0]+"]"+"("+"https://pr0gramm.com/user/" + infos[0]+")", true)
				.addField("Datum", infos[3], true)
				.addField("Benis", String.valueOf(benis), true)
				.setFooter("Gesendet von: " + infos[6] + " | " + infos[5])
				.setImage(imgUrl);
		if(!infos[4].contains("nsfl"))
			ch.sendMessage(eb.build()).queue();
	}

	public static void createVideo(TextChannel ch, String[] infos, int benis, String imgUrl)  {
		eb.clear();
		eb.setTitle(infos[2],infos[1])
				.addField("Uploader", "["+infos[0]+"]"+"("+"https://pr0gramm.com/user/" + infos[0]+")", true)
				.addField("Datum", infos[3], true)
				.addField("Benis", String.valueOf(benis), true)
				.setFooter("Gesendet von: " + infos[6] + " | " + infos[5]);
		if(!infos[4].contains("nsfl"))   {
			ch.sendMessage(imgUrl).queue();
			ch.sendMessage(eb.build()).queue();
		}
	}

	public static void createComment(TextChannel ch, String[] infos)  {
		eb.clear();
		eb.setTitle("Kommentar von " + infos[1], infos[3]).setDescription(infos[4])
				.addField("Autor", "["+infos[1]+"]"+"("+"https://pr0gramm.com/user/" + infos[1]+")", true)
				.addField("Datum", infos[0], true)
				.addField("Benis", infos[7], true)
				.addField("", "[Link zum Post]("+infos[2]+")", true)
				.addBlankField(true)
				.addField("", "[Link zum Kommentar]("+infos[3]+")",true)
				.setFooter("Gesendet von:" + infos[5] + " | " + infos[6]);
		ch.sendMessage(eb.build()).queue();
	}
}
