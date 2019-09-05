<!--system/user/user_view.jsp-->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../base/base.jsp"%>


    <style>

    </style>
    <script>
        EHM.ImportSelect();
    </script>
</head>
<body>

<form id="form">
    <table class="table table-bordered table-form" align="center" style="width: 98%">

            <tr>
                <th width="20%"><label class="Validform_label">昵称:</label></th>
                <td width="80%">
                    <p style="margin-bottom: 0px;" class="">${systemUserVo.nickName}</p>
                </td>
            </tr>
            <tr>
                <th><label class="Validform_label">手机号:</label></th>
                <td>
                    <p style="margin-bottom: 0px;" class="">${systemUserVo.phone}</p>
                </td>
            </tr>
            <tr>
                <th><label class="Validform_label">用户组织:</label></th>
                <td>
                    <div class="form-group">
                        <select id="organizationId" name="organizationId" multiple="multiple" class="form-control select2" disabled>
                            <c:forEach var="orgLists" items="${orgList}">
                                <option value="${orgLists.code}"
                                        <c:forEach var="orgListSelects" items="${orgListSelect}">
                                            <c:if test="${orgListSelects.code==orgLists.code}">selected="selected"</c:if>
                                        </c:forEach>>
                                        ${orgLists.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th><label class="Validform_label">用户角色:</label></th>
                <td>
                    <div class="form-group">
                        <select id="roleId" name="roleId" multiple="multiple" class="form-control select2" disabled>
                            <c:forEach var="roleLists" items="${roleList}">
                                <option value="${roleLists.code}"
                                        <c:forEach var="roleListSelects" items="${roleListSelect}">
                                            <c:if test="${roleListSelects.code==roleLists.code}">selected="selected"</c:if>
                                        </c:forEach>>
                                        ${roleLists.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th>邮箱:</th>
                <td>
                    <p style="margin-bottom: 0px;" class="">${systemUserVo.email}</p>
                </td>
            </tr>
            <tr>
                <th>姓名:</th>
                <td>
                    <p style="margin-bottom: 0px;" class="">${systemUserVo.realName}</p>
                </td>
            </tr>
            <tr>
                <th>性别:</th>
                <td>
                    <p style="margin-bottom: 0px;" class="">${systemUserVo.sexName}</p>
                </td>
            </tr>
            <tr>
                <th>状态:</th>
                <td>
                    <p style="margin-bottom: 0px;" class="">${systemUserVo.userStateName}</p>
                </td>
            </tr>
        <tr>
            <th>最后登录IP:</th>
            <td>
                <p style="margin-bottom: 0px;" class="">${systemUserVo.lastIp}</p>
            </td>
        </tr>
        <tr>
            <th>最后登录时间:</th>
            <td>
                <p style="margin-bottom: 0px;" class="lastLogin"></p>
                <input type="hidden" id="lastLogin" value="${systemUserVo.lastLogin}">
            </td>
        </tr>
        <tr>
            <th>修改时间:</th>
            <td>
                <p style="margin-bottom: 0px;" class="editDate"></p>
                <input type="hidden" id="editDate" value="${systemUserVo.editDate}">
            </td>
        </tr>

        <tr>
            <td colspan="2">
                <div align="center">
                    <button type="button" title="关闭" class="btn btn-primary btn-xs" onClick="top.layer.close(top.layer.index);" style="width: 100px;">
                        <span class="fa fa-close"></span>
                        <span style="padding-top:2px">&nbsp;关&nbsp;闭&nbsp;</span>
                    </button>
                </div>
            </td>
        </tr>
    </table>
</form>


<script>
    var UserView = {

    };
    $(function () {
        if(!$("#editDate").val() == ""){
            var editDate=dateToGMT($("#editDate").val()).format("yyyy-MM-dd HH:mm:ss");
            $(".editDate").html(editDate);
        }
        if(!$("#lastLogin").val() == ""){
            var editDate=dateToGMT($("#lastLogin").val()).format("yyyy-MM-dd HH:mm:ss");
            $(".lastLogin").html(editDate);
        }
        $(".select2").select2();

    });


</script>
</body>
</html>
