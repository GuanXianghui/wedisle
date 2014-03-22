<%@ page import="com.gxx.record.dao.wedisle.WedisleMainStepDao" %>
<%@ page import="java.util.*" %>
<%@ page import="com.gxx.record.entities.wedisle.WedisleMainStep" %>
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
<script type="text/javascript" src="./Scripts/main.js"></script>
<script type="text/javascript" src="./Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="./Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="./Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
<script type="text/javascript">
		$(document).ready(function() {
			$(".dialogBtn").fancybox();
		});
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
  <div class="title"> <span class="logo"><img src="images/logo.png" alt="My Wonderland" /></span> <span class="pagetitle">记录我的历程，<a href="#">创建我的主页</a></span> </div>
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
          <li class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
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
          <li class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
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
          <li class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
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
          <li class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
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
          <li class="cc_content_<%=step.getId()%>"><%=step.getName()%></li>
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
                  <li class="taskfin">完成度<br/><b class="num">22%</b>
                      <div class="taskProgressPanel">
                          <ul>
                              <li>全部任务：222</li>
                              <li>已完成：22</li>
                              <li>未完成：180</li>
                          </ul>
                      </div>
                  </li>
                  <li class="taskalert alertOn"><a href="<%=PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL)%>remind.jsp" class="dialogBtn">提醒</a></li>
                  <li class="taskassign assignOn"><a href="#guestList" class="dialogBtn">分配</a></li>
                  <li class="taskguide"><a href="<%=PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL)%>wedding/ztree/showArticle.jsp?id=<%=step.getArticleId()%>" class="dialogBtn">指导</a></li>
                  <li class="tasktool">工具</b>
                      <div class="taskProgressPanel">
                          <ul>
                              <li><a href="#">预算计算器</a></li>
                              <li><a href="#">座位安排</a></li>
                          </ul>
                      </div>
                  </li>
                  <li class="taskbudget">预算<br/><b class="num">$</b>
                  </li>
              </ul>
              <div class="flexslider taskcontent">
                  <ul class="slides">
                      <%
                          List<WedisleMainStep> tempSteps = WedisleMainStepDao.queryLevel3ByLevel2Id(step.getId());
                          for(int i=0;i<tempSteps.size();i++){
                              WedisleMainStep tempStep = tempSteps.get(i);
                      %>
                      <li>
                          <h3><%=step.getName()%>（<%=i+1%>/<%=tempSteps.size()%>）</h3>
                          <h2><%=tempStep.getName()%></h2>
                          <button class="btn_idoit">完&nbsp;&nbsp;&nbsp;&nbsp;成</button>
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
  <h3>目前这个任务已分配给<strong>小关</strong>，在下方选择和修改需要分配这个任务的小伙伴<a href="#" class="linkRight">管理帮帮团</a></h3>
  <p class="groupabc"><a href="#">全部</a><a href="#">A</a><a href="#">B</a><a href="#">C</a><a href="#">D</a></p>
  <h4 id="agroup">A</h4>
    <ul class="guestList">
      <li class="group4" title="女方亲属"><input name="" type="checkbox" value="" /><label for="">大舅</label><p class="taskState">未分配任务</p></li>
      <li class="group1" title="共同好友"><input name="" type="checkbox" value="" checked="checked" /><label for="">小白免</label><p class="taskState">已分配<span class="num">4</span>个任务</p></li>
      <li class="group3"><input name="" type="checkbox" value="" /><label for="">小姑</label><p class="taskState">未分配任务</p></li>
      <li class="group3"><input name="" type="checkbox" value="" checked="checked" /><label for="">史密斯夫妇</label><p class="taskState">已分配<span class="num">1</span>个任务</p></li>
      <li class="group1" title="外人"><input name="" type="checkbox" value="" /><label for="">奥巴马夫妇</label><p class="taskState">未分配任务</p></li>
      <li class="group9" title="男方亲属"><input name="" type="checkbox" value="" /><label for="">男方直系亲属</label><p class="taskState">未分配任务</p></li>
      <li class="group1" title="女方亲属"><input name="" type="checkbox" value="" /><label for="">女方直系亲属</label><p class="taskState">未分配任务</p></li>
    </ul>
      <h4 id="bgroup">B</h4>
      <ul class="guestList">
      <li class="group9" title="男方亲属"><input name="" type="checkbox" value="" /><label for="">男方直系亲属</label><p class="taskState">未分配任务</p></li>
      <li class="group1" title="女方亲属"><input name="" type="checkbox" value="" /><label for="">女方直系亲属</label><p class="taskState">未分配任务</p></li>
      <li class="group4" title="女方亲属"><input name="" type="checkbox" value="" /><label for="">大舅</label><p class="taskState">未分配任务</p></li>
      <li class="group3"><input name="" type="checkbox" value="" /><label for="">小姑</label><p class="taskState">未分配任务</p></li>
      <li class="group1" title="共同好友"><input name="" type="checkbox" value="" checked="checked" /><label for="">小白免</label><p class="taskState">已分配<span class="num">4</span>个任务</p></li>
      <li class="group3"><input name="" type="checkbox" value="" checked="checked" /><label for="">史密斯夫妇</label><p class="taskState">已分配<span class="num">1</span>个任务</p></li>
      <li class="group1" title="外人"><input name="" type="checkbox" value="" /><label for="">奥巴马夫妇</label><p class="taskState">未分配任务</p></li>
      </ul>
      <h4 id="cgroup">C</h4>
      <ul class="guestList">
      <li class="group4" title="女方亲属"><input name="" type="checkbox" value="" /><label for="">大舅</label><p class="taskState">未分配任务</p></li>
      <li class="group1" title="共同好友"><input name="" type="checkbox" value="" checked="checked" /><label for="">小白免</label><p class="taskState">已分配<span class="num">4</span>个任务</p></li>
      <li class="group1" title="外人"><input name="" type="checkbox" value="" /><label for="">奥巴马夫妇</label><p class="taskState">未分配任务</p></li>
      <li class="group9" title="男方亲属"><input name="" type="checkbox" value="" /><label for="">男方直系亲属</label><p class="taskState">未分配任务</p></li>
      <li class="group3"><input name="" type="checkbox" value="" /><label for="">小姑</label><p class="taskState">未分配任务</p></li>
      <li class="group3"><input name="" type="checkbox" value="" checked="checked" /><label for="">史密斯夫妇</label><p class="taskState">已分配<span class="num">1</span>个任务</p></li>
      <li class="group1" title="女方亲属"><input name="" type="checkbox" value="" /><label for="">女方直系亲属</label><p class="taskState">未分配任务</p></li>
      </ul>
      <h4 id="dgroup">D</h4>
      <ul class="guestList">
      <li class="group4" title="女方亲属"><input name="" type="checkbox" value="" /><label for="">大舅</label><p class="taskState">未分配任务</p></li>
      <li class="group1" title="女方亲属"><input name="" type="checkbox" value="" /><label for="">女方直系亲属</label><p class="taskState">未分配任务</p></li>
      <li class="group3"><input name="" type="checkbox" value="" /><label for="">小姑</label><p class="taskState">未分配任务</p></li>
      <li class="group3"><input name="" type="checkbox" value="" checked="checked" /><label for="">史密斯夫妇</label><p class="taskState">已分配<span class="num">1</span>个任务</p></li>
      <li class="group1" title="外人"><input name="" type="checkbox" value="" /><label for="">奥巴马夫妇</label><p class="taskState">未分配任务</p></li>
      <li class="group1" title="共同好友"><input name="" type="checkbox" value="" checked="checked" /><label for="">小白免</label><p class="taskState">已分配<span class="num">4</span>个任务</p></li>
      <li class="group9" title="男方亲属"><input name="" type="checkbox" value="" /><label for="">男方直系亲属</label><p class="taskState">未分配任务</p></li>
      </ul>
    <a href="#guestList2" class="dialogBtn"><button class="btnAddguest">确定</button></a>
  </div>

    <div id="guestList2" class="dialogInnerBox">
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
