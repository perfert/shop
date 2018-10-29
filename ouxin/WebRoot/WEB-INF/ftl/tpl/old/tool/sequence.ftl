<#macro sequence value>
	<#if value?is_sequence>
		<#list value as item>
			<@sequence value=item; key><#nested key /></@sequence>
		</#list>
	<#elseif "" != value>
		<#nested value />
	</#if>
</#macro>

