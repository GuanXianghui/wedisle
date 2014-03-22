<%@ page import="java.util.List" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleMarryRegistDao" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleMarryRegist" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../header.jsp" %>
<html>
<head>
    <title>婚礼岛 - wedisle.com</title>
    <link rel="shortcut icon" type="image/x-icon" href="<%=baseUrl%>favicon.ico" />
    <link href="<%=baseUrl%>style/homePlus.css" rel="stylesheet" type="text/css" />
    <link href="<%=baseUrl%>style/flexslider.css" rel="stylesheet" type="text/css" />
    <link href="<%=baseUrl%>style/signin.css" rel="stylesheet" type="text/css" />
    <link href="<%=baseUrl%>style/tools.css" rel="stylesheet" type="text/css" />
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
        //alert('<%="请先登录！"%>');
        //location.href = "<%=baseUrl%>login.jsp";
        <%
            }

            if(StringUtils.isNotBlank(message)){
        %>
            alert("<%=message%>");
        <%
            }
        %>

        //修改
        function update(i){
            document.getElementById("name").value = document.getElementById("name" + i).innerHTML;
            document.getElementById("place").value = document.getElementById("place" + i).innerHTML;
            document.getElementById("phone").value = document.getElementById("phone" + i).innerHTML;
            document.getElementById("time").value = document.getElementById("time" + i).innerHTML;
            document.getElementById("bigImageSrc").value = document.getElementById("bigImageSrc" + i).href;
            document.getElementById("imageSrc").value = document.getElementById("imageSrc" + i).href;
        }
        //删除
        function deleteRegist(i){
            document.getElementById("deleteId").value = i;
            document.forms["deleteForm"].submit();
        }
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
    <h3>管理婚姻登记处</h3>
</div>
<div align="center" class="mapAreas">
    <table>
        <tr>
            <td colspan="7">婚姻登记处</td>
        </tr>
        <tr>
            <td>名字</td>
            <td>地址</td>
            <td>联系电话</td>
            <td>办理时间</td>
            <td>大图</td>
            <td colspan="2">图片</td>
        </tr>
        <%
            //if(isLogin)
            //{
                List<WedisleMarryRegist> list = WedisleMarryRegistDao.queryAllWedisleMarryRegist();
                for(int i=0;i<list.size();i++)
                {
                    WedisleMarryRegist regist = list.get(i);
        %>
        <tr>
            <td id="name<%=i%>"><%=regist.getName()%></td>
            <td id="place<%=i%>"><%=regist.getPlace()%></td>
            <td id="phone<%=i%>"><%=regist.getPhone()%></td>
            <td id="time<%=i%>"><%=regist.getTime()%></td>
            <td><a id="bigImageSrc<%=i%>" href="<%=regist.getBigImageSrc()%>" target="_blank"> 查看大图</a></td>
            <td><a id="imageSrc<%=i%>" href="<%=regist.getImageSrc()%>" target="_blank"> 看图</a></td>
            <td><button onclick="update(<%=i%>)">修改</button><button onclick="deleteRegist(<%=regist.getId()%>)">删除</button></td>
        </tr>
        <%
                }
            //}
        %>
        <form action="<%=baseUrl%>addMarryRegist" method="post">
            <tr>
                <td><input id="name" type="text" name="name"></td>
                <td><input id="place" type="text" name="place"></td>
                <td><input id="phone" type="text" name="phone"></td>
                <td><input id="time" type="text" name="time"></td>
                <td><input id="bigImageSrc" type="text" name="bigImageSrc"></td>
                <td><input id="imageSrc" type="text" name="imageSrc"></td>
                <td><input type="submit" value="保存"></td>
            </tr>
        </form>
        <form name="deleteForm" action="<%=baseUrl%>addMarryRegist" method="post">
            <input type="hidden" name="type" value="delete">
            <input type="hidden" name="id" id="deleteId">
        </form>

    </table>
</div>
</body>
</html>