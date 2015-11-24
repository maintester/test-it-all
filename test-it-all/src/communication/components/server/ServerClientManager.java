package communication.components.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.Configuration;

import communication.components.client.TalkTo;
import communication.components.config.ComConstants;
import communication.components.config.ConfigurationUtil;
import communication.interfaces.IConfiguration;

public class ServerClientManager {

	Map<String, HashMap> clients = new HashMap<>();
	Map<String, ArrayList> clientsCommands = new HashMap<>();
	Map<String, HashMap> clientsState = new HashMap<>();
	
	public String receiveIncommingMessage(String sMessage) {
		Map<String, String> values = new HashMap<>();
		String sRet = ComConstants.returnOk;
		System.out.println("receiveIncommingMessage: " + sMessage);
		values = new Utils().UrlToMap(sMessage);
		 
		String id = values.get(ComConstants.paramID);
		//System.out.println("habe id = " + id);
	
		HashMap clientvalues;
		if (clients.containsValue(id)) {
			clientvalues = clients.get(id);
		} else {
			clientvalues = new HashMap();
		}
		if (values.containsKey(ComConstants.paramIP)  ) {
			clientvalues.put(ComConstants.paramIP, values.get(ComConstants.paramIP));
		}
		if (values.containsKey(ComConstants.paramMessage)) {
			clientvalues.put(ComConstants.paramMessage, values.get(ComConstants.paramMessage));
		}
		System.out.println(clientvalues);
		clients.put(id, clientvalues);
		//System.out.println(clients);
		//*****************
		// handling state
		if(clientsState.containsKey(id)){
			
		}else{
			clientsState.put(id, new HashMap());
		}
		HashMap statevalues = clientsState.get(id);
		statevalues.put(ComConstants.paramLastVisit, (int)new Date().getTime()/1000 );
		clientsState.put(id, statevalues);

		//*******************
		Iterator<String> iter = clients.keySet().iterator();
		System.out.println("**************");
		while (iter.hasNext()) {
			String sid = iter.next();
			System.out.println(clients.get(sid));
			 //System.out.println(iter.next());
		}
		if(clientsCommands.containsKey(id)){
			ArrayList commandslist =clientsCommands.get(id);
			if(commandslist.size()>0){
				for (int i = 0; i < commandslist.size(); i++) {
					System.out.println(commandslist.get(i));
					sRet+= "\n" + ComConstants.paramCommand + "="+commandslist.get(i);
				}
				commandslist= new ArrayList();
				clientsCommands.put(id, commandslist);
				
			}
		}
		System.out.println("returning" + sRet);
		return sRet;
	}
	 
	public String sendOutgoingMessage(String command){
		String sRet = "";
		
		return sRet;
	}
	
	
	public void sendMessageToSelectedClients(String commandToSend, List<String> selectedValuesList) {
		System.out.println("sendMessageToSelectedClients :"+ commandToSend);
		TalkTo talk = new TalkTo();
		ArrayList clientcommand;
		
		for(String clientId:selectedValuesList){
			//clientsCommands.put(clientId, commandToSend);
			if (clientsCommands.containsKey(clientId)) {
				clientcommand = clientsCommands.get(clientId);
			
			}else{
				clientcommand= new ArrayList();
			}
			clientcommand.add(commandToSend);
			clientsCommands.put(clientId, clientcommand);
			System.out.println(clientsCommands);
//				if(clientvalues.containsKey(ComConstants.paramMessageFromServer)){
//					mess= (String) clientvalues.get(ComConstants.paramMessageFromServer);
//				}
//				mess+= ComConstants.paraDelemiter+ commandToSend;
//				clientvalues.put(ComConstants.paramMessageFromServer, mess);
//				clients.put(clientId, clientvalues);
//				System.out.println("+++++++++++++++++++++++++++++");
//				System.out.println(clients.get(clientId));
//				System.out.println("+++++++++++++++++++++++++++++");
			
			//System.out.println(s);
//			IConfiguration config =ConfigurationUtil.getInstance(); 
//			HashMap client = clients.get(clientId);
//			String target = "http:/"+client.get(ComConstants.paramIP) + ":" +config.getValue("CLIENTLISTENPORT") +"/index.html" ;
//			System.out.println("target: " + target);
//			talk.setReceiver(target);
//			talk.talkTo(ComConstants.paramCommand + "=" + commandToSend);
		}
		
	}
	//**************************************************************
	//** getter setter
	//**************************************************************	
	public Map<String, HashMap> getClients() {
		return clients;
	}
	
	public Map<String, ArrayList> getClientsCommands() {
		return clientsCommands;
	}

	public void setClientsCommands(Map<String, ArrayList> clientsCommands) {
		this.clientsCommands = clientsCommands;
	}
	 
}
