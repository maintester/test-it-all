package org.json;

public class testjson {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	 
		JSONObject jo = new JSONObject();
		jo.append("name", new String("holger"));
		jo.append("name", new String("berb"));
		jo.append("nachname", "reinecke");
		jo.put("geb","17.03.64");
		System.out.println(jo.toString());
	}

}
