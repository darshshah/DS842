import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

public class Update_file {

   public void check_file(Path path) throws IOException, InterruptedException {
      
     try {
     WatchService watchService = FileSystems.getDefault().newWatchService();
     path.register(watchService,StandardWatchEventKinds.ENTRY_MODIFY);
     MessagePasser mp = new MessagePasser();
     
     while (true) {
 
            WatchKey key = watchService.take();
            for (WatchEvent<?> watchEvent : key.pollEvents()) 
            {
            	 Kind<?> kind = watchEvent.kind();
            	 
                if (kind == StandardWatchEventKinds.OVERFLOW) {
                   continue;
                }
                System.out.println("Modified");
                mp.Update_Config();
            }
             key.reset();
             boolean valid = key.reset();
             if (!valid) {
            	 break;
            }
             //exit loop if the key is not valid (if the directory was deleted, for example)
         	}
     }
     finally{}
    }
}
  