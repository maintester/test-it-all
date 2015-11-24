package communication.components.client.command;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import communication.components.config.ComConstants;
import communication.components.config.ConfigurationUtil;
import communication.components.server.Utils;
import communication.interfaces.IClientCommand;

public class ClientCommandClick implements IClientCommand {

	@Override
	public Object doCommand(String sCommand) {
		System.out.println(sCommand);
		return ComConstants.returnOk;

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
		Robot robot=null;
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		robot.mouseMove(Integer.parseInt(param1), Integer.parseInt(param2));
		if (sCommand.equals(ComConstants.comMouseDown)) {
			robot.mousePress(InputEvent.BUTTON1_MASK);
		}
		if (sCommand.equals(ComConstants.comMouseUp)) {
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		
		 
		return null;
	}

	@Override
	public Object setParam(String sParam, String sValue) {
		// TODO Auto-generated method stub
		return null;
	}
}
