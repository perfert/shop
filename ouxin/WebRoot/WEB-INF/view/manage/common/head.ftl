<#--
	head by default page
	@author: MAS 
-->
<#macro head title="后台管理系统">
<@o.head title=title>
	
	<!-- bootstrap & fontawesome -->
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/bootstrap.css" />
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/font-awesome.css" />
	
	<!-- page specific plugin styles -->
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/jquery.gritter.css" />
	<#nested />

	<!-- text fonts -->
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/ace-fonts.css" />

	<!-- ace styles -->
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />

	<!--[if lte IE 9]>
		<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/ace-part2.css" class="ace-main-stylesheet" />
	<![endif]-->

	<!--[if lte IE 9]>
	  <link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/ace-ie.css" />
	<![endif]-->

	<!-- inline styles related to this page -->

	<!-- ace settings handler -->
	<script src="<@o.res/>/ui/ace/assets/js/ace-extra.js"></script>

	<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

	<!--[if lte IE 8]>
	<script src="<@o.res/>/ui/ace/assets/js/html5shiv.js"></script>
	<script src="<@o.res/>/ui/ace/assets/js/respond.js"></script>
	<![endif]-->
	
	<!-- amaze --> 
	<link rel="stylesheet" href="<@o.res/>/ui/amazeui/assets/css/amaze.min.css">
	<style type="text/css">
		.am-form-error label { color:#dd514c; }
		.am-form-success label { color:#5eb95e; }
		.am-field-error, .am-form-error .am-form-field, .am-form-error .ace-file-container
		{
			border-color: #dd514c!important;
		    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		}
		.am-field-valid, .am-form-success .am-form-field .am-form-success .ace-file-container
		{
			border-color: #5eb95e!important;
			-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
		}
		.table > thead > tr > th, .table > tbody > tr > th, .table > tfoot > tr > th, .table > thead > tr > td, .table > tbody > tr > td, .table > tfoot > tr > td
		{
			vertical-align: middle;
		}
	</style>
	
</@o.head>
</#macro>

<#--
	head by list page
	@author: MAS 
-->
<#macro head_list title="后台管理系统">
<@head title=title>
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/jquery-ui.css" />
	<#nested />
</@head>
</#macro>

<#--
	head by input page
	@author: MAS 
-->
<#macro head_input title="后台管理系统">
<@head title=title>
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/jquery-ui.custom.css" />
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/chosen.css" />
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/bootstrap-datepicker3.css" />
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/bootstrap-timepicker.css" />
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/daterangepicker.css" />
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/bootstrap-datetimepicker.css" />
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/colorpicker.css" />
	<link rel="stylesheet" href="<@o.res/>/ui/ace/assets/css/ace.onpage-help.css" />
	<#nested />
</@head>
</#macro>