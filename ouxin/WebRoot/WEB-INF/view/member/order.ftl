<!DOCTYPE html>
<html class="no-js">
<head>
	<title>我的订单</title>
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
	<style>
		.white_bg{background:#fff;}
		.p20{padding:2rem;}
		.bb1{border-bottom:solid 0.1rem #ccc;}
		.ccc{color:#ccc;}
		.myOrder_list li{margin-top:3rem;background:#fff;}
		.myOrder_goods img{width:7rem;height:7rem;margin-right:1.5rem;border:solid 0.1rem #ccc;float:left;}
		.all_goods{line-height:7rem;}
		.skg_btn,.skg_btn:hover{background:#fff;border:solid 0.1rem #999999;color:#bcbcbc;border-radius:0.5rem;padding:0.5rem 0.8rem;}
		.skg_btn.on,.skg_btn.on:hover{border:solid 0.1rem #c11;color:#ff5c5c;}
	</style>
</head>

<body>
	<div class="tab_frame TabFrame">
		<ul class="tab_header am-avg-sm-3">
			<li class="tab_trigger TabTrigger <#if "0" == orderState>on</#if>"><a href="${ctxPath}/member/order/0">待支付</a></li>
			<li class="tab_trigger TabTrigger <#if "1" == orderState>on</#if>"> <a href="${ctxPath}/member/order/1">待发货</a></li>
			<li class="tab_trigger TabTrigger <#if "2" == orderState>on</#if>"><a href="${ctxPath}/member/order/2">待收货</a></li>
		</ul>
		<div class="tab_body" style="margin-bottom:40px;">
			<div class="">
				<ul class="myOrder_list "></ul>
			</div>
			<div class="loading OrderLoad" style="display:none;">数据正在加载中......</div>
			<div class="OrderData" style="display:none;"></div>
		</div>
	</div>
	
	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>

	<footer class="wap_footer">
		<ul class="am-avg-sm-4 footer_nav">
			<li><a href="${ctxPath}/index"><img class="idefault" src="${ctxPath}/resource/web/image/icon/home.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/home-ck.png" /><em>首页</em></a></li>
			<li><a href="${ctxPath}/member/cart" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/cart.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/cart-ck.png" /><em>购物车</em></a></li>
			<li><a href="${ctxPath!}/member/index" class="on" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/person.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/person-ck.png" /><em>用户中心</em></a></li>
			<li><a href="${ctxPath!}/member/distribute" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/shopping.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/shopping-ck.png" /><em>我的众销</em></a></li>
		</ul>
	</footer>
	
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
		var data_type="${orderState}";
		$(function(){
			loadData(pageNo, data_type);
		
			//滚到底部继续加载数据
			var $window = $(window);
			var $document = $(document.body);
			//初始分页数据
			$window.scroll(function(){
				var $this = $(this);
				if ( $document.height() - ($this.scrollTop() + $this.height()) < 60 ) {
					 $('.OrderLoad').show();
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
				$('.myOrder_list').html("");
			}
			 $('.OrderData').load('${ctxPath}/member/order/' + data_type + '/' + pageNo, function(){
				 $('.OrderLoad').hide();
				 $('.OrderData').children().appendTo( $('.myOrder_list'));
			});
		}
	</script>
</body>
</html>