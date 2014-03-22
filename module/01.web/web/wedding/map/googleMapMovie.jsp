<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>Demo19-某地前往某地</title>
    <style type="text/css">
        html { height: 100%; }
        body { height: 100%; margin: 0px; padding: 0px; }
        .search { margin: 30px auto; width: 50%; }
        #map_canvas { width: 80%; height: 70%; margin: 30px auto; }
        .pt{position: absolute; border: 1px solid gray; border-width: 1px 1px 1px 1px; width: 1px; height: 9px; cursor: pointer;}
    </style>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript">
        var directionDisplay;
        var directionsService = new google.maps.DirectionsService();
        var map;
        function initialize()
        {
            directionsDisplay = new google.maps.DirectionsRenderer();
            var guangzhou = new google.maps.LatLng(34.26667, 108.95000);
            var myOptions = {
                zoom: 4,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                center: guangzhou,
                draggableCursor:"crosshair",
                draggingCursor:"move"
            }
            map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
            directionsDisplay.setMap(map);

            calcRoute();

            google.maps.event.addListener(map, 'click', function(event) {
                alert(event.latLng);
            });
        }

        function calcRoute()
        {
            var start = "上海";
            var end = "广州";
            var request = {
                origin: start,
                destination: end,
                travelMode: google.maps.DirectionsTravelMode.DRIVING
            };
            directionsService.route(request, function(response, status)
            {
                if (status == google.maps.DirectionsStatus.OK)
                {
                    //alert(response.routes[0].overview_path.length);
                    //alert(response.routes[0].legs[0].distance.text);
                    //alert(response.routes[0].legs[0].distance.value);
                    paths = response.routes[0].overview_path;
                    directionsDisplay.setDirections(response);
                    initPts();
                }
            });
        }

        var paths;
        var marker;

        function changeMark(i)
        {
            //alert(paths[i].lat());
            //alert(paths[i].lng());
            var ll = new google.maps.LatLng(paths[i].lat(), paths[i].lng());
            if(null != marker)
            {
                marker.setMap(null);
            }
            marker = new google.maps.Marker({
                position: ll,
                map: map,
                title: 'Hello World!',
                icon: 'http://maps.google.com/mapfiles/ms/micons/horsebackriding.png'
            });
            marker.setAnimation(google.maps.Animation.BOUNCE);
            //go2(i);
        }

        function go2(i)
        {
            var request = {
                origin: paths[i].lat() + ',' + paths[i].lng(),
                destination: paths[i+1].lat() + ',' + paths[i+1].lng(),
                travelMode: google.maps.DirectionsTravelMode.DRIVING
            };

            alert(request.origin);
            alert(request.destination);
            directionsService.route(request, function(response, status)
            {
                if (status == google.maps.DirectionsStatus.OK)
                {
                    alert(response.routes[0].overview_path);
                    alert(response.routes[0].legs[0].distance.text);
                    //alert(response.routes[0].legs[0].distance.value);
                    //paths = response.routes[0].overview_path;
                    directionsDisplay.setDirections(response);
                }
            });
        }

        function initPts()
        {
            var content = "";
            for(var i=0;i<paths.length;i+=1)
            {
                var leftStr = i*100/paths.length + "%";
                var tempContent = "<span class=\"pt\" style=\"left: " + leftStr + ";\" onmouseover=\"changeMark(" + i + ");\" onmouseout=\"this.style.backgroundColor='white'\"></span>";
                content += tempContent;
            }

            document.getElementById("pts").innerHTML=content;
        }
    </script>
</head>
<body onload="initialize()">
<div id="map_canvas">
</div>
<%--<div align="center">--%>
    <%--<button onclick="changeMark(100)">100</button>--%>
    <%--<button onclick="changeMark(101)">101</button>--%>
    <%--<button onclick="changeMark(102)">102</button>--%>
    <%--<button onclick="changeMark(103)">103</button>--%>
    <%--<button onclick="changeMark(104)">104</button>--%>
    <%--<button onclick="changeMark(200)">200</button>--%>
<%--</div>--%>
<div align="center">
    <div style="position: relative; border: 1px solid gray; border-width: 0px 0px 1px 0px;
        width: 1000px; height: 10px;" id="pts"></div>
</div>
</body>
</html>