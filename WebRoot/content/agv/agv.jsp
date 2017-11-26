<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/header.jsp"%> 
<script type="text/javascript">
	var gridObj;
    $(function(){
        gridObj = $.fn.bsgrid.init('searchTable', {
            url: '<%=basePath%>agv/dataGrid',
			pageSizeSelect : true,
			pageSize : 10,
			autoLoad : true,
			pagingLittleToolbar : true,//精简工具条
			stripeRows : true,//隔行变色
			displayBlankRows : false,//不显示空白行
			displayPagingToolbarOnlyMultiPages : false
		});
		$("#name,#type").keydown(function(event) {
			if (event.which == 13) {
				search();
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
    	if (record.online){
    		if(record.forbidden){
                return '<font color="#E81717">禁用</font>';
            }else{
    	        return '<font color="green">'+record.state+'</font>';
    	    }
    	}else{
    		return '<font color="#E81717">离线</font>';
    	}
        
    }
    function typeOper(record, rowIndex, colIndex, options){
		return record.agvType.name;
	}
	function userOper(record, rowIndex, colIndex, options){
		var html = "";
		return record.user.userName;
	}
    function operate(record, rowIndex, colIndex, options) {
        var html = "";
        html+='<input type="button" class="ext_btn ext_btn_success" value="编辑" onclick="editFun('+record.id+');">';
        html+='<input type="button" class="ext_btn ext_btn_error" value="删除" onclick="deleteFun('+record.id+');">';
        return html;
    }
    
    function editFun(id){
        location.href = "<%=basePath%>agv/editAgvPage?id="+id;
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
<title>车辆管理</title>
</head>
<body>
	<%@ include file="/menu.jsp" %>
    <div class="mycontainer">
        <div id="search_bar" class="mt10">
            <div class="box">
                <div class="box_border">
                    <div class="box_top">
                        <b class="pl15">车辆管理</b>
                    </div>
                    <div class="box_center pt10 pb10">
                        <table class="form_table" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>AGV编号</td>
                                <td>
                                    <input type="text" name="name" id="name" class="input-text lh25" size="10">
                                </td>
                                <td>车辆类型</td>
                                <td>
                                    <input type="text" name="type" id="type" class="input-text lh25" size="10">
                                </td>
                                <td>
                                    <div id="button" class="mt10">
                                        <input type="button" name="button" id="sbtn" class="mbtn mbtn82 mbtn_search" value="查询" onClick="search();">
                                        <input type="button" name="button" class="mbtn mbtn82 mbtn_add" value="添加" 
                                        onClick="javascript:location.href='<%=basePath%>agv/addAgvPage'">
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div id="table" class="mt10">
            <table id="searchTable">
                <tr>
                    <th w_index="id" width="10%;">ID</th>
                    <th w_index="name" width="10%;">AGV编号</th>
                    <th w_render="typeOper" width="10%;">车辆类型</th>
                    <th w_render="stateformatter" width="5%;">状态</th>
                    <th w_render="userOper" width="5%;">创建人</th>
                    <th w_index="createDate" width="10%;">创建时间</th>
                    <th w_render="operate" width="20%;">操作</th>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
