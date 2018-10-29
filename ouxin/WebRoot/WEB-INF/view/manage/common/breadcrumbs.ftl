<#--
	breadcrumbs page
	@author: MAS
-->
<#macro breadcrumbs items=''>
					<!-- #section:basics/content.breadcrumbs -->
					<div class="breadcrumbs breadcrumbs-fixed" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<@o.a href="${ctxPath!}/manage/main">首 页</@o.a>
							</li>
							
							<#if items??>
								<#if items?is_hash>
									<#list items?keys as key>
										<#if key?? && "" != key!?trim>
											<#if "#" == key!trim>
												<li class="active">${items[key]!''}</li>
											<#else>
												<li class="active"><@o.a href="${key}">${items[key]!''}</a></@o.a></li>
											</#if>
										</#if>
									</#list>
								<#elseif items?is_sequence>
									<#list items as item>
										<#if item??>
											<#if "" != item!?trim>
												<li class="active">${items!}</li>
											</#if>
										</#if>
									</#list>
								<#elseif "" != items!?trim>
									<li class="active">${items!}</li>
								</#if>
							</#if>
						</ul><!-- /.breadcrumb -->
					</div>
					<!-- /section:basics/content.breadcrumbs -->
</#macro>