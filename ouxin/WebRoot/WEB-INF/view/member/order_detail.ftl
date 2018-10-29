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
	<style type="text/css">
		
		.data_wrap .wrap_blocko {display:block;overflow:hidden;text-align:right; white-space: nowrap; text-overflow: ellipsis; overflow: hidden;}
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
		
		.order_payment {line-height:2.6rem;border-bottom:0.1rem dotted #eee;}
		.order_payment .pay_label {display:inline-block;margin-top:1rem;}
		.order_payment .pay_items {margin-top:-1rem;}
		.order_payment .pay_items a {border:0.1rem solid #eee;float:right;margin:1rem 0 0 1rem;}
		.order_payment .pay_items a.on {border:0.1rem solid #c11;}
		.order_payment .pay_items img {display:block;height:2.6rem;}
		
		.order_receipt {margin-top:0;}
		
		.iosCheck {border:0.1rem solid #eaeaea;height:2.6rem;width:4.6rem;border-radius:2.6rem;position:relative;}
		.iosCheck i {position:absolute;top:-0.1rem;width:2.6rem;height:2.6rem;border:0.1rem solid #ccc;border-radius:2.6rem;background:#fff;}
		.iosCheck.off {background:#fcfcfc;}
		.iosCheck.off i {left:-0.1rem;box-shadow:0.1rem 0 0.5rem #bbb;}
		.iosCheck.on {background:#c11;}
		.iosCheck.on i {right:-0.1rem;box-shadow:-0.1rem 0 0.5rem #bbb;}
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
		.cart_list li.packageItem{border-top:none;padding:0 0 0.5rem 0}
		.cart_list .gcheck {float:left;height:6.4rem;}
		.cart_list .icheck {float:left;margin:2.2rem 1rem 0 0;}
		.cart_list .icheck input {display:none;}
		
		.cart_list .gimg {float:left;width:6.4rem;height:6.4rem;display:block;margin-right:1rem;}
		.cart_list .ginfo {display:block;overflow:hidden;color:#5d5b5b;font-size:1.4rem;line-height:1.6rem;height:6.4rem;}
		.cart_list .gname {max-height:3.2rem;overflow:hidden;}
		.cart_list .gsku {color:#9a9a9a;padding-right:5rem;font-size:1.2rem;}
		.cart_list .gsku em {margin-right:0.6rem;display:inline;}
		.cart_list .gcount {float:right;margin-left:1rem;text-align:right;}
		.cart_list .gprice {color:#ff0000;}
		.cart_list .goldPrice {color:#bdbdbd;}
		.cart_list .sku_select.on {float:left;border:0.1rem solid #ccc;margin-top:0.3rem;padding:0.2rem 0.5rem;line-height:1.8rem;cursor:pointer;}
		.cart_list .sku_select.on:after {content:'\f107';}
		.cart_list .sku_select.editing {border:0.1rem solid #c11;}
		
		.order_count {line-height:2.6rem;}
		.order_count em {color:#c11;}
		
		.order_footer { background:#fff;padding:1rem 1.2rem;line-height:1.6rem;font-size:1.4rem;box-shadow:0 0 0.5rem #ccc;position:fixed;left:0;right:0;bottom:0;width:100%;}
		.order_footer em {color:#c11;}
		.order_footer .abtn {float:right;height:3.2rem;line-height:3.2rem;padding:0 2rem;font-size:1.6rem;}
		.order_footer .footer_left {float:left;}
		
		.setMeal{padding-left:3rem;font-size:1.4rem;}
		.setMealTitle{line-height:2rem;padding:.5rem 0;}
	</style>
</head>

<body>
	<div class="">
		<div class="data_wrap">
			<span class="wrap_right" style="color:#888;">${bean.orderSn}</span>
			<#if -2 == bean.orderState>
				<span style="color:#c11;">已取消</span>
			<#elseif -1 == bean.orderState>
				<span style="color:#c11;">已删除</span>
			<#elseif 0 == bean.orderState>	
				<span style="color:#c11;">等待支付中</span>
			<#elseif 1 == bean.orderState>
				<span style="color:#c11;">已支付，待发货</span>
			<#elseif 2 == bean.orderState>
				<span style="color:#c11;">已发货，待收货</span>
			<#elseif 3 == bean.orderState>
				<span style="color:#c11;">已收货，待确认</span>
			<#elseif 4 == bean.orderState>
				<span style="color:#c11;">已完成
			<#else>
				<span style="color:#c11;">异常
			</#if>
		</div>
		<a href="javascript:;" attr-href="#" attr-addrId="460000172656230" class="data_wrap order_address clearfix" >
			<span class="date_more"><em><i></i></em></span>
			<img style="float:left;margin-right:10px;display:inline;height:3rem;" src="${ctxPath}/resource/web/styles/storewap/img/member/ico-order-location.png" />
			<span class="wrap_block" >
				<p><label>收件人：</label><em id="orderPerson">${bean.contactName}</em>&emsp;<em id="orderMobile">${bean.contactPhone}</em></p>
				<p id="orderAddr">${bean.address}</p>
			</span>
		</a>
		<div class="wrap">
			<div class="wap_p1">
				<p class="order_ul1 data_wrap " id="<#if 0 != bean.orderState><#else>currentPay</#if>">
					<a href="javascript:;" class=" clearfix">
						<span class="date_more"><em><i></i></em></span>
						<span class="data_label">支付方式</span>
						<#if 0 < bean.orderState>
							<span class="wrap_blocko pay_type  SelectOnItem  on" id="pay_type" attr-id="" attr-key="" style="">${(1 == bean.payType)?string('余额支付', (2 == bean.payType)?string('商币支付', (3 == bean.payType)?string('微信支付', '')))}</span>
						<#elseif bean.payType?? && 0 lt bean.payType>
							<span class="wrap_blocko pay_type  SelectOnItem  on" id="pay_type" attr-id="" attr-key="" style="">${(1 == bean.payType)?string('余额支付', (2 == bean.payType)?string('商币支付', (3 == bean.payType)?string('微信支付', '')))}</span>
							<input type="hidden" name="payType" id="pay_type_ipt" value="${bean.payType}">
						<#elseif (member.money - member.frozenMoney) gte (bean.payPrice)>
							<span class="wrap_blocko pay_type  SelectOnItem  on" id="pay_type" attr-id="" attr-key="" style="">余额支付</span>
							<input type="hidden" name="payType" id="pay_type_ipt" value="1">
						<#elseif (member.subMoney - member.frozenSubMoney) gte (bean.payPrice)>
							<span class="wrap_blocko pay_type  SelectOnItem  on" id="pay_type" attr-id="" attr-key="" style="">商币支付</span>
							<input type="hidden" name="payType" id="pay_type_ipt" value="2">
						<#else>
							<span class="wrap_blocko pay_type  SelectOnItem  on" id="pay_type" attr-id="" attr-key="" style="">请选择</span>
							<input type="hidden" name="payType" id="pay_type_ipt" value="">
						</#if>
					</a>
				</p>
			</div>
		</div>
		<div class="order_cart">
			<h2 class="cart_title"><img class="ico" src="${ctxPath}/resource/web/styles/storewap/img/member/ico-order-good.png" />订单商品</h2>
			<div class="cart_con">
				<ul class="cart_list CartList">
					<#list bean.items as item>
						<li class="CartItem">
							<div class="gcheck ItemCheck on disabled" attr-salePrice="${item.price}" attr-name="${item.name}" attr-flag=""><a href="javascript:;" class="icheck on"></a></div>
							<a href="${ctxPath}/vip/product/${item.productId}"><img class="gimg ItemImg" src="${ctxPath}/${item.image}"  /></a>
							<div class="ginfo">
								<div class="gcount">
									<p class="gprice">&yen; <var class="ItemPrice">${item.price?string(",##0.00")}</var></p>
									<p class="goldPrice"><s>&yen; <var class="ItemOldPrice">${item.marketPrice?string(",##0.00")}</var></s></p>
									<div class="y_blank16"></div>
									<p class="gnum">&times; <var class="ItemNum" id="buy_num_470000013035768" >${item.num}</var></p>
								</div>
									<p class="gname"><a href="${ctxPath}/vip/product/${item.productId}">${item.name}</a></p>
								
								<p class="gsku clearfix"><span class="sku_select"><em></em></span></p>
							</div>
							<div class="clearperfect"></div>
						</li>
					</#list>
				</ul>
			</div>
		</div>
		<div class="data_wrap order_count clearfix">
			<p><em class="wrap_right">&yen; <var class="CartSum">${bean.totalPrice?string(",##0.00")}</em><span>商品结算金额</span></p>
			<p><em class="wrap_right">&yen; <var class="CartFare">0.00</em><span>运费</span></p>
			<#if 0 lt bean.totalDiscount><p><em class="wrap_right">- &yen; <var class="DiscountAmount">${bean.totalDiscount?string(",##0.00")}</em><span>折扣价格</span></p></#if>
			<#if bean.isUseGold && 0 lt bean.hasUseGold><p><em class="wrap_right">- <var class="DiscountAmount">${bean.hasUseGold?string(",##0.00")}</em><span>使用LC</span></p></#if>
			<div class="xDottedLine" style="margin:0.5rem 0;"></div>
			<p><em class="wrap_right">&yen; <var class="CartTotal">${bean.payPrice?string(",##0.00")}</em><span>订单金额</span></p>
		</div>
		<div style="height:4.2rem;font-size:0;overflow:hidden;"></div>
	</div>
	<#if 0 == bean.orderState>
		<div class="order_footer clearfix">
			<a href="javascript:;" class="abtn confirm submitEditOrder">去支付</a>
			<div class="footer_left">
				<p>本单您只需支付</p>
				<p><em>&yen; <var class="CartTotal">${bean.payPrice?string(",##0.00")}</em></p>
			</div>
		</div>
	<#else>
		<footer class="wap_footer">
			<ul class="am-avg-sm-4 footer_nav">
				<li><a href="${ctxPath}/index"><img class="idefault" src="${ctxPath}/resource/web/image/icon/home.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/home-ck.png" /><em>首页</em></a></li>
				<li><a href="${ctxPath}/member/cart" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/cart.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/cart-ck.png" /><em>购物车</em></a></li>
				<li><a href="${ctxPath!}/member/index" class="on" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/person.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/person-ck.png" /><em>用户中心</em></a></li>
				<li><a href="${ctxPath!}/member/distribute" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/shopping.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/shopping-ck.png" /><em>我的众销</em></a></li>
			</ul>
		</footer>
	</#if>
	
	<div class="dialog_frame" id="payDetailBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box" style="position:relative; top:30%;">
			<div class="dialog_body">
				<ul class="addr_list payList" id="payList">
					<#if (member.money - member.frozenMoney) gte bean.payPrice>
						<li class="payItem" attr-id="1" key="1">余额支付</li>
					<#elseif (member.money - member.frozenMoney) gte (bean.payPrice)>
						<li class="payItem" attr-id="1" key="1" id="virPayType" style="display:none;">余额支付</li>
					</#if>
					<#if (member.subMoney - member.frozenSubMoney) gte bean.payPrice>
						<li class="payItem" attr-id="2" key="2">Ts支付</li>
					<#elseif (member.subMoney - member.frozenSubMoney) gte bean.payPrice>
						<li class="payItem" attr-id="2" key="2" id="subPayType" style="display:none;">Ts支付</li>
					</#if>
					<li class="payItem" attr-id="3" key="3">微信支付</li>
				</ul>
			</div>
		</div>
	</div>
	
	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>

	<script type="text/javascript">
		<#if error?? && "error" == error.status>
			showTipsBox("${error.message}");
		</#if>
		$(function(){
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
		
		function is_weixn(){
			var ua = navigator.userAgent.toLowerCase();
			if(ua.match(/MicroMessenger/i)=="micromessenger") {
				return true;
			} else {
				return false;
			}
		}
	</script>
</body>
</html>