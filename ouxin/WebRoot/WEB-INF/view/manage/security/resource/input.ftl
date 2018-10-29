<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath!}/manage/security/${systemModuleId}/resource/0/list":"后台菜单列表", "#":"${(bean?? && bean.id ??) ? string('修改', '新增')}"} />
					
			<@content>
				<@form prefix="${ctxPath!}/manage/security/${systemModuleId}/resource/${parentId!0}/">
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">上级：</label>
						<div class="col-sm-9">
							<input name="parent.name" value="${(parent.name)!'无'}" type="text" class="col-xs-3" disabled />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">名称：</label>
						<div class="col-sm-9">
							<input name="bean.name" value="${bean.name}" type="text" class="col-xs-3 tooltip-info" placeholder="菜单名称" title="菜单名称（至少2个字符）" data-rel="tooltip"  data-placement="right" minlength="2" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">访问路径：</label>
						<div class="col-sm-9">
							<input name="bean.uri" value="${bean.uri}" type="text" class="col-xs-3 tooltip-info" placeholder="访问路径" title="访问路径" data-rel="tooltip"  data-placement="right" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">图标：</label>
						<div class="col-sm-9">
							<input name="bean.icon" value="${bean.icon}" type="text" class="col-xs-3 tooltip-info" placeholder="图标icon" title="图标icon" data-rel="tooltip"  data-placement="right" />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">是否菜单：</label>
						<div class="radio">
							<label>
								<input name="bean.isMenu" type="radio" class="ace input-lg" value="1" ${(("" == bean!?trim) || bean.isMenu) ? string('checked', '')} required/>
								<span class="lbl"> 是 </span>
							</label>
							<label style="margin-left:20px;">
								<input name="bean.isMenu" type="radio" class="ace input-lg" value="0" ${(bean?? && (! bean.isMenu)) ? string('checked', '')}/>
								<span class="lbl"> 否 </span>
							</label>
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
