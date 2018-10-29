<!DOCTYPE html>
<html class="no-js">
<head>
	<title>账户资金</title>
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
	<style type="text/css">
		/*头部积分信息*/
		.cashPoints_box{ display:block; height:auto; padding:1.5rem 1rem; margin:1.5rem auto;clear:both; background:#fff;}
		.cashPoints_box .ico_points{ height:4rem; display:inline-block; margin:0.5rem 0 0;}
		.cashPoints_info{ display:inline-block; padding:1rem 0 0 1rem; vertical-align:top;}
		.cashPoints_info li{ height:1.7rem; line-height:1.7rem; color:#696969; vertical-align:top;}
		.cashPoints_info li var{ color:#ff5c5c;font-weight:bold; padding:0 0 0 0.5rem;}
		.cashPoints_info li:nth-child(2) var{ padding:0 1.5rem;}
		
		/*签到按钮*/
		.signBtn_box{ float:right; }
		.signBtn_box a{ display:block; width:5rem; height:5rem; background: url(${ctxPath}/resource/web/styles/storewap/img/member/sign-btn.png) center bottom no-repeat; background-size:99%; }
		.signBtn_box a.disabled{ background-position:center top;}
		
		/*积分列表*/
		.cashPoints_list{ display:block; text-align: center;  background:#fff;}
		.cashPoints_list table{ border:none;}
		.am-table{border-collapse:collapse;}
		.cashPoints_list table tr th{ width:25%; height:5rem; color:#696969; white-space:nowrap;text-align: center; vertical-align: middle;}
		.cashPoints_list table tr td{ color:#bababa; height:5rem; font-weight:bold; vertical-align: middle; }
		.cashPoints_list table tr td:nth-child(3){ font-weight: normal;}
		
		.am-text-center a{ color:#7a7a7a;}
		
		/*底部TAB*/
		.page_tab{ height:6rem;}
		.page_tab span{ width:100%;padding:5px 0; display:block; position: fixed; bottom:0; left:0; right:0; background:#fff; border-top:solid 1px #ccc; z-index:50; box-shadow: 0px 0px 0.5rem #CCC;}
		.page_tab a{ width:50%; height:4rem; line-height:4rem; display:inline-block; text-align:center; border-right:solid 1px #ccc;}
		.page_tab a:last-child{ border:none;}
	</style>
</head>

<body>
	<div class="cashPoints_box">
		<#if "1" == fundsType>
			<img class="ico_points" src="${ctxPath}/resource/web/styles/storewap/img/member/index/money.png" >
		<#elseif "3" == fundsType>
			<img class="ico_points" src="${ctxPath}/resource/web/styles/storewap/img/member/index/my_money.png" style="padding-top:0.6rem; height:3.5rem;">
		<#elseif "5" == fundsType>
			<img class="ico_points" src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-points.png" >
		<#elseif "7" == fundsType>
			<img class="ico_points" src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-points.png" >
		<#elseif "11" == fundsType>
			<img class="ico_points" src="${ctxPath}/resource/web/styles/storewap/img/member/index/yong.png" >
		<#elseif "21" == fundsType>
			<img class="ico_points" src="${ctxPath}/resource/web/styles/storewap/img/member/index/global.png" style="padding-top:0; height:2.8rem;">
		<#else>
			<img  class="ico_points"src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-points.png" >
		</#if>
		<ul class="cashPoints_info">
			<li>当前${("1"==fundsType)?string('余额', ("3"==fundsType)?string('商币', ("5"==fundsType)?string('赢币', ("7"==fundsType)?string('积分', ("11"==fundsType)?string('佣金',  ("21"==fundsType)?string('分红', '账户'))))))}：
				<var>
					<#if "1" == fundsType>
						${(member.money - member.frozenMoney)?string(",##0.00")}
					<#elseif "3" == fundsType>
						${(member.subMoney - member.frozenSubMoney)?string(",##0.00")}
					<#elseif "5" == fundsType>
						${(member.gold - member.frozenGold)?string(",##0.00")}
					<#elseif "7" == fundsType>
						${(member.integral - member.frozenIntegral)?string(",##0.00")}
					<#elseif "11" == fundsType>
						${(member.brokerageAll)?string(",##0.00")}
					<#elseif "21" == fundsType>
						${(member.brokerageDividend)?string(",##0.00")}
					<#else>
						--
					</#if>
				</var>
			</li>
			<#if "1" != fundsType && "11" != fundsType && "21" != fundsType><li>可抵扣<var>
				<#if "3" == fundsType>
					${(member.subMoney - member.frozenSubMoney)?string(",##0.00")}
				<#elseif "5" == fundsType>
					${(member.gold - member.frozenGold)?string(",##0.00")}
				<#elseif "7" == fundsType>
					${(member.integral - member.frozenIntegral)?string(",##0.00")}
				<#else>
					--
				</#if>
			</var>元</li></#if>
		</ul>
		<#--<span class="signBtn_box"><a  class="disabled"  href="javascript:;" id="cashPoints_checkIn"></a></span>-->
	</div>

	<div class="cashPoints_list">
		<ul class="fundsData " style="display:none;"></ul>
		<div class="loading fundsLoad" style="display:none;">更多数据正涌出来了......</div><div class="PointsData" style="display:none;"></div>
		<table class="am-table am-table-bordered ">
		    <thead>
		        <tr>
		            <th>收入/支出</th>
		            <th>当前结余</th>
		            <th>备注</th>
		            <th>时间</th>
		        </tr>
		    </thead>
		    <tbody class="fundsList">
			</tbody>
		</table>
	</div>
	
	<footer class="wap_footer">
		<ul class="am-avg-sm-4 footer_nav">
			<li><a href="${ctxPath}/index"><img class="idefault" src="${ctxPath}/resource/web/image/icon/home.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/home-ck.png" /><em>首页</em></a></li>
			<li><a href="${ctxPath}/member/cart" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/cart.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/cart-ck.png" /><em>购物车</em></a></li>
			<li><a href="${ctxPath!}/member/index" <#if "1" == fundsType || "5" == fundsType || "7" == fundsType>class="on"</#if>><img class="idefault" src="${ctxPath}/resource/web/image/icon/person.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/person-ck.png" /><em>用户中心</em></a></li>
			<li><a href="${ctxPath!}/member/distribute"  <#if "1" == fundsType || "5" == fundsType || "7" == fundsType><#else>class="on"</#if>><img class="idefault" src="${ctxPath}/resource/web/image/icon/shopping.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/shopping-ck.png" /><em>我的众销</em></a></li>
		</ul>
	</footer>
	
	<#--<div class="page_tab">
		<span>
			<a href="#">特别说明</a><a href="#">积分制度</a>
		</span>
	</div>-->

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
		var data_type="${fundsType!1}";
		$(function(){
			loadData(pageNo, data_type);
		
			//滚到底部继续加载数据
			var $window = $(window);
			var $document = $(document.body);
			//初始分页数据
			$window.scroll(function(){
				var $this = $(this);
				if ( $document.height() - ($this.scrollTop() + $this.height()) < 60 ) {
					 $('.fundsLoad').show();
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
				$('.fundsList').html("");
			}
			 $('.fundsData').load('${ctxPath}/member/funds/' + data_type + '/' + pageNo, function(){
				 $('.fundsLoad').hide();
				 $('.fundsData').children().appendTo( $('.fundsList'));
			});
		}
	</script>
</body>
</html>