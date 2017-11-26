<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<%@ include file="/header.jsp"%> 
<script type="text/javascript">
	$(function() {
		$("#submit").click(function(){
			var oldPassword = $("#oldPassword");
            var userPassword = $("#userPassword");
            var userPassword2 = $("#userPassword2");
            var dpwd = $("#dpwd");
            if(oldPassword.val()===""){
                alertMsg("提示","请输入原密码！");
                oldPassword.focus();
                return false;
            }
            if(userPassword.val()===""){
                alertMsg("提示","请输入新密码！");
                userPassword.focus();
                return false;
            }
            if(userPassword.val()!=userPassword2.val()){
                alertMsg("提示","前后密码输入不一致！");
                userPassword.focus();
                return false;
            }
            
            $.post("<%=basePath%>user/changepwd",$("#editform").serialize(),function(data){
                data = $.parseJSON(data);
                if(data.success){
                    var d = alertMsg("密码修改成功！");
                    setTimeout(function(){
                        d.remove();
                    },1000);
                }else{
                    alert(data.msg);
                    return false;
                }
            });
        });
	});
</script>
<title></title>
</head>
<body>
	<%@ include file="/menu.jsp"%> 
    <div class="mycontainer">
        <div id="forms" class="mt10">
            <div class="box">
                <div class="box_border">
                    <div class="box_top">
                        <b class="pl15">修改密码</b>
                    </div>
                    <div class="box_center">
                        <form method="POST" id="editform" class="jqtransform">
                            <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>原密码：</td>
                                        <td class="">
                                            <input type="password" name="oldPassword" id="oldPassword" class="input-text lh30" size="40" />
                                        	<span class="tipspan"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>新密码：</td>
                                        <td class="">
                                            <input type="password" name="userPassword" id="userPassword" class="input-text lh30" size="40" />
                                        	<span class="tipspan"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>确认密码：</td>
                                        <td class="">
                                            <input type="password" name="userPassword2" id="userPassword2" class="input-text lh30" size="40" />
                                        	<span class="tipspan"></span>
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
