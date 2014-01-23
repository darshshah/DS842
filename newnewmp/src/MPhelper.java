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
			System.out.println("Cannot find the nameasdfaf!");
			return false;
		}
		for (int i = 0; i < length; i++){
			if (name.equals(users.get(i).getName())) {return true;}
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
			if (name.equals(users.get(i).getName())) {return users.get(i).getIp();}
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
			if (name.equals(users.get(i).getName())) {return users.get(i).getPort();}
		}
		return -1;
	}
	public void parseConfigFile(String configuration_filename, ArrayList<User> users, ArrayList<Rule> sendRules, ArrayList<Rule> receiveRules){
		Yaml yaml = new Yaml();
		File file = new File(configuration_filename);
		String content = null;
		try{
			System.out.println("the name of the file is "+file);
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		
		Map<String, ArrayList<Map<String, Object>>> configmap = (Map<String,ArrayList<Map<String, Object>>>) yaml.load(content);
		
		List<Map<String, Object>> configlist = configmap.get("configuration");
					
		if(!users.isEmpty())
			users.clear();
			
		for (int i = 0; i < configlist.size(); i++){
			users.add(new User(configlist.get(i).get("name").toString(),configlist.get(i).get("ip").toString(),Integer.parseInt(configlist.get(i).get("port").toString())));
		}
		
		configlist = configmap.get("sendRules");
		System.out.println(configlist);
		
		if(!sendRules.isEmpty())
			sendRules.clear();
		
		for (int i = 0; i < configlist.size(); i++){
			Map<String, Object> temp= configlist.get(i);
			
			if (!temp.containsKey("action")){
				temp.put("action","");
			}
			if (!temp.containsKey("kind")){
				temp.put("kind","");
			}
			if (!temp.containsKey("src")){
				temp.put("src","");
			}
			if (!temp.containsKey("dest")){
				temp.put("dest","");
			}
			if (!temp.containsKey("seqNum")){
				temp.put("seqNum","-1");
			}
			if (!temp.containsKey("duplicate")){
				temp.put("duplicate","");
			}
			sendRules.add(new Rule(configlist.get(i).get("action").toString(),configlist.get(i).get("src").toString(),configlist.get(i).get("dest").toString(),configlist.get(i).get("kind").toString(),Integer.parseInt(configlist.get(i).get("seqNum").toString()), configlist.get(i).get("duplicate").toString()));
		}
		
		configlist = configmap.get("receiveRules");
		
		if(!receiveRules.isEmpty())
			receiveRules.clear();
		
		for (int i = 0; i < configlist.size(); i++){
			Map<String, Object> temp= configlist.get(i);
			if (!temp.containsKey("action")){
				temp.put("action","");
			}
			if (!temp.containsKey("kind")){
				temp.put("kind","");
			}
			if (!temp.containsKey("src")){
				temp.put("src","");
			}
			if (!temp.containsKey("dest")){
				temp.put("dest","");
			}
			if (!temp.containsKey("seqNum")){
				temp.put("seqNum","-1");
			}
			if (!temp.containsKey("duplicate")){
				temp.put("duplicate","");
			}
			receiveRules.add(new Rule(configlist.get(i).get("action").toString(),configlist.get(i).get("src").toString(),configlist.get(i).get("dest").toString(),configlist.get(i).get("kind").toString(),Integer.parseInt(configlist.get(i).get("seqNum").toString()),configlist.get(i).get("duplicate").toString()));
		}
	}
}
