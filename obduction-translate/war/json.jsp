<%@ page contentType="application/javascript; charset=UTF-8" language="java" %>

<%@ page import="com.obductiongame.translate.server.LanguageLoader" %>
<%@ page import="com.obductiongame.translate.shared.Language" %>

<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>

<%
	response.setHeader("Cache-Control", "public, max-age=600");/*expies*/

	Language[] languages = LanguageLoader.getLanguages();
	pageContext.setAttribute("languages", languages);
%>

var languages = <json:array var="language" items="${languages}">
					<json:object>
						<json:property name="name" value="${language.name}" />
						<json:property name="code" value="${language.code}" />
					</json:object>
				</json:array>;