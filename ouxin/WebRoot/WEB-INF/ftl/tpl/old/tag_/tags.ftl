
<#macro head title="">
	<head>
		<@headTitle name=title />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<#nested/>
	</head>
</#macro>

<#macro body id="" name="" class="" style="" eles="">
	<body <#include "../common/tagAttr.ftl"/> ><#nested/></body>
</#macro>

<#macro div id="" name="" class="" style="" eles="">
	<div <#include "../common/tagAttr.ftl"/> ><#nested/></div>
</#macro>

<#macro ul id="" name="" class="" style="" eles="">
	<ul <#include "../common/tagAttr.ftl"/> ><#nested/></ul>
</#macro>

<#macro li id="" name="" class="" style="" eles="">
	<li <#include "../common/tagAttr.ftl"/> ><#nested/></li>
</#macro>

<#macro tr id="" name="" class="" style="" eles="">
	<tr <#include "../common/tagAttr.ftl"/> ><#nested/></tr>
</#macro>

<#macro strong id="" name="" class="" style="" eles="">
	<strong <#include "../common/tagAttr.ftl"/> ><#nested/></strong>
</#macro>

<#macro th id="" colspan="" rowspan="" name="" class="" style="" width="" eles="">
	<th
		${("" != colspan!?trim)?string(' colspan="' + (colspan!) + '"', '')}
		${("" != rowspan!?trim)?string(' rowspan="' + (rowspan!) + '"', '')}
		<#include "../common/tagAttr.ftl"/>
	><#nested/></th>
</#macro>

<#macro td id="" colspan="" rowspan="" name="" class="" style="" eles="">
	<td
		${("" != colspan!?trim)?string(' colspan="' + (colspan!) + '"', '')}
		${("" != rowspan!?trim)?string(' rowspan="' + (rowspan!) + '"', '')}
		<#include "../common/tagAttr.ftl"/>
	><#nested/></td>
</#macro>

<#macro input type="" id="" name="" value="" class="" style="" eles="">
	<input
		${("" != type!?trim)?string(' type="' + (type!) + '"', '')}
		${("" != value!?trim)?string(' value="' + (value!) + '"', '')}
		<#include "../common/tagAttr.ftl"/>
	/>
</#macro>

<#macro a href="" id="" name="" class="" style="" target="" title="" eles="">
	<a
		${("" != href!?trim)?string(' href="' + (href!) + '"', '')}
		${("" != target!?trim)?string(' target="' + (target!) + '"', '')}
		<#include "../common/tagAttr.ftl"/>
	><#nested/></a>
</#macro>