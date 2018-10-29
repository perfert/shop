<#if pager?? && 0 lt pager.result!?size>
	<#list pager.result as item>
		<li id="orderList_460000172656251">
			<a href="${ctxPath}/member/order/${item.id}/detail" class="editOrder">
				<!-- 已取消 -->
				<div class="bb1 p20">
					<p class="f16">订单号：${item.orderSn}</p>
				</div>
				<div class="bb1 p20 myOrder_goods">
					<#assign nums=0/>
					<#list item.items as it>
						<#assign nums=nums + it.num/>
						<img src="${ctxPath}/${it.image}" />
					</#list>
					<p class="f16 all_goods">X <var>${nums!1}</var>件商品 <i class="am-icon-chevron-right ccc"></i></p>
					<div class="cb"></div>
				</div>
			</a>
			<div class="bb1 p20">
				<p class="f18">订单金额：￥<var>${item.payPrice?string(",##0.00")}</var></p>
				<p class="tr mt20">
					<#if -2 == item.orderState>
						<a href="javascript:;" attr-href="#" role="button" class="skg_btn on f16 mr10 topay">已取消</a>
					<#elseif -1 == item.orderState>
						<a href="javascript:;" attr-href="#" role="button" class="skg_btn on f16 mr10 topay">已删除</a>						
					<#elseif 0 == item.orderState>	
						<a href="${ctxPath}/weichat/pay/order/${item.id}" attr-href="#" role="button" class="skg_btn on f16 mr10 topay">去支付</a>
					<#elseif 1 == item.orderState>
						<a href="javascript:;" attr-href="#" role="button" class="skg_btn on f16 mr10 topay">已支付，待发货</a>
					<#elseif 2 == item.orderState>
						已发货&nbsp;&nbsp;${item.logisticsName}&nbsp;&nbsp;${item.logisticsSn}<#--&nbsp;&nbsp;<a href="${ctxPath}/member/logistics/${item.logisticsCode}/${item.logisticsSn}" role="button" class="look">查看物流</a>-->
					<#elseif 3 == item.orderState>
						<a href="javascript:;" attr-href="#" role="button" class="skg_btn on f16 mr10 topay">已收货</a>
					<#elseif 4 == item.orderState>
						<a href="javascript:;" attr-href="#" role="button" class="skg_btn on f16 mr10 topay">已完成</a>
					<#else>
						<a href="javascript:;" attr-href="#" role="button" class="skg_btn on f16 mr10 topay">异常</a>
					</#if>
				</p>
			</div>
		</li>
	</#list>
</#if>