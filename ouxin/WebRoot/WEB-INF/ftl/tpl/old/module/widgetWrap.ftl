<#macro inputWrap type="" label="" id="" value="" name="" width="" verify="" required=false readonly="">
	<#if "" != label>
		<@strong>${label}</@strong>
	</#if>
	<@iText type=type id=id value=value name=name width=width verify=verify readonly=readonly />
	<#if true == required>
		<@iRequired />
	</#if>
</#macro>

<#macro textareaWrap label="" id="" value="" name="" width="" height="" verify="" required=false>
	<#if "" != label>
		<@strong>${label}</@strong>
	</#if>
	<@iTextarea id=id value=value name=name width=width height=height verify=verify />
	<#if true == required>
		<@iRequired />
	</#if>
</#macro>


<#--
	label 标签名称
	required 是否显示必填的星号

	value={} 逆向级联json数据

	isLast 最后的select是否一定要选择
-->
<#macro selectWrap id="" label="" required=false value={} items=[] itemValue="" itemText="" defaultValue="" name="" width="" cwidth="" align="" ajax="" verify="" ignore="" isLast=false load="">
	<#if "" != ajax>

		<@levelWrap id=id name=name defaultValue=defaultValue ajax=ajax itemValue=itemValue itemText=itemText verify=verify ignore=ignore isLast=isLast load=load>
			<#if "" != label><@strong>${label}</@strong></#if>
			<@cascade width=width value=value defaultValue=defaultValue items=items itemValue=itemValue itemText=itemText ajax=ajax ignore=ignore />
			<#if required><@iRequired /></#if>
		</@levelWrap>

	<#else>

		<#if "" != label><@strong>${label}</@strong></#if>
		<@iSelect id=id defaultValue=defaultValue items=items itemValue=itemValue itemText=itemText width=width name=name cwidth=cwidth align=align verify=verify />
		<#if required><@iRequired /></#if>

	</#if>
</#macro>

<#--
	异步级联及自动逆向级联

	value={} 用于判断级联输出结构
-->
<#macro cascade width="" value="" defaultValue="" items=[] itemValue="" itemText="" ajax="" ignore="">
	<#if (value.parent)??>
		<@cascade width=width value=value.parent defaultValue=value[itemValue] items=items itemValue=itemValue itemText=itemText ajax=ajax ignore=ignore />
		<@iSelect width=width defaultValue=value[itemValue] items=value.parent.children itemValue=itemValue itemText=itemText ajax=ajax ignore=ignore />
	<#elseif "" != value>
		<@iSelect width=width defaultValue=value[itemValue]! items=items itemValue=itemValue itemText=itemText ajax=ajax ignore=ignore />
	<#else>
		<@iSelect width=width defaultValue=defaultValue items=items itemValue=itemValue itemText=itemText ajax=ajax ignore=ignore />
	</#if>
</#macro>

<#macro editorWrap uploadJson label="" id="" value="" name="" width="730px" height="" verify="" required=false keType="simple">
	<#if "" != label>
		<@strong>${label}</@strong>
	</#if>
	<@editor id=id value=value name=name width=width height=height verify=verify uploadJson=uploadJson  keType=keType />
	<#if true == required>
		<@iRequired />
	</#if>
</#macro>


<#macro uploadWrap label="" id="" value="" name="" class="" verify="" required=false>
	<#if "" != label>
		<@strong>${label}</@strong>
	</#if>
	<@upload id=id name=name class=class verify=verify />
	<#if true == required>
		<@iRequired />
	</#if>
</#macro>