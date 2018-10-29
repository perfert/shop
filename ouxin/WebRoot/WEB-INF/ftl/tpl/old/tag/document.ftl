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

<#macro head title="">
	<head>
		<@headTitle name=title />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<@commonRes />
		<#nested/>
	</head>
</#macro>

<#macro headTitle name="">
	<title>${name!""}</title>
</#macro>

<#-- hrefs=[xxx,xxx] -->
<#macro csslink hrefs=[]>
	<@sequence value=hrefs; key>
		<link rel="stylesheet" type="text/css" href=${key} />
	</@sequence>
</#macro>

<#-- srcs=[xxx,xxx] -->
<#macro script srcs=[]>
	<@sequence value=srcs; key>
		<script type="text/javascript" src=${key}></script>
	</@sequence>
</#macro>

<#macro meta eles="" name="" content="">
	<#if "" != name && "" != content>
		<meta name="${name}" content="${content}" />
	<#else>
		<@list eles=eles; key, value>
			<meta ${key}=${value} />
		</@list>
	</#if>
</#macro>


<#macro body id="" class="" style="">
	<body <#if "" != id> id="${id}"</#if><#if "" != class> class="${class}"</#if><#if "" != style> style="${style}"</#if>>
		<#nested />
	</body>
</#macro>