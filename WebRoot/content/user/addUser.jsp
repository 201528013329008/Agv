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
            var dpwd = $("#dpwd");
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
            if(dpwd.val()===""){
                alertMsg("提示","管理员登录密码不能为空！");
                dpwd.focus();
                return false;
            }
            $.post("<%=basePath%>user/super/add",$("#addform").serialize(),function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("添加成功！");
                    setTimeout(function(){
                    	history.back(-1);
                    },500);
                }else{
                    alert(data.msg);
                    return false;
                }
            });
        });
	});
</script>
<title>添加用户</title>
</head>
<body>
<%@ include file="/menu.jsp"%> 
    <div class="mycontainer">
        <div id="forms" class="mt10">
            <div class="box">
                <div class="box_border">
                    <div class="box_top">
                        <b class="pl15">添加用户</b>
                    </div>
                    <div class="box_center">
                        <form method="POST" id="addform" class="jqtransform">
                            <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>登录账号：</td>
                                        <td class="">
                                            <input type="text" name="loginName" id="loginName" class="input-text lh30" size="40" />
                                            <span class="tipspan">建议添加英文账号</span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>管理员姓名：</td>
                                        <td class="">
                                            <input type="text" name="userName" id="userName" class="input-text lh30" size="40" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>登录密码：</td>
                                        <td class="">
                                            <input type="password" name="userPassword" id="userPassword" class="input-text lh30" size="40" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>用户类型：</td>
                                        <td class="">
                                            <input type="radio" name="userType" id="userType1" value="person" class="input-radio" checked="checked"/>普通用户
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="userType" id="userType2" value="super" class="input-radio" />管理员
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
