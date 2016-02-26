
import java.io.*;
import java.util.*;


/**	
 * This object stores the configuration parameters used in the ETL program. 
 *
*/
public class ETLConfig 
{

    /** Name used in properties file to indicate no logFileName or errFileName. */
    public final static String NO_FILE = "";
	
    /** Name of file to write log messages to.  If "stderr" or "stdout" then
     * messages are written to System.err or System.out respectively. */
    private String logFileName;
    
    /** The opened log file. */
    public PrintWriter log;
    

    /** Name of file to write error messages to.  If "stderr" or "stdout" then
     * messages are written to System.err or System.out respectively. */
    private String errFileName;
    /** The opened error file. */
	public PrintWriter err;


    /** A PrintWriter guaranteed not to go to a NullWriter (/dev/null). */
    public PrintWriter seriousErr;
    

    /** The properties object. */
	private Properties Propers = new Properties();

	/**
 	* Construct an ETLConfig object
 	* @param String  configFileName
 	* @return a new ETLConfig object
 	*/
	public ETLConfig(String configFileName) 
	{
		FileInputStream finputstream = null;
		try 
		{
			finputstream = new FileInputStream(configFileName);
		}
		catch (FileNotFoundException e)  {
			System.err.println("#### Config: properties file not found!");
			throw new IllegalArgumentException("Unable to find file \"" + configFileName + "\"");
		}
	
		try 
		{
			Propers.load(finputstream);
		}
		catch (IOException e) 
		{
			System.err.println("#### Config: properties file load error: " + e.getMessage());
		}
		
		
		openErrAndLogFiles();
   }


    /** Open the log and err files.  If filename is "stdout" or "stderr" the 
     * error or log messages are redirected to System.out or System.err.
     */
	private void openErrAndLogFiles() 
	{
		//open logFileName
		logFileName = Propers.getProperty("LOGFILENAME", NO_FILE);
        if (logFileName.equals(NO_FILE))
        {
            log = new PrintWriter(new NullWriter());
        }
		else if (logFileName.equals("stdout")) 
        {
            log = new PrintWriter(System.out, true);
        }
		else if (logFileName.equals("stderr")) 
        {
            log = new PrintWriter(System.err, true);
        }
		else 
        {
			try 
			{
				log = new PrintWriter(new FileWriter(logFileName), true);
			}
			catch (IOException e)
			{
				System.err.println("Unable to open log file \"" + logFileName +"\"");
                System.err.println("Exiting.");
                System.exit(1);
			}
		}
			
			
		//get ErrorFile
		errFileName = Propers.getProperty("ERRORFILENAME", NO_FILE);
        if (errFileName.equals(NO_FILE))
        {
            err = new PrintWriter(new NullWriter());
            seriousErr = new PrintWriter(System.out, true);
        }
        else if (errFileName.equals("stdout")) 
        {
            err = new PrintWriter(System.out, true);
            seriousErr = err;
        }
        else if (errFileName.equals("stderr")) 
        {
            err = new PrintWriter(System.err, true);
            seriousErr = err;
        }
		else if (!errFileName.equals(NO_FILE)){
			try 
			{
				err = new PrintWriter(new FileWriter(errFileName), true);
                seriousErr = err;
			}
			catch (IOException e) 
			{
				System.err.println("Unable to open error file \"" + errFileName +"\"");
                System.err.println("Exiting.");
                System.exit(1);
			}
		}
    }

	/** 
 	* Gets a property . 
 	* */
	public String getStringProperty(String name)
    {
        return Propers.getProperty(name);
    }

	/**
 	*  Gets an integer property. 
 	* */
	public int getIntProperty(String name)
    {
        String s = Propers.getProperty(name);
        
        if(s==null)
        {
        	return -1;
        }
        
        try 
        {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException nfe) 
        {
            err.println("Property \"" + name + "\" did not parse as an integer!");
            return -1;
        }
    }

	/**
	 *  Gets a float property. 
	 * */
	public float getFloatProperty(String name)
	{
		String s = Propers.getProperty(name);
       
		if(s==null)
		{
			return -1.0f;
		}
        
		try 
		{
			return Float.parseFloat(s);
		}
		catch (NumberFormatException nfe) 
		{
			err.println("Property \"" + name + "\" did not parse as a float!");
			return -1.0f;
		}
	}
	
	
	/**
	 *  Gets a double property. 
	 * */
	public double getDoubleProperty(String name)
	{
		String s = Propers.getProperty(name);
       
		if(s==null)
		{
			return -1.0;
		}
        
		try 
		{
			return Double.parseDouble(s);
		}
		catch (NumberFormatException nfe) 
		{
			err.println("Property \"" + name + "\" did not parse as a double!");
			return -1.0;
		}
	}
	
	/**
	 *  Gets a boolean property. 
	 * */
	public boolean getBooleanProperty(String name)
	{
		String s = Propers.getProperty(name);
       
		if(s==null)
		{
			return false;
		}
        
		if (s.equalsIgnoreCase("yes")
		|| s.equalsIgnoreCase("y") 
		|| s.equalsIgnoreCase("true")
		|| s.equalsIgnoreCase("1")) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	/** Converts all the properties to a CGI URL-encoded string */
	public String toString() 
    {
        String s = "";
        Enumeration e = Propers.propertyNames();
        while (e.hasMoreElements()) 
        {
            String key = (String) e.nextElement();
            String value = Propers.getProperty(key);

            if (!key.equals(""))
                s = s+key +" = "+ value+"\n";
        }
        return s;
    }


	/** Converts all the properties to a string */
	public void toFile(PrintWriter output) 
    {
        String s = "";
        Enumeration e = Propers.propertyNames();
        while (e.hasMoreElements()) 
        {
            String key = (String) e.nextElement();
            String value = Propers.getProperty(key);

            output.println(key + " = " + value);
        }
        
        output.flush();
    }
	/**
	 * Round the float number and return the rounded integer
	 * @param F float
	 * @return integer
	 */
	 public static int ETLRounding (Float F) 
	 {
		 float f = F.floatValue();
		 Double d;
		 int i;
        
		 if (f < 1 && f>0) 
		 {
			 i = 1;
		 }
		 else 
		 {
			 d = new Double(Math.floor((double)f));
			 i = d.intValue();
			 if ((double)f - d.doubleValue() >= 0.5) 
			 {
				 i++;
			 }
		 }
		 return i;        
	 }
}

final class NullWriter extends Writer 
{
	public NullWriter() 
	{
	}
    
	public void close()  throws IOException
	{
	}
	public void flush()  throws IOException
	{
	}
	public void write(char[] cbuf, int off, int len)  throws IOException
	{
	}
}
