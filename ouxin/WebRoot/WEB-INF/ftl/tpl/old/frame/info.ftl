<#macro infoBody>
	<body class="info_body">
		<div class="sysinfo">
			<#nested />
		</div>
	</body>
</#macro>

<#macro infoTab title="">
	<div class="info_tab info_box InfoTab">
		<div class="f_title"><span><cite><strong>${title}</strong></cite></span></div>
		<#nested />
	</div>
</#macro>

<#macro infoTabLabelList>
	<ul class="tab_list clearfix InfoTabLabelList">
		<#nested />
	</ul>
	<div class="clearperfect"></div>
</#macro>

<#macro infoTabLabelItem label="" on=false>
	<li><a href="javascript:;" <#if true==on>class="on"</#if>>${label}</a></li>
</#macro>

<#macro infoTabCon>
	<div class="tab_con">
		<#nested />
	</div>
</#macro>

<#macro infoTabConList>
	<ul class="con_list InfoTabConList">
		<#nested />
	</ul>
</#macro>

<#macro infoTabConItem name="" num="" href="">
	<li>
		<div class="line01"></div>
		<div class="line02"></div>
		<div class="l_text"><em>${name}<var>（${num}个）</var></em><a href="${href}" class="Abtn"><span><cite>立即处理</cite></span></a></div>
	</li>
</#macro>

<#macro infoTable title="">
	<div class="info_table info_box">
		<div class="f_title"><span><cite><strong>${title}</strong></cite></span></div>
		<ul class="table_list">
			<#nested />
		</ul>
	</div>
</#macro>

<#macro infoTableList label="">
	<li>
		<div class="list_title"><span>${label}</span><i></i></div>
		<div class="list_con clearfix">
			<dl>
				<#nested />
			</dl>
		</div>
		<div class="clearperfect"></div>
	</li>
</#macro>

<#macro infoTableListItem name="" num="">
	<dt>${name}<var>${num}</var></dt>
</#macro>



<#macro infoScript>
	<script type="text/javascript">
	<!--
		var $InfoTab = $('.InfoTab');
		var $InfoTabLabelItem = $('.InfoTabLabelList li a');

		//初始化
		$.each($InfoTab,function(i,n){
			var $n = $(n);
			var $InfoTabLabelItem = $n.find('.InfoTabLabelList li a');
			var $InfoTabConList = $n.find('.InfoTabConList');
			if ( $InfoTabLabelItem.hasClass('on') ) {
				$InfoTabConList.eq($InfoTabLabelItem.parent().index()).show();
			}
		})
		$InfoTabLabelItem.click(function(){
			$InfoTabLabelItem.removeClass('on');
			$(this).addClass('on');
			$InfoTabConList.hide();
			$InfoTabConList.eq($InfoTabLabelItem.parent().index()).show();
		});

	//-->
	</script>
</#macro>