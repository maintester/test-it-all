package communication.components.client.command;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;

import communication.components.client.TalkTo;
import communication.components.config.ComConstants;
import communication.components.config.ConfigurationUtil;
import communication.components.server.Utils;
import communication.interfaces.IClientCommand;
import communication.interfaces.IServerParent;

public class ClientCommandConnectToServer extends Thread implements IClientCommand {
	TalkTo talk = new TalkTo();
	String uuid = "";
	int sleeptime = 2000;
	String additionalMessage="";
	IServerParent serverParent = null;

	@Override
	public Object doCommand(String sCommand) {
		//System.out.println("ClientCommandConnectToServer get command " +sCommand);
		HashMap<String, String> values = new Utils().CommandToMap(sCommand);
		uuid = values.get(ComConstants.paramUUID);
		talk.setReceiver(ConfigurationUtil.getInstance().getValue("SERVERNAME"));
		this.start();
		return ComConstants.returnOk;
	}

	public void run() {
		while (true) {
			//System.out.println("sending hello message to server");

			if(additionalMessage.length()>1){
				additionalMessage=ComConstants.paramDelimiter+additionalMessage;
			}
			String result = talk.talkTo("message=hello"+ComConstants.paramDelimiter+"id=" + uuid + additionalMessage);
			additionalMessage="";
			if (result.equals(ComConstants.returnError)) {
				// try it more often
				sleeptime = 1000;
			} else {
				sleeptime = 2000;
			}
			serverParent.send_message_to_window(result);
			serverParent.haveOneRequest(result);
			System.out.println(result);

			try {
				Thread.sleep(sleeptime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setServerParent(IServerParent serverParent) {
		this.serverParent = serverParent;
	}
	
	@Override
	public Object getParam(String sParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object doCommand(String sCommand, String param1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object doCommand(String sCommand, String param1, String param2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object setParam(String sParam, String sValue) {
		if(sParam.equals(ComConstants.paramAddToNextMessage)){
			additionalMessage+= sValue;
		}
		return null;
	}

}
