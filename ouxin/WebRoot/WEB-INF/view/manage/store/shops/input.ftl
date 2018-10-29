<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/store/shops/list":"店铺列表", "#":"店铺认证"} />
					
			<@content>
					<form action="${ctxPath}/manage/store/shops/verify/${certificate.shopsId!}" method="post" class="form-horizontal" role="form" enctype="multipart/form-data" data-am-validator>
							<input type="hidden" name="verify" id="verify" />
							<!-- #section:elements.form -->
							<div class="space-10"></div>
							<div class="space-4"></div>
				
							<div class="form-group am-form-group">
								<label class="col-sm-2 control-label no-padding-right">公司名称：</label>
								<div class="col-sm-9">
									<input name="bean.name" value="${certificate.name}" type="text" class="col-xs-3 tooltip-info"  readonly placeholder="用户账号" title="账号（5~12个字符）" data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12" required />
								</div>
							</div>
    
    						<div class="form-group am-form-group">
								<label class="col-sm-2 control-label no-padding-right">法人：</label>
								<div class="col-sm-9">
									<input name="bean.legalPerson" value="${certificate.legalPerson}" type="text" class="col-xs-3 tooltip-info"  readonly placeholder="用户账号" title="账号（5~12个字符）" data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12" required />
								</div>
							</div>
							
							<div class="form-group am-form-group">
								<label class="col-sm-2 control-label no-padding-right">法人身份证号码：</label>
								<div class="col-sm-9">
									<input name="bean.cardNo" value="${certificate.cardNo}" type="text" class="col-xs-3 tooltip-info"  readonly placeholder="用户账号" title="账号（5~12个字符）" data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12" required />
								</div>
							</div>
    
				
							<#if certificate.cardFacePath??>
								<div class="form-group am-form-group">
									<label class="col-sm-2 control-label no-padding-right">身份证正面：</label>
									<div class="col-sm-9">
										<img src="${ctxPath}/${certificate.cardFacePath}" height="200"  style="border-radius:5px;border:1px solid #bbb" />
									</div>
								</div>
							</#if>
							
							<#if certificate.cardReversePath??>
								<div class="form-group am-form-group">
									<label class="col-sm-2 control-label no-padding-right">身份证反面：</label>
									<div class="col-sm-9">
										<img src="${ctxPath}/${certificate.cardReversePath}" height="200"  style="border-radius:5px;border:1px solid #bbb" />
									</div>
								</div>
							</#if>
							
							<#if certificate.businessLicensePath??>
								<div class="form-group am-form-group">
									<label class="col-sm-2 control-label no-padding-right">营业执照：</label>
									<div class="col-sm-9">
										<img src="${ctxPath}/${certificate.businessLicensePath}" height="300"  style="border-radius:5px;border:1px solid #bbb" />
									</div>
								</div>
							</#if>
							
							<div class="form-group am-form-group">
								<label class="col-sm-2 control-label no-padding-right">不通过原因：</label>
								<div class="col-sm-9">
									<input name="reason" value="" type="text" class="col-xs-3 tooltip-info" placeholder="不通过原因"  data-rel="tooltip"  data-placement="right" />
								</div>
							</div>
							
							<div class="space-4"></div>
							<div class="clearfix form-actions">
								<div class="col-md-offset-3 col-md-9">
									<button class="btn btn-info submit pass">
										<i class="ace-icon fa fa-check bigger-110"></i> 
										通过
									</button>
									&nbsp; &nbsp; &nbsp;
									<button class="btn nopass">
										<i class="ace-icon fa fa-undo bigger-110"></i>
										不通过
									</button>
								</div>
							</div>
					</form>
								
			</@content>
		</@container>
		
		<@end_input />
		
		<script type="text/javascript">
				jQuery(function($){
					$('.pass').click(function(){
						$('#verify').val(1);
					});
					$('.nopass').click(function(){
						$('#verify').val(2);
					});
				});
		</script>
	</body>
</@mngTpl>
