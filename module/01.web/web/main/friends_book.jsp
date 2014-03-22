<%@ page import="com.gxx.record.dao.wedisle.WedisleRelaFriendDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="日志,心声,人生经历,7-blog"/>
<meta name="Description" content="My Dairy weekly" />
<title>用户管理平台-婚礼岛</title>
<link rel="shortcut icon" type="image/x-icon" href="http://7swing.com/favicon.ico" />
<link href="<%=baseUrl%>/style/homePlus.css" rel="stylesheet" type="text/css" />
<link href="<%=baseUrl%>/style/SpryTabbedPanels.css" rel="stylesheet" type="text/css" />
<link href="<%=baseUrl%>/style/cp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=baseUrl%>/Scripts/jquery-min.js"></script>
<script type="text/javascript" src="<%=baseUrl%>/Scripts/comm.js"></script>
<script type="text/javascript" src="<%=baseUrl%>/Scripts/pinyin.js"></script>
<script src="<%=baseUrl%>/Scripts/SpryTabbedPanels.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=baseUrl%>/Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="<%=baseUrl%>/Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="<%=baseUrl%>/Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<script type="text/javascript">
    //所有亲戚的名字，逗号分割
    var all_names = "";
    //选中的亲戚的名字
    var choose_names = "";
    //所有帮帮团的名字，逗号分隔
    var all_help_names = "";
    //所有工作人员的名字，逗号分隔
    var all_worker_names = "";
    $(document).ready(function() {
        <%
            if(!isLogin)
            {
        %>
        location.href = "<%=baseUrl%>login.jsp";
        <%
            }
        %>

        <%
            if(isLogin)
            {
        %>
        //展示亲友簿
        var friends = eval(<%=WedisleUtils.getJsonFromWedisleRelaFriends(WedisleRelaFriendDao.queryRelaFriendsByUserId(wedisleUser.getId()))%>);
        var html = "<tr><th class=\"tdcb\"><input type=\"checkbox\" onchange=\"check_all_box(this)\"/></th>"
                + "<th>姓名</th><th>关系</th><th>出席人数</th><th>邮箱</th><th>电话</th><th>地址</th><th>备注</th></tr>";
        for(var i=0;i<friends.length;i++){
            if(all_names != ""){
                all_names += ',';
            }
            all_names += friends[i]["name"];
            html += "<tr><td class=\"tdcb\"><input class=\"cb\" type=\"checkbox\" onclick=\"choose_box(this,'"
                    + friends[i]["name"] + "')\"/></td><td><a class=\"dialogBtn\" href=\"#guestEdit\">"
                    + friends[i]["name"] + "</a></td>" + "<td><span class=\"group1\">"
                    + friends[i]["relationship"] + "</span></td><td><span>" + friends[i]["num"]
                    + "</span></td><td>" + friends[i]["email"] + "</td>" + "<td><span>" + friends[i]["phone"]
                    + "</span></td><td><span title=\"" + friends[i]["place"] + "\">" + friends[i]["place"]
                    + "</span></td><td><span>" + friends[i]["resv"] + "</span></td></tr>";
        }
        document.getElementById("friends_id").innerHTML = html;

        //展示帮帮团
        var html2 = "<tr><th class=\"tdcb\"><input type=\"checkbox\" onchange=\"check_all_help_group_box(this)"
                + "\"/></th><th>姓名</th><th>角色</th><th>电话</th></tr>";
        for(var i=0;i<friends.length;i++){
            if(friends[i]["helpGroup"] != ""){
                if(all_help_names != ""){
                    all_help_names += ","
                }
                all_help_names += friends[i]["name"];
                html2 += "<tr><td class=\"tdcb\"><input class=\"cb_help2\" type=\"checkbox\" onchange=\"check_help_group_box(this, '"
                        + friends[i]["name"] + "')\"/></td><td><a class=\"dialogBtn\" href=\"#guestEdit\">"
                        + friends[i]["name"] + "</a></td><td>" + friends[i]["helpGroup"] + "</td><td>"
                        + friends[i]["phone"] + "</td></tr>";
            }
        }
        document.getElementById("help_group_id").innerHTML = html2;

        //展示工作人员
        var html3 = "<tr><th class=\"tdcb\"><input type=\"checkbox\" onchange=\"check_all_worker_box(this)"
                + "\"/></th><th>姓名</th><th>角色</th><th>电话</th></tr>";
        for(var i=0;i<friends.length;i++){
            if(friends[i]["worker"] != ""){
                if(all_worker_names != ""){
                    all_worker_names += ","
                }
                all_worker_names += friends[i]["name"];
                html3 += "<tr><td class=\"tdcb\"><input class=\"cb_worker\" type=\"checkbox\" onchange=\"check_worker_box(this, '"
                        + friends[i]["name"] + "')\"/></td><td><a class=\"dialogBtn\" href=\"#guestEdit\">"
                        + friends[i]["name"] + "</a></td><td>" + friends[i]["worker"] + "</td><td>"
                        + friends[i]["phone"] + "</td></tr>";
            }
        }
        document.getElementById("worker_id").innerHTML = html3;

        <%
            }
        %>

        // 点击亲友姓名
        $(".dialogBtn").fancybox();
        $(".dialogBtn").click(function(){
            var name = this.innerHTML;
            for(var i=0;i<friends.length;i++){
                if(friends[i]["name"] == name){
                    document.getElementById("edit_table_name").value = friends[i]["name"];
                    document.getElementById("edit_table_name").disabled = "disabled";
                    document.getElementById("edit_table_relationship").value = friends[i]["relationship"];
                    document.getElementById("edit_table_num").value = friends[i]["num"];
                    document.getElementById("edit_table_email").value = friends[i]["email"];
                    document.getElementById("edit_table_phone").value = friends[i]["phone"];
                    document.getElementById("edit_table_place").value = friends[i]["place"];
                    document.getElementById("edit_table_resv").value = friends[i]["resv"];
                    document.getElementById("edit_table_new_group_1").style.display = "none";
                    document.getElementById("edit_table_new_group_1").value = "";
                    document.getElementById("edit_table_new_group_2").style.display = "none";
                }
            }
        });

        <%
        //展示信息
        if(StringUtils.isNotBlank(message)){
        %>
        document.getElementById("delete_rela_friend_msg").innerHTML = "<%=message%>";
        document.getElementById("delete_rela_friend_msg").style.display = "";
        <%
        }
        %>

        //展示亲友成员
        var headHtml = "";
        var helpGroupCount = 0;
        var workerCount = 0;
        var bodyHtml1 = "";
        var bodyHtml2 = "";
        var letterArray = new Array();
        var elseFriends = new Array();
        for(var i=0;i<friends.length;i++){
            if(friends[i]["helpGroup"] != ""){
                helpGroupCount ++;
            }
            if(friends[i]["worker"] != ""){
                workerCount ++;
            }
            var beginWithLetter = false;
            for(var j=0;j<BIG_LETTERS.length;j++){
                if(BIG_LETTERS[j] == getFirstPinYin(friends[i]["name"])){
                    beginWithLetter = true;
                    break;
                }
            }
            if(beginWithLetter == false){
                elseFriends[elseFriends.length] = friends[i];
            }
        }
        headHtml += "<h3 id=\"guestListHead1\" style=\"display:none;\">已有<div class=\"value\">"
                + helpGroupCount + "</div>位帮帮团成员。请在下方选择需要加入帮帮团的小伙伴并为他们分配角色</h3>";
        headHtml += "<h3 id=\"guestListHead2\" style=\"display:none;\">已有<div class=\"value\">"
                + workerCount + "</div>位工作人员。请在下方选择作为工作人员的小伙伴并为他们分配角色</h3>";
        document.getElementById("guestListHead").innerHTML = headHtml;

        for(var i=0;i<BIG_LETTERS.length;i++){
            var letterHasFriend = false;//这个字母是否有亲友
            for(var j=0;j<friends.length;j++){
                if(BIG_LETTERS[i] == getFirstPinYin(friends[j]["name"])){
                    if(letterHasFriend == false){
                        letterHasFriend = true;
                        letterArray[letterArray.length] = BIG_LETTERS[i];
                        bodyHtml2 += "<h4 id=\"agroup\"><a name=\"help_" + BIG_LETTERS[i] + "\">" + BIG_LETTERS[i] + "</a></h4><ul class=\"guestList\">";
                    }
                    bodyHtml2 += "<li class=\"group1\"><input class=\"cb_help\" type=\"checkbox\" onchange=\"chooseHelp(this,'" + friends[j]["name"] + "')\"/>" + friends[j]["name"];
                    if(friends[j]["helpGroup"] == ""){
                        bodyHtml2 += "<p class=\"unaddhelp\">未加入帮帮团</p>"
                    } else {
                        bodyHtml2 += "<p class=\"addhelp\">已加入帮帮团</p>"
                    }
                    if(friends[j]["worker"] == ""){
                        bodyHtml2 += "<p class=\"noworker\">非工作人员</p>"
                    } else {
                        bodyHtml2 += "<p class=\"worker\">工作人员</p>"
                    }
                    bodyHtml2 += "</li>";
                }
            }
            if(letterHasFriend){
                bodyHtml2 += "</ul>";
            }
        }
        if(elseFriends.length > 0){
            bodyHtml2 += "<h4 id=\"agroup\"><a name=\"help_qita\">其他</a></h4><ul class=\"guestList\">";
            for(var i=0;i<elseFriends.length;i++){
                bodyHtml2 += "<li class=\"group1\"><input class=\"cb_help\" type=\"checkbox\" onchange=\"chooseHelp(this,'" + elseFriends[i]["name"] + "')\"/>" + elseFriends[i]["name"];
                if(elseFriends[i]["helpGroup"] == ""){
                    bodyHtml2 += "<p class=\"unaddhelp\">未加入帮帮团</p>"
                } else {
                    bodyHtml2 += "<p class=\"addhelp\">已加入帮帮团</p>"
                }
                if(elseFriends[i]["worker"] == ""){
                    bodyHtml2 += "<p class=\"noworker\">非工作人员</p>"
                } else {
                    bodyHtml2 += "<p class=\"worker\">工作人员</p>"
                }
                bodyHtml2 += "</li>";
            }
            bodyHtml2 += "</ul>";
        }
        bodyHtml1 += "<p class=\"groupabc\"><a href=\"#\" onclick=\"chooseAllHelp()\">全部</a>"
        for(var i=0;i<letterArray.length;i++){
            bodyHtml1 += "<a href=\"#help_" + letterArray[i] + "\">" + letterArray[i] + "</a>";
        }
        if(elseFriends.length > 0){
            bodyHtml1 += "<a href=\"#help_qita\">其他</a>";
        }
        bodyHtml1 += "</p>";
        document.getElementById("guestListBody").innerHTML = bodyHtml1 + bodyHtml2;
    });

    //点击添加按钮
    function before_add(){
        document.getElementById("edit_table_name").value = "";
        document.getElementById("edit_table_name").disabled = "";
        document.getElementById("edit_table_relationship").value = "";
        document.getElementById("edit_table_num").value = "";
        document.getElementById("edit_table_email").value = "";
        document.getElementById("edit_table_phone").value = "";
        document.getElementById("edit_table_place").value = "";
        document.getElementById("edit_table_resv").value = "";
        document.getElementById("edit_table_new_group_1").style.display = "none";
        document.getElementById("edit_table_new_group_1").value = "";
        document.getElementById("edit_table_new_group_2").style.display = "none";
    }

    //改变关系
    function change_relationship(t){
        if("自定义分组" == t.value){
            document.getElementById("edit_table_new_group_1").style.display = "";
            document.getElementById("edit_table_new_group_2").style.display = "";
        } else{
            document.getElementById("edit_table_new_group_1").style.display = "none";
            document.getElementById("edit_table_new_group_2").style.display = "none";
        }
    }

    //提交修改关系表格
    function check_submit_edit_table(){
        var name = document.getElementById("edit_table_name").value;
        if("" == name){
            document.getElementById("edit_table_msg").style.display = "";
            document.getElementById("edit_table_msg").innerHTML = "亲友姓名还没写哦，亲";
            return;
        }
        var relationship = document.getElementById("edit_table_relationship").value;
        var new_group = document.getElementById("edit_table_new_group_1").value;
        if(relationship == "自定义分组" && new_group==""){
            document.getElementById("edit_table_msg").style.display = "";
            document.getElementById("edit_table_msg").innerHTML = "亲友自定义组名还没写哦，亲";
            return;
        }
        if(relationship == "自定义分组" && new_group=="自定义分组"){
            document.getElementById("edit_table_msg").style.display = "";
            document.getElementById("edit_table_msg").innerHTML = "请填写其他自定义组名哦，亲";
            return;
        }

        if(relationship == "自定义分组" && new_group!="自定义分组"){
            var nodes = document.getElementById("edit_table_relationship").childNodes;
            for(var i=0;i<nodes.length;i++){
                if(nodes[i].innerHTML == new_group){
                    document.getElementById("edit_table_msg").style.display = "";
                    document.getElementById("edit_table_msg").innerHTML = "该组名已存在哦，亲";
                    return;
                }
            }
        }

        var num = document.getElementById("edit_table_num").value;
        if("" == num){
            document.getElementById("edit_table_msg").style.display = "";
            document.getElementById("edit_table_msg").innerHTML = "出席人数还没写哦，亲";
            return;
        }

        //js校验数字
        var reg = /^\d+$/;
        var re = num.match( reg );
        if(!re){
            document.getElementById("edit_table_msg").style.display = "";
            document.getElementById("edit_table_msg").innerHTML = "出席人数只能写数字哦，亲";
            return;
        }

        // 赋值名字
        document.getElementById("edit_table_hidden_name").value = document.getElementById("edit_table_name").value;
        document.getElementById("update_rela_friend_form").submit();
    }

    //全选
    function check_all_box(t){
        var boxes = document.getElementsByClassName("cb");
        for(var i=0;i<boxes.length;i++){
            if(t.checked){
                boxes[i].checked = true;
                choose_names = all_names;
            } else {
                boxes[i].checked = false;
                choose_names = "";
            }
        }
    }

    //选择某个单选框
    function choose_box(t, name){
        if(t.checked){
            if(choose_names.length > 0){
                choose_names += ",";
            }
            choose_names += name;
        } else {
            var names = choose_names.split(",");
            var temp = "";
            for(var i=0;i<names.length;i++){
                if(name == names[i]){
                    continue;
                }
                if(temp.length > 0){
                    temp += ",";
                }
                temp += names[i];
            }
            choose_names = temp;
        }
    }

    //删除亲友
    function delete_rela_friend(){
        if(choose_names == ""){
            document.getElementById("delete_rela_friend_msg").innerHTML = "还没有选择要删除的亲友姓名哦，亲";
            document.getElementById("delete_rela_friend_msg").style.display = "";
            return;
        }
        document.getElementById("delete_rela_friend_names").value = choose_names;
        document.getElementById("delete_rela_friend_form").submit();
    }

    //帮帮团---------------------------------------------------------------
    var choose_help_names = "";
    var choose_help_names2 = "";
    //选择全部帮帮团
    function chooseAllHelp(){
        var checked = false;
        if(choose_help_names.split(",").length == all_names.split(",").length){
            choose_help_names = "";
        } else {
            choose_help_names = all_names;
            checked = true;
        }
        var boxes = document.getElementsByClassName("cb_help");
        for(var i=0;i<boxes.length;i++){
            if(checked){
                boxes[i].checked = true;
            } else {
                boxes[i].checked = false;
            }
        }
    }
    //选择帮帮团
    function chooseHelp(t, name){
        if(t.checked){
            if(choose_help_names != ""){
                choose_help_names += ",";
            }
            choose_help_names += name;
        } else {
            var new_names = "";
            var names = choose_help_names.split(",");
            for(var i=0;i<names.length;i++){
                if(names[i] == name){
                    continue;
                } else {
                    if(new_names != ""){
                        new_names += ",";
                    }
                    new_names += names[i];
                }
            }
            choose_help_names = new_names;
        }
    }

    //更新帮帮团
    function update_help_group(){
        if(choose_help_names == ""){
            document.getElementById("update_help_group_msg").style.dispay = "";
            document.getElementById("update_help_group_msg").innerHTML = "请选择亲友哦，亲";
            return;
        }
        if(document.getElementById("update_help_group_id").value == ""){
            document.getElementById("update_help_group_msg").style.dispay = "";
            document.getElementById("update_help_group_msg").innerHTML = "请输入角色哦，亲";
            return;
        }
        document.getElementById("update_help_group_names_id").value = choose_help_names;
        document.getElementById("update_help_group_form").submit();
    }

    //全选帮帮团
    function check_all_help_group_box(t){
        var boxes = document.getElementsByClassName("cb_help2");
        for(var i=0;i<boxes.length;i++){
            if(t.checked){
                boxes[i].checked = true;
                choose_help_names2 = all_help_names;
            } else {
                boxes[i].checked = false;
                choose_help_names2 = "";
            }
        }
    }
    //选择帮帮团
    function check_help_group_box(t, name){
        if(t.checked){
            if(choose_help_names2.length > 0){
                choose_help_names2 += ",";
            }
            choose_help_names2 += name;
        } else {
            var names = choose_help_names2.split(",");
            var temp = "";
            for(var i=0;i<names.length;i++){
                if(name == names[i]){
                    continue;
                }
                if(temp.length > 0){
                    temp += ",";
                }
                temp += names[i];
            }
            choose_help_names2 = temp;
        }
    }

    //删除帮帮团
    function delete_help_group(){
        if(choose_help_names2 == ""){
            document.getElementById("delete_help_group_msg").innerHTML = "还没有选择要删除的帮帮团亲友哦，亲";
            document.getElementById("delete_help_group_msg").style.display = "";
            return;
        }
        document.getElementById("delete_help_group_names").value = choose_help_names2;
        document.getElementById("delete_help_group_form").submit();
    }

    //添加帮帮团
    function before_add_help(){
        document.getElementById("guestListHead1").style.display = "";
        document.getElementById("guestListHead2").style.display = "none";
        document.getElementById("update_type_id").value = "help_group";
    }

    //工作人员------------------------------
    var choose_worker = "";
    //全选工作人员
    function check_all_worker_box(t){
        var boxes = document.getElementsByClassName("cb_worker");
        for(var i=0;i<boxes.length;i++){
            if(t.checked){
                boxes[i].checked = true;
                choose_worker = all_worker_names;
            } else {
                boxes[i].checked = false;
                choose_worker = "";
            }
        }
    }
    //选择工作人员
    function check_worker_box(t, name){
        if(t.checked){
            if(choose_worker.length > 0){
                choose_worker += ",";
            }
            choose_worker += name;
        } else {
            var names = choose_worker.split(",");
            var temp = "";
            for(var i=0;i<names.length;i++){
                if(name == names[i]){
                    continue;
                }
                if(temp.length > 0){
                    temp += ",";
                }
                temp += names[i];
            }
            choose_worker = temp;
        }
    }

    //删除工作人员
    function delete_worker(){
        if(choose_worker == ""){
            document.getElementById("delete_worker_msg").innerHTML = "还没有选择要删除的工作人员哦，亲";
            document.getElementById("delete_worker_msg").style.display = "";
            return;
        }
        document.getElementById("delete_worker_names").value = choose_worker;
        document.getElementById("delete_worker_form").submit();
    }

    //添加工作人员
    function before_add_worker(){
        document.getElementById("guestListHead1").style.display = "none";
        document.getElementById("guestListHead2").style.display = "";
        document.getElementById("update_type_id").value = "worker";
    }
</script>
    
</head>
<body>
<div class="header">
    <%--<p class="topright"><a href="#">婚礼岛首页</a> | <a href="#">我的主页</a> | <a href="#" class="msgrev msgnew">&nbsp;</a> | <a href="#">退出</a></p>--%>
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
    <div class="title"> <span class="logo"><img src="<%=baseUrl%>/images/logo.png" alt="My Wonderland" /></span> <span class="pagetitle">记录我的历程，<a href="#">创建我的主页</a></span> </div>
</div>
<div class="main">
  <div class="subbar" style="display: none;">
    <ul>
      <li><a href="#" data-filter="*">我的婚礼岛</a></li>
      <li><a href="#" data-filter=".mon">我的计划</a></li>
      <li class="selected"><a href="#" data-filter=".tue">亲友簿</a></li>
      <li><a href="#" data-filter=".wed">任务分配</a></li>
      <li><a href="#" data-filter=".thu">我的工具</a></li>
      <li><a href="#" data-filter=".fri">个人信息维护</a></li>
    </ul>
  </div>
  <div class="mainmod" style="margin-left:0px;">
    <div class="main-inside">
      <h1>亲友簿</h1>
      <div id="TabbedPanels1" class="TabbedPanels">
  <ul class="TabbedPanelsTabGroup">
    <li class="TabbedPanelsTab" tabindex="0">亲友簿</li>
    <li class="TabbedPanelsTab" tabindex="0">帮帮团</li>
    <li class="TabbedPanelsTab" tabindex="0">工作人员</li>    
    <li class="TabbedPanelsTab">+ 添加分组</li>
  </ul>
  <div class="TabbedPanelsContentGroup">
    <div class="TabbedPanelsContent">
    <div class="guestlist">
      <div class="editbar"><a class="dialogBtn" href="#guestEdit" onclick="before_add()">+ 添加</a><a href="#" onclick="delete_rela_friend()">x 删除</a></div>
        <div class="msgtip msgwarn" style="display: none;" id="delete_rela_friend_msg">还没有选择要删除的亲友姓名哦，亲</div>
        <table width="100%" border="0" cellspacing="1" cellpadding="0" class="guestList" id="friends_id">
</table>
        <%
            if(isLogin)
            {
        %>
      <div class="listInfo">已添加<span class="num"><%=WedisleUtils.countFriends(WedisleRelaFriendDao.queryRelaFriendsByUserId(wedisleUser.getId()))%></span>名亲友</div>
        <%
            }
        %>
      <div class="pagination">
<a href="#">首页</a><a href="#">上一页</a><a href="#" class="current">1</a><a href="#">2</a><a href="#">3</a><a>…</a><a href="#">下一页</a><a href="#">末页</a>
</div>
      </div>
      <div class="seatlist">
      <h2>婚宴宾客</h2>
      <div class="editbar"><a href="<%=baseUrl%>tool/tool_seat.jsp">查看排位</a></div>
      <%
          if(isLogin)
          {
      %>
      <div class="listInfo">已安排<span class="num"><%=WedisleUtils.countFriendsWithSeat(WedisleRelaFriendDao.queryRelaFriendsByUserId(wedisleUser.getId()))%></span>个席位，剩余<span class="num"><%=WedisleUtils.countLeftSeat(wedisleUser.getId())%></span>个空位。</div>
          <%
              }
          %>
      </div>
    </div>
    <div class="TabbedPanelsContent">
    <div class="helperlist">
      <div class="msgtip msginfo">从亲友簿中挑选小伙伴们组成帮帮团，给他们分配任务。 <a class="dialogBtn" href="#guestList" onclick="before_add_help()">设置帮帮团</a></div>
      </div>
      <div class="editbar"><a class="dialogBtn" href="#guestList" onclick="before_add_help()">+ 添加</a><a href="#" onclick="delete_help_group()">x 删除</a></div>
      <div class="msgtip msgwarn" style="display: none;" id="delete_help_group_msg">还没有选择要删除的亲友姓名哦，亲</div>
      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="guestList" id="help_group_id">
    </table>
        <%
            if(isLogin)
            {
        %>
      <div class="listInfo">已添加<span class="num"><%=WedisleUtils.countHelpGroup(wedisleUser.getId())%></span>名帮帮团成员</div>
        <%
            }
        %>
      <div class="pagination">
<a href="#" class="current">1</a>
</div>
    </div>
    <div class="TabbedPanelsContent">
    <div class="workerlist">
      </div>
    <div class="editbar"><a class="dialogBtn" href="#guestList" onclick="before_add_worker()">+ 添加</a><a href="#" onclick="delete_worker()">x 删除</a></div>
        <div class="msgtip msgwarn" style="display: none;" id="delete_worker_msg"></div>
      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="guestList" id="worker_id">
</table>
        <%
            if(isLogin)
            {
        %>
      <div class="listInfo">已添加<span class="num"><%=WedisleUtils.countWorker(wedisleUser.getId())%></span>名工作人员</div>
        <%
            }
        %>
      <div class="pagination">
<a href="#" class="current">1</a>
</div>
      </div>
    
    
  </div>
</div>
      
      
      
    </div>
    <div class="cf"></div>
  </div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
  <a href="#">关于婚礼岛</a></p>
  
  <div style="display:none">
  <div id="guestEdit" class="dialogInnerBox">
      <form id="update_rela_friend_form" action="<%=baseUrl%>updateRelaFriend" method="post" onsubmit="return false;">
          <input type="hidden" id="edit_table_hidden_name" name="userName"/>
  <ul class="guestedit">
  <li>
  <label>姓名<span class="requiredico">*</span></label>
  <input type="text" id="edit_table_name"/>
  </li>
  <li>
  <label>关系<span class="requiredico">*</span></label>
  <% if(isLogin){%>
      <select name="relationship" id="edit_table_relationship" onchange="change_relationship(this)"
              ><%
          String type = wedisleUser.getFriendsType();
          String[] types = type.split(",");
          for(String t : types){
            %><option value="<%=t%>"><%=t%></option><%
          }
          if(types.length < 10){
      %><option value="自定义分组">自定义分组</option><%
          }
      %></select>
  <%}%>
  <input name="newGroup" id="edit_table_new_group_1" type="text" placeholder="填写自定义组名" style="display: none;" />
  <div id="edit_table_new_group_2" class="msgtip msginfo" style="display:none">最多支持10个关系组</div>
  </li>
  <li>
  <label>出席人数<span class="requiredico">*</span></label>
  <input type="text" value="1" name="num" id="edit_table_num"/>
  </li>
  <li>
  <label>邮箱</label>
  <input type="text"  name="email" id="edit_table_email"/>
  </li>
  <li>
  <label>电话</label>
  <input type="text"  name="phone" id="edit_table_phone"/>
  </li>
  <li>
  <label>地址</label>
  <input type="text"  name="place" id="edit_table_place"/>
  </li>
  <li>
  <label>备注</label>
  <input type="text"  name="resv" id="edit_table_resv"/>
  </li>
  <li>
  <div class="msgtip msgwarn" style="display: none;" id="edit_table_msg">亲友姓名还没写哦，亲</div>
  </li>
  <li>
  <button class="btnsave" onclick="check_submit_edit_table();">保存</button>
  <button class="btntext" onclick="document.getElementById('fancybox-close').click();">取消</button>
  </li>
  </ul>
      </form>
  </div>
  <div style="display: none;">
      <form id="delete_rela_friend_form" action="<%=baseUrl%>deleteRelaFriend" method="post">
          <input type="hidden" id="delete_rela_friend_names" name="names"/>
      </form>
  </div>
  <div style="display: none;">
      <form id="delete_help_group_form" action="<%=baseUrl%>deleteHelpGroup" method="post">
          <input type="hidden" id="delete_help_group_names" name="names"/>
      </form>
  </div>
  <div style="display: none;">
      <form id="delete_worker_form" action="<%=baseUrl%>deleteWorker" method="post">
          <input type="hidden" id="delete_worker_names" name="names"/>
      </form>
  </div>
  <div id="guestList" class="dialogInnerBox">
      <div id="guestListHead">
      </div>
      <div id="guestListBody">
      </div>
      <div id="guestListFoot">
          <ul class="formsubmit">
              <li>
                  <label>分配角色</label>
                  <form id="update_help_group_form" action="<%=baseUrl%>updateHelpGroup" method="post">
                      <input type="hidden" id="update_type_id" name="updateType"/>
                      <input type="hidden" id="update_help_group_names_id" name="names"/>
                      <input id="update_help_group_id" type="text" name="helpGroup"/>
                      <div class="msgtip msgwarn" id="update_help_group_msg"></div>
                  </form>
              </li>
              <li>
                  <button class="btnsave" onclick="update_help_group()">确定</button>
              </li>
          </ul>
      </div>
  </div>
</div>
<script type="text/javascript">
<!--
var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
//-->
  </script>
</body>
</html>
