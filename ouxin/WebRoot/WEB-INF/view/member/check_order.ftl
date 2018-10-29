<!DOCTYPE html>
<html class="no-js">
<head>
	<title>订单确认</title>
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
	<script src="${ctxPath}/resource/web/scripts/storewap/verifyData.js"></script>
		<style type="text/css">
		.order_adress{padding-left:3rem;}
		.wrap{width:100%;padding-top:2rem;background-color: #f5f8fa;}
		.wap_p1{padding-left: 5%;border-top:1px solid #e6e6e6;border-bottom:1px solid #e6e6e6;margin-bottom:2rem; background-color:#fff;}
		.products{padding:0.8rem 0;}
		.products .pro{display:inline-block;width: 4.9rem;height: 4.9rem;border:1px solid #e6e6e6;background-color: #dd4;margin-right:1rem;}
		.pro_count{height:4.9rem;line-height:1.9rem;}
		.pro_count em{padding-right:2rem;}
		.songhuo{border-top:1px solid #e6e6e6;font-size: 1.4rem;height:4rem;}
		.order_ul1{height:4rem;}
		
		
		.pay_type img{width: 9rem;height: 2.5rem;position: absolute;top: 30%;left: 38%;}
		.usable_integral{color:#c11;}
		.data_wrap{padding-left:0;}
		.data_wrap .wrap_blocko {display:block;overflow:hidden;text-align:right; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;}
		
		.order_payment {line-height:2.6rem;border-bottom:0.1rem dotted #eee;}
		.order_payment .pay_label {display:inline-block;margin-top:1rem;}
		.order_payment .pay_items {margin-top:-1rem;}
		.order_payment .pay_items a {border:0.1rem solid #eee;float:right;margin:1rem 0 0 1rem;}
		.order_payment .pay_items a.on {border:0.1rem solid #c11;}
		.order_payment .pay_items img {display:block;height:2.6rem;}
		
		.order_receipt {margin-top:0;}
		
		.iosCheck {border:0.1rem solid #eaeaea;height:2.6rem;width:4.6rem;border-radius:2.6rem;position:relative;}
		.iosCheck i {position:absolute;top:0rem;width:2.4rem;height:2.4rem;border:0.1rem solid #ccc;border-radius:2.6rem;background:#fff;}
		.iosCheck.off {background:#fcfcfc;}
		.iosCheck.off i {left:-0.1rem;box-shadow:0.1rem 0 0.5rem #bbb;}
		.iosCheck.on {background:#00e367;}
		.iosCheck.on i {right:-0.1rem;box-shadow:-0.1rem 0 0.5rem #00e367;border:1px solid #00e367;}
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
		.cart_list .gcheck {float:left;height:6.4rem;}
		.cart_list .icheck {float:left;margin:2.2rem 1rem 0 0;}
		.cart_list .icheck input {display:none;}
		
		.cart_list .gimg_wraper{position: relative;}
		.cart_list .glabel {position:absolute;display: inline-block;width:100%;height:2rem;line-height:2rem;color:#fff;bottom:0;left:0;background-color: #f22;text-align: center;}
		.cart_list .gimg {float:left;width:6.4rem;height:6.4rem;display:block;margin-right:1rem;}
		img.gimg{border:1px solid #ccc;width:70px;height:70px;}
		.cart_list .ginfo {display:block;overflow:hidden;color:#5d5b5b;font-size:1.4rem;line-height:1.6rem;height:6.4rem;}
		.cart_list .gname {max-height:3.2rem;overflow:hidden;}
		.cart_list .gsku {color:#9a9a9a;padding-right:5rem;font-size:1.2rem;}
		.cart_list .gsku em {margin-right:0.6rem;display:inline;}
		.cart_list .gcount {float:right;margin-left:1rem;text-align:right;}
		.cart_list .goldPrice {color:#bdbdbd;}
		.cart_list .sku_select.on {float:left;border:0.1rem solid #ccc;margin-top:0.3rem;padding:0.2rem 0.5rem;line-height:1.8rem;cursor:pointer;}
		.cart_list .sku_select.on:after {content:'\f107';}
		.cart_list .sku_select.editing {border:0.1rem solid #c11;}
		
		.order_count {line-height:2.6rem;padding:1rem 5%;}
		.order_count em {color:#c11;}
		
		.order_footer {background:#333;line-height:1.6rem;font-size:1.4rem;box-shadow:0 0 0.5rem #ccc;position:fixed;left:0;right:0;bottom:0;width:100%;}
		.order_footer .abtn {float:right;height:4rem;line-height:4rem;padding:0 2rem;font-size:1.8rem;  border-radius: 0;}
		.order_footer .footer_left {float:left;color:#fff;padding:1rem;font-size:1.5rem;}
		.order_footer .footer_left  em{font-size:1.8rem;}
		
		

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
	<form action="${ctxPath!}/member/buy" method="post" id="orderForm">
	<div class="pb20">
		<#if address??>
			<a href="${ctxPath!}/member/address<#if productIds?? && 0 lt productIds?size>?productIds=<#list productIds as item><#if 0 lt item_index>,</#if>${item}</#list></#if>" attr-href="resource/web/so/findAddrs.htm?addrId=460000172656230" attr-addrid="460000172656230" class="data_wrap order_address clearfix" style="display: block;">
				<span class="date_more"><em><i></i></em></span>
				<img style="position:absolute;left:1rem;top:50%;margin-top:-1.5rem;height:2rem;" src="${ctxPath}/resource/web/styles/storewap/img/member/ico_distance.jpg">
				<span class="wrap_block order_adress">
					<p><span>收件人：</span><em id="orderPerson">${address.contactName}</em> <em>${address.contactPhone}</em></p>
					<p>收货地址：${address.address}</p>
					<input type="hidden" name="addressId" id="addressId" value="${address.id}">
				</span>
			</a>
		<#else>
			<input type="hidden" name="addressId" id="addressId">
			<a href="${ctxPath!}/member/address<#if productIds?? && 0 lt productIds?size>?productIds=<#list productIds as item><#if 0 lt item_index>,</#if>${item}</#list></#if>" class="data_wrap addr_add"><i></i>添加新地址</a>
		</#if>
		<div class="wrap">
			<div class="wap_p1">
				<p class="products data_wrap  clearfix" id="prodList" attr-href="${ctxPath}/resource/web/ec/so/getProList.htm">
					<span class="date_more"><em><i></i></em></span>
					<#assign totalPrice=0/>
					<#assign totalGold=0/>
					<#assign totalDiscount=0/>
					<#assign level=cache_member_level_map[(member.level + '')]/>
					<#if carts?? && 0 lt carts?size>
						<span class="date_more pro_count CartList"><em>${carts?size}件</em></span>
						<#list carts as item>
							<#assign totalPrice=( totalPrice + (item.price * (item.num)!1) )/>
							<#assign totalGold=( totalGold + (item.virPrice * (item.num)!1) )/>
							<#if level?? && 0 lt level.repeatDiscount>
								<#assign totalDiscount=( totalDiscount + (item.price * (item.num)!1) * ( 1 - 0.01 * level.repeatDiscount ) )/>
							</#if>
							<img class="gimg ItemImg" src="${ctxPath!}/${item.image}">
							<input type="hidden" name="productIds" value="${item.id}">
						</#list>
					<#else>
						<input type="hidden" name="productIds" id="productIds">
					</#if>
					
					<#assign canUseVirPrice=0/>
					<#if member?? && (member.gold - member.frozenGold) gt 0 && totalGold gt 0>
						<#if (member.gold - member.frozenGold) lt totalGold>
							<#assign canUseVirPrice=(member.gold - member.frozenGold)/>
						<#else>
							<#assign canUseVirPrice=totalGold/>
						</#if>
					</#if>
				</p>
			</div>
			<div class="wap_p1">
				<p class="order_ul1 data_wrap " id="currentPay">
					<a href="javascript:;" class=" clearfix">
						<span class="date_more"><em><i></i></em></span>
						<span class="data_label">支付方式</span>
						<#if payType?? && 0 lt payType>
							<span class="wrap_blocko pay_type  SelectOnItem  on" id="pay_type" attr-id="" attr-key="" style="">${(1 == payType)?string('余额支付', (2 == payType)?string('Ts支付', (3 == payType)?string('微信支付', '')))}</span>
							<input type="hidden" name="payType" id="pay_type_ipt" value="${payType}">
						<#elseif (member.money - member.frozenMoney) gte (totalPrice - canUseVirPrice - totalDiscount)>
							<span class="wrap_blocko pay_type  SelectOnItem  on" id="pay_type" attr-id="" attr-key="" style="">余额支付</span>
							<input type="hidden" name="payType" id="pay_type_ipt" value="1">
						<#elseif (member.subMoney - member.frozenSubMoney) gte (totalPrice - canUseVirPrice - totalDiscount)>
							<span class="wrap_blocko pay_type  SelectOnItem  on" id="pay_type" attr-id="" attr-key="" style="">Ts支付</span>
							<input type="hidden" name="payType" id="pay_type_ipt" value="2">
						<#else>
							<span class="wrap_blocko pay_type  SelectOnItem  on" id="pay_type" attr-id="" attr-key="" style="">请选择</span>
							<input type="hidden" name="payType" id="pay_type_ipt" value="">
						</#if>
					</a>
				</p>
			</div>
			
			<div class="wap_p1">
				<p class="order_ul1 data_wrap " id="currentPayMember">
					<a href="javascript:;" class=" clearfix">
						<span class="date_more"><em><i></i></em></span>
						<span class="data_label">支付账户</span>
						<span class="wrap_blocko pay_type  checkParent  on" id="payMember" attr-id="" attr-key="" style="">${(payMember??)?string((payMember.account)!'', '（主账户）')}</span>
						<input type="hidden" name="payMember.id" id="payMemberId" value="${(payMember.id)!''}">
					</a>
				</p>
			</div>
			
			<#if canUseVirPrice gt 0>
				<div class="wap_p1">
					<p class="order_ul1 data_wrap" id="usePointBox">
						<a href="javascript:;" class="wrap_right iosCheck IOSCheck off"><i class="CheckButton"></i></a>
						<span class="data_label">可用<var id="orderPay_usePoint">${canUseVirPrice}</var>LC抵扣<em class="usable_integral">￥<var class="UsableIntegral" id="orderPay_usePointTotal">${canUseVirPrice}</var></em></span>
						<input type="hidden" name="useGold" id="useGold" value="0">
					</p>
				</div>
			<#else>
				<input type="hidden" name="useGold" id="useGold" value="0">
			</#if>
		</div>
		
		<div class="data_wrap order_count clearfix">
			<p><em class="wrap_right">¥ <var class="CartSum">${totalPrice!0}</var></em><span>商品结算金额</span></p>
			<#if 0 < totalDiscount><p><em class="wrap_right">¥ <var class="CartFare">${totalDiscount}</var></em><span>折扣</span></p></#if>
			<p><em class="wrap_right">¥ <var class="CartFare">0.00</var></em><span>运费</span></p>
			<p><em class="wrap_right">¥ <var class="CartTotal">${(totalPrice!0) - totalDiscount}</var></em><span>订单金额</span></p>
		</div>
		<div style="height:4.2rem;font-size:0;overflow:hidden;"></div>
	</div>
	
	<div class="order_footer clearfix">
		<a href="javascript:submitOrder();" class="abtn confirm">确认支付</a>
		<div class="footer_left">
			<span>实付：</span>
			<span><em>¥ <var class="CartTotal" id="CartTotal">${(totalPrice!0) - totalDiscount}</var></em></span>
		</div>
	</div>
	<input type="hidden" id="submitOrderFlag" value="false">
	</form>
	
	<div class="dialog_frame" id="payDetailBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box" style="position:relative; top:30%;">
			<div class="dialog_body">
				<ul class="addr_list payList" id="payList">
					<#if (member.money - member.frozenMoney) gte (totalPrice)>
						<li class="payItem" attr-id="1" key="1">余额支付</li>
					<#elseif (member.money - member.frozenMoney) gte (totalPrice - canUseVirPrice)>
						<li class="payItem" attr-id="1" key="1" id="virPayType" style="display:none;">余额支付</li>
					</#if>
					<#if (member.subMoney - member.frozenSubMoney) gte (totalPrice)>
						<li class="payItem" attr-id="2" key="2">Ts支付</li>
					<#elseif (member.subMoney - member.frozenSubMoney) gte (totalPrice - canUseVirPrice)>
						<li class="payItem" attr-id="2" key="2" id="subPayType" style="display:none;">Ts支付</li>
					</#if>
					<li class="payItem" attr-id="3" key="3">微信支付</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="dialog_frame confirm DialogFrame" id="AddressBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox">
			<div class="dialog_body DialogBody">
				<table class="form_table">
					<tr>
						<th style="width:9rem;"><strong>一级子号：</strong></th>
						<td><div class="addr_select AddressDetail province" attr-id="" >（主账户）</div></td>
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
					<#--<#if provinceList?? && 0 lt provinceList?size>
						<#list provinceList as item>
							<li class="AddressItem" attr-id="${item.code}" key="${item.code}">${item.name}</li>
						</#list>
					</#if>-->
				</ul>
			</div>
		</div>
	</div>
	
	<#--
	<div class="dialog_frame DialogFrame" id="subAccountListBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox" >
			<div class="dialog_body DialogBody">
				<ul class="addr_list AddressList" id="subAccountList">
				</ul>
			</div>
		</div>
	</div>
	
	<div class="dialog_frame confirm DialogFrame" id="subAccounttBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox">
			<div class="dialog_body DialogBody">
				<table class="form_table">
					<tr>
						<th style="width:9rem;"><strong>一级子号：</strong></th>
						<td><div class="addr_select querySubAccount oneSubAccount" attr-id="" >（主账号）</div></td>
					</tr>
					<tr>
						<th><strong>二级子号：</strong></th>
						<td><div class="addr_select querySubAccount twoSubAccount" attr-id="" attr-parentId=""></div></td>
					</tr>
				</table>
			</div>
			<div class="dialog_footer DialogFooter"><a href="javascript:;" class="abtn confirm AddressEditConfirm">确定</a><a href="javascript:;" class="abtn default DialogClose">取消</a></div>
		</div>
	</div>-->

	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>
	<script src="${ctxPath}/resource/web/scripts/storewap/common.js"></script>
	<script type="text/javascript">
		<#if error?? && "error" == error.status>
			showTipsBox("${error.message}");
		</#if>
		$(function(){
		
			//总价 对象
			var $CartTotal = $('.CartTotal');
		
			//积分抵扣事件
			$('.IOSCheck').click(function(){
				var $this = $(this);
				var gold = parseFloat($('#orderPay_usePointTotal').text());
				if ( $this.hasClass('on') ) {
					pointUnCheck(gold);
				}else {
					pointCheck(gold);
				}
			});
			
			//使用积分选中
			function pointCheck(gold){
				$(".IOSCheck").addClass('on').removeClass('off');
				$CartTotal.text((parseFloat($('#CartTotal').text()) - gold));
				$('#useGold').attr('value', 1);
				$("#point_p").show();
				var virPayType = $('#virPayType');
				if( virPayType ) { virPayType.show(); }
				var subPayType = $('#subPayType');
				if( subPayType ) { subPayType.show(); }
			}
			//积分不选中
			function pointUnCheck(gold){
				$(".IOSCheck").removeClass('on').addClass('off');
				$CartTotal.text((parseFloat($('#CartTotal').text()) + gold));
				$('#useGold').attr('value', 0);
				$("#point_p").hide();
				var virPayType = $('#virPayType');
				if( virPayType ) { virPayType.hide(); }
				var subPayType = $('#subPayType');
				if( subPayType ) { subPayType.hide(); }
			}
			
			var $currentPay = $('#currentPay');
			var $payDetailBox = $('#payDetailBox');
			var $payList = $('.payList');
			//支付方式
			$currentPay.click(function(){
				dialogShow($payDetailBox);
			});
			$payList.on('click', '.payItem', function(){
				var $this = $(this);
				var id = $this.attr("attr-id");
				var pay_type = $('#pay_type');
				pay_type.text($this.text());
				pay_type.attr("attr-id", id);
				$('#pay_type_ipt').attr('value', id);
				dialogClose($payDetailBox);
			});
		});
		
		function submitOrder()
		{
			if("" == $('#addressId').val()) {
				showTipsBox("请选择收货地址！");
				return false;
			}
			if("" == $('#pay_type_ipt').val()) {
				showTipsBox("请选择支付方式！");
				return false;
			}
			
			//防止重复提交
			if($("#submitOrderFlag").val() == "true"){
				return false;
			}
			$("#submitOrderFlag").val("true");
			$('#orderForm').submit();
		}
	</script>
	
	<script type="text/javascript">
		$(function(){
			
		//地址修改验证
			$('.AddressCheck').verifyData({
				dealResult : function(verifyResult){
					if ( ! verifyResult.status ) {
						showTipsBox(verifyResult.msg);
					}
				},
				success : function(){
					//console.log(this);
				}
			});
		
			//选择处理
			var $AddressSelect = $('#currentPayMember');
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
					addrList.html('<li class="AddressItem" attr-id="" key="">（主账户）</li>');
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
								addrList.append('<li class="AddressItem" attr-id="'+ obj.id +'" key="'+ obj.id +'">'+ obj.account +'</li>');
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
				$('#payMemberId').val(id);
				$('#payMember').text($this.text());
				$AddressDetailOn.closest("tr").next().find(".AddressDetail").attr("attr-parentId", id);
				$AddressDetailOn.closest("tr").nextAll().find(".AddressDetail").text("");
				$AddressDetailOn.text($this.text());
				$AddressDetailOn.attr("attr-id", id);
				dialogClose($AddressDetailBox);
				$AddressDetail.removeClass('on');
			});
			
			$(".VerifySubmit").on("click", function(){
				editAddr();
			});
			
			initAddrEdit();
			
			function initAddrEdit(){
				if($("#addrId").val() != ""){
					$('#payMember').text('（主账户）');
					$('#payMemberId').val('');
				}
			}
		});
	</script>

</body></html>