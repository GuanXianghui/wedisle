<%@ page import="com.gxx.record.interfaces.BaseInterface" %>
<%@ page import="com.gxx.record.utils.PropertyUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<% String baseUrl = PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL);%>
<head>
    <title></title>
    <script type="text/javascript">
        function change()
        {
            var url = "<%=baseUrl%>wedding/map/base_map.jsp";
            url += "?location=";
            url += document.getElementById("location").value;
            url += "&zoom="
            url += document.getElementById("zoom").value;
            url += "&target="
            url += document.getElementById("target").value;
            url += "&region="
            url += document.getElementById("region").value;
            url += "&place="
            url += document.getElementById("place").value;
            url += "&phone="
            url += document.getElementById("phone").value;
            url += "&width="
            url += document.getElementById("width").value;
            url += "&height="
            url += document.getElementById("height").value;
            document.getElementById("link").href=url;
            document.getElementById("link").innerHTML=url;

            var staticImgValue = "http://api.map.baidu.com/staticimage"
                    + "?center=" + document.getElementById("location").value
                    + "&width="+document.getElementById("width").value
                    + "&height=" +document.getElementById("height").value
                    + "&zoom="+document.getElementById("zoom").value
                    + "&markers="+document.getElementById("markers").value;
            document.getElementById("staticImg").href = staticImgValue
            document.getElementById("staticImg").innerHTML = staticImgValue;

            var location = document.getElementById("location").value;
            var reverseLoc = "";
            if(location.indexOf(",") > 0){
                var latlng = location.split(",");
                reverseLoc = latlng[1] + "," + latlng[0];
            }

            var bigImgValue = "http://map.baidu.com/"
                    + "?latlng=" + reverseLoc
                    + "&title="+document.getElementById("target").value
                    + "&content=" +document.getElementById("place").value
                    + "&autoOpen=true&l=";
            document.getElementById("bigImg").href = bigImgValue
            document.getElementById("bigImg").innerHTML = bigImgValue;
        }
    </script>
</head>
<body>
<div align="center">
    <h1>地图嵌入</h1>
    <h6>
        注意：我们网站内部嵌入以下链接即可，但是目标需要配置
    </h6>
    <h6>
        注意：百度地图坐标，请进<a href="http://api.map.baidu.com/lbsapi/getpoint/index.html" target="_blank">百度拾取坐标系统</a>查找
    </h6>
    <table style="border: 1px solid gray;">
        <tr>
            <td>地图坐标:</td>
            <td><input type="text" onchange="change()" id="location" value="121.562319,31.299027"></td>
        </tr>
        <tr>
            <td>放大倍数:</td>
            <td><input type="text" onchange="change()" id="zoom" value="16"></td>
        </tr>
        <tr>
            <td>目标地:</td>
            <td><input type="text" onchange="change()" id="target" value="上海理工大学"></td>
        </tr>
        <tr>
            <td>城市:</td>
            <td><input type="text" onchange="change()" id="region" value="上海"></td>
        </tr>
        <tr>
            <td>地址:</td>
            <td><input type="text" onchange="change()" id="place" value="军工路516号"></td>
        </tr>
        <tr>
            <td>电话:</td>
            <td><input type="text" onchange="change()" id="phone" value="13764603603,10086"></td>
        </tr>
        <tr>
            <td>宽度:</td>
            <td><input type="text" onchange="change()" id="width" value="800"></td>
        </tr>
        <tr>
            <td>高度:</td>
            <td><input type="text" onchange="change()" id="height" value="600"></td>
        </tr>
        <tr>
            <td>标注(|分隔):</td>
            <td><input type="text" onchange="change()" id="markers" value="上海理工大学|上海电缆研究所"></td>
        </tr>
    </table>
    <h1>动态互动</h1>
    <a href="<%=baseUrl%>wedding/map/base_map.jsp?location=121.562319,31.299027&zoom=16&target=上海理工大学&region=上海&place=军工路516号&phone=13764603603,10086&width=800&height=600"
            id="link"
            target="_blank">
        <%=baseUrl%>wedding/map/base_map.jsp?location=121.562319,31.299027&zoom=16&target=上海理工大学&region=上海&place=军工路516号&phone=13764603603,10086&width=800&height=600
    </a>
    <h1>静态图片</h1>
    <a href="http://api.map.baidu.com/staticimage?center=121.562319,31.299027&width=600&height=400&zoom=16&markers=上海理工大学|上海电缆研究所"
       id="staticImg"
       target="_blank">
        http://api.map.baidu.com/staticimage?center=121.562319,31.299027&width=600&height=400&zoom=16&markers=上海理工大学|上海电缆研究所
    </a>
    <h1>查看大图</h1>
    <a href="http://map.baidu.com/?latlng=31.299027,121.562319&title=上海理工大学&content=军工路516号&autoOpen=true&l="
       id="bigImg"
       target="_blank">
        http://map.baidu.com/?latlng=31.299027,121.562319&title=上海理工大学&content=军工路516号&autoOpen=true&l=
    </a>
</div>
</body>
</html>