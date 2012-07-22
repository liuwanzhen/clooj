package clj;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import clj.Client.Session;

public  class Worker implements Runnable{

		private Socket socket = null ;
		private Const.MODE mode = null ;
		private String line = null ;
		private OutputStream out = null ;
		private String protocal = null ;
		
		public Worker(Socket socket,Const.MODE mode,String line,String protocal){
			this.socket = socket ;
			this.mode = mode ;
			this.line = line ;
			this.protocal = protocal ;
		}
		@Override
		public void run() {
			try {
				out = socket.getOutputStream();
				String reqContent = mode.name() +":"+line ;
				System.out.println("reqContent="+reqContent);
				byte[] reqContentBytes = reqContent.getBytes();
				out.write(protocal.getBytes());
				Util.writeInt(out, reqContentBytes.length);
				out.write(reqContentBytes);
				out.flush();

				byte[] responseContent = Util.readInputStreamToByteArray(socket.getInputStream());
				socket.close();
				String ret = new String(responseContent,Client.ENCODING);
				Util.echoResult(socket.getRemoteSocketAddress().toString(),ret);

				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}