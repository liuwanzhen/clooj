package clj;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Client {

	// private static String HOST = "localhost";
	private static Set<String> HOST_SET = new HashSet<String>();
	public static String ENCODING = "UTF-8";

	static {
		HOST_SET.add("172.31.184.16");
//		HOST_SET.add("172.31.184.15");

	}

	private static int PORT = 13579;

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		// System.out.println(args.length);
		Session session = new Session();
		session.begin();
		// String[] s = "1 2    3 4    5".split(" ");
		// System.out.println(s.length);
		// for(String ss : s){
		// System.out.println(ss);
		// }

	}

	public static class Session {
		private BufferedReader br = null;

		// private String mode = "clj";

		private Const.MODE mode = Const.MODE.shell;
		/**
		 * 0001 表示是文本协议，0002表示是二进制协议，服务器端会根据协议不同区别对待
		 */
		private String protocal = "0001";

		private String line = "";

		private boolean firstTime = true;

		/**
		 * true 表示已经对commond进行了处理 false 表示不是commond，需要进一步处理
		 * 
		 * @return
		 */
		private boolean switchModeIfNessary() {
			String lineTrim = line.trim();
			 
			if (Util.existInConstMODE(lineTrim)) {
				mode = Const.MODE.valueOf(lineTrim);
				return true;
			}

			return false ;

		}

		private void formulizeShell() {
			String[] shellLine = line.split(" ");
			List<String> list = new ArrayList<String>();
			for (String item : shellLine) {
				if (item == null || "".equals(item) || " ".equals(item)) {
					continue;
				}
				list.add(item.trim());
			}
			StringBuilder sb = new StringBuilder();
			for (String it : list) {
				sb.append(it).append(" ");
			}
			line = sb.toString().trim();
		}

		public void begin() throws Exception {

			br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("connect to " + HOST_SET.toString() + " ...");

			Set<Socket> socketSet = new HashSet<Socket>();

			if (firstTime) {
				boolean sucess = true;
				for (String host : HOST_SET) {
					try {
						Socket socket = new Socket(host, PORT);
						System.out.println("连接主机【" + host + "】，端口【" + PORT
								+ "】......      [  OK  ]");
						socketSet.add(socket);
					} catch (Exception e) {
						sucess = false;
						System.out.println("连接主机【" + host + "】，端口【" + PORT
								+ "】......      [ ERROR ]");

					}

				}
				if (!sucess) {
					return;
				}
				System.out.println("connection has been establish.");
				Util.echoPrompt(this.mode);
			}

			while ((line = br.readLine()) != null) {

				line = line.trim();
				if ("".equals(line)
						|| switchModeIfNessary()) {
					Util.echoPrompt(mode);
					continue;
				}
				
					switch (mode) {
					case clj:
						break;
					case shell:
						formulizeShell();
						break;
					case command:
						if(Util.isShellCommand(line)){
							mode = Const.MODE.shell;
							line = Const.SHELL_COMMAND.get(line.trim());
						}else{
							System.out.println("local command :" + line);
						}
						break ;
					}
				

				if (!firstTime) {
					socketSet.clear();
					for (String host : HOST_SET) {
						Socket socket = new Socket(host, PORT);
						socketSet.add(socket);
					}
				}
				firstTime = false;

				for (Socket soc : socketSet) {
					Worker worker = new Worker(soc, mode, line, protocal);
					Thread t = new Thread(worker);
					t.start();
					t.join();
				}
				Util.echoPrompt(this.mode);

			}

		}

		public void close() {

		}

	}

}
