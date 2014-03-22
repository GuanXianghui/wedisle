<%@ page import="org.apache.struts2.ServletActionContext" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="日志,心声,人生经历,7-blog"/>
<meta name="Description" content="My Dairy weekly" />
<title>用户管理平台-婚礼岛</title>
<link rel="shortcut icon" type="image/x-icon" href="http://7swing.com/favicon.ico" />
<link href="style/homePlus.css" rel="stylesheet" type="text/css" />
<link href="style/cp.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="Scripts/jquery-min.js"></script>
<script type="text/javascript" src="Scripts/comm.js"></script>
<script type="text/javascript" src="Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js"></script>
<script type="text/javascript" src="Scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
<link rel="stylesheet" type="text/css" href="Scripts/fancybox/jquery.fancybox-1.3.4.css" media="screen" />
    <%
        String htmlContent = "<script type=\"text/javascript\">\n" +
                "    // 定义日期格式化\n" +
                "    defineDateFormat();\n" +
                "    // 定义日期格式化\n" +
                "    function defineDateFormat(){\n" +
                "        // 对Date的扩展，将 Date 转化为指定格式的String\n" +
                "        // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，\n" +
                "        // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)\n" +
                "        // 例子：\n" +
                "        // (new Date()).format(\"yyyy-MM-dd hh:mm:ss.S\") ==> 2006-07-02 08:09:04.423\n" +
                "        // (new Date()).format(\"yyyy-M-d h:m:s.S\")      ==> 2006-7-2 8:9:4.18\n" +
                "        Date.prototype.format = function(fmt)\n" +
                "        {\n" +
                "            var o = {\n" +
                "                \"M+\" : this.getMonth()+1,                 //月份\n" +
                "                \"d+\" : this.getDate(),                    //日\n" +
                "                \"h+\" : this.getHours(),                   //小时\n" +
                "                \"m+\" : this.getMinutes(),                 //分\n" +
                "                \"s+\" : this.getSeconds(),                 //秒\n" +
                "                \"q+\" : Math.floor((this.getMonth()+3)/3), //季度\n" +
                "                \"S\"  : this.getMilliseconds()             //毫秒\n" +
                "            };\n" +
                "            if(/(y+)/.test(fmt))\n" +
                "                fmt=fmt.replace(RegExp.$1, (this.getFullYear()+\"\").substr(4 - RegExp.$1.length));\n" +
                "            for(var k in o)\n" +
                "                if(new RegExp(\"(\"+ k +\")\").test(fmt))\n" +
                "                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : ((\"00\"+ o[k]).substr((\"\"+ o[k]).length)));\n" +
                "            return fmt;\n" +
                "        }\n" +
                "    }\n" +
                "</script>\n" +
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<meta name=\"keywords\" content=\"日志,心声,人生经历,7-blog\"/>\n" +
                "<meta name=\"Description\" content=\"My Dairy weekly\" />\n" +
                "<title>用户管理平台-婚礼岛</title>\n" +
                "<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"http://7swing.com/favicon.ico\" />\n" +
                "<link href=\"" + baseUrl + "style/homePlus.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<link href=\"" + baseUrl + "style/cp.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<script type=\"text/javascript\" src=\"" + baseUrl + "Scripts/jquery-min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"" + baseUrl + "Scripts/comm.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"" + baseUrl + "Scripts/fancybox/jquery.mousewheel-3.0.4.pack.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"" + baseUrl + "Scripts/fancybox/jquery.fancybox-1.3.4.pack.js\"></script>\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"" + baseUrl + "Scripts/fancybox/jquery.fancybox-1.3.4.css\" media=\"screen\" />\n" +
                "<script type=\"text/javascript\">\n" +
                "\t\t$(document).ready(function() {\n" +
                "\t\t\t$(\".dialogBtn\").fancybox();\n" +
                "\t\t});\n" +
                "\t</script>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"header\">\n" +
                "  <p class=\"topright\"><a href=\"#\">婚礼岛首页</a> | <a href=\"#\">我的主页</a> | <a href=\"#\" class=\"msgrev msgnew\">&nbsp;</a> | <a href=\"#\">退出</a></p>\n" +
                "  <div class=\"title\"> <span class=\"logo\"><img src=\"" + baseUrl + "/images/logo.png\" alt=\"My Wonderland\" /></span> <span class=\"pagetitle\">记录我的历程，<a href=\"#\">创建我的主页</a></span> </div>\n" +
                "</div>\n" +
                "<div class=\"main\">\n" +
                "  <div class=\"subbar\" style=\"display: none;\">\n" +
                "    <ul>\n" +
                "      <li class=\"selected\"><a href=\"#\">我的婚礼岛</a></li>\n" +
                "      <li><a href=\"#\">我的计划</a></li>\n" +
                "      <li><a href=\"#\">亲友簿</a></li>\n" +
                "      <li><a href=\"#\">任务分配</a></li>\n" +
                "      <li><a href=\"#\">我的工具</a></li>\n" +
                "      <li><a href=\"#\">个人信息维护</a></li>\n" +
                "    </ul>\n" +
                "  </div>\n" +
                "  <div class=\"mainmod\" style=\"margin-left:0px;\">\n" +
                "    <div class=\"main-inside\">\n" +
                "      <h1>呆萌小痞子给您分配任务:</h1>\n" +
                "      <div class=\"cShowMod\">\n" +
                "    <div class=\"cImagebox\"><img src=\"" + baseUrl + "/images/b_sample.jpg\" /></div>\n" +
                "    <div class=\"cInfobox\">\n" +
                "    <h3 class=\"cname\"><span class=\"maleIco\">呆萌小痞子</span>+<span class=\"femaleIco\">腹黑女汉子</span></h3>\n" +
                "    <ul>\n" +
                "    <li><div class=\"countdown\">距我们的婚礼还有<span class=\"num\">24</span>天</div></li>\n" +
                "    <li>我们的婚期是2013年3月19日</li>\n" +
                "    <li><a href=\"#\" target=\"_blank\">我们的主页 > </a> | <a class=\"icolink qrcode dialogBtn\" href=\"" + baseUrl + "images/mp_qrcode.png\" target=\"_blank\">扫描二维码访问</a></li>\n" +
                "    </ul>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  <div class=\"wedInfo\">\n" +
                "  <h2>任务信息</h2>\n" +
                "  <ul class=\"marryInfos\">\n" +
                "    <li>主题：" + request.getParameter("title") + "</li>\n" +
                "    <li>接收人：" + request.getParameter("friends") + "</li>\n" +
                "    <li>开始时间：" + request.getParameter("beginTime") + "</li>\n" +
                "    <li>结束时间：" + request.getParameter("endTime") + "</li>\n" +
                "    <br>任务内容：<br>" + request.getParameter("content") + "\n" +
                "    </ul>\n" +
                "  </div>\n" +
                "</div>\n" +
                "<p class=\"copyright\"><span>Copyright©上海XXXXXXXX有限公司</span><br />\n" +
                "  <a href=\"#\">关于婚礼岛</a></p>\n" +
                "<div style=\"display: none\">\n" +
                "    <img id=\"qr\" src=\"http://api.k780.com:88/?app=qr.get&level=L&size=6&data=http://go.jhost.cn/gxx\" alt=\"二维码\">\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
        session.setAttribute("htmlContent", htmlContent);
        long nowTime = new Date().getTime();
        String htmlRealPath = ServletActionContext.getServletContext().getRealPath("/") + nowTime + ".html";//服务器上文件路径
        String htmlUrl = baseUrl + nowTime + ".html";
        OutputStream out2 = new BufferedOutputStream(new FileOutputStream(new File(htmlRealPath)));
        out2.write(htmlContent.getBytes("UTF-8"));
        out2.flush();
    %>
<script type="text/javascript">
		$(document).ready(function() {
			$(".dialogBtn").fancybox();
		});
	</script>
</head>
<body>
<div class="header">
  <p class="topright"><a href="#">婚礼岛首页</a> | <a href="#">我的主页</a> | <a href="#" class="msgrev msgnew">&nbsp;</a> | <a href="#">退出</a></p>
  <div class="title"> <span class="logo"><img src="images/logo.png" alt="My Wonderland" /></span> <span class="pagetitle">记录我的历程，<a href="#">创建我的主页</a></span> </div>
</div>
<div class="main">
  <div class="subbar" style="display: none;">
    <ul>
      <li class="selected"><a href="#">我的婚礼岛</a></li>
      <li><a href="#">我的计划</a></li>
      <li><a href="#">亲友簿</a></li>
      <li><a href="#">任务分配</a></li>
      <li><a href="#">我的工具</a></li>
      <li><a href="#">个人信息维护</a></li>
    </ul>
  </div>
  <div class="mainmod" style="margin-left:0px;">
    <div class="main-inside">
      <h1>呆萌小痞子给您分配任务:</h1>
      <div class="cShowMod">
    <div class="cImagebox"><img src="images/b_sample.jpg" /></div>
    <div class="cInfobox">
    <h3 class="cname"><span class="maleIco">呆萌小痞子</span>+<span class="femaleIco">腹黑女汉子</span></h3>
    <ul>
    <li><div class="countdown">距我们的婚礼还有<span class="num">24</span>天</div></li>
    <li>我们的婚期是2013年3月19日</li>
    <li><a href="#" target="_blank">我们的主页 > </a> | <a class="icolink qrcode dialogBtn" href="images/mp_qrcode.png" target="_blank">扫描二维码访问</a></li>
    </ul>
    </div>
  </div>
  <div class="wedInfo">
  <h2>任务信息</h2>
  <ul class="marryInfos">
    <li>主题：<%=request.getParameter("title")%></li>
    <li>接收人：<%=request.getParameter("friends")%></li>
    <li>开始时间：<%=request.getParameter("beginTime")%></li>
    <li>结束时间：<%=request.getParameter("endTime")%></li>
    <br>任务内容：<br><%=request.getParameter("content")%>
    </ul>
  </div>
    <div class="regInfo">
        <h2>分配方式</h2>
        <ul class="regInfos">
            <li>
                <a href="http://connect.qq.com/widget/shareqq/index.html?url=<%=htmlUrl%>&desc=<%=request.getParameter("title")%>&summary=<%=request.getParameter("content")%>&pics=http://www.baidu.com/img/bdlogo.gif" target="_blank">
                    <img width="15px" src="http://shoujiqq.qjis.com/uploads/101228/1-10122R21254936.jpg">
                </a> |
                <a href="javascript: document.forms['sendEmailForm'].submit();" class="msgrev msgnew" target="_blank">&nbsp;</a> |
                <a class="icolink qrcode dialogBtn" href="#qr" target="_blank"></a>
            </li>
        </ul>
    </div>

    </div>
    <div class="cf"></div>
  </div>
</div>
<p class="copyright"><span>Copyright©上海XXXXXXXX有限公司</span><br />
  <a href="#">关于婚礼岛</a></p>
<div style="display: none">
    <img id="qr" src="http://api.k780.com:88/?app=qr.get&level=L&size=6&data=<%=htmlUrl%>" alt="二维码">
</div>
<form name="sendEmailForm" action="<%=baseUrl%>sendEmail.jsp" target="_blank" method="post" style="display: none;">
    <input type="hidden" name="title" value="<%=request.getParameter("title")%>">
    <input type="hidden" name="friends" value="<%=request.getParameter("friends")%>">
</form>
</body>
</html>
