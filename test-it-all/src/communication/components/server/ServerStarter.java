package communication.components.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
//http://fragments.turtlemeat.com/javawebserver.php
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

import communication.components.config.ComConstants;
import communication.components.config.ConfigurationUtil;
import communication.interfaces.IConfiguration;
import communication.interfaces.IServerParent;

// http://fragments.turtlemeat.com/javawebserver.php
public class ServerStarter extends JFrame implements IServerParent {
	ServerClientManager clientManager = new ServerClientManager();
	// declare some panel, scrollpanel, textarea for gui
	JList<String> clientlist;
	DefaultListModel<String> model;
	Timer t = new Timer(this);
	JPanel jPanel1 = new JPanel();
	Image img;
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextArea jTextArea2 = new JTextArea();
	JButton doPhoto = new JButton("StartFoto");
	JButton stopPhoto = new JButton("StopFoto");
	JButton getPhoto = new JButton("GetFoto");
	ImageIcon icon = new ImageIcon("d:/bild.png");

	JLabel picturelabel = new JLabel();
	static Integer listen_port = null;

	// basic class constructor
	public ServerStarter() {
		try {
			 
			jbInit();
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

		listen_port = new Integer(config.getIntValue("SERVERLISTENPORT"));

		// create an instance of this class
		ServerStarter webserver = new ServerStarter();
	}

	class SharedMouseEventListener implements MouseListener {
		// where initialization occurs:
		// Register for mouse events on blankArea and the panel.

		public void mousePressed(MouseEvent e) {
			clientManager.sendMessageToSelectedClients(ComConstants.comMouseDown + "&"+ComConstants.paramX+"=" + e.getX() + "&"+ComConstants.paramY+"="+ e.getY() ,
					clientlist.getSelectedValuesList());
			// saySomething("Mouse pressed; # of clicks: "
			// + e.getClickCount(), e);
		}

		public void mouseReleased(MouseEvent e) {
			clientManager.sendMessageToSelectedClients(ComConstants.comMouseUp  + "_" + e.getX() + "_"+ e.getY() ,
					clientlist.getSelectedValuesList());
			// saySomething("Mouse released; # of clicks: "
			// + e.getClickCount(), e);
		}

		public void mouseEntered(MouseEvent e) {
			// saySomething("Mouse entered", e);
		}

		public void mouseExited(MouseEvent e) {
			// saySomething("Mouse exited", e);
		}

		public void mouseClicked(MouseEvent e) {

			//saySomething("Mouse clicked (# of clicks: " + e.getClickCount() + ")" + e.getX() + " " + e.getY(), e);
			clientManager.sendMessageToSelectedClients(ComConstants.comMouseClick  + "_" + e.getX() + "_"+ e.getY() ,
					clientlist.getSelectedValuesList());
		}

		void saySomething(String eventDescription, MouseEvent e) {
			String sout = eventDescription + " detected on " + e.getComponent().getClass().getName() + ".";
			System.out.println(sout);

		}
	}

	class SharedListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			// System.out.println(clientlist.getSelectedIndex());
			return;
		}
	}

	class SharedButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {

			// Die Quelle wird mit getSource() abgefragt und mit den
			// Buttons abgeglichen. Wenn die Quelle des ActionEvents einer
			// der Buttons ist, wird der Text des JLabels entsprechend geändert
			if (ae.getSource() == doPhoto) {
				clientManager.sendMessageToSelectedClients(ComConstants.comTakingPictures_Start,
						clientlist.getSelectedValuesList());
			}
			if (ae.getSource() == stopPhoto) {
				clientManager.sendMessageToSelectedClients(ComConstants.comTakingPictures_Stop,
						clientlist.getSelectedValuesList());
			}
			if (ae.getSource() == getPhoto) {
				clientManager.sendMessageToSelectedClients(ComConstants.comGetLastPicture,
						clientlist.getSelectedValuesList());
//				ImageIcon image = null;
//				try {
//					URL url = new URL("http://localhost:8080/index.html?command=5");
//					BufferedImage c = ImageIO.read(url);
//					image = new ImageIcon(c);
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				ImageIcon icon2 = new ImageIcon("http://localhost:8080/index.html?command=5");
//				picturelabel.setIcon(image);
			}
			 
		}
	}

	// set up the user interface
	private void jbInit() throws Exception {
		t.start();
		model = new DefaultListModel();
		//model.add(0, "abc");
		clientlist = new JList(model);
		clientlist.addListSelectionListener(new SharedListSelectionHandler());
		// clientlist.setFixedCellWidth(20);
		// clientlist.setSize(50, 50);
		JScrollPane pane = new JScrollPane(clientlist);

		pane.setMaximumSize(new Dimension(200, 200));
		pane.setMinimumSize(new Dimension(200, 200));
		pane.setPreferredSize(new Dimension(100, 200));

		// oh the pretty colors
		jTextArea2.setBackground(new Color(16, 12, 66));
		jTextArea2.setForeground(new Color(151, 138, 255));
		jTextArea2.setBorder(BorderFactory.createLoweredBevelBorder());
		jTextArea2.setToolTipText("");
		jTextArea2.setEditable(false);
		jTextArea2.setColumns(30);
		jTextArea2.setRows(15);
		// change this to impress your friends
		this.setTitle("Jon\'s fancy prancy webserver");
		// ugly inline listener, it's for handling closing of the window
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				this_windowClosed(e);
			}
		});
		// add the various to the proper containers
		jScrollPane1.getViewport().add(jTextArea2);
		jPanel1.add(jScrollPane1);
		this.getContentPane().setLayout(null);
		jPanel1.setBounds(new Rectangle(0, 0, 400, 200));
		this.getContentPane().add(jPanel1);
		pane.setBounds(new Rectangle(0, 210, 400, 200));

		doPhoto = new JButton("startFoto");
		doPhoto.setBounds(new Rectangle(0, 410, 100, 20));
		doPhoto.addActionListener(new SharedButtonActionListener());
		stopPhoto = new JButton("stopFoto");
		stopPhoto.setBounds(new Rectangle(0, 440, 100, 20));
		stopPhoto.addActionListener(new SharedButtonActionListener());
		getPhoto = new JButton("getFoto");
		getPhoto.setBounds(new Rectangle(0, 470, 100, 20));
		getPhoto.addActionListener(new SharedButtonActionListener());
		picturelabel.setBounds(new Rectangle(ConfigurationUtil.getInstance().getIntValue("PICTURE_START_X"),
				500 + ConfigurationUtil.getInstance().getIntValue("PICTURE_START_Y"), ConfigurationUtil.getInstance()
						.getIntValue("PICTURE_END_X"), ConfigurationUtil.getInstance().getIntValue("PICTURE_END_Y")));
		picturelabel.addMouseListener(new SharedMouseEventListener());
		this.getContentPane().add(doPhoto);
		this.getContentPane().add(stopPhoto);
		this.getContentPane().add(getPhoto);
		this.getContentPane().add(picturelabel);

		this.getContentPane().add(pane);

		// tveak the apearance
		this.setVisible(true);
		this.setSize(620, 650);
		this.setResizable(true);
		// make sure it is drawn
		this.validate();
		// create the actual serverstuff,
		// all that is implemented in another class
		new Server(listen_port.intValue(), this);
	}

	// exit program when "X" is pressed.
	void this_windowClosed(WindowEvent e) {
		System.exit(1);
	}

	// this is a method to get messages from the actual
	// server to the window
	@Override
	public String send_message_to_window(String s) {
		jTextArea2.append(s);
		return "";
	}

	@Override
	public String haveOneRequest(String s) {
		// jTextArea2.append(s);
		System.out.println("xgot request " + s);
		String sRet = "";
		String[] aMess = s.split("\\?");
		System.out.println("*.*.*.*.*");
		System.out.println(aMess[1]);
		HashMap<String, String> values = new Utils().UrlToMap(aMess[1]);
		String lastid = values.get("id");
		sRet = clientManager.receiveIncommingMessage(aMess[1]);
		System.out.println("sret = "+ sRet );
		Set<String> ks = clientManager.getClients().keySet();
		System.out.println(".-.-.-.-");
		System.out.println(ks);
		Iterator<String> iter = ks.iterator();
	 
		while (iter.hasNext()) {
			String newid= iter.next();
			if (model.contains(newid) == false) {
				model.addElement(newid);
			}
		}
		//System.out.println("#############");
//		if(clientManager.getClients().get(lastid).containsKey(ComConstants.paramMessageFromServer)){
//			sRet=(String) clientManager.getClients().get(lastid).get(ComConstants.paramMessageFromServer);
//		}
		//System.out.println(clientManager.getClients().get(lastid).get(key));
	//	System.out.println("#############");
 

		return sRet;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	private void update(){
		System.out.println("update");
	}
	
	private String getClientInfoObject() {
		JSONObject jo = new JSONObject();
		Set<String> ks = clientManager.getClients().keySet();
		Iterator<String> iter = ks.iterator();
		while (iter.hasNext()) {
			String id = iter.next();
			jo.append("ids", id);
		}
		return jo.toString();
	}

	@Override
	public void getNews(String sNews) {
		 if(sNews.equals(ComConstants.comTickTack)){
			 update();
		 }
	}

	@Override
	public Object getParam(String sParam) {
		// TODO Auto-generated method stub
		return null;
	}
} // class end
