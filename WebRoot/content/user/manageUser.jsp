<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/header.jsp"%> 
<script type="text/javascript">
	var gridObj;
    $(function(){
        gridObj = $.fn.bsgrid.init('searchTable', {
            url: '<%=basePath%>user/super/dataGrid',
			pageSizeSelect : true,
			pageSize : 10,
			autoLoad : true,
			pagingLittleToolbar : true,//精简工具条
			stripeRows : true,//隔行变色
			displayBlankRows : false,//不显示空白行
			displayPagingToolbarOnlyMultiPages : false
		});
		$("#loginName,#userName").keydown(function(event) {
			if (event.which == 13) {
				search();
			}
		});
	});
     var alertMsg = function(title, msg) {
       var d = dialog({
           title : title,
           content : msg
       });
       d.show();
       return d;
    }
	function search() {
		var loginName = $("#loginName").val();
		var userName = $("#userName").val();
		gridObj.options.otherParames = {
			"userName" : userName,
			"loginName" :loginName
		};
		gridObj.refreshPage();
	}
	function userStatusformatter(record, rowIndex, colIndex, options){
        if($.trim(record.userStatus)=="0"){
            return '<font color="red">无效</font>';
        }else if($.trim(record.userStatus)=="1"){
            return '<font color="green">有效</font>';
        }
    }
    function userTypeformatter(record, rowIndex, colIndex, options){
        if($.trim(record.userType)=="super"){
            return '<font color="red">管理员</font>';
        }else{
	        return '<font color="#E81717">普通用户</font>';
	    }
    }
    function operate(record, rowIndex, colIndex, options) {
        var html = "";
        html+='<input type="button" class="ext_btn ext_btn_success" value="编辑" onclick="editFun('+record.userId+');">';
        html+='<input type="button" class="ext_btn ext_btn_error" value="删除" onclick="deleteFun('+record.userId+');">';
        return html;
    }
    
    function editFun(id){
        location.href = '<%=basePath%>user/super/editPage?id='+id;
    }
    function deleteFun(id){
        dialog({
            title:'确认删除记录吗？删除后记录将不可返回，点击【继续】确定删除',
            button: [{
                    value: '继续',
                    callback: function () {
                         $.post("<%=basePath%>user/super/delete?id="+id,function(data){
                            data = $.parseJSON(data);
                            if(data.success){
                                var d = alertMsg("删除成功！");
                                gridObj.refreshPage();
                                setTimeout(function(){
                                    d.remove();
                                },500);
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
<title>用户管理</title>
</head>
<body>
<%@ include file="/menu.jsp"%> 
    <div class="mycontainer">

        <div id="search_bar" class="mt10">
            <div class="box">
                <div class="box_border">
                    <div class="box_top">
                        <b class="pl15">用户账号管理</b>
                    </div>
                    <div class="box_center pt10 pb10">
                        <table class="form_table" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>登录账号</td>
                                <td>
                                    <input type="text" name="loginName" id="loginName" class="input-text lh25" size="10">
                                </td>
                                <td>用户姓名</td>
                                <td>
                                    <input type="text" name="userName" id="userName" class="input-text lh25" size="10">
                                </td>
                                <td>
                                    <div id="button" class="mt10">
                                        <input type="button" name="button" id="sbtn" class="mbtn mbtn82 mbtn_search" value="查询" onClick="search();">
                                        <c:if test="${sessionInfo.user.userType=='super'}"> 
                                        <input type="button" name="button" class="mbtn mbtn82 mbtn_add" value="添加" 
                                        onClick="javascript:location.href='<%=basePath%>user/super/addPage'">
                                        </c:if>
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
                    <th w_index="userId" width="10%;">ID</th>
                    <th w_index="loginName" width="10%;">登录账号</th>
                    <th w_index="userName" width="20%;">用户姓名</th>
                    <th w_render="userTypeformatter" width="20%;">用户类型</th>
                    <th w_render="userStatusformatter" width="20%;">状态</th>
                    <th w_render="operate" width="20%;">操作</th>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
