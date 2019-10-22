package core.team.Main.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import core.team.Main.CoreEssentials;

public class CommandRules implements CommandExecutor {
	
	private CoreEssentials instance;
	
	public CommandRules(CoreEssentials instance) {
		this.instance = instance;
		instance.getCommand("rules").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		File rules = new File(instance.getDataFolder(),"rules.txt");
		if(!rules.exists()) {
			rules.getParentFile().mkdirs();
			sender.sendMessage(instance.getMessages().getString("ErrorRulesNotFound").replace("&", "§"));
            copy(instance.getResource("rules.txt"), rules);
            return true;
		}
		try {

            // First of all, you need to define file you want to read

            // Setup BufferedReader
            BufferedReader br = new BufferedReader(new FileReader(rules));

            // Read line by line
            String line = null;
            sender.sendMessage(instance.getMessages().getString("rulesTop").replace("&", "§"));
            while ((line = br.readLine()) != null) {
            	sender.sendMessage(line.replace("&", "§"));
            }
            


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return true;
	}
	
	private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	
}
