<!-- base/base.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<meta name="keywords" content="i助理"/>
<meta name="description" content="i助理 你的个人小助手"/>
<meta name="renderer" content="webkit"/>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<%
    String domain = request.getServerName()+":"+request.getServerPort()+request.getContextPath();//  admin.mtx.com:1111
%>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<c:set var="domain" value="<%=domain%>"/>
<title>i助理 - 个人小助手</title>
<link rel="shortcut icon" href="${basePath}/resources/mtx-admin/image/favicon.ico" type="image/x-icon" />
<script type="text/javascript" src="${basePath}/resources/mtx-admin/js/EHM/base.js"></script>
