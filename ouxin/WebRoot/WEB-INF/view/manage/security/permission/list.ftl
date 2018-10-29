<#include '../../common/tpl.ftl' />
<#include 'tree.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath!}/manage/security/${systemModuleId!}/role/list":"角色列表", "#":"权限配置"} />		
			<@content>
					
				<div class="widget-box widget-color-blue2">
					<div class="widget-header">
						<h4 class="widget-title lighter smaller">选择配置权限</h4>
					</div>
					
					<form action="${ctxPath!}/manage/security/${systemModuleId!}/role/${roleId!}/permission/create" method="post">
						<div class="widget-body">
							<div class="widget-main padding-8">
								<ul id="menuTree"></ul>
							</div>
						</div>
						
						<div class="clearfix form-actions">
							<div class="col-md-offset-3 col-md-9">
								<button type="submit" class="btn btn-info submit"><i class="ace-icon fa fa-check bigger-110"></i> 配 置 </button>
							</div>
							<div id="menuIds" style="display:none;">
								<#if permissions?? && permissions?size gt 0>
									<#list permissions as item>
										<input type="hidden" name="menuIds" value="${item!}" id="mId-${item!}" />
									</#list>
								</#if>
							</div>
						</div>
					</form>
				</div>
					
			</@content>
		</@container>
		
		<@end_list>
			<script type="text/javascript" src="<@o.res/>/ui/ace/assets/js/fuelux/fuelux.tree.js"></script>
			<script type="text/javascript">
				jQuery(function($){
					var sampleData = initiateDemoData();//see below
					
					$('#menuTree').ace_tree({
						dataSource: sampleData['menuTreeDate'],
						multiSelect: true,
						cacheItems: true,
						'open-icon' : 'ace-icon tree-minus',
						'close-icon' : 'ace-icon tree-plus',
						'itemSelect' : true,
						'folderSelect': false,
						'selected-icon' : 'ace-icon fa fa-check',
						'unselected-icon' : 'ace-icon fa fa-times',
						'data-selected' : '3',
						loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>'
					}).on('selected.fu.tree', function (event, data) {
						//console.log(data);
					 	//$('#selectCategory')[0].value = data['target'].text;
					 	$('#selectCategory').attr('value', data['target'].text);
					 	$('#menuIds').append('<input type="hidden" name="menuIds" value="' + data['target'].id + '" id="mId-' + data['target'].id + '" />');
					}).on('deselected.fu.tree', function (event, data) {
						$('#selectCategory').attr('value', '');
						$('#mId-' + data['target'].id).remove();
					}).tree("discloseAll");
										
					function initiateDemoData(){
						<@tree treeNodes=menuTree items=""/>
						var menuTreeDate = function(options, callback){
							var $data = null
							if(!("text" in options) && !("type" in options)){
								$data = tree_data;//the root tree
								callback({ data: $data });
								return;
							}
							else if("type" in options && options.type == "folder") {
								if("additionalParameters" in options && "children" in options.additionalParameters)
									$data = options.additionalParameters.children || {};
								else $data = {}//no data
							}
							
							if($data != null)//this setTimeout is only for mimicking some random delay
								setTimeout(function(){callback({ data: $data });} , parseInt(Math.random() * 500) + 200);
				
							//we have used static data here
							//but you can retrieve your data dynamically from a server using ajax call
							//checkout examples/treeview.html and examples/treeview.js for more info
						}
				
						
						return {'menuTreeDate' : menuTreeDate}
					}
				});
		</script>
		</@end_list>
	</body>
</@mngTpl>