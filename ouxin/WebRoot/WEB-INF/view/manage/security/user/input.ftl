<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/security/user/list":"用户列表", "#":"${(bean?? && bean.id ??) ? string('修改', '新增')}"} />
					
			<@content>
				<@form prefix="${ctxPath}/manage/security/user/">
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">系统模块：</label>
						<div class="col-sm-9">
							<select name="bean.systemModuleId" id="bean.systemModuleId" required>
								<option value="">请选择</option>
								<#list systemModules as item>
									<option value="${item.id}" ${(bean.systemModuleId == item.id)?string('selected', '')}>${item.name}</option>
								</#list>
							</select>
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">角色：</label>
						<div class="col-sm-9">
							<select name="bean.roleId" id="bean.roleId" required>
								<option value="">请选择</option>
								<#list roles as item>
									<#if "1" != item.id?trim>
										<option value="${item.id}" ${(bean.roleId == item.id)?string('selected', '')}>${item.name}</option>
									</#if>
								</#list>
							</select>
						</div>
					</div>
					<div class="space-4"></div>
				
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">账号：</label>
						<div class="col-sm-9">
							<input name="bean.account" value="${bean.account}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))} placeholder="用户账号" title="账号（5~12个字符）" data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<#if bean??>
					<#else>
						<div class="form-group am-form-group">
							<label class="col-sm-2 control-label no-padding-right">密码：</label>
							<div class="col-sm-9">
								<input name="bean.password" value="${bean.password}" type="password" class="col-xs-3 tooltip-info" placeholder="密码" title="密码（6~12个字符）" data-rel="tooltip"  data-placement="right" minlength="6" maxlength="12" required />
							</div>
						</div>
						<div class="space-4"></div>
					</#if>
					
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
