<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/chat/friends/list":"用户列表", "#":"${(bean?? && bean.id ??) ? string('修改', '新增')}"} />
					
			<@content>
				<@form prefix="${ctxPath}/manage/chat/friends/">
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">本人账号：</label>
						<div class="col-sm-9">
							<input name="bean.memberAccount" value="${bean.memberAccount}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))} placeholder="用户账号" data-rel="tooltip"  data-placement="right" minlength="1" maxlength="15" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">好友账号：</label>
						<div class="col-sm-9">
							<input name="bean.friendAccount" value="${bean.friendAccount}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))} placeholder="用户账号"  data-rel="tooltip"  data-placement="right" minlength="1" maxlength="15" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">好友别名：</label>
						<div class="col-sm-9">
							<input name="bean.alias" value="${bean.alias}" type="text" class="col-xs-3 tooltip-info" placeholder="好友别名" title="好友别名" data-rel="tooltip"  data-placement="right" />
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
