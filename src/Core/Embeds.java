package Core;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Embeds {
	////////////////////////////////////////////////////////////////////////////
	//* Building a Discord Embed with all specified Infos from arguments     *//
	////////////////////////////////////////////////////////////////////////////
	public static void createImage(TextChannel ch, String title, String Author, String footer, String imgUrl, String postUrl, String date, String cat, String poster) {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle(title, postUrl);
		String autorLink = "https://pr0gramm.com/user/" + Author;
		eb.addField("Uploader", "["+Author+"]"+"("+autorLink+")", true)
				.addField("Datum", date, true)
				.addField("Kategorie", cat, true);
		eb.setFooter("Gesendet von: " + poster + " | " + footer);
		eb.setImage(imgUrl);
		if(!cat.contains("nsfl"))
			ch.sendMessage(eb.build()).queue();
	}
	public static void createVideo(TextChannel ch, String title, String Author, String footer, String imgUrl, String postUrl, String date, String cat, String poster) throws IOException {
		EmbedBuilder eb = new EmbedBuilder();
		InputStream file = new URL(imgUrl).openStream();
		eb.setTitle(title, postUrl);
		String autorLink = "https://pr0gramm.com/user/" + Author;
		eb.addField("Uploader", "["+Author+"]"+"("+autorLink+")", true)
				.addField("Datum", date, true)
				.addField("Kategorie", cat, true);
		eb.setFooter("Gesendet von: " + poster + " | " + footer);
		if(!cat.contains("nsfl"))
			ch.sendFile(file, "video.mp4").embed(eb.build()).queue();
	}
	public static void createComment(TextChannel ch, String author, String commUrl, String comment, String benis, String date, String postUrl, String poster)  {
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Kommentar von " + author, commUrl);
		eb.setDescription(comment);
		eb.addField("Benis", benis, true);
		String autorLink = "https://pr0gramm.com/user/" + author;
		eb.addField("Autor", "["+author+"]"+"("+autorLink+")", true);
		eb.addField("Datum", date, true);
		eb.addField("", "[Link zum Post]("+postUrl+")", true);
		eb.addBlankField(true);
		eb.addField("", "[Link zum Kommentar]("+commUrl+")",true);
		eb.setFooter("Gesendet von:" + poster + " | " + benis);
		ch.sendMessage(eb.build()).queue();
	}
}
