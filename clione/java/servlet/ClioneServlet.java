package clione.java.servlet;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clojure.lang.RT;

@WebServlet(name="ClioneServlet", urlPatterns={"*.do","*.json","*.xml"} )
public class ClioneServlet extends HttpServlet {

	public static File CLASS_PATH = null ; 
	
	static{
		try {
			CLASS_PATH = new File(ClioneServlet.class.getResource("/").toURI());
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CLIONE_CORE_NS = "clione.clj.web.core";
	@Override
	public void init() throws ServletException {
		try {
			RT.loadResourceScript("clione/clj/web/core.clj");
			RT.loadResourceScript("clione.clj");
			RT.loadResourceScript("clione/clj/util.clj");
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long start = System.currentTimeMillis();
		
		//String path = request.getServletPath();
		Object ret =  RT.var(CLIONE_CORE_NS, "process-request").invoke(request,response);
		System.out.println(ret);
		
		
		//process-request
//		int index = path.lastIndexOf(".");
//		String urlPath = path.substring(0,index);
//		System.out.println(urlPath);
////		Object paramStr = RT.var(CLIONE_CORE_NS, "get-param-str").invoke(urlPath);
////		System.out.println(paramStr);
//		String paramStr = (String)RT.var(CLIONE_CORE_NS, "get-param-str").invoke(urlPath);
//		if(paramStr != null){
//			Object ret = null ;
//			if(paramStr == ""){
//				ret =  RT.var(CLIONE_CORE_NS, "apply-request").invoke(urlPath,null,request,response);
//			}else{
//				String[] params = paramStr.split(",");
//				Object[] cljParams = new Object[params.length - 1];
//				for(int i=0 ; i < params.length - 1 ; i++){
//					cljParams[i] = request.getParameter(params[i]);
//				}
//				ret =  RT.var(CLIONE_CORE_NS, "apply-request").invoke(urlPath,cljParams,request,response);
//			}
//			System.out.println(ret);
//		}
//		System.out.println("time elipse :"+(System.currentTimeMillis() - start));
		
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	public static void main(String[] args) {
		String csn = Charset.defaultCharset().name();
		System.out.println(csn);
	}
}
