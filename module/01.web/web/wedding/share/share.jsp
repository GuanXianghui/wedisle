<%@ page import="com.gxx.record.interfaces.BaseInterface" %>
<%@ page import="com.gxx.record.utils.PropertyUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <% String baseUrl = PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL);%>
    <title></title>
    <script type="text/javascript">
        var baseUrl = "<%=baseUrl%>";
        function change()
        {
            var title = document.getElementById("title").value;
            var content = document.getElementById("content").value;
            var url = document.getElementById("url").value;
            var photo = document.getElementById("photo").value;


            var temp = "http://service.weibo.com/share/share.php?url=" + url
                    + "&title=" + title + "&pic=" + photo;
            document.getElementById("weibo").href = temp;
            document.getElementById("weibo").innerHTML = temp;

            temp = "http://connect.qq.com/widget/shareqq/index.html?"
                    + "url=" + url + "&"
                    + "desc=" + title + "&"
                    + "summary=" + content + "&"//发不出去
                    + "pics=" + photo;//发不出去
            document.getElementById("qq").href = temp;
            document.getElementById("qq").innerHTML = temp;

            temp = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?"
                    + "url=" + url + "&"
                    + "title=" + title + "&"
                    + "summary=" + content + "&"
                    + "pics=" + photo;
            document.getElementById("qq_zone").href = temp;
            document.getElementById("qq_zone").innerHTML = temp;

            temp = "http://widget.renren.com/dialog/share?"
                    + "resourceUrl=" + url + "&"
                    + "title=" + title + "&"
                    + "images=" + photo + "&"
                    + "description=" + content;
            document.getElementById("renren").href = temp;
            document.getElementById("renren").innerHTML = temp;

            temp = "http://share.v.t.qq.com/index.php?"
                    + "c=share&"
                    + "a=index&"
                    + "title=" + title + "&"
                    + "pic=" + photo + "&"
                    + "url=" + url;
            document.getElementById("qq_weibo").href = temp;
            document.getElementById("qq_weibo").innerHTML = temp;

            temp = "http://www.kaixin001.com/login/open_login.php?"
                    + "flag=1&"
                    + "url=%2Frest%2Frecords.php"
                    + "%3F"
                    + "url"
                    + "%3D"
                    + url
                    + "%26content"
                    + "%3D"
                    + content
                    + "%26"
                    + "pic"
                    + "%3D"
                    + photo
                    + "%26starid%3D%24%7BRALATEUID%7D%26aid%3D100013770%26style%3D111";
            document.getElementById("kaixin").href = temp;
            document.getElementById("kaixin").innerHTML = temp;

            temp = "http://go.10086.cn/ishare.do?"
                    + "m=wt&"
                    + "u=" + url + "&"
                    + "t=" + content + "&"
                    + "sid=c1659189f4a91d92218af2b2a2dbf51b&"
                    + "sso_command=checkLogin&"
                    + "sso_cl_key=pKaJczJqaU";
            document.getElementById("phone").href = temp;
            document.getElementById("phone").innerHTML = temp;

            temp = "https://chart.googleapis.com/chart?cht=qr&chs=150x150&choe=UTF-8&chld=L|4&chl=" + url;
            document.getElementById("weixin").href = temp;
            document.getElementById("weixin").innerHTML = temp;
            temp = "http://api.k780.com:88/?app=qr.get&level=L&size=6&data=" + url;
            document.getElementById("weixin2").href = temp;
            document.getElementById("weixin2").innerHTML = temp;
        }
    </script>
</head>
<body onload="change()">
<div align="center">
    <h1>分享内容到各个媒介</h1>
    <h6>
        注意：我们网站内部嵌入以下链接即可，但是参数需要配置
    </h6>
    <table style="border: 1px solid gray;">
        <tr>
            <td>标题:</td>
            <td><input type="text" onchange="change()" id="title" value="标题"></td>
        </tr>
        <tr>
            <td>内容:</td>
            <td><input type="text" onchange="change()" id="content" value="内容"></td>
        </tr>
        <tr>
            <td>网址链接:</td>
            <td><input type="text" onchange="change()" id="url" value="http://go.jhost.cn/gxx"></td>
        </tr>
        <tr>
            <td>图片地址:</td>
            <td><input type="text" onchange="change()" id="photo" value="http://go.jhost.cn/gxx/ueditor/jsp/head/1379921398393.jpg"></td>
        </tr>
    </table>
    <h1>分享新浪微博</h1>
    <a href="" id="weibo" target="_blank"></a>
    <h1>分享qq好友</h1>
    <a href="" id="qq" target="_blank"></a>
    <h1>分享qq空间</h1>
    <a href="" id="qq_zone" target="_blank"></a>
    <h1>分享人人网</h1>
    <a href="" id="renren" target="_blank"></a>
    <h1>分享腾讯微博</h1>
    <a href="" id="qq_weibo" target="_blank"></a>
    <h1>分享开心网</h1>
    <a href="" id="kaixin" target="_blank"></a>
    <h1>分享到手机</h1>
    <a href="" id="phone" target="_blank"></a>
    <h1>分享微信</h1>
    <a href="" id="weixin" target="_blank"></a><br>
    <a href="" id="weixin2" target="_blank"></a>
</div>
</body>
</html>