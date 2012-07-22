package clj;
import java.io.*;
import java.util.*;

public class CmdProcessBuilder {
  public static void main(String args[]) 
     throws InterruptedException,IOException 
  {
	  
    List<String> command = new ArrayList<String>();
    command.add("cd e:/temp");


    ProcessBuilder builder = new ProcessBuilder(command);
  //  Map<String, String> environ = builder.environment();

    final Process process = builder.start();
    InputStream is = process.getInputStream();
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    String line;
    while ((line = br.readLine()) != null) {
      System.out.println(line);
    }
    System.out.println("Program terminated!");
  }
}