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
   
	function search() {
		var name = $("#name").val();
		var type = $("#type").val();
		gridObj.options.otherParames = {
			"type" : type,
			"name" :name
		};
		gridObj.refreshPage();
	}
	function downloadformatter(record, rowIndex, colIndex, options){
	    var html = "";
	    html +='<a target="_blank" href="<%=basePath%>files/download?fileName='+record.fileName+'">下载查看</a>';	return html;
    }
    function stateformatter(record, rowIndex, colIndex, options){
        if($.trim(record.state)=="1"){
            return '<font color="green">有效</font>';
        }else{
	        return '<font color="#E81717">无效</font>';
	    }
    }
	function userOper(record, rowIndex, colIndex, options){
		var html = "";
		return record.addUser.userName;
	}
    function operate(record, rowIndex, colIndex, options) {
        var html = "";
        html+='<input type="button" class="ext_btn ext_btn_success" value="编辑" onclick="editFun('+record.id+');">';
        html+='<input type="button" class="ext_btn ext_btn_error" value="删除" onclick="deleteFun('+record.id+');">';
        return html;
    }
    
    function editFun(id){
        location.href = "<%=basePath%>agv/editPage?id="+id;
    }
    function deleteFun(id){
        dialog({
            title:'确认删除记录吗？删除后记录将不可返回，点击【继续】确定删除',
            button: [{
                    value: '继续',
                    callback: function () {
                         $.post("<%=basePath%>agv/delete?id="+id,function(data){
                            data = $.parseJSON(data);
                            if(data.success){
                                alertMsg("删除成功！");
                                gridObj.refreshPage();
                            }else{
                                alert(data.msg);
                                return false;
                            }
                        });
                    }
                },
                {
                    value: '取消'
                }
            ]
        }).show();
    }
    function exportData(){
        dialog({
            title:'确认导出数据吗？点击【继续】确定导出',
            button: [{
                    value: '继续',
                    callback: function () {
                         $.post('<%=basePath%>agv/export',function(data){
                            data = $.parseJSON(data);
                            if(data.success){
                                window.open('<%=basePath%>files/export?downName='+data.obj);
                            }else{
                                alert(data.msg);
                                return false;
                            }
                        });
                    }
                },
                {
                    value: '取消'
                }
            ]
        }).show();
    }
</script>

<title>人工控制</title>
</head>
<body>
<%@ include file="/menu.jsp"%> 
    <div class="mycontainer">

        <div id="container" class="mt10">

			<table class="non_border_table pt15 pb15 mgridTable">
				<tbody>
					<tr>
						<td>
							<div class="panel panel-primary" >
								<div class="panel-heading">
									<h3 id="panelTitle" class="panel-title">系统开关</h3>
								</div>
								<div class="panel-body">
									<div class="btn-group btn-group-vertical" role="group" style="min-width:200px"
										aria-label="ok">
										<div class="btn-group" role="group">
											<input type="button" class="btn btn-success" id="submit"
												value="启动" onClick="systemOn(true);">
										</div>
										<div class="btn-group" role="group">
											<input type="button" class="btn btn-default" id="cancel" style="margin-top:10px"
												value="关闭" onClick="systemOn(false);">
										</div>
										<div class="btn-group" role="group">
											<input type="button" class="btn btn-danger" id="cancel" style="margin-top:10px"
												value="强制关闭" onClick="forceOff();">
										</div>
									</div>
								</div>
							</div>
						</td>
						<td>
							<div class="panel panel-primary" >
								<div class="panel-heading">
									<h3 id="panelTitle" class="panel-title">服务开关</h3>
								</div>
								<div class="panel-body">
									<div class="btn-group btn-group-vertical" style="min-width:200px" role="group"
										aria-label="ok">
										<div class="btn-group" role="group">
											<input type="button" class="btn btn-success" id="submit"
												value="启动" onClick="serviceOn(true);">
										</div>
										<div class="btn-group" role="group">
											<input type="button" class="btn btn-danger" id="cancel" style="margin-top:10px"
												value="关闭" onClick="serviceOn(false);">
										</div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
    </div>
</body>
</html>
