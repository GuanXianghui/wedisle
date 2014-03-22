<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleWorkDayDao" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleWorkDay" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>工具 - 婚礼岛</title>
    <link rel="shortcut icon" type="image/x-icon" href="../favicon.ico" />
    <link href="../style/homePlus.css" rel="stylesheet" type="text/css" />
    <link href="../style/tools.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="../Scripts/jquery-min.js"></script>
    <script type="text/javascript" src="../Scripts/comm.js"></script>
    <script type="text/javascript" src="../Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
    <script type="text/javascript" src="../Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <link rel="stylesheet" type="text/css" href="../Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
    <script type="text/javascript">
        <%
            String monthStr = request.getParameter("month");
            if(StringUtils.isBlank(monthStr)){
                 monthStr = ServiceDataUtil.getDate(new Date()).substring(0, 6);
            }
            java.util.Date date = ServiceDataUtil.getDate(monthStr + "01");//取得当月第一天，下面用到的都是yyyy和MM

            int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
            int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
            String thisMonth = new SimpleDateFormat("yyyyMM").format(date);
            List<WedisleWorkDay> wedisleWorkDays = WedisleWorkDayDao.queryWedisleWorkDaysByMonth(thisMonth);
            String thisMonthJson = WedisleUtils.getJsonFromWedisleWorkDays(wedisleWorkDays);
            String lastMonth;
            if(month == 1){
                lastMonth = (year - 1) + "12";
            } else if (month <= 10){
                lastMonth = year + "0" + (month - 1);
            } else {
                lastMonth = year + "" + (month - 1);
            }
            String nextMonth;
            if(month == 12){
                nextMonth = (year + 1) + "01";
            } else if (month >= 9){
                nextMonth = year + "" + (month + 1);
            } else {
                nextMonth = year + "0" + (month + 1);
            }
            wedisleWorkDays = WedisleWorkDayDao.queryWedisleWorkDaysByMonth(nextMonth);
            String nextMonthJson = WedisleUtils.getJsonFromWedisleWorkDays(wedisleWorkDays);
        %>

        //本月和次月的工作日信息
        var thisMonthWorkDays = eval(<%=thisMonthJson%>);
        var nextMonthWorkDays = eval(<%=nextMonthJson%>);

        //选择年月
        var thisYear = <%=new SimpleDateFormat("yyyy").format(date)%>;
        var thisMonth = <%=new SimpleDateFormat("MM").format(date)%>;
        var nextYear = <%=nextMonth.substring(0, 4)%>;
        var nextMonth = <%=nextMonth.substring(4)%>;

        //yyyyMM
        var thisYearAndMonth = "<%=thisMonth%>";
        var nextYearAndMonth = "<%=nextMonth%>";

        //得到两个月的所有天数以及是否工作日
        var allDays = getAllDays();
        var allIsWorkDay = getAllIsWorkDay();

        //一个月有几天
        var thisDaysInMonth = 0;
        var nextDaysInMonth = 0;

        $(document).ready(function() {
            $(".dialogBtn").fancybox();
            initMonth(thisYear, thisMonth);
            getAjaxMarrySuggest("this");
        });

        //初始化月份
        function initMonth(year, month){
            var daysInMonth = getDaysFromYearAndMonth(year, month);
            thisDaysInMonth = daysInMonth;
            var startDay = new Date(year, month-1, 1).getDay();

            //清空每个格子
            for(var i=1;i<=84;i++){
                document.getElementById("td_" + i).innerHTML = "";
            }
            for(var i=1;i<=daysInMonth;i++){
                var html = "<h4  id=\"h4_" + thisYearAndMonth + ((i<10)?"0"+i:i) + "\"";
                html += ">" + i + "</h4>&nbsp;";
                document.getElementById("td_" + (startDay + i)).innerHTML = html;

                var day = new Date(year, month-1, i).getDay();
                var dateStr = new Date(year, month-1, i).format("yyyyMMdd");
                if(day == 6 || day == 0){
                    var addText = "假";
                    for(var j=0;j<thisMonthWorkDays.length;j++){
                        if(thisMonthWorkDays[j]["date"] == dateStr && thisMonthWorkDays[j]["isWorkDay"]==true){
                            addText = "班";
                            break;
                        }
                    }
                    document.getElementById("td_" + (startDay + i)).innerHTML += addText;
                } else {
                    for(var j=0;j<thisMonthWorkDays.length;j++){
                        if(thisMonthWorkDays[j]["date"] == dateStr && thisMonthWorkDays[j]["isWorkDay"]==false){
                            //document.getElementById("td_" + (startDay + i)).className = "vocation";
                            document.getElementById("td_" + (startDay + i)).innerHTML += "假";
                            break;
                        }
                    }
                }
            }

            //显示开头月份
            document.getElementById("show_this_month_id").innerHTML = year + "/" + (month>9?month:"0" + month);
            //算下个月
            if(month == 12){
                year = year + 1;
                month = 1;
            } else {
                month = month + 1;
            }
            //显示终止月份
            document.getElementById("show_next_month_id").innerHTML = year + "/" + (month>9?month:"0" + month);

            var lastMonthDaysInMonth = daysInMonth;
            daysInMonth = getDaysFromYearAndMonth(year, month);
            nextDaysInMonth = daysInMonth;
            for(var i=1;i<=daysInMonth;i++){
                var html = "<h4 style=\"color: purple;\"  id=\"h4_" + nextYearAndMonth + ((i<10)?"0"+i:i) + "\"";
                html += ">" + i + "</h4>&nbsp;";
                document.getElementById("td_" + (startDay + lastMonthDaysInMonth + i)).innerHTML = html;

                var day = new Date(year, month-1, i).getDay();
                var dateStr = new Date(year, month-1, i).format("yyyyMMdd");
                if(day == 6 || day == 0){
                    var addText = "假";
                    for(var j=0;j<nextMonthWorkDays.length;j++){
                        if(nextMonthWorkDays[j]["date"] == dateStr && nextMonthWorkDays[j]["isWorkDay"]==true){
                            addText = "班";
                            break;
                        }
                    }
                    document.getElementById("td_" + (startDay + lastMonthDaysInMonth + i)).innerHTML += addText;
                } else {
                    for(var j=0;j<nextMonthWorkDays.length;j++){
                        if(nextMonthWorkDays[j]["date"] == dateStr && nextMonthWorkDays[j]["isWorkDay"]==false){
                            //document.getElementById("td_" + (startDay + lastMonthDaysInMonth + i)).className = "vocation";
                            document.getElementById("td_" + (startDay + lastMonthDaysInMonth + i)).innerHTML += "假";
                            break;
                        }
                    }
                }
            }

            //控制第10行和第11行和第12行的显示
            document.getElementById("tr_1").style.display = "none";
            document.getElementById("tr_2").style.display = "none";
            document.getElementById("tr_3").style.display = "none";
            if(lastMonthDaysInMonth + daysInMonth + startDay > 63){
                document.getElementById("tr_1").style.display = "";
            }
            if(lastMonthDaysInMonth + daysInMonth + startDay > 70){
                document.getElementById("tr_2").style.display = "";
            }
            if(lastMonthDaysInMonth + daysInMonth + startDay > 77){
                document.getElementById("tr_3").style.display = "";
            }
        }

        // 得到两个月的所有天数
        function getAllDays(){
            var array = new Array();
            var daysInMonth = getDaysFromYearAndMonth(thisYear, thisMonth);
            for(var i=1;i<=daysInMonth;i++){
                var date = i;
                if(i<10){
                    date = "0" + i;
                }
                array.push(thisYearAndMonth + date);
            }

            var nextDaysInMonth = getDaysFromYearAndMonth(nextYear, nextMonth);
            for(var i=1;i<=nextDaysInMonth;i++){
                var date = i;
                if(i<10){
                    date = "0" + i;
                }
                array.push(nextYearAndMonth + date);
            }
            return array;
        }

        // 得到两个月的所有天是否工作日
        function getAllIsWorkDay(){
            var array = new Array();
            for(var i=0;i<allDays.length;i++){
                var date = allDays[i];
                var day = new Date(parseInt(date.substr(0,4)), parseInt(date.substr(4,2))-1, parseInt(date.substr(6,2))).getDay();
                if(day == 6 || day == 0){
                    var isWorkDay = false;
                    for(var j=0;j<thisMonthWorkDays.length;j++){
                        if(thisMonthWorkDays[j]["date"] == date && thisMonthWorkDays[j]["isWorkDay"]==true){
                            isWorkDay = true;
                            break;
                        }
                    }
                    for(var j=0;j<nextMonthWorkDays.length;j++){
                        if(nextMonthWorkDays[j]["date"] == date && nextMonthWorkDays[j]["isWorkDay"]==true){
                            isWorkDay = true;
                            break;
                        }
                    }
                    array.push(isWorkDay);
                    continue;
                } else {
                    var isWorkDay = true;
                    for(var j=0;j<thisMonthWorkDays.length;j++){
                        if(thisMonthWorkDays[j]["date"] == date && thisMonthWorkDays[j]["isWorkDay"]==false){
                            isWorkDay = false;
                            break;
                        }
                    }
                    for(var j=0;j<nextMonthWorkDays.length;j++){
                        if(nextMonthWorkDays[j]["date"] == date && nextMonthWorkDays[j]["isWorkDay"]==false){
                            isWorkDay = false;
                            break;
                        }
                    }
                    array.push(isWorkDay);
                    continue;
                }
            }
            return array;
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

        // 计算婚假
        function calculate(){
            var type = document.getElementById("type").value;
            var date1 = document.getElementById("date1").value;
            var date2 = document.getElementById("date2").value;
            var dateStr1 = date1.substr(0,4) + date1.substr(5,2) + date1.substr(8,2);
            var dateStr2 = date2.substr(0,4) + date2.substr(5,2) + date2.substr(8,2);
            if(date1 == ""){
                alert("请选择开始日期");
                return;
            }
            if(date2 == ""){
                alert("请选择结束日期");
                return;
            }
            if((date1.substr(0,4) != thisYear || date1.substr(5,2) != thisMonth) &&
                    (date1.substr(0,4) != nextYear || date1.substr(5,2) != nextMonth)){
                alert("开始日期请选择本月和次月的时间");
                return;
            }
            if((date2.substr(0,4) != thisYear || date2.substr(5,2) != thisMonth) &&
                    (date2.substr(0,4) != nextYear || date2.substr(5,2) != nextMonth)){
                alert("结束日期请选择本月和次月的时间");
                return;
            }
            if(date1 > date2){
                alert("结束日期不能早于开始日期");
                return;
            }
            //把北京清空
            for(var i=1;i<=84;i++){
                document.getElementById("td_" + i).className = "";
            }
            //选中的日期段背景加上笑脸
            var temp = parseInt(date1.replace("-", "").replace("-", ""));
            while(temp <= date2.replace("-", "").replace("-", "")){
                document.getElementById("h4_" + temp).parentNode.className = "vocation";
                temp+=1;
                if(temp%100 > thisDaysInMonth){
                    temp = parseInt(nextYearAndMonth + "01");
                }
            }

            //算实际休假=选择的时间段+前后的假日
            var firstDay = dateStr1;
            var lastDay = dateStr2;
            for(var i=0;i<allDays.length;i++){
                if(allDays[i] <= dateStr2){
                    continue;
                }
                if(allIsWorkDay[i] == true){
                    break;
                }
                lastDay = allDays[i];
            }
            for(var i=allDays.length-1;i>=0;i--){
                if(allDays[i] >= dateStr1){
                    continue;
                }
                if(allIsWorkDay[i] == true){
                    break;
                }
                firstDay = allDays[i];
            }
            document.getElementById("realDay1").innerHTML = firstDay.substr(0,4) + "/" + firstDay.substr(4,2) + "/" + firstDay.substr(6,2);
            document.getElementById("realDay2").innerHTML = lastDay.substr(0,4) + "/" + lastDay.substr(4,2) + "/" + lastDay.substr(6,2);

            //算还需要请假几天
            var selectWorkDays = 0;//选中的工作日数
            var selectHolidays = 0;//选中的节假日数
            for(var i=0;i<allDays.length;i++){
                if(allDays[i] < dateStr1){
                    continue;
                }
                if(allDays[i] > dateStr2){
                    break;
                }
                if(allIsWorkDay[i] == true){
                    selectWorkDays ++;
                } else {
                    selectHolidays ++
                }
            }
            var ownHolidays = document.getElementById("type").value;//根据婚嫁类型得到的可休天数
            var mostRemoveWorkDays = 0;//消掉工作日数
            var needMoreHoliday = 0;//还需要请假几天
            //循环选中的每天算从当日开始请假，消掉工作日数最多则最优，needMoreHoliday=selectWorkDays-mostRemoveWorkDays
            for(var i=0;i<allDays.length;i++){
                if(allDays[i] < dateStr1){
                    continue;
                }
                if(allDays[i] > dateStr2){
                    break;
                }
                var startDate = allDays[i];
                var removeWorkDays = 0;//消掉工作日数
                for(var k=0;k<ownHolidays;k++){
                    var temp = getDayAfterNum(startDate, k);
                    var tempIsWorkDay = getIsWorkDayAfterNum(startDate, k);
                    if(tempIsWorkDay){
                        removeWorkDays ++;
                    }
                    if(temp == dateStr2){
                        break;
                    }
                }
                if(removeWorkDays > mostRemoveWorkDays){
                    mostRemoveWorkDays = removeWorkDays;
                }
            }
            needMoreHoliday=selectWorkDays-mostRemoveWorkDays;
            document.getElementById("needMoreHolidayId").innerHTML = needMoreHoliday;
        }

        //返回该天的后num天
        function getDayAfterNum(fromDay, num){
            for(var i=0;i<allDays.length;i++){
                if(allDays[i] < fromDay){
                    continue;
                }
                if(num == 0){
                    return allDays[i];
                }
                num --;
            }
            alert("exception x!");
            return "";
        }

        //返回该天的后num天是否工作日
        function getIsWorkDayAfterNum(fromDay, num){
            for(var i=0;i<allDays.length;i++){
                if(allDays[i] < fromDay){
                    continue;
                }
                if(num == 0){
                    return allIsWorkDay[i];
                }
                num --;
            }
            alert("exception x!");
            return false;
        }

        //ajax获取当月工作日信息
        function getAjaxMarrySuggest(type){
            var year = "";
            var month = "";
            if("this" == type){
                year = thisYear;
                month = thisMonth;
            }

            if("next" == type){
                year = nextYear;
                month = nextMonth;
            }
            var SUCCESS_STR = "success";//成功编码
            $.ajax({
                type: "post",
                async: false,
                url: "<%=baseUrl%>tool/workday/getMarrySuggest.jsp",
                data: "chooseYear=" + year + "&chooseMonth=" + month,
                success: function(data, textStatus){
                    if((SUCCESS_STR == textStatus) && (null != data))
                    {
                        if(data != ""){
                            var datas = data.split(",");
                            var date1 = datas[0].substr(0,4) + "/" + datas[0].substr(4,2) + "/" + datas[0].substr(6,2);
                            var date2 = datas[1].substr(0,4) + "/" + datas[1].substr(4,2) + "/" + datas[1].substr(6,2);
                            var date3 = datas[2].substr(0,4) + "/" + datas[2].substr(4,2) + "/" + datas[2].substr(6,2);
                            var date4 = datas[3].substr(0,4) + "/" + datas[3].substr(4,2) + "/" + datas[3].substr(6,2);
                            if("this" == type){
                                document.getElementById("this_legal_date").innerHTML = date1 + "至" + date2;
                                document.getElementById("this_late_date").innerHTML = date3 + "至" + date4;
                                var array = getRealHoliday(datas[0], datas[1]);
                                document.getElementById("this_real_legal_date").innerHTML = array[0] + "至" + array[1];
                                array = getRealHoliday(datas[2], datas[3]);
                                document.getElementById("this_real_late_date").innerHTML = array[0] + "至" + array[1];
                                getAjaxMarrySuggest("next");
                            }
                            if("next" == type){
                                document.getElementById("next_legal_date").innerHTML = date1 + "至" + date2;
                                document.getElementById("next_late_date").innerHTML = date3 + "至" + date4;
                                var array = getRealHoliday(datas[0], datas[1]);
                                document.getElementById("next_real_legal_date").innerHTML = array[0] + "至" + array[1];
                                array = getRealHoliday(datas[2], datas[3]);
                                document.getElementById("next_real_late_date").innerHTML = array[0] + "至" + array[1];
                            }
                        } else {
                            if("this" == type){
                                document.getElementById("this_legal").style.display = "none";
                                document.getElementById("this_late").style.display = "none";
                                getAjaxMarrySuggest("next");
                            }
                            if("next" == type){
                                document.getElementById("next_legal").style.display = "none";
                                document.getElementById("next_late").style.display = "none";
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

        //算实际休假=选择的时间段+前后的假日
        function getRealHoliday(dateStr1, dateStr2){
            var array = new Array();
            var firstDay = dateStr1;
            var lastDay = dateStr2;
            for(var i=0;i<allDays.length;i++){
                if(allDays[i] <= dateStr2){
                    continue;
                }
                if(allIsWorkDay[i] == true){
                    break;
                }
                lastDay = allDays[i];
            }
            for(var i=allDays.length-1;i>=0;i--){
                if(allDays[i] >= dateStr1){
                    continue;
                }
                if(allIsWorkDay[i] == true){
                    break;
                }
                firstDay = allDays[i];
            }
            array.push(firstDay.substr(0,4) + "/" + firstDay.substr(4,2) + "/" + firstDay.substr(6,2));
            array.push(lastDay.substr(0,4) + "/" + lastDay.substr(4,2) + "/" + lastDay.substr(6,2));
            return array;
        }
    </script>
</head>
<body>
<div class="header">
    <p class="topright"><a href="#">注册</a> | <a href="#">登录</a></p>
    <div class="title"> <span class="logo"><img src="../images/logo.png" alt="My Wonderland" /></span> <span class="pagetitle">记录我的历程，<a href="#">创建我的主页</a></span> </div>
</div>
<ul id="MenuBar1" class="MenuBarHorizontal reference">
    <li><a href="#" target="_blank">流程首页</a></li>
    <li><a href="#">找婚庆</a></li>
    <li><a href="#" target="_blank">选酒店</a></li>
    <li><a href="#">购婚品</a></li>
    <li><a href="#" class="activeMenu">工具箱</a></li>
</ul>
<div class="wrap">
    <div class="toolbox">
        <ul class="toolsp toollist">
            <li><a href="<%=baseUrl%>tool/tool_map.jsp"><img src="<%=baseUrl%>images/tool_map.png" /></a><br /><a href="<%=baseUrl%>tool/tool_map.jsp">登记查询</a></li>
            <li><a href="<%=baseUrl%>tool/tool_material.jsp"><img src="<%=baseUrl%>images/tool_list.png" /></a><br /><a href="<%=baseUrl%>tool/tool_material.jsp">材料清单</a></li>
            <li><a href="<%=baseUrl%>tool/tool_calendar.jsp"><img src="<%=baseUrl%>images/tool_day.png" /></a><br /><a href="<%=baseUrl%>tool/tool_calendar.jsp">黄道吉日</a></li>
            <li><a href="#"><img src="<%=baseUrl%>images/tool_absense.png" /></a><br /><a href="#">请假单</a></li>
            <li><a href="<%=baseUrl%>tool/tool_absense.jsp"><img src="<%=baseUrl%>images/tool_period.png" /></a><br /><a href="<%=baseUrl%>tool/tool_absense.jsp">婚假建议</a></li>
            <li><a href="#"><img src="<%=baseUrl%>images/tool_pe.png" /></a><br /><a href="#">婚检样张</a></li>
        </ul>
        <input class="hideinput" type="checkbox" id="alltools" />
        <ul class="toolall toollist">
            <li><a href="<%=baseUrl%>tool/tool_budget.jsp"><img src="<%=baseUrl%>images/tool_budget.png" /></a><br /><a href="<%=baseUrl%>tool/tool_budget.jsp">婚礼预算</a></li>
            <li><a href="<%=baseUrl%>tool/tool_seat.jsp"><img src="<%=baseUrl%>images/tool_seat.png" /></a><br /><a href="<%=baseUrl%>tool/tool_seat.jsp">座位安排</a></li>
            <li></li>
            <li></li>
            <li></li>
        </ul>
        <label class="morebar" for="alltools"></label>
    </div>
    <div class="toolbox">
        <h1>婚假建议</h1>
        <h3 class="msgtip msginfo">婚假中遇到法定节假日会被抵消，看看您的婚假最多能放几天！</h3>
        <div class="selectDate fl">
            <ul class="absenseInput">
                <li><label>婚假类型</label><br />
                    <select id="type" name="">
                        <option value="10">晚婚假(10天)</option>
                        <option value="3">法定婚假(3天)</option>
                    </select>
                </li>
                <li><label>婚假时间</label><br /><input id="date1" type="date" /> ~ <input id="date2" type="date" /></li>
                <li><button onclick="calculate()" class="">计算婚假</button></li>
            </ul>
            <div class="absenseResult">根据您的选择的婚假类型和起止日期，您额外还需要请假<div id="needMoreHolidayId" class="value">--</div>天。<span class="absenseAll">（实际休假从<div id="realDay1" class="value">--</div>至<div id="realDay2" class="value">--</div>）</span></div>
            <p id="this_legal"><b>本月推荐法定婚假：</b><br /><span id="this_legal_date"></span>（实际休假从<span id="this_real_legal_date"></span>）</p>
            <p id="this_late"><b>本月推荐晚婚假：</b><br /><span id="this_late_date"></span>（实际休假从<span id="this_real_late_date"></span>）</p>
            <p id="next_legal"><b>次月推荐法定婚假：</b><br /><span id="next_legal_date"></span>（实际休假从<span id="next_real_legal_date"></span>）</p>
            <p id="next_late"><b>次月推荐晚婚假：</b><br /><span id="next_late_date"></span>（实际休假从<span id="next_real_late_date"></span>）</p>
        </div>
        <div class="calendarbox fl">
            <div class="calendarHead">
                <a name="pos"></a>
                <a href="<%=baseUrl%>tool/tool_absense.jsp?month=<%=lastMonth%>#pos" class="icoPrev icofont inb" title="上个月">&lt;</a><span class="time"><span id="show_this_month_id"></span>~<span id="show_next_month_id"></span></span><a href="<%=baseUrl%>tool/tool_absense.jsp?month=<%=nextMonth%>#pos" class="icoNext icofont inb" title="下个月">&gt;</a>
            </div>
            <table border="0" cellspacing="1" cellpadding="0" class="calendarMonth fl">
                <tr>
                    <th>周日</th>
                    <th>周一</th>
                    <th>周二</th>
                    <th>周三</th>
                    <th>周四</th>
                    <th>周五</th>
                    <th>周六</th>
                </tr>
                <tr>
                    <td id="td_1"></td>
                    <td id="td_2"></td>
                    <td id="td_3"><h4>1</h4>&nbsp;假</td>
                    <td id="td_4"><h4>2</h4>&nbsp;假</td>
                    <td id="td_5"><h4>3</h4></td>
                    <td id="td_6"><h4>4</h4></td>
                    <td id="td_7"><h4>5</h4></td>
                </tr>
                <tr>
                    <td id="td_8"><h4>6</h4>&nbsp;</td>
                    <td id="td_9"><h4>7</h4>&nbsp;</td>
                    <td id="td_10"><h4>8</h4></td>
                    <td id="td_11"><h4>9</h4></td>
                    <td id="td_12"><h4>10</h4></td>
                    <td id="td_13"><h4>11</h4></td>
                    <td id="td_14"><h4>12</h4></td>
                </tr>
                <tr>
                    <td id="td_15"><h4>13</h4></td>
                    <td id="td_16"><h4>14</h4></td>
                    <td id="td_17"><h4>15</h4></td>
                    <td id="td_18"><h4>16</h4></td>
                    <td id="td_19"><h4>17</h4></td>
                    <td id="td_20"><h4>18</h4></td>
                    <td id="td_21"><h4>19</h4></td>
                </tr>
                <tr>
                    <td id="td_22"><h4>20</h4></td>
                    <td id="td_23"><h4>21</h4></td>
                    <td id="td_24"><h4>22</h4></td>
                    <td id="td_25"><h4>23</h4></td>
                    <td id="td_26"><h4>24</h4></td>
                    <td id="td_27"><h4>25</h4></td>
                    <td id="td_28"><h4>26</h4></td>
                </tr>
                <tr>
                    <td id="td_29"><h4>27</h4></td>
                    <td id="td_30"><h4>28</h4></td>
                    <td id="td_31"><h4>29</h4></td>
                    <td id="td_32"><h4>30</h4></td>
                    <td id="td_33"><h4>31</h4></td>
                    <td id="td_34"></td>
                    <td id="td_35"></td>
                </tr>
                <tr>
                    <td id="td_36"><h4>27</h4></td>
                    <td id="td_37"><h4>28</h4></td>
                    <td id="td_38"><h4>29</h4></td>
                    <td id="td_39"><h4>30</h4></td>
                    <td id="td_40"><h4>31</h4></td>
                    <td id="td_41"></td>
                    <td id="td_42"></td>
                </tr>
                <tr>
                    <td id="td_43"><h4>27</h4></td>
                    <td id="td_44"><h4>28</h4></td>
                    <td id="td_45"><h4>29</h4></td>
                    <td id="td_46"><h4>30</h4></td>
                    <td id="td_47"><h4>31</h4></td>
                    <td id="td_48"></td>
                    <td id="td_49"></td>
                </tr>
                <tr>
                    <td id="td_50"><h4>27</h4></td>
                    <td id="td_51"><h4>28</h4></td>
                    <td id="td_52"><h4>29</h4></td>
                    <td id="td_53"><h4>30</h4></td>
                    <td id="td_54"><h4>31</h4></td>
                    <td id="td_55"></td>
                    <td id="td_56"></td>
                </tr>
                <tr>
                    <td id="td_57"><h4>27</h4></td>
                    <td id="td_58"><h4>28</h4></td>
                    <td id="td_59"><h4>29</h4></td>
                    <td id="td_60"><h4>30</h4></td>
                    <td id="td_61"><h4>31</h4></td>
                    <td id="td_62"></td>
                    <td id="td_63"></td>
                </tr>
                <tr id="tr_1">
                    <td id="td_64"><h4>27</h4></td>
                    <td id="td_65"><h4>28</h4></td>
                    <td id="td_66"><h4>29</h4></td>
                    <td id="td_67"><h4>30</h4></td>
                    <td id="td_68"><h4>31</h4></td>
                    <td id="td_69"></td>
                    <td id="td_70"></td>
                </tr>
                <tr id="tr_2">
                    <td id="td_71"><h4>27</h4></td>
                    <td id="td_72"><h4>28</h4></td>
                    <td id="td_73"><h4>29</h4></td>
                    <td id="td_74"><h4>30</h4></td>
                    <td id="td_75"><h4>31</h4></td>
                    <td id="td_76"></td>
                    <td id="td_77"></td>
                </tr>
                <tr id="tr_3">
                    <td id="td_78"><h4>27</h4></td>
                    <td id="td_79"><h4>28</h4></td>
                    <td id="td_80"><h4>29</h4></td>
                    <td id="td_81"><h4>30</h4></td>
                    <td id="td_82"><h4>31</h4></td>
                    <td id="td_83"></td>
                    <td id="td_84"></td>
                </tr>
            </table>
        </div>


        <div class="cf"></div>
    </div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
    <a href="#">关于婚礼岛</a></p>
<div style="display:none">

</div>
</body>
</html>