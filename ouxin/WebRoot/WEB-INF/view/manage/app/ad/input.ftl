<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/app/${position}/ad/list":"广告列表", "#":"${(bean?? && bean.id ??) ? string('修改', '新增')}"} />
			<@content>
				<@form prefix="${ctxPath}/manage/app/${position}/ad/">
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">广告标题：</label>
						<div class="col-sm-9">
							<input name="bean.title" value="${bean.title}" type="text" class="col-xs-3 tooltip-info" placeholder="广告标题"  data-rel="tooltip"  data-placement="right" minlength="1" maxlength="15" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">类型：</label>
						<div class="col-sm-9">
							<select name="bean.type" id="bean.type" required>
								<option value="">请选择</option>
								<option value="0" <#if bean.type?? && bean.type  == 0>selected</#if>>文本</option>
								<option value="1" <#if bean.type?? && bean.type  == 1>selected</#if>>图片</option>
							</select>
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">广告位：</label>
						<div class="col-sm-9">
							<select name="bean.positionId" id="bean.positionId" required>
								<option value="">请选择</option>
								<#list adPositionList as position>
									<option value="${position.id}" <#if bean.positionId?? && bean.positionId == position.id>selected</#if>>
									${position.name}
									</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">显示图：</label>
						<div class="col-sm-9" style="width:20%;">
							<input name="file" type="file" class="simple-input-file" <#if !bean??>required</#if>/>
							<#if bean??><input name="bean.filePath" type="hidden" value="${bean.filePath}"/></#if>
						</div>
						<#if bean.filePath??><a class="green" target="_blank" href="${ctxPath}/${bean.filePath}">查看</a></#if>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">访问URL：</label>
						<div class="col-sm-9">
							<input name="bean.url" value="${bean.url}" type="text" class="col-xs-3 tooltip-info" placeholder="访问URL"  data-rel="tooltip"  required data-placement="right" />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">状态：</label>
						<div class="radio">
							<#if bean?? && ("-1" == bean.state?trim)>
								<label class="light-grey">
									<input name="bean.state" type="radio" class="ace input-lg" value="-1" checked required />
									<span class="lbl"> 已删除 </span>
								</label>
							<#else>
								<label>
									<input name="bean.state" type="radio" class="ace input-lg" value="1" ${(("" == bean!?trim) || ("" == bean.state?trim) || 1 == bean.state) ? string('checked', '')} required/>
									<span class="lbl"> 开 启 </span>
								</label>
								<label style="margin-left:20px;">
									<input name="bean.state" type="radio" class="ace input-lg" value="0" ${(bean?? && 0 == bean.state) ? string('checked', '')}/>
									<span class="lbl"> 关 闭 </span>
								</label>
							</#if>
						</div>
					</div>
				</@form>
			</@content>
		</@container>
		
		<@end_input />
	</body>
</@mngTpl>
