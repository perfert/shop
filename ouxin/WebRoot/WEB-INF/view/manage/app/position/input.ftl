<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/app/position/list":"广告位列表", "#":"${(bean?? && bean.id ??) ? string('修改', '新增')}"} />
					
			<@content>
				<@form prefix="${ctxPath}/manage/app/position/">
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">名称：</label>
						<div class="col-sm-9">
							<input name="bean.name" value="${bean.name}" type="text" class="col-xs-3 tooltip-info" placeholder="名称"  data-rel="tooltip"  data-placement="right" minlength="1" maxlength="15" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">广告位高：</label>
						<div class="col-sm-9">
							<input name="bean.height" value="${bean.height}" type="text" class="col-xs-3 tooltip-info" placeholder="广告位高" title="单位:相素" data-rel="tooltip"  min="0" max="10000" required data-placement="right" />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">广告位宽：</label>
						<div class="col-sm-9">
							<input name="bean.width" value="${bean.width}" type="text" class="col-xs-3 tooltip-info" placeholder="广告位宽" title="单位:相素" data-rel="tooltip"  min="0" max="10000" required data-placement="right" />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">规则标志：</label>
						<div class="col-sm-9">
							<input name="bean.sign" value="${bean.sign}" type="text" class="col-xs-3 tooltip-info" placeholder="规则标志" title="规则标志" data-rel="tooltip"  required data-placement="right" />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">最大轮播图数量：</label>
						<div class="col-sm-9">
							<input name="bean.maxAd" value="${bean.maxAd}" type="text" class="col-xs-3 tooltip-info" placeholder="最大轮播图数量" title="最少为1" min="1" max="10000" required data-rel="tooltip"  data-placement="right" />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">说明：</label>
						<div class="col-sm-9">
							<input name="bean.detail" value="${bean.detail}" type="text" class="col-xs-3 tooltip-info" placeholder="说明" title="说明" data-rel="tooltip"  data-placement="right" />
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
