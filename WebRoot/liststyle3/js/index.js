/*banner*/
$(function(){
        var $banner=$('.banner1');
        var $banner_ul=$('.banner-img');
        var $btn=$('.banner-btn');
        var $btn_a=$btn.find('a')
        var v_width=$banner.width();
        
        var page=1;
        
        var timer=null;
        var btnClass=null;

        var page_count=$banner_ul.find('li').length;//把这个值赋给小圆点的个数
        
        var banner_cir="<li class='selected' href='#'><a></a></li>";
        for(var i=1;i<page_count;i++){
                //动态添加小圆点
                banner_cir+="<li><a href='#'></a></li>";
                }
        $('.banner-circle').append(banner_cir);
        
        var cirLeft=$('.banner-circle').width()*(-0.5);
        $('.banner-circle').css({'marginLeft':cirLeft});
        
        $banner_ul.width(page_count*v_width);
        
        function move(obj,classname){
                //手动及自动播放
        if(!$banner_ul.is(':animated')){
                if(classname=='prevBtn'){
                        if(page==1){
                                        $banner_ul.animate({left:-v_width*(page_count-1)});
                                        page=page_count; 
                                        cirMove();
                        }
                        else{
                                        $banner_ul.animate({left:'+='+v_width},"slow");
                                        page--;
                                        cirMove();
                        }        
                }
                else{
                        if(page==page_count){
                                        $banner_ul.animate({left:0});
                                        page=1;
                                        cirMove();
                                }
                        else{
                                        $banner_ul.animate({left:'-='+v_width},"slow");
                                        page++;
                                        cirMove();
                                }
                        }
                }
        }
        
        function cirMove(){
                //检测page的值，使当前的page与selected的小圆点一致
                $('.banner-circle li').eq(page-1).addClass('selected')
                                                                                                .siblings().removeClass('selected');
        }
        
        $banner.mouseover(function(){
                $btn.css({'display':'block'});
                clearInterval(timer);
                                }).mouseout(function(){
                $btn.css({'display':'none'});                
                clearInterval(timer);
                timer=setInterval(move,3000);
                                }).trigger("mouseout");//激活自动播放

        $btn_a.mouseover(function(){
                //实现透明渐变，阻止冒泡
                        $(this).animate({opacity:0.6},'fast');
                        $btn.css({'display':'block'});
                         return false;
                }).mouseleave(function(){
                        $(this).animate({opacity:0.3},'fast');
                        $btn.css({'display':'none'});
                         return false;
                }).click(function(){
                        //手动点击清除计时器
                        btnClass=this.className;
                        clearInterval(timer);
                        timer=setInterval(move,3000);
                        move($(this),this.className);
                });
                
        $('.banner-circle li').live('click',function(){
                        var index=$('.banner-circle li').index(this);
                        $banner_ul.animate({left:-v_width*index},'slow');
                        page=index+1;
                        cirMove();
                });
});

/*首页*/
$(function(){
	$('.login .login_show').click(function(){
		$('.login2').hide();
		$('.login2').eq($(this).index()).show();	
		$('.login_show').removeClass('on');
		$('.login_show').eq($(this).index()).addClass('on');
	})
	
	$('.index1_1 .index1_1li').click(function(){
		$('.index1_3').hide();
		$('.index1_3').eq($(this).index()).show();	
		$('.index1_1li').removeClass('on1');
		$('.index1_1li').eq($(this).index()).addClass('on1');
	})
	
	$('.index1_7 .index1_7s').click(function(){
		$('.index1_08').hide();
		$('.index1_08').eq($(this).index()).show();	
		$('.index1_7s').removeClass('on2');
		$('.index1_7s').eq($(this).index()).addClass('on2');
	})
	
	$('.index1_8_ul li:nth-child(5n)').css({'margin-right':'0'})
	
	//renjiahui 11-09 点击收回最侧列表
	$('li.li-items').click(function(){
		if($(this).find('.h1-open').css('display')=='none'){
			$(this).children('.h1-open').show();
		}else{
			$(this).children('.h1-open').hide();
		}	
	})
	
	//renjiahui  11-09科小觅列表
	$('.li1109').click(function(){	
	  $('.li1109').removeClass('on4');
	  $('.li1109').eq($(this).index()).addClass('on4');
	})
})

$(function(){
	var LiWidth=$(".index_box1 ul li").width();
	var index02=0;
	var length=$(".index_box1 ul li").length;
	var ulWidth=1000*length;

	$(".index_box1 ul").css({width:ulWidth});

	$(".prev").click(function(){
		if(index02==0){
			index02=length-1;
			showImg01(index02);
		}else{
			index02=index02-1;
			showImg01(index02);
		}
	});
	$(".next").click(function(){
		if(index02==length-1){
			index02=0;
			showImg01(index02);
		}else{
			index02++;
			showImg01(index02);
		}
	});

	function showImg01(a){
		var left01=-a*1000;
		$(".index_box1 ul").stop().animate({left:left01+"px"},1000);
	}
});





/*我的科小觅*/
$(function(){
	var ab=true;
	$('.img-xin').click(function(){
		if(ab){
			$(this).css({'background':'url(./img/xinh.png) left center no-repeat','color':'red'});
		}else{
			$(this).css({'background':'url(./img/xin.png) left center no-repeat','color':'#999'});
		}
		ab=!ab;
	})
})


/*我的科小觅-轮番*/
$(function(){
        var $banner=$('.index2_box02');
        var $banner_ul=$('.index2_box02-img');
        var $btn=$('.index2_box02-btn');
        var $btn_a=$btn.find('a')
        var v_width=$banner.width();
        
        var page=1;
        
        var timer=null;
        var btnClass=null;

        var page_count=$banner_ul.find('li').length;//把这个值赋给小圆点的个数
        
        var banner_cir="<li class='selected' href='#'><a></a></li>";
        for(var i=1;i<page_count;i++){
                //动态添加小圆点
                banner_cir+="<li><a href='#'></a></li>";
                }
        $('.index2_box02-circle').append(banner_cir);
        
        var cirLeft=$('.index2_box02-circle').width()*(-0.5);
        $('.index2_box02-circle').css({'marginLeft':cirLeft});
        
        $banner_ul.width(page_count*v_width);
        
        function move(obj,classname){
                //手动及自动播放
        if(!$banner_ul.is(':animated')){
                if(classname=='prevBtn'){
                        if(page==1){
                                        $banner_ul.animate({left:-v_width*(page_count-1)});
                                        page=page_count; 
                                        cirMove();
                        }
                        else{
                                        $banner_ul.animate({left:'+='+v_width},"slow");
                                        page--;
                                        cirMove();
                        }        
                }
                else{
                        if(page==page_count){
                                        $banner_ul.animate({left:0});
                                        page=1;
                                        cirMove();
                                }
                        else{
                                        $banner_ul.animate({left:'-='+v_width},"slow");
                                        page++;
                                        cirMove();
                                }
                        }
                }
        }
        
        function cirMove(){
                //检测page的值，使当前的page与selected的小圆点一致
                $('.index2_box02-circle li').eq(page-1).addClass('selected')
                                                                                                .siblings().removeClass('selected');
        }
        
        $banner.mouseover(function(){
                $btn.css({'display':'block'});
                clearInterval(timer);
                                }).mouseout(function(){
                $btn.css({'display':'none'});                
                clearInterval(timer);
                timer=setInterval(move,3000);
                                }).trigger("mouseout");//激活自动播放

        $btn_a.mouseover(function(){
                //实现透明渐变，阻止冒泡
                        $(this).animate({opacity:0.6},'fast');
                        $btn.css({'display':'block'});
                         return false;
                }).mouseleave(function(){
                        $(this).animate({opacity:0.3},'fast');
                        $btn.css({'display':'none'});
                         return false;
                }).click(function(){
                        //手动点击清除计时器
                        btnClass=this.className;
                        clearInterval(timer);
                        timer=setInterval(move,3000);
                        move($(this),this.className);
                });
                
        $('.index2_box02-circle li').live('click',function(){
                        var index=$('.index2_box02-circle li').index(this);
                        $banner_ul.animate({left:-v_width*index},'slow');
                        page=index+1;
                        cirMove();
                });
});




/*小觅服务*/
$(function(){
	$(".input1").click(function(){
		$(".place_box1").toggle();
		return false;
	});
	$(".place_box2").find("strong").click(function(event){
		var Input=$(this).html();
		$(".input1").val(Input);
		$(".place_box1").hide();
	});				
	$(document).click(function(){
		$(".place_box1").hide();
	});
	
	$(".input2").click(function(){
		$(".index5_2t2").toggle();
		return false;
	});
	$(".index5_2t3 li").click(function(event){
		var Input=$(this).html();
		$(".input2").val(Input);
		$(".index5_2t2").hide();
	});				
	$(document).click(function(){
		$(".index5_2t2").hide();
	});
})


$(function(){
	
	/*搜索科技*/
	var imgshou=true;
	$('.img-shou').click(function(){
		if(imgshou){
			$(this).css({'background':'url(./img/xinh.png) left center no-repeat','color':'red'});
		}else{
			$(this).css({'background':'url(./img/xin.png) left center no-repeat','color':'#999'});
		}
		imgshou=!imgshou;
	})
	
	$('.index3_box .click-li').click(function(){
		$('.index3_box1').hide();
		$('.index3_box1').eq($(this).index()).show();
		$('.index3_box2').hide();
		$('.index3_box2').eq($(this).index()).show();	
		$('.click-li').removeClass('on5');
		$('.click-li').eq($(this).index()).addClass('on5');
	})
	
	$('.index4_3box .onli').hover(function(){
		$('.index4_3box2').hide();
		$('.index4_3box2').eq($(this).index()).show();
		$('.onli').removeClass('in1');
		$('.onli').eq($(this).index()).addClass('in1');
	})
	
	$(".input1").click(function(){
		$(".place_box1").toggle();
		return false;
	});
	$(".place_box2").find("strong").click(function(event){
		var Input=$(this).html();
		$(".input1").val(Input);
		$(".place_box1").hide();
	});				
	$(document).click(function(){
		$(".place_box1").hide();
	});
	
	$(".input1").click(function(){
		$(".place_box1").toggle();
		return false;
	});
	$(".place_box2").find("strong").click(function(event){
		var Input=$(this).html();
		$(".input1").val(Input);
		$(".place_box1").hide();
	});				
	$(document).click(function(){
		$(".place_box1").hide();
	});
	
	$('.baoming').click(function(){
		alert('已报名');	
	})
	
	/*方案页*/
	$('.index7_1box4 .showli').click(function(){
		$('.index7_1box3').hide();
		$('.index7_1box3').eq($(this).index()).show();
		$('.showli').removeClass('in2');
		$('.showli').eq($(this).index()).addClass('in2');
	})
	
	/*科技人员详细页*/
	var LiWidth=$(".index7_1box05 ul li").width();
	var index02=0;
	var length=$(".index7_1box05 ul li").length;
	var ulWidth=808*length;

	$(".index7_1box05 ul").css({width:ulWidth});

	$(".prev1").click(function(){
		if(index02==0){
			index02=length-1;
			showImg01(index02);
		}else{
			index02=index02-1;
			showImg01(index02);
		}
	});
	$(".next1").click(function(){
		if(index02==length-1){
			index02=0;
			showImg01(index02);
		}else{
			index02++;
			showImg01(index02);
		}
	});

	function showImg01(a){
		var left01=-a*808;
		$(".index7_1box05 ul").stop().animate({left:left01+"px"},1000);
	};
	
	/*新建科技需求*/
	$('#button').click(function(){
		$('.box').show();
		$('.box1_1').show();	
	})
	$('.yuyue').click(function(){
		$('.box').show();
		$('.box1_1').show();	
	})
	$('#show').click(function(){
		$('.box').show();
		$('.box3_1').show();	
	})
	$('#close').click(function(){
		$('.box').hide();
		$('.box1_1').hide();
		$('.box1_2').hide();
		$('.box3_1').hide();	
	})
})






































