import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.*;
import org.yaml.snakeyaml.Yaml;


public class MPhelper {
	public MPhelper(){}
	public boolean containName(ArrayList<User> users, String name){
		int length = users.size();
		if (length == 0){
			System.out.println("Cannot find the name!");
			return false;
		}
		for (int i = 0; i < length; i++){
			if (name == users.get(i).name) {return true;}
		}
		System.out.println("Cannot find the name!");
		return false;
	}
	public String getIp(ArrayList<User> users, String name){
		int length = users.size();
		if (length == 0){
			System.out.println("getIp Error!");
			return null;
		}
		for (int i = 0; i < length; i++){
			if (name == users.get(i).name) {return users.get(i).ip;}
		}
		return null;
	}
	public int getPort(ArrayList<User> users, String name){
		int length = users.size();
		if (length == 0){
			System.out.println("getIp Error!");
			return -1;
		}
		for (int i = 0; i < length; i++){
			if (name == users.get(i).name) {return users.get(i).port;}
		}
		return -1;
	}
	public void parseConfigFile(String configuration_filename, ArrayList<User> users, ArrayList<Rule> sendRules, ArrayList<Rule> receiveRules){
		Yaml yaml = new Yaml();
		File file = new File(configuration_filename);
		String content = null;
		try{
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		Map<String, ArrayList<Map<String, String>>> configmap = (Map<String,ArrayList<Map<String, String>>>) yaml.load(content);
		
		List<Map<String, String>> configlist = configmap.get("configuration");
		for (int i = 0; i < configlist.size(); i++){
			users.add(new User(configlist.get(i).get("name"),configlist.get(i).get("ip"),Integer.parseInt(configlist.get(i).get("port"))));
		}
		
		configlist = configmap.get("sendRules");
		for (int i = 0; i < configlist.size(); i++){
			sendRules.add(new Rule(configlist.get(i).get("action"),configlist.get(i).get("src"),configlist.get(i).get("dest"),configlist.get(i).get("kind"),Integer.parseInt(configlist.get(i).get("seqNum"))));
		}
		
		configlist = configmap.get("receiveRules");
		for (int i = 0; i < configlist.size(); i++){
			receiveRules.add(new Rule(configlist.get(i).get("action"),configlist.get(i).get("src"),configlist.get(i).get("dest"),configlist.get(i).get("kind"),Integer.parseInt(configlist.get(i).get("seqNum"))));
		}
	}
}
