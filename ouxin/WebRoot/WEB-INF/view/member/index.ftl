<#include "toLogin.ftl" />

<!DOCTYPE html>
<html class="no-js">
<head>
	<title>用户中心</title>
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
	<link rel="stylesheet" href="${ctxPath}/resource/web/styles/storewap/css/index.css">
	<script type="text/javascript">var _ctxPath="${ctxPath!}";</script>
	<script type="text/javascript" src="${ctxPath}/resource/web/scripts/storewap/member/login.js"></script>
	<style type="text/css">
	
		/*头部信息*/
		.userInfo_box{ height:auto; padding:3rem 0;display:block; background:url(../resource/web/styles/storewap/img/member/index/bg-memberCenter.png) center top no-repeat; background-size:100% 100%; text-align:center;}
		.userInfo_head{ height:6rem; display:block;}
		.userInfo_head a{ width:6rem; height:6rem; display:inline-block; border-radius:6rem;}
		.userInfo_head a img{ width:100%; border-radius:6rem;}
		.userInfo_name_level{ display:inline-block; padding:0.5rem 0 0 0.5rem; font-size:1.4rem; margin:0; clear:both; text-align:left; vertical-align:top;}
		.userInfo_name{ height:1.5rem; display:block;}
		.userInfo_name a { display:block; color:#fff;font-size:1.6rem;text-align: center;}
		.userInfo_level{color:#C0C0C0;font-size:1.6rem;text-align: center;margin-top:0.5rem;}
		
		/*主要菜单*/
		.nav_box{ padding:0 1rem; display:block; /* border-bottom:solid 1px #ddd; */ background:#fff;}
		
		/* .nav_main{border-bottom:solid 1px #ccc;} */
		.nav_main li{ text-align:center; border-bottom:solid 1px #ccc;padding:0.6rem 0 1.8rem 0;}
		.nav_main li a, .nav_main li span{height:3.5rem; display:block; line-height:3.6rem; text-align:left; }
		.nav_main li p{line-height:1rem; font-size:1.5rem;text-align: center; }
		.nav_main li .r { color:#c11; }
		.nav_main li img{ height:3rem; margin:auto; display:block;}
		
		
		/*菜单列表*/
		.nav_box_2{padding:0 1rem; margin-top:1.3rem; /* border-bottom:solid 1px #ddd; border-top:solid 1px #ddd;  */background:#fff;}
		.nav_list{}
		.nav_list li{border-bottom:solid 1px #ccc;padding:0.7rem 0;}
		.nav_list li:last-child{ border-bottom:none;}
		.nav_list li a{height:3.5rem; display:block; line-height:3.6rem; text-align:left; position:relative; }
		.nav_list li a span{ display:inline-block; vertical-align:top; font-size:1.6rem;}
		.nav_list li a img{ height:3rem;  display:inline-block;margin:0.3rem 0 0 0;;}
		
		/* .nav_list li a:after{ content:""; ; } */
		.right_arrows{width:1rem; height:3rem; display:block; position:absolute; right:0px; top:3px; background:url(../resource/web/styles/storewap/img/member/index/logo-enter.png) center center no-repeat; background-size:100%;}
		
		.logout{ display:block; padding:2rem 0; text-align:center;}
		.logout .abtn{padding:0; width:18rem; height:4rem; line-height:4rem; font-size:1.6rem;}
		
		
		.hasContent{color:#999;float:right;margin-right:2rem;font-size:1.2rem}
		.hasTips{position:relative;}
		.hasTips::before{position:absolute;right:1.2rem;height:0.7rem;width:0.7rem;border-radius:100%;background:#ce1111;content:" "}
		.grade1,.grade2,.grade3,.grade4,.grade5,.grade6{display: inline-block; width: 20px; height: 20px;margin: auto;margin-left:0.4rem;vertical-align: middle;}
		.grade1{ background: url(../resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -1px;background-size: 162px;}
		.grade2{ background: url(../resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -29px;background-size: 162px;}
		.grade3{ background: url(../resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -59px;background-size: 162px;}
		.grade4{ background: url(../resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -89px;background-size: 162px;}
		.grade5{ background: url(../resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -115px;background-size: 162px;}
		.grade6{ background: url(../resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -142px;background-size: 162px;}
		.red{color:#C11;letter-spacing:2px;}
	</style>
</head>

<body>
	<div class="userInfo_box">
		<span class="userInfo_head"><a href="javascript:;"><img src="${("" != (bean.logo!'')?trim)?string(bean.logo, ctxPath + '/resource/web/styles/storewap/img/member/noface.png')}" /></a></span>
		<ul class="userInfo_name_level">
			<li class="userInfo_name"><a href="javascript:;">${bean.nickName!}</a></li>
			<li class="userInfo_level red">${(4==bean.level)?string('普通会员', (3==bean.level)?string('银卡会员', (2==bean.level)?string('金卡会员', '渠道合伙人')))}<i class="${(4==bean.level)?string('grade5', (3==bean.level)?string('grade3', (2==bean.level)?string('grade4', 'grade6')))}"></i></li>
		</ul>
	</div>

	<div class="nav_box">
		<ul class="nav_main am-avg-sm-4">
			<li><a href="${ctxPath}/member/order/0"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-waitPay.png" ><p>待支付</p></a></li>
			<li><a href="${ctxPath}/member/order/2"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-waitReceipt.png" ><p>待收货</p></a></li>
			<li><span><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/money.png" ><p>余 <em class="r">${(bean.money - bean.frozenMoney)!0}</em></p></span></li>
			<li><span><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-points.png" ><p>赢币 <em class="r">${(bean.gold - bean.frozenGold)!0}</em></p></span></li>
		</ul>
		<ul class="nav_list">
			<li><a href="${ctxPath}/member/order/0"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-orders.png" ><span>我的订单</span> <i class="right_arrows"></i><em class="hasContent">查看全部订单</em></a></li>
			<li><a href="${ctxPath}/yyg/order"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-orders.png" ><span>我的一元赢购</span> <i class="right_arrows"></i><em class="hasContent">查看全部一元赢购记录</em></a></li>
		</ul>
	</div>


	<div class="nav_box_2">
		<ul class="nav_list">
			<!--<li><a href="point.html"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-points.png" ><span>签到赚积分</span><em class="hasContent">您有20积分,可抵0.10元</em><i class="right_arrows"></i></a></li>-->
			<!--<li><a href="coupon.html"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-coupon.png" ><span>我的优惠券</span><em class="hasContent">您现有优惠券1张</em><i class="right_arrows"></i></a></li>-->
			<li><a href="${ctxPath}/member/funds/1"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/money.png" ><span>余额明细</span><em class="hasContent">余额、佣金等明细</em><i class="right_arrows"></i></a></li>
			<li><a href="${ctxPath}/member/funds/5"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-points.png" ><span>赢币明细</span><em class="hasContent">赢币明细</em><i class="right_arrows"></i></a></li>
		</ul>
	</div>
	
	<div class="nav_box_2">
		<ul class="nav_list">
			<li><a data-am-collapse="{parent: '#first_nav', target: '#sub_info'}" href="javascript:void(0);"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-passwordLoginInfo.png" ><span>安全信息</span> <em class="hasContent">账户/密码 安全信息</em></a></li>
			<div class="am-collapse" id="sub_info">
				<li><a href="${ctxPath}/member/pwd"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/key.png" style="padding-left:0.5rem; margin-top:0.8rem; height:1.8rem; padding-right:0.8rem;"><span>登录密码</span><i class="right_arrows"></i></a></li>
				<li><a href="${ctxPath}/member/paypwd"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/key.png" style="padding-left:0.5rem; margin-top:0.8rem; height:1.8rem; padding-right:0.8rem;"><span>支付密码</span><i class="right_arrows"></i></a></li>
			</div>
			<li><a href="${ctxPath}/weichat/${bean.id}/qrcode" id="addSubAccount"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/qrcode.png" style="padding-top:0.2rem; padding-left:0.4rem; padding-right:0.3rem; height:2.6rem;"><span>推广二维码</span><em class="hasContent">我的推广二维码</em><i class="right_arrows"></i></a></li>
			<li><a href="#"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-guanyu.png" ><span>关于我们</span></a></li>
		</ul>
	</div>

	<div class="logout">
		<a class="abtn " onclick="logout()"  href="javascript:;">退出当前帐号</a>
	</div>
	
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
	</script>
</body>
</html>