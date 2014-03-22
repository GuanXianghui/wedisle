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
<script type="text/javascript" src="../Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="../Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="../Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<script type="text/javascript">
    $(document).ready(function() {
        $(".dialogBtn").fancybox();
    });
    /**
     输入：总预算，桌数
     输出：婚宴总预算：/2		每桌预算：	/2/桌数
     婚纱&礼服：/2/5		婚戒：	/2/5
     新人饰品：/2/25		捧花&花艺：/2/25*2
     摄影&摄像：/2/25*4  乐队/司仪：/2/25/2*3
     婚礼蛋糕：/2/25		请贴/婚礼卡片：	/2/25*2
     喜糖/回礼：/2/25*2	其他（用车）：	/2/25/2*3
     */
    //分配预算
    function calBudget(){
        var budgetSum = parseInt(document.getElementById("budgetSum").value);
        var tableSum = parseInt(document.getElementById("tableSum").value);
        document.getElementById("value1").innerHTML = budgetSum/2;
        document.getElementById("value2").innerHTML = budgetSum/2/tableSum;
        document.getElementById("value3").innerHTML = budgetSum/2/5;
        document.getElementById("value4").innerHTML = budgetSum/2/5;
        document.getElementById("value5").innerHTML = budgetSum/2/25;
        document.getElementById("value6").innerHTML = budgetSum/2/25*2;
        document.getElementById("value7").innerHTML = budgetSum/2/25*4;
        document.getElementById("value8").innerHTML = budgetSum/2/25/2*3;
        document.getElementById("value9").innerHTML = budgetSum/2/25;
        document.getElementById("value10").innerHTML = budgetSum/2/25*2;
        document.getElementById("value11").innerHTML = budgetSum/2/25*2;
        document.getElementById("value12").innerHTML = budgetSum/2/25/2*3;
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
<div class="toolbox">
<h1>婚礼预算</h1>
<ul class="budgetInputs">
<li><input type="text" id="budgetSum" placeholder="您婚礼的总预算" />元</li>
<li><input type="text" id="tableSum" placeholder="预计婚宴桌数" />桌</li>
<li><button onclick="calBudget()">分配预算</button></li>
</ul>
<h3>建议将您的结婚预算分配为：</h3>
<h2 class="budgetCate ico_eat">婚宴</h2>
<ul class="budgetDetails">
<li><label>婚宴总预算</label><div id="value1" class="value"></div></li>
<li><label>每桌预算</label><div id="value2" class="value"></div></li>
</ul>
<h2 class="budgetCate ico_ring">服饰</h2>
<ul class="budgetDetails">
<li><label>婚纱及礼服</label><div id="value3" class="value"></div></li>
<li><label>婚戒</label><div id="value4" class="value"></div></li>
<li><label>新人饰品</label><div id="value5" class="value"></div></li>
<li><label>捧花及花艺</label><div id="value6" class="value"></div></li>
</ul>
<h2 class="budgetCate ico_man">人工</h2>
<ul class="budgetDetails">
<li><label>摄影摄像</label><div id="value7" class="value"></div></li>
<li><label>乐队司仪</label><div id="value8" class="value"></div></li>
</ul>
<h2 class="budgetCate ico_bag">其他</h2>
<ul class="budgetDetails">
<li><label>婚礼蛋糕</label><div id="value9" class="value"></div></li>
<li><label>请帖及婚礼卡片</label><div id="value10" class="value"></div></li>
<li><label>喜糖及回礼</label><div id="value11" class="value"></div></li>
<li><label>其他（用车）</label><div id="value12" class="value"></div></li>
</ul>
  </div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
  <a href="#">关于婚礼岛</a></p>
<div style="display:none">
  
</div>
</body>
</html>
