package communication.components.server;

import javax.security.auth.login.Configuration;

import communication.components.config.ComConstants;
import communication.components.config.ConfigurationUtil;
import communication.interfaces.IServerParent;

public class Timer extends Thread {

	IServerParent parent;
	int loopCounter = 0;
	int sleepTime = ConfigurationUtil.getInstance().getIntValue("SERVERINTERVAL");

	public Timer(IServerParent pparent) {
		this.parent = pparent;
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {}
			parent.getNews(ComConstants.comTickTack);
		}
	}

}
