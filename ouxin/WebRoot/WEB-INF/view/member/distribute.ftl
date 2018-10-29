<#include "toLogin.ftl" />

<!DOCTYPE html>
<html class="no-js">
<head>
	<title>我的众销</title>
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
	<script src="${ctxPath}/resource/web/scripts/storewap/common.js"></script>
	<script src="${ctxPath}/resource/web/scripts/storewap/verifyData.js"></script>
	<style type="text/css">
	
		/*头部信息*/
		.userInfo_box{ height:auto; padding:3rem 0;display:block; background:url(${ctxPath}/resource/web/styles/storewap/img/member/index/bg-memberCenter.png) center top no-repeat; background-size:100% 100%; text-align:center;}
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
		.right_arrows{width:1rem; height:3rem; display:block; position:absolute; right:0px; top:3px; background:url(${ctxPath}/resource/web/styles/storewap/img/member/index/logo-enter.png) center center no-repeat; background-size:100%;}
		
		.logout{ display:block; padding:2rem 0; text-align:center;}
		.logout .abtn{padding:0; width:18rem; height:4rem; line-height:4rem; font-size:1.6rem;}
		
		
		.hasContent{color:#999;float:right;margin-right:2rem;font-size:1.2rem}
		.hasTips{position:relative;}
		.hasTips::before{position:absolute;right:1.2rem;height:0.7rem;width:0.7rem;border-radius:100%;background:#ce1111;content:" "}
		.grade1,.grade2,.grade3,.grade4,.grade5,.grade6{display: inline-block; width: 20px; height: 20px;margin: auto;margin-left:0.4rem;vertical-align: middle;}
		.grade1{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -1px;background-size: 162px;}
		.grade2{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -29px;background-size: 162px;}
		.grade3{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -59px;background-size: 162px;}
		.grade4{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -89px;background-size: 162px;}
		.grade5{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -115px;background-size: 162px;}
		.grade6{ background: url(${ctxPath}/resource/web/styles/storewap/img/member/index/ico_grade.png) no-repeat -142px;background-size: 162px;}
		.red{color:#C11;letter-spacing:2px;}
		
		.address_edit {border-top:0.1rem solid #eee;border-bottom:0.1rem solid #eee;padding-top:0;padding-bottom:0;}
		.addr_select {display:block;height:2.4rem;line-height:2.4rem;}
		.addr_list {overflow:hidden;}
		.addr_list li {border-top:0.1rem solid #eee;line-height:3.6rem;height:3.6rem;padding-left:1.5rem;font-size:1.4rem;}
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
			<li><span><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/money.png" ><p>余 <em class="r">${(bean.money - bean.frozenMoney)!0}</em></p></span></li>
			<li><span><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/my_money.png" style="margin-bottom:0.7rem; padding-top:0.3rem; height:2.3rem;"><p>商币 <em class="r">${(bean.subMoney - bean.frozenSubMoney)!0}</em></p></span></li>
			<li><span><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-points.png" ><p>赢币 <em class="r">${(bean.gold - bean.frozenGold)!0}</em></p></span></li>
			<li><span><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/yong.png" ><p>佣 <em class="r">${bean.brokerageAll!0}</em></p></span></li>
		</ul>
	</div>

	<div class="nav_box_2">
		<ul class="nav_list" id="first_nav">
			<li><a href="${ctxPath}/member/sub/1"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/one_user.png" style="height:2.7rem; padding-right:0.3rem;"><span>合伙人</span><em class="hasContent" style="color:Red;"><i class="grade4" style="margin-top:-2px;"></i> ${bean.oneJkCount!0 + bean.oneSubJkCount!0} <i class="grade3" style="margin-top:-2px;"></i> ${bean.oneYkCount!0 + bean.oneSubYkCount!0} <i class="grade5" style="margin-top:-2px;"></i> ${(bean.onePtCount!0 + bean.oneSubPtCount!0)} </em><i class="right_arrows"></i></a></li>
			<li><a href="${ctxPath}/member/sub/2"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/two_user.png" style="height:2.5rem; padding-right:0.3rem;"><span>代理商</span><em class="hasContent" style="color:Red;"><i class="grade4" style="margin-top:-2px;"></i> ${bean.twoJkCount!0 + bean.twoSubJkCount!0} <i class="grade3" style="margin-top:-2px;"></i> ${bean.twoYkCount!0 + bean.twoSubYkCount!0} <i class="grade5" style="margin-top:-2px;"></i> ${(bean.twoPtCount!0 + bean.twoSubPtCount!0)} </em><i class="right_arrows"></i></a></li>
			<li><a href="${ctxPath}/member/sub/3"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/three_user.png" style="height:2.7rem; padding-right:0.3rem;"><span>业务员</span><em class="hasContent" style="color:Red;"><i class="grade4" style="margin-top:-2px;"></i> ${bean.threeJkCount!0} <i class="grade3" style="margin-top:-2px;"></i> ${bean.threeYkCount!0} <i class="grade5" style="margin-top:-2px;"></i> ${(bean.threePtCount!0)}  </em><i class="right_arrows"></i></a></li>
		</ul>
	</div>

	<div class="nav_box_2">
		<ul class="nav_list">
			<!--<li><a href="point.html"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-points.png" ><span>签到赚积分</span><em class="hasContent">您有20积分,可抵0.10元</em><i class="right_arrows"></i></a></li>-->
			<!--<li><a href="coupon.html"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-coupon.png" ><span>我的优惠券</span><em class="hasContent">您现有优惠券1张</em><i class="right_arrows"></i></a></li>-->
			<li><a href="${ctxPath}/member/funds/3"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/my_money.png" style="padding-top:0.5rem; padding-left:0.3rem; padding-right:0.5rem;  height:2.6rem;"><span>商币明细</span><em class="hasContent">商币明细</em><i class="right_arrows"></i></a></li>
			<li><a href="${ctxPath}/member/funds/11"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/money.png" ><span>佣金明细</span><em class="hasContent">佣金明细</em><i class="right_arrows"></i></a></li>
			<li><a href="${ctxPath!''}/member/deposit/list"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/deposit.png" style=""><span>提现明细</span><em class="hasContent">提现明细列表</em><i class="right_arrows"></i></a></li>
			<li><a href="${ctxPath}/member/funds/21"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/global.png" style="padding-left:0.1rem; padding-right:0.1rem;  height:2.8rem;"><span>全球分红【<span style="color:Red;">${((bean.oneJdCount!0 + bean.oneSubJdCount!0) / 3)?int + ((bean.oneJkCount!0 + bean.oneSubJkCount!0) / 30)?int}</span>份】渠道(<span style="color:Red;">${((bean.oneJdCount!0 + bean.oneSubJdCount!0) / 3)?int}</span>) 金卡(<span style="color:Red;">${((bean.oneJkCount!0 + bean.oneSubJkCount!0) / 30)?int}</span>)</span><em class="hasContent">分红明细</em><i class="right_arrows"></i></a></li>
		</ul>
	</div>
	
	<div class="nav_box_2">
		<ul class="nav_list">
			<li><a data-am-collapse="{parent: '#first_nav', target: '#sub_account'}" href="javascript:void(0);"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/one_user.png" style="height:2.7rem; padding-right:0.3rem;" ><span>我的子账户</span><em class="hasContent">所属的子账户</em><i class="right_arrows"></i></a></li>
			<div class="am-collapse" id="sub_account">
				<li><a href="${ctxPath}/member/mysub/1"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/sub_user.png" style="height:2.7rem; padding-right:0.3rem;"><span>一级子号</span><em class="hasContent" style="color:Red;"><i class="grade4" style="margin-top:-2px;"></i> ${bean.oneSubJkCount!0} <i class="grade3" style="margin-top:-2px;"></i> ${bean.oneSubYkCount!0} <i class="grade5" style="margin-top:-2px;"></i> ${(bean.oneSubPtCount!0)} </em><i class="right_arrows"></i></a></li>
				<li><a href="${ctxPath}/member/mysub/2"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/sub_user.png" style="height:2.7rem; padding-right:0.3rem;"><span>二级子号</span><em class="hasContent" style="color:Red;"><i class="grade4" style="margin-top:-2px;"></i> ${bean.twoSubJkCount!0} <i class="grade3" style="margin-top:-2px;"></i> ${bean.twoSubYkCount!0} <i class="grade5" style="margin-top:-2px;"></i> ${(bean.twoSubPtCount!0)} </em><i class="right_arrows"></i></a></li>
			</div>
			<li><a href="javascript:void(0);" id="addSubAccount"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/sub_add.png" style="padding-top:0.5rem; padding-left:0.8rem; padding-right:0.3rem; height:2.6rem;"><span>添加子账户</span><em class="hasContent">在本账户下添加子账户</em><i class="right_arrows"></i></a></li>
		</ul>
	</div>
	
	<div class="nav_box_2" style="margin-bottom:20px;">
		<ul class="nav_list">
			<li><a href="${ctxPath!''}/member/totransfer"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/transfer.png" style="padding-top:0.5rem; padding-left:0.3rem; padding-right:0.5rem;  height:2.6rem;"><span>商币转账</span><em class="hasContent">转账到另一账户</em><i class="right_arrows"></i></a></li>
			<li><a href="${ctxPath!''}/member/bank"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/bankcard.png" style="padding-top:0.2rem; padding-left:0.2rem; padding-right:0.2rem;  height:2.8rem;"><span>我的银行卡</span><em class="hasContent">提现指定的银行卡</em><i class="right_arrows"></i></a></li>
			<li><a href="${ctxPath!''}/member/todeposit"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/extract.png" style="padding-top:0.2rem; padding-left:0.2rem; padding-right:0.2rem;  height:2.8rem;"><span>余额提现</span><em class="hasContent">申请提现到银行卡</em><i class="right_arrows"></i></a></li>
		</ul>
	</div>
	
	<div class="dialog_frame confirm DialogFrame" id="subAccountBox">		
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox">
			<div class="dialog_body DialogBody">
				<table class="form_table">
					<tr>
						<th style="width:9rem;"><strong>所属账户：</strong></th>
						<td><div class="addr_select checkParent province" attr-id="" >（主账号）</div></td>
						<input type="hidden" id="recommand" name="recommand" value="" />
					</tr>
					<tr>
						<th><strong>子账号：</strong></th>
						<td><input type="text" class="itext" name="account" id="account" value="" /></td>
					</tr>
					<tr>
						<th><strong>密码：</strong></th>
						<td><input type="text"  class="itext" name="password" id="password" value="" /></td>
					</tr>
					<tr style="border:0;">
						<th>&nbsp;&nbsp;</th>
						<td id="subAccountMsg" style="color:Red;">&nbsp;&nbsp;</td>
					</tr>
				</table>
			</div>
			<div class="dialog_footer DialogFooter"><a href="javascript:;" class="abtn confirm addSubConfirm">确定</a><a href="javascript:;" class="abtn default DialogClose">取消</a></div>
		</div>
	</div>
	
	<div class="dialog_frame DialogFrame" id="subAccountListBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox" >
			<div class="dialog_body DialogBody">
				<ul class="addr_list AddressList" id="addrList">
				</ul>
			</div>
		</div>
	</div>
	
	<footer class="wap_footer">
		<ul class="am-avg-sm-4 footer_nav">
			<li><a href="${ctxPath}/index"><img class="idefault" src="${ctxPath}/resource/web/image/icon/home.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/home-ck.png" /><em>首页</em></a></li>
			<li><a href="${ctxPath}/member/cart" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/cart.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/cart-ck.png" /><em>购物车</em></a></li>
			<li><a href="${ctxPath!}/member/index" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/person.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/person-ck.png" /><em>用户中心</em></a></li>
			<li><a href="${ctxPath!}/member/distribute" class="on" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/shopping.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/shopping-ck.png" /><em>我的众销</em></a></li>
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
		
		$(function(){
			$(document).on("change", "#account", function(){
				if("" != $(this).val())
				{
					$.ajax({
						type: "POST",
						url: '${ctxPath}/member/exist/${bean.code}/' + $(this).val(),
						success : function(data){
							var res = $.parseJSON(data);
							if(res.statusCode == "200"){
								if(res.result || "true" == res.result) $('#subAccountMsg').html( "账户已存在！" );
								else $('#subAccountMsg').html( "" );
							}else{
								$('#subAccountMsg').html( res.msg );
							}
						}
					});
				}
			});
		
			var subAccountBox = $('#subAccountBox');
			$('#addSubAccount').click(function(){
				dialogShow(subAccountBox);
			});
			
			$('.checkParent').click(function(){
				var $this = $(this);
				var addrList = $('#addrList');
				addrList.html('<li class="checkSubItem" attr-id="" key="">（主账号）</li>');
				$.ajax({
					type: "POST",
					url: '${ctxPath}/member/one/sub',
					success:function(result){
						if( 0 < result.length)
						{
							var data=$.parseJSON(result);
							var obj;
							for(var idx in data)
							{
								obj = data[idx];
								addrList.append('<li class="checkSubItem" attr-id="'+ obj.id +'" key="'+ obj.id +'">'+ obj.account +'</li>');
							}
							$this.addClass('on');
							dialogShow($('#subAccountListBox'));
						}
					}, 
					error : function(result){
						var data=$.parseJSON(result);
						showTipsBox( res.msg );
					}
				})
			});
			
			$('.addSubConfirm').click(function(){
				var recommand = $('#recommand').val();
				var mobile = $('#account').val();
				var password = $('#password').val();
				if("" == mobile) $('#subAccountMsg').html("请输入账户名");
				if(""==password) $('#subAccountMsg').html("密码不能为空");
				if( 6 > password.length) $('#subAccountMsg').html("密码不得少于6位数");
				$.ajax({
					type : 'POST',
					url : '${ctxPath}/member/register/sub',
					data : {
						'account' : mobile,
						'password' : password,
						'recommand' : recommand
					},
					success : function(data){
						var res = $.parseJSON(data);
						if(res.statusCode == "200"){
							alert("注册成功");
							dialogClose(subAccountBox);
						}else{
							$('#subAccountMsg').html( res.msg );
						}
					},
					error : function(res){
						$('#subAccountMsg').html( res.msg );
					}
				});
			})
			
			$('#addrList').on('click', '.checkSubItem', function(){
				var $this = $(this);
				var $checkParent = $('.checkParent');
				
				var id = $this.attr("attr-id");
				$checkParent.closest("tr").next().find(".AddressDetail").attr("attr-parentId", id);
				$checkParent.closest("tr").nextAll().find(".AddressDetail").text("");
				$checkParent.text($this.text());
				$checkParent.attr("attr-id", id);
				
				$('#recommand').val(id);
				
				dialogClose($('#subAccountListBox'));
				$('.checkParent').removeClass('on');
			});
		});
	</script>
</body>
</html>