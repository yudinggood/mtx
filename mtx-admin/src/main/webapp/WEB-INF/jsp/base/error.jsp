<!-- base/error.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Page-Enter" content="blendTrans(Duration=0.2)" />
    <meta http-equiv="Page-Exit" content="blendTrans(Duration=0.2)" />
    <%@ include file="base.jsp"%>
    <script type="text/javascript">
        EHM.ImportError();
    </script>
</head>
<body>
<%
    Exception e = null != exception ? (Exception) exception : (Exception)request.getAttribute("ex");
    String description = e.getMessage();
%>
<p>
    &nbsp;&nbsp;
</p>

<table border=0 cellpadding=1 cellspacing=1 class="form_table" width="95%" align="center">
    <thead><tr>
        <th nowrap >
            <div align="left"> 出错信息</div>
        </th>
    </tr></thead>
    <tbody>
    <tr>
        <td height="150px" bgcolor="#f4f4f4" style='text-align:center' valign="middle">
            <img align="middle" height="15px" width="15px" src='${basePath}/resources/mtx-admin/image/error_icon.png'>

            <%= description%>

        </td>
    </tr>
    <tr>
        <td  bgcolor="#f4f4f4" style='line-height:140%;' >
            错误详情：</br>
            <% e.printStackTrace(new java.io.PrintWriter(out)); %>

        </td>
    </tr>
    </tbody>
</table>

<div class="columns-3-aabbC-aa">
    <p align="center">
        <input type="button" onclick="toBack()" value="返&nbsp;&nbsp;回" class="btn4"/>
    </p>
</div>
<script type="text/javascript">

    function toBack() {
        if (window.opener == null){
            window.history.go(-1);
        }else {
            window.close();
        }
    }
</script>
</body>
</html>
