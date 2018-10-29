<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/chat/member/list":"用户列表", "#":"重置密码"} />
					
			<@content>
					<form action="${ctxPath}/manage/chat/member/pwd" method="post" class="form-horizontal" role="form" enctype="multipart/form-data" data-am-validator>
							<input type="hidden" name="bean.id" value="${bean.id!}" />
							<input type="hidden" name="id" value="${bean.id!}" />
							<!-- #section:elements.form -->
							<div class="space-10"></div>
							<div class="space-4"></div>
				
							<div class="form-group am-form-group">
								<label class="col-sm-2 control-label no-padding-right">账号：</label>
								<div class="col-sm-9">
									<input name="bean.account" value="${bean.account}" type="text" class="col-xs-3 tooltip-info"  readonly placeholder="用户账号" title="账号（5~12个字符）" data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12" required />
								</div>
							</div>
							<div class="space-4"></div>
							<div class="form-group am-form-group">
								<label class="col-sm-2 control-label no-padding-right">密码：</label>
								<div class="col-sm-9">
									<input name="bean.password" value="" type="password" class="col-xs-3 tooltip-info" placeholder="密码" title="密码（6~12个字符）" data-rel="tooltip"  data-placement="right" minlength="6" maxlength="12" required />
								</div>
							</div>
							<div class="space-4"></div>
							<div class="clearfix form-actions">
								<div class="col-md-offset-3 col-md-9">
									<button ${(bean?? && ("-1" == bean.state?trim))?string('disabled', '')} class="btn btn-info ${(bean?? && ("-1" == bean.state?trim))?string('disabled', '')}" type="${(bean?? && ("-1" == bean.state?trim))?string('botton', 'submit')}">
										<i class="ace-icon fa fa-check bigger-110"></i>
										${(bean?? && bean.id ??) ? string('修 改', '新 增')}
									</button>
	
									&nbsp; &nbsp; &nbsp;
									<button class="btn" type="reset" disabled>
										<i class="ace-icon fa fa-undo bigger-110"></i>
										重 置
									</button>
								</div>
							</div>
					</form>
								
			</@content>
		</@container>
		
		<@end_input />
	</body>
</@mngTpl>
