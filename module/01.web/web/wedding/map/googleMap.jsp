<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Simple Map</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
    <style>
        html, body, #map-canvas {
            height: 100%;
            margin: 0px;
            padding: 0px
        }
    </style>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>
    <script>
        var px = [31.151487,31.1504802,31.1555691,31.155747];
        var py = [121.503293,121.4973117,121.4962651,121.496726];
        // 0 定义参数
        var map;
        var directionsDisplay;
        var directionsService = new google.maps.DirectionsService();
        var circle;
        var circleCenter;
        var circleMarker;
        var r;

        // 1 初始化方法
        function initialize() {
            // 1.1 初始化Map参数
            var mapOptions = {
                zoom: 15,
                center: new google.maps.LatLng(0, 0),
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };

            // 1.2 初始化map
            map = new google.maps.Map(document.getElementById('map-canvas'),
                    mapOptions);

            // 1.3 初始化其他参数
            directionsDisplay = new google.maps.DirectionsRenderer();
            directionsDisplay.setMap(map);
        }

        // 初始化方法
        google.maps.event.addDomListener(window, 'load', initialize);

        // 设置围栏
        function click1()
        {
            var centerPointX = document.getElementById("centerPointXId").value;
            var centerPointY = document.getElementById("centerPointYId").value;
            r = document.getElementById("rId").value;
            circleCenter = new google.maps.LatLng(centerPointX, centerPointY);
            map.setCenter(circleCenter);
            circleMarker = new google.maps.Marker({
                map: map,
                position: circleCenter,
                title: '围栏中心'
            });
            circle = new google.maps.Circle({
                map: map,
                center: circleCenter,
                radius: parseFloat(r),
                strokeColor: "#0000FF",
                strokeOpacity: 1.0,
                strokeWeight: 2,
                fillColor: "#0000FF",
                fillOpacity: 0
            });
        }

        // 开车路径
        function calcRoute(t) {
            var waypts = [];
            for(var i=1;i<t;i=i+1)
            {
                waypts.push({
                    location: px[i] + ',' + py[i],
                    stopover:true});
            }
            var request;
            if(t == 1)
            {
                request = {
                    origin: px[0] + ',' + py[0],
                    destination: px[t] + ',' + py[t],
                    waypoints: waypts,
                    optimizeWaypoints: true,
                    travelMode: google.maps.DirectionsTravelMode.DRIVING
                };
            } else
            {
                request = {
                    origin: px[0] + ',' + py[0],
                    destination: px[t] + ',' + py[t],
                    waypoints: waypts,
                    optimizeWaypoints: true,
                    travelMode: google.maps.DirectionsTravelMode.DRIVING
                };
            }
            directionsService.route(request, function(response, status) {
                if (status == google.maps.DirectionsStatus.OK) {
                        directionsDisplay.setDirections(response);
                }
            });
            checkOutOfDistance(new google.maps.LatLng(px[t], py[t]));
        }

        // 测俩坐标直线距离
        function distance(LatLng1,LatLng2) {
            var a = Math.sin(LatLng1.lat() * Math.PI / 180) * Math.sin(LatLng2.lat() * Math.PI / 180);
            var b = Math.cos(LatLng1.lat() * Math.PI / 180) * Math.cos(LatLng2.lat() * Math.PI / 180) * Math.cos((LatLng2.lng() - LatLng1.lng()) * Math.PI / 180);
            return 6371000 * Math.acos(a + b);
        }

        // 判断目标当前跃出围栏
        function checkOutOfDistance(LatLng)
        {
            if(r < distance(LatLng, circleCenter))
            {
                alert("目标当前跃出围栏！");
            }
        }
    </script>
</head>
<body>
<div align="center">
    <table border="1">
        <tr>
            <td>
                设置初始化围栏
            </td>
            <td>
                中心坐标：
                <input id="centerPointXId" type="text" value="31.1515221">
                <input id="centerPointYId" type="text" value="121.5032767">
                范围：<input id="rId" type="text" value="600">米
            </td>
            <td>
                <input type="button" value="执行" onclick="click1()">
            </td>
        </tr>
        <tr>
            <td>
                第一步：
            </td>
            <td>
                从点[31.151487,121.503293]行驶到[31.1504802,121.4973117]
            </td>
            <td>
                <input type="button" value="执行" onclick="calcRoute(1)">
            </td>
        </tr>
        <tr>
            <td>
                第二步：
            </td>
            <td>
                从点[31.1504802,121.4973117]行驶到[31.1555691,121.4962651]
            </td>
            <td>
                <input type="button" value="执行" onclick="calcRoute(2)">
            </td>
        </tr>
        <tr>
            <td>
                第三步：
            </td>
            <td>
                从点[31.1555691,121.4962651]行驶到[31.155747,121.496726]
            </td>
            <td>
                <input type="button" value="执行" onclick="calcRoute(3)">
            </td>
        </tr>
    </table>
</div>
<div id="map-canvas" style="width:640px; height:480px"></div>
</body>
</html>







