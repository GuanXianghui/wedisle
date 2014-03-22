<%@ page import="com.gxx.record.utils.PropertyUtil" %>
<%@ page import="com.gxx.record.interfaces.BaseInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String baseUrl = PropertyUtil.getInstance().getProperty(BaseInterface.BASE_URL);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js" type="text/javascript"></script>
<title>带农历，天干地支，只可选择今天及以后的日期，已经过去的alert一个提示框</title>
<style> 
body,td,.p1,.p2,.i{font-family:arial}
body{margin:6px 0 0 0;background-color:#fff;color:#000;}
table{border:0}
#cal{width:434px;border:1px solid #c3d9ff;font-size:12px;margin:8px 0 0 15px}
#cal #top{height:29px;line-height:29px;background:#e7eef8;color:#003784;padding-left:70px}
#cal #top select{font-size:12px}
#cal #top input{padding:0}
#cal ul#wk{margin:0;padding:0;height:25px}
#cal ul#wk li{float:left;width:60px;text-align:center;line-height:25px;list-style:none}
#cal ul#wk li b{font-weight:normal;color:#c60b02}
#cal #cm{clear:left;border-top:1px solid #ddd;border-bottom:1px dotted #ddd;position:relative}
#cal #cm .cell{position:absolute;width:42px;height:36px;text-align:center;margin:0 0 0 9px;cursor: pointer;}
#cal #cm .cell .so{font:bold 16px arial;}
#cal #bm{text-align:right;height:24px;line-height:24px;padding:0 13px 0 0}
#cal #bm a{color:7977ce}
#cal #fd{display:none;position:absolute;border:1px solid #dddddf;background:#feffcd;padding:10px;line-height:21px;width:150px}
#cal #fd b{font-weight:normal;color:#c60a00}
</style>
<!--[if IE]>
<style>
#cal #top{padding-top:4px}
#cal #top input{width:65px}
#cal #fd{width:170px}
</style>
<![endif]-->
 <div class="data" style="display: none;">
    <span>
        <input type="text" name="it" id="regDate" class="it date-pick calShow"/>
    </span>
    <span>
        <a href="#" class="calShow">显示日期</a>
    </span>
</div>

<div align="center">
    <div id="cal" style="">
            <div id="top">公元 
              <select id="sel_year">
              </select>
               年 
              <select id="sel_month">
              </select>
               月  农历<span></span>年 [ <span></span>年 ]
              <input type="button" value="回到今天" title="点击后跳转回今天" onclick="bb()" style="padding:0px">
            </div>
            <ul id="wk">
              <li>一</li>
              <li>二</li>
              <li>三</li>
              <li>四</li>
              <li>五</li>
              <li><b>六</b></li>
              <li><b>日</b></li>
            </ul>
            <div id="cm"></div>
            <div id="bm"></div>
    </div>
</div>

<div align="center" id="jiRiDiv" style="display: none;">
    <table border="1">
        <tr>
            <td>公历：</td>
            <td id="jiRiGongli"></td>
        </tr>
        <tr>
            <td>农历：</td>
            <td id="jiRiNongli"></td>
        </tr>
        <tr>
            <td>宜：</td>
            <td id="jiRiYi"></td>
        </tr>
        <tr>
            <td>忌：</td>
            <td id="jiRiJi"></td>
        </tr>
        <tr>
            <td>冲：</td>
            <td id="jiRiChong"></td>
        </tr>
        <tr>
            <td>五行：</td>
            <td id="jiRiWuXing"></td>
        </tr>
        <tr>
            <td>彭祖百忌：</td>
            <td id="jiRiPengZu"></td>
        </tr>
        <tr>
            <td>次岁：</td>
            <td id="jiRiCiSui"></td>
        </tr>
    </table>
</div>

<script type="text/javascript">
// Unicode与GB2312互转
var GB2312UnicodeConverter = {
    ToUnicode: function (str) {
        return escape(str).toLocaleLowerCase().replace(/%u/gi, '\\u');
    }
    , ToGB2312: function (str) {
        return unescape(str.replace(/\\u/gi, '%u'));
    }
};
var jiRiYear;
var jiRiMonth;
var jiRi;
var SUCCESS_STR = "success";//成功编码
/**
 * 获得好日子信息
 */
function getGoodDayInfo(year, month, day)
{
    var monthStr = month;
    if(monthStr.length == 1)
    {
        monthStr = "0" + monthStr;
    }
    $.ajax({
        type: "post",
        async: false,
        url: "<%=baseUrl%>wedding/gooday/getGoodDayInfo.jsp",
        data: "date=" + year + "-" + monthStr,
        success: function(data, textStatus){
            if((SUCCESS_STR == textStatus) && (null != data) && (data.indexOf("(")==0))
            {
                data = GB2312UnicodeConverter.ToGB2312(data);
                jiRi = eval(data);
                jiRiYear = year;
                jiRiMonth = month;
                chooseDay(day);
                //alert(jiRi.detail[0].gongli);
                /**
                 "gongli":"公元2014年09月01日",
                 "nongli":"农历2014年八月(大)初八 星期一 处女座",
                 "chong":"冲蛇(己巳)煞西",
                 "yi":"祭祀 理发 作灶 沐浴 修饰垣墙 平治道涂",
                 "ji":"嫁娶 栽种 祈福 造桥 安葬 安门 伐木 作梁",
                 "wuxing":"山头火 平执位       ",
                 "sueici":"甲午        年 壬申        月 乙亥        日 属马",
                 "pengzu":"乙不栽植千株不长 亥不嫁娶不利新郎"*
                 */
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

function chooseDay(day)
{
    if(null == jiRi)
    {
        alert("加载黄道吉日失败，请重试！");
        return;
    }
    document.getElementById("jiRiDiv").style.display = "";
    document.getElementById("jiRiGongli").innerHTML = jiRi.detail[day-1].gongli;
    document.getElementById("jiRiNongli").innerHTML = jiRi.detail[day-1].nongli;
    document.getElementById("jiRiYi").innerHTML = jiRi.detail[day-1].yi;
    document.getElementById("jiRiJi").innerHTML = jiRi.detail[day-1].ji;
    document.getElementById("jiRiChong").innerHTML = jiRi.detail[day-1].chong;
    document.getElementById("jiRiWuXing").innerHTML = jiRi.detail[day-1].wuxing;
    document.getElementById("jiRiPengZu").innerHTML = jiRi.detail[day-1].pengzu;
    document.getElementById("jiRiCiSui").innerHTML = jiRi.detail[day-1].sueici;
}

<!--
(function(){
var S=navigator.userAgent.indexOf("MSIE")!=-1&&!window.opera;
function M(C){
return document.getElementById(C)
} function R(C){
return document.createElement(C)
} var P=[19416,19168,42352,21717,53856,55632,91476,22176,39632,21970,19168,42422,42192,53840,119381,46400,54944,44450,38320,84343,18800,42160,46261,27216,27968,109396,11104,38256,21234,18800,25958,54432,59984,28309,23248,11104,100067,37600,116951,51536,54432,120998,46416,22176,107956,9680,37584,53938,43344,46423,27808,46416,86869,19872,42448,83315,21200,43432,59728,27296,44710,43856,19296,43748,42352,21088,62051,55632,23383,22176,38608,19925,19152,42192,54484,53840,54616,46400,46496,103846,38320,18864,43380,42160,45690,27216,27968,44870,43872,38256,19189,18800,25776,29859,59984,27480,21952,43872,38613,37600,51552,55636,54432,55888,30034,22176,43959,9680,37584,51893,43344,46240,47780,44368,21977,19360,42416,86390,21168,43312,31060,27296,44368,23378,19296,42726,42208,53856,60005,54576,23200,30371,38608,19415,19152,42192,118966,53840,54560,56645,46496,22224,21938,18864,42359,42160,43600,111189,27936,44448];
var K="甲乙丙丁戊己庚辛壬癸";
var J="子丑寅卯辰巳午未申酉戌亥";
var O="鼠牛虎兔龙蛇马羊猴鸡狗猪";
var L=["小寒","大寒","立春","雨水","惊蛰","春分","清明","谷雨","立夏","小满","芒种","夏至","小暑","大暑","立秋","处暑","白露","秋分","寒露","霜降","立冬","小雪","大雪","冬至"];
var D=[0,21208,43467,63836,85337,107014,128867,150921,173149,195551,218072,240693,263343,285989,308563,331033,353350,375494,397447,419210,440795,462224,483532,504758];
var B="日一二三四五六七八九十";
var H=["正","二","三","四","五","六","七","八","九","十","十一","腊"];
var E="初十廿卅";
var V={
"0101":"*1元旦节","0214":"情人节","0305":"学雷锋纪念日","0308":"妇女节","0312":"植树节","0315":"消费者权益日","0401":"愚人节","0501":"*1劳动节","0504":"青年节","0601":"国际儿童节","0701":"中国共产党诞辰","0801":"建军节","0910":"中国教师节","1001":"*3国庆节","1224":"平安夜","1225":"圣诞节"
} ;
var T={
"0101":"*2春节","0115":"元宵节","0505":"*1端午节","0815":"*1中秋节","0909":"重阳节","1208":"腊八节","0100":"除夕"
} ;
function U(Y){
function c(j,i){
var h=new Date((31556925974.7*(j-1900)+D[i]*60000)+Date.UTC(1900,0,6,2,5));
return(h.getUTCDate())
} function d(k){
var h,j=348;
for(h=32768;h>8;h>>=1){
j+=(P[k-1900]&h)?1:0
} return(j+b(k))
} function a(h){
return(K.charAt(h%10)+J.charAt(h%12))
} function b(h){
if(g(h)){
return((P[h-1900]&65536)?30:29)
} else{
return(0)
} 
} function g(h){
return(P[h-1900]&15)
} function e(i,h){
return((P[i-1900]&(65536>>h))?30:29)
} function C(m){
var k,j=0,h=0;
var l=new Date(1900,0,31);
var n=(m-l)/86400000;
this.dayCyl=n+40;
this.monCyl=14;
for(k=1900;k<2050&&n>0;k++){
h=d(k);
n-=h;
this.monCyl+=12
} if(n<0){
n+=h;
k--;
this.monCyl-=12
} this.year=k;
this.yearCyl=k-1864;
j=g(k);
this.isLeap=false;
for(k=1;k<13&&n>0;k++){
if(j>0&&k==(j+1)&&this.isLeap==false){
--k;
this.isLeap=true;
h=b(this.year)
} else{
h=e(this.year,k)
} if(this.isLeap==true&&k==(j+1)){
this.isLeap=false
} n-=h;
if(this.isLeap==false){
this.monCyl++
} 
} if(n==0&&j>0&&k==j+1){
if(this.isLeap){
this.isLeap=false
} else{
this.isLeap=true;
--k;
--this.monCyl
} 
} if(n<0){
n+=h;
--k;
--this.monCyl
} this.month=k;
this.day=n+1
} function G(h){
return h<10?"0"+h:h
} function f(i,j){
bb();
var h=i;
return j.replace(/dd?d?d?|MM?M?M?|yy?y?y?/g,function(k){
switch(k){
case"yyyy":var l="000"+h.getFullYear();
return l.substring(l.length-4);
case"dd":return G(h.getDate());
case"d":return h.getDate().toString();
case"MM":return G((h.getMonth()+1));
case"M":return h.getMonth()+1
} 
} )
} function Z(i,h){
bb();
var j;
switch(i,h){
case 10:j="初十";
break;
case 20:j="二十";
break;
case 30:j="三十";
break;
default:j=E.charAt(Math.floor(h/10));
j+=B.charAt(h%10)
} return(j)
} this.date=Y;
this.isToday=false;
this.isRestDay=false;
this.solarYear=f(Y,"yyyy");
this.solarMonth=f(Y,"M");
this.solarDate=f(Y,"d");
this.solarWeekDay=Y.getDay();
this.solarWeekDayInChinese="星期"+B.charAt(this.solarWeekDay);
var X=new C(Y);
this.lunarYear=X.year;
this.shengxiao=O.charAt((this.lunarYear-4)%12);
this.lunarMonth=X.month;
this.lunarIsLeapMonth=X.isLeap;
this.lunarMonthInChinese=this.lunarIsLeapMonth?"闰"+H[X.month-1]:H[X.month-1];
this.lunarDate=X.day;
this.showInLunar=this.lunarDateInChinese=Z(this.lunarMonth,this.lunarDate);
if(this.lunarDate==1){
this.showInLunar=this.lunarMonthInChinese+"月"
} this.ganzhiYear=a(X.yearCyl);
this.ganzhiMonth=a(X.monCyl);
this.ganzhiDate=a(X.dayCyl++);
this.jieqi="";
this.restDays=0;
if(c(this.solarYear,(this.solarMonth-1)*2)==f(Y,"d")){
this.showInLunar=this.jieqi=L[(this.solarMonth-1)*2]
} if(c(this.solarYear,(this.solarMonth-1)*2+1)==f(Y,"d")){
this.showInLunar=this.jieqi=L[(this.solarMonth-1)*2+1]
} if(this.showInLunar=="清明"){
this.showInLunar="清明节";
this.restDays=1
} this.solarFestival=V[f(Y,"MM")+f(Y,"dd")];
if(typeof this.solarFestival=="undefined"){
this.solarFestival=""
} else{
if(/\*(\d)/.test(this.solarFestival)){
this.restDays=parseInt(RegExp.$1);
this.solarFestival=this.solarFestival.replace(/\*\d/,"")
} 
} this.showInLunar=(this.solarFestival=="")?this.showInLunar:this.solarFestival;
this.lunarFestival=T[this.lunarIsLeapMonth?"00":G(this.lunarMonth)+G(this.lunarDate)];
if(typeof this.lunarFestival=="undefined"){
this.lunarFestival=""
} else{
if(/\*(\d)/.test(this.lunarFestival)){
this.restDays=(this.restDays>parseInt(RegExp.$1))?this.restDays:parseInt(RegExp.$1);
this.lunarFestival=this.lunarFestival.replace(/\*\d/,"")
} 
} if(this.lunarMonth==12&&this.lunarDate==e(this.lunarYear,12)){
this.lunarFestival=T["0100"];
this.restDays=1
} this.showInLunar=(this.lunarFestival=="")?this.showInLunar:this.lunarFestival;
this.showInLunar=(this.showInLunar.length>4)?this.showInLunar.substr(0,2)+"...":this.showInLunar
} var Q=(function(){
var X={
} ;
X.lines=0;
X.dateArray=new Array(42);
function Y(a){
return(((a%4===0)&&(a%100!==0))||(a%400===0))
} function G(a,b){
return[31,(Y(a)?29:28),31,30,31,30,31,31,30,31,30,31][b]
} function C(a,b){
a.setDate(a.getDate()+b);
return a
} function Z(a){
bb();
var f=0;
var c=new U(new Date(a.solarYear,a.solarMonth-1,1));
var d=(c.solarWeekDay-1==-1)?6:c.solarWeekDay-1;
X.lines=Math.ceil((d+G(a.solarYear,a.solarMonth-1))/7);
for(var e=0;e<X.dateArray.length;e++){
if(c.restDays!=0){
f=c.restDays
} if(f>0){
c.isRest=true
} if(d-->0||c.solarMonth!=a.solarMonth){
X.dateArray[e]=null;
continue
} var b=new U(new Date());
if(c.solarYear==b.solarYear&&c.solarMonth==b.solarMonth&&c.solarDate==b.solarDate){
c.isToday=true
} X.dateArray[e]=c;
c=new U(C(c.date,1));
f--
} 
} return{
init:function(a){
Z(a)
} ,getJson:function(){
return X
} 
} 
} )();
var W=(function(){
var C=M("top").getElementsByTagName("SELECT")[0];
var X=M("top").getElementsByTagName("SELECT")[1];
var G=M("top").getElementsByTagName("SPAN")[0];
var c=M("top").getElementsByTagName("SPAN")[1];
var Y=M("top").getElementsByTagName("INPUT")[0];
function a(g){
G.innerHTML=g.ganzhiYear;
c.innerHTML=g.shengxiao
} function b(g){
C[g.solarYear-1901].selected=true;
X[g.solarMonth-1].selected=true
} function f(){
bb();
var j=C.value;
var g=X.value;
var i=new U(new Date(j,g-1,1));
Q.init(i);
N.draw();
if(this==C){
i=new U(new Date(j,3,1));
G.innerHTML=i.ganzhiYear;
c.innerHTML=i.shengxiao
} var h=new U(new Date());
Y.style.visibility=(j==h.solarYear&&g==h.solarMonth)?"hidden":"visible"
} function Z(){
bb();
var g=new U(new Date());
a(g);
b(g);
Q.init(g);
N.draw();
Y.style.visibility="hidden"
bb();
} function d(k,g){
for(var j=1901;j<2050;j++){
var h=R("OPTION");
h.value=j;
h.innerHTML=j;
if(j==k){
h.selected="selected"
} C.appendChild(h)
} for(var j=1;j<13;j++){
var h=R("OPTION");
h.value=j;
h.innerHTML=j;
if(j==g){
h.selected="selected"
} X.appendChild(h)
} C.onchange=f;
X.onchange=f
} function e(g){
d(g.solarYear,g.solarMonth);
G.innerHTML=g.ganzhiYear;
c.innerHTML=g.shengxiao;
Y.onclick=Z;bb();
Y.style.visibility="hidden"
} return{
init:function(g){
e(g)
} ,reset:function(g){
b(g)
} 
} 
} )();
var N=(function(){
function C(){
var Z=Q.getJson();
var c=Z.dateArray;
M("cm").style.height=Z.lines*38+2+"px";
M("cm").innerHTML="";
for(var a=0;a<c.length;a++){
if(c[a]==null){
continue
} var X=R("DIV");
if(c[a].isToday){
X.style.border="1px solid #a5b9da";
X.style.background="#c1d9ff"
} X.className="cell";
X.style.left=(a%7)*60+"px";
X.style.top=Math.floor(a/7)*38+2+"px";
var b=R("DIV");
b.className="so";
// http://www.codefans.net
b.style.color=((a%7)>4||c[a].isRest)?"#c60b02":"#313131";
b.innerHTML=c[a].solarDate;
X.appendChild(b);
var Y=R("DIV");
Y.style.color="#666";
Y.innerHTML=c[a].showInLunar;
X.appendChild(Y);
X.onmouseover=(function(d){
return function(f){
F.show({
dateIndex:d,cell:this
} )
} 
} )(a);
X.onmouseout=function(){
F.hide()
} ;
M("cm").appendChild(X)
} var G=R("DIV");
G.id="fd";
M("cm").appendChild(G);
F.init(G)
} return{
draw:function(G){
C(G)
} 
} 
} )();




var F=(function(){
var C;
function Y(e,c){
if(arguments.length>1){
var b=/([.*+?^=!:${}()|[\]\/\\])/g,Z="{".replace(b,"\\$1"),d="}".replace(b,"\\$1");
var a=new RegExp("#"+Z+"([^"+Z+d+"]+)"+d,"g");
if(typeof (c)=="object"){
return e.replace(a,function(f,h){
var g=c[h];
return typeof (g)=="undefined"?"":g
} )
} 
} return e
}
    function G(b){
        var a=Q.getJson().dateArray[b.dateIndex];
        var Z=b.cell;
        var c="#" + "{solarYear} 年 #" + "{solarMonth} 月 #" + "{solarDate} 日 #" + "{solarWeekDayInChinese}";
        c+="<br><b>农历 #" + "{lunarMonthInChinese}月#" + "{lunarDateInChinese}</b>";
        c+="<br>#" + "{ganzhiYear}年 #" + "{ganzhiMonth}月 #" + "{ganzhiDate}日";
        if(a.solarFestival!=""||a.lunarFestival!=""||a.jieqi!=""){
            c+="<br><b>#" + "{lunarFestival} #" + "{solarFestival} #" + "{jieqi}</b>"
        }
        C.innerHTML=Y(c,a);
        C.style.top=Z.offsetTop+Z.offsetHeight-5+"px";
        C.style.left=Z.offsetLeft+Z.offsetWidth-5+"px";
        C.style.display="block"
    }
    function X(){
C.style.display="none"
} return{
show:function(Z){
G(Z)
} ,hide:function(){
X()
} ,init:function(Z){
C=Z
} 
} 
} )();
var A=new U(new Date());
if(S){
window.attachEvent("onload",function(){
W.reset(A)
} )
} W.init(A);
Q.init(A);
N.draw();
} )();
//-->

var jSelectDate = {

    /**
     * 选项设置
     */
    settings : {
        css: "date",
        borderCss: "date",
        disabled: false,
        yearBefore : 0,
        yearAfter : 10,
        isShowLabel: true
    },
    
    /**
     * 初始化对向
     * @param {Object} el 用于存放日期结果的文本框 jQuery DOM
     */
    init: function(els){
    
        els.each(function(){
            var el = $(this);
            
            /* 取得旧的日期 */
            var elValue = el.val();
            elDate = new Date();
            var nYear = elDate.getYear();
            var nMonth = jSelectDate.returnMonth(elDate.getMonth());
            var nDay = elDate.getDate();
    var nHours = jSelectDate.returnHours(elDate.getHours()); 
    var nMinutes = elDate.getMinutes(); 
    //取整处理-----------------
    nMinutes = Math.floor(nMinutes/10)+'0';
    if (nHours < 10) nHours ="0" + nHours; 
    //初始对象值
    el.val(nYear + "-" + nMonth + "-" + nDay+ " "+nHours+":"+nMinutes+":00");
            /* 隐藏给出的对象 */
            el.hide();
            /* 先算出当前共有多少个jSelectDate */
            var currentIdx = 1;//$("jSelectDateBorder").length +
            
             /* 加入控件到文本框的位置 */
            var spanDate = document.createElement("span");
            spanDate.id = "spanDate" + currentIdx;
            spanDate.className = "jSelectDateBorder " + jSelectDate.settings.borderCss;
            spanDate.disabled = jSelectDate.settings.disabled;
            
            el.after(spanDate);
            
            /* 创建年 */
            var selYear = document.createElement("select");
            selYear.id = "selYear" + currentIdx
            selYear.className = jSelectDate.settings.css;
            selYear.disabled = jSelectDate.settings.disabled;
            
            /* 加入选项 */
            var begin = jSelectDate.settings.yearNow - jSelectDate.settings.yearBefore;
            var end = jSelectDate.settings.yearNow + jSelectDate.settings.yearAfter;
            for (var i = begin; i <= end; i++) {
            
                var option = document.createElement("option");
                option.value = i;
                option.innerHTML = i;
                
                /* 判断是否有旧的值，如果有就选中 */
                if (!isNaN(nYear)) {
                    if (i == nYear) {
                        option.selected = true;
                    }
                }
                selYear.appendChild(option);
                option = null;
            } 
            
            $(spanDate).append(selYear);
            
            /* 创建月 */
            var selMonth = document.createElement("select");
            selMonth.id = "selMonth" + currentIdx
            selMonth.className = jSelectDate.settings.css;
            selMonth.disabled = jSelectDate.settings.disabled;
            /* 加入选项 */
            for (var i = 1; i <= 12; i++) {
                var option = document.createElement("option");
                if (i < 10){option.value ="0" + i; option.innerHTML ="0" + i; }else{option.value = i;option.innerHTML = i; }
                
                /* 判断是否有旧的值，如果有就选中 */
                if (!isNaN(nMonth)) {
                    if (i == nMonth) {
                        option.selected = true;
                    }
                }
                
                selMonth.appendChild(option);
                option = null;
            }
            /* 加入控件到文本框的位置 */
            $(selYear).after(selMonth);
            
            
            /* 创建日 */
            var selDay = document.createElement("select");
            selDay.id = "selDay" + currentIdx
            selDay.className = jSelectDate.settings.css;
            selDay.disabled = jSelectDate.settings.disabled;
            
            /* 算出最大的天数 */            
            var maxDayNum = 30;
            if (nMonth == 2) {
                if (jSelectDate.isLeapYear(nYear)) {
                    maxDayNum = 29;
                }
                else{
                    maxDayNum = 28;
                }
            }
            else if (jSelectDate.isLargeMonth(nMonth)) {
                    maxDayNum = 31;
            }
            
            /* 加入选项 */
            for (var i = 1; i <= maxDayNum; i++) {
            
                var option = document.createElement("option");
                if (i < 10){option.value ="0" + i; option.innerHTML ="0" + i; }else{option.value = i;option.innerHTML = i; }
                
                /* 判断是否有旧的值，如果有就选中 */
                if (!isNaN(nDay)) {
                    if (i == nDay) {
                        option.selected = true;
                    }
                }
                
                selDay.appendChild(option);
                option = null;
            }
            /* 加入控件到文本框的位置 */
            $(selMonth).after(selDay);

            if (jSelectDate.settings.isShowLabel) {
                $(selMonth).before("年 ");
                $(selDay).before("月 ");
                $(selDay).after("日 ");
     $(selHours).after(":");
            }else{
                $(selMonth).before(" ");
                $(selDay).before(" ");
            }
            
            /* 返回当前选择的日期 */
            var SetDate = function(){
                var year = $(selYear).val();
                var month = $(selMonth).val();
                var day = $(selDay).val();
     var Hours = $(selHours).val();
     var Minutes = $(selMinutes).val();
                el.val(year + "-" + month + "-" + day+ " "+Hours+":"+Minutes+":00");
            }
            
            /**
             * 给几个下拉列表加入更改后的事件
             */
    $(selHours).change(function(){
                return SetDate();
            });
    $(selMinutes).change(function(){
                return SetDate();
            });
    
            $(selDay).change(function(){
                return SetDate();
            });
            $(selMonth).change(function(){
                jSelectDate.progressDaySize(this,true);
                
                /* 更新文本框中的日期 */
                return SetDate();
            });
            $(selYear).change(function(){
                jSelectDate.progressDaySize(this,false);
                return SetDate();
            });
        })
    },
    
    /**
     * 判断是否闰年
     * @param {Object} year
     */
    isLeapYear : function(year){return (0==year%4&&((year%100!=0)||(year%400==0)));},

    /**
     * 判断是否是大月
     * @param {Object} monthNum
     */
    isLargeMonth : function(monthNum){
        var largeArray = [true,false,true,false,true,false,true,true,false,true,false,true];
        return largeArray[monthNum - 1];
    },
    
    returnMonth: function(num){
        var arr = new Array("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        return arr[num];
    },

    
    /**
     * 创建一个Option对象
     * @param {Object} value 值
     * @param {Object} text 文本
     */
    createOption : function(value,text){
        var option = document.createElement("option");
        option.value = value;
        option.innerHTML = text;
        return option;            
    },
    
    /**
     * 处理天数
     * @param {Object} el 下拉列表对像
     * @param {Object} isMonth 是否是月的下拉列表 或者就处理 年的下拉列表
     */
    progressDaySize: function(el,isMonth){
        if (isMonth == true) {
            /* 选择月时处理大月、小月和二月的情况 */
            var month = $(el).val();
            var year = $($("select", $(el).parent())[0]).val()
            var selDay = $($("select", $(el).parent())[2]);
            if (month == 2) {
            
                /* 2月处理 */
                $("option:contains('31')", selDay).remove();
                $("option:contains('30')", selDay).remove();
                
                /* 闰年处理 */
                if (!jSelectDate.isLeapYear(year)) {
                    $("option:contains('29')", selDay).remove();
                }
                else {
                
                    if ($("option:contains('29')", selDay).length == 0) {
                        selDay.append(jSelectDate.createOption(29, 29));
                    }
                }
            }
            else 
                if (!jSelectDate.isLargeMonth(month)) {
                
                    /* 小月处理 */
                    if ($("option:contains('30')", selDay).length == 0) {
                        selDay.append(jSelectDate.createOption(30, 30));
                    }
                    
                    $("option:contains('31')", selDay).remove();
                }
                else {
                
                    /* 大月处理 */
                    if ($("option:contains('30')", selDay).length == 0) {
                        selDay.append(jSelectDate.createOption(30, 30));
                    }
                    
                    if ($("option:contains('31')", selDay).length == 0) {
                        selDay.append(jSelectDate.createOption(31, 31));
                    }
                }
        }
        else{
            /* 处理闰年的二月问题 */
            var panelDate = $(el).parent();
            var year = $(el).val();
            var month = $($("select",panelDate)[1]).val()
            var selDay = $($("select",panelDate)[2]);
            if(month == 2){
                $("option:contains('31')",selDay).remove();
                $("option:contains('30')",selDay).remove();
                if(! jSelectDate.isLeapYear(year)){                
                    $("option:contains('29')",selDay).remove();
                }
                else{
                    
                    if($("option:contains('29')",selDay).length == 0){
                        selDay.append(jSelectDate.createOption(29, 29));
                    }
                }
            }
            
        }
    }   
}
jQuery.fn.jSelectDate = function(settings){
    var getNowYear = function(){
        /* 得到现在的年 */
        var date = new Date();
        return date.getYear();
    }
    //alert(settings.yearBefore);
    jSelectDate.settings.yearNow = getNowYear();
    $.extend(jSelectDate.settings, settings);
    jSelectDate.init($(this));
    return $(this);
}
  $(document).ready(function(){
/*$("#calShow").click(function(){
	$("#cal").css("display","block");
});	*/

$(".calShow").toggle(
function(){
var a = document.getElementById("sel_year");
var b=document.getElementById("sel_month");
$("#cal").show('slow');

},
function(){ 
$("#cal").hide('slow');
}
);
})
getTime();
bb();
function getTime()
{
var a = document.getElementById("sel_year");
var b=document.getElementById("sel_month");
var time=new Date();
d1=toDate(a.options[a.selectedIndex].value+"-"+b.options[b.selectedIndex].value+"-"+time.getDate());
}
function toDate(str){
    var sd=str.split("-");
    return new Date(sd[0],sd[1],sd[2]);
}
function bb()
{
    var aa=document.getElementById("cm").children;
    var a = document.getElementById("sel_year");
    var b=document.getElementById("sel_month");
    for(var i=0;i<aa.length;i++)
    {
        aa[i].onclick=function()
        {
            var d2=toDate(a.options[a.selectedIndex].value+"-"+b.options[b.selectedIndex].value+"-"+this.firstChild.innerHTML);
            if(d2<d1)
            {
                alert("请选择当前和之后的日期");
            }
            else{
                var year = a.options[a.selectedIndex].value;
                var month = b.options[b.selectedIndex].value;
                var day = this.firstChild.innerHTML;
                if(jiRiYear != year || jiRiYear != month)
                {
                    getGoodDayInfo(year, month, day);
                }
                //document.getElementById("regDate").value=a.options[a.selectedIndex].value+"年"+b.options[b.selectedIndex].value+"月"+this.firstChild.innerHTML+"日";
            }
        }
    }
}
function cc()
{
var aa=document.getElementById("cm").children;
var a = document.getElementById("sel_year");
var b=document.getElementById("sel_month");
  var d2=toDate(a.options[a.selectedIndex].value+"-"+b.options[b.selectedIndex].value+"-"+aa[i].firstChild.innerHTML);
  if(d2<d1)
  {
   aa[i].style.background="red";
   }
}

</script>