<#include 'common/tpl.ftl' />

<@mngTpl>
	
	<@head />

	<body class="no-skin">
	
		<@container>
			<@breadcrumbs items="" />
			
			<@content>
				<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->

								<!-- #section:pages/error -->
								<div class="error-container">
									<div class="well">
										<h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="ace-icon fa fa-random"></i>
												500
											</span>
											无操作权限！
										</h1>

										<hr>
										<h3 class="lighter smaller">
											请联系管理员
											<i class="ace-icon fa fa-wrench icon-animated-wrench bigger-125"></i>
										</h3>

									</div>
								</div>

								<!-- /section:pages/error -->

								<!-- PAGE CONTENT ENDS -->
							</div>
			</@content>
		</@container>
		
		<@end />
	</body>
</@mngTpl>