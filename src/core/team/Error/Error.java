package core.team.Error;

import java.util.logging.Level;

import core.team.Main.CoreEssentials;

public class Error {
	
	public static void execute(CoreEssentials plugin, Exception ex) {
		plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
	}
	
	public static void close(CoreEssentials plugin, Exception ex) {
		plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
	}
	
}
