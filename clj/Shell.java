package clj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Shell {

	public static byte[] execute(String command) throws IOException {
		Process proc = Runtime.getRuntime().exec("/bin/bash");
		ByteArrayOutputStream bos = null ;
		if (proc != null) {
			InputStream is = proc.getInputStream();
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(proc.getOutputStream())), true);
			out.println(command);
			out.println("exit");
			try {
				byte[] buf = new byte[1024];
				int len = -1;
				bos = new ByteArrayOutputStream();
				while ((len = is.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}
				proc.waitFor();
				is.close();
				out.close();
				proc.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bos.toByteArray();
	}
	
	public static void loop() throws Exception{
		Process proc = Runtime.getRuntime().exec("/bin/bash");
		ByteArrayOutputStream bos = null ;
		if (proc != null) {
			InputStream is = proc.getInputStream();
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(proc.getOutputStream())), true);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String command = null ;
			while ((command = br.readLine()) != null ) {
				out.println(command );
				
				//proc.waitFor();
				//out.println("exit" );
				byte[] buf = new byte[1024];
				int len = -1;
				bos = new ByteArrayOutputStream();
				while ((len = is.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}
				System.out.println(new String(bos.toByteArray()));
				
			}
			
			//out.println("exit");
//			try {
//				byte[] buf = new byte[1024];
//				int len = -1;
//				bos = new ByteArrayOutputStream();
//				while ((len = is.read(buf)) != -1) {
//					bos.write(buf, 0, len);
//				}
//				proc.waitFor();
//				is.close();
//				out.close();
//				proc.destroy();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
	}

	public static void main(String[] args) throws Exception {
//		byte[] bf = execute(args[0]);
//		System.out.println(new String(bf));
		loop();
	}
}
