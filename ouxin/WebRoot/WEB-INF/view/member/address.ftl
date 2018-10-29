<!DOCTYPE html>
<html class="no-js">
<head>
	<title>我的收货地址</title>
	<meta charset="utf-8">
	<meta http-equiv="Cache-Control" content="no-siteapp"/>
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="email=no">
	<meta name="format-detection" content="address=no">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link rel="stylesheet" href="../resource/web/styles/storewap/css/amazeui.min.css">
	<link rel="stylesheet" href="../resource/web/styles/storewap/css/common.css">
	<script src="../resource/web/scripts/jquery/jquery-1.11.2.min.js"></script>
	<script src="../resource/web/scripts/storewap/amazeui.min.js"></script>
	<script src="../resource/web/scripts/util/jquery.cookie.js"></script>
	<script src="../resource/web/scripts/jqueryQrcode/jquery.qrcode.min.js"></script>
	<script src="../resource/web/scripts/storewap/pop.js"></script>
	<script>
		var _hmt = _hmt || [];
		(function() {
			var hm = document.createElement("script");
			hm.src = "//hm.baidu.com/hm.js?a20a1085f6b134c671e0089abf515716";
			var s = document.getElementsByTagName("script")[0]; 
			s.parentNode.insertBefore(hm, s);
		})();
	</script>
	<script src="../resource/web/scripts/storewap/common.js"></script>
	<style type="text/css">
		.addr_add {text-align:center;border-top:0.1rem solid #eee;border-bottom:0.1rem solid #eee;}
		.addr_add i:after {border:0.1rem solid #c11;content:"\f067";display:inline-block;width:2.2rem;height:2.2rem;color:#e11;border-radius:2.2rem;margin-right:0.5rem;}
		
		.addr_list {border-bottom:0.1rem solid #eee;margin-top:1rem;}
		.addr_list li {border-top:0.1rem solid #eee;padding:0;overflow:hidden;}
		.addr_list .data_wrap {margin:0;}
		.data_wrap .date_more{width:4rem;}
	</style>
</head>

<body>
	<div class="">
		<a href="${ctxPath!}/member/address/input<#if productIds?? && 0 lt productIds?size>?productIds=<#list productIds as item><#if 0 lt item_index>,</#if>${item}</#list></#if>" class="data_wrap addr_add"><i></i>添加新地址</a>
		<ul class="addr_list">
			<#if addressList?? && 0 lt addressList?size>
				<#list addressList as item>
					<li>
						<div class="edit_wrap EditWrap">
							<span class="edit_toolbar EditToolbar" ><a href="${ctxPath!}/member/address<#if productIds?? && 0 lt productIds?size>?productIds=<#list productIds as item><#if 0 lt item_index>,</#if>${item}</#list></#if>" class="data_edit"><em>编辑</em></a><a href="javascript:;" attr-id="460000172656230" class="data_delete"><em>删除</em></a></span>
							<a href="${ctxPath!}/member/check_order?addressId=${item.id}<#if productIds?? && 0 lt productIds?size>&productIds=<#list productIds as item><#if 0 lt item_index>,</#if>${item}</#list></#if>" class="data_wrap DataWrap" attr-id="460000172656230">
								<span class="date_more"><em><b class="icheck ICheck"></b></em></span>
								<span class="wrap_block">
									<span class="wrap_block"><em>收件人：${item.contactName}</em><em class="wrap_right">${item.contactPhone}</em></span>
									<span class="wrap_block">${item.address}</span>
								</span>
							</a>
						</div>
					</li>
				</#list>
			</#if>
		</ul>
	</div>
	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>
</body>
</html>