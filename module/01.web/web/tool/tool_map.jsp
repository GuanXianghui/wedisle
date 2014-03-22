<%@ page import="com.gxx.record.dao.wedisle.WedisleMarryRegistDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工具 - 婚礼岛</title>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
<link href="../style/homePlus.css" rel="stylesheet" type="text/css" />
<link href="../style/tools.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../Scripts/jquery-min.js"></script>
<script type="text/javascript" src="../Scripts/comm.js"></script>
<script type="text/javascript">
    var marryRegists;
    $(document).ready(function (){
        marryRegists = eval(<%=WedisleUtils.getJsonFromWedisleMarryRegists(WedisleMarryRegistDao.queryAllWedisleMarryRegist())%>);
        //初始化下拉框
        var html = "";
        for(var i=0;i<marryRegists.length;i++){
            html += "<option value='" + marryRegists[i]["id"] + "'>" + marryRegists[i]["name"] + "</option>";
        }
        document.getElementById("select_id").innerHTML = html;
        //初始化各项值
        chooseRegist(document.getElementById("select_id").value);
    });

    //选择登记处
    function chooseRegist(v){
        for(var i=0;i<marryRegists.length;i++){
            if(marryRegists[i]["id"] == v){
                document.getElementById("place").innerHTML = marryRegists[i]["place"];
                document.getElementById("phone").innerHTML = marryRegists[i]["phone"];
                document.getElementById("time").innerHTML = marryRegists[i]["time"];
                document.getElementById("bigImageSrc").href = marryRegists[i]["bigImageSrc"];
                document.getElementById("imageSrc").src = marryRegists[i]["imageSrc"];
            }
        }
    }
</script>
</head>
<body>
<div class="header">
  <p class="topright"><a href="#">注册</a> | <a href="#">登录</a></p>
  <div class="title"> <span class="logo"><img src="../images/logo.png" alt="My Wonderland" /></span> <span class="pagetitle">记录我的历程，<a href="#">创建我的主页</a></span> </div>
</div>
<ul id="MenuBar1" class="MenuBarHorizontal reference">
  <li><a href="#" target="_blank">流程首页</a></li>
  <li><a href="#">找婚庆</a></li>
  <li><a href="#" target="_blank">选酒店</a></li>
  <li><a href="#">购婚品</a></li>
  <li><a href="#" class="activeMenu">工具箱</a></li>
</ul>
<div class="wrap">
    <div class="toolbox">
        <ul class="toolsp toollist">
            <li><a href="<%=baseUrl%>tool/tool_map.jsp"><img src="<%=baseUrl%>images/tool_map.png" /></a><br /><a href="<%=baseUrl%>tool/tool_map.jsp">登记查询</a></li>
            <li><a href="<%=baseUrl%>tool/tool_material.jsp"><img src="<%=baseUrl%>images/tool_list.png" /></a><br /><a href="<%=baseUrl%>tool/tool_material.jsp">材料清单</a></li>
            <li><a href="<%=baseUrl%>tool/tool_calendar.jsp"><img src="<%=baseUrl%>images/tool_day.png" /></a><br /><a href="<%=baseUrl%>tool/tool_calendar.jsp">黄道吉日</a></li>
            <li><a href="#"><img src="<%=baseUrl%>images/tool_absense.png" /></a><br /><a href="#">请假单</a></li>
            <li><a href="<%=baseUrl%>tool/tool_absense.jsp"><img src="<%=baseUrl%>images/tool_period.png" /></a><br /><a href="<%=baseUrl%>tool/tool_absense.jsp">婚假建议</a></li>
            <li><a href="#"><img src="<%=baseUrl%>images/tool_pe.png" /></a><br /><a href="#">婚检样张</a></li>
        </ul>
        <input class="hideinput" type="checkbox" id="alltools" />
        <ul class="toolall toollist">
            <li><a href="<%=baseUrl%>tool/tool_budget.jsp"><img src="<%=baseUrl%>images/tool_budget.png" /></a><br /><a href="<%=baseUrl%>tool/tool_budget.jsp">婚礼预算</a></li>
            <li><a href="<%=baseUrl%>tool/tool_seat.jsp"><img src="<%=baseUrl%>images/tool_seat.png" /></a><br /><a href="<%=baseUrl%>tool/tool_seat.jsp">座位安排</a></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
        <label class="morebar" for="alltools"></label>
    </div>
<div id="tool_map" class="toolbox">
<h1>登记查询</h1>
    <select name="" id="select_id" onchange='chooseRegist(this.value)'>
    <option>黄浦区登记处</option>
    <option>杨浦区登记处</option>
    </select>
    <div class="mapAreas"><p>地址：<span id="place"></span>&nbsp;&nbsp;&nbsp;&nbsp;联系电话：<span id="phone"></span><br />办理时间：<span id="time"></span></p><p><a class="icolink viewmap" id="bigImageSrc" href="#" target="_blank">查看大地图</a></p><p><img id="imageSrc" src="../images/sample_map.jpg" /></p>
    </div>
  </div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
  <a href="#">关于婚礼岛</a></p>
<div style="display:none">
  
</div>
</body>
</html>
