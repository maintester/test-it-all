package communication.components.client.command;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;


import communication.components.config.ComConstants;
import communication.components.config.ConfigurationUtil;
import communication.components.server.Utils;
import communication.interfaces.IClientCommand;

public class ClientCommandCleanup extends Thread implements IClientCommand  {

	@Override
	public Object doCommand(String sCommand) {
		this.start();
		return ComConstants.returnOk;
		
	}
	public void run() {
		String sPath = ConfigurationUtil.getInstance().getValue("PATHTOPICTURES");

		File folder = new File(sPath);
		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles) {
			if (file.isFile()) {
				file.delete();
			}
		}
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
		// TODO Auto-generated method stub
		return null;
	}
}
