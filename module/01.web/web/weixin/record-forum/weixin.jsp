<%@ page import="java.util.Map" %><%@ page import="java.util.Iterator" %><%@ page import="com.gxx.record.utils.ServiceDataUtil" %><%@ page import="java.util.Date" %><%@ page import="org.apache.commons.lang.StringUtils" %><%@ page import="com.gxx.record.utils.WeixinUtils" %><%@ page contentType="text/html;charset=UTF-8" language="java" %><%    String token = "gxx";//记录论坛公众平台设置的token

    String signature = null;
    String timestamp = null;
    String nonce = null;
    String echostr = null;

    String log = StringUtils.EMPTY;
    String dateTime = ServiceDataUtil.getDefaultDateTime(new Date());
    log += "[时间:" + dateTime + "]";
    Map map = request.getParameterMap();
    Iterator iterator = map.keySet().iterator();
    while (iterator.hasNext()){
        String key = (String)iterator.next();
        String[] values = (String[])map.get(key);
        String value = StringUtils.EMPTY;
        for(String temp : values){
            if(StringUtils.isNotBlank(value)){
                value += ",";
            }
            value += temp;
        }
        log += ",[" + key + ":" + value + "]";
        if("signature".equals(key)){
            signature = value;
        }
        if("timestamp".equals(key)){
            timestamp = value;
        }
        if("nonce".equals(key)){
            nonce = value;
        }
        if("echostr".equals(key)){
            echostr = value;
        }
    }
    String log_tag = "WEIXIN_RECORD_FORUM_LOG";
    String weixin_record_forum_log = (String)application.getAttribute(log_tag);
    if(StringUtils.isEmpty(weixin_record_forum_log)){
        weixin_record_forum_log = StringUtils.EMPTY;
    }
    if(StringUtils.isNotBlank(weixin_record_forum_log)){
        weixin_record_forum_log += "<br>";
    }
    weixin_record_forum_log += log;
    application.setAttribute(log_tag, weixin_record_forum_log);

    if(StringUtils.isBlank(timestamp) || StringUtils.isBlank(nonce) || StringUtils.isBlank(signature)){
        response.getWriter().write("error request~");
        return;
    }
    if(!WeixinUtils.checkSign(token, timestamp, nonce, signature)){
        response.getWriter().write("check sign error~");
        return;
    }
    if(StringUtils.isNotBlank(echostr)){
        response.getWriter().write(echostr);
        return;
    }
    response.getWriter().write("Record-forum has gotten your msg,ths~");
%>