<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工具 - 婚礼岛</title>
<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
<link href="../style/homePlus.css" rel="stylesheet" type="text/css" />
<link href="../style/tools.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../Scripts/jquery-min.js"></script>
<script type="text/javascript" src="../Scripts/comm.js"></script>
<script type="text/javascript" src="../Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="../Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="../Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<script type="text/javascript">
    //选择年月
    var chooseYear = <%=new SimpleDateFormat("yyyy").format(new java.util.Date())%>;
    var chooseMonth = <%=new SimpleDateFormat("MM").format(new java.util.Date())%>;
    var chooseDate = "<%=new SimpleDateFormat("yyyyMMdd").format(new java.util.Date())%>";
    //选择点
    var nodeId = null;
    //吉日json数组
    var jiRi = null;
    //初始化
    $(document).ready(function() {
        $(".dialogBtn").fancybox();
        initMonth(chooseYear, chooseMonth);
        // 初始化页面调用
        showInitJiRi();
    });

    //初始化月份
    function initMonth(year, month){
        //ajax获取当月吉日信息
        getAjaxJiRi(new Date(year, month-1, 1).format("yyyyMMdd").substr(0, 6));

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

            //吉日数据范围2011年~2022年
            if(year < 2011 || year > 2022){
                html += " class=\"pastDate\"";
            }

            //选中的日期底色为红色
            if (date == chooseDate){
                html += " class=\"goodDate\"";
                nodeId = "td_" + (startDay + i) + "_h4";
            } else {
                //所有宜嫁娶底色为红色
                for(var j=0;j<jiRi.length;j++){
                    if(jiRi[j]["date"] == date && jiRi[j]["yi"].indexOf("嫁娶")>-1){
                        html += " class=\"goodDate\"";
                    }
                }
            }
            html += ">" + i + "</h4>";
            document.getElementById("td_" + (startDay + i)).innerHTML = html;
        }
        //展示年和月
        showYearAndMonth();
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

    /* 计算星期几 0~6 星期天~星期六
    function getDay(str){
        var year = parseInt(str.substr(0, 4));
        var month = parseInt(str.substr(5, 6));
        var date = parseInt(str.substr(7, 8));
        var object = new Date(year, month, date);
        return object.getDay();
    }*/

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

    //展示年和月
    function showYearAndMonth(){
        document.getElementById("show_year_id").innerHTML = chooseYear;
        document.getElementById("show_month_id").innerHTML = chooseMonth;
    }

    //ajax获取当月吉日信息
    function getAjaxJiRi(yearAndMonth){
        var SUCCESS_STR = "success";//成功编码
        $.ajax({
            type: "post",
            async: false,
            url: "<%=baseUrl%>tool/goodday/getGoodDays.jsp",
            data: "yearAndMonth=" + yearAndMonth,
            success: function(data, textStatus){
                if((SUCCESS_STR == textStatus) && (null != data) && (data.indexOf("[")==0))
                {
                    jiRi = eval(data);
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
        if(null == jiRi || jiRi.length <= 0){
            alert("暂无当日的吉日数据！");
            return;
        }

        //判上一个选中的点如果宜嫁娶则className不变
        var isYiJiaQu = false;
        for(var j=0;j<jiRi.length;j++){
            if(jiRi[j]["date"] == chooseDate && jiRi[j]["yi"].indexOf("嫁娶")>-1){
                isYiJiaQu = true;
            }
        }
        if(isYiJiaQu ==  false){
            document.getElementById(nodeId).className = "";
        }

        t.className = "goodDate";
        chooseDate = date;
        nodeId = t.id;
        for(var i=0;i<jiRi.length;i++){
            if(jiRi[i]["date"] == date){
                showJiRi(jiRi[i]);
                return;
            }
        }
        alert("暂无当日的吉日数据！");
        return;
    }

    // 初始化页面调用
    function showInitJiRi(){
        for(var i=0;i<jiRi.length;i++){
            if(jiRi[i]["date"] == chooseDate){
                showJiRi(jiRi[i]);
                return;
            }
        }
    }

    // 展示某日吉日数据
    function showJiRi(array){
        var date = array["date"];
        document.getElementById("show_date_id").innerHTML = parseInt(date.substr(6,8));
        document.getElementById("show_date_detail_id").innerHTML = date.substr(0,4) + "年" + date.substr(4,2) + "月" + date.substr(6,2) + "日";
        document.getElementById("show_gongli_id").innerHTML = array["gongli"];
        document.getElementById("show_nongli_id").innerHTML = array["nongli"];
        document.getElementById("show_ji_id").innerHTML = array["ji"];
        document.getElementById("show_yi_id").innerHTML = array["yi"];
        document.getElementById("show_chong_id").innerHTML = array["chong"];
        document.getElementById("show_cisui_id").innerHTML = array["cisui"];
        document.getElementById("show_pengzubaiji_id").innerHTML = array["pengzubaiji"];
        document.getElementById("show_wuxing_id").innerHTML = array["wuxing"];
        document.getElementById("yijiaqu").style.display = "none";
        if(array["yi"].indexOf("嫁娶") > -1){
            document.getElementById("yijiaqu").style.display = "";
        }
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
<h1>黄道吉日</h1>
<div class="selectDate fl"><p>请在右侧选择日期。</p><p id="show_date_id" class="bigDate fl">28</p><p class="dateDetail"><span id="show_date_detail_id">2013年10月28日&nbsp;星期一&nbsp;</span> <span id="yijiaqu" class="icoGoodday inb">宜嫁娶</span><br /><small id="show_gongli_id">公元2013年10月28日</small>  <small id="show_nongli_id">农历2013年九月(小)廿四</small></p>
        <p><b>宜：</b><span id="show_yi_id">嫁娶 祭祀 祈福 求嗣 开光 出行 拆卸 修造 动土 上梁 安床 纳畜 入殓 破土</span></p>
<p><b>忌：</b><span id="show_ji_id">入宅 移徙 掘井 理发 伐木 交易 开市 作灶</span></p>
<p><b>冲：</b><span id="show_chong_id">冲鸡(辛酉)煞西</span></p>
<p><b>五行：</b><span id="show_wuxing_id">炉中火 执执位</span></p>
<p><b>彭祖百忌：</b><span id="show_pengzubaiji_id">丁不剃头头必生疮 卯不穿井水泉不香</span></p>
<p><b>次岁：</b><span id="show_cisui_id">癸巳 年 壬戌 月 丁卯 日 属蛇</span></p>
        </div>
        <div class="calendarbox fl"><div class="calendarHead">
          <a href="javascript:lastMonth()" class="icoPrev icofont inb" title="上个月">&lt;</a><span class="time"><span id="show_year_id"></span>-<span id="show_month_id"></span></span><a href="javascript:nextMonth()" class="icoNext icofont inb" title="下个月">&gt;</a>
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
        
    
<div class="cf"></div>
  </div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
  <a href="#">关于婚礼岛</a></p>
<div style="display:none">
  
</div>
</body>
</html>
