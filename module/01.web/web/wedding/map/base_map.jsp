<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String location = request.getParameter("location");
    String zoom = request.getParameter("zoom");
    String target = new String(request.getParameter("target").getBytes("ISO-8859-1"), "UTF-8");
    String region = new String(request.getParameter("region").getBytes("ISO-8859-1"), "UTF-8");
    String place = new String(request.getParameter("place").getBytes("ISO-8859-1"), "UTF-8");
    String phone = request.getParameter("phone");
    String width = request.getParameter("width");
    String height = request.getParameter("height");
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>地图</title>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=1.5&ak=508a48fd31e045c23f42b9d71febbd41"></script>
    <script src="http://d1.lashouimg.com/static/js/release/jquery-1.4.2.min.js" type="text/javascript"></script>
    <style type="text/css">
        html,body{
            border: 1px solid gray;
            width:<%=width%>px;
            height:<%=height%>px;
            margin:0;
            overflow:hidden;
        }
    </style>
</head>
<body>
    <div style="width:<%=width%>px;height:<%=height%>px;border:1px solid gray;" id="container">
    </div>
</body>
</html>
<script type="text/javascript">
    var map = new BMap.Map("container");//地图对象
    map.centerAndZoom(new BMap.Point(<%=location%>), <%=zoom%>);//设初始化地图
    map.enableScrollWheelZoom();//启用滚轮放大缩小，默认禁用
    var marker=new BMap.Marker(new BMap.Point(<%=location%>));//标注表示地图上的点，可自定义标注的图标
    map.addOverlay(marker);//向地图添加覆盖物
    map.enableKeyboard();//启用键盘操作，默认禁用
    map.enableInertialDragging();//启用地图惯性拖拽，默认禁用
    map.addControl(new BMap.NavigationControl());//地图平移缩放控件，PC端默认位于地图左上方
    map.addControl(new BMap.ScaleControl());//比例尺控件，默认位于地图左下方，显示地图的比例关系
    map.addControl(new BMap.OverviewMapControl());//缩略地图控件，默认位于地图右下方，是一个可折叠的缩略地图
    map.addControl(new BMap.MapTypeControl());//地图类型控件，默认位于地图右上方

    var licontent="<b><%=target%></b><br>";
    licontent+="<span><strong>地址：</strong><%=place%></span><br>";
    licontent+="<span><strong>电话：</strong><%=phone%></span><br>";
    licontent+="<span class=\"input\"><strong></strong><input class=\"outset\" type=\"text\" name=\"origin\" value=\"\"/><input class=\"outset-but\" type=\"button\" value=\"公交\" onclick=\"gotobaidu(1)\" /><input class=\"outset-but\" type=\"button\" value=\"驾车\"  onclick=\"gotobaidu(2)\"/><a class=\"gotob\" href=\"url=\"http://api.map.baidu.com/direction?destination=latlng:"+marker.getPosition().lat+","+marker.getPosition().lng+"|name:<%=target%>"+"®ion=<%=region%>"+"&output=html\" target=\"_blank\"></a></span>";
    var hiddeninput="<input type=\"hidden\" value=\""+'<%=region%>'+"\" name=\"region\" /><input type=\"hidden\" value=\"html\" name=\"output\" /><input type=\"hidden\" value=\"driving\" name=\"mode\" /><input type=\"hidden\" value=\"latlng:"+marker.getPosition().lat+","+marker.getPosition().lng+"|name:<%=target%>"+"\" name=\"destination\" />";
    var content1 ="<form id=\"gotobaiduform\" action=\"http://api.map.baidu.com/direction\" target=\"_blank\" method=\"get\"><span style=\"font-size: 10px;\">" + licontent +hiddeninput+"</span></form>";
    var opts1 = { width: 300 };

    var infoWindow = new BMap.InfoWindow(content1, opts1);//信息窗口也是一种特殊的覆盖物
    marker.openInfoWindow(infoWindow);//打开窗口
    marker.addEventListener('click',function(){ marker.openInfoWindow(infoWindow);});//监听点击事件，打开窗口

    function gotobaidu(type)
    {
        if($.trim($("input[name=origin]").val())=="")
        {
            alert("请输入起点！");
            return;
        }else{
            if(type==1)
            {
                $("input[name=mode]").val("transit");
                $("#gotobaiduform")[0].submit();
            }else if(type==2)
            {
                $("input[name=mode]").val("driving");
                $("#gotobaiduform")[0].submit();
            }
        }
    }
</script>
