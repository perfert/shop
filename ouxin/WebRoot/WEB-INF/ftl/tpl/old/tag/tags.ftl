
<#macro a id="" name="" title="" href="" class="" style="" target="" eles="">
	<a
		<#if "" != title> title="${title}"</#if>
		<#if "" != href> href="${href}"<#else> href="javascript:;"</#if>
		<#if "" != target> target="${target}"</#if>
		<#include "../tool/tagAttr.ftl"/>
		<@tagEles items=eles />
	><#nested/></a>
</#macro>

<#macro li id="" name="" class="" style="" eles="">
	<li	<#include "../tool/tagAttr.ftl"/> <@tagEles items=eles />><#nested/></li>
</#macro>

<#macro tr id="" name="" class="" style="" eles="">
	<tr <#include "../tool/tagAttr.ftl"/> <@tagEles items=eles />><#nested/></tr>
</#macro>

<#macro strong id="" name="" class="" style="" eles="">
	<strong <#include "../tool/tagAttr.ftl"/> <@tagEles items=eles />><#nested/></strong>
</#macro>

<#macro th id="" colspan="" rowspan="" name="" class="" style="" width="" eles="">
	<th
		<#if "" != colspan> colspan="${colspan}"</#if>
		<#if "" != rowspan> rowspan="${rowspan}"</#if>
		<#if "" != width> width="${width}"</#if>
		<#include "../tool/tagAttr.ftl"/>
		<@tagEles items=eles />
	><#nested/></th>
</#macro>

<#macro td id="" colspan="" rowspan="" name="" class="" style="" eles="">
	<td
		<#if "" != colspan> colspan="${colspan}"</#if>
		<#if "" != rowspan> rowspan="${rowspan}"</#if>
		<#include "../tool/tagAttr.ftl"/>
		<@tagEles items=eles />
	><#nested/></td>
</#macro>

<#macro input type="" id="" name="" value="" class="" style="" eles="">
	<input
		<#if "" != type> type="${type}"<#else> type="hidden"</#if>
		<#if "" != value> value="${value}"</#if>
		<#include "../tool/tagAttr.ftl"/>
		<@tagEles items=eles />
	/>
</#macro>