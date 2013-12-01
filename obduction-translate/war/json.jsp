<%@ page trimDirectiveWhitespaces="true" %>
<%@ page contentType="application/javascript; charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.obductiongame.translate.server.LanguageLoader" %>
<%@ page import="com.obductiongame.translate.shared.Language" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<%
	/*long cacheTimeSeconds = 30 * 24 * 60 * 60;
	response.setHeader("Cache-Control", "public, max-age=" + cacheTimeSeconds);
	response.setDateHeader("Expires", new Date().getTime() + cacheTimeSeconds * 1000);*/

	Language[] languages = LanguageLoader.getLanguages();
	pageContext.setAttribute("languages", languages);
%>
var languages = <json:array var="language" items="${languages}">
					<json:object>
						<json:property name="name" value="${language.name}" />
						<json:property name="code" value="${language.code}" />
					</json:object>
				</json:array>;