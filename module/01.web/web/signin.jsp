<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>婚礼岛 - wedisle.com</title>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
<link href="style/homePlus.css" rel="stylesheet" type="text/css" />
<link href="style/flexslider.css" rel="stylesheet" type="text/css" />
<link href="style/signin.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="Scripts/jquery-min.js"></script>
<script type="text/javascript" src="Scripts/comm.js"></script>
<script type="text/javascript">
    function load(){
    <%
        if(StringUtils.isNotBlank(messageLoc) && StringUtils.isNotBlank(message)){
            if("1".equals(messageLoc)){
        %>
            var node = document.getElementById("msg_id1");
            node.innerHTML = '<%=message%>';
            node.style.display = "";
        <%
            }else if("2".equals(messageLoc)){
        %>
            var node = document.getElementById("msg_id2");
            node.innerHTML = '<%=message%>';
            node.style.display = "";
        <%
            }else if("3".equals(messageLoc)){
        %>
            var node = document.getElementById("msg_id3");
            node.innerHTML = '<%=message%>';
            node.style.display = "";
        <%
            }
        }
    %>
    }
</script>
</head>
<body onload="load();">
<div class="header">
<p class="topright">
    <%
        if(isLogin)
        {
    %>
    <%=wedisleUser.getUserName()%> | <a href="<%=baseUrl%>main.jsp">个人主页</a> | <a href="<%=baseUrl%>logout.jsp">退出</a>
    <%
        }else
        {
    %>
    <a href="<%=baseUrl%>signin.jsp">注册</a> | <a href="<%=baseUrl%>login.jsp">登录</a>
    <%
        }
    %>
</p>
<div class="title">
<span class="logo"><img src="images/logo.png" alt="My Wonderland" /></span>
<span class="pagetitle">记录我的历程，<a href="#">创建我的主页</a></span>
  
</div>
</div>
<div class="wrap">
<div class="signinbox">
<h1>用户注册</h1>
    <form action="<%=baseUrl%>signInWedisle" method="post">
        <ul>
            <li>
                <input type="text" placeholder="请输入邮箱" name="userName" value="<%=StringUtils.trimToEmpty((String)request.getAttribute("userName"))%>"/>
                <div class="msgtip msginfo">邮箱将作为您的登录名使用</div>
                <div class="msgtip msgwarn" id="msg_id1" style="display: none;"></div>
            </li>
            <li>
                <input type="password" placeholder="请输入密码" name="password" />
                <div class="msgtip msginfo">密码由6-12位字母、数字或符号组成</div>
                <div class="msgtip msgwarn" id="msg_id2" style="display: none;"></div>
            </li>
            <li>
                <input type="password" placeholder="请再次输入密码" name="password2" />
                <div class="msgtip msgwarn" id="msg_id3" style="display: none;"></div>
            </li>
            <li>
                <button>注&nbsp;&nbsp;&nbsp;册</button>
            </li>
        </ul>
    </form>
</div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br /><a href="#">关于婚礼岛</a></p>
<script src="Scripts/jquery.flexslider-min.js"></script> 
</body>
</html>
