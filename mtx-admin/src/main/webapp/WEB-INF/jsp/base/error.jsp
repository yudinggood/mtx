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
    <style type="text/css">
        .highlight {
            background-color: #fff34d;
            -moz-border-radius: 5px; /* FF1+ */
            -webkit-border-radius: 5px; /* Saf3-4 */
            border-radius: 5px; /* Opera 10.5, IE 9, Saf5, Chrome */
            -moz-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.7); /* FF3.5+ */
            -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.7); /* Saf3.0+, Chrome */
            box-shadow: 0 1px 4px rgba(0, 0, 0, 0.7); /* Opera 10.5+, IE 9.0 */
        }

        .highlight {
            padding:1px 4px;
            margin:0 -4px;
        }
    </style>
</head>
<body>
<%--<%
    Exception e = null != exception ? (Exception) exception : (Exception)request.getAttribute("ex");
    String description = e.getMessage();
%>--%>
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

            <%--<%= description%>--%>
            ${code}
        </td>
    </tr>
    <tr>
        <td  bgcolor="#f4f4f4" style='line-height:140%;white-space: pre-line;' >
            错误详情：
            <%--<% e.printStackTrace(new java.io.PrintWriter(out)); %>--%>
            ${message}
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
    $(function() {
        $('body').highlight( "mtx" );
    });

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
