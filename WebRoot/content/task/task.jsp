<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<%@ include file="/header.jsp"%>
<script src="<%=basePath%>js/me/webSocket.js"></script>
<script type="text/javascript">
	var gridObj,gridObjHistory;
    $(function(){
    	    	
        gridObj = $.fn.bsgrid.init('searchTable', {
            url: '<%=basePath%>task/dataGridCurrent',
			pageSizeSelect : true,
			pageSize : 10,
			autoLoad : true,
			pagingLittleToolbar : true,//精简工具条
			stripeRows : true,//隔行变色
			displayBlankRows : false,//不显示空白行
			displayPagingToolbarOnlyMultiPages : false,
		});
		
        gridObjHistory = $.fn.bsgrid.init('searchTableHistory', {
            url: '<%=basePath%>task/dataGridHistory',
            pageSizeSelect : true,
			pageSize : 10,
			autoLoad : true,
			pagingLittleToolbar : true,//精简工具条
			stripeRows : true,//隔行变色
			displayBlankRows : false,//不显示空白行
			displayPagingToolbarOnlyMultiPages : false,
			
		});
        
        initWebSocket();
                
	});
    
	function search() {
		var name = $("#nameSrh").val();
 		var searchParames = {
 			"name" :name
		};
 		gridObj.search(searchParames);
	}
	
	function searchHistory() {
 		var name = $("#nameHisSrh").val();
 		var searchParames = {
 			"name" :name
 		};
 		gridObjHistory.search(searchParames);
	}
			
    function stateformatter(record, rowIndex, colIndex, options){
    	//'unread:未读,waiting:等待,moving:运行,loading:取货,unloading:卸货,cancelled:取消,finished:正常结束'
        if($.trim(record.state)=="unread"){
            return '<font color="red">未读</font>';
        }else if($.trim(record.state)=="waiting"){
	        return '<font color="#E81717">等待</font>';
	    }else if($.trim(record.state)=="moving"){
	        return '<font color="#E81717">运行</font>';
	    }else if($.trim(record.state)=="loading"){
	        return '<font color="#E81717">取货</font>';
	    }else if($.trim(record.state)=="unloading"){
	        return '<font color="#E81717">卸货</font>';
	    }else if($.trim(record.state)=="parking"){
	        return '<font color="gray">前往停车</font>';
	    }else if($.trim(record.state)=="cancelled"){
	        return '<font color="gray">取消</font>';
	    }else if($.trim(record.state)=="finished"){
	        return '<font color="green">完成</font>';
	    }
    }
    function typeOper(record, rowIndex, colIndex, options){
		if (record.typeId == 0){
			return "移动";
		}else if (record.typeId == 1){
			return "运输";
		}else if (record.typeId == 2){
			return "货架";
		}
	}
	function userOper(record, rowIndex, colIndex, options){
		return record.user.userName;
	}
	
	function agvOper(record, rowIndex, colIndex, options){
		return record.agv?record.agv.name:null;
	}
	
	function agvTypeOper(record, rowIndex, colIndex, options){
		return record.agvType?record.agvType.name:null;
	}
	
	function startPosOper(record, rowIndex, colIndex, options){
		switch (record.typeId){
		case 0:
		case 1:
			return record.startPos;
			break;
		case 2:
			return record.shelf1!=null?record.shelf1.sn:null;
			break;
		}
	}
	
	function endPosOper(record, rowIndex, colIndex, options){
		switch (record.typeId){
		case 0:
		case 1:
			return record.endPos;
			break;
		case 2:
			return record.shelf2!=null?record.shelf2.sn:null;
			break;
		}
	}
	
    function operate(record, rowIndex, colIndex, options) {
        var html = "";
        html+='<input type="button" class="ext_btn ext_btn_success" value="取消" onclick=cancelTask('+record.id+',"'+record.state+'");>';	            
        html+='<input type="button" class="ext_btn ext_btn_success" value="修改" onclick=editTask('+record.id+',"'+record.state+'");>';	            
        
        if(record.state == "loadingFinish")
        {
        	html+='<input type="button" class="ext_btn ext_btn_success" value="装货完毕" onclick=loadFinish('+record.id+');>';
        }else if(record.state == 5)
        {
        	html+='<input type="button" class="ext_btn ext_btn_success" value="卸货完毕" onclick=unloadFinish('+record.id+');>';	            
        }
        return html;
    }
    
    function operateHis(record, rowIndex, colIndex, options) {
        var html = "";
        
        return html;
    }
    
</script>
<title>任务管理</title>
</head>
<body>
	<%@ include file="/menu.jsp"%>
	<div class="mycontainer">
		<ul class="nav nav-tabs">
			<li role="presentation" class="active"><a href="#tabCurrent"
				role="tab" data-toggle="tab">当前任务</a></li>
			<li role="presentation"><a href="#tabHistory" role="tab"
				data-toggle="tab">历史任务</a></li>
		</ul>
		<div class="tab-content" style="margin-top: 10px">
			<%--当前任务--------------------------------------------------------------------------------------- --%>
			<div role="tabpanel" class="tab-pane fade in active" id="tabCurrent">
				<div id="search_bar" class="mt10">
					<div class="box">
						<div class="box_border">
							<div class="box_center pt10 pb10">
								<table class="form_table" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td>任务名</td>
										<td><input type="text" name="nameSrh" id="nameSrh"
											class="input-text lh25" size="10"></td>
										<td>
											<div id="button" class="mt10">
												<input type="button" ="button" id="sbtn"
													class="mbtn mbtn82 mbtn_search" value="查询"
													onClick="search();"> 
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div id="table" class="mt10" style="width: 100%; overflow: scroll;">
					<table id="searchTable" style="width: 2000px;">
						<tr>
							<th w_index="id" width="3%;">任务号</th>
							<th w_index="name" width="3%;">任务名</th>
							<th w_render="startPosOper" width="3%;">起点</th>
							<th w_render="endPosOper" width="3%;">终点</th>
							<th w_index="priority" width="3%;">优先级</th>
							<th w_index="scheduleTime" width="8%;">要求到达时间</th>
							<th w_render="typeOper" width="3%;">任务类型</th>
							<th w_render="stateformatter" width="3%;">任务状态</th>
							<th w_render="agvOper" width="4%;">AGV编号</th>
							<th w_render="userOper" width="3%;">创建人</th>
							<th w_index="createDate" width="8%">创建时间</th>
							<th w_index="startDate" width="8%;">开始时间</th>
							<th w_index="startLoadTime" width="8%;">开始装载</th>
							<th w_index="endLoadDate" width="8%;">结束装载</th>
							<th w_index="startUnloadDate" width="8%;">开始卸载</th>
							<th w_index="endUnloadDate" width="8%;">结束卸载</th>
							<th w_index="endDate" width="8%;">完成时间</th>
						</tr>
					</table>
				</div>
			</div>
			<%--当前任务完--------------------------------------------------------------------------------------- --%>
			<%--历史任务--------------------------------------------------------------------------------------- --%>
			<div role="tabpanel" class="tab-pane fade" id="tabHistory">
				<div id="search_bar" class="mt10">
					<div class="box">
						<div class="box_border">
							<div class="box_center pt10 pb10">
								<table class="form_table" border="0" cellpadding="0"
									cellspacing="0">
									<tr>
										<td>任务名</td>
										<td><input type="text" name="nameHisSrh" id="nameHisSrh"
											class="input-text lh25" size="10"></td>
										<td>
											<div id="buttonHistory" class="mt10">
												<input type="button" ="button" id="sbtnH"
													class="mbtn mbtn82 mbtn_search" value="查询"
													onClick="searchHistory();"> 
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div id="tableHistory" class="mt10" style="width: 100%; overflow: scroll;">
					<table id="searchTableHistory" style="width: 2000px;">
						<tr>
							<th w_index="id" width="3%;">任务号</th>
							<th w_index="name" width="3%;">任务名</th>
							<th w_render="startPosOper" width="3%;">起点</th>
							<th w_render="endPosOper" width="3%;">终点</th>
							<th w_index="priority" width="3%;">优先级</th>
							<th w_index="scheduleTime" width="8%;">要求到达时间</th>
							<th w_render="typeOper" width="3%;">任务类型</th>
							<th w_render="stateformatter" width="3%;">任务状态</th>
							<th w_render="agvOper" width="4%;">AGV编号</th>
							<th w_render="userOper" width="3%;">创建人</th>
							<th w_index="createDate" width="8%">创建时间</th>
							<th w_index="startDate" width="8%;">开始时间</th>
							<th w_index="startLoadTime" width="8%;">开始装载</th>
							<th w_index="endLoadDate" width="8%;">结束装载</th>
							<th w_index="startUnloadDate" width="8%;">开始卸载</th>
							<th w_index="endUnloadDate" width="8%;">结束卸载</th>
							<th w_index="endDate" width="8%;">完成时间</th>
						</tr>
					</table>
				</div>
			</div>
			<%--历史任务完--------------------------------------------------------------------------------------- --%>
		</div>
	</div>
</body>
</html>
