<!DOCTYPE html>
<html class="no-js">
<head>
	<title>Ts转账</title>
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
		.address_edit {border-top:0.1rem solid #eee;border-bottom:0.1rem solid #eee;padding-top:0;padding-bottom:0;}
		.addr_select {display:block;height:2.4rem;line-height:2.4rem;}
		.addr_list {overflow:hidden;}
		.addr_list li {border-top:0.1rem solid #eee;line-height:3.6rem;height:3.6rem;padding-left:1.5rem;font-size:1.4rem;}
	</style>
</head>

<body>
	<div class="AddressCheck">
		<#--<form action="${ctxPath}/member/transfer" method="post">-->
		<div class="data_wrap address_edit">
			<input type="hidden" class="itext" id="existAccountIn" value="1" />
			<table class="form_table">
				<tr>
					<th style="width:9rem;"><strong>转出账户：</strong></th>
					<td><div class="addr_select checkParent province" attr-id="" id="accountOut">主账号<当前Ts：${bean.subMoney - bean.frozenSubMoney}></div></td>
					<input type="hidden" id="accountOutId" name="accountOutId" value="${bean.id}" />
				</tr>
				<tr>
					<th><strong>转入账户：</strong></th>
					<td><input type="text" class="itext" name="code" id="code" value="86" style="width:50px;" maxlength="4" /><input type="text" class="itext" name="accountIn" id="accountIn" value=""  style="width:70%; border-left:1px solid #e5e5e5;" /></td>
				</tr>
				<tr>
					<th><strong>转账金额：</strong></th>
					<td><input type="text" class="itext" name="cash" id="cash" value="" /></td>
				</tr>
				<tr>
					<th><strong>支付密码：</strong></th>
					<td><input type="password"  class="itext" name="password" id="password" value="" /></td>
				</tr>
				<tr style="border:0;">
					<th>&nbsp;&nbsp;</th>
					<td id="subAccountMsg" style="color:Red;">&nbsp;&nbsp;</td>
				</tr>
			</table>
		</div>
		<div style="padding:1.5rem;"><a href="javascript:;" class="abtn confirmFixed VerifySubmit">确认转账</a></div>
		<#--</form>-->
	</div>
	
		<div class="dialog_frame confirm DialogFrame" id="AddressBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox">
			<div class="dialog_body DialogBody">
				<table class="form_table">
					<tr>
						<th style="width:9rem;"><strong>一级子号：</strong></th>
						<td><div class="addr_select AddressDetail province" attr-id="${bean.id}" >（主账户）<当前Ts：${bean.subMoney - bean.frozenSubMoney}></div></td>
					</tr>
					<tr>
						<th><strong>二级子号：</strong></th>
						<td><div class="addr_select AddressDetail city" attr-id="" attr-parentId=""></div></td>
					</tr>
				</table>
			</div>
			<div class="dialog_footer DialogFooter"><a href="javascript:;" class="abtn confirm AddressEditConfirm">确定</a><a href="javascript:;" class="abtn default DialogClose">取消</a></div>
		</div>
	</div>
	
	<div class="dialog_frame DialogFrame" id="AddressDetailBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox" >
			<div class="dialog_body DialogBody">
				<ul class="addr_list AddressList" id="addrList">
				</ul>
			</div>
		</div>
	</div>

	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>
	
	<script type="text/javascript">
		$(function(){
			$(document).on("change", "#accountIn", function(){
				var code = $.trim( $('#code').val() );
				var accountIn = $.trim( $('#accountIn').val() );
				
				if( '' == code ) 
				{
					showTipsBox( "请输入转入账户国际码！" );
					return ;
				}
				if( '' == accountIn ) 
				{
					showTipsBox( "请输入转入账户账号！" );
					return ;
				}
				
				if("" != $(this).val())
				{
					$.ajax({
						type: "POST",
						url: '${ctxPath}/member/exist/' + code + '/' + accountIn,
						success : function(data){
							var res = $.parseJSON(data);
							if(res.statusCode == "200"){
								if(! res.result || "false" == res.result) {
									$('#existAccountIn').val(0);
									showTipsBox( "转入账户不存在！" );
								} else {
									$('#existAccountIn').val(1);
								}
							}else{
								showTipsBox( res.msg );
							}
						}
					});
				}
			});
			
			//选择处理
			var $AddressSelect = $('#accountOut');
			var $AddressEditConfirm = $('.AddressEditConfirm');
			var $AddressDetail = $('.AddressDetail');
			var $AddressBox = $('#AddressBox');
			var $AddressDetailBox = $('#AddressDetailBox');
			
			//编辑层触发
			$AddressSelect.click(function(){
				dialogShow($AddressBox);
			});
			//一二级
			$AddressDetail.click(function(){
				var $this = $(this);
				var addrList = $('#addrList');
				var parentId = $(this).attr("attr-parentId")?$(this).attr("attr-parentId"):"";
				var path;
				
				if($this.hasClass("province")){
					path = "${ctxPath!}/member/one/sub";
					addrList.html('<li class="AddressItem" attr-id="${bean.id}" key="${bean.id}">主账户<当前Ts：${bean.subMoney - bean.frozenSubMoney}></li>');
				}
				
				if($this.hasClass("city")){
					if(parentId == ""){
						showTipsBox('<strong>请先选择一级子号</strong>');
						return;
					}
					path = "${ctxPath!}/member/" + parentId + "/one/sub";
					addrList.html('');
				}
				$.ajax({
					type: "POST",
					url:path,
					success:function(result){
						if( 0 < result.length)
						{
							var data=$.parseJSON(result);
							var obj;
							for(var idx in data)
							{
								obj = data[idx];
								addrList.append('<li class="AddressItem" attr-id="'+ obj.id +'" key="'+ obj.id +'">'+ obj.account +'&nbsp;<当前Ts：' + (obj.subMoney - obj.frozenSubMoney) + '></li>');
							}
							if( 0 < data.length ) 
							{
								$this.addClass('on');
								dialogShow($AddressDetailBox);
							} else {
								showTipsBox('<strong>无二级子号</strong>');
							}
						}
					}
				})
			});
			//一二级确定
			$AddressEditConfirm.click(function(){
				dialogClose($AddressBox);
			})
			//一二级列表
			var $AddressList = $('.AddressList');
			$AddressList.on('click', '.AddressItem', function(){
				var $this = $(this);
				var $AddressDetailOn = $('.AddressDetail.on');
				
				var id = $this.attr("attr-id");
				$('#accountOutId').val(id);
				$('#accountOut').text($this.text());
				$AddressDetailOn.closest("tr").next().find(".AddressDetail").attr("attr-parentId", id);
				$AddressDetailOn.closest("tr").nextAll().find(".AddressDetail").text("");
				$AddressDetailOn.text($this.text());
				$AddressDetailOn.attr("attr-id", id);
				dialogClose($AddressDetailBox);
				$AddressDetail.removeClass('on');
			});
			
			$(".VerifySubmit").on("click", function()
			{
				var accountOutId = $('#accountOutId').val();
				var code = $('#code').val();
				var accountIn = $('#accountIn').val();
				var cash = $('#cash').val();
				var password = $('#password').val();
				var existAccountIn = $('#existAccountIn').val();
				
				if( "" == $.trim(accountOutId) ) {
					showTipsBox('<strong>请选择转出账户</strong>');
					return;
				}
				if( "" == $.trim(code) ) {
					showTipsBox('<strong>请输入转入账户国际码</strong>');
					return;
				}
				if( "" == $.trim(accountIn) ) {
					showTipsBox('<strong>请输入要转入的账户</strong>');
					return;
				}
				if( 0 == $.trim(existAccountIn) || "0" == $.trim(existAccountIn) )
				{
					showTipsBox('<strong>转入账户不存在</strong>');
					return ;
				}
				if( "" == $.trim(cash) ) {
					showTipsBox('<strong>请输入转账金额</strong>');
					return;
				}
				if( "" == $.trim(password) ) {
					showTipsBox('<strong>支付密码不能为空</strong>');
					return;
				}
				
				$.ajax({
					type: "POST",
					url: '${ctxPath}/member/transfer',
					data : {
						'accountOutId' : accountOutId
						, 'accountIn' : accountIn
						, 'cash' : cash
						, 'password' : password
						, 'code' : code
					},
					success:function(result){
						var res=$.parseJSON(result);
						if(res.statusCode == "200"){
							alert(res.msg);
							window.location.reload();
						}else{
							console.log(res.msg);
							showTipsBox( res.msg );
						}
					}
				})
			});
			
		});
	</script>
</body>
</html>