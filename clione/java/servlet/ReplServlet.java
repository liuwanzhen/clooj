package clione.java.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import clojure.lang.RT;
import clojure.lang.Var;

@WebServlet(name="ReplServlet", urlPatterns="/repl")
public class ReplServlet extends HttpServlet {

	@Override
	public void init() throws ServletException {
		try {
			RT.loadResourceScript("clione/clj/repl/repl.clj");
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.init();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		byte[] buf = new byte[2048];
//		int len = request.getInputStream().read(buf);
		System.out.println(request.getParameter("expr"));
//		response.getOutputStream().write("{result:\"123\"}".getBytes());
//		response.getOutputStream().flush();
		
		Var repl =  RT.var("clione.clj.repl.repl", "repl");
		ByteArrayInputStream bis = new ByteArrayInputStream(request.getParameter("expr").getBytes());
		repl.invoke(bis,response.getOutputStream());
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
