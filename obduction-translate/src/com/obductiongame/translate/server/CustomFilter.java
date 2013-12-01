package com.obductiongame.translate.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class CustomFilter implements javax.servlet.Filter {  

	private static final Logger LOG = Logger.getLogger(CustomFilter.class.getName());

	private class SyncAllAjaxController extends NicelyResynchronizingAjaxController {
		private static final long serialVersionUID = 618521267116996955L;

		@Override
		public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
			return true;
		}
	}

	private final long pumpEventLoopTimeoutMillis = 200;
	private final long jsTimeoutMillis = 200;
	private final long pageWaitMillis = 100;
	private final int maxLoopChecks = 2;

	private WebClient webClient;

	@Override
	public void destroy() {
		if (webClient != null)
			webClient.closeAllWindows();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");

		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
			String queryString = httpRequest.getQueryString();

			if ((queryString != null) && (queryString.contains("_escaped_fragment_"))) {
				String[] splitQueryString = queryString.split("[?&]_escaped_fragment_=");

				StringBuffer originalRequest = httpRequest.getRequestURL();
				if (splitQueryString.length > 1) {
					originalRequest.append(splitQueryString[0]);
					String hashFragment = URLDecoder.decode(splitQueryString[1], "UTF-8");
					if (hashFragment.length() > 0) {
						originalRequest.append("#!");
						originalRequest.append(hashFragment);
					}
				}
				WebRequest webRequest = new WebRequest(new URL(originalRequest.toString()));

				WebClient webClient = new WebClient(BrowserVersion.FIREFOX_3_6);
				webClient.setJavaScriptEnabled(true);
				webClient.setThrowExceptionOnScriptError(false);
				webClient.setRedirectEnabled(false);
				webClient.setAjaxController(new SyncAllAjaxController());
				webClient.setCssErrorHandler(new SilentCssErrorHandler());

				LOG.log(Level.INFO, "HtmlUnit starting webClient.getPage(webRequest) where webRequest = " + webRequest.toString());
				HtmlPage page = webClient.getPage(webRequest);

				webClient.getJavaScriptEngine().pumpEventLoop(pumpEventLoopTimeoutMillis);

				int waitForBackgroundJavaScript = webClient.waitForBackgroundJavaScript(jsTimeoutMillis);
				int loopCount = 0;
				while ((waitForBackgroundJavaScript > 0) && (loopCount < maxLoopChecks)) {
					loopCount++;
					waitForBackgroundJavaScript = webClient.waitForBackgroundJavaScript(jsTimeoutMillis);

					if (waitForBackgroundJavaScript == 0) {
						LOG.log(Level.FINE, "HtmlUnit exits background javascript at loop counter " + loopCount);
						break;
					}

					synchronized (page) {
						LOG.log(Level.FINE, "HtmlUnit waits for background javascript at loop counter " + loopCount);
						try {
							page.wait(pageWaitMillis);
						}
						catch (InterruptedException e) {
							LOG.log(Level.SEVERE, "HtmlUnit ERROR on page.wait at loop counter " + loopCount);
							e.printStackTrace();
						}
					}
				}

				webClient.getAjaxController().processSynchron(page, webRequest, false);
				if (webClient.getJavaScriptEngine().isScriptRunning()) {
					LOG.log(Level.WARNING, "HtmlUnit webClient.getJavaScriptEngine().shutdownJavaScriptExecutor()");
					webClient.getJavaScriptEngine().shutdownJavaScriptExecutor();
				}

				String staticSnapshotHtml = page.asXml();
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(staticSnapshotHtml);

				webClient.closeAllWindows();
				out.flush();
				out.close();
			} else {
				if (response instanceof HttpServletResponse) {
					/*HttpServletResponse httpResponse = (HttpServletResponse)response;
					httpResponse.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
					httpResponse.setHeader("X-Frame-Options", "DENY");
					httpResponse.setHeader("X-XSS-Protection", "1; mode=block");
					httpResponse.setHeader("X-Content-Type-Options", "nosniff");*/
					/*httpResponse.setHeader("Content-Security-Policy", "sandbox; default-src 'none'; report-uri /cspReport");*/
				}
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
