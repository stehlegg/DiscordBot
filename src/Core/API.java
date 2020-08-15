package Core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.managers.Presence;

public class API {

	////////////////////////////////////////////////////////////////////////////
	//* Discord API Object                                                   *//
	//* Getter and Setter for Discord API                                    *//
	////////////////////////////////////////////////////////////////////////////
	private static JDA api;
	public static void setAPI(JDA jda)  {
		api = jda;
	}
	public static JDA getApi()  {
		return api;
	}

	////////////////////////////////////////////////////////////////////////////
	//* Discord Presence Object                                              *//
	//* Getter and Setter for Discord Presence                               *//
	////////////////////////////////////////////////////////////////////////////
	private static Presence pres;
	public static void setPres(Presence pc) {
		pres = pc;
	}
	public static Presence getPres()  {
		return pres;
	}
}
