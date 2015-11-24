package communication.components.config;

import java.util.HashMap;

import communication.interfaces.IConfiguration;

public class ConfigurationUtil implements IConfiguration{
	private static ConfigurationUtil instance = new ConfigurationUtil();
	private HashMap<String, String> values = new HashMap();

	private ConfigurationUtil() {
		values.put("SERVERNAME", "http://127.0.0.1:80/command.html");
		values.put("SERVERLISTENPORT", "80");
		values.put("CLIENTLISTENPORT", "8080");
		values.put("SERVERINTERVAL", "1000");
		 
		values.put("LISTENPORT", "49153");
		//values.put("PATHTOFILES", "C:/TMP/");
		values.put("PATHTOFILES", "files/");
		values.put("PATHTOPICTURES", "files/pictures/");
		
		values.put("PICTURE_START_X", "0");
		values.put("PICTURE_START_Y", "0");
		values.put("PICTURE_END_X", "400");
		values.put("PICTURE_END_Y", "400");
		 
	}

	public static ConfigurationUtil getInstance() {
		return instance;
	}
	@Override
	public String getValue(String name) {
		if (values.containsKey(name)) {
			return values.get(name);
		}
		return "";
	}
	@Override
	public int getIntValue(String name) {
		if (values.containsKey(name)) {
			return Integer.parseInt(values.get(name));
		}
		return 0;
	}

	 
}
