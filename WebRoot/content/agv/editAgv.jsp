<%@ page language="java" import="agv.pageModel.Agv" pageEncoding="utf-8"%>
<%
Agv agv = (Agv)request.getAttribute("agv");
if (agv == null){
	agv = new Agv();
}
%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<%@ include file="/header.jsp"%>
<script type="text/javascript">
var agvId = <%=agv.getId() %>;
var operation = '';
if (agvId!=null){
	operation = "edit";
}else{
	operation = "add";
}

$(function(){
		
	//加载小车类型数据
	$.get("<%=basePath%>agv/getAgvTypeList",function(jqGrid,status){
		var jqGrid = eval("("+jqGrid+")");
		var selector = $("#agvType_selector");
		
      	if (jqGrid.success){
      		var datas = jqGrid.data;
      		for (i in datas){
      			selector.append('<option id=typeOption'+datas[i].id+' value='+datas[i].id+'>'+datas[i].name+'</option>');
      		}
      		if (operation=="edit"){
      			$("#typeOption"+$("#agvTypeId").val()).attr("selected","true");
			}
      	}else{
      		alert("获取车辆类型数据失败！");
      	}
		
    });
	
	if (operation=="edit"){
		$("#panelTitle").text("修改车辆");
		$("#forbiddenOption"+$("#forbidden").val()).attr("selected","true");
	}else if(operation=="add"){
		$("input.form-control").val(""); //所有置空
		$("#panelTitle").text("添加车辆");
	}
	
	$("#submit").click(function(){
		var name = $("#name").val();
		
		$("#typeId").val($("#agvType_selector").val());
		$("#forbidden").val($("#forbidden_selector").val());
		
		if(name == ""){
			layer.msg('请输入任务名称', {icon: 0,time: 2000});
			$("#name").focus();
			return false;
		}
        
		if (operation=="add"){
            $.post("<%=basePath%>agv/add", $("#agvForm").serialize(), function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("添加成功！");
                    setTimeout(function(){
                    	history.back(-1);
                    },500);
                }else{
                	alertMsg(data.msg);
                    return false;
                }
            });
		}else if (operation=="edit"){
			$.post("<%=basePath%>agv/edit", $("#agvForm").serialize(), function(data){
                data = $.parseJSON(data);
                if(data.success){
                    alertMsg("修改成功！");
                    setTimeout(function(){
                    	history.back(-1);
                    },500);
                }else{
                	alertMsg(data.msg);
                    return false;
                }
            });
		}
    });
});
	
	

</script>
<title>编辑车辆</title>
</head>
<body>
    <div class="mycontainer">
		<form method="POST" id="agvForm" style="max-width:500px">
			<input class="form-control" type="hidden" name="id" id="id"	value="<%=agv.getId() %>"> 
			<input class="form-control" type="hidden" name="typeId" id="typeId" value="<%=agv.getTypeId() %>"> 
			<input class="form-control" type="hidden" name="forbidden" id="forbidden" value="<%=agv.getForbidden()==null?null:(agv.getForbidden()?1:0)%>"> 
			
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 id="panelTitle" class="panel-title"></h3>
				</div>
				<div class="panel-body">
					<div class="input-group">
						<span class="input-group-addon" id="basic-addon5">车辆名称</span> 
					<input	id="name" name="name" type="text" class="form-control"
							placeholder="请输入车辆名称" aria-describedby="basic-addon5"
							value="<%=agv.getName() %>">
					</div>
					<br>
					<div class="input-group">
						<span class="input-group-addon" id="basic-addon4">车辆类型</span> <select
							id="agvType_selector" class="form-control">
						</select>
					</div>
					<br>
					<div class="input-group">
						<span class="input-group-addon" id="basic-addon1">是否禁用</span> 
						<select
							id="forbidden_selector" class="form-control">
							<option id="forbiddenOption0" value="0">否</option>
							<option id="forbiddenOption1" value="1">是</option>
						</select>
					</div>
					<br>
					<div class="btn-group btn-group-justified" role="group"
						aria-label="ok">
						<div class="btn-group" role="group">
							<input type="button" class="btn btn-success" id="submit"
								value="确定">
						</div>
						<div class="btn-group" role="group">
							<input type="button" class="btn btn-default" id="cancel"
								value="取消" onClick="history.back();">
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
