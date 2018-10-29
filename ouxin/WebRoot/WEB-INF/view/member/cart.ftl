<#include "member/toLogin.ftl" />

<!DOCTYPE html>
<html class="no-js">
<head>
	<title>购物车</title>
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
	<link rel="stylesheet" href="${ctxPath}/resource/web/styles/storewap/css/cart.css">
	<script type="text/javascript" src="${ctxPath}/resource/web/scripts/jquery/jquery-tmpl/jquery.tmpl.min.js" ></script>
	<script type="text/x-jquery-tmpl" id="cart_template"></script>
	<script type="text/javascript"  src="${ctxPath}/resource/web/scripts/storewap/product/product.js"></script>
	<script src="${ctxPath}/resource/web/scripts/storewap/common.js"></script>
	
	<link href="${ctxPath}/resource/miao/styles/Mobile/Common.css" rel="stylesheet" />
	<link href="${ctxPath}/resource/miao/styles/Mobile/Cart.css" rel="stylesheet" />
</head>

<body>
	<form id="buyForm" action="${ctxPath!}/member/check_order" method="post">
	<div class="cart_body">
		<ul class="cart_list CartList">
			<#assign totalPrice=0/>
			<#assign totalProduct=0/>
			<#if carts?? && 0 lt carts?size>
				<#list carts as item>
					<#assign totalPrice=( totalPrice + (item.price * (item.num)!1) )/>
					<#assign totalProduct=( totalProduct + (item.num)!1 )/>
					<li class="CartItem" id="${item.productId}" attr_prodSkuId="470000109012290" attr_saveType="buy_now" attr_isMain="Y" attr_mainId="">
						<div  class="gcheck ItemCheck on buyNowItem "   ><a href="javascript:;" class="icheck on"><input class="productIds" type="checkbox" name="productIds" checked="checked" value="${item.id}" /></a></div>
						<div class="gimg_wraper gimg clearfix">
							<img class="gimg ItemImg"  src="${ctxPath}/${item.image}"  />
						</div>
						<div class="ginfo" style="height:10.6rem;">
							<p class="gname">${item.name}</p>
							<!-- <p class="gsku clearfix"> <span class="sku_select SkuSelect" id="510000087000453"><em >更改属性及数量</em></span></p> -->		
							<div class="gnum clearfix">
								<strong>数量：</strong>
								<span>
									<a href="javascript:;" class="NumDecrease">-</a>
									<input type="hidden" value="${item.price}" class="item_price" />
									<input type="hidden" value="${item.marketPrice}" class="item_marketPrice" />
									<input class="cartEdit NumInput" type="text" onafterpaste="this.value=this.value.replace(/\D/g,'')" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${(item.num)!1}" minvalue="1" maxvalue="99999" required>
									<a href="javascript:;" class="NumIncrease">+</a>
								</span>
							</div>
							<div class="gcount">
								<span class="gprice">&yen; <var class="ItemPrice">${(item.price * (item.num)!1)}</var></span>
								<span class="goldPrice"><s>&yen; <var class="ItemOldPrice">${(item.marketPrice * (item.num)!1)}</var></s></span>
								<span class="gnum f-Cart-Other cartDel" val="${item.id}" style="width:auto;"><a href="javascript:void(0);" class="fr z-del" name="delLink" data-termid="377"></a></span>
							</div>
						</div>
						<div class="clearperfect"></div>
					</li>
				</#list>
			</#if>
			<input type="hidden" id="isFreeShip" value="true" />
		</ul>
		<!-- <div class="gsum">商品结算金额 <em>￥<var class="CartSum">288</em> + 运费 <em>￥<var class="CartFare">0.0</var></em><em></em></div> -->
	</div>
	
	<div class="pop_frame DialogFrame" id="CartEditFrame" style="z-index:999;">
		<b class="pop_mask DialogMask DialogClose"></b>
		<div class="pop_box DialogBox">
			<div class="pop_body DialogBody">
				<a href="javascript:;" class="pop_close DialogClose">×</a>
				<div class="cartEdit_box CartEditBox" style="height:30rem;">
				</div>
			</div>
			<div class="pop_footer DialogFooter"><a href="javascript:;" class="abtn confirm SKUConfirm">确定</a></div>
		</div>
	</div>

	<div class="cart_footer">
		<span class="check_all CheckAll on"><a href="javascript:;" class="icheck on"></a>全选</span>
		<a href="javascript:void(0);" class="abtn confirm pay_btn CartPay" id="toCheckOut">去结算 (<var class="PayNum">${totalProduct!0}</var>)</a>
		<a href="#" class="abtn confirm pay_btn CartDelete" style="display:none;">删除选中</a>
		<div class="cart_total">
			<p class="need_pay">本单(含运费<em>&yen;<var class="CartFare">0.0</var></em>)共</p>
			<p class="money">&yen; <var class="CartTotal">${totalPrice!0}</var></p>
		</div>
	</div>
	</form>
	
	<div class="dialog_frame none" id="preCheckOutDialog" style="display:none;z-index:1001;">
	    <b class="dialog_mask"></b>
	    <div class="dialog_box">
	        <div class="dialog_body">
	            <div class="dialog_header">
	                <div class="dialog_title">部分商品库存不足，是否需要移除?</div>
	            </div> 
	        </div>
	        <div class="dialog_footer">
	            <a href="javascript:;" class="abtn confirm" id="cancle_bt">取消</a>&nbsp&nbsp&nbsp&nbsp&nbsp
	  			<a href="javascript:;" class="abtn confirm" id="remove_bt">移除</a>
	        </div>
	    </div>
	</div>
	<div style="display:none" id="cart_tmpHtml"></div>

	<footer class="wap_footer">
		<ul class="am-avg-sm-4 footer_nav">
			<li><a href="${ctxPath!}/index" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/home.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/home-ck.png" /><em>首页</em></a></li>
			<li><a href="${ctxPath!}/member/cart" class="on"><img class="idefault" src="${ctxPath}/resource/web/image/icon/cart.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/cart-ck.png" /><em>购物车</em></a></li>
			<li><a href="${ctxPath!}/member/index" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/person.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/person-ck.png" /><em>用户中心</em></a></li>
			<li><a href="${ctxPath!}/member/distribute" ><img class="idefault" src="${ctxPath}/resource/web/image/icon/shopping.png" /><img class="iactive" src="${ctxPath}/resource/web/image/icon/shopping-ck.png" /><em>我的众销</em></a></li>
		</ul>
	</footer>


	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>

	<script type="text/javascript">
		
		$(function(){
		
			$(document).on("click", "#toCheckOut", function(){
				if($("input[name='productIds']").is(':checked'))
				{
					buyForm.submit();
				}
			});
		
			$(document).on("click", ".NumDecrease", function(){
				var parent = $(this).parents('.CartItem');
				var ipt = parent.find('.NumInput');
				if( 1 < ipt.val() )
				{
					var num = parseInt(ipt.val()) - 1;
					modifyItem(parent, num);
				}
			});
			
			$(document).on("click", ".NumIncrease", function(){
				var parent = $(this).parents('.CartItem');
				var ipt = parent.find('.NumInput');
				var num = parseInt(ipt.val()) + 1;
				modifyItem(parent, num);
			});
			
			$(document).on("change", ".cartEdit", function(){
				var parent = $(this).parents('.CartItem');
				var ipt = $(this);
				if( 1 <= ipt.val() )
				{
					modifyItem(parent, parseInt(ipt.val()));
				} else {
					ipt.val(1);
					modifyItem(parent, 1);
				}
			});
			
			$(document).on("click", ".cartDel", function(){
				var pid = $(this).attr('val');
				var obj = $('#' + pid);
				var parent = $(this).parents('.CartItem');
				if(confirm('您确定要删除吗？'))
				{
					$.ajax({
						type: "POST",
						url:"${ctxPath!}/member/del/cart/" + pid,
						success:function(result)
						{
							obj.remove();
							var data = $.parseJSON(result);
							if( '200' == data.statusCode ) {
								parent.remove();
								cartTotal();
							} else {
								showTipsBox(data.msg);
							}
						}
					});
				}
			});
			
			function modifyItem(itemObj, num)
			{
				var ipt = itemObj.find('.NumInput');
				if( 1 <= num )
				{
					var item_price = itemObj.find('.item_price').val();
					var item_marketPrice = itemObj.find('.item_marketPrice').val();
					var ItemPrice = itemObj.find('.ItemPrice');
					var ItemOldPrice = itemObj.find('.ItemOldPrice');
					$.ajax({
						type: "POST",
						url:"${ctxPath!}/update/cart/product/" + itemObj.attr('id') + "/" + num,
						success:function(result)
						{
							var data = $.parseJSON(result);
							if( '200' == data.statusCode ) {
								//ipt.attr('value', num);
								ipt.val(num);
								ItemPrice.text( item_price * num );
								ItemOldPrice.text( item_marketPrice * num );
								cartTotal();
							} else {
								showTipsBox(data.msg);
							}
						}
					});
				}
			}
			
			//算总
			function cartTotal()
			{
				var itemObj = $('.CartItem');
				var ItemCheck;
				var sum = 0;
				var num = 0;
				$.each(itemObj, function(i, n) {
					ItemCheck = $(this).find('.ItemCheck');
					if( ItemCheck.hasClass('on') )
					{
						sum += parseInt($(this).find('.ItemPrice').text());
						num += parseInt($(this).find('.NumInput').val());
						$(this).find('.productIds').prop("checked",true);
					} else {
						$(this).find('.productIds').prop("checked",false);
					}
				});
				$('.CartTotal').text(sum);
				$('.PayNum').text(num);
			}
		
			//购物车复选框点击事件
			$(document).on("click",".CartList .ItemCheck",function(){
				var $this = $(this);
				if ( $this.hasClass('on') ) {
					$this.removeClass('on');
					$this.children().removeClass('on');
				}else {
					$this.addClass('on');
					$this.children().addClass('on');
				}
				allCheck();
				var ItemChecked = $('.CartList .ItemCheck.on');
			});
			
			//全选按钮事件
			var $CheckAll = $('.CheckAll');
			$(document).on("click",".CheckAll",function(){
				var $ItemCheck = $('.CartList .ItemCheck');
				var $this = $(this);
				if ( $this.hasClass('on') ) {
					$this.removeClass('on');
					$this.children().removeClass('on');
					$ItemCheck.removeClass('on');
					$ItemCheck.children().removeClass('on');
				}else {
					$this.addClass('on');
					$this.children().addClass('on');
					$ItemCheck.addClass('on');
					$ItemCheck.children().addClass('on');
				}
				cartTotal();
			});
			
			
			//选中状态检测
			function allCheck(){
				var $CheckAll = $('.CheckAll');
				var $ItemChecked = $('.CartList .ItemCheck.on');
		
				$ItemChecked.children().addClass('on');
				var $ItemCheck = $('.CartList .ItemCheck');
				if ( $ItemCheck.length > 0 ) {
					//单选满足全选时改变全选对象状态
					if ( $ItemChecked.length == $ItemCheck.length ) {
						$CheckAll.addClass('on');
						$CheckAll.children().addClass('on');
					}else {
						$CheckAll.removeClass('on');
						$CheckAll.children().removeClass('on');
					}
				}else {
					$CheckAll.removeClass('on');
					$CheckAll.children().removeClass('on');
				}
				cartTotal();
			}
		});
	</script>
</body>
</html>