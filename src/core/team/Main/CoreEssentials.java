package core.team.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import core.team.Database.Database;
import core.team.Database.SQLite;
import core.team.Main.commands.CommandRules;

public class CoreEssentials extends JavaPlugin {
	
	private static CoreEssentials instance;
	private Map<String, Database> databases = new HashMap();
	
	File configFile;
	File LangFile;
    
    FileConfiguration lang;
	FileConfiguration config;
	
	public void onEnable()
	  {
	    getDataFolder().mkdirs();
	    instance = this;
	    
	    configFile = new File(getDataFolder(), "config.yml");
	    LangFile = new File(getDataFolder(), "lang.yml");
	    
	    try {
            firstRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
	    
	    config = new YamlConfiguration();
	    lang = new YamlConfiguration();
	    
	    loadYamls();
	    
	    new CommandRules(this);
	  }
	  
	  public void onDisable() {}
	  
	  public static CoreEssentials getInstance()
	  {
	    return instance;
	  }
	  
	  public static CoreEssentials hookSQLiteLib(Plugin hostPlugin)
	  {
		  CoreEssentials plugin = (CoreEssentials)Bukkit.getPluginManager().getPlugin("CoreEssentials");
	    if (plugin == null)
	    {
	      Bukkit.getLogger().severe("CoreEssentials (SQLiteAPI) is not yet ready! You have you called hookSQLiteLib() too early.");
	      return null;
	    }
	    return plugin;
	  }
	  
	  public void initializeDatabase(String databaseName, String createStatement)
	  {
	    Database db = new SQLite(databaseName, createStatement, getDataFolder(),instance);
	    db.load();
	    this.databases.put(databaseName, db);
	  }
	  
	  public void initializeDatabase(Plugin plugin, String databaseName, String createStatement)

	  {
	    Database db = new SQLite(databaseName, createStatement, plugin.getDataFolder(),instance);
	    db.load();
	    this.databases.put(databaseName, db);
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
	  
	  private void firstRun() throws Exception {
	        if(!configFile.exists()){
	        	getLogger().log(Level.WARNING,"Couldn't find config.yml, creating one!...");
	            configFile.getParentFile().mkdirs();
	            copy(getResource("config.yml"), configFile);
	            getLogger().log(Level.INFO,"Created config.yml file");
	        }
	        if(!LangFile.exists()){
	        	getLogger().log(Level.WARNING,"Couldn't find lang.yml, creating one!...");
	            configFile.getParentFile().mkdirs();
	            copy(getResource("lang.yml"), LangFile);
	            getLogger().log(Level.INFO,"Created lang.yml file");
	        }
	    }
	  
	  public void loadYamls() {
	        try {
	            config.load(configFile);
	            getLogger().log(Level.INFO,"Loaded config.yml");
	            lang.load(LangFile);
	            getLogger().log(Level.INFO,"Loaded lang.yml");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public void saveYamls() {
	    	try {
				lang.save(LangFile);
				getLogger().log(Level.INFO,"Saved lang.yml files...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	  
	  public Map<String, Database> getDatabases()
	  {
	    return this.databases;
	  }
	  
	  public Database getDatabase(String databaseName)
	  {
	    return (Database)getDatabases().get(databaseName);
	  }

	public FileConfiguration getMessages() {return lang;}
	public FileConfiguration getConfig() {return config;}


}
