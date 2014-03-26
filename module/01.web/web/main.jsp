<%@ page import="com.gxx.record.dao.wedisle.WedisleMainStepDao" %>
<%@ page import="java.util.*" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleMainStep" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleRelaFriendDao" %>
<%@ page import="com.gxx.record.dao.wedisle.WedisleUserStepDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
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
<script type="text/javascript" src="./Scripts/pinyin.js"></script>
<script type="text/javascript" src="./Scripts/main.js"></script>
<script type="text/javascript" src="./Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="./Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="./Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
    <script type="text/javascript">
        //所有亲戚的名字，逗号分割
        var all_names = "";
        //选择的好友列表，逗号分割
        var choose_help_names = "";
        //所有亲友
        var friends = new Array();
        //所有首页步骤信息
        var mainSteps = new Array();
        //该用户所有的步骤
        var userSteps = new Array();
        //从 级别2Id+位置索引 得到 级别3的Id，格式2id_index:3id
        var level2IndexMapLevel3 = new Array();
        //当前level2id
        var nowLevel2Id = 0;
        //当前level3id
        var nowLevel3Id = 0;

        //初始化
        $(document).ready(function() {
            //所有首页步骤信息
            mainSteps = eval(<%=WedisleUtils.getJsonFromWedisleMainSteps(WedisleMainStepDao.queryAllWedisleMainSteps())%>);
            <%
                if(isLogin){
            %>
                //初始化朋友列表
                initGuestList();
                //初始化用户步骤
                userSteps = eval(<%=WedisleUtils.getJsonFromWedisleUserSteps(WedisleUserStepDao.queryWedisleUserStepsByUserId(wedisleUser.getId()))%>);
                //刷新完成度
                refreshProgress();
            <%
                }
            %>
            //初始化该类元素
            $(".dialogBtn").fancybox();
        });

        <%
            if(isLogin){
        %>
        //初始化朋友列表
        function initGuestList(){
            //展示亲友簿
            friends = eval(<%=WedisleUtils.getJsonFromWedisleRelaFriends(WedisleRelaFriendDao.queryRelaFriendsByUserId(wedisleUser.getId()))%>);

            for(var i=0;i<friends.length;i++){
                if(all_names != ""){
                    all_names += ',';
                }
                all_names += friends[i]["name"];
            }

            //展示亲友成员
            var bodyHtml1 = "";
            var bodyHtml2 = "";
            var letterArray = new Array();
            var elseFriends = new Array();
            for(var i=0;i<friends.length;i++){
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
            for(var i=0;i<BIG_LETTERS.length;i++){
                var letterHasFriend = false;//这个字母是否有亲友
                for(var j=0;j<friends.length;j++){
                    if(BIG_LETTERS[i] == getFirstPinYin(friends[j]["name"])){
                        if(letterHasFriend == false){
                            letterHasFriend = true;
                            letterArray[letterArray.length] = BIG_LETTERS[i];
                            bodyHtml2 += "<h4 id=\"agroup\"><a name=\"help_" + BIG_LETTERS[i] + "\">" + BIG_LETTERS[i] + "</a></h4><ul class=\"guestList\">";
                        }
                        bodyHtml2 += "<li class=\"group" + (j%10+1) + "\"><input class=\"cb_help\" type=\"checkbox\" onchange=\"chooseHelp(this,'" + friends[j]["name"] + "')\"/>" + friends[j]["name"];
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
                    bodyHtml2 += "<li class=\"group" + (j%10+1) + "\"><input class=\"cb_help\" type=\"checkbox\" onchange=\"chooseHelp(this,'" + elseFriends[i]["name"] + "')\"/>" + elseFriends[i]["name"];
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
        }
        <%
            }
        %>

        //选择全部好友
        function chooseAllHelp(){
            var checked = false;
            if(all_names.split(",").length > 1){
                if(choose_help_names.split(",").length == all_names.split(",").length){
                    choose_help_names = "";
                } else {
                    choose_help_names = all_names;
                    checked = true;
                }
            } else {//只有1个好友或者没有好友
                if(choose_help_names == all_names){
                    choose_help_names = "";
                } else {
                    choose_help_names = all_names;
                    checked = true;
                }
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

        //选择亲友
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

        //分配任务选择好友后下一步
        function assignTask1(){
            if(choose_help_names == ""){
                alert("请选择任务分配对象");
                return false;
            }

            var dispatchTitle = "";
            var dispatchBeginDate = "";
            var dispatchEndDate = "";
            var dispatchContent = "";
            for(var i=0;i<userSteps.length;i++){
                if(userSteps[i]["stepId"] == nowLevel3Id){
                    dispatchTitle = userSteps[i]["dispatchTitle"];
                    dispatchBeginDate = userSteps[i]["dispatchBeginDate"];
                    dispatchEndDate = userSteps[i]["dispatchEndDate"];
                    dispatchContent = userSteps[i]["dispatchContent"];
                }
            }
            document.getElementById("dispatch_title").value = dispatchTitle;
            if("" != dispatchBeginDate){
                document.getElementById("dispatch_begin_date").value = dispatchBeginDate.substr(0, 4)
                        + "-" + dispatchBeginDate.substr(4, 2) + "-" + dispatchBeginDate.substr(6, 2);
            } else {
                document.getElementById("dispatch_begin_date").value = dispatchBeginDate;
            }
            if("" != dispatchEndDate){
                document.getElementById("dispatch_end_date").value = dispatchEndDate.substr(0, 4)
                        + "-" + dispatchEndDate.substr(4, 2) + "-" + dispatchEndDate.substr(6, 2);
            } else {
                document.getElementById("dispatch_end_date").value = dispatchEndDate;
            }
            document.getElementById("dispatch_content").value = dispatchContent;

            $("#jump_to_taskassign").click();;
        }

        //填写任务信息后下一步
        function assignTask2(){
            if(document.getElementById("dispatch_title").value == ""){
                alert("填写任务信息！");
                return false;
            }

            //准备分配模版
            prepareDispatchModel();
        }

        //刷新第二级别下的内容
        function refreshLevel2(id){
            var content = document.getElementById("flexSlider" + id).innerHTML;
            var indexStr = "flex-active\">";
            var index = 1;
            if(content.indexOf(indexStr) > -1){
                content = content.substr(content.indexOf(indexStr) + indexStr.length);
                indexStr = "<";
                if(content.indexOf(indexStr) > -1){
                    content = content.substr(0, content.indexOf(indexStr));
                }
                index = parseInt(content);
            }
            var beginStr = id + "_" + index + "=";
            var level3Id = 0;
            for(var i=0;i<level2IndexMapLevel3.length;i++){
                if(level2IndexMapLevel3[i].indexOf(beginStr) > -1){
                    level3Id = level2IndexMapLevel3[i].substr(beginStr.length);
                    break;
                }
            }
            //赋值当前level2id
            nowLevel2Id = id;
            //赋值当前level3id
            nowLevel3Id = level3Id;

            //分配好友
            var dispatchFriends = "";
            var dispatchTitle = "";
            var dispatchBeginDate = "";
            var dispatchEndDate = "";
            var dispatchContent = "";
            var dispatchHtml = "";
            var isDone = false;
            for(var i=0;i<userSteps.length;i++){
                if(userSteps[i]["stepId"] == level3Id){
                    dispatchFriends = userSteps[i]["dispatchFriends"];
                    dispatchTitle = userSteps[i]["dispatchTitle"];
                    dispatchBeginDate = userSteps[i]["dispatchBeginDate"];
                    dispatchEndDate = userSteps[i]["dispatchEndDate"];
                    dispatchContent = userSteps[i]["dispatchContent"];
                    dispatchHtml = userSteps[i]["dispatchHtml"];
                    isDone = userSteps[i]["isDone"];
                }
            }
            if("" == dispatchFriends){//未分配
                document.getElementById("taskAssign" + id).innerHTML = "<a onclick=\"initSelectFriends()\" href=\"#guestList\" class=\"dialogBtn\">分配</a>";
                //好友都不选中
                choose_help_names = "";
                var boxes = document.getElementsByClassName("cb_help");
                for(var i=0;i<boxes.length;i++){
                    boxes[i].checked = false;
                }
                document.getElementById("dispatch_title").value = "";
                document.getElementById("dispatch_begin_date").value = "";
                document.getElementById("dispatch_end_date").value = "";
                document.getElementById("dispatch_content").value = "";
            } else {//已分配
                document.getElementById("taskAssign" + id).innerHTML = "<a href=\"#taskassigned\" class=\"dialogBtn\">已分配</a>";
                //更新已分配用户描述
                document.getElementById("task_assigned_head").innerHTML = "任务已成功分配给如下人员，"
                        + "你可以再次使用邮件或QQ向对方发送通知。<a id=\"preview_task\" target=\"_blank\" href=\""
                        + dispatchHtml + "\">预览</a>";

                //更新分配的好友
                var html = "";
                //<li><b>小白免</b><i>13872637483</i><a class="img_mail imgIco" href="#">&nbsp;</a><a class="img_qq imgIco" href="#">&nbsp;</a></li>
                var names = dispatchFriends.split(",");

                for(var i=0;i<names.length;i++){
                    html += "<li><b>" + names[i] + "</b>";

                    var phone = "<i style=\"width: 130px;\">未设置</i>";
                    var email = "<a class=\"img_no_mail imgIco\" href=\"javascript: alert('未设置邮箱！');\">&nbsp;</a>";
                    for(var j=0;j<friends.length;j++){
                        if(friends[j]["name"] == names[i]){
                            if("" != friends[j]["phone"]){
                                phone = "<i style=\"width: 130px;\">" + friends[j]["phone"] + "</i>";
                            }
                            if("" != friends[j]["email"]){
                                //email = friends[j]["email"];
                                email = "<a class=\"img_mail imgIco\" href=\"javascript: sendEmail('"
                                        + dispatchTitle + "(婚礼岛任务调度系统)','" + friends[j]["email"]
                                        + "','" + dispatchHtml + "')\">&nbsp;</a>";
                            }
                        }
                    }
                    //url不能发送localhost:80得用.replace("localhost:80","www.wedisle.com")
                    var qqUrl = "http://connect.qq.com/widget/shareqq/index.html?" +
                            "url=" + dispatchHtml + "&desc=" + dispatchTitle + "(婚礼岛任务调度系统)&" +
                            "summary=" + dispatchTitle + "&pics=http://www.wedisle.com/favicon.ico"
                    var qq = "<a class=\"img_qq imgIco\" href=\"" + qqUrl + "\" target=\"_blank\">&nbsp;</a></li>";
                    html += phone + email + qq;
                }
                document.getElementById("task_owners").innerHTML = html;
            }

            //查文章id
            var articleId = 0;
            for(var i=0;i<mainSteps.length;i++){
                if(mainSteps[i]["id"] == level3Id){
                    articleId = mainSteps[i]["articleId"];
                }
            }
            document.getElementById("taskGuide" + id).innerHTML = "<a href=\"<%=PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL)%>wedding/ztree/showArticle.jsp?id="+articleId+"\" class=\"dialogBtn\">指导</a>";

            if(isDone){
                document.getElementById("btn" + nowLevel3Id).innerHTML = "已&nbsp;&nbsp;完&nbsp;&nbsp;成";
                document.getElementById("btn" + nowLevel3Id).className = "btn_idone";
            } else {
                document.getElementById("btn" + nowLevel3Id).innerHTML = "完&nbsp;&nbsp;&nbsp;&nbsp;成";
                document.getElementById("btn" + nowLevel3Id).className = "btn_idoit";
            }

            //初始化该类元素
            $(".dialogBtn").fancybox();
        }

        //准备分配模版
        function prepareDispatchModel(){
            var dispatchTitle = document.getElementById("dispatch_title").value;
            var dispatchBeginDate = document.getElementById("dispatch_begin_date").value;
            var dispatchEndDate = document.getElementById("dispatch_end_date").value;
            var dispatchContent = document.getElementById("dispatch_content").value;
            var SUCCESS_STR = "success";//成功编码
            $.ajax({
                type: "post",
                async: false,
                url: "<%=baseUrl%>dispatch/dispatch.jsp",
                data: "dispatchTitle=" + dispatchTitle + "&dispatchBeginDate=" + dispatchBeginDate
                        + "&dispatchEndDate=" + dispatchEndDate + "&dispatchContent=" + dispatchContent
                        + "&dispatchFriends=" + choose_help_names + "&stepId=" + nowLevel3Id,
                success: function(data, textStatus){
                    if((SUCCESS_STR == textStatus) && (null != data))
                    {
                        data = eval("(" + data + ")");
                        if(!data["isSuccess"]){
                            alert(data["message"]);
                            return;
                        }

                        //更新预览连接
                        document.getElementById("task_assigned_head").innerHTML = "任务分配模版已经在后台生成，"
                                + "你可以使用邮件或者QQ向对方发送通知。<a id=\"preview_task\" target=\"_blank\" href=\""
                                + data["htmlUrl"] + "\">预览</a>";

                        //更新分配的好友
                        var html = "";
                        //<li><b>小白免</b><i>13872637483</i><a class="img_mail imgIco" href="#">&nbsp;</a><a class="img_qq imgIco" href="#">&nbsp;</a></li>
                        var names = choose_help_names.split(",");

                        for(var i=0;i<names.length;i++){
                            html += "<li><b>" + names[i] + "</b>";

                            var phone = "<i style=\"width: 130px;\">未设置</i>";
                            var email = "<a class=\"img_no_mail imgIco\" href=\"javascript: alert('未设置邮箱！');\">&nbsp;</a>";
                            for(var j=0;j<friends.length;j++){
                                if(friends[j]["name"] == names[i]){
                                    if("" != friends[j]["phone"]){
                                        phone = "<i style=\"width: 130px;\">" + friends[j]["phone"] + "</i>";
                                    }
                                    if("" != friends[j]["email"]){
                                        //email = friends[j]["email"];
                                        email = "<a class=\"img_mail imgIco\" href=\"javascript: sendEmail('"
                                                + dispatchTitle + "(婚礼岛任务调度系统)','" + friends[j]["email"]
                                                + "','" + data["htmlUrl"] + "')\">&nbsp;</a>";
                                    }
                                }
                            }
                            //url不能发送localhost:80得用.replace("localhost:80","www.wedisle.com")
                            var qqUrl = "http://connect.qq.com/widget/shareqq/index.html?" +
                                    "url=" + data["htmlUrl"] + "&desc=" + dispatchTitle + "(婚礼岛任务调度系统)&" +
                                    "summary=" + dispatchTitle + "&pics=http://www.wedisle.com/favicon.ico"
                            var qq = "<a class=\"img_qq imgIco\" href=\"" + qqUrl + "\" target=\"_blank\">&nbsp;</a></li>";
                            html += phone + email + qq;
                        }
                        document.getElementById("task_owners").innerHTML = html;

                        //更新
                        for(var i=0;i<userSteps.length;i++){
                            if(userSteps[i]["stepId"] == nowLevel3Id){
                                userSteps[i]["dispatchFriends"] = choose_help_names;
                                userSteps[i]["dispatchTitle"] = dispatchTitle;
                                userSteps[i]["dispatchBeginDate"] = dispatchBeginDate;
                                userSteps[i]["dispatchEndDate"] = dispatchEndDate;
                                userSteps[i]["dispatchContent"] = dispatchContent;
                                userSteps[i]["dispatchHtml"] = data["htmlUrl"];
                            }
                        }

                        //更新成已分配
                        document.getElementById("taskAssign" + nowLevel2Id).innerHTML = "<a href=\"#taskassigned\" class=\"dialogBtn\">已分配</a>";

                        //初始化该类元素
                        $(".dialogBtn").fancybox();

                        $("#jump_to_taskassigned").click();
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

        //发送邮件
        function sendEmail(title, emails, htmlUrl){
            var SUCCESS_STR = "success";//成功编码
            $.ajax({
                type: "post",
                async: false,
                url: "<%=baseUrl%>dispatch/sendEmail.jsp",
                data: "title=" + title + "&emails=" + emails + "&htmlUrl=" + htmlUrl,
                success: function(data, textStatus){
                    if((SUCCESS_STR == textStatus) && (null != data))
                    {
                        data = eval("(" + data + ")");
                        if(!data["isSuccess"]){
                            alert(data["message"]);
                            return;
                        }
                        alert(data["message"]);
                        return;
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

        //初始化选择被分配任务的好友
        function initSelectFriends()
        {
            var dispatchFriends = "";
            for(var i=0;i<userSteps.length;i++){
                if(userSteps[i]["stepId"] == nowLevel3Id){
                    dispatchFriends = userSteps[i]["dispatchFriends"];
                }
            }
            var boxes = document.getElementsByClassName("cb_help");
            for(var i=0;i<boxes.length;i++){
                if(("," + dispatchFriends + ",").indexOf("," + boxes[i].parentNode.innerText + ",") > -1) {
                    boxes[i].checked = true;
                } else {
                    boxes[i].checked = false;
                }
            }
            choose_help_names = dispatchFriends;
        }

        //点击完成按钮
        function doneTask(t, stepId){
            //已完成
            if(t.innerText.indexOf("已") > -1){
                return;
            }
            //未完成，点击成已完成
            var SUCCESS_STR = "success";//成功编码
            $.ajax({
                type: "post",
                async: false,
                url: "<%=baseUrl%>dispatch/doneTask.jsp",
                data: "stepId=" + stepId,
                success: function(data, textStatus){
                    if((SUCCESS_STR == textStatus) && (null != data))
                    {
                        data = eval("(" + data + ")");
                        if(!data["isSuccess"]){
                            alert(data["message"]);
                            return;
                        }
                        //更新
                        for(var i=0;i<userSteps.length;i++){
                            if(userSteps[i]["stepId"] == stepId){
                                userSteps[i]["isDone"] = true;
                            }
                        }
                        document.getElementById("btn" + nowLevel3Id).innerHTML = "已&nbsp;&nbsp;完&nbsp;&nbsp;成";
                        document.getElementById("btn" + nowLevel3Id).className = "btn_idone";
                        //刷新完成度
                        refreshProgress();
                        return;
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

        //刷新完成度
        function refreshProgress(){
            var countStep = userSteps.length;
            var countDoneStep = 0;
            for(var i=0;i<userSteps.length;i++){
                if(userSteps[i]["isDone"]){
                    countDoneStep ++;
                }
            }

            var html = "完成度<br/><b class=\"num\">" + parseInt(countDoneStep*100/countStep) + "%</b>"
                    + "<div class=\"taskProgressPanel\">"
                    + "<ul>"
                    + "<li>全部任务：" + countStep + "</li>"
                    + "<li>已完成：" + countDoneStep + "</li>"
                    + "<li>未完成：" + (countStep - countDoneStep) + "</li>"
                    + "</ul>"
                    + "</div>";
            var boxes = document.getElementsByClassName("taskfin");
            for(var i=0;i<boxes.length;i++){
                boxes[i].innerHTML = html;
            }
        }

        /**
         * 初始化该类元素
         * 因为不知道某些情况下点击弹出去框来
         * 貌似效果不是很好 x
         */
        function clickBody(){
            $(".dialogBtn").fancybox();
        }
    </script>
</head>
<body onclick="clickBody()">
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
  <div class="title"> <span class="logo"><img src="images/logo.png" alt="My Wonderland" /></span></div>
</div>
<div id="cc_menu" class="cc_menu">
  <div class="btmBorder"></div>
  <div class="cc_item" style="z-index:5;">
    <a href="javascript:void(0)"><span class="cc_title" id="nav1">婚礼<br />
    筹划</span></a>
    <div class="cc_submenu">
      <ul class="smenu1">
          <%
              List<WedisleMainStep> steps = WedisleMainStepDao.queryLevel2ByLevel1Id(1);
              for(WedisleMainStep step : steps){
          %>
          <li onclick="refreshLevel2(<%=step.getId()%>)" class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
          <%
              }
          %>
      </ul>
    </div>
  </div>
  <div class="cc_item" style="z-index:4;">
    <a href="javascript:void(0)"><span class="cc_title" id="nav2">婚纱<br />
    摄影</span></a>
    <div class="cc_submenu">
      <ul class="smenu2">
          <%
              steps = WedisleMainStepDao.queryLevel2ByLevel1Id(2);
              for(WedisleMainStep step : steps){
          %>
          <li onclick="refreshLevel2(<%=step.getId()%>)" class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
          <%
              }
          %>
      </ul>
    </div>
  </div>
  <div class="cc_item" style="z-index:3;">
    <a href="javascript:void(0)"><span class="cc_title" id="nav3">酒店<br />
    婚庆</span></a>
    <div class="cc_submenu">
      <ul class="smenu3">
          <%
              steps = WedisleMainStepDao.queryLevel2ByLevel1Id(3);
              for(WedisleMainStep step : steps){
          %>
          <li onclick="refreshLevel2(<%=step.getId()%>)" class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
          <%
              }
          %>
      </ul>
    </div>
  </div>
  <div class="cc_item" style="z-index:2;">
    <a href="javascript:void(0)"><span class="cc_title" id="nav4">婚品<br />
    布置</span></a>
    <div class="cc_submenu">
      <ul class="smenu4">
          <%
              steps = WedisleMainStepDao.queryLevel2ByLevel1Id(4);
              for(WedisleMainStep step : steps){
          %>
          <li onclick="refreshLevel2(<%=step.getId()%>)" class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
          <%
              }
          %>
      </ul>
    </div>
  </div>
  <div class="cc_item" style="z-index:1;">
    <a href="javascript:void(0)"><span class="cc_title" id="nav5">大婚<br />
    一周</span></a>
    <div class="cc_submenu">
      <ul class="smenu5">
          <%
              steps = WedisleMainStepDao.queryLevel2ByLevel1Id(5);
              for(WedisleMainStep step : steps){
          %>
          <li onclick="refreshLevel2(<%=step.getId()%>)" class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
          <%
              }
          %>
      </ul>
    </div>
  </div>

  <div id="cc_content" class="cc_content"> <span id="cc_back" class="cc_back">&lt; back</span>
      <%
          steps = WedisleMainStepDao.queryAllWedisleMainSteps();
          for(WedisleMainStep step : steps){
              if(step.getLevel() == 3){continue;}
      %>
      <div class="cc_content_<%=step.getId()%> cunit">
          <div class="taskcontentbox">
              <ul class="taskLeftbar">
                  <li class="taskfin">完成度<br/><b class="num">0%</b>
                      <div class="taskProgressPanel">
                          <ul>
                              <li>全部任务：100</li>
                              <li>已完成：0</li>
                              <li>未完成：100</li>
                          </ul>
                      </div>
                  </li>
                  <li class="taskalert alertOn"><a href="<%=PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL)%>remind.jsp" class="dialogBtn">提醒</a></li>
                  <li class="taskassign assignOn" id="taskAssign<%=step.getId()%>"><a onclick="initSelectFriends()" href="#guestList" class="dialogBtn">分配</a></li>
                  <li class="taskguide" id="taskGuide<%=step.getId()%>"><a href="<%=PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL)%>wedding/ztree/showArticle.jsp?id=<%=step.getArticleId()%>" class="dialogBtn">指导</a></li>
                  <li class="tasktool">工具</b>
                      <div class="taskProgressPanel">
                          <ul>
                              <li><a target="_blank" href="<%=baseUrl%>tool/tool_map.jsp">登记查询</a></li>
                              <li><a target="_blank" href="<%=baseUrl%>tool/tool_calendar.jsp">黄道吉日</a></li>
                              <li><a target="_blank" href="<%=baseUrl%>tool/tool_absense.jsp">婚假建议</a></li>
                              <li><a target="_blank" href="<%=baseUrl%>tool/tool_budget.jsp">婚礼预算</a></li>
                              <li><a target="_blank" href="<%=baseUrl%>tool/tool_seat.jsp">座位安排</a></li>
                          </ul>
                      </div>
                  </li>
                  <li class="taskbudget" style="cursor: pointer;" onclick="window.open ('<%=baseUrl%>tool/tool_budget.jsp')">预算<br/><b class="num">$</b>
                  </li>
              </ul>
              <div class="flexslider taskcontent" id="flexSlider<%=step.getId()%>" onclick="refreshLevel2(<%=step.getId()%>)">
                  <ul class="slides">
                      <%
                          List<WedisleMainStep> tempSteps = WedisleMainStepDao.queryLevel3ByLevel2Id(step.getId());
                          for(int i=0;i<tempSteps.size();i++){
                              WedisleMainStep tempStep = tempSteps.get(i);
                      %>
                          <script type="text/javascript">
                              level2IndexMapLevel3[level2IndexMapLevel3.length] = "<%=step.getId()%>_<%=i+1%>=<%=tempStep.getId()%>";
                          </script>
                      <li>
                          <h3><%=step.getName()%>（<%=i+1%>/<%=tempSteps.size()%>）</h3>
                          <h2><%=tempStep.getName()%></h2>
                          <button class="btn_idoit" id="btn<%=tempStep.getId()%>" onclick="doneTask(this,<%=tempStep.getId()%>)">完&nbsp;&nbsp;&nbsp;&nbsp;成</button>
                      </li>
                      <%
                          }
                      %>
                  </ul>
              </div>
              <div class="cf"></div>
          </div>
      </div>
      <%
          }
      %>
  </div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
  <a href="#">关于婚礼岛</a></p>
<div style="display:none">
      <div id="guestList" class="dialogInnerBox">
          <div>
            <h3>在下方选择需要分配这个任务的小伙伴<a href="<%=baseUrl%>main/friends_book.jsp" class="linkRight">管理帮帮团</a></h3>
          </div>
          <div id="guestListBody">
          </div>
          <a id="jump_to_taskassign" class="dialogBtn" href="#taskassign"></a>
          <button class="btnsave" onclick="assignTask1()">下一步</button>
      </div>

    <div id="taskassign" class="dialogInnerBox">
        <h3>填写任务信息</h3>
        <ul class="formedit">
            <li>
                <label>任务名称<span class="requiredico">*</span></label>
                <input id="dispatch_title" type="text" value="" />
            </li>
            <li>
                <label>起始时间</label>
                <input id="dispatch_begin_date" type="date" />
            </li>
            <li>
                <label>结束时间</label>
                <input id="dispatch_end_date" type="date" />
            </li>
            <li>
                <label>任务内容</label>
                <textarea id="dispatch_content"></textarea>
            </li>
            <li>
                <a id="jump_to_taskassigned" class="dialogBtn" href="#taskassigned"></a>
                <button class="btnsave" onclick="assignTask2()">下一步</button>
            </li>
        </ul>
    </div>

    <div id="taskassigned" class="dialogInnerBox">
        <div class="msgtip msgsuc" id="task_assigned_head">任务分配模版已经在后台生成，你可以使用邮件或者QQ向对方发送通知。<a id="preview_task" target="_blank" href="#">预览</a></div>
        <ul class="taskOwners" id="task_owners">
            <li><b>小白免</b><i>13872637483</i><a class="img_mail imgIco" href="#">&nbsp;</a><a class="img_qq imgIco" href="#">&nbsp;</a></li>
            <li><b>马上结</b><i>13872637483</i><a class="img_mail imgIco" href="#">&nbsp;</a><a class="img_qq imgIco" href="#">&nbsp;</a></li>
        </ul>
        <a class="dialogBtn" onclick="initSelectFriends()" href="#guestList">修改分配人员 ></a>
    </div>

    <div id="guestList2" class="dialogInnerBox"><!-- todo -->
        <div id="guestListFoot">
            <ul class="formsubmit">
                <li style="text-align: center;">
                    <label>分配任务</label>
                    <form name="taskForm" action="<%=baseUrl%>distribute.jsp" method="post">
                        <table>
                            <tr>
                                <td>任务主题：</td><td><input type="text" name="title" value="帮我拿婚纱"/></td>
                            </tr>
                            <tr>
                                <td>分配对象：</td><td><input type="text" name="friends" value="419066357@qq.com,136192182@qq.com"/></td>
                            </tr>
                            <tr>
                                <td>开始时间：</td><td><input type="text" name="beginTime" value="2014年2月23日"/></td>
                            </tr>
                            <tr>
                                <td>结束时间：</td><td><input type="text" name="endTime" value="2014年2月30日"/></td>
                            </tr>
                            <tr>
                                <td>任务内容：</td>
                                <td>
                                    <textarea rows="10" cols="10" name="content">请在这个时间段内帮我拿婚纱，多谢~</textarea>
                                </td>
                            </tr>
                        </table>
                    </form>
                </li>
                <li style="text-align: center;">
                    <button class="btnsave" onclick="document.forms['taskForm'].submit();">确定</button>
                </li>
            </ul>
        </div>
    </div>
</div>
<script src="./Scripts/jquery.flexslider-min.js"></script>
<script type="text/javascript" charset="utf-8"> 
     $(window).load(function() { 
       $('.flexslider').flexslider({ 
            prevText: "&lt;",
            nextText: "&gt;",
            slideshow: false
        }); 
     }); 
</script>
</body>
</html>
