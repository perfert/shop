<!DOCTYPE html>
<html class="no-js">
<head>
	<title>我的 ${("1"==layer)?string('一级会员', ("2"==layer)?string('二级会员', ("3"==layer)?string('三级会员', '--')))}</title>
	<meta charset="utf-8">
	<meta http-equiv="Cache-Control" content="no-siteapp"/>
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="email=no">
	<meta name="format-detection" content="address=no">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" href="${ctxPath}/resource/web/styles/storewap/css/amazeui.min.css">
	<link rel="stylesheet" href="${ctxPath}/resource/web/styles/storewap/css/common.css">
	<script src="${ctxPath}/resource/web/scripts/jquery/jquery-1.11.2.min.js"></script>
	<script src="${ctxPath}/resource/web/scripts/storewap/amazeui.min.js"></script>
	<script src="${ctxPath}/resource/web/scripts/util/jquery.cookie.js"></script>
	<script src="${ctxPath}/resource/web/scripts/jqueryQrcode/jquery.qrcode.min.js"></script>
	<script src="${ctxPath}/resource/web/scripts/storewap/pop.js"></script>
	<script>
		var _hmt = _hmt || [];
		(function() {
			var hm = document.createElement("script");
			hm.src = "//hm.baidu.com/hm.js?a20a1085f6b134c671e0089abf515716";
			var s = document.getElementsByTagName("script")[0]; 
			s.parentNode.insertBefore(hm, s);
		})();
	</script>
	<script src="${ctxPath}/resource/web/scripts/storewap/common.js"></script>
	<style type="text/css">
		
		.data_wrap .wrap_blocko {display:block;overflow:hidden;text-align:right; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;}
		.addr_add {text-align:center;border-top:0.1rem solid #eee;border-bottom:0.1rem solid #eee;}
		.addr_add i:after {border:0.1rem solid #c11;content:"\f067";display:inline-block;width:2.2rem;height:2.2rem;color:#e11;border-radius:2.2rem;margin-right:0.5rem;}
		
		.addr_list {border-bottom:0.1rem solid #eee;margin-top:1rem;}
		.addr_list li {border-top:0.1rem solid #eee;padding:0;overflow:hidden;}
		.addr_list .data_wrap {margin:0;}
		.data_wrap .date_more{width:4rem;}
				
		.address_edit {border-top:0.1rem solid #eee;border-bottom:0.1rem solid #eee;padding-top:0;padding-bottom:0;}
		.addr_select {display:block;height:2.4rem;line-height:2.4rem;}
		.addr_list {overflow:hidden;}
		.addr_list li {border-top:0.1rem solid #eee;line-height:3.6rem;height:3.6rem;padding-left:1.5rem;font-size:1.4rem;}
		
		.order_payment {line-height:2.6rem;border-bottom:0.1rem dotted #eee;}
		.order_payment .pay_label {display:inline-block;margin-top:1rem;}
		.order_payment .pay_items {margin-top:-1rem;}
		.order_payment .pay_items a {border:0.1rem solid #eee;float:right;margin:1rem 0 0 1rem;}
		.order_payment .pay_items a.on {border:0.1rem solid #c11;}
		.order_payment .pay_items img {display:block;height:2.6rem;}
		
		.order_receipt {margin-top:0;}
		
		.iosCheck {border:0.1rem solid #eaeaea;height:2.6rem;width:4.6rem;border-radius:2.6rem;position:relative;}
		.iosCheck i {position:absolute;top:-0.1rem;width:2.6rem;height:2.6rem;border:0.1rem solid #ccc;border-radius:2.6rem;background:#fff;}
		.iosCheck.off {background:#fcfcfc;}
		.iosCheck.off i {left:-0.1rem;box-shadow:0.1rem 0 0.5rem #bbb;}
		.iosCheck.on {background:#c11;}
		.iosCheck.on i {right:-0.1rem;box-shadow:-0.1rem 0 0.5rem #bbb;}
		/*
		
		.iosCheck b {position:absolute;left:-0.1rem;top:-0.1rem;background:#c11;width:200%;height:200%;}
		.iosCheck i {position:absolute;top:-0.1rem;width:2.6rem;height:2.6rem;border:0.1rem solid #ccc;border-radius:2.6rem;background:#fff;}
		
		.iosCheck.off b {opacity:0;filter:alpha(opacity=0);}
		.iosCheck.off i {left:-0.1rem;box-shadow:0.1rem 0 0.5rem #bbb;}
		.iosCheck.on b {opacity:1;filter:alpha(opacity=100);}
		.iosCheck.on i {right:-0.1rem;box-shadow:-0.1rem 0 0.5rem #bbb;}
		*/
		
		.order_cart {margin-top:1rem;box-shadow:0 0 0.5rem #ccc;}
		.order_cart .cart_title {background:#aaa;color:#fff;padding:0 1.2rem;height:4rem;line-height:4rem;font-size:1.6rem;}
		.order_cart .cart_title .ico {height:2rem;margin:1rem 1rem 0 0;float:left;}
		.order_cart .cart_con {overflow:hidden;}
		
		.cart_list {margin-top:-0.1rem;padding:0 1.2rem;}
		.cart_list li {padding:1rem 0;border-top:0.1rem dotted #dfdfdf;}
		.cart_list li.packageItem{border-top:none;padding:0 0 0.5rem 0}
		.cart_list .gcheck {float:left;height:6.4rem;}
		.cart_list .icheck {float:left;margin:2.2rem 1rem 0 0;}
		.cart_list .icheck input {display:none;}
		
		.cart_list .gimg {float:left;height:6.4rem;display:block;margin-right:1rem;}
		.cart_list .ginfo {display:block;overflow:hidden;color:#5d5b5b;font-size:1.4rem;line-height:1.6rem;height:6.4rem;}
		.cart_list .gname {max-height:3.2rem;overflow:hidden;}
		.cart_list .gsku {color:#9a9a9a;padding-right:5rem;font-size:1.2rem;}
		.cart_list .gsku em {margin-right:0.6rem;display:inline;}
		.cart_list .gcount {float:right;margin-left:1rem;text-align:right;}
		.cart_list .gprice {color:#ff0000;}
		.cart_list .goldPrice {color:#bdbdbd;}
		.cart_list .sku_select.on {float:left;border:0.1rem solid #ccc;margin-top:0.3rem;padding:0.2rem 0.5rem;line-height:1.8rem;cursor:pointer;}
		.cart_list .sku_select.on:after {content:'\f107';}
		.cart_list .sku_select.editing {border:0.1rem solid #c11;}
		
		.order_count {line-height:2.6rem;}
		.order_count em {color:#c11;}
		
		.order_footer { background:#fff;padding:1rem 1.2rem;line-height:1.6rem;font-size:1.4rem;box-shadow:0 0 0.5rem #ccc;position:fixed;left:0;right:0;bottom:0;width:100%;}
		.order_footer em {color:#c11;}
		.order_footer .abtn {float:right;height:3.2rem;line-height:3.2rem;padding:0 2rem;font-size:1.6rem;}
		.order_footer .footer_left {float:left;}
		
		.setMeal{padding-left:3rem;font-size:1.4rem;}
		.setMealTitle{line-height:2rem;padding:.5rem 0;}
		
		.grade1,.grade2,.grade3,.grade4,.grade5,.grade6{display: inline-block; width: 20px; height: 20px;margin: auto;margin-left:0.4rem;vertical-align: middle;}
		.grade1{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -1px;background-size: 162px;}
		.grade2{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -29px;background-size: 162px;}
		.grade3{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -59px;background-size: 162px;}
		.grade4{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -89px;background-size: 162px;}
		.grade5{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -115px;background-size: 162px;}
		.grade6{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -142px;background-size: 162px;}
	</style>
</head>

<body>
	<header class="header_white">
		<div class="header_left">
			<a href="javascript:history.back(-1);" class="header_btn"><img src="${ctxPath}/resource/web/styles/storewap/img/common/arrow-left.png"></a>
		</div>
		<div class="header_title">${("1"==layer)?string('一级会员', ("2"==layer)?string('二级会员', ("3"==layer)?string('三级会员', '--')))}</div>
	</header>
	<div class="">
		<div class="order_cart">
			<div class="cart_con">
				<ul class="memberData " style="display:none;"></ul>
				<div class="loading memberLoad" style="display:none;">更多数据正涌出来了......</div><div class="PointsData" style="display:none;"></div>
				<ul class="cart_list memberList">
					
				</ul>
			</div>
		</div>
	</div>
	
	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>
	
	<script type="text/javascript">
		$(function(){
			(function(){
				var $window = $(window);
				var $WapToolbar = $('.WapToolbar');
				var $GoToTop = $('.GoToTop');
				$window.scroll(function(){
					scrollTopCheck();
				});
		
				function scrollTopCheck(){
					if ( $window.scrollTop() > $window.height() ) {
						$GoToTop.show();
					}else {
						$GoToTop.hide();
					}
				}
				scrollTopCheck();
		
				$GoToTop.click(function(){
					$window.scrollTop(0);
				});
			})();
		});
		
		var pageNo=0;
		var data_type="${layer!1}";
		$(function(){
			loadData(pageNo, data_type);
		
			//滚到底部继续加载数据
			var $window = $(window);
			var $document = $(document.body);
			//初始分页数据
			$window.scroll(function(){
				var $this = $(this);
				if ( $document.height() - ($this.scrollTop() + $this.height()) < 60 ) {
					 $('.memberLoad').show();
					 pageNo ++;
					loadData(pageNo, data_type);
				}
			});
		})
		
		function loadData(pn, dt)
		{
			pageNo = pn;
			data_type = dt;
			if(pageNo==1){
				$('.memberList').html("");
			}
			 $('.memberData').load('${ctxPath}/member/sub/' + data_type + '/' + pageNo, function(){
				 $('.memberLoad').hide();
				 $('.memberData').children().appendTo( $('.memberList'));
			});
		}
	</script>
</body>
</html>