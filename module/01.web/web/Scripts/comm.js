// JavaScript Document


//悬浮快捷面板

var sbarO = 10; //起始顶部间距（原始值）

var sbarActiveSpeed = 1000; //  

var sbarScrollSpeed = 10; //  

var sbarTime;   

function RefreshSbar() {   
     alert(11)
    var top, sbarRefreshTime;       

    sbarRefreshTime = sbarActiveSpeed;     

    top = document.documentElement.scrollTop || (document.body ? document.body.scrollTop : 0);  

    document.getElementById('subbar').style.top = 1000 + "px"; //挪动到新位置    

    if (top > sbarO) {    

        document.getElementById('subbar').style.top = top-100 + "px"; //恢复到原位置   

    } else {           

        document.getElementById('subbar').style.top = sbarO + "px";   

    }

    sbarRefreshTime = sbarScrollSpeed;      

    sbarTime = window.setTimeout( 'RefreshSbar()', sbarRefreshTime);   

}     

function InitializeSbar() {   

    var topvalue = document.documentElement.scrollTop || (document.body?document.body.scrollTop:0); 

    var topFinalvalue = topvalue + sbarO;   

    document.getElementById('subbar').style.top = topFinalvalue + "px"; 

    RefreshSbar();     

}

//返回顶部

function goTopEx(){

        var obj=document.getElementById("goTopBtn");

        function getScrollTop(){

                return document.documentElement.scrollTop || (document.body ? document.body.scrollTop : 0);

            }

        function setScrollTop(value){

                document.documentElement.scrollTop=value;

				document.body.scrollTop=value

            }    

        window.onscroll=function(){getScrollTop()>0?obj.style.display="":obj.style.display="none";}

        obj.onclick=function(){

            var goTop=setInterval(scrollMove,10);

            function scrollMove(){

                    setScrollTop(getScrollTop()/1.1);

                    if(getScrollTop()<1)clearInterval(goTop);

                }

        }

    }



//置顶导航

$(function() {

                var d=1000;

                $('#menu span').each(function(){

                    $(this).stop().animate({

                        'top':'-39px'

                    },d+=250);

                });



                $('#menu > li').hover(

                function () {

                    var $this = $(this);

                    $('a',$this).addClass('hover');

                    $('span',$this).stop().animate({'top':'45px'},300).css({'zIndex':'-1'});

                },

                function () {

                    var $this = $(this);

                    $('a',$this).removeClass('hover');

                    $('span',$this).stop().animate({'top':'-39px'},800).css({'zIndex':'-1'});

                }

            );

            });

$(function() {

                $('ul.menu a').bind('click',function(event){

                    var $anchor = $(this);

                    

                    $('html, body').stop().animate({

                        scrollTop: $($anchor.attr('href')).offset().top

                    }, 1500,'easeInOutExpo');

                    /*

                    if you don't want to use the easing effects:

                    $('html, body').stop().animate({

                        scrollTop: $($anchor.attr('href')).offset().top

                    }, 1000);

                    */

                    event.preventDefault();

                });

            });