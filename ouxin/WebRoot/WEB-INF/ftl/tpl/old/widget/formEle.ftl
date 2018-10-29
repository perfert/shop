<#macro button formAction="" href="" status="">
	<a href="<#if "" != href>${href}<#else>javascript:;</#if>" class="Abtn ${status} <#if "" != formAction>f_submit</#if>" <#if "" != formAction>formAction="${formAction}"</#if>><span><cite><#nested /></cite></span></a>
</#macro>

<#macro iText type="" id="" value="" name="" width="" verify="" pageCount="" readonly="">
	<span class="Itext <#if "" != pageCount>Jpage JPage</#if>">
		<cite>
			<em>
				<input
					<#if "" != type> type="${type}"<#else> type="text"</#if>
					<#if "" != id> id="${id}"</#if>
					<#if "" != name> name="${name}"</#if>
					<#if "" != value> value="${value}"</#if>
					<#if "" != width> style="width:${width}"</#if>
					<#if "" != verify> data-verify="${verify}"</#if>
					<#if "" != readonly> readonly="readonly"</#if>
				/>
				<#if "" != pageCount><i></i><strong class="JMax">${pageCount}</strong></#if>
			</em>
		</cite>
	</span>
</#macro>

<#--
	id 可不用

	name 隐藏input的name
	defaultValue 隐藏input根据这个找到对应的内容做js处理
	verify 隐藏input，用于js验证

	ajax 储存ajax的路径
	itemValue 储存ajax请求回来的json值的id
	itemText 储存ajax请求回来的json值的value
	load 级联其它select, 格式: [{"id":xxx, "ajax":xxx}, {"id":xxx, "ajax":xxx}] id: div的id, ajax: 请求的URL.

-->
<#macro levelWrap id="" name="" defaultValue="" ajax="" itemValue="" itemText="" verify="" ignore="" isLast=false load="">
	<div id="${id!}" class="select_wrap" ajax="${ajax}" itemValue="${itemValue}" itemText="${itemText}" <#if "" != ignore>data-ignore="${ignore}"</#if>  islast="${isLast?string}" <#if "" != load>load="${load!}"</#if>>
		<input type="hidden" name="${name}" value="${defaultValue}" <#if "" != verify>data-verify="${verify}"</#if>  />
		<#nested />
	</div>
</#macro>


<#--
	width 父级下拉框的宽度
	cwidth 下拉框内容的宽度
	align 下拉框内容的显示位置

	name 隐藏input的name，如果使用ajax就不用传，因为机构不一样
	defaultValue 隐藏input根据这个找到对应的内容做js处理，如果使用ajax，此属性用于判断那个处于选中状态

	itemValue 用于js处理异步json的id，自动循环所需的id
	itemText 用于js处理异步json的value，自动循环所需的value

	ajax 仅用于判断使用哪种结构
	items 仅用于判断是 自动循环 还是 嵌套循环

	formAction 下拉框自动提交表单的action

-->
<#macro iSelect id="" width="" cwidth="" align="" name="" ajax="" items=[] defaultValue="" itemValue="" itemText="" formAction="" verify="" ignore="">

	<#if "" != ajax>

		<span class="Iselect ICselect">
			<cite>
				<em>
					<input type="text" readonly="readonly" value="" <#if "" != width> style="width:${width}"</#if> 	/>
				</em>
			</cite>
			<div class="Iselect_shadow <#if "" != align>Iselect_shadow_${align}<#else>Iselect_shadow_bottom</#if>"></div>
			<div class="Iselect_con <#if "" != align>Iselect_con_${align}<#else>Iselect_shadow_bottom</#if>">
				<ul class="Iselect_list">
					<#if 0 lt items?size>
						<@iSelectItem>请选择</@iSelectItem>
						<#list items as item>
							<#if ignore != item[itemValue]>
								<@iSelectItem defaultValue=defaultValue itemValue=item[itemValue]><#if "" != itemText>${item[itemText]}<#else>${item?string!}</#if></@iSelectItem>
							</#if>
						</#list>
					<#else>
						<#nested />
					</#if>
				</ul>
			</div>
		</span>

	<#else>
		<@widgetWrap id=id>
		<span class="Iselect">
			<cite>
				<em>
					<input type="hidden" name="${name}" value="${defaultValue}" <#if "" != verify>data-verify="${verify}"</#if> />
					<input type="text" readonly="readonly" value="" <#if "" != width> style="width:${width}"</#if> 	/>
				</em>
			</cite>
			<div class="Iselect_shadow <#if "" != align>Iselect_shadow_${align}<#else>Iselect_shadow_bottom</#if>"></div>
			<div class="Iselect_con <#if "" != align>Iselect_con_${align}<#else>Iselect_shadow_bottom</#if>">
				<ul class="Iselect_list">
					<#if 0 lt items?size>
						<@iSelectItem>请选择</@iSelectItem>
						<#list items as item>
							<@iSelectItem defaultValue=defaultValue itemValue=item[itemValue]><#if "" != itemText>${item[itemText]}<#else>${item?string!}</#if></@iSelectItem>
						</#list>
					<#else>
						<#nested />
					</#if>
				</ul>
			</div>
		</span>
		</@widgetWrap>
	</#if>

</#macro>


<#macro iSelectItem formAction="" href="" defaultValue="" itemValue="">
	<#if "" != formAction>
		<li><a href="javascript:;" formAction="${formAction}" value="${itemValue}" class="f_submit <#if "" != defaultValue && defaultValue == itemValue>on</#if>"><#nested /></a></li>
	<#elseif "" != href>
		<li><a href="${href}" value="${itemValue}" <#if "" != defaultValue &&  defaultValue == itemValue>class="on"</#if>><#nested /></a></li>
	<#else>
		<li><a href="javascript:;" value="${itemValue}" <#if "" != defaultValue &&  defaultValue == itemValue>class="on"</#if>><#nested /></a></li>
	</#if>
</#macro>

<#--用于多批选项并行的时候JS处理-->
<#macro checkWrap>
	<div class="check_wrap">
		<#nested />
	</div>
</#macro>

<#--复选框接口-->
<#macro iCheckBox name="" on=false value="">
	<label class="check_label"><a href="javascript:;" class="Icheck CIcheck <#if true==on>on</#if>"><i></i><input type="checkbox" value="${value}" name="${name}" <#if true == on> checked="checked"</#if> /></a><#nested /></label>
</#macro>

<#--单选框接口-->
<#macro iRadio name="" on=false value="">
	<label class="check_label"><a href="javascript:;" class="Iradio CIradio <#if true==on>on</#if>"><i></i><input type="radio" value="${value}" name="${name}" <#if true == on> checked="checked"</#if> /></a><#nested /></label>
</#macro>

<#--是否必填的那颗XX-->
<#macro iRequired>
	<i class="required">*</i>
</#macro>

<#--文本域接口-->
<#macro iTextarea id="" value="" name="" verify="" width="" height="">
	<span class="Ibox">
		<textarea style="width:${width};height:${height};"
			<#if "" != id> id="${id}"</#if>
			<#if "" != name> name="${name}"</#if>
			<#if "" != verify> data-verify="${verify}"</#if>
		>${value}</textarea>

		<b class="ITAborderX borderT"></b>
		<b class="ITAborderX borderB"></b>
		<b class="ITAborderY borderL"></b>
		<b class="ITAborderY borderR"></b>

		<b class="ITAcorner cornerTL"></b>
		<b class="ITAcorner cornerTR"></b>
		<b class="ITAcorner cornerBL"></b>
		<b class="ITAcorner cornerBR"></b>
	</span>
</#macro>

<#macro editor uploadJson="" id="" value="" name="" verify="" width="300px" height="200px"  keType="">
	<@widgetGroup style="float:left;margin-left:10px;">
		<textarea class="kindeditor" name="${name!}" id="${id!}" style='width:${width};height:${height};' <#if "" != verify> data-verify="${verify}"</#if>  uploadJson="${uploadJson!}" keType="${keType!}">${value!}</textarea>
	</@widgetGroup>
</#macro>


<#macro upload id="" name="" verify="" class="" >
	<@widgetGroup style="float:left;margin-left:10px;">
		<input type="file" name="${name!}" class="${class!"dupload"}" <#if verify!="">data-verify="${verify}"</#if> />
	</@widgetGroup>
</#macro>

<#--翼神啊!!!-->
<#macro widgetWrap id="" style="">
	<div class="widget_wrap"<#if "" != style> style="${style}"</#if><#if "" != id> id="${id}"</#if>>
		<#nested />
	</div>
</#macro>

<#--实现类似表格的布局-->
<#macro widgetGroup style="">
	<div class="widget_group" <#if "" != style>style="${style}"</#if>>
		<#nested />
	</div>
</#macro>