<#include 'categoryTree.ftl' />

<script src="<@o.res/>/ui/ace/assets/js/fuelux/fuelux.tree.js"></script>
<script type="text/javascript">
	jQuery(function($){
		$( "#selectCategory" ).on('click', function() {
			$("#categoryTreeLayer").slideDown("fast");			
			$("body").bind("mousedown", onCategoryBodyDown);
		});
		
		function onCategoryBodyDown(event) {
			if (!(event.target.id == "menuCategoryBtn" || event.target.id == "selectCategory" || event.target.id == "categoryTreeLayer" || $(event.target).parents("#categoryTreeLayer").length>0)) {
				hideCategoryMenu();
			}
		}
		
		function hideCategoryMenu() {
			$("#categoryTreeLayer").fadeOut("fast");
			$("body").unbind("mousedown", onCategoryBodyDown);
			$("#selectCategory").blur();
		}
	
		var sampleData = initiateDemoData();//see below
		
		$('#categoryTree').ace_tree({
			dataSource: sampleData['categoryTreeDate'],
			multiSelect: false,
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
		 	$('#categoryId').attr('value', data['target'].id);
		 	hideCategoryMenu();
		}).on('deselected.fu.tree', function (event, data) {
			$('#selectCategory').attr('value', '');
		 	$('#categoryId').attr('value', '');
		 	hideCategoryMenu();
		});
							
							
		function initiateDemoData(){
			<@categoryTree treeNodes=cache_category_tree items=""/>
			var categoryTreeDate = function(options, callback){
				var $data = null
				if(!("text" in options) && !("type" in options)){
					$data = category_tree_data;//the root tree
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
	
			
			return {'categoryTreeDate' : categoryTreeDate}
		}
	});
</script>