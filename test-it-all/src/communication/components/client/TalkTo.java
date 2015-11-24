package communication.components.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import communication.components.config.ComConstants;
import communication.interfaces.ITalker;

public class TalkTo implements ITalker {

	private final String USER_AGENT = "Mozilla/5.0";
	private String receiver;

	public static void main(String[] args) throws Exception {

		ITalker talker = new TalkTo();

		System.out.println("Testing 1 - Send Http GET request");
		talker.setReceiver("http://localhost/index.html?");
		talker.talkTo("message=hello&id=3rreedfdd454");
		talker.talkTo("message=hello&id=4ffdrreedfdd454");
		talker.talkTo("message=hello&id=5fdrreedfdd454");
		talker.talkTo("message=hello&id=6fdrreedfdd454");

		// System.out.println("\nTesting 2 - Send Http POST request");
		// http.sendPost();

	}
	
	// HTTP GET request
	@Override
	public String talkTo(String sMessage) {
		String sRet = "";//ComConstants.returnOk;
		String url = "";// "http://localhost/index.html?message=hello&id=urr44dfdd454";
		url = receiver + "?" + sMessage;
		//System.out.println("sending" + url);
		StringBuffer response;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			// add request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = con.getResponseCode();
			//System.out.println("\nSending 'GET' request to URL : " + url);
			//System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append(ComConstants.lineDelimiter);
			}
			sRet+=response;
			//System.out.println("resones = " + response);
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			sRet = ComConstants.returnError;
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			sRet = ComConstants.returnError;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			sRet = ComConstants.returnError;
		}

		// print result
		// System.out.println(response.toString());
		return sRet;

	}

	// HTTP POST request
	private void sendPost() throws Exception {

		String url = "https://selfsolve.apple.com/wcResults.do";
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

	@Override
	public void setReceiver(String sRec) {
		receiver = sRec;
	}

}
