<#macro pageList>
	<ul class="page_list">
		<#nested/>
	</ul>
</#macro>

<#macro pageListItem on=false formAction="" more=false>
	<#if true == more>
		<li><b>...</b></li>
	<#else>
		<li><a href="javascript:;" class="Abtn f_submit <#if true == on>on</#if>" formAction="${formAction}"><span><cite><#nested/></cite></span></a></li>
	</#if>
</#macro>