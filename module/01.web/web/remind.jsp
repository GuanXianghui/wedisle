<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleRemindDao" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleRemind" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>婚礼岛 - wedisle.com</title>
    <link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
    <link href="./style/flexslider.css" rel="stylesheet" type="text/css" />
    <link href="./style/homePlus.css" rel="stylesheet" type="text/css" />
    <link href="./style/main.css" rel="stylesheet" type="text/css" />
    <link href="./style/tools.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="./Scripts/jquery-min.js"></script>
    <script type="text/javascript" src="./Scripts/comm.js"></script>
    <script type="text/javascript" src="./Scripts/main.js"></script>
    <script type="text/javascript" src="./Scripts/GetCNDate.js"></script>
    <script type="text/javascript" src="./Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
    <script type="text/javascript" src="./Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <link rel="stylesheet" type="text/css" href="./Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
    <script type="text/javascript">
        <%
            // 要求必须先登录
            if(!isLogin)
            {
        %>
        location.href = "<%=baseUrl%>login.jsp";
        <%
                return;
            }
        %>

        //选择年月
        var chooseYear = <%=new SimpleDateFormat("yyyy").format(new java.util.Date())%>;
        var chooseMonth = <%=new SimpleDateFormat("MM").format(new java.util.Date())%>;

        //初始化加速加载页面
        <%
            int userId = wedisleUser.getId();
            String beginDate = "20140213";
            String endDate = "20140405";
            List<WedisleRemind> wedisleReminds = WedisleRemindDao.queryWedisleRemindsBetweenDate(userId, beginDate, endDate);
        %>
        var iniBeginDate = "<%=beginDate%>";
        var iniEndDate = "<%=endDate%>";
        var iniReminds = eval(<%=WedisleUtils.getJsonFromWedisleReminds(wedisleReminds)%>);
        var isIni = true;

        //页面中每天的提醒数据
        var reminds = new Array();
        //查看提醒id
        var showRemindId = 0;

        //初始化
        $(document).ready(function() {
            $(".dialogBtn").fancybox();
            initMonth(chooseYear, chooseMonth);
        });

        //初始化月份
        function initMonth(year, month){
            //ajax获取当月工作日信息
            //getAjaxWorkDay(new Date(year, month-1, 1).format("yyyyMMdd").substr(0, 6));
            var daysInMonth = getDaysFromYearAndMonth(year, month);
            var startDate = new Date(year, month-1, 1);
            var startDay = startDate.getDay();
            //控制第五行和第六行的显示
            document.getElementById("ul_1").style.display = "none";
            document.getElementById("ul_2").style.display = "none";
            if(daysInMonth + startDay > 28){
                document.getElementById("ul_1").style.display = "";
            }
            if(daysInMonth + startDay > 35){
                document.getElementById("ul_2").style.display = "";
            }

            //开始日期和结束日期
            var beginDate = "";
            var endDate = "";

            //清空每个格子
            for(var i=1;i<=42;i++){
                var date = addDate(startDate, -1*startDay+i-1);
                var dateStr = date.format("yyyyMMdd");
                if(i==1){
                    beginDate = dateStr;
                }
                if(i==42){
                    endDate = dateStr;
                }

                document.getElementById("li_" + i).className = "leftday";
                if(i%7 > 4 || i%7 == 0){
                    document.getElementById("li_" + i).className = "rightday";
                }
                if(startDate.format("yyyyMM") != date.format("yyyyMM")){
                    document.getElementById("li_" + i).className += " othermonth";
                }
                var html = "<h3>" + dateStr.substr(6,2) + "<span class=\"moonday\">"
                        + GetCNDate(date).substr(GetCNDate(date).length-2) + "</span></h3>"
                        + "<ul id=\"li_" + i + "_reminds\" class=\"reminds\">"
                        + "<li><a id=\"li_" + i + "_remindA\" href=\"javascript:remindActive(" + i + ")\">+添加提醒</a></li>"
                        + "</ul>"
                        + "<div id=\"li_" + i + "_remindOut\" class=\"remindOut\" style=\"display: none;\">"
                        + "<div class=\"remindIn\">"
                        + "<ul class=\"formedit\">"
                        + "<li>"
                        + "<label>事件<span class=\"requiredico\">*</span></label>"
                        + "<input id=\"li_" + i + "_event\" type=\"text\" value=\"\" />"
                        + "</li>"
                        + "<li>"
                        + "<label>时间<span class=\"requiredico\">*</span></label>"
                        + "<label id=\"li_" + i + "_date\" style=\"display: none;\">" + dateStr + "</label>"
                        + "<label id=\"li_" + i + "_operateType\" style=\"display: none;\">save</label>"
                        + "<span>" + date.format("yyyy-M-dd") + " " + getWeekDay(date) + "</span></li>"
                        + "<li>"
                        + "<label>提醒</label>"
                        + "<select id=\"li_" + i + "_remindType\" onchange=\"changeRemindType(" + i + ")\">"
                        + "<option value=\"no\">不提醒</option>"
                        + "<option value=\"before\">前一天</option>"
                        + "<option value=\"same\">同一天</option>"
                        + "<option value=\"time\">指定时间</option>"
                        + "</select>"
                        + "<br/>"
                        + "<input id=\"li_" + i + "_remindDate\" type=\"date\" style=\"display: none;\"/>"
                        + "<input id=\"li_" + i + "_remindTime\" type=\"time\" style=\"display: none;\"/>"
                        + "</li>"
                        + "<li>"
                        + "<button class=\"btnsave\" onclick=\"saveRemind(" + i + ")\">保存</button>"
                        + "<button class=\"btntext\" onclick=\"closeRemind(" + i + ");\">关闭</button><div class=\"msgtip msgwarn\" id=\"li_" + i + "_remindMsg\" style=\"diaplay: none;\"></div>"
                        + "</li>"
                        + "</ul>"
                        + "</div>"
                        + "</div>";
                html += "<div id=\"li_" + i + "_showRemindOut\" class=\"remindOut\" style=\"display: none;\">"
                        + "<div class=\"remindIn\">"
                        + "<ul>"
                        + "<li id=\"li_" + i + "_showRemindEvent\">登记结婚</li>"
                        + "<li id=\"li_" + i + "_showRemindDate\">2014-2-23 星期三</li>"
                        + "<li id=\"li_" + i + "_showRemindDetail\">提醒：2014-2-22 8:00</li>"
                        + "<li>"
                        + "<button class=\"btnsave\" onclick=\"beforeUpdateRemind(" + i + ");\">编辑</button>"
                        + "<button class=\"btndelete\" onclick=\"deleteRemind(" + i + ");\">删除</button>"
                        + "<button class=\"btntext\" onclick=\"closeShowRemind(" + i + ");\">关闭</button><div class=\"msgtip msgwarn\" id=\"li_" + i + "_showRemindMsg\" style=\"diaplay: none;\"></div>"
                        + "</li>"
                        + "</ul>"
                        + "</div>"
                        + "</div>";

                document.getElementById("li_" + i).innerHTML = html;
            }
            for(var i=1;i<=daysInMonth;i++){
                var date = new Date(year, month-1, i).format("yyyyMMdd");
            }
            //展示年和月
            showYearAndMonth();

            if(iniBeginDate == beginDate && iniEndDate == endDate && isIni==true){
                reminds = iniReminds;
                isIni = false;
            } else {
                //根据起止日期查用户提醒
                queryRemindsBetweenDate(beginDate, endDate);
            }

            //填充用户提醒
            if(reminds.length > 0){
                for(var i=1;i<=42;i++){
                    var date = document.getElementById("li_" + i + "_date").innerHTML;
                    var html = "";
                    for(var j=0;j<reminds.length;j++){
                        if(reminds[j]["date"] == date){
                            html += "<li><a id=\"li_id_" + reminds[j]["id"] + "\" href=\"javascript:showRemindActive(" + i + "," + reminds[j]["id"] + ")\">" + reminds[j]["event"] + "</a></li>";
                        }
                    }
                    document.getElementById("li_" + i + "_reminds").innerHTML += html;
                }
            }
        }

        //展示年和月
        function showYearAndMonth(){
            document.getElementById("show_year_id").innerHTML = chooseYear;
            document.getElementById("show_month_id").innerHTML = chooseMonth;
        }

        // 计算这个月有几天
        function getDaysFromYearAndMonth(year, month){
            //每月的总天数
            var arr = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
            //计算当前年是否是闰年
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
            {
                arr[1] = "29";
            }
            return arr[month -1];
        }

        //点击添加提醒
        function remindActive(index){
            for(var i=1;i<=42;i++){
                document.getElementById("li_" + i + "_remindOut").style.display = "none";
                document.getElementById("li_" + i + "_remindA").className = "";
                document.getElementById("li_" + i + "_showRemindOut").style.display = "none";
            }
            for(var i=0;i<reminds.length;i++){
                document.getElementById("li_id_" + reminds[i]["id"]).className = "";
            }
            document.getElementById("li_" + index + "_remindOut").style.display = "";
            document.getElementById("li_" + index + "_remindA").className = "remindActive";
            document.getElementById("li_" + index + "_operateType").innerHTML = "save";
        }

        //点击提醒
        function showRemindActive(index, id){
            for(var i=1;i<=42;i++){
                document.getElementById("li_" + i + "_remindOut").style.display = "none";
                document.getElementById("li_" + i + "_remindA").className = "";
                document.getElementById("li_" + i + "_showRemindOut").style.display = "none";
            }
            for(var i=0;i<reminds.length;i++){
                document.getElementById("li_id_" + reminds[i]["id"]).className = "";
            }

            for(var i=0;i<reminds.length;i++){
                if(reminds[i]["id"] == id){
                    document.getElementById("li_" + index + "_showRemindEvent").innerHTML = reminds[i]["event"];
                    var dateStr = reminds[i]["date"];
                    var date = new Date(dateStr.substr(0, 4), dateStr.substr(4, 2)-1, dateStr.substr(6, 2));
                    document.getElementById("li_" + index + "_showRemindDate").innerHTML = date.format("yyyy-MM-dd") + " " + getWeekDay(date) ;
                    var remindDetail = "";
                    var remindDateStr = reminds[i]["remindDate"];
                    var remindTimeStr = reminds[i]["remindTime"];
                    if(reminds[i]["remindType"] == "no"){
                        remindDetail = "不提醒";
                    } else if(reminds[i]["remindType"] == "before"){
                        remindDetail = "提醒：" + addDate(date, -1).format("yyyy-MM-dd 00:00");
                    } else if(reminds[i]["remindType"] == "same"){
                        remindDetail = "提醒：" + date.format("yyyy-MM-dd 00:00");
                    }  else if(reminds[i]["remindType"] == "time"){
                        remindDetail = "提醒：" + remindDateStr.substr(0,4) + "-" + remindDateStr.substr(4,2) + "-"
                                + remindDateStr.substr(6,2) + " " + remindTimeStr.substr(0,2) + ":"
                                + remindTimeStr.substr(2,2);
                    }
                    document.getElementById("li_" + index + "_showRemindDetail").innerHTML = remindDetail;
                }
            }
            document.getElementById("li_id_" + id).className = "remindActive";
            document.getElementById("li_" + index + "_showRemindOut").style.display = "";
            showRemindId = id;
        }

        //点击关闭提醒
        function closeRemind(index){
            document.getElementById("li_" + index + "_remindOut").style.display = "none";
            document.getElementById("li_" + index + "_remindA").className = "";
            if(showRemindId > 0){
                document.getElementById("li_id_" + showRemindId).className = "";
            }
        }

        //点击关闭查看提醒
        function closeShowRemind(index){
            if(showRemindId > 0){
                document.getElementById("li_id_" + showRemindId).className = "";
            }
            document.getElementById("li_" + index + "_showRemindOut").style.display = "none";
        }

        //点击编辑按钮
        function beforeUpdateRemind(index){
            document.getElementById("li_" + index + "_showRemindOut").style.display = "none";
            document.getElementById("li_" + index + "_remindOut").style.display = "";
            document.getElementById("li_" + index + "_operateType").innerHTML = "update";
            for(var i=0;i<reminds.length;i++){
                if(reminds[i]["id"] == showRemindId){
                    document.getElementById("li_" + index + "_event").value = reminds[i]["event"];
                    document.getElementById("li_" + index + "_remindType").value = reminds[i]["remindType"];
                    changeRemindType(index);
                    document.getElementById("li_" + index + "_remindDate").value = reminds[i]["remindDate"].substr(0,4)
                            + "-" + reminds[i]["remindDate"].substr(4,2) + "-" + reminds[i]["remindDate"].substr(6,2);
                    document.getElementById("li_" + index + "_remindTime").value = reminds[i]["remindTime"].substr(0,2)
                            + ":" + reminds[i]["remindTime"].substr(2,2);
                }
            }
        }

        //删除提醒
        function deleteRemind(index){
            var operateType = "delete";
            var SUCCESS_STR = "success";//成功编码
            $.ajax({
                type: "post",
                async: false,
                url: "<%=baseUrl%>tool/remind/operateRemind.jsp",
                data: "operateType=" + operateType + "&id=" + showRemindId,
                success: function(data, textStatus){
                    if((SUCCESS_STR == textStatus) && (null != data))
                    {
                        data = eval("(" + data + ")");
                        if(false == data["isSuccess"]){
                            document.getElementById("li_" + index + "_showRemindMsg").style.display = "";
                            document.getElementById("li_" + index + "_showRemindMsg").innerHTML = data["message"];
                            return;
                        } else {
                            document.getElementById("li_id_" + showRemindId).style.display = "none";
                            document.getElementById("li_" + index + "_showRemindOut").style.display = "none";
                        }
                    } else
                    {
                        alert("Connection failed,please try again later!");
                    }
                },
                error: function(data, textStatus){
                    alert("Connection failed,please try again later!");
                }
            });
        }

        //点击保存提醒
        function saveRemind(index){
            var operateType = document.getElementById("li_" + index + "_operateType").innerHTML;
            var event = document.getElementById("li_" + index + "_event").value;
            var date = document.getElementById("li_" + index + "_date").innerHTML;
            var remindType = document.getElementById("li_" + index + "_remindType").value;
            var remindDate = document.getElementById("li_" + index + "_remindDate").value;
            var remindTime = document.getElementById("li_" + index + "_remindTime").value;
            if(remindDate!=""){remindDate = remindDate.replace("-", "").replace("-", "");}
            if(remindTime!=""){remindTime = remindTime.replace(":", "") + "00";}
            if(event == ""){
                document.getElementById("li_" + index + "_remindMsg").style.display = "";
                document.getElementById("li_" + index + "_remindMsg").innerHTML = "请输入事件名称！";
                return;
            }
            if(remindType == "time" && "" == remindDate){
                document.getElementById("li_" + index + "_remindMsg").style.display = "";
                document.getElementById("li_" + index + "_remindMsg").innerHTML = "请选择提醒日期！";
                return;
            }
            if(remindType == "time" && "" == remindTime){
                document.getElementById("li_" + index + "_remindMsg").style.display = "";
                document.getElementById("li_" + index + "_remindMsg").innerHTML = "请选择提醒时间！";
                return;
            }
            document.getElementById("li_" + index + "_remindMsg").style.display = "none";

            var SUCCESS_STR = "success";//成功编码
            $.ajax({
                type: "post",
                async: false,
                url: "<%=baseUrl%>tool/remind/operateRemind.jsp",
                data: "operateType=" + operateType + "&event=" + event + "&date=" + date + "&remindType=" + remindType + "&remindDate=" + remindDate + "&remindTime=" + remindTime + "&updateId=" + showRemindId,
                success: function(data, textStatus){
                    if((SUCCESS_STR == textStatus) && (null != data))
                    {
                        data = eval("(" + data + ")");
                        if(false == data["isSuccess"]){
                            document.getElementById("li_" + index + "_remindMsg").style.display = "";
                            document.getElementById("li_" + index + "_remindMsg").innerHTML = data["message"];
                            return;
                        } else {
                            if(operateType == "save"){
                                var array = new Array();
                                array["id"] = data["id"];
                                array["event"] = event;
                                array["date"] = date;
                                array["remindType"] = remindType;
                                array["remindDate"] = remindDate;
                                array["remindTime"] = remindTime;
                                reminds.push(array);
                                closeRemind(index);

                                var html = "<li><a id=\"li_id_" + array["id"]
                                        + "\" href=\"javascript:showRemindActive(" + index + ","
                                        + array["id"] + ")\">" + event + "</a></li>";
                                document.getElementById("li_" + index + "_reminds").innerHTML += html;
                            } else if(operateType == "update"){
                                for(var i=0;i<reminds.length;i++){
                                    if(reminds[i]["id"] == showRemindId){
                                        reminds[i]["event"] = event;
                                        reminds[i]["remindType"] = remindType;
                                        reminds[i]["remindDate"] = remindDate;
                                        reminds[i]["remindTime"] = remindTime;
                                        showRemindActive(index, showRemindId);
                                        document.getElementById("li_id_" + showRemindId).innerHTML = event;
                                    }
                                }
                            }
                        }
                    } else
                    {
                        alert("Connection failed,please try again later!");
                    }
                },
                error: function(data, textStatus){
                    alert("Connection failed,please try again later!");
                }
            });
        }

        //改变提醒类型
        function changeRemindType(index){
            document.getElementById("li_" + index + "_remindDate").style.display = "none";
            document.getElementById("li_" + index + "_remindTime").style.display = "none";
            if(document.getElementById("li_" + index + "_remindType").value == "time"){
                document.getElementById("li_" + index + "_remindDate").style.display = "";
                document.getElementById("li_" + index + "_remindTime").style.display = "";
            }
        }

        //date+addDate天
        function addDate(date, addDate){
            var dateValue = date.valueOf();
            return new Date(dateValue + addDate  * 24 * 60 * 60 * 1000);
        }

        //获取星期几
        function getWeekDay(date){
            var week = date.getDay();
            var weekday = "";
            if (week == 0)
                weekday = "星期日";
            else if (week == 1)
                weekday = "星期一";
            else if (week == 2)
                weekday = "星期二";
            else if (week == 3)
                weekday = "星期三";
            else if (week == 4)
                weekday = "星期四";
            else if (week == 5)
                weekday = "星期五";
            else if (week == 6)
                weekday = "星期六";
            return weekday;
        }

        //根据起止日期查用户提醒
        function queryRemindsBetweenDate(beginDate, endDate){
            var SUCCESS_STR = "success";//成功编码
            $.ajax({
                type: "post",
                async: false,
                url: "<%=baseUrl%>tool/remind/queryRemindsBetweenDate.jsp",
                data: "beginDate=" + beginDate + "&endDate=" + endDate,
                success: function(data, textStatus){
                    if((SUCCESS_STR == textStatus) && (null != data))
                    {
                        data = eval("(" + data + ")");
                        if(false == data["isSuccess"]){
                            alert(data["message"]);
                            return;
                        } else {
                            reminds = data["reminds"];
                        }
                    } else
                    {
                        alert("Connection failed,please try again later!");
                    }
                },
                error: function(data, textStatus){
                    alert("Connection failed,please try again later!");
                }
            });
        }

        //选择上个月
        function lastMonth(){
            chooseMonth = chooseMonth - 1;
            if(chooseMonth == 0){
                chooseYear = chooseYear - 1;
                chooseMonth = 12;
            }
            initMonth(chooseYear, chooseMonth);
            //页面中每天的提醒数据
            reminds = new Array();
            //查看提醒id
            showRemindId = 0;
        }

        //选择下个月
        function nextMonth(){
            chooseMonth = chooseMonth + 1;
            if(chooseMonth == 13){
                chooseYear = chooseYear + 1;
                chooseMonth = 1;
            }
            initMonth(chooseYear, chooseMonth);
            //页面中每天的提醒数据
            reminds = new Array();
            //查看提醒id
            showRemindId = 0
        }
    </script>
</head>
<body>
<div id="calendar" class="calendarFrame">
<h2><span id="show_year_id">2013</span>-<span id="show_month_id">10</span>&nbsp;<a href="javascript:lastMonth()" class="icoPrev icofont" title="上个月">&lt;</a><a href="javascript:nextMonth()" class="icoNext icofont" title="下个月">&gt;</a></h2>
<div class="calendarMod">
<ul class="calendarline dateline">
    <li>周日</li>
    <li>周一</li>
    <li>周二</li>
    <li>周三</li>
    <li>周四</li>
    <li>周五</li>
    <li>周六</li>
</ul>
<ul class="calendarline dayline">
    <li id="li_1" class="leftday othermonth">
        <h3>29<span class="moonday">十九</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_2" class="leftday othermonth">
        <h3>30<span class="moonday">二十</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_3" class="leftday">
        <h3>1<span class="moonday">廿一</span></h3>
        <ul class="reminds">
            <li><a href="#" class="remindActive">+添加提醒</a></li>
            <li><a href="#">提醒一</a></li>
            <li><a href="#">提醒二</a></li>
        </ul>
        <div class="remindOut">
            <div class="remindIn">
                <ul class="formedit">
                    <li>
                        <label>事件<span class="requiredico">*</span></label>
                        <input type="text" value="" />
                    </li>
                    <li>
                        <label>时间<span class="requiredico">*</span></label>
                        2014-2-23 星期三 </li>
                    <li>
                        <label>提醒</label>
                        <select>
                            <option>不提醒</option>
                            <option>前一天</option>
                            <option>同一天</option>
                            <option>指定时间</option>
                        </select>
                        <br/>
                        <input type="date" />
                        <input type="time" />
                    </li>
                    <li>
                        <button class="btnsave">保存</button>
                        <button class="btntext">关闭</button>
                    </li>
                </ul>
            </div>
        </div>
    </li>
    <li id="li_4" class="leftday">
        <h3>2<span class="moonday">廿二</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_5" class="rightday">
        <h3>3<span class="moonday">廿三</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_6" class="rightday">
        <h3>4<span class="moonday">廿四</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_7" class="rightday">
        <h3>5<span class="moonday">廿五</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
</ul>
<div class="cf"></div>
<ul class="calendarline dayline">
    <li id="li_8" class="leftday">
        <h3>6<span class="moonday">廿六</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_9" class="leftday">
        <h3>7<span class="moonday">廿七</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_10" class="leftday">
        <h3>8<span class="moonday">廿八</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_11" class="leftday">
        <h3>9<span class="moonday">廿九</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_12" class="rightday">
        <h3>10<span class="moonday">三十</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_13" class="rightday">
        <h3>11<span class="moonday">三月</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_14" class="rightday">
        <h3>12<span class="moonday">初二</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
</ul>
<div class="cf"></div>
<ul class="calendarline dayline">
    <li id="li_15" class="leftday">
        <h3>13<span class="moonday">初三</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_16" class="leftday">
        <h3>14<span class="moonday">初四</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_17" class="leftday">
        <h3>15<span class="moonday">初五</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_18" class="leftday">
        <h3>16<span class="moonday">初六</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_19" class="rightday">
        <h3>17<span class="moonday">初七</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_20" class="rightday">
        <h3>18<span class="moonday">初八</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_21" class="rightday">
        <h3>19<span class="moonday">初九</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
</ul>
<div class="cf"></div>
<ul class="calendarline dayline">
    <li id="li_22" class="leftday">
        <h3>20<span class="moonday">初十</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_23" class="leftday">
        <h3>21<span class="moonday">十一</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_24" class="leftday">
        <h3>22<span class="moonday">十二</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_25" class="leftday">
        <h3>23<span class="moonday">十三</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_26" class="rightday">
        <h3>24<span class="moonday">十四</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_27" class="rightday">
        <h3>25<span class="moonday">十五</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
            <li><a href="#" class="remindActive">提醒一</a></li>
            <li><a href="#">提醒二</a></li>
            <li><a href="#">提醒二</a></li>
        </ul>
        <div class="remindOut">
            <div class="remindIn">
                <ul>
                    <li>登记结婚</li>
                    <li>2014-2-23 星期三</li>
                    <li>提醒：2014-2-22 8:00</li>
                    <li>
                        <button class="btnsave">编辑</button>
                        <button class="btndelete">删除</button>
                        <button class="btntext">关闭</button>
                    </li>
                </ul>
            </div>
        </div>
    </li>
    <li id="li_28" class="rightday">
        <h3>26<span class="moonday">十六</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
</ul>
<div class="cf"></div>
<ul id="ul_1" class="calendarline dayline">
    <li id="li_29" class="leftday">
        <h3>27<span class="moonday">十七</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_30" class="leftday">
        <h3>28<span class="moonday">十八</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_31" class="leftday">
        <h3>29<span class="moonday">十九</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_32" class="leftday">
        <h3>30<span class="moonday">二十</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_33" class="rightday">
        <h3>31<span class="moonday">廿一</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_34" class="rightday othermonth">
        <h3>1<span class="moonday">廿二</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
    <li id="li_35" class="rightday othermonth">
        <h3>2<span class="moonday">廿三</span></h3>
        <ul class="reminds">
            <li><a href="#">+添加提醒</a></li>
        </ul>
    </li>
</ul>
<div class="cf"></div>
<ul id="ul_2" class="calendarline dayline">
    <li id="li_36">
    </li>
    <li id="li_37">
    </li>
    <li id="li_38">
    </li>
    <li id="li_39">
    </li>
    <li id="li_40">
    </li>
    <li id="li_41">
    </li>
    <li id="li_42">
    </li>
</ul>
<div class="cf"></div>
</div>
</div>
</body>
</html>