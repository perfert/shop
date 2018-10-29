<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/news/article/list":"朋友圈列表", "#":"查看图片"} />
			<@content>
				<@form prefix="${ctxPath}/manage/chat/apply/">
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">说说内容：</label>
						<div class="col-sm-9">
							<textarea style='width:420px;height:80px;'>${bean.content}</textarea>
						</div>
					</div>
					<div class="space-4"></div>
					
					<#if images?? && 0 lt images!?size>
							<div class="form-group am-form-group">
								<label class="col-sm-2 control-label no-padding-right">图片：</label>
								<div class="col-sm-9">
								<#list images as item>
									<img src="${ctxPath}/${bean.prefix}${item}" height="60" style="border-radius:5px;border:1px solid #bbb" />
								</#list>
								</div>
							</div>
					</#if>
				</@form>
			</@content>
		</@container>
		
		<@end_input />
	</body>
</@mngTpl>
