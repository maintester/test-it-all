package communication.interfaces;

public interface IClientCommand {
	public Object doCommand(String sCommand);
	public Object doCommand(String sCommand, String param1);
	public Object doCommand(String sCommand, String param1, String param2);
	public Object getParam(String sParam);
	public Object setParam(String sParam, String sValue);
}
