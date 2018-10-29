<#--
	左右树接口.
	items: 整科树 list. <list>
	checks: 树节点中, 需要选中的值. <list>
	name: input name.
-->
<#macro tree items checks=[] type="" name="" href="" hrefParam="">
	<#if items?? && 0 lt items?size>
		<div class="tree_wrap">
			<#list items as item>
				<p class="tree_item"><label class="tree_label"><#if "" != type><a href="javascript:;" class="Icheck CIcheck <#if checks?? && 0 lt checks?size && checks?seq_contains(item.id)>on</#if>"><i></i><input type="checkbox" value="${(item.id)!}" name="${name!}" <#if checks?? && 0 lt checks?size && checks?seq_contains(item.id)>checked="checked"</#if> /></a></#if><a href="javascript:;" class="tree_arrow"></a><a href="<#if "" != href>${href!}${item.id}${hrefParam!}<#else>javascript:;</#if>">${(item.name)!}</a></label></p>
				<#if item.children??>
					<@tree items=item.children checks=checks type=type name=name href=href hrefParam=hrefParam/>
				</#if>
			</#list>
		</div>
	</#if>
</#macro>

<#macro treeWrap items checks=[] name="" label="树" type="" href="" hrefParam="">
	<#if items?? && 0 lt items?size>
		<div class="Itree ${type!}">
			<strong>${label!'树'}</strong>
			<@tree items=items checks=checks type=type name=name href=href hrefParam=hrefParam/>
		</div>
	</#if>
</#macro>