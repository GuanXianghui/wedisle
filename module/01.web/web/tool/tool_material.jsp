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
		$(document).ready(function() {
			$(".dialogBtn").fancybox();
		});
        function preview()
        {
            bdhtml=window.document.body.innerHTML;
            sprnstr="<!--startprint-->";
            eprnstr="<!--endprint-->";
            prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17);
            prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
            window.document.body.innerHTML=prnhtml;
            window.print();
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
<div id="tool_map" class="toolbox">
<h1>材料清单</h1>
<p><a class="icolink qrcode dialogBtn" href="../images/mp_qrcode.png" target="_blank">扫描二维码</a><a class="icolink print" href="javascript: preview()" target="_blank">打印</a></p>
    <!--startprint-->
    <table width="100%" border="0" cellspacing="1" cellpadding="0" class="materialTable">
  <tr>
    <td colspan="7"><h2>结婚登记确认表</h2></td>
    </tr>
  <tr>
    <td colspan="7"><b>资格校对</b></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="3"><b>要求</b></td>
    <td colspan="2"><b>满足？</b></td>
    <td rowspan="5">-</td>
  </tr>
  <tr>
    <td>1</td>
    <td colspan="3">年龄（周岁）：男 &gt;= 22、女 &gt;= 20</td>
    <td colspan="2"><label>
      <input type="checkbox" name="checkbox" id="checkbox" />
    </label></td>
    </tr>
  <tr>
    <td>2</td>
    <td colspan="3">未患有医学上认为不应当结婚的疾病</td>
    <td colspan="2"><label>
      <input type="checkbox" name="checkbox2" id="checkbox2" />
    </label></td>
    </tr>
  <tr>
    <td>3</td>
    <td colspan="3">双方非直系血亲或者三代以内旁系血亲</td>
    <td colspan="2"><label>
      <input type="checkbox" name="checkbox3" id="checkbox3" />
    </label></td>
    </tr>
  <tr>
    <td>4</td>
    <td colspan="3">双方自愿和无配偶</td>
    <td colspan="2"><label>
      <input type="checkbox" name="checkbox4" id="checkbox4" />
    </label></td>
    </tr>
  <tr>
    <td colspan="7"><b>办理规范校对</b></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="3"><b>校对</b></td>
    <td colspan="2"><b>满足</b></td>
    <td><b>办理前确认</b></td>
  </tr>
  <tr>
    <td>1</td>
    <td colspan="3">是否一方常住户口所在地的婚姻登记机关申请</td>
    <td colspan="2"><label>
      <input type="checkbox" name="checkbox5" id="checkbox5" />
    </label></td>
    <td><label>
      <input type="checkbox" name="checkbox8" id="checkbox8" />
    </label></td>
  </tr>
  <tr>
    <td>2</td>
    <td colspan="3">是否双方亲自办理（不得委托他人代理）</td>
    <td colspan="2"><label>
      <input type="checkbox" name="checkbox6" id="checkbox6" />
    </label></td>
    <td><label>
      <input type="checkbox" name="checkbox9" id="checkbox9" />
    </label></td>
  </tr>
  <tr>
    <td>3</td>
    <td colspan="3">是否在办理时间内</td>
    <td colspan="2"><label>
      <input type="checkbox" name="checkbox7" id="checkbox7" />
    </label></td>
    <td><label>
      <input type="checkbox" name="checkbox10" id="checkbox10" />
    </label></td>
  </tr>
  <tr>
    <td colspan="7"><b>材料校对</b></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td colspan="2"><b>材料</b></td>
    <td><b>注意事项</b></td>
    <td><b>男方？</b></td>
    <td><b>女方？</b></td>
    <td><b>办理前确认</b></td>
  </tr>
  <tr>
    <td>1</td>
    <td>户口本</td>
    <td>原件</td>
    <td rowspan="4">-</td>
    <td><input type="checkbox" name="checkbox11" id="checkbox11" /></td>
    <td><input type="checkbox" name="checkbox12" id="checkbox12" /></td>
    <td><input type="checkbox" name="checkbox13" id="checkbox13" /></td>
  </tr>
  <tr>
    <td>2</td>
    <td>户口本</td>
    <td>复印件1份</td>
    <td><input type="checkbox" name="checkbox18" id="checkbox18" /></td>
    <td><input type="checkbox" name="checkbox17" id="checkbox17" /></td>
    <td><input type="checkbox" name="checkbox14" id="checkbox14" /></td>
  </tr>
  <tr>
    <td>3</td>
    <td>身份证</td>
    <td>原件</td>
    <td><input type="checkbox" name="checkbox19" id="checkbox19" /></td>
    <td><input type="checkbox" name="checkbox16" id="checkbox16" /></td>
    <td><input type="checkbox" name="checkbox15" id="checkbox15" /></td>
  </tr>
  <tr>
    <td>4</td>
    <td>身份证</td>
    <td>复印件1份</td>
    <td><input type="checkbox" name="checkbox20" id="checkbox20" /></td>
    <td><input type="checkbox" name="checkbox21" id="checkbox21" /></td>
    <td><input type="checkbox" name="checkbox22" id="checkbox22" /></td>
  </tr>
  <tr>
    <td>5</td>
    <td>2寸合影证件照</td>
    <td>3张</td>
    <td>现场可拍</td>
    <td colspan="2"><input type="checkbox" name="checkbox25" id="checkbox25" /></td>
    <td><input type="checkbox" name="checkbox23" id="checkbox23" /></td>
  </tr>
  <tr>
    <td>6</td>
    <td>零钱</td>
    <td>-</td>
    <td>-</td>
    <td colspan="2"><input type="checkbox" name="checkbox26" id="checkbox26" /></td>
    <td><input type="checkbox" name="checkbox24" id="checkbox24" /></td>
  </tr>
  <tr>
    <td colspan="7"><b>办理流程</b></td>
    </tr>
  <tr>
    <td colspan="7"><ol class="pregress"><li>当事人出示证件</li><li>当事人填写《申请结婚登记声明书》</li><li>婚姻登记机关审查、颁发《结婚证》</li></ol></td>
    </tr>
  <tr>
    <td colspan="7"><b>备忘</b></td>
    </tr>
  <tr>
    <td colspan="7">&nbsp;</td>
    </tr>
</table>
    <!--endprint-->

</div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
  <a href="#">关于婚礼岛</a></p>
<div style="display:none">
  
</div>
</body>
</html>
