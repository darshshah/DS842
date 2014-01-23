import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

public class Update_file implements Runnable {

	private Path fpath;
	private WatchService watchService;
	private WatchKey key;

	public Update_file(Path path) {
		this.fpath = path;
	}

	public void run() {
		// TODO Auto-generated method stub

		try {
			watchService = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fpath.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessagePasser mp = new MessagePasser();

		while (true) {

			try {
				key = watchService.take();
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
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
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// exit loop if the key is not valid (if the directory was deleted,
			// for example)
		}
	}

}