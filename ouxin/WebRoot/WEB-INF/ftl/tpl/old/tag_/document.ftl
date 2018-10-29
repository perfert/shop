<#--  tool  @sequence  @list  -->

<#macro document xhtml=false>
	<!DOCTYPE HTML<#if xhtml?? && true == xhtml> PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"</#if>>
	<@html xhtml=xhtml><#nested/></@html>
</#macro>

<#macro html xhtml=false vml="">
	<html<#if xhtml?? && true == xhtml> xmlns="http://www.w3.org/1999/xhtml"</#if>>
		<#nested/>
	</html>
</#macro>

<#macro headTitle name="">
	<title>${name!""}</title>
</#macro>

<#-- hrefs=[xxx,xxx] -->
<#macro csslink resPefrix=false mngResPerfix=false hrefs=[]>
	<@sequence value=hrefs; key>
		<#if resPefrix!>
			<link rel="stylesheet" type="text/css" href="<@o.resUrl uri='${key}' />" />
		<#elseif mngResPerfix!>
			<link rel="stylesheet" type="text/css" href="<@o.mngResUrl uri='${key}' />" />
		<#else>
			<link rel="stylesheet" type="text/css" href="${key}" />
		</#if>
	</@sequence>
</#macro>

<#-- srcs=[xxx,xxx] -->
<#macro script resPefrix=false mngResPerfix=false srcs=[]>
	<@sequence value=srcs; key>
		<#if resPefrix!>
			<script type="text/javascript" src="<@o.resUrl uri='${key}' />" charset="UTF-8"></script>
		<#elseif mngResPerfix!>
			<script type="text/javascript" src="<@o.mngResUrl uri='${key}' />" charset="UTF-8"></script>
		<#else>
			<script type="text/javascript" src="${key}" charset="UTF-8"></script>
		</#if>
	</@sequence>
</#macro>

<#macro meta eles="" name="" content="">
	<#if "" != name!?trim && "" != content!?trim>
		<meta name="${name}" content="${content}" />
	<#else>
		<@list eles=eles; key, value>
			<meta ${key}="${value}" />
		</@list>
	</#if>
</#macro>