<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="广告位列表" />			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/app/position/"; pager, prefix>
					
					<@search prefix=prefix; prefix>
						<label>名称：<input type="text" name="query.name" value="${query.name}" aria-controls="sample-table-2" /></label>
					</@search>
					<@thead>
						<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:35%;">名称</th>
						<th class="sorting_disabled" style="width:15%;">标识</th>
						<th class="sorting_disabled" style="width:10%;">宽</th>
						<th class="sorting_disabled" style="width:10%;">高</th>
						<th class="sorting_disabled" style="width:10%;">播图数量</th>
						<th class="sorting_disabled" style="width:10%;">操作</th>
					</@thead>
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:35%;">${item.name}</td>
							<td style="width:15%;">${item.sign}</td>
							<td style="width:10%;">${item.width}</td>
							<td style="width:10%;">${item.height}</td>
							<td style="width:10%;">${item.maxAd}</td>
							<td class="op" style="width:10%;">
								<div class="hidden-sm hidden-xs action-buttons">
									<#if 0 lte item.state>
									<a class="purple" href="${ctxPath}/manage/app/${item.id}/ad/list"><i class="ace-icon fa fa-list bigger-130"></i></a>
									<a class="green" href="${prefix}${item.id}"> <i class="ace-icon fa fa-pencil bigger-130"></i></a>
									<a class="delete-dialog red" href="javascript:void(0);" link="${prefix}del/${item.id}" ><i class="ace-icon fa fa-trash-o bigger-130"></i></a>
									</#if>
								</div>
							</td>
						</tr>
					</@tbody>
					
				</@dataTable>
			</@content>
		</@container>
		
		<@end_list />
	</body>
</@mngTpl>