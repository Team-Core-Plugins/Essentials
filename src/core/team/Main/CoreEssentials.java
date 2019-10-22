package core.team.Main;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import core.team.Database.Database;
import core.team.Database.SQLite;

public class CoreEssentials extends JavaPlugin {
	
	private static CoreEssentials instance;
	private Map<String, Database> databases = new HashMap();
	
	public void onEnable()
	  {
	    getDataFolder().mkdirs();
	    instance = this;
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
	  
	  public Map<String, Database> getDatabases()
	  {
	    return this.databases;
	  }
	  
	  public Database getDatabase(String databaseName)
	  {
	    return (Database)getDatabases().get(databaseName);
	  }

}
