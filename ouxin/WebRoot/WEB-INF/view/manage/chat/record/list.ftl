<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/chat/member/list":"会员列表", "#":"账户详细"} />
			<@content>
				<div class="tabbable">
					<div class="tab-content">
						<div id="basInfo" class="tab-pane fade in active">
							<@dataTable pager=pager prefix="${ctxPath}/manage/chat/member/${id}/record/"; pager, prefix>
								<@thead>
									<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
									<th class="sorting_disabled" style="width:10%;">余额</th>
									<th class="sorting_disabled" style="width:15%;">收入</th>
									<th class="sorting_disabled" style="width:15%;">支出</th>
									<th class="sorting_disabled" style="width:30%;">描述</th>
									<th class="sorting_disabled" style="width:10%;">订单号</th>
									<th class="sorting_disabled" style="width:10%;"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>时间</th>
								</@thead>		
								<@tbody pager=pager prefix=prefix; item, pager, prefix>
									<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
										<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
										<td style="width:10%;">${item.nowCash?string(",##0.00")}</td>
										<td style="width:15%;">${item.income}</td>
										<td style="width:15%;">${item.expense}</td>
										<td style="width:30%;"><#if item.remark == "wealth.rp.platform">平台直充<#elseif item.remark == "wealth.rp.send">发送红包<#elseif item.remark == "wealth.rp.receive">抢红包<#elseif item.remark == "wealth.rp.return">红包返款</#if></td>
										<td style="width:10%;">${item.sn}</td>
										<td style="width:10%;">${item.modifyDate?string("yyyy-MM-dd HH:mm:ss")}</td>
									</tr>
								</@tbody>
							</@dataTable>						
						</div>
					</div>
				</div>
			</@content>
		</@container>
		
		<@end_list />
	</body>
</@mngTpl>