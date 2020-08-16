package pr0;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class Captcha {
	public static String token;
	public static Message capMsg;
	public static void getCaptcha(TextChannel ch) throws IOException {
		String url = "https://pr0gramm.com/api/user/captcha";
		JSONObject json = Inspect.readJson(url);
		String encoded = json.get("captcha").toString();
		String base64 = encoded.split(",")[1];
		base64.replace("\\","");
		BufferedImage img = null;
		byte[] imgBytes;
		BASE64Decoder decode = new BASE64Decoder();
		imgBytes = decode.decodeBuffer(base64);
		ByteArrayInputStream bis = new ByteArrayInputStream(imgBytes);
		img = ImageIO.read(bis);
		bis.close();

		File output = new File("captcha.png");
		ImageIO.write(img, "png", output);

		token = json.getString("token");
		ch.sendFile(output).append(token).queue();
	}
}
