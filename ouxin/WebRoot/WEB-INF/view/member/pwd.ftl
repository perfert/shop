<!DOCTYPE html>
<html class="no-js">
<head>
	<title>登录密码</title>
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
	<link rel="stylesheet" href="${ctxPath}/resource/web/styles/storewap/css/login.css">
	<script src="${ctxPath}/resource/web/scripts/storewap/verifyData.js"></script>
	<script type="text/javascript" src="${ctxPath}/resource/web/scripts/storewap/member/login.js"></script>
	<script type="text/javascript" src="${ctxPath}/resource/web/scripts/util/md5.js"></script>
	<script src="${ctxPath}/resource/web/scripts/storewap/common.js"></script>
	
	<style type="text/css">
		.passwordLogin {
			padding: 0 1.5rem;
			margin: 1.5rem 0 0;
			border-bottom: solid 1px #ccc;
			border-top: solid 1px #ccc;
			background: #fff;
			font-size: 1.4rem;
		}
		
		.passwordLogin li {
			padding: 0.7rem 0;
			border-bottom: solid 1px #ddd;
			display: -webkit-box;
			display: box;
			display: flex;
			display: -webkit-flex;
			display: -ms-flexbox;
		}
		
		.passwordLogin li:last-child {
			border: none;
		}
		
		span.title {
			width: 7rem;
			height: 3rem;
			line-height: 3rem;
			text-align: right;
			display: inline-block;
		}
		
		span.flex {
			width: 100%;
			display: block;
			-webkit-box-flex: 1;
			box-flex: 1;
			-webkit-flex: 1;
			flex: 1;
		}
		
		span.flex input,span.flex a {
			width: 100%;
			height: 3rem;
			line-height: 3rem;
			display: block;
			border: none;
			color: #000;
			background: none;
			position: relative;
			color: #353535;
		}
		
		span.flex a:after {
			content: "";
			width: 1rem;
			height: 3rem;
			display: block;
			position: absolute;
			right: 0px;
			top: 0;
			background:
				url(../resource/web/styles/storewap/img/member/index/logo-enter.png) center
				center no-repeat;
			background-size: 100%;
		}
		
		.check_box {
			height: 3rem;
			line-height: 3rem;
		}
		
		.icheck {
			overflow: hidden;
			margin: 0.5rem 0.5rem 0 1rem;
			vertical-align: top;
		}
		
		.submit {
			display: block;
			padding: 2rem 0;
			text-align: center;
		}
		
		.submit .abtn {
			padding: 0;
			width: 18rem;
			height: 4rem;
			line-height: 4rem;
			font-size: 1.6rem;
		}
		
		.btn_refresh {
			height: 3rem;
			display: inline-block;
			border-left: solid 1px #ddd;
		}
		
		.btn_refresh a {
			height: 3rem;
			padding: 0 3rem 0 1rem;
			display: block;
			background: url(../resource/web/styles/storewap/img/member/ico-refresh.png)
				right center no-repeat;
			background-size: 1.8rem auto;
		}
		
		.btn_refresh a img {
			height: 3rem;
		}
	</style>
</head>

<body>

	<div class="RegisterCheck">
		<ul class="passwordLogin ">
			<li>
				<span class="title">手机号：</span><span class="flex"><input class="itext" type="text" value="${bean.code}-${bean.mobile}"  readOnly/></span>
				<#--<a href="javascript:;" class="mobileCaptha_btn abtn confirm GetMobileCaptha" attr-flag="register" style="position:static;"><em class="CheckDefault">发送验证码</em><span class="CheckActive">已发送<var class="CountDown">60</var></span></a>-->
			</li>
			<li><span class="title">验证码：</span><span class="flex"><input class="itext" type="text" placeholder="手机验证码"  verify-data="smsCaptha" value="7123"/></span></li>
			<li><span class="title">新密码：</span><span class="flex"><input type="password" onselectstart="return false;" onpaste="return false" placeholder="请输入新密码" verify-data="password" /></span></li>
			<li><span class="title">确认密码：</span><span class="flex"><input type="password" placeholder="请再次输入新密码" verify-data="rePassword"></span></li>
		</ul>
		<div class="submit">
			<a href="javascript:;" class="abtn confirm VerifySubmit">提交</a>
		</div>
	</div>

	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>

	<script>
		$(function() {
			$("input:password").bind("copy cut paste", function(e) {
				return false;
			})

			$('.RegisterCheck').verifyData({
				rules : {
					smsCaptha : {
						require : true
					}
				},
				msg : {
					smsCaptha : {
						require : '请输入验证码！'
					}
				},
				dealResult : function(verifyResult) {
					if (!verifyResult.status) {
						showTipsBox(verifyResult.msg);
					}
				},
				success : function() {
					//submitRegister(this);
					//这里返回的this为验证域的对象，也就是$('.RegisterCheck')
					//可根据这个对象找到对应的资源进行二次操作
					//console.log(this);
					formSbmitFunction($(this));
				}
			});
			
		});
	
		$('.GetMobileCaptha').click(function(){
			$this = $(this);
			$CountDown = $('.CountDown');
			if ( $this.hasClass('disabled') ) {
				return false;
			}
			if( ! $this.hasClass('disabled') )
			{
				var second = 60;
				$this.addClass('disabled on');
				function count(){
					second --;
					$CountDown.text(second);
					if ( second == 0 ) {
						clearTimeout(t);
						$this.removeClass('disabled on');
					}
				}
				var t = setInterval(count, 1000);
			}
			$.ajax({
				type : 'POST',
				url : "${ctxPath!}/ssms/rest/pwd",
				success : function(data){
					var res = $.parseJSON(data);
					if(res.statusCode == "200"){
						showTipsBox(res.msg);
					}else{
						showTipsBox( res.msg );
					}
				},
				error : function(res){
					showTipsBox('发送验证码异常！');
				}
			});
		});
	
		function formSbmitFunction(formData){
			var smsCaptha = formData.find("[verify-data=smsCaptha]").val();
			var password = formData.find("[verify-data=password]").val();
			var oldPwd = formData.find("[verify-data=oldPassword]").val();
			$.ajax({
				type : 'POST',
				url : "${ctxPath}/member/updatePassword",
				data : {
					'password' : password,
					'smsCaptha' : smsCaptha
				},
				success : function(data){
					var res = $.parseJSON(data);
					if(res.statusCode == "200"){
						alert('重置密码成功');
						window.location = "${ctxPath}/member/index";
					}else{
						showTipsBox('<strong>'+res.msg+'</strong>');
					}
				},
				error : function(res){
					showTipsBox('<strong>'+res.msg+'</strong>');
				}
			});
		} 
	</script>
</body>
</html>