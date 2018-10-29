<#macro head title="">
<head>
	<@headTitle name=title />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<#nested/>
</head>
</#macro>

<#macro a href="" id="" name="" class="" style="" target="" title="" eles="">
	<a
		${("" != href!?trim)?string(' href="' + (href!) + '"', '')}
		${("" != target!?trim)?string(' target="' + (target!) + '"', '')}
		<#include "../common/tagAttr.ftl"/>
	><#nested/></a>
</#macro>