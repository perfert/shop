<#--
	end by default page
	@author: MAS 
-->
<#macro end>
		<!-- basic scripts -->
	
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<@o.res/>/ui/ace/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		
		<!--[if IE]>
		<script type="text/javascript">
			 window.jQuery || document.write("<script src='<@o.res/>/ui/ace/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='<@o.res/>/ui/ace/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>
		<script src="<@o.res/>/ui/ace/assets/js/bootstrap.js"></script>
		
		<!-- page specific plugin scripts -->
		<script src="<@o.res/>/ui/ace/assets/js/jquery.gritter.js"></script>
		<#nested/>

		<!-- ace scripts -->
		<script src="<@o.res/>/ui/ace/assets/js/ace/elements.scroller.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/elements.colorpicker.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/elements.fileinput.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/elements.typeahead.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/elements.wysiwyg.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/elements.spinner.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/elements.treeview.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/elements.wizard.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/elements.aside.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/ace.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/ace.ajax-content.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/ace.touch-drag.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/ace.sidebar.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/ace.sidebar-scroll-1.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/ace.submenu-hover.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/ace.widget-box.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/ace.widget-on-reload.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/ace/ace.searchbox-autocomplete.js"></script>
		
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				<#if error??>
					$.gritter.add({
						//title: '错误！',
						text: "<i class='ace-icon fa ${('success' == error.status?trim)?string('fa-check', ('warn' == error.status?trim)?string('fa-warning', 'fa-times'))}'></i> ${error.message }",
						//image: '<@o.res/>/ui/ace/assets/avatars/avatar1.png', //in Ace demo ../assets will be replaced by correct assets path
						sticky: false,
						time: '${('success' == error.status?trim)?string('3000', '6000')}',
						class_name: '${('success' == error.status?trim)?string('gritter-success', ('warn' == error.status?trim)?string('gritter-warning', 'gritter-error'))} gritter-topper'
					});
				</#if>
			});
		</script>
</#macro>

<#--
	end by list page
	@author: MAS 
-->
<#macro end_list>
	<@end>
		<script src="<@o.res/>/ui/ace/assets/js/jquery-ui.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/jquery.ui.touch-punch.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/dataTables/jquery.dataTables.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/dataTables/jquery.dataTables.bootstrap.js"></script>
		
		<script src="<@o.res/>/ui/ace/assets/js/dataTables/extensions/select/dataTables.select.js"></script>
		<script type="text/javascript">
			jQuery(function($) {
				var active_class = 'selected';
				//select/deselect all rows according to table header checkbox
				$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					
					$('#dynamic-table').find('tbody > tr').each(function(){
						var row = this;
						var th_disabled = $(this).find('input[type=checkbox]')[0].disabled;
						if(!th_disabled)
						{ 
							if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
							else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
						}
					});
				});
				
				//select/deselect a row when the checkbox is checked/unchecked
				$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
					var $row = $(this).closest('tr');
					if(!this.disabled)
					{
						if(this.checked) $row.addClass(active_class);
						else $row.removeClass(active_class);
					}
				});
				
				$('#dynamic-table > tbody > tr > td').not('.op').on('click', function(){
					var $row = $(this).closest('tr');
					var cbx = $row.find('input[type=checkbox]')[0];
					if(cbx)
					{
						var th_checked = cbx.checked;
						var th_disabled = cbx.disabled;
						if(!th_disabled) 
						{
							if(th_checked) $row.removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
							else $row.addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
						}
					}
				});
			})
			
			$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
				_title: function(title) {
					var $title = this.options.title || '&nbsp;'
					if( ("title_html" in this.options) && this.options.title_html == true )
						title.html($title);
					else title.text($title);
				}
			}));
			
			$( ".delete-dialog" ).on('click', function(e) {
				
				e.preventDefault();
				var href=$(this).attr('link');
			
				$( "#dialog-confirm" ).removeClass('hide').dialog({
					resizable: false,
					width: '320',
					modal: true,
					title: "<div class='widget-header'><h4 class='smaller'><i class='ace-icon fa fa-exclamation-triangle red'></i> 警告！</h4></div>",
					title_html: true,
					buttons: [
						{
							html: "<i class='ace-icon fa fa-times bigger-110'></i>&nbsp; 取消",
							"class" : "btn btn-minier",
							click: function() {
								$( this ).dialog( "close" );
							}
						}
						,
						{
							html: "<i class='ace-icon fa fa-trash-o bigger-110'></i>&nbsp; 删除",
							"class" : "btn btn-danger btn-minier",
							click: function() {
								$( this ).dialog( "close" );
								window.location.href=href;
							}
						}
					]
				});
			});
		</script>
		<#nested/>
	</@end>
</#macro>

<#--
	end by input page
	@author: MAS 
-->
<#macro end_input>
	<@end>
		<!--[if lte IE 8]>
		  <script src="<@o.res/>/ui/ace/assets/js/excanvas.js"></script>
		<![endif]-->
		<script src="<@o.res/>/ui/ace/assets/js/jquery-ui.custom.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/jquery.ui.touch-punch.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/chosen.jquery.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/fuelux/fuelux.spinner.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/date-time/bootstrap-datepicker.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/date-time/bootstrap-timepicker.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/date-time/moment.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/date-time/daterangepicker.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/date-time/bootstrap-datetimepicker.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/bootstrap-colorpicker.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/jquery.knob.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/autosize.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/jquery.inputlimiter.1.3.1.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/jquery.maskedinput.js"></script>
		<script src="<@o.res/>/ui/ace/assets/js/bootstrap-tag.js"></script>
		
		<script type="text/javascript">
			jQuery(function($) {
				$('[data-rel=tooltip]').tooltip({container:'body'});
				$('[data-rel=popover]').popover({container:'body'});
				
				jQuery(function($) {
					$('.simple-input-file').ace_file_input({
						no_file:'No File ...',
						btn_choose:'Choose',
						btn_change:'Change',
						droppable:false,
						onchange:null,
						thumbnail:false //| true | large
						//whitelist:'gif|png|jpg|jpeg'
						//blacklist:'exe|php'
						//onchange:''
						//
					});
				});
			});
		</script>
		<#nested/>
		
		<!-- amaze -->
		<script src="<@o.res/>/ui/amazeui/assets/js/amazeui.min.js"></script>
	</@end>
</#macro>