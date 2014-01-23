import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MessagePasserTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	 public static void main(String[] args) throws IOException, InterruptedException {
		 
		 	System.out.println("args are - " + args[0] +" and "+ args[1]);
		    Path filepath = Paths.get(args[0]);
		    Update_file monitor = new Update_file();
		    System.out.println("Hello from YAML");
		    
			MessagePasser mpass = new MessagePasser(args[0],args[1]);
			
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    String str = null;
		    String s1 = null;
		    String s2 = null;
		    String s3 = null;
		    while(true){
		    	System.out.println("What do you want to do? [Type in 's' or 'r'].");
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
		    	}else if (str.equals("r")){
		    		Message mess = mpass.receive();
		    		if (!mess.equals(null)){
		    			System.out.println("*******MESSAGE*****");
		    			System.out.println("Send from: " + mess.getSource());
		    			System.out.println("Content: "+ mess.getData().toString());
		    			System.out.println("*******MESSAGE*****");
		    		}
		    	}else{
		    		System.out.println("BAD INPUT!");
		    	}
		    }
	 }
}
		    
		    /*try {
				MessagePasser mpass = new MessagePasser(args[0],args[1]);
				System.out.println("step1");
				Message mess = new Message("bob", "ACK", "ABCD");
				System.out.println("step2");
				mpass.send(mess);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("New message Passer failed");
				e1.printStackTrace();
			}	*/		
		    
		   /* try {
		       monitor.check_file(filepath);
		    } catch (InterruptedException e) {
		         System.out.println(e);
		    } catch (IOException e) {
		    	System.out.println(e);
			}*/


