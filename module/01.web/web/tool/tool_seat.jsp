<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gxx.record.interfaces.BaseInterface" %>
<%@ page import="com.gxx.record.utils.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleUser" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleSeatInfo" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleSeatInfoDao" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleRelaFriendDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleRelaFriend" %>
<%@ page import="com.gxx.record.utils.WedisleUtils" %>
<%
    // wed isle首页链接头
    String baseUrl = PropertyUtil.getInstance().getProperty(BaseInterface.WED_ISLE_BASE_URL);
    // 获取登录的用户对象
    WedisleUser wedisleUser = (WedisleUser)session.getAttribute(BaseInterface.WEDISLE_USER);
    // 判是否已登录
    boolean isLogin = null != wedisleUser;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>工具 - 婚礼岛</title>
    <link rel="shortcut icon" type="image/x-icon" href="<%=baseUrl%>favicon.ico" />
    <link href="<%=baseUrl%>style/homePlus.css" rel="stylesheet" type="text/css" />
    <link href="<%=baseUrl%>style/tools.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=baseUrl%>Scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>Scripts/comm.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
    <%
        WedisleSeatInfo seatInfo = null;
        List relaFriendsWithSeat = null;
        List relaFriendsWithoutSeat = null;
        //判如果已登录 获得席位信息
        if(isLogin)
        {
            seatInfo = WedisleSeatInfoDao.getSeatInfoByUserId(wedisleUser.getId());
            relaFriendsWithSeat = WedisleRelaFriendDao.queryRelaFriendsByUserIdWithSeat(wedisleUser.getId());
            relaFriendsWithoutSeat = WedisleRelaFriendDao.queryRelaFriendsByUserIdWithoutSeat(wedisleUser.getId());
        }
    %>
    <script type="text/javascript">
        <%
            // 要求必须先登录
            if(!isLogin)
            {
        %>
        alert('<%="请先登录！"%>');
        location.href = "<%=baseUrl%>login.jsp";
        <%
            }
        %>
        <%
            // 打印错误信息
            String errorMsg = (String) request.getAttribute("errorMsg");
            if(StringUtils.isNotBlank(errorMsg))
            {
        %>
        alert('<%=errorMsg%>');
        <%
            }
        %>

        <%
            // 打印提示信息
            String message = (String) request.getAttribute("message");
            if(StringUtils.isNotBlank(message))
            {
        %>
        alert('<%=message%>');
        <%
            }
        %>
        // 初始化
        $(document).ready(function() {
            $(".dialogBtn").fancybox();
            changeTable();
        });
        //打印
        function preview()
        {
            bdhtml=window.document.body.innerHTML;
            sprnstr="<!--startprint-->";
            eprnstr="<!--endprint-->";
            prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
            prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
            //window.document.body.innerHTML=prnhtml;
            window.print();
        }
        <%
            if(isLogin)
            {
        %>
        var nowTable = 0;
        var nowTableCount = <%=seatInfo.getTableCount()%>;
        var nowNumEveryTable = <%=seatInfo.getNumEveryTable()%>;
        var relaFriendsWithSeatNames = eval("(<%=WedisleUtils.formatRelaFriendJsonArrayStr(relaFriendsWithSeat, "name")%>)");
        var relaFriendsWithSeatNums = eval("(<%=WedisleUtils.formatRelaFriendJsonArrayStr(relaFriendsWithSeat, "num")%>)");
        var relaFriendsWithSeatSeats = eval("(<%=WedisleUtils.formatRelaFriendJsonArrayStr(relaFriendsWithSeat, "seat")%>)");
        var relaFriendsWithoutSeatNames = eval("(<%=WedisleUtils.formatRelaFriendJsonArrayStr(relaFriendsWithoutSeat, "name")%>)");
        var relaFriendsWithoutSeatNums = eval("(<%=WedisleUtils.formatRelaFriendJsonArrayStr(relaFriendsWithoutSeat, "num")%>)");
        var relaFriendsWithoutSeatSeats = eval("(<%=WedisleUtils.formatRelaFriendJsonArrayStr(relaFriendsWithoutSeat, "seat")%>)");
        <%
            }
        %>
        // 选择入座
        function putToSeat()
        {
            var temp1 = new Array().concat(relaFriendsWithoutSeatNames);
            var temp2 = new Array().concat(relaFriendsWithoutSeatNums);
            var temp3 = new Array().concat(relaFriendsWithoutSeatSeats);
            var temp4 = new Array().concat(relaFriendsWithSeatNames);
            var temp5 = new Array().concat(relaFriendsWithSeatNums);
            var temp6 = new Array().concat(relaFriendsWithSeatSeats);
            for(var i=relaFriendsWithSeatNames.length-1;i>=0;i--)
            {
                var checked = $("#relaFriendsWithSeat" + (i+1)).is(':checked');
                if(!checked)
                {
                    if(nowTable == relaFriendsWithSeatSeats[i])
                    {
                        relaFriendsWithoutSeatNames.push(relaFriendsWithSeatNames[i]);
                        relaFriendsWithoutSeatNums.push(relaFriendsWithSeatNums[i]);
                        relaFriendsWithoutSeatSeats.push(0);
                        relaFriendsWithSeatNames.splice(i, 1);
                        relaFriendsWithSeatNums.splice(i, 1);
                        relaFriendsWithSeatSeats.splice(i, 1);
                    }
                }
            }
            for(var i=relaFriendsWithoutSeatNames.length-1;i>=0;i--)
            {
                var checked = $("#relaFriendsWithoutSeat" + (i+1)).is(':checked');//所有jquery的版本都支持，别忘记加冒号
                if(checked)//判是否被选中
                {
                    relaFriendsWithSeatNames.push(relaFriendsWithoutSeatNames[i]);
                    relaFriendsWithSeatNums.push(relaFriendsWithoutSeatNums[i]);
                    relaFriendsWithSeatSeats.push(nowTable);
                    relaFriendsWithoutSeatNames.splice(i, 1);
                    relaFriendsWithoutSeatNums.splice(i, 1);
                    relaFriendsWithoutSeatSeats.splice(i, 1);
                }
            }
            var tempCount = 0;
            for(var i=0;i<relaFriendsWithSeatSeats.length;i++)
            {
                if(nowTable == relaFriendsWithSeatSeats[i])
                {
                    tempCount += relaFriendsWithSeatNums[i];
                }
            }
            if(tempCount > nowNumEveryTable)
            {
                alert("您选择的亲友数[" + tempCount + "]大于每桌人数上限[" + nowNumEveryTable + "]");
                relaFriendsWithoutSeatNames = temp1;
                relaFriendsWithoutSeatNums = temp2;
                relaFriendsWithoutSeatSeats = temp3;
                relaFriendsWithSeatNames = temp4;
                relaFriendsWithSeatNums = temp5;
                relaFriendsWithSeatSeats = temp6;
            } else
            {
                $("#fancybox-close").click();
                changeTable();
            }
        }
        // 修改桌数或者没桌人数 检验是否ok
        function checkChangeTable()
        {
            var tableCount = parseInt($("#table_count_id").val());
            var numEveryTable = parseInt($("#num_every_table_id").val());
            if(tableCount > nowTableCount || numEveryTable > nowNumEveryTable){
                return true;
            }
            if(tableCount < nowTableCount){
                if(tableCount < 1)
                {
                    alert("目前配置最少1桌");
                    return false;
                }
                for(var i=nowTableCount;i>=tableCount+1;i--){
                    for(var j=0;j<relaFriendsWithSeatSeats.length;j++)
                    {
                        if((i) == relaFriendsWithSeatSeats[j])
                        {
                            alert("第" + i + "桌已经有人坐了，不能减桌");
                            return false;
                        }
                    }
                }
                return true;
            }
            if(numEveryTable < nowNumEveryTable){
                if(numEveryTable < 1)
                {
                    alert("目前配置最少1人1桌");
                    return false;
                }
                // 判是否有哪桌已经坐满，不能再减座
                var tempMaxCount = 0;
                var tempTableIndex = 0;
                for(var i=0;i<nowTableCount;i++)
                {
                    var tempCount = 0;
                    for(var j=0;j<relaFriendsWithSeatSeats.length;j++)
                    {
                        if((i+1) == relaFriendsWithSeatSeats[j])
                        {
                            tempCount += relaFriendsWithSeatNums[j];
                        }
                    }
                    if(tempMaxCount < tempCount)
                    {
                        tempMaxCount = tempCount;
                        tempTableIndex = (i+1)
                    }
                }
                if(tempMaxCount > numEveryTable)
                {
                    alert("第" + tempTableIndex + "桌座位不够，不能减座");
                    return false;
                }
                return true;
            }
            return true;
        }
        // 修改桌数或者没桌人数
        function changeTable()
        {
            if(!checkChangeTable())
            {
                $("#table_count_id").val(nowTableCount);
                $("#num_every_table_id").val(nowNumEveryTable);
                return;
            }
            nowTableCount = $("#table_count_id").val();//桌数
            nowNumEveryTable = $("#num_every_table_id").val();//每桌人数
            $("#total_num_id").html(nowTableCount*nowNumEveryTable);
            $("#with_seat_num_id").html(getSeatedFriendsNum());
            $("#empty_seat_id").html(nowTableCount*nowNumEveryTable - getSeatedFriendsNum());

            var seatContent = "";// 座位表格内容
            var tableCount = $("#table_count_id").val();//桌数
            var numEveryTable = parseInt($("#num_every_table_id").val());
            var totalLine = parseInt((tableCount-1)/5+1);//每行5桌，算总共要循环几次

            //循环totalLine次
            for(var n=0;n<totalLine;n++){
                // 第一行
                var beginContent = "<tr><td width=\"60\">&nbsp;</td>";
                var content = "";
                for(var i=0;i<5;i++)
                {
                    if((5*n)+(i+1) <= tableCount){
                        content += "<td>" + ((5*n)+(i+1)) + "桌</td>";
                    } else {
                        content += "<td></td>";
                    }
                }
                var endContent = "<td width=\"60\">&nbsp;</td></tr>";

                // 内容加入第一行
                seatContent += (beginContent + content + endContent);

                // 第二行
                beginContent = "<tr><td>人数</td>";
                content = "";
                for(var i=5*n;i<5*n+5;i++)
                {
                    if((i+1) <= tableCount){
                        content += "<td rowspan=\"" + (numEveryTable+1) + "\">"
                                + "<a class=\"dialogBtn\" href=\"#guestList\" onclick=\"updateRelaFriendList(" + (i+1) + ")\">+选择亲友</a>"
                                + "<div class=\"guests\">";
                        for(var j=0;j<relaFriendsWithSeatSeats.length;j++)
                        {
                            if((i+1)==relaFriendsWithSeatSeats[j])
                            {
                                content += "<div class=\"guestUnit\" style=\"height:" + (29*relaFriendsWithSeatNums[j]) + "px\">"
                                        + relaFriendsWithSeatNames[j] + "<span class=\"unitnum\">"
                                        + relaFriendsWithSeatNums[j] + "</span></div>";
                            }
                        }
                        content += "</div>"
                                + "</td>";
                    } else {
                        content += "<td rowspan=\"" + (numEveryTable+1) + "\"></td>"
                    }
                }
                endContent = "<td rowspan=\"" + (numEveryTable+1) + "\">" +
                        "<a href=\"javascript:addTable()\">+加桌</a><br>" +
                        "<a href=\"javascript:minusTable()\">-减桌</a>" +
                        "</td></tr>";

                // 内容加入第二行
                seatContent += (beginContent + content + endContent);

                // 第三行
                var thirdLine = "";
                for(var i=0;i<numEveryTable;i++)
                {
                    thirdLine += "<tr><td>" + (i+1) + "</td></tr>"
                }
                // 内容加入第三行
                seatContent += thirdLine;
            }

            // 第四行
            var forthLine = "<td>&nbsp;</td>"
                    + "<td colspan=\"5\">" +
                    "<a href=\"javascript:addSeat()\">+加座</a>&nbsp;" +
                    "<a href=\"javascript:minusSeat()\">-减座</a>" +
                    "</td>"
                    + "<td>&nbsp;</td>";

            // 内容加入第四行
            seatContent += forthLine;

            $("#seat_table").html(seatContent);
            $(".dialogBtn").fancybox();
        }
        // 加桌
        function addTable()
        {
            var temp = parseInt($("#table_count_id").val());
            if(temp >= 100)
            {
                alert("目前配置最多100桌");
                return;
            }
            $("#table_count_id").val(temp+1);
            changeTable();
        }
        // 减桌
        function minusTable()
        {
            var temp = parseInt($("#table_count_id").val());
            if(temp <= 1)
            {
                alert("目前配置最少1桌");
                return;
            }
            // 判最后一个座位是否已经有人坐了
            var isLastTableSit = false;
            for(var j=0;j<relaFriendsWithSeatSeats.length;j++)
            {
                if((nowTableCount) == relaFriendsWithSeatSeats[j])
                {
                    isLastTableSit = true;
                }
            }
            if(isLastTableSit)
            {
                alert("最后一桌已经有人坐了，不能减桌");
            } else
            {
                $("#table_count_id").val(temp-1);
                changeTable();
            }
        }
        // 加座
        function addSeat()
        {
            var temp = parseInt($("#num_every_table_id").val());
            if(temp >= 10)
            {
                alert("目前配置最多10人1桌");
                return;
            }
            $("#num_every_table_id").val(temp+1);
            changeTable();
        }
        // 减座
        function minusSeat()
        {
            var temp = parseInt($("#num_every_table_id").val());
            if(temp <= 1)
            {
                alert("目前配置最少1人1桌");
                return;
            }
            // 判是否有哪桌已经坐满，不能再减座
            var tempMaxCount = 0;
            var tempTableIndex = 0;
            for(var i=0;i<nowTableCount;i++)
            {
                var tempCount = 0;
                for(var j=0;j<relaFriendsWithSeatSeats.length;j++)
                {
                    if((i+1) == relaFriendsWithSeatSeats[j])
                    {
                        tempCount += relaFriendsWithSeatNums[j];
                    }
                }
                if(tempMaxCount < tempCount)
                {
                    tempMaxCount = tempCount;
                    tempTableIndex = (i+1)
                }
            }
            if(tempMaxCount == nowNumEveryTable)
            {
                alert("第" + tempTableIndex + "桌已经坐满，不能再减座");
            } else
            {
                $("#num_every_table_id").val(temp-1);
                changeTable();
            }
        }
        // 更新选择亲友窗口
        function updateRelaFriendList(t)
        {
        <%
            if(isLogin)
            {
        %>
            nowTable = t;
            var content = "";
            content += "<h3>您的亲友簿共<div class=\"value\">" + (getSeatedFriendsNum() + getNeverSeatedFriendsNum()) + "</div>位亲友，";
            content += "其中<div class=\"value\">" + getSeatedFriendsNum() + "</div>人已安排座位。";
            content += "<a target=\"_blank\" href=\"<%=baseUrl%>main/friends_book.jsp\" class=\"linkRight\">管理我的亲友簿</a></h3>";
            content += "<ul class=\"guestList\">";
            for(var i=0;i<relaFriendsWithSeatNames.length;i++)
            {
                content += "<li>";
                content += "<input id=\"relaFriendsWithSeat" + (i+1) + "\" name=\"\" type=\"checkbox\" value=\"\" checked=\"checked\"";
                if(nowTable != relaFriendsWithSeatSeats[i])
                {
                    content += " disabled=\"disabled\"";
                }
                content += "/>";
                content += "<label for=\"relaFriendsWithSeat" + (i+1) + "\">" + relaFriendsWithSeatNames[i] + "(" + relaFriendsWithSeatNums[i] + ")</label>";
                content += "<p class=\"unitState\">安排在" + relaFriendsWithSeatSeats[i] + "桌</p>";
                content += "</li>";
            }

            for(var i=0;i<relaFriendsWithoutSeatNames.length;i++)
            {
                content += "<li>";
                content += "<input id=\"relaFriendsWithoutSeat" + (i+1) + "\" name=\"\" type=\"checkbox\" value=\"\"/>";
                content += "<label for=\"relaFriendsWithoutSeat" + (i+1) + "\">" + relaFriendsWithoutSeatNames[i] + "(" + relaFriendsWithoutSeatNums[i] + ")</label>";
                content += "<p class=\"unitState\">未安排</p>";
                content += "</li>";
            }
            content += "</ul>";
            content += "<div align=\"center\"><button class=\"btnAddguest\" onclick=\"putToSeat()\">确定</button></div>";
            $("#guestList").html(content);
        <%
            }
        %>
        }
        // 保存席位
        function saveSeat()
        {
            $("#saveSeatForm_tableCount").val(nowTableCount);
            $("#saveSeatForm_numEveryTable").val(nowNumEveryTable);
            $("#saveSeatForm_relaFriendsWithSeatNames").val(relaFriendsWithSeatNames.toString());
            $("#saveSeatForm_relaFriendsWithSeatNums").val(relaFriendsWithSeatNums.toString());
            $("#saveSeatForm_relaFriendsWithSeatSeats").val(relaFriendsWithSeatSeats.toString());
            $("#saveSeatForm_relaFriendsWithoutSeatNames").val(relaFriendsWithoutSeatNames.toString());
            $("#saveSeatForm_relaFriendsWithoutSeatNums").val(relaFriendsWithoutSeatNums.toString());
            $("#saveSeatForm_relaFriendsWithoutSeatSeats").val(relaFriendsWithoutSeatSeats.toString());
            $("#saveSeatForm").submit();
        }
        // 计算已经有座位的人
        function getSeatedFriendsNum()
        {
            var tempCount = 0;
            for(var i=0;i<relaFriendsWithSeatNums.length;i++)
            {
                tempCount += relaFriendsWithSeatNums[i];
            }
            return tempCount;
        }
        // 计算未安排座位的人
        function getNeverSeatedFriendsNum()
        {
            var tempCount = 0;
            for(var i=0;i<relaFriendsWithoutSeatNums.length;i++)
            {
                tempCount += relaFriendsWithoutSeatNums[i];
            }
            return tempCount;
        }
    </script>
</head>
<body>
<div class="header">
    <p class="topright">
        <%
            if(isLogin)
            {
        %>
        <%=wedisleUser.getUserName()%> | <a href="<%=baseUrl%>main.jsp">个人主页</a> | <a href="<%=baseUrl%>logout.jsp">退出</a>
        <%
        }else
        {
        %>
        <a href="<%=baseUrl%>signin.jsp">注册</a> | <a href="<%=baseUrl%>login.jsp">登录</a>
        <%
            }
        %>
    </p>
    <div class="title">
        <span class="logo"><img src="<%=baseUrl%>images/logo.png" alt="My Wonderland" /></span>
        <span class="pagetitle">记录我的历程，<a href="#">创建我的主页</a></span>
    </div>
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
    <h1>座位安排</h1>
    <ul class="seatSet">
        <li>
            <label>桌数</label>
            <select id="table_count_id" onchange="changeTable()">
                <%
                    //判如果已登录
                    if(isLogin)
                    {
                        seatInfo = WedisleSeatInfoDao.getSeatInfoByUserId(wedisleUser.getId());
                        for(int i=0;i<100;i++)
                        {
                            out.print("<option" + ((i+1)==seatInfo.getTableCount()?" selected=\"selected\"":"") + ">" + (i+1) + "</option>");
                        }
                    }
                %>
            </select>
        </li>
        <li>
            <label>每桌人数</label>
            <select id="num_every_table_id" onchange="changeTable()">
                <%
                    //判如果已登录
                    if(isLogin)
                    {
                        seatInfo = WedisleSeatInfoDao.getSeatInfoByUserId(wedisleUser.getId());
                        for(int i=0;i<10;i++)
                        {
                            out.print("<option" + ((i+1)==seatInfo.getNumEveryTable()?" selected=\"selected\"":"") + ">" + (i+1) + "</option>");
                        }
                    }
                %>
            </select>
        </li>
        <%
            //判如果已登录
            if(isLogin)
            {
        %>
        <li>您最多可宴请<div id="total_num_id" class="value"><%=seatInfo.getTableCount()*seatInfo.getNumEveryTable()%></div>人，已安排<div class="value" id="with_seat_num_id"><%=relaFriendsWithSeat.size()%></div>人，当前剩余<div class="value" id="empty_seat_id"><%=seatInfo.getTableCount()*seatInfo.getNumEveryTable()-relaFriendsWithSeat.size()%></div>个座位</li>
        <%
            }
        %>
    </ul>
    <h3>您可以通过“+”选择亲友来为亲友调整座位。</h3>
    <p><a class="icolink save" href="javascript: saveSeat();">保存</a><a class="icolink print" href="javascript: preview();" target="_blank">打印</a></p>
    <!--startprint-->
    <div style="overflow: auto;" width="100%">
        <table width="100%" border="0" cellspacing="1" cellpadding="0" class="seatTable" id="seat_table">
            <tr id="first_line_id">
            </tr>
            <tr id="second_line_id">
            </tr>
            <tr id="third_line_id_1" style="display: none;">
                <td>1</td>
            </tr>
            <tr id="third_line_id_2" style="display: none;">
                <td>2</td>
            </tr>
            <tr id="third_line_id_3" style="display: none;">
                <td>3</td>
            </tr>
            <tr id="third_line_id_4" style="display: none;">
                <td>4</td>
            </tr>
            <tr id="third_line_id_5" style="display: none;">
                <td>5</td>
            </tr>
            <tr id="third_line_id_6" style="display: none;">
                <td>6</td>
            </tr>
            <tr id="third_line_id_7" style="display: none;">
                <td>7</td>
            </tr>
            <tr id="third_line_id_8" style="display: none;">
                <td>8</td>
            </tr>
            <tr id="third_line_id_9" style="display: none;">
                <td>9</td>
            </tr>
            <tr id="third_line_id_10" style="display: none;">
                <td>10</td>
            </tr>
            <tr id="forth_line_id">
            </tr>
        </table>
    </div>
    <!--endprint-->
</div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
    <a href="#">关于婚礼岛</a></p>

<%
    //判如果已登录
    if(isLogin)
    {
%>
<div style="display:none">
    <div id="guestList" class="dialogInnerBox">
        <h3>您的亲友簿共<div class="value"><%=relaFriendsWithSeat.size()+relaFriendsWithoutSeat.size()%></div>位亲友，其中<div class="value"><%=relaFriendsWithSeat.size()%></div>人已安排座位。<a target="_blank" href="<%=baseUrl%>main/friends_book.jsp" class="linkRight">管理我的亲友簿</a></h3>
        <ul class="guestList">
            <%
                for(int i=0;i<relaFriendsWithSeat.size();i++)
                {
                    WedisleRelaFriend friend = (WedisleRelaFriend)relaFriendsWithSeat.get(i);
            %>
            <li>
                <input id="relaFriendsWithSeat<%=i+1%>" name="" type="checkbox" value="" checked="checked"/>
                <label for="relaFriendsWithSeat<%=i+1%>"><%=friend.getName()%>(<%=friend.getNum()%>)</label>
                <p class="unitState">安排在<%=friend.getSeat()%>桌</p>
            </li>
            <%
                }
            %>
            <%
                for(int i=0;i<relaFriendsWithoutSeat.size();i++)
                {
                    WedisleRelaFriend friend = (WedisleRelaFriend)relaFriendsWithoutSeat.get(i);
            %>
            <li>
                <input id="relaFriendsWithoutSeat<%=i+1%>" name="" type="checkbox" value=""/>
                <label for="relaFriendsWithoutSeat<%=i+1%>"><%=friend.getName()%>(<%=friend.getNum()%>)</label>
                <p class="unitState">未安排</p>
            </li>
            <%
                }
            %>
        </ul>
        <button class="btnAddguest" onclick="putToSeat()">确定</button>
    </div>
</div>
<%
    }
%>
<form action="<%=baseUrl%>saveSeat" id="saveSeatForm" method="post">
    <input type="hidden" name="tableCount" id="saveSeatForm_tableCount">
    <input type="hidden" name="numEveryTable" id="saveSeatForm_numEveryTable">
    <input type="hidden" name="relaFriendsWithSeatNames" id="saveSeatForm_relaFriendsWithSeatNames">
    <input type="hidden" name="relaFriendsWithSeatNums" id="saveSeatForm_relaFriendsWithSeatNums">
    <input type="hidden" name="relaFriendsWithSeatSeats" id="saveSeatForm_relaFriendsWithSeatSeats">
    <input type="hidden" name="relaFriendsWithoutSeatNames" id="saveSeatForm_relaFriendsWithoutSeatNames">
    <input type="hidden" name="relaFriendsWithoutSeatNums" id="saveSeatForm_relaFriendsWithoutSeatNums">
    <input type="hidden" name="relaFriendsWithoutSeatSeats" id="saveSeatForm_relaFriendsWithoutSeatSeats">
</form>
</body>
</html>
