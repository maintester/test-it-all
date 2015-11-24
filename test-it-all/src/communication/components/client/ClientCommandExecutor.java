package communication.components.client;

import java.io.File;
import java.util.HashMap;

import communication.components.client.command.ClientCommandCleanup;
import communication.components.client.command.ClientCommandClick;
import communication.components.client.command.ClientCommandConnectToServer;
import communication.components.client.command.ClientCommandPicture;
import communication.components.config.ComConstants;
import communication.components.config.ConfigurationUtil;

import communication.components.server.Utils;
import communication.interfaces.IClientCommand;
import communication.interfaces.ICommandExecutor;
import communication.interfaces.IServerParent;

public class ClientCommandExecutor implements ICommandExecutor {

	IServerParent serverParent;
	ClientCommandPicture ccp = new ClientCommandPicture();
	ClientCommandConnectToServer ccconserver = new ClientCommandConnectToServer();
	ClientCommandClick ccclick = new ClientCommandClick();

	public Object receiveIncommingMessage(String sMess) {
		Object sRet = "";
		 System.out.println("Client got " + sMess);
		String command = "";
		HashMap<String, String> values = new Utils().CommandToMap(sMess);
		System.out.println("values");
		System.out.println(values);
		if (values.containsKey(ComConstants.paramCommand)) {
			command = values.get(ComConstants.paramCommand);
		}
		if (command.equals(ComConstants.comTakingPictures_Start)  ) {
			ccp.doCommand(command);
		}
		if (command.equals(ComConstants.comTakingPictures_Stop)) {
			if (ccp.isAlive()) {
				ccp.interrupt();
			}
		}
		if (command.equals(ComConstants.comConnectToServer)) {
			
			sRet = ccconserver.doCommand(sMess);
			serverParent.haveOneRequest((String)sRet);
			//System.out.println("connectresult = " +sRet);
		}
		if (command.equals(ComConstants.comCleanup)) {
			IClientCommand cccup = new ClientCommandCleanup();
			cccup.doCommand(command);
		}
		if (command.equals(ComConstants.comGetLastPicture)) {
			sMess = ComConstants.paramLastPicture+"="+ ccp.getParam("paramLastPic");
			ccconserver.setParam(ComConstants.paramAddToNextMessage,sMess);
		}
		if (command.equals(ComConstants.comMouseClick) |
				command.equals(ComConstants.comMouseDown)|
				command.equals(ComConstants.comMouseUp)|
				command.equals(ComConstants.comMouseDblClick)|
				command.equals(ComConstants.comMouseMove) 
				) {
			ccclick.doCommand(command,  values.get(ComConstants.paramX),values.get(ComConstants.paramY) );

		}
		return sMess;
	}

	@Override
	public void getNews(String sNews) {
		serverParent.send_message_to_window(sNews) ;
	}

	@Override
	public Object getParam(String sParam) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setServerParent(IServerParent serverParent) {
		this.serverParent = serverParent;
		ccconserver.setServerParent(this.serverParent);
	}

	 
}
