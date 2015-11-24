package communication.components.client.command;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import communication.components.config.ComConstants;
import communication.components.config.ConfigurationUtil;
import communication.components.server.Utils;
import communication.interfaces.IClientCommand;

public class ClientCommandPicture extends Thread implements IClientCommand {

	private String paramLastPic = "";
	private String lastPic = "";

	@Override
	public Object doCommand(String sCommand) {
		if (sCommand.equals(ComConstants.comTakingPictures_Start)) {
			if (this.isAlive() == false) {
				this.start();
			}
		}
		if (sCommand.equals(ComConstants.comGetLastPicture)) {
			
		}
		return ComConstants.returnOk;
	}

	public void run() {
		boolean run = true;
		while (run == true) {
			takeScreenshootOf(ConfigurationUtil.getInstance().getIntValue("PICTURE_START_X"), ConfigurationUtil.getInstance()
					.getIntValue("PICTURE_START_Y"), ConfigurationUtil.getInstance().getIntValue("PICTURE_END_X"),
					ConfigurationUtil.getInstance().getIntValue("PICTURE_END_Y"));
			System.out.println("taking picture");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
				run = false;
			}
		}
	}

	public BufferedImage takeScreenshootOf(int x, int y, int width, int height) {
		BufferedImage bi = null;
		String sName = "" + new Date().getTime();
		String sPath = ConfigurationUtil.getInstance().getValue("PATHTOPICTURES");
		try {
			sName = new Utils().getTimeName();
			//paramLastPic = sPath + sName + ".png";
			//System.out.println("nameofpicture=" + sName);
			bi = new Robot().createScreenCapture(new Rectangle(x, y, width, height));

			// http://www.mkyong.com/java/how-to-convert-bufferedimage-to-byte-in-java/
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			// http://www.rgagnon.com/javadetails/java-0598.html
			paramLastPic = DatatypeConverter.printBase64Binary(imageInByte);

			ImageIO.write(bi, "png", new File(sPath + sName + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bi;

	}

	@Override
	public Object getParam(String sParam) {
		if (sParam.equals("paramLastPic")) {
			return paramLastPic;
		}
		return "";

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
