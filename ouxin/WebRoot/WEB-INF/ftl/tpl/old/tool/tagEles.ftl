<#macro tagEles items>
	<#if items??>
		<#if items?is_hash>
			<#list items?keys as key>
				${("" != key!?trim)?string(' ' + key! + '=' + (items[key]!), '')}
			</#list>
		<#elseif items?is_sequence>
			<#list items as item>
				<#if item??>
					<#if item?is_hash || item?is_sequence>
						<@tagEles item/>
					<#elseif "" != item!?trim>
						${' ' + item}
					</#if>
				</#if>
			</#list>
		</#if>
	</#if>
</#macro>