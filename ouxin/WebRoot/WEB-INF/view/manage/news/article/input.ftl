<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/chat/apply/list":"好友申请列表", "#":"${(bean?? && bean.id ??) ? string('修改', '新增')}"} />
					
			<@content>
				<@form prefix="${ctxPath}/manage/chat/apply/">
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">本人账号：</label>
						<div class="col-sm-9">
							<input name="bean.applyAccount" value="${bean.applyAccount}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))} placeholder="本人账号" data-rel="tooltip"  data-placement="right" minlength="1" maxlength="15" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">好友账号：</label>
						<div class="col-sm-9">
							<input name="bean.receiveAccount" value="${bean.receiveAccount}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))} placeholder="好友账号"  data-rel="tooltip"  data-placement="right" minlength="1" maxlength="15" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">请求消息：</label>
						<div class="col-sm-9">
							<input name="bean.msg" value="${bean.msg}" type="text" class="col-xs-3 tooltip-info" placeholder="请求消息" title="请求消息" data-rel="tooltip"  data-placement="right" />
						</div>
					</div>
					<div class="space-4"></div>
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">申请状态：</label>
						<div class="col-sm-9">
							<select name="bean.type" id="bean.type" required>
								<option value="">请选择</option>
								<option value="0" ${(bean.type == 0)?string('selected', '')}>直接加好友</option>
								<option value="1" ${(bean.type == 1)?string('selected', '')}>请求加好友</option>
								<option value="2" ${(bean.type == 2)?string('selected', '')}>同意加好友</option>
								<option value="3" ${(bean.type == 3)?string('selected', '')}>拒绝加好友</option>
							</select>
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
