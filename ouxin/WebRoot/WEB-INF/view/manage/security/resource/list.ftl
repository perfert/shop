<#include '../../common/tpl.ftl' />
<#include 'tree.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="后台菜单列表" />			
			<@content>
					
				<div class="row">
					<div class="col-xs-12">
						<div class="row">
								<div class="col-sm-8" style="width:auto;">
									<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
										<a class="blue" href="${ctxPath!}/manage/security/${systemModuleId}/resource/${parentId}/create"> <i class="fa fa-plus-circle bigger-280"></i></a>
									</div>
								</div>
							</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-sm-12">
						<div class="dd-draghandle" style="max-width:100%;">	
							<ol class="dd-list">	
								<li class="dd-item dd2-item" data-id="13">
									<div class="dd-handle dd2-handle purple">
										图标
									</div>
									<div class="dd2-content purple">
										<span>名称</span>
										<div class="pull-right action-buttons">
											<span style="float:left; width:300px;">访问路径</span>
											<span style="float:left; text-align:center; width:40px;">菜单</span>
											<span style="float:left; text-align:center; width:40px;">左值</span>
											<span style="float:left; text-align:center; width:40px;">右值</span>
											<span style="float:left; text-align:center; width:40px;">状态</span>
											<span style="float:left; text-align:center; width:100px;">操作</span>
										</div>
									</div>
								</li>
							</ol>
						</div>
					
						<div class="dd dd-draghandle" style="max-width:100%;">
							<#if menuTree?? && 0 lt menuTree?size>
								<@tree items=menuTree prefix="${ctxPath!}/manage/security/${systemModuleId}/resource/" />
							<#else>					
								<ol class="dd-list">	
									<li class="dd-item dd2-item" data-id="13">
										<div class="dd-handle dd2-handle">
											<i class="normal-icon ace-icon fa fa-exclamation-triangle red bigger-130"></i>
											<i class="drag-icon ace-icon fa fa-arrows bigger-125"></i>
										</div>
										<div class="dd2-content red">无数据！</div>
									</li>
								</ol>
							</#if>
						</div>
					</div>
				</div>
				
				<div id="dialog-confirm" class="hide">
					<div class="alert alert-info bigger-110">
						此操作将 <i style="color:Red;">附带删除其所有子菜单数据。</i><br/><br/><i class="red">数据删除后，将无法恢复！</i>
					</div>

					<div class="space-6"></div>

					<p class="bigger-110 bolder center grey">
						<i class="ace-icon fa fa-hand-o-right blue bigger-120"></i>
						确定要删除吗？
					</p>
				</div>
					
			</@content>
		</@container>
		
		<@end_list>
			<script src="<@o.res/>/ui/ace/assets/js/jquery.nestable.js"></script>
			<script type="text/javascript">
				jQuery(function($){
				
					$('.dd').nestable();
					var original = JSON.stringify( $('.dd').nestable('serialize') );
					
					$('.dd-handle a').on('mousedown', function(e){
						console.log('mousedown');
						e.stopPropagation();false;
					});
					
					$('[data-rel="tooltip"]').tooltip();
					
					$('.dd').nestable().on('change', function(){ 
						var r = $('.dd').nestable('serialize'); 
						if(JSON.stringify(r) != original)
						{
							$.gritter.add({
								//title: '错误！',
								text: "<i class='ace-icon fa fa-lock'></i> 老大，不要试拉，该功能暂未开放，放过我先吧！！！",
								//image: '<@o.res/>/ui/ace/assets/avatars/avatar1.png', //in Ace demo ../assets will be replaced by correct assets path
								sticky: false,
								time: '6000',
								class_name: 'gritter-success gritter-light gritter-topper'
							});
						}
	                });
					//$('#nestable').nestable().on('change', updateOutput);
				});
		</script>
		</@end_list>
	</body>
</@mngTpl>