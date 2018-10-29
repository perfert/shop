<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/app/apk/list":"Apk列表", "#":"${(bean?? && bean.id ??) ? string('修改', '新增')}"} />
					
			<@content>
				<@form prefix="${ctxPath}/manage/app/apk/">
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">版本名称：</label>
						<div class="col-sm-9">
							<input name="bean.versionName" value="${bean.versionName}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))} placeholder="版本名称" data-rel="tooltip"  data-placement="right" minlength="1" maxlength="30" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">版本号：</label>
						<div class="col-sm-9">
							<input name="bean.version" value="${bean.version}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))} placeholder="版本号"   title="需大于当前版本号:${nowVersion}" data-rel="tooltip"  data-placement="right" minlength="1" maxlength="30" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">版本介绍：</label>
						<div class="col-sm-9">
							<textarea name="bean.detail" maxlength="100" class="col-xs-3 tooltip-info" placeholder="版本介绍" title="请用冒号;作为分隔" 
							data-rel="tooltip"  data-placement="right" required>${bean.detail}</textarea>
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">上传：</label>
						<div class="col-sm-9" style="width:20%;">
							<input name="file" type="file" class="simple-input-file" required/>
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
