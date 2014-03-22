<%@ page import="com.gxx.record.dao.wedisle.WedisleRelaFriendDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleRelaFriend" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<html>
<head>
    <title>婚礼岛 - wedisle.com</title>
    <link rel="shortcut icon" type="image/x-icon" href="<%=baseUrl%>favicon.ico" />
    <link href="<%=baseUrl%>style/homePlus.css" rel="stylesheet" type="text/css" />
    <link href="<%=baseUrl%>style/flexslider.css" rel="stylesheet" type="text/css" />
    <link href="<%=baseUrl%>style/signin.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=baseUrl%>Scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>Scripts/comm.js"></script>
    <style type="text/css">
        table td{border: 1px solid gray; text-align: center; width: 100px;}
    </style>
    <script type="text/javascript">
        <%
            if(!isLogin)
            {
        %>
        alert('<%="请先登录！"%>');
        location.href = "<%=baseUrl%>login.jsp";
        <%
            }
        %>
    </script>
</head>
<body>
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
</div>
<div align="center">
    <h3>管理我的亲友簿</h3>
</div>
<div align="center">
    <table>
        <tr>
            <td colspan="3">亲友簿</td>
        </tr>
        <tr>
            <td>亲友名称</td>
            <td>人数</td>
            <td>席位号</td>
        </tr>
        <%
            if(isLogin)
            {
                List friends = WedisleRelaFriendDao.queryRelaFriendsByUserId(wedisleUser.getId());
                for(int i=0;i<friends.size();i++)
                {
                    WedisleRelaFriend friend = (WedisleRelaFriend)friends.get(i);
        %>
        <tr>
            <td><%=friend.getName()%></td>
            <td><%=friend.getNum()%>人</td>
            <td><%=friend.getSeat()>0?friend.getSeat()+"桌":"未安排"%></td>
        </tr>
        <%
                }
            }
        %>
        <form action="<%=baseUrl%>addRelaFriend" method="post">
            <tr>
                <td><input type="text" name="name"></td>
                <td><input type="text" name="num"></td>
                <td><input type="submit" value="新增"></td>
            </tr>
        </form>
    </table>
</div>
</body>
</html>