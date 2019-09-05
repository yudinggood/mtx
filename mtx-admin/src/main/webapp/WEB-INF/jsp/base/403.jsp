<!-- base/403.jsp-->
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
<p>
    &nbsp;&nbsp;
</p>

<table border=0 cellpadding=1 cellspacing=1 class="form_table" width="95%" align="center">
    <thead><tr>
        <th nowrap >
            <div align="left"> 403没有访问权限</div>
        </th>
    </tr></thead>
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
