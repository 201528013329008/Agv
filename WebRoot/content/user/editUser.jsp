<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<%@ include file="/header.jsp"%> 
<script type="text/javascript">
	$(function() {
		$("#submit").click(function(){
            var loginName = $("#loginName");
            var userName = $("#userName");
            if(loginName.val()===""){
                alertMsg("提示","管理员登录账号不能为空！");
                loginName.focus();
                return false;
            }
            if(userName.val()===""){
                alertMsg("提示","管理员姓名不能为空！");
                userName.focus();
                return false;
            }
            
            $.post("<%=basePath%>user/super/edit",$("#editform").serialize(),function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("编辑成功！");
                    setTimeout(function(){
                        location.href="<%=basePath%>user/super/managePage";
                    },500);
                }else{
                    alert(data.msg);
                    return false;
                }
            });
        });
	});
</script>
<title>添加管理员</title>
</head>
<body>
<%@ include file="/menu.jsp"%>
    <div class="mycontainer">
        <div id="forms" class="mt10">
            <div class="box">
                <div class="box_border">
                    <div class="box_top">
                        <b class="pl15">管理员信息修改</b>
                    </div>
                    <div class="box_center">
                        <form method="POST" id="editform" class="jqtransform">
                            <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>登录账号：</td>
                                        <td class="">
                                        	<input type="hidden" name="userId" value="${user.userId }"/>
                                            <input type="text" name="loginName" id="loginName"
                                             class="input-text lh30" size="40" value="${user.loginName }"/>
                                            <span class="tipspan">建议英文账号</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>管理员姓名：</td>
                                        <td class="">
                                            <input type="text" name="userName" id="userName" class="input-text lh30" size="40" value="${user.userName }"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right">登录密码：</td>
                                        <td class="">
                                            <input type="password" name="userPassword" id="userPassword" class="input-text lh30" size="40" />
                                        	<span class="tipspan">留空则不修改密码</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>用户类型：</td>
                                        <td class="">
                                            <input type="radio" name="userType" id="userType1" value="person" class="input-radio" 
                                             <c:if test="${user.userType=='person'}">checked="checked"</c:if>/>普通用户
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="userType" id="userType2" value="super" class="input-radio"
                                              <c:if test="${user.userType=='super'}">checked="checked"</c:if> />管理员
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>用户状态：</td>
                                        <td class="">
                                            <input type="radio" name="userStatus" id="userStatus1" value="1" class="input-radio" 
                                             <c:if test="${user.userStatus=='1'}">checked="checked"</c:if>/>有效
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="userStatus" id="userStatus2" value="0" class="input-radio"
                                              <c:if test="${user.userStatus=='0'}">checked="checked"</c:if> />无效
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right">&nbsp;</td>
                                        <td class="">
                                            <input type="button" name="button" id="submit" class="mbtn mbtn82 mbtn_save2" value="提交" />
                                            <input type="reset" name="button" class="mbtn mbtn82 mbtn_res" value="重置" />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
