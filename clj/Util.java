package clj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Util {
	public static int bufLen = 4 * 1024 ;
	public static void writeInt(OutputStream out, int v) {

		try {
			out.write((v >>> 24) & 0xFF);
			out.write((v >>> 16) & 0xFF);
			out.write((v >>> 8) & 0xFF);
			out.write((v >>> 0) & 0xFF);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static byte[] readInputStreamToByteArray(InputStream is) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);

		byte[] buf = new byte[bufLen];
		int len = -1;
		while ((len = is.read(buf)) != -1) {
			bos.write(buf, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}
	
	public static void echoResult(String host ,String ret){
		System.out.println("***********		response from ["+host+"]	****************");
		System.out.println(ret);
	}
	
	

	public  static void echoPrompt(Const.MODE mode){
		System.out.print( mode.getPrompt());
	}
	
	public static boolean existInConstMODE(String text){
		
		for(Const.MODE m : Const.MODE.values()){
			if(text.trim().equals(m.name())){
				return true ;
			}
		}
		return false ;
	}
	
	public static boolean isShellCommand(String command){
		String cd = command.toLowerCase();
		if(Const.SHELL_COMMAND.containsKey(cd)){
			return true ;
		}
		return false ;
	}
}
