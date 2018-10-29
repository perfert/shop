<!DOCTYPE html>
<html class="no-js">
<head>
	<title>登录/注册</title>
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
		.address_edit {border-top:0.1rem solid #eee;border-bottom:0.1rem solid #eee;padding-top:0;padding-bottom:0;}
		.addr_select {display:block;height:2.4rem;line-height:2.4rem;}
		.addr_list {overflow:hidden;}
		.addr_list li {border-top:0.1rem solid #eee;line-height:3.6rem;height:3.6rem;padding-left:1.5rem;font-size:1.4rem;}
	</style>
</head>

<body>
	<input type="hidden" id="loginRegister_source" name="source" value="/member/memberInfo.htm"/>
	<input type="hidden" id="loginRegister_direct" name="direct" value=""/>
	<input type="hidden" id="loginRegister_callType" name="callType" value=""/>
	<input type="hidden" id="loginRegister_directCartId" name="directCartId" value=""/>
	
	<input type="hidden" id="isWeixin" name="isWeixin" value="0"/>
	
	<div class="tab_frame TabFrame pass_frame">
		<ul class="tab_header clearfix">
			<li class="tab_trigger TabTrigger "><a href="javascript:;" class="register">注 册</a><i></i></li>
			<li class="tab_trigger TabTrigger on"><a href="javascript:;" class="login">登 录</a><i></i></li>
		</ul>
		<div class="tab_body">
			<div class="tab_item TabItem  RegisterCheck">
				<div class="pass_box">
					<table class="form_table">
						<tr>
							<td>
								<input class="itext" type="text" placeholder="国际码"  id="loginRegister_registerMobile_Code" verify-data="code" value="86"  maxlength="4" style="width:50px;" />
								<input class="itext" type="text" placeholder="请输入手机号码" value = "15817005649"  id="loginRegister_registerMobile" verify-data="mobile"  maxlength="13"  style="width:70%; border-left:1px solid #e5e5e5;" />
								<#--<a href="javascript:;" class="mobileCaptha_btn abtn confirm GetMobileCaptha" attr-flag="register"><em class="CheckDefault">发送验证码</em><span class="CheckActive">已发送<var class="CountDown">60</var></span></a>-->
							</td>
						</tr>
						<#--<tr>
							<td>
								<input class="itext" type="text" id="captchaCode" placeholder="输入图片验证码"  verify-data="captha"/>
								<img id="captcha" title="点击刷新" src="${ctxPath!''}/capatch" class="mobileCaptha_btn confirm" style="width:90px; float:right; cursor:pointer;"/>
							</td>
						</tr>-->
						<tr>
							<td><input class="itext" type="text" placeholder="手机验证码"  verify-data="smsCaptha" value="7123"/></td>
						</tr>
						<tr>
							<td><input class="itext" type="password" value = "123456" placeholder="请填写密码" verify-data="password" /></td>
						</tr>
						<tr>
							<td><input class="itext" type="password" value = "123456" placeholder="请再次填写密码" verify-data="rePassword" /></td>
						</tr>
						<tr style="border:0;">
							<td>
								<input class="itext" type="text" placeholder="国际码" verify-data="recommandCode" value="86"  maxlength="4" style="width:50px;" />
								<input class="itext" type="text" placeholder="推荐人手机号码" verify-data="recommand" value="${pcode!''}" style="width:70%; border-left:1px solid #e5e5e5;" />
							</td>
						</tr>
					</table>
				</div>
				<div class="login_btn"><a href="javascript:;" class="abtn confirmFixed VerifySubmit">注 册</a></div>
			</div>
			<div class="tab_item TabItem on LoginCheck">
				<div class="pass_box">
					<table class="form_table " id="loginRegister_loginTable">
						<tr>
							<td>
								<input class="itext" type="text" placeholder="国际码" verify-data="code" value="86"  maxlength="4" style="width:50px;" />
								<input class="itext" type="text" placeholder="登录账号"  verify-data="loginName" style="width:70%; border-left:1px solid #e5e5e5;"/>
							</td>
						</tr>
						<tr style="border:0;">
							<td class="pr">
								<input id="password" class="itext" type="password" placeholder="登录密码"  verify-data="loginPassword"  />
								<label id="togglePassword" class="see"><img src="${ctxPath!}/resource/web/styles/storewap/img/common/icon_see.png" /></label>
							</td>
						</tr>
					</table>
				</div>
				<!-- <div class="rememberPassword"><span><a href="javascript:;" class="icheck">&nbsp; <input type="checkbox" /></a>记住密码</span></div> -->
				<div class="login_btn"><a href="javascript:;" class="abtn confirmFixed VerifySubmit">登 录</a></div>
				<div class="other_way clearfix">
					<a href="${ctxPath!''}/forget">忘记密码？</a>
					<!-- <a href="javascript:;" class="mobile_way">快捷登录</a> -->
				</div>
			</div>
		</div>
	</div>
	
	<div class="dialog_frame confirm DialogFrame" id="AddressBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox">
			<div class="dialog_body DialogBody">
				<table class="form_table">
					<tr>
						<th style="width:9rem;"><strong>国际码：</strong></th>
						<td><input class="itext" type="text" id="mobileCodeTxt" value="" maxlength="4"/></td>
					</tr>
					<tr>
						<th style="width:9rem;"><strong>手机号码：</strong></th>
						<td><input class="itext" type="text" id="mobileTxt" value="" maxlength="11"/></td>
					</tr>
					<tr>
						<th style="width:9rem;"><strong>验证码：</strong></th>
						<td>
							<input class="itext" type="text" id="captchaCode" value="" maxlength="4"/>
							<img id="captcha" title="点击刷新" src="${ctxPath!''}/capatch" class="mobileCaptha_btn confirm" style="width:90px; float:right; cursor:pointer; margin-right:20px;"/>
						</td>
					</tr>
					<tr style="border:0;">
						<th>&nbsp;&nbsp;</th>
						<td id="errorMsg" style="color:Red;">&nbsp;&nbsp;</td>
					</tr>
				</table>
			</div>
			<div class="dialog_footer DialogFooter"><a href="javascript:;" class="abtn confirm AddressEditConfirm" style="margin:0;">获取手机验证码</a><a href="javascript:;" class="abtn default DialogClose"  style="margin:0;">取消</a></div>
		</div>
	</div>
	
	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>

	<script>
		$(function(){
			$("#captcha").click(function(){
				var src = $(this).attr("src");
				$(this).attr("src", chgUrl(src));
				$("#captchaCode").focus().select();
			});
			<#-- 时间戳，为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳 -->
			function chgUrl(url) {
				var timestamp = (new Date()).valueOf();
				if( url.indexOf("?") >= 0 ) {
					url = url.substring(0, url.indexOf("?"));
				}
			    if((url.indexOf("&")>=0)){
			        url += "×tamp=" + timestamp;
			    }else{
			        url += "?timestamp=" + timestamp;
			    }
			    return url;
			}
		});
	
		var _ctxPath="${ctxPath!}";
		$(".TabTrigger").click(function(){
			if($(this).index()==0){
				$(".regist_ad").show();
			}else{
				$(".regist_ad").hide();
			}
		})
		$(function(){
			$(document).on("change", "#loginRegister_registerMobile, #loginRegister_registerMobile_Code, #mobileTxt, #mobileCodeTxt", function(){
				var $id = $.trim( $(this).attr('id') );
				var code = '';
				var tel = '';
				if( 'loginRegister_registerMobile' == $id )
				{
					code = $.trim( $('#loginRegister_registerMobile_Code').val() );
					tel = $.trim( $(this).val() );
				} else if( 'mobileTxt' == $id )
				{
					code = $.trim( $('#mobileCodeTxt').val() );
					tel = $.trim( $(this).val() );
				} else if( 'loginRegister_registerMobile_Code' == $id )
				{
					code = $.trim( $(this).val() );
					tel = $.trim( $('#loginRegister_registerMobile').val() );
				} else if( 'mobileCodeTxt' == $id )
				{
					code = $.trim( $(this).val() );
					tel = $.trim( $('#mobileTxt').val() );
				}
				
				if( '' == code || '' == tel ) return ;
				
				$.ajax({
					type: "POST",
					url: '${ctxPath}/member/exist/' + code + '/' + tel,
					success : function(data){
						var res = $.parseJSON(data);
						if(res.statusCode == "200"){
							if(res.result || "true" == res.result) showTipsBox( "账户已存在！" );
						}else{
							showTipsBox( res.msg );
						}
					}
				});
			});
		
			//显示密码可见性
			$('#password').togglePassword({
		        el: '#togglePassword'
		    });
			//手机获取验证码按钮
			var $GetMobileCaptha = $('.GetMobileCaptha');
			var $AddressBox = $('#AddressBox');
			
			$GetMobileCaptha.click(function(){
				var mobileTxt = $('[verify-data=mobile]').val();
				var mobileCodeTxt = $('[verify-data=code]').val();
				if ( $.trim(mobileTxt) == '' )
				{
					showTipsBox('请先输入您的手机号码');
				} else if( $.trim(mobileCodeTxt) == '') {
					showTipsBox('请先输入国际码');
				}  else {
					if ( $(this).hasClass('disabled') ) {
						return false;
					}else {
						$('#mobileTxt').val(mobileTxt);
						$('#mobileCodeTxt').val(mobileCodeTxt);
						dialogShow($AddressBox);
					}
				}
			});
			
			// 确定发送
			var $CountDown = $('.CountDown');
			$('.AddressEditConfirm').click(function(){
				var captchaCode = $('#captchaCode').val();
				if( 0 >= captchaCode.length || 4 > captchaCode.length )
				{
					$('#errorMsg').html('验证码错误！');
					return ;
				}
				var code = $.trim($('#mobileCodeTxt').val());
				var mobile = $.trim($('#mobileTxt').val());
				
				if( '' == code || ! /^[1-9]\d{1,3}/.test(code) ) 
				{
					$('#errorMsg').html('请输入正确的国际码！');
					return ;
				}
				if( '' == mobile || ( '86' == code && ! /^((13[0-9])|(15[^4,\D])|(18[0-9])|(17[0-9])|(14[5|7]))\d{8}$/.test(mobile) ) || ( '86' != code && ! /^[1-9][0-9]{3,18}/.test(mobile) ) )
				{
					$('#errorMsg').html('请输入正确的手机号码！');
					return ;
				}
				
				$.ajax({
					type : 'POST',
					url : "${ctxPath!}/ssms/regist",
					data : { 'mobile' : mobile, 'captcha' : $('#captchaCode').val(), 'code' : code },
					success : function(data){
						var res = $.parseJSON(data);
						if(res.statusCode == "200"){
							$('#loginRegister_registerMobile').val(mobile);
							$('#loginRegister_registerMobile_Code').val( code );
							dialogClose($AddressBox);
							showTipsBox(res.msg);
							if( ! $GetMobileCaptha.hasClass('disabled') )
							{
								var second = 60;
								$GetMobileCaptha.addClass('disabled on');
								function count(){
									second --;
									$CountDown.text(second);
									if ( second == 0 ) {
										clearTimeout(t);
										$GetMobileCaptha.removeClass('disabled on');
									}
								}
								var t = setInterval(count, 1000);
							}
						}else{
							$('#errorMsg').html( res.msg );
						}
					},
					error : function(res){
						$('#errorMsg').html('发送验证码异常！');
					}
				});
			})
			
			$('.RegisterCheck').verifyData({
				dealResult : function(verifyResult){
					if ( ! verifyResult.status ) {
						showTipsBox(verifyResult.msg);
					}
				},
				success : function(){
					submitRegister(this);
					//这里返回的this为验证域的对象，也就是$('.RegisterCheck')
					//可根据这个对象找到对应的资源进行二次操作
					//console.log(this);
				}
			});
		
			$(".rememberPassword .icheck").on("click",function(){
				if($(this).hasClass("on")){
					$(this).removeClass("on");
					$(".rememberPassword .icheck input").attr("checked",false); 
				}else{
					$(this).addClass("on");
					$(".rememberPassword .icheck input").attr("checked",true); 
				}
				//console.log($(".rememberPassword .icheck input").attr("checked"));
			});
			
			
		});
		$('.LoginCheck').verifyData({
			success : function(){
				//console.log(this);
				submitLogin(this);
			}
		});
		
		function is_weixn(){
			var ua = navigator.userAgent.toLowerCase();
			if(ua.match(/MicroMessenger/i)=="micromessenger") {
				return true;
			} else {
				return false;
			}
		}
		function getCookie(c_name)
		{
		if (document.cookie.length>0)
		{ 
		c_start=document.cookie.indexOf(c_name + "=")
		if (c_start!=-1)
		{ 
		c_start=c_start + c_name.length+1 
		c_end=document.cookie.indexOf(";",c_start)
		if (c_end==-1) c_end=document.cookie.length
		return unescape(document.cookie.substring(c_start,c_end))
		} 
		}
		return ""
		}
		function setCookie(c_name,value,expiredays)
		{
		var exdate=new Date()
		exdate.setDate(exdate.getDate()+expiredays)
		document.cookie=c_name+ "=" +escape(value)+
		((expiredays==null) ? "" : "; expires="+exdate.toGMTString())
		}
		//显示密码
		(function ( $ ) {
		    $.fn.togglePassword = function( options ) {
		        var s = $.extend( $.fn.togglePassword.defaults, options ),
		        input = $( this );
		
		        $( s.el ).bind( s.ev, function() {
		            "password" == $( input ).attr( "type" ) ?
		                $( input ).attr( "type", "text" ) :
		                $( input ).attr( "type", "password" );
		        });
		    };
		
		    $.fn.togglePassword.defaults = {
		        ev: "click"
		    };
		}( jQuery ));
	</script>
</body>
</html>