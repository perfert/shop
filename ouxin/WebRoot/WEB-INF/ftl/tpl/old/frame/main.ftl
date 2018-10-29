<#macro mainBody>
	<body>
		<#nested />
		<div class="clearperfect"></div>
		<div class="footer">
			<a href="www.oa161.com" target="blank" class="t_site" /></a>
			<a href="javascript:;" class="t_menu Ct_menu" /></a>
			<a href="${base}/logout" class="t_exit" /></a>
		</div>
	</body>
</#macro>

<#-- module @logo  -->
<#macro mainHeader>
	<div class="header">
		<#nested />
		<@logo />
		<iframe class="menu Cmenu" name="menuFrame" frameborder="0" width="100%" scrolling="auto" src="${base}/security/main/menu"></iframe>
	</div>
</#macro>

<#macro mainIframe id="" src="">
	<iframe class="main Cmain" <#if "" != id>id="${id}"</#if> name="mainFrame" frameborder="0" width="100%" scrolling="auto" src="${src!""}"></iframe>
</#macro>

<#macro mainScript>
	<script type="text/javascript">
	<!--

	$(function(){

		//菜单框架，主框架
		var $screenHeight = $(window).height();
		$('.Cmenu,.Cmain').height($screenHeight - 90);
		$(window).resize(function(){
			var $screenHeight = $(window).height();
			$('.Cmenu,.Cmain').height($screenHeight - 90);
		});

		//显示菜单
		$('.Ct_menu').click(function(){
			if ( $('.Cmenu').css('display') == 'none' ) {
				$('.Cmenu').slideDown();
			}else {
				$('.Cmenu').slideUp();
			}
		});

	});
	//-->
	</script>
</#macro>