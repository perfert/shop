<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="Apk列表" />			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/app/apk/"; pager, prefix>
					
					<@search prefix=prefix; prefix>
						<label>版本名称：<input type="text" name="query.versionName" value="${query.versionName}" aria-controls="sample-table-2" /></label>
					</@search>
					<@thead>
						<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:15%;">版本名称</th>
						<th class="sorting_disabled" style="width:10%;">版本号</th>
						<th class="sorting_disabled" style="width:40%;">更新说明</th>
						<th class="sorting_disabled" style="width:15%;">下载地址</th>
						<th class="sorting_disabled" style="width:10%;">操作</th>
					</@thead>
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:15%;">${item.versionName}</td>
							<td style="width:10%;">${item.version}</td>
							<td style="width:40%;">${item.detail}</td>
							<td style="width:15%;">${item.filePath}</td>
							<td class="op" style="width:10%;">
								<div class="hidden-sm hidden-xs action-buttons">
									<#if 0 lte item.state>
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