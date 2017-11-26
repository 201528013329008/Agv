<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String menuPath = basePath+"menu.jsp";
%>

<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>js/colResizable-1.3.min.js"></script> --%>
<script type="text/javascript" src="<%=basePath%>js/common.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/artDialog-master/css/ui-dialog.css">
<script src="<%=basePath%>js/artDialog-master/dist/dialog-min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/util.js"></script>
<script src="<%=basePath%>js/layer/layer.js"></script>

<%-- select2 --%>
<link href="<%=basePath%>js/select2/select2.min.css" rel="stylesheet"/>
<script src="<%=basePath%>js/select2/select2.full.js"></script>
<%-- bootstrap --%>
<link href="<%=basePath%>js/bootstrap/bootstrap.css" rel="stylesheet"/>
<script src="<%=basePath%>js/bootstrap/bootstrap.min.js"></script>
<%-- tween 动画组件 --%>
<script src="<%=basePath%>js/tween/tweenjs-0.6.2.min.js"></script>
<%-- easel 动画组件 --%>
<script src="<%=basePath%>js/easel/easeljs-0.8.2.min.js"></script>
<%-- bsgrid --%>
<script type="text/javascript" src="<%=basePath%>js/bsgrid/js/lang/grid.zh-CN.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/bsgrid/merged/bsgrid.all.min.js"></script>

<link rel="stylesheet" href="<%=basePath%>css/main.css">
<link rel="stylesheet" href="<%=basePath%>css/map.css" />
<link rel="stylesheet" href="<%=basePath%>css/button.css">
<link rel="stylesheet" href="<%=basePath%>js/bsgrid/merged/bsgrid.all.min.css" />
<link rel="stylesheet" href="<%=basePath%>css/common.css">



