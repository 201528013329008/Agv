<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/header.jsp"%> 
<script src="<%=basePath%>js/me/mouseTools.js"></script>
<script src="<%=basePath%>js/me/map/showMap.js"></script>
<script src="<%=basePath%>js/me/webSocket.js"></script>
<script type="text/javascript">
	var gridObj;
	var activeStation = {stationId:<%=request.getAttribute("id")%>};
	
	var mblue = "#337ab7";
	var mgrey = "#969696";
	
	var stationA;
	var stationB;
	
	$(function() {
		stationA = {
				trayState:     $("#workTrayState_A"),
				cacheState:    $("#cacheTrayState_A"),
				trayBtn1:      $("#btn1_A"), //用来确认工人搬运东西
				trayBtn2:      $("#btn2_A"), //用来进行运输任务中间步骤
				cacheBtn1:     $("#btn3_A"), //用来确认工人搬运东西
				cacheBtn2:     $("#btn4_A"), //用来进行运输任务中间步骤
				trayBtn1Func:  function(){btnAction({id:1,occupied:true},"是否确定已将货物放入货位？");},
				trayBtn2Func:  btnAction,
				cacheBtn1Func: function(){btnAction({id:2,occupied:false},"是否确定已将空托盘移除？");},
				cacheBtn2Func: btnAction,
				trayType:      "out",        //对外输出型工位
				cacheType:     "in",         //输入型工位
				trayId:        1,            //货位对应的shelfId        
				cacheId:       2             //托盘对应的shelfId
			};
		
		stationA.trayBtn2.hide();
		stationA.cacheBtn2.hide();
		
		stationB = {
				trayState:     $("#workTrayState_B"),
				cacheState:    $("#cacheTrayState_B"),
				trayBtn1:      $("#btn1_B"), //用来确认工人搬运东西
				trayBtn2:      $("#btn2_B"), //用来进行运输任务中间步骤
				cacheBtn1:     $("#btn3_B"), //用来确认工人搬运东西
				cacheBtn2:     $("#btn4_B"), //用来进行运输任务中间步骤
				trayBtn1Func:  function(){btnAction({id:3,occupied:false},"是否确定已将货物移除？");},
				trayBtn2Func:  btnAction,
				cacheBtn1Func: function(){btnAction({id:4,occupied:true},"是否确定已将空托盘放入货位？");},
				cacheBtn2Func: btnAction,
				trayType:      "in",        //对外输出型工位
				cacheType:     "out",         //输入型工位
				trayId:        3,            //货位对应的shelfId        
				cacheId:       4             //托盘对应的shelfId
			};
		
		stationB.trayBtn2.hide();
		stationB.cacheBtn2.hide();
			
		updateAll();
		
		initMap(function(){
			addRoad();
			addIntersection();
			addWorkStation();
			addStop();
			addShelf();
			addCar();
			stage.update();
		});
		
		initWebSocket();
		window.setInterval("updateAll()",10000);
		
	});
	
	//更新页面中所有动态信息
	function updateAll(){
		$.get("<%=basePath%>shelf/dataGrid",function(jqGrid,status){
			var jqGrid = eval("("+jqGrid+")");
			var shelfs = jqGrid.data;
			$.get("<%=basePath%>task/dataGridCurrent",function(jqGrid,status){
				jqGrid = eval("("+jqGrid+")");
				var tasks = jqGrid.data;
				updateStation(stationA,shelfs,tasks);
				updateStation(stationB,shelfs,tasks);
			});
		});
		
		
		
		if (activeStation.stationId == 1){
			$("#btn1_B").attr("disabled", true);
			$("#btn2_B").attr("disabled", true);
			$("#btn3_B").attr("disabled", true);
			$("#btn4_B").attr("disabled", true);
			$("#panelTitleA").css('background',mblue);
			$("#panelTitleB").css('background',mgrey);
			$("#panelA").css('border-color',mblue);
			$("#panelB").css('border-color',mgrey);
			
		}else if(activeStation.stationId == 2){
			$("#btn1_A").attr("disabled", true);
			$("#btn2_A").attr("disabled", true);
			$("#btn3_A").attr("disabled", true);
			$("#btn4_A").attr("disabled", true);
			$("#panelTitleA").css('background',mgrey);
			$("#panelTitleB").css('background',mblue);
			$("#panelA").css('border-color',mgrey);
			$("#panelB").css('border-color',mblue);
		}
	}
	
	function updateStation(station, shelfs, tasks){
		station.trayBtn1.click(station.trayBtn1Func);
		station.cacheBtn1.click(station.cacheBtn1Func);
		for (var i in shelfs){
			shelf = shelfs[i];
			//如果是货位
			if (shelf.id == station.trayId){
				if (station.trayType == "out"){
					if (shelf.occupied){
						station.trayState.val("已放置");
						station.trayState.css({'color':mblue});
						station.trayBtn1.hide();
					}else{
						station.trayState.val("未放置");
						station.trayState.css({'color':'black'});
						station.trayBtn1.text("放置");
						station.trayBtn1.show();
					}
				}else{//in
					if (shelf.occupied){
						station.trayState.val("已放置");
						station.trayState.css({'color':mblue});
						station.trayBtn1.text("清空");
						station.trayBtn1.show();
					}else{
						station.trayState.val("未放置");
						station.trayState.css({'color':'black'});
						station.trayBtn1.hide();
					}
				}
				
			}
			//如果是托盘位
			else if(shelf.id == station.cacheId){
				if (station.cacheType == "out"){
					if (shelf.occupied){
						station.cacheState.val("已放置");
						station.cacheState.css({'color':mblue});
						station.cacheBtn1.hide();
					}else{
						station.cacheState.val("未放置");
						station.cacheState.css({'color':'black'});
						station.cacheBtn1.text("放置");
						station.cacheBtn1.show();
					}
				}else{//in
					if (shelf.occupied){
						station.cacheState.val("已放置");
						station.cacheState.css({'color':mblue});
						station.cacheBtn1.text("清空");
						station.cacheBtn1.show();
					}else{
						station.cacheState.val("未放置");
						station.cacheState.css({'color':'black'});
						station.cacheBtn1.hide();
					}
				}
			}	
		}
		
		//人工干预按钮复位
		station.trayBtn2.hide();
		station.cacheBtn2.hide();
		//判断是否有与工位相关的任务需要人为操作
		for (var i in tasks){
			var task = tasks[i];
			//判断需要人工干预的类型
			if (task.startPos == station.trayId && task.state == "loading"){
				station.trayBtn2.show();
				station.trayBtn2.text("装载完成");
				station.trayBtn2.click(function(taskId,shelfId){          //函数执行时基本类型传递的是拷贝，其他时候传递的都是引用
					return function(){ loadFinish(taskId,shelfId); };
				}(task.id,station.trayId));
			}else if(task.endPos == station.trayId && task.state == "unloading"){
				station.trayBtn2.show();
				station.trayBtn2.text("卸载完成");
				station.trayBtn2.click(function(taskId,shelfId){
					return function(){ unloadFinish(taskId,shelfId); };
				}(task.id,station.trayId));
			}else if(task.startPos == station.cacheId && task.state == "loading"){
				station.cacheBtn2.show();
				station.cacheBtn2.text("装载完成");
				station.cacheBtn2.click(function(taskId,shelfId){
					return function(){ loadFinish(taskId,shelfId); };
				}(task.id,station.cacheId));
			}else if(task.endPos == station.cacheId && task.state == "unloading"){
				station.cacheBtn2.show();
				station.cacheBtn2.text("卸载完成");
				station.cacheBtn2.click(function(taskId,shelfId){
					return function(){ unloadFinish(taskId,shelfId); };
				}(task.id,station.cacheId));
			}
		}
	}
	
	//定期更新小车位置
	function onMapTick(){
		$.get(getRootPath()+"agv/dataGrid",function(jqGrid,status){
			var jqGrid = eval("("+jqGrid+")");
			
	      	if (jqGrid.success){
	      		var datas = jqGrid.data;
	      		for (i in datas){
	      			var agv = datas[i];
	      			var car = carMap.get(agv.id);
	      			if (car!=null){
	      				createjs.Tween.get(car)
	      				.to({x:SX(agv.x), y:SY(agv.y)}, 1000)
	      			}
	      		}
	      		stage.update();
	      	}else{
	      		alert("获取车辆数据失败！");
	      	}
			
	    });
	}
	
	//装载完成人工信号,同时应该将托盘状态置空
	function loadFinish(taskId,shelfId){
   		$.get("<%= basePath %>task/loadFinish?taskId="+taskId, function(data,status){
   			$.post("<%=basePath%>shelf/edit", {id:shelfId, occupied:false}, function(data){
				data = $.parseJSON(data);
                if(data.success){
                	layer.msg("操作成功", {icon: 0,time: 500});
                    updateAll(); //刷新所有状态
                }else{
                	layer.msg("操作失败", {icon: 0,time: 1000});
                }
			});
       	 });
    	
    }
    //卸载完成人工信号，同时应该将托盘状态置满
    function unloadFinish(taskId,shelfId){
   		$.get("<%= basePath %>task/unloadFinish?taskId="+taskId, function(data,status){
   			$.post("<%= basePath %>shelf/edit", {id:shelfId, occupied:true}, function(data){
				data = $.parseJSON(data);
                if(data.success){
                	layer.msg("操作成功", {icon: 0,time: 500});
                    updateAll(); //刷新所有状态
                }else{
                	layer.msg("操作失败", {icon: 0,time: 1000});
                }
			});
       	 });
    }
   
	//按钮操作
	function btnAction(shelf,msg){
		
		layer.confirm(msg, {title:'警告',
			  btn: ['确定','取消'] //按钮
			}, function(index){
				$.post("<%=basePath%>shelf/edit", shelf, function(data){
					data = $.parseJSON(data);
	                if(data.success){
	                	layer.msg("操作成功", {icon: 0,time: 500});
	                    updateAll(); //刷新所有状态
	                }else{
	                	layer.msg("操作失败", {icon: 0,time: 1000});
	                }
				});
				layer.close(index);
			}, function(index){
				layer.close(index);
			});
	}
		
	//收到下位机消息回调
    function onGetWebsocketMsg(msg){
    	updateAll();
    }	
	
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
						<div class="panel panel-primary" style="width:300px" id="panelA">
							<div class="panel-heading" id="panelTitleA">
								<h3 id="panelTitle" class="panel-title">工位A</h3>
							</div>
							<div class="panel-body">
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">货位状态</span> 
									<input id="workTrayState_A" type="text" class="form-control"
										aria-describedby="basic-addon6" readonly="" style="text-align:center"> 
								</div>
  								<button class="btn btn-default" type="button" id="btn1_A" style="width:100%;height:50px;margin-bottom:20px"></button>
  								<button class="btn btn-default" type="button" id="btn2_A" style="width:100%;height:50px;margin-bottom:20px"></button>
  								
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">托盘状态</span> 
									<input id="cacheTrayState_A" type="text" class="form-control"
										aria-describedby="basic-addon6" readonly="" style="text-align:center"> 
								</div>
        						<button class="btn btn-default" type="button" id="btn3_A" style="width:100%;height:50px;"></button>
        						<button class="btn btn-default" type="button" id="btn4_A" style="width:100%;height:50px;"></button>
							</div>
						</div>
						<div class="panel panel-primary" style="width:300px" id="panelB">
							<div class="panel-heading" id="panelTitleB">
								<h3 id="panelTitle" class="panel-title">工位B</h3>
							</div>
							<div class="panel-body">
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">货位状态</span> 
									<input id="workTrayState_B" type="text" class="form-control"
										aria-describedby="basic-addon6" readonly="" style="text-align:center"> 
								</div>
        						<button class="btn btn-default" type="button" id="btn1_B" style="width:100%;height:50px;margin-bottom:20px"></button>
								<button class="btn btn-default" type="button" id="btn2_B" style="width:100%;height:50px;margin-bottom:20px"></button>
								
								<div class="input-group">
									<span class="input-group-addon" id="basic-addon1">托盘状态</span> 
									<input id="cacheTrayState_B" type="text" class="form-control"
										aria-describedby="basic-addon6" readonly="" style="text-align:center"> 
								</div>
        						<button class="btn btn-default" type="button" id="btn3_B" style="width:100%;height:50px;"></button>
        						<button class="btn btn-default" type="button" id="btn4_B" style="width:100%;height:50px;"></button>
							</div>
						</div>
					</td>
					<td>
						<div style="text-align: center;">
							<canvas id="mapCanvas" width="800" height="400"></canvas>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
    </div>
</body>
</html>
