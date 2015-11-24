package communication.components.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.UUID;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//http://fragments.turtlemeat.com/javawebserver.php
import javax.swing.JPanel;

import communication.components.config.ComConstants;
import communication.components.config.ConfigurationUtil;
import communication.components.server.Server;
import communication.interfaces.IConfiguration;
import communication.interfaces.IServerParent;

// http://fragments.turtlemeat.com/javawebserver.php
public class ClientStarter extends JFrame implements IServerParent {
	ClientCommandExecutor ccExec = new ClientCommandExecutor();
	// declare some panel, scrollpanel, textarea for gui
	JPanel jPanel1 = new JPanel();
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextArea jTextArea2 = new JTextArea();
	static Integer listen_port = null;
	TalkTo talk = new TalkTo();
	UUID myId = UUID.randomUUID();

	// basic class constructor
	public ClientStarter() {
		ccExec.setServerParent((IServerParent)this);
		try {

			jbInit();
			ccExec.receiveIncommingMessage( ComConstants.paramCommand + "="+ ComConstants.comCleanup);
			ccExec.receiveIncommingMessage( ComConstants.paramCommand + "="+ ComConstants.comConnectToServer+":" +ComConstants.paramUUID+"=" + myId);
			//talk.setReceiver(ConfigurationUtil.getInstance().getValue("SERVERNAME"));
			//talk.talkTo("message=hello&id=" + myId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// the JavaAPI entry point
	// where it starts this class if run
	public static void main(String[] args) {
		// start server on port x, default 80
		// use argument to main for what port to start on
		IConfiguration config = ConfigurationUtil.getInstance();

		listen_port = new Integer(config.getIntValue("CLIENTLISTENPORT"));

		// create an instance of this class
		ClientStarter webserver = new ClientStarter();
	}

	// set up the user interface
	private void jbInit() throws Exception {
		// oh the pretty colors
		jTextArea2.setBackground(new Color(16, 12, 66));
		jTextArea2.setForeground(new Color(151, 138, 255));
		jTextArea2.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextArea2.setToolTipText("");
		jTextArea2.setEditable(false);
		jTextArea2.setColumns(30);
		jTextArea2.setRows(15);
		// change this to impress your friends
		this.setTitle("SmallClient");
		// ugly inline listener, it's for handling closing of the window
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				this_windowClosed(e);
			}
		});
		// add the various to the proper containers
		jScrollPane1.getViewport().add(jTextArea2);
		jPanel1.add(jScrollPane1);
		this.getContentPane().add(jPanel1, BorderLayout.NORTH);

		// tveak the apearance
		this.setVisible(true);
		this.setSize(420, 350);
		this.setResizable(true);
		// make sure it is drawn
		this.validate();
		//new Server(listen_port.intValue(), this);

		// create the actual serverstuff,
		// all that is implemented in another class
		// new Server(listen_port.intValue(), this);
	}

	// exit program when "X" is pressed.
	void this_windowClosed(WindowEvent e) {
		System.exit(1);
	}

	// this is a method to get messages from the actual
	// server to the window
	@Override
	public String send_message_to_window(String s) {
		jTextArea2.append(s+"\n");
		return "";
	}

	@Override
	public Object haveOneRequest(String s) {
		String[] linelist =s.split(ComConstants.lineDelimiter);
		for(int n =0;n< linelist.length;n++){
			ccExec.receiveIncommingMessage(s);
		}
		//jTextArea2.append(s);
//		System.out.println("got message from server " + s);
//		int posofquestion = s.indexOf("?");
//		if (posofquestion != -1) {
//			posofquestion++;
//			s = s.substring(posofquestion);
//			return ccExec.receiveIncommingMessage(s);
//		}
		return "";
	}

	@Override
	public void getNews(String sNews) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getParam(String sParam) {
		// TODO Auto-generated method stub
		return null;
	}

} // class end
