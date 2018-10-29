
<#macro tree treeNodes items='' layer="1">

	<#if treeNodes?? && 0 lt treeNodes?size>
		<#if "" != items>
			tree_data${items} = { "children" :
		<#else>
			var tree_data =
		</#if>
		{
		<#list treeNodes as item>
			'${item.node.id}' : {
				id : '${item.node.id}'
				, text : '${item.node.name}'
				, type : '${(0 lt item.children?size)?string('folder', 'item')}'
				${(item.checked)?string(", 'additionalParameters' : {'item-selected' : true}", "")}
				, attr : {
						cssClass : '${(item.checked)?string("tree-selected tree-open", "")}'
						${(item.checked)?string(", 'item-selected' : true", "")}
						}
				}${((item_index + 1) lt treeNodes?size)?string(',', '')}
		</#list>
		<#if "" != items>
			}};
		<#else>
			};
		</#if>
		<#list treeNodes as item>
			<#if item.children?? && 0 lt item.children?size>
				<#if "11" == layer>
					<@tree treeNodes=item.children items="['${item.parent.node.id}']['additionalParameters']['children']['${item.node.id}']['additionalParameters']" layer=(layer+1)/>
				<#else>
					<@tree treeNodes=item.children items="['${item.node.id}']['additionalParameters']" layer=(layer+1)/>
				</#if>
			</#if>
		</#list>
	</#if>
	<#nested />
</#macro>