<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<%@ include file="/header.jsp"%>
<%-- myTools --%>
<script src="<%=basePath%>js/me/mouseTools.js"></script>
<script src="<%=basePath%>js/me/map/showMap.js"></script>
<script type="text/javascript">

	function init(){
		initMap(function(){
			stage.update();
		});
	}
	function tick(evt){
		stage.update();
	}
	
	var step = 0;
	function selectRoad(road) {
        //选择小车
		layer.confirm('是否确定选择？', {
		    btn: ['是','否'] //按钮
		}, function(index){
			$("#carId").val(car.id);
			$("isAuto").val(1);
			layer.close(index);
		    addFun();
		    
		}, function(index){
			$("#isAuto").val(0);
			$("#carId").val("");
			layer.close(index);
		});
	}
	
	
</script>
<title></title>
</head>
<body onload="init();">
<%@ include file="/menu.jsp"%> 
	<div class="mycontainer" >    	
		<div style="text-align: center;" >
		      <canvas id="mapCanvas" width="1000" height="1000" >
		</canvas>           
    </div> 
</body>
</html>
