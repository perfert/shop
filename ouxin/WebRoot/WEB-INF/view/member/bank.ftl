<!DOCTYPE html>
<html class="no-js">
<head>
	<title>我的银行卡</title>
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
	</style>
</head>

<body>

	<div class="RegisterCheck">
		<ul class="passwordLogin ">
			<li>
				<span class="title">手机号：</span><span class="flex"><input class="itext" type="text" value="${my.code}-${my.mobile}"  readOnly/></span>
				<#--<a href="javascript:;" class="mobileCaptha_btn abtn confirm GetMobileCaptha" attr-flag="register" style="position:static;"><em class="CheckDefault">发送验证码</em><span class="CheckActive">已发送<var class="CountDown">60</var></span></a>-->
			</li>
			<li><span class="title">验证码：</span><span class="flex"><input class="itext" type="text" placeholder="手机验证码"  verify-data="smsCaptha" value="7123"/></span></li>
			<li><span class="title">所属银行：</span>
				<span class="flex"><input class="itext" type="text" placeholder="选择银行"  verify-data="bankName" readonly="readonly" id="bankName" value="${bean.bankName}"/><input type="hidden" id="bankId" value="${bean.bankId}" verify-data="bankId"/><input type="hidden"  value="${bean.id}" verify-data="beanId"/></span>
			</li>
			<li><span class="title">所属支行：</span><span class="flex"><input type="text" placeholder="请输入支行名称" verify-data="bankSubName" value="${bean.bankSubName}"></span></li>
			<li><span class="title">银行卡号：</span><span class="flex"><input type="text" placeholder="请输入银行卡号" verify-data="bankCardCode" value="${bean.bankCardCode}"></span></li>
			<li><span class="title">持卡人：</span><span class="flex"><input type="text" placeholder="银行卡持卡人姓名" verify-data="bankCardName" value="${bean.bankCardName}"></span></li>
		</ul>
		<div class="submit">
			<a href="javascript:;" class="abtn confirm VerifySubmit">${("" == (bean.id + '')!'')?string('设置银行卡', '更新银卡')}</a>
		</div>
	</div>

	<div class="dialog_frame DialogFrame" id="bankDetailBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox" >
			<div class="dialog_body DialogBody">
				<ul class="addr_list AddressList" id="bankList">
					<#if banks?? && 0 lt banks?size>
						<#list banks as item>
							<li class="bankItem" attr-id="${item.id}" key="${item.id}">${item.name}</li>
						</#list>
					</#if>
				</ul>
			</div>
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
			
			
			var $bankName = $('#bankName');
			var $bankDetailBox = $('#bankDetailBox');
			var $bankList = $('#bankList');
			//支付方式
			$bankName.click(function(){
				dialogShow($bankDetailBox);
			});
			$bankList.on('click', '.bankItem', function(){
				var $this = $(this);
				var id = $this.attr("attr-id");
				$bankName.attr('value', $this.text());
				$('#bankId').attr('value', id);
				dialogClose($bankDetailBox);
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
				url : "${ctxPath!}/ssms/modify/bank",
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
			var beanId = formData.find("[verify-data=beanId]").val();
			var smsCaptha = formData.find("[verify-data=smsCaptha]").val();
			var bankId = formData.find("[verify-data=bankId]").val();
			var bankSubName = formData.find("[verify-data=bankSubName]").val();
			var bankCardCode = formData.find("[verify-data=bankCardCode]").val();
			var bankCardName = formData.find("[verify-data=bankCardName]").val();
			
			if( "" == bankId ) {
				showTipsBox( "请选择银行！" );
				return false;
			}
			if( "" == bankSubName ) {
				showTipsBox( "请输入支行名称！" );
				return false;
			}
			if( "" == bankCardCode ) {
				showTipsBox( "请输入银行卡号！" );
				return false;
			}
			if( "" == bankCardName ) {
				showTipsBox( "请输入持卡人姓名！" );
				return false;
			}
			
			$.ajax({
				type : 'POST',
				url : "${ctxPath}/member/bank",
				data : {
					'bean.id' : beanId,
					'smsCaptha' : smsCaptha,
					'bean.bankId' : bankId,
					'bean.bankSubName' : bankSubName,
					'bean.bankCardCode' : bankCardCode,
					'bean.bankCardName' : bankCardName
				},
				success : function(data){
					var res = $.parseJSON(data);
					if(res.statusCode == "200"){
						alert('绑定银行卡成功');
						window.location = "${ctxPath}/member/distribute";
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