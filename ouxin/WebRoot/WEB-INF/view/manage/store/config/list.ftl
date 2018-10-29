<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="押金配置列表" />			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/store/config/" formId="memberListForm"; pager, prefix>
				
					<@search prefix=prefix; prefix>
						<label>名称：<input type="text" name="query.name" value="${query.name}" aria-controls="sample-table-2" /></label>
					</@search>
					<@thead>
						<th class="sorting_disabled" style="width:5%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:30%;">配置名称</th>
						<th class="sorting_disabled" style="width:20%;">押金数量</th>
						<th class="sorting_disabled" style="width:10%;">支付类型</th>
						<th class="sorting_disabled" style="width:10%;">状态</th>
						<th class="sorting_disabled" style="width:20%;"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>创建时间</th>
						<th class="sorting_disabled" style="width:5%;">操作</th>
					</@thead>
					
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:5%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:30%;">${item.name}</td>
							<td style="width:20%;">${item.cash}</td>
							<td style="width:10%;">${item.typeName}</td>
							<td style="width:10%;">
								<span class="ace-icon fa ${(1==item.state)?string('green', ((0==item.state)?string('light-orange', 'light-grey')))}"><i class="${(1==item.state)?string('fa-check', ((0==item.state)?string('fa-ban', 'fa-times')))}"></i></span>
							</td>
							<td style="width:20%;">${item.createDate?string("yyyy-MM-dd HH:mm:ss")}</td>
							<@operator item=item prefix=prefix width="10"; item, prefix />
						</tr>
					</@tbody>
					<div id="recharge-confirm" class="hide">
						<div class="alert alert-info bigger-110" id="recharge-info">
							
						</div>

						<div class="space-6"></div>

						<p class="bigger-110 bolder center grey">
							<i class="ace-icon fa fa-hand-o-right blue bigger-120"></i>
							确定充值信息无误吗？
						</p>
					</div>
				</@dataTable>
			</@content>
		</@container>
		
		<@end_list>
			<script src="<@o.res/>/ui/ace/assets/js/jquery-ui.js"></script>
			<script src="<@o.res/>/ui/ace/assets/js/jquery.ui.touch-punch.js"></script>
			<script src="<@o.res/>/ui/ace/assets/js/bootbox.js"></script>
			<script type="text/javascript">
			
				$(".recharge").on('click', function() {
					var id=$(this).attr('val');
					var name=$(this).attr('name');
					bootbox.prompt("请输入充值Ts数", function(result) {
						if (result && "" != $.trim(result) && !isNaN(result)) {
							if( 0 >= parseInt(result) || 10000 < parseInt(result) )
							{
								$.gritter.add({
									//title: '错误！',
									text: "<i class='ace-icon fa fa-times'></i> 充值数值必须大于0或小于10000",
									sticky: false,
									time:  '6000',
									class_name: 'gritter-error gritter-topper'
								});
							} else {
								$('#recharge-info').text("对会员：" + name + " 进行钱包充值：" + result + " ？");
								$( "#recharge-confirm" ).removeClass('hide').dialog({
									resizable: false,
									width: '320',
									modal: true,
									title: '',
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
											html: "<i class='ace-icon fa fa-trash-o bigger-110'></i>&nbsp; 充值",
											"class" : "btn btn-danger btn-minier",
											click: function() {
												$( this ).dialog( "close" );
												$.ajax({
													type: "POST",
													url:"${ctxPath!}/manage/chat/member/recharge",
													data: {'id':id, 'cash':result },
													success:function(result)
													{
														var data = $.parseJSON(result);
														if( '200' == data.statusCode ) {
															$.gritter.add({
																text: "<i class='ace-icon fa success'></i> 充值成功！",
																sticky: false,
																time:  '3000',
																class_name: 'gritter-success gritter-topper'
															});
															setTimeout(function () {
																$('#memberListForm').submit();
															}, 1000);  
														} else {
															$.gritter.add({
																text: "<i class='ace-icon fa fa-times'></i> " + data.msg,
																sticky: false,
																time:  '6000',
																class_name: 'gritter-error gritter-topper'
															});
														}
													}
												});
											}
										}
									]
								});
							}
						} else {
							if("" != $.trim(result)) {
								$.gritter.add({
									text: "<i class='ace-icon fa fa-times'></i> 请输入正确的充值数值",
									sticky: false,
									time:  '6000',
									class_name: 'gritter-error gritter-topper'
								});
							}
						}
					});
				});
				/*$(function() {
				  $('.recharge').on('click', function() {
				    $('#my-prompt').modal({
				      relatedTarget: this,
				      onConfirm: function(e) {
				        alert('你输入的是：' + e.data || '');
				      },
				      onCancel: function(e) {
				        alert('不想说!');
				      }
				    });
				  });
				});*/
			</script>
		</@end_list>
	</body>
</@mngTpl>