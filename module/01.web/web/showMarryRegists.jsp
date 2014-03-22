<%@ page import="java.util.List" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleMarryRegistDao" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleMarryRegist" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <textarea rows="10" cols="20">
    <%
        List<WedisleMarryRegist> wedisleMarryRegists = WedisleMarryRegistDao.queryAllWedisleMarryRegist();
        for(int i=0;i<wedisleMarryRegists.size();i++){
            WedisleMarryRegist temp = wedisleMarryRegists.get(i);
    %>
    insert into wedisle_marry_regist(id,name,place,phone,time,big_image_src,image_src)values(
    <%=temp.getId() + ",'" + temp.getName() + "','" + temp.getPlace() + "','" + temp.getPhone() + "','" + (temp.getTime()) + "','" + temp.getBigImageSrc() + "','" + temp.getImageSrc() + "'"%>
    );SPLIT_GXX
    <%
        }
    %>
    </textarea>
</body>
</html>