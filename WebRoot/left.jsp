<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link rel="stylesheet" href="<%=basePath%>css/common.css">
<link rel="stylesheet" href="<%=basePath%>css/style.css">
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.SuperSlide.js"></script>
<script type="text/javascript">
	$(function() {
		$(".sideMenu").slide({
			titCell : "h3",
			targetCell : "ul",
			defaultIndex : 0,
			effect : 'slideDown',
			delayTime : '500',
			trigger : 'click',
			triggerTime : '150',
			defaultPlay : true,
			returnDefault : false,
			easing : 'easeInQuint',
			endFun : function() {
				scrollWW();
			}
		});
		$(window).resize(function() {
			scrollWW();
		});
	});
	function scrollWW() {
		if ($(".side").height() < $(".sideMenu").height()) {
			$(".scroll").show();
			var pos = $(".sideMenu ul:visible").position().top - 38;
			$('.sideMenu').animate({
				top : -pos
			});
		} else {
			$(".scroll").hide();
			$('.sideMenu').animate({
				top : 0
			});
			n = 1;
		}
	}

	var n = 1;
	function menuScroll(num) {
		var Scroll = $('.sideMenu');
		var ScrollP = $('.sideMenu').position();
		/*alert(n);
		alert(ScrollP.top);*/
		if (num == 1) {
			Scroll.animate({
				top : ScrollP.top - 38
			});
			n = n + 1;
		} else {
			if (ScrollP.top > -38 && ScrollP.top != 0) {
				ScrollP.top = -38;
			}
			if (ScrollP.top < 0) {
				Scroll.animate({
					top : 38 + ScrollP.top
				});
			} else {
				n = 1;
			}
			if (n > 1) {
				n = n - 1;
			}
		}
	}
</script>

</head>

<body style="background:#f0f9fd;">
    <!--  <div class="top">
        <div id="side_here">
            <div id="side_here_l" class="fl"></div>
            <div id="here_area" class="fl">当前位置：</div>
        </div>
    </div>-->
    <div class="side">
        <div class="sideMenu" style="margin:0 auto">
        	<c:if test="${sessionInfo.user.userType=='super'}">
        	<!--  <h3>管理账号</h3>
            <ul>
                <li class="on">
                    <a href="<%=basePath%>user/manager" target="rightFrame">管理列表</a>
                </li>
                <li>
                    <a href="<%=basePath%>user/addPage" target="rightFrame">添加管理</a>
                </li>
            </ul>-->
            </c:if>
            <div id="menudiv">
	            <h3>任务管理</h3>
	            <ul>
	                <li>
	                    <a href="<%=basePath%>task/addPage" target="rightFrame">添加任务</a>
	                </li>
	            </ul>
            </div>
            
        </div>
    </div>
</body>
</html>

