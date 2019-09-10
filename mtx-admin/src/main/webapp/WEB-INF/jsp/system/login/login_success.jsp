<!--system/test/test.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>


    <style>

    </style>
    <script>

    </script>
</head>
<body>




<script>
    var LoginSuccess = {

    };
    $(function () {
        window.close();
        window.opener.location.href='${backurl}';

    });


</script>
</body>
</html>
