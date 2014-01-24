import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MessagePasserTest {
	
	private static class Receiver implements Runnable{
		MessagePasser mpass;
		public Receiver (MessagePasser mpass){
			this.mpass = mpass;
		}
		@Override
		public void run(){
			Message mess;
			try {
				while(true){
	    			mess = mpass.receive();
		    		if (mess!=null){
		    			System.out.println("*******MESSAGE*****");
		    			System.out.println("Send from: " + mess.getSource());
		    			System.out.println("Content: "+ mess.getData().toString());
		    			System.out.println("SeqNum: "+ mess.getSequenceNumber());
		    			System.out.println("*******MESSAGE*****");
		    			System.out.println("What do you want to do? [Type in 's' or 'e' to end].");
		    			
		    		}
	    		}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	 public static void main(String[] args) throws IOException, InterruptedException {
		 
		 	System.out.println("args are - " + args[0] +" and "+ args[1]);
		 
			MessagePasser mpass = new MessagePasser(args[0],args[1]);
			new Thread(new Receiver(mpass)).start();
			
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    String str = null;
		    String s1 = null;
		    String s2 = null;
		    String s3 = null;
		    String src = args[1];
		    while(true){
		    	System.out.println("What do you want to do? [Type in 's' or 'e' to end].");
		    	try {
					str = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	if(str.equals("s")){
		    		System.out.println("[SEND]name?type?content?");
		    		s1 = br.readLine();
		    		s2 = br.readLine();
		    		s3 = br.readLine();
		    		Message mess = new Message(s1,s2,s3);
		    		mpass.send(mess);
		    	}/*else if (str.equals("r")){
		    		while(!mpass.recbuf.isEmpty()){
		    			Message mess = mpass.receive();
			    		if (mess!=null){
			    			System.out.println("*******MESSAGE*****");
			    			System.out.println("Send from: " + mess.getSource());
			    			System.out.println("Content: "+ mess.getData().toString());
			    			System.out.println("SeqNum: "+ mess.getSequenceNumber());
			    			System.out.println("*******MESSAGE*****");
			    		}
		    		}
		    	}*/else if (str.equals("e")){
		    		System.out.println("Exit Sucessfully");
		    		System.exit(0);
		    	}else{
		    		System.out.println("BAD INPUT!");
 
		    	}
		    }
	 }
}
