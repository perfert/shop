<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/store/config/list":"压金配置列表", "#":"压金配置"} />
					
			<@content>
				<@form prefix="${ctxPath}/manage/store/config/">
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">名称：</label>
						<div class="col-sm-9">
							<input name="bean.name" value="${bean.name}" type="text" class="col-xs-3 tooltip-info" placeholder="名称" title="名称（至少1个字符）" data-rel="tooltip"  data-placement="right" minlength="2" required />
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">财富类型：</label>
						<div class="col-sm-9">
							<select name="bean.wealthTypeId" id="typeId" required>
								<option value="">请选择</option>
								<#list typeList as item>
									<option <#if item.id = bean.wealthTypeId>selected</#if> value="${item.id}">${item.name}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="space-4"></div>
							
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">押金数量：</label>
						<div class="col-sm-9">
							<input name="bean.cash" value="${bean.cash}" type="text" class="col-xs-3 tooltip-info" placeholder="押金数量" title="必填" data-rel="tooltip"  data-placement="right" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">排序：</label>
						<div class="col-sm-9">
							<input name="bean.priority" value="${(bean!).priority!100}" type="text" class="col-xs-3 tooltip-info" placeholder="排序优先级" title="排序优先级（0～9999）" data-rel="tooltip"  data-placement="right" min="0" max="9999" number required />
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
