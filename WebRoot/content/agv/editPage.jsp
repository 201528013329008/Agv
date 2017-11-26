<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<%@ include file="/header.jsp"%> 
<script type="text/javascript">
	$(function() {
		$("#submit").click(function(){
            var name = $("#name");
            var type = $("#type");
            var capacity = $("#capacity");
            var maxVel = $("#maxVel");
            var turnVel = $("#turnVel");
            if(name.val()===""){
                alertMsg("提示","AGV编号不能为空！");
                name.focus();
                return false;
            }
            if(type.val()===""){
                alertMsg("提示","车辆类型不能为空！");
                type.focus();
                return false;
            }
            if(capacity.val()===""){
                alertMsg("提示","最大负重不能为空！");
                capacity.focus();
                return false;
            }
            if(maxVel.val()===""){
                alertMsg("提示","最大直线行驶速度不能为空！");
                capacity.focus();
                return false;
            }
            if(turnVel.val()===""){
                alertMsg("提示","最大负重不能为空！");
                capacity.focus();
                return false;
            }
            $.post("<%=basePath%>agv/edit",$("#addform").serialize(),function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("编辑成功！");
                    setTimeout(function(){
                        location.href="<%=basePath%>agv/manager";
                    },2000);
                }else{
                    alert(data.msg);
                    return false;
                }
            });
        });
	});
</script>
<title>添加车辆</title>
</head>
<body>
<%@ include file="/menu.jsp"%> 
    <div class="mycontainer">
        <div id="forms" class="mt10">
            <div class="box">
                <div class="box_border">
                    <div class="box_top">
                        <b class="pl15">编辑车辆</b>
                    </div>
                    <div class="box_center">
                        <form method="POST" id="addform" class="jqtransform">
                            <table class="form_table pt15 pb15" width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tbody>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>AGV编号：</td>
                                        <td class="">
                                        	<input type="hidden" name="id" value="${agv.id}"/>
                                            <input type="text" name="name" id="name" value="${agv.name}" class="input-text lh30" size="40" />
                                            <span class="tipspan"></span>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>车辆类型：</td>
                                        <td class="">
                                            <input type="text" name="type" id="type"  value="${agv.type}" class="input-text lh30" size="40" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>最大负重：</td>
                                        <td class="">
                                            <input type="text" name="capacity" id="capacity"  value="${agv.capacity}" class="input-text lh30" size="40" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>最大直线行驶速度：</td>
                                        <td class="">
                                            <input type="text" name="maxVel" id="maxVel"  value="${agv.maxVel}" class="input-text lh30" size="40" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>拐弯速度：</td>
                                        <td class="">
                                            <input type="text" name="turnVel" id="turnVel"  value="${agv.turnVel}" class="input-text lh30" size="40" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td_right"><font color="red">*</font>用户状态：</td>
                                        <td class="">
                                            <input type="radio" name="state" id="state1" value="1" class="input-radio" 
                                             <c:if test="${agv.state=='1'}">checked="checked"</c:if>/>有效
                                            &nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="state" id="state2" value="0" class="input-radio"
                                              <c:if test="${agv.state=='0'}">checked="checked"</c:if> />无效
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
