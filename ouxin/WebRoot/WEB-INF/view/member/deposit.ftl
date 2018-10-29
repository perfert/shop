<!DOCTYPE html>
<html class="no-js">
<head>
	<title>提现申请</title>
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
				<#if bank??>
					<tr>
						<th><strong>提现银行：</strong></th>
						<td style="color:Red;">${bank.bankName}</td>
					</tr>
					<tr>
						<th><strong>分行：</strong></th>
						<td style="color:Red;">${bank.bankSubName}</td>
					</tr>
					<tr>
						<th><strong>银行卡号：</strong></th>
						<td style="color:Red;">${bank.bankCardCode}</td>
					</tr>
					<tr>
						<th><strong>持卡人：</strong></th>
						<td style="color:Red;">${bank.bankCardName}</td>
					</tr>
				</#if>
				<tr>
					<th style="width:9rem;"><strong>提现账户：</strong></th>
					<td><div class="addr_select checkParent province" attr-id="" id="accountOut">（主账号）<当前余额：${member.money - member.frozenMoney}></div></td>
					<input type="hidden" id="accountOutId" name="accountOutId" value="${member.id}" />
				</tr>
				<tr>
					<th><strong>提现金额：</strong></th>
					<td><input type="text" class="itext" name="cash" id="cash" value="${(cfg.minCash)!0}" /></td>
				</tr>
				<tr>
					<th><strong>手续费:${((cfg.free)!0)}%：</strong></th>
					<td><input type="text" class="itext" value="${((cfg.minCash)!0) * ((cfg.free)!0) / 100 }" id="free" readOnly /></td>
				</tr>
				<tr>
					<th><strong>支付密码：</strong></th>
					<td><input type="password"  class="itext" name="password" id="password" value="" /></td>
				</tr>
				<tr>
					<th><strong>备注：</strong></th>
					<td><input type="text" class="itext" name="remark" id="remark" value="" /></td>
				</tr>
				
				<#if bank??>
					<tr style="border:0;">
						<th>&nbsp;&nbsp;</th>
						<td id="subAccountMsg" style="color:Red;">&nbsp;&nbsp;</td>
					</tr>
				<#else>
					<tr style="border:0;">
						<th>&nbsp;&nbsp;</th>
						<td id="subAccountMsg" style="color:Red;">请先绑定提现银行卡！<a href="${ctxPath!''}/member/bank">点击绑定</a></td>
					</tr>
				</#if>
			</table>
		</div>
		<div style="padding:1.5rem;"><a href="javascript:;" class="abtn confirmFixed VerifySubmit">申请提现</a></div>
		<#--</form>-->
	</div>
	
		<div class="dialog_frame confirm DialogFrame" id="AddressBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox">
			<div class="dialog_body DialogBody">
				<table class="form_table">
					<tr>
						<th style="width:9rem;"><strong>一级子号：</strong></th>
						<td><div class="addr_select AddressDetail province" attr-id="${member.id}" >（主账户）<当前余额：${member.money - member.frozenMoney}></div></td>
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
		
			$(document).on("change", "#cash", function(){
				if("" != $(this).val() && 0 < parseFloat($(this).val()))
				{
					$('#free').val( ( parseFloat($(this).val()) * ${(cfg.free)!0} / 100 ) );
				} else {
					$('#free').val(0);
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
					addrList.html('<li class="AddressItem" attr-id="${member.id}" key="${member.id}">（主账户）<当前余额：${member.money - member.frozenMoney}></li>');
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
								addrList.append('<li class="AddressItem" attr-id="'+ obj.id +'" key="'+ obj.id +'">'+ obj.account +'&nbsp;<当前余额：' + (obj.money - obj.frozenMoney) + '></li>');
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
				var remark = $('#remark').val();
				var cash = $('#cash').val();
				var password = $('#password').val();
				var existAccountIn = $('#existAccountIn').val();
				
				if( "" == $.trim(accountOutId) ) {
					showTipsBox('<strong>请选择提现账户</strong>');
					return;
				}
				if( "" == $.trim(cash) || 0 >= parseFloat($.trim(cash)) || ${(cfg.minCash)!0} > parseFloat($.trim(cash)) ) {
					showTipsBox('<strong>提现金额不得小于 ${(cfg.minCash)!0}</strong>');
					return;
				}
				<#if 0 lt cfg.maxCash>
					if( ${(cfg.maxCash)!0} < parseFloat($.trim(cash)) ) {
						showTipsBox('<strong>提现金额不得超过 ${(cfg.maxCash)!0}</strong>');
						return;
					}
				</#if>
				if( "" == $.trim(password) ) {
					showTipsBox('<strong>支付密码不能为空</strong>');
					return;
				}
				
				$.ajax({
					type: "POST",
					url: '${ctxPath}/member/deposit',
					data : {
						'accountOutId' : accountOutId
						, 'remark' : remark
						, 'cash' : cash
						, 'password' : password
					},
					success:function(result){
						var res=$.parseJSON(result);
						if(res.statusCode == "200"){
							alert(res.msg);
							window.location = "${ctxPath}/member/deposit/list";
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