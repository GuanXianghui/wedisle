<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工具 - 婚礼岛</title>
<link rel="shortcut icon" type="image/x-icon" href="../../favicon.ico" />
<link href="../../style/homePlus.css" rel="stylesheet" type="text/css" />
<link href="../../style/tools.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../../Scripts/jquery-min.js"></script>
<script type="text/javascript" src="../../Scripts/comm.js"></script>
<script type="text/javascript" src="../../Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="../../Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="../../Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<script type="text/javascript">
    //选择年月
    var chooseYear = <%=new SimpleDateFormat("yyyy").format(new java.util.Date())%>;
    var chooseMonth = <%=new SimpleDateFormat("MM").format(new java.util.Date())%>;
    //本月工作日数组
    var workDays = null;

    //工作日数组
    var workDayArray = new Array();
    //节假日数组
    var holidayArray = new Array();

    //初始化
    $(document).ready(function() {
        $(".dialogBtn").fancybox();
        initMonth(chooseYear, chooseMonth);
    });

    //初始化月份
    function initMonth(year, month){
        //ajax获取当月工作日信息
        getAjaxWorkDay(new Date(year, month-1, 1).format("yyyyMMdd").substr(0, 6));
        var daysInMonth = getDaysFromYearAndMonth(year, month);
        var startDay = new Date(year, month-1, 1).getDay();
        //控制第五行和第六行的显示
        document.getElementById("tr_1").style.display = "none";
        document.getElementById("tr_2").style.display = "none";
        if(daysInMonth + startDay > 28){
            document.getElementById("tr_1").style.display = "";
        }
        if(daysInMonth + startDay > 32){
            document.getElementById("tr_2").style.display = "";
        }

        //清空每个格子
        for(var i=1;i<=42;i++){
            document.getElementById("td_" + i).innerHTML = "";
        }
        for(var i=1;i<=daysInMonth;i++){
            var date = new Date(year, month-1, i).format("yyyyMMdd");

            var html = "<h4 style=\"cursor: pointer;\"";
            html += " onclick=\"chooseToday(this, " + date + ")\"";
            html += " id=\"td_" + (startDay + i) + "_h4\""

            var className = "";
            //判断周末
            var day = new Date(year, month-1, i).getDay();
            if(day == 6 || day == 0){
                className = " class=\"goodDate\"";
            }
            //判断非工作日
            for(var j=0;j<workDays.length;j++){
                if(workDays[j]["date"] == date){
                    if(workDays[j]["isWorkDay"] ==  false){
                        className = " class=\"goodDate\"";
                    } else {
                        className = "";
                    }
                }
            }
            html += className + ">" + i + "</h4>";
            document.getElementById("td_" + (startDay + i)).innerHTML = html;
        }
        //展示年和月
        showYearAndMonth();
    }

    //展示年和月
    function showYearAndMonth(){
        document.getElementById("show_year_id").innerHTML = chooseYear;
        document.getElementById("show_month_id").innerHTML = chooseMonth;
        document.getElementById("show_year_id2").innerHTML = chooseYear;
        document.getElementById("show_month_id2").innerHTML = chooseMonth;
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

    //选择上个月
    function lastMonth(){
        chooseMonth = chooseMonth - 1;
        if(chooseMonth == 0){
            chooseYear = chooseYear - 1;
            chooseMonth = 12;
        }
        initMonth(chooseYear, chooseMonth);
    }

    //选择下个月
    function nextMonth(){
        chooseMonth = chooseMonth + 1;
        if(chooseMonth == 13){
            chooseYear = chooseYear + 1;
            chooseMonth = 1;
        }
        initMonth(chooseYear, chooseMonth);
    }

    //保存工作日配置
    function saveWorkDays(){
        var SUCCESS_STR = "success";//成功编码
        $.ajax({
            type: "post",
            async: false,
            url: "<%=baseUrl%>tool/workday/saveWorkDays.jsp",
            data: "workDays=" + workDayArray + "&holidays=" + holidayArray ,
            success: function(data, textStatus){
                if((SUCCESS_STR == textStatus) && (null != data) && (data.indexOf("OK")==0))
                {
                    alert("保存工作日成功！");
                    document.location = "<%=baseUrl%>tool/workday/workday_config.jsp";
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

    //保存婚嫁建议
    function saveMarrySuggest(){
        //chooseYear;chooseMonth
        var date1 = document.getElementById("date1").value;
        var date2 = document.getElementById("date2").value;
        var date3 = document.getElementById("date3").value;
        var date4 = document.getElementById("date4").value;
        if(date1 == "" || date2 == "" || date3 == "" || date4 == ""){
            alert("请选择推荐婚嫁日期！");
            return;
        }
        date1 = date1.replace("-", "").replace("-", "");
        date2 = date2.replace("-", "").replace("-", "");
        date3 = date3.replace("-", "").replace("-", "");
        date4 = date4.replace("-", "").replace("-", "");

        var SUCCESS_STR = "success";//成功编码
        $.ajax({
            type: "post",
            async: false,
            url: "<%=baseUrl%>tool/workday/saveMarrySuggest.jsp",
            data: "chooseYear=" + chooseYear + "&chooseMonth=" + chooseMonth + "&date1=" + date1 + "&date2=" + date2 + "&date3=" + date3 + "&date4=" + date4 ,
            success: function(data, textStatus){
                if((SUCCESS_STR == textStatus) && (null != data) && (data.indexOf("OK")==0))
                {
                    alert("保存婚嫁建议成功！");
                    document.location = "<%=baseUrl%>tool/workday/workday_config.jsp";
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

    //ajax获取当月工作日信息
    function getAjaxWorkDay(yearAndMonth){
        var SUCCESS_STR = "success";//成功编码
        $.ajax({
            type: "post",
            async: false,
            url: "<%=baseUrl%>tool/workday/getWorkDays.jsp",
            data: "yearAndMonth=" + yearAndMonth,
            success: function(data, textStatus){
                if((SUCCESS_STR == textStatus) && (null != data) && (data.indexOf("[")==0))
                {
                    workDays = eval(data);
                    getAjaxMarrySuggest(yearAndMonth);
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

    //ajax获取当月工作日信息
    function getAjaxMarrySuggest(yearAndMonth){
        var SUCCESS_STR = "success";//成功编码
        $.ajax({
            type: "post",
            async: false,
            url: "<%=baseUrl%>tool/workday/getMarrySuggest.jsp",
            data: "chooseYear=" + yearAndMonth.substr(0,4) + "&chooseMonth=" + yearAndMonth.substr(4,2),
            success: function(data, textStatus){
                if((SUCCESS_STR == textStatus) && (null != data))
                {
                    if(data != ""){
                        var datas = data.split(",");
                        document.getElementById("date1").value = datas[0].substr(0,4) + "-" + datas[0].substr(4,2) + "-" + datas[0].substr(6,2);
                        document.getElementById("date2").value = datas[1].substr(0,4) + "-" + datas[1].substr(4,2) + "-" + datas[1].substr(6,2);
                        document.getElementById("date3").value = datas[2].substr(0,4) + "-" + datas[2].substr(4,2) + "-" + datas[2].substr(6,2);
                        document.getElementById("date4").value = datas[3].substr(0,4) + "-" + datas[3].substr(4,2) + "-" + datas[3].substr(6,2);
                    } else {
                        document.getElementById("date1").value = "";
                        document.getElementById("date2").value = "";
                        document.getElementById("date3").value = "";
                        document.getElementById("date4").value = "";
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

    //选择当日
    function chooseToday(t, date){
        if(t.className == "goodDate"){
            t.className = "";
            workDayArray.push(date);
            var deletePos = -1;
            for(var i=0;i<holidayArray.length;i++){
                if(holidayArray[i] == date){
                    deletePos = i;
                    break;
                }
            }
            if(deletePos > -1){
                holidayArray.splice(deletePos, 1);
            }
        } else {
            t.className = "goodDate";
            holidayArray.push(date);
            var deletePos = -1;
            for(var i=0;i<workDayArray.length;i++){
                if(workDayArray[i] == date){
                    deletePos = i;
                    break;
                }
            }
            if(deletePos > -1){
                workDayArray.splice(deletePos, 1);
            }
        }
    }
</script>
</head>
<body>
<div class="header">
    <div class="title"> <span class="logo"><img src="../../images/logo.png" alt="My Wonderland" /></span> </div>
</div>
<div class="wrap">
    <div class="toolbox" align="center">
        <h1>工作日配置</h1>
        <div class="calendarbox fl" style="width: 100%;">
            <div class="calendarHead">
                <a href="javascript:lastMonth()" class="icoPrev icofont inb" title="上个月">&lt;</a><span class="time"><span id="show_year_id"></span>-<span id="show_month_id"></span></span><a href="javascript:nextMonth()" class="icoNext icofont inb" title="下个月">&gt;</a>
            </div>
            <div>
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
                        <td id="td_3"><h4 class="pastDate">1</h4></td>
                        <td id="td_4"><h4 class="pastDate">2</h4></td>
                        <td id="td_5"><h4 class="pastDate">3</h4></td>
                        <td id="td_6"><h4 class="pastDate">4</h4></td>
                        <td id="td_7"><h4 class="pastDate">5</h4></td>
                    </tr>
                    <tr>
                        <td id="td_8"><h4 class="pastDate">6</h4></td>
                        <td id="td_9"><h4>7</h4></td>
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
                    <tr id="tr_1">
                        <td id="td_29"><h4>27</h4></td>
                        <td id="td_30"><h4 class="goodDate">28</h4></td>
                        <td id="td_31"><h4>29</h4></td>
                        <td id="td_32"><h4>30</h4></td>
                        <td id="td_33"><h4>31</h4></td>
                        <td id="td_34"></td>
                        <td id="td_35"></td>
                    </tr>
                    <tr id="tr_2">
                        <td id="td_36"><h4>27</h4></td>
                        <td id="td_37"><h4 class="goodDate">28</h4></td>
                        <td id="td_38"><h4>29</h4></td>
                        <td id="td_39"><h4>30</h4></td>
                        <td id="td_40"><h4>31</h4></td>
                        <td id="td_41"></td>
                        <td id="td_42"></td>
                    </tr>
                </table>
            </div>
        </div>
        <div>
            <h3>注意：底色红色为节假日，白色为工作日，不配置则默认周末为节假日，国庆节，劳动节，春节等都需要手工配置，哇哈哈！</h3>
        </div>
        <button onclick="saveWorkDays()">保存工作日</button>
        <br>
        <br>
        <div>
            <h1>本月(<span id="show_year_id2"></span>-<span id="show_month_id2"></span>)婚嫁推荐配置</h1>
            <h3>法定婚嫁：<input id="date1" type="date" /> ~ <input id="date2" type="date" /></h3>
            <h3>&nbsp;&nbsp;&nbsp;&nbsp;晚婚嫁：<input id="date3" type="date" /> ~ <input id="date4" type="date" /></h3>
        </div>
        <div>
            <h3>注意：起止时间请选在本月内，我没有校验哦，哇哈哈！</h3>
        </div>
        <button onclick="saveMarrySuggest()">保存婚嫁推荐</button>
        <div class="cf"></div>
    </div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
    <a href="#">关于婚礼岛</a></p>
</body>
</html>