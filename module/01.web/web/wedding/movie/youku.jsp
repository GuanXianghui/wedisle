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
            var url = "<%=baseUrl%>wedding/movie/base_youku.jsp";
            url += "?url=";
            url += document.getElementById("url").value;
            url += "&width="
            url += document.getElementById("width").value;
            url += "&height="
            url += document.getElementById("height").value;
            document.getElementById("link").href=url;
            document.getElementById("link").innerHTML=url;
        }
    </script>
</head>
<body>
<div align="center">
    <h1>优酷视频嵌入</h1>
    <h6>
        注意：我们网站内部嵌入以下链接即可，但是参数需要配置
    </h6>
    <h6>
        注意：优酷视频必须在优酷上上传，审核通过后，将视频地址填入优酷视频地址方框中，比如：http://player.youku.com/player.php/sid/XNjE4ODM3MDUy/v.swf
    </h6>
    <table style="border: 1px solid gray;">
        <tr>
            <td>优酷视频地址:</td>
            <td><input type="text" style="width: 400px;" onchange="change()" id="url"
                       value="http://player.youku.com/player.php/sid/XNjE4ODM3MDUy/v.swf"></td>
        </tr>
        <tr>
            <td>宽度:</td>
            <td><input type="text" style="width: 400px;" onchange="change()" id="width" value="800"></td>
        </tr>
        <tr>
            <td>高度:</td>
            <td><input type="text" style="width: 400px;" onchange="change()" id="height" value="600"></td>
        </tr>
    </table>
    <h1>优酷视频嵌入配置地址</h1>
    <a href="<%=baseUrl%>wedding/movie/base_youku.jsp?url=http://player.youku.com/player.php/sid/XNjE4ODM3MDUy/v.swf&width=800&height=600"
       id="link" target="_blank">
        <%=baseUrl%>wedding/movie/base_youku.jsp?url=http://player.youku.com/player.php/sid/XNjE4ODM3MDUy/v.swf&width=800&height=600
    </a>
</div>
</body>
</html>