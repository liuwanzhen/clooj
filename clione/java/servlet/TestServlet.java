package clione.java.servlet;

import java.io.IOException;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clojure.lang.RT;

@WebServlet(name="TestServlet", urlPatterns={"/test"} )
public class TestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CLIONE_CORE_NS = "clione.clj.web.core";
	@Override
	public void init() throws ServletException {
		try {
			RT.loadResourceScript("clione/clj/web/url.clj");
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		URLClassLoader ul = URLClassLoader.newInstance(null);
				
		Map map = new HashMap();
		map.put("name", "zs");
		request.setAttribute("map", map);
		request.setAttribute("age", 10);
		request.setAttribute("tt", RT.var("clione.clj.web.url", "tt").get());
		
		System.out.println(RT.var("clione.clj.web.url", "tt").get());
		request.getRequestDispatcher("/test.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
