<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="红包配置表" />			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/wallet/rp_config/"; pager, prefix>
				
					<@thead>
						<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:30%;">名称</th>
						<th class="sorting_disabled" style="width:15%;">最低值</th>
						<th class="sorting_disabled" style="width:15%;">最高值</th>
						<th class="sorting_disabled" style="width:10%;">优先级</th>
						<th class="sorting_disabled" style="width:10%;">状态</th>
						<th class="sorting_disabled" style="width:10%;">操作</th>
					</@thead>
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:30%;">${item.name}</td>
							<td style="width:15%;">${item.min}</td>
							<td style="width:15%;">${item.max}</td>
							<td style="width:10%;">${item.priority}</td>
							<td style="width:10%;">
								<span class="ace-icon fa ${(1==item.state)?string('green', ((0==item.state)?string('light-orange', 'light-grey')))}"><i class="${(1==item.state)?string('fa-check', ((0==item.state)?string('fa-ban', 'fa-times')))}"></i></span>
							</td>
							<@operator item=item prefix=prefix width="10"; item, prefix />
						</tr>
					</@tbody>
					
				</@dataTable>
			</@content>
		</@container>
		
		<@end_list />
	</body>
</@mngTpl>