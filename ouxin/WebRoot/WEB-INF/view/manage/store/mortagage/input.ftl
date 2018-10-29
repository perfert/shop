<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/store/shops/list":"店铺列表", "#":"查看押金"} />
			<@content>
					<form action="#" method="post" class="form-horizontal" role="form" enctype="multipart/form-data" data-am-validator>
							<!-- #section:elements.form -->
							<div class="space-10"></div>
							<div class="space-4"></div>
				
							<div class="form-group am-form-group">
								<label class="col-sm-2 control-label no-padding-right">支付类型：</label>
								<div class="col-sm-9">
									<input name="bean.typeName" value="${bean.typeName}" type="text" class="col-xs-3 tooltip-info"  readonly data-rel="tooltip"  data-placement="right"  />
								</div>
							</div>
							<div class="space-4"></div>
							<div class="form-group am-form-group">
								<label class="col-sm-2 control-label no-padding-right">支付金额：</label>
								<div class="col-sm-9">
								<input name="bean.cash" value="${bean.cash}" type="text" class="col-xs-3 tooltip-info"  readonly data-rel="tooltip"  data-placement="right"  />
								</div>
							</div>
							<div class="space-4"></div>
					</form>
								
			</@content>
		</@container>
		
		<@end_input />
	</body>
</@mngTpl>
