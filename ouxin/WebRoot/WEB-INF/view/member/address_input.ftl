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
	<script src="${ctxPath}/resource/web/scripts/storewap/verifyData.js"></script>
	<style type="text/css">
		.address_edit {border-top:0.1rem solid #eee;border-bottom:0.1rem solid #eee;padding-top:0;padding-bottom:0;}
		.addr_select {display:block;height:2.4rem;line-height:2.4rem;}
		.addr_list {overflow:hidden;}
		.addr_list li {border-top:0.1rem solid #eee;line-height:3.6rem;height:3.6rem;padding-left:1.5rem;font-size:1.4rem;}
	</style>
</head>

<body>
	<div class="AddressCheck">
		<div class="data_wrap address_edit">
			<input type="hidden" id="addrId" value="${bean.id}"/>
			<input type="hidden" id="isDefault" value="${(bean?? && bean.isDefault)?string(1, 0)}"/>
			<table class="form_table">
				<tr>
					<th style="width:9rem;"><strong>收货人：</strong></th>
					<td><input class="itext" type="text" id="person" value=""/></td>
				</tr>
				<tr>
					<th><strong>手机号码：</strong></th>
					<td><input class="itext" type="text" verify-data="mobile" id="mobile" value="" maxlength="13" /></td>
				</tr>
				<tr>
					<th><strong>所在地区：</strong></th>
					<td><div class="addr_select AddressSelect" ></div></td>
				</tr>
				<tr style="border:0;">
					<th><strong>详细地址：</strong></th>
					<td><input class="itext" type="text" id="addr" value=""/></td>
				</tr>
			</table>
		</div>
		<div style="padding:1.5rem;"><a href="javascript:;" class="abtn confirmFixed VerifySubmit">确&emsp;认</a></div>
	</div>

	<div class="dialog_frame confirm DialogFrame" id="AddressBox">
		<b class="dialog_mask DialogMask"></b>
		<div class="dialog_box DialogBox">
			<div class="dialog_body DialogBody">
				<table class="form_table">
					<tr>
						<th style="width:9rem;"><strong>省份：</strong></th>
						<td><div class="addr_select AddressDetail province" attr-id="" ></div></td>
					</tr>
					<tr>
						<th><strong>城市：</strong></th>
						<td><div class="addr_select AddressDetail city" attr-id="" attr-parentId=""></div></td>
					</tr>
					<tr style="border:0;">
						<th><strong>所在地区：</strong></th>
						<td><div class="addr_select AddressDetail county" attr-id="" attr-parentId=""></div></td>
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

	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>
	
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
		
			//地址选择处理
			var $AddressSelect = $('.AddressSelect');
			var $AddressEditConfirm = $('.AddressEditConfirm');
			var $AddressDetail = $('.AddressDetail');
			var $AddressBox = $('#AddressBox');
			var $AddressDetailBox = $('#AddressDetailBox');
			
			//地址编辑层触发
			$AddressSelect.click(function(){
				dialogShow($AddressBox);
			});
			//省市区
			$AddressDetail.click(function(){
				var $this = $(this);
				var parentId = $(this).attr("attr-parentId")?$(this).attr("attr-parentId"):"";
				var path;
				
				if($this.hasClass("province")){
					path = "${ctxPath!}/province";
				}
				
				if($this.hasClass("city")){
					if($(".province").text() == ""){
						showTipsBox('<strong>请先选择省份</strong>');
						return;
					}
					path = "${ctxPath!}/" + parentId + "/city";
				}
				if($this.hasClass("county")){
					if($(".city").text() == ""){
						showTipsBox('<strong>请先选择城市</strong>');
						return;
					}
					path = "${ctxPath!}/" + parentId + "/area";
				}
				$.ajax({
					type: "POST",
					url:path,
					success:function(result){
						//$this.addClass('on');
						//dialogShow($AddressDetailBox);
						var addrList = $('#addrList');
						addrList.html('');
						if( 0 < result.length)
						{
							var data=$.parseJSON(result);
							var obj;
							for(var idx in data)
							{
								obj = data[idx];
								addrList.append('<li class="AddressItem" attr-id="'+ obj.code +'" key="'+ obj.code +'">'+ obj.name +'</li>');
							}
							$this.addClass('on');
							dialogShow($AddressDetailBox);
						}
					}
				})
			});
			//省市区确定
			$AddressEditConfirm.click(function(){
				var addressArray = [];
				var addressIdArray = [];
				$.each($AddressDetail, function(i, n){
					var $n = $(n);
					if ( $n.text() == '' ) {
						showTipsBox('请完善<strong>省市区</strong>信息');
					}else {
						addressArray.push($n.text());
						addressIdArray.push($n.attr("attr-id"));
					}
				});
				if ( addressArray.length == 3 ) {
					$AddressSelect.text(addressArray.join('，'));
					$AddressSelect.attr("attr-addrIdArr", addressIdArray.join('，'));
					dialogClose($AddressBox);
				}
			})
			//省市区列表
			var $AddressList = $('.AddressList');
			$AddressList.on('click', '.AddressItem', function(){
				var $this = $(this);
				var $AddressDetailOn = $('.AddressDetail.on');
				
				var id = $this.attr("attr-id");
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
					var addrNameArr = $(".province").text() + "，" + $(".city").text() + "，" + $(".county").text();
					var addrIdArr = $(".province").attr("attr-id") + "，" + $(".city").attr("attr-id") + "，" + $(".county").attr("attr-id");
					$AddressSelect.text(addrNameArr);
					$AddressSelect.attr("attr-addrIdArr", addrIdArr);
				}
			}
			
			function editAddr(){
				var addrNameArr = $AddressSelect.text().split('，');
				var addrIdArr = $AddressSelect.attr("attr-addrIdArr").split('，');
				var obj = {
						'bean.id' : $("#addrId").val()?$("#addrId").val():"",
						'bean.provinceCode' : $.trim(addrIdArr[0]),
						'bean.cityCode' : $.trim(addrIdArr[1]),
						'bean.areaCode' : $.trim(addrIdArr[2]),
						'bean.address' : ($.trim(addrNameArr[0]) + " " + $.trim(addrNameArr[1]) + " " + $.trim(addrNameArr[2]) + " " + $.trim($("#addr").val())),
						'bean.detail' : $.trim($("#addr").val()),
						'bean.contactName' : $.trim($("#person").val()),
						'bean.contactPhone' : $.trim($("#mobile").val()),
						'bean.isDefault' : $.trim($("#isDefault").val())
					};
					
				$.ajax({
					type : 'POST',
					url : "${ctxPath!}/member/update/address",
					data : obj,
					success : function(data){
						var res = $.parseJSON(data);
						if(res.statusCode == "200"){
							<#if itemId?? && "" != itemId>
								window.location.href = "${ctxPath!}/yyg/to/award/${itemId}";
							<#else>
								window.location.href = "${ctxPath!}/member/address<#if productIds?? && 0 lt productIds?size>?productIds=<#list productIds as item><#if 0 lt item_index>,</#if>${item}</#list></#if>";
							</#if>
						}else{
							showTipsBox('<strong>新增收货地址失败，'+res.msg+'</strong>');
						}
					},
					error : function(res){
						showTipsBox('<strong>新增收货地址失败</strong>');
					}
				});
			}
		});
	</script>
</body>
</html>