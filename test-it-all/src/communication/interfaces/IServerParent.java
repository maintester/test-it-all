package communication.interfaces;

public interface IServerParent {
	public String send_message_to_window(String sMessage);
	public Object haveOneRequest(String sRequest);
	public void getNews(String sNews);
	public Object getParam(String sParam);
}
