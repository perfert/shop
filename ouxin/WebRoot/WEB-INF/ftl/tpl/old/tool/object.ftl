<#macro hash eles>
	<#if eles?is_hash>
		<#list eles?keys as key>
			<#if eles[key]?is_sequence || eles[key]?is_hash>
				<@hash eles=eles[key]; key, value><#nested key, value/></@hash>
			<#else>
				<#nested key, eles[key]/>
			</#if>
		</#list>
	<#elseif eles?is_sequence>
		<@list eles=eles; kye, value><#nested key, value/></@list>
	<#else>
		<#nested key, eles/>
	</#if>
</#macro>

<#macro list eles=[]>
	<#if eles?is_sequence>
		<#list eles as item>
			<@list eles=item; key, value><#nested key, value/></@list>
		</#list>
	<#elseif eles?is_hash>
		<@hash eles=eles; key, value><#nested key, value/></@hash>
	<#else>
		<#nested key, eles/>
	</#if>
</#macro>