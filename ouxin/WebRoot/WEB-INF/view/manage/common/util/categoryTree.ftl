
<#macro categoryTree treeNodes items=''>

	<#if treeNodes?? && 0 lt treeNodes?size>
		<#if "" != items>
			category_tree_data${items} = { "children" :
		<#else>
			var category_tree_data =
		</#if>
		{
		<#list treeNodes as item>
			'${item.node.id}' : {
				id : '${item.node.id}'
				, text : '${item.node.name}'
				, type : '${(0 lt item.children?size)?string('folder', 'item')}'
				${(item.select)?string(", 'additionalParameters' : {'item-selected' : true}", "")}
				, attr : {
						cssClass : '${(item.select)?string("tree-selected tree-open", "")}'
						${(item.select)?string(", 'item-selected' : true", "")}
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
				<@categoryTree treeNodes=item.children items="['${item.node.id}']['additionalParameters']"/>
			</#if>
		</#list>
	</#if>
	<#nested />
</#macro>