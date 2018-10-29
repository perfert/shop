<#macro fileUploadWrap label="" ajax="">
	<strong>图片上传：</strong>
	<@widgetGroup>
	<div class="upload_frame" <#if "" != ajax>ajax="${ajax}"</#if>>
		<#nested />
	</div>
	</@widgetGroup>

</#macro>

<#macro uploadItem imgSrc="" name="" param="" title="">
	<span class="Ibox">
		<div class="upload img_fixed">
			<#if "" != imgSrc><img class="upload_img" src="${imgSrc}" /></#if>
			<label class="upload_label">你正在上传的是：</label>
			<span class="upload_text"></span>

			<p class="upload_title" <#if "" != imgSrc>style="display:block;"</#if>>
				<span <#if "" == title>style="display:none;"</#if>>${title}</span>
				<input type="text" <#if "" != title>style="display:none;"</#if> value="亲！给个标题吧！" name="title" />
			</p>

			<input class="upload_file" type="file" name="${name}" value="" param="${param}" />
			<a href="javascript:;" class="upload_btn upload_delete"><span><cite></cite></span></a>
			<a href="javascript:;" class="upload_btn upload_edit"><span><cite></cite></span></a>
			<a href="javascript:;" class="upload_add" <#if "" != imgSrc>style="display:none;"</#if>></a>
		</div>

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