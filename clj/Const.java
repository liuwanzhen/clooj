package clj;

import java.util.HashMap;
import java.util.Map;

public class Const {
	public static Map<String,String> SHELL_COMMAND = new HashMap<String,String>();
	
	static{
		SHELL_COMMAND.put("lls", "ls");
	}

	public enum MODE{
		clj("clj=>","切换至clojure模式，此模式下可以输入任意clojure代码") ,
		shell( "shell->\t","切换至shell模式，此模式下可以输入任意linux shell命令，如 ls"),
		command( "command->\t","切换至command模式，此模式下可以");
		
		private String prompt ;
		private String helpText ;
		
		
		private MODE( String prompt, String helpText) {
			this.prompt = prompt;
			this.helpText = helpText;
		}

		public String getPrompt() {
			return prompt;
		}
		 
		public String getHelpText() {
			return helpText;
		}
		
	}
//	public static Map<String,String> COMMOND = new HashMap<String,String>(){{
//		put("clj", "切换至clojure模式，此模式下可以输入任意clojure代码");
//		put("shell", "切换至shell模式，此模式下可以输入任意linux shell命令，如 ls");
//	}};
//	
//	
//	public static Map<String,String> MODE_MAP_PROMPT = new HashMap<String,String>(){{
//		put("clj", "=>");
//		put("shell", ">");
//	}};
	
	public static void main(String[] args) {
			System.out.println(SHELL_COMMAND);
		
	}
}
