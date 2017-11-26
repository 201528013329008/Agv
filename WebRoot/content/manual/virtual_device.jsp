<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/header.jsp"%> 
<script type="text/javascript">
	var gridObj;
	$(function() {
		$.get("<%=basePath%>agv/dataGrid",function(jqGrid,status){
			var jqGrid = eval("("+jqGrid+")");
			var selector = $("#agv_selector");
			
	      	if (jqGrid.success){
	      		var datas = jqGrid.data;
	      		for (i in datas){
	      			selector.append('<option value='+datas[i].id+'>'+datas[i].name+'</option>');
	      		}
	      		
	      	}else{
	      		alert("获取小车数据失败！");
	      	}
			
	    });
	});
   
</script>

<title>人工控制</title>
</head>
<body>
<%@ include file="/menu.jsp"%> 
    <div class="mycontainer">
		<table class="non_border_table pt15 pb15 mgridTable">
			<tbody>
				<tr>
					<td>
						<div class="panel panel-primary" style="width:300px">
							<div class="panel-heading">
								<h3 id="panelTitle" class="panel-title">充电设备控制</h3>
							</div>
							<div class="panel-body">
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">设备编号</span> 
									<select id="charge_selector" class="form-control"
										style="background: #f7fcfe;">
										<option>CG001</option>
										<option>CG002</option>
										<option>CG003</option>
										<option>CG004</option>
										<option>CG005</option>
										<option>CG006</option>
									</select>
								</div>
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">当前状态</span> 
									<input id="chargeState" type="text" class="form-control"
										aria-describedby="basic-addon6" readonly=""> 
								</div>
								<div class="btn-group btn-group-justified" role="group"	aria-label="ok" >
									<div class="btn-group" role="group">
										<input type="button" class="btn btn-success" id="chargeFinbtn"
										    value="充电完成" onClick='chargeFinish();'>
									</div>
<!-- 									<div class="btn-group" role="group"> -->
<!-- 										<input type="button" class="btn btn-default" id="chargeOffbtn" -->
<!-- 											value="关闭" onClick='chargeOn(false);'> -->
<!-- 									</div> -->
								</div>
							</div>
						</div>
					</td>
				</tr>

			</tbody>
		</table>
    </div>
</body>
</html>
