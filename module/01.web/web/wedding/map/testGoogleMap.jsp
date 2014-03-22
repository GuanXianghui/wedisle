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
        var map;
        function initialize()
        {
            var guangzhou = new google.maps.LatLng(34.26667, 108.95000);
            var myOptions = {
                zoom: 4,
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                center: guangzhou,
                draggableCursor:"crosshair",
                draggingCursor:"move"
            }
            map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

            google.maps.event.addListener(map, 'click', function(event) {
                alert(event.latLng);
            });
        }
    </script>
</head>
<body onload="initialize()">
<h1></h1>
<div id="map_canvas">
</div>
</body>
</html>