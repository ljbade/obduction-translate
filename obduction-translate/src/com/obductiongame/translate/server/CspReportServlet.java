package com.obductiongame.translate.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.*;

@SuppressWarnings("serial")
public class CspReportServlet extends HttpServlet {

	private static final Logger LOG = Logger.getLogger(CspReportServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");

		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = req.getReader();

		String line;
		while((line = reader.readLine()) != null){
			buffer.append(line);
		}

		String input = buffer.toString();

		LOG.log(Level.WARNING, "CSP Report:\n" + input);

		resp.getWriter().println("Reported.");
	}

}