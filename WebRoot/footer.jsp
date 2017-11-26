<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<link href="<%=basePath%>css/style.css" rel="stylesheet" type="text/css" />

</head>

<body>
    <div class="bottom">
        <div id="bottom_bg">
            邮箱：XXXXXXXX@XXXXXXXX &nbsp;&nbsp;&nbsp;&nbsp;电话：XXXXXXXX</span>
             <i>版权所有 2016</i>
        </div>
    </div>
</body>
</html>
