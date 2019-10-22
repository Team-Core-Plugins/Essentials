package core.team.Database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import core.team.Main.CoreEssentials;

public class SQLite
  extends Database
{
  private String dbname;
  private String createTestTable = "CREATE TABLE IF NOT EXISTS test (`test` varchar(32) NOT NULL,PRIMARY KEY (`test`));";
  private String customCreateString;
  private File dataFolder;
  
  public SQLite(String databaseName, String createStatement, File folder,CoreEssentials instance)
  {
	super(instance);
    this.dbname = databaseName;
    this.customCreateString = createStatement;
    this.dataFolder = folder;
  }
  
  public Connection getSQLConnection()
  {
    File folder = new File(this.dataFolder, this.dbname + ".db");
    if (!folder.exists()) {
      try
      {
        folder.createNewFile();
      }
      catch (IOException e)
      {
    	CoreEssentials.getInstance().getLogger().log(Level.SEVERE, "File write error: " + this.dbname + ".db");
      }
    }
    try
    {
      if ((this.connection != null) && (!this.connection.isClosed())) {
        return this.connection;
      }
      Class.forName("org.sqlite.JDBC");
      this.connection = DriverManager.getConnection("jdbc:sqlite:" + folder);
      return this.connection;
    }
    catch (SQLException ex)
    {
      CoreEssentials.getInstance().getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
    }
    catch (ClassNotFoundException ex)
    {
      CoreEssentials.getInstance().getLogger().log(Level.SEVERE, 
        "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
    }
    return null;
  }
  
  public void load()
  {
    this.connection = getSQLConnection();
    try
    {
      Statement s = this.connection.createStatement();
      s.executeUpdate(this.createTestTable);
      s.executeUpdate(this.customCreateString);
      s.close();
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    initialize();
  }
  
  public File getDataFolder()
  {
    return this.dataFolder;
  }
}
