package communication.components.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import communication.components.config.ComConstants;

public class Utils {
	public HashMap<String, String> UrlToMap(String sVal) {
		HashMap<String, String> values = new HashMap<String, String>();
		String[] singlevalues = sVal.split(ComConstants.paramDelimiter);
		for (int n = 0; n < singlevalues.length; n++) {
			String[] singelvalue = singlevalues[n].split(ComConstants.valueDelimiter);
			if (singelvalue.length > 1) {
				values.put(singelvalue[0], singelvalue[1]);
			}
		}
		return values;
	}
	public HashMap<String, String> CommandToMap(String sVal) {
		HashMap<String, String> values = new HashMap<String, String>();
		String[] singlevalues = sVal.split(ComConstants.lineDelimiter);
		for (int n = 0; n < singlevalues.length; n++) {
			String[] singelvalue = singlevalues[n].split(ComConstants.valueDelimiter);
			if (singelvalue.length > 1) {
				values.put(singelvalue[0], singelvalue[1]);
			}
		}
		return values;
	}
	public String getTimeName() {
		return "" + new Date().getTime();
	}
}
