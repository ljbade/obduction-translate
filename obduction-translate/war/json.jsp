<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="application/javascript;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.obductiongame.translate.server.LanguageLoader" %>
<%@ page import="com.obductiongame.translate.shared.Language" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%
	/*response.setHeader("Cache-Control", "public, max-age=600");
	response.setDateHeader("Expires", new Date().getTime() + 600 * 1000);*/

	/*response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
	response.setHeader("X-Frame-Options", "DENY");
	response.setHeader("X-XSS-Protection", "1; mode=block");
	response.setHeader("X-Content-Type-Options", "nosniff");*/
	/*response.setHeader("Content-Security-Policy", "sandbox; default-src 'none'; report-uri /cspReport");*/

	Language[] languages = LanguageLoader.getLanguages();
	pageContext.setAttribute("languages", languages);
%>
var languages = <json:array var="language" items="${languages}">
					<json:object>
						<json:property name="name" value="${language.name}" />
						<json:property name="code" value="${language.code}" />
					</json:object>
				</json:array>;