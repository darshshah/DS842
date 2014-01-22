import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MessagePasserTest {

	/**
	 * @param args
	 */
	 public static void main(String[] args) {
		 
		 	System.out.println("args are - " + args[0] +" and "+ args[1]);
		    Path filepath = Paths.get(args[0]);
		    Update_file monitor = new Update_file();
		    System.out.println("Hello from YAML");
		    
		    try {
				MessagePasser mpass = new MessagePasser(args[0],args[1]);
				System.out.println("step1");
				Message mess = new Message("bob", "ACK", "ABCD");
				System.out.println("step2");
				mpass.send(mess);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("New message Passer failed");
				e1.printStackTrace();
			}			
		    
		    try {
		       monitor.check_file(filepath);
		    } catch (InterruptedException e) {
		         System.out.println(e);
		    } catch (IOException e) {
		    	System.out.println(e);
			}
		  }
		}


