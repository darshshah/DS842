import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class MessagePasser {
	BlockingQueue<Message> recbuf = new LinkedBlockingDeque<Message>();
	Map<String, Socket> socketmap = new HashMap<String, Socket>();
	ArrayList<User> users = new ArrayList<User>();
	ArrayList<Rule> sendRules = new ArrayList<Rule>();
	ArrayList<Rule> receiveRules = new ArrayList<Rule>();
	private String localname;
	public MessagePasser(String configuration_filename,String local_name) throws IOException {
		this.localname = local_name;
		MPhelper helper = new MPhelper();
		helper.parseConfigFile(configuration_filename, users, sendRules, receiveRules);

		ServerSocket receiveserver = null;

		//Start accepting connection
		setConnection(users, local_name, helper, socketmap, receiveserver);
		
		new Thread(new Receiver(receiveserver,receiveRules)).start();
		System.out.println("Server thread successfully started.");

	}
	/*method send(Message)*/
	public void send(Message message) throws UnknownHostException, IOException {
		String destname = message.getDest();
		message.setDuplicate(false);
		message.setSource(localname);
		
		if (!socketmap.containsKey(destname)){
			User thedest = null;
			for (int i = 0; i < users.size(); i++){
				if (destname == users.get(i).name){
					thedest = users.get(i);
				}
			}
			Socket s = new Socket(thedest.ip,thedest.port);
			socketmap.put(destname,s);
		}
		
		Socket s = socketmap.get(destname);
		String seaction = message.matchRules(sendRules);
		if (seaction == "") {
			ObjectOutputStream sendstream = new ObjectOutputStream(s.getOutputStream());
			sendstream.writeObject(message);
		}
		else if (seaction == "drop") {}
		else if (seaction == "duplicate") {
			ObjectOutputStream sendstream = new ObjectOutputStream(s.getOutputStream());
			sendstream.writeObject(message);
			message.setDuplicate(true);
			sendstream.writeObject(message);}
		else if (seaction == "delay") {/*TODO*/}
		else {
			System.out.println("Send action read error!");
		}
		
	}
	/*End of method send(Message)*/
	/*method receive()*/
	public Message receive() throws InterruptedException {
		return recbuf.take();
	}
	/*End of method receive()*/
	/*method setConnection*/
	private void setConnection(ArrayList<User> users, String local_name, MPhelper helper, Map<String, Socket> socketmap, ServerSocket listener) throws IOException{
		if (!helper.containName(users, local_name)){
			return;
		}
		//Itself as a server
		listener = new ServerSocket(helper.getPort(users, local_name));
		//Itself as a client
		for (int i = 0; i < users.size(); i++){
			String thename = users.get(i).name;
			if (thename!=local_name){
				Socket s = new Socket(users.get(i).ip,users.get(i).port);
				socketmap.put(thename,s);
			}
		}		
	}
	/*End of method setConnection*/
	/*Receiver*/
	private class Receiver implements Runnable{
		ObjectInputStream is;
		ServerSocket receiveserver;
		Socket receivesocket;
		ArrayList<Rule> receiverules;
		
		public Receiver (ServerSocket receiveserver, ArrayList<Rule> rr) throws IOException{
			this.receiveserver = receiveserver;
			this.receiverules = rr;
		}
		@Override
		public void run(){
			while (true){
				try {
					receivesocket = receiveserver.accept();
					System.out.println("Server accepted connection.");
					is=new ObjectInputStream(receivesocket.getInputStream());
					Message recm = (Message) is.readObject();
					String recaction = recm.matchRules(receiverules);
					if (recaction == "") {recbuf.add(recm);}
					else if (recaction == "drop") {/*TODO*/}
					else if (recaction == "duplicate") {
						recbuf.add(recm);
						recm.setDuplicate(true);
						recbuf.add(recm);}
					else if (recaction == "delay") {/*TODO*/}
					else {
						System.out.println("Receive action read error!");
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/*End of class Receiver*/
}
