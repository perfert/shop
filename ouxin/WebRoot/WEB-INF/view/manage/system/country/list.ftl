<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="国家列表" />			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/system/country/"; pager, prefix>
				
					<@search prefix=prefix; prefix>
						<label>名称：<input type="text" name="query.name" value="${query.name}" aria-controls="sample-table-2" /></label>
					</@search>
					
					<@thead>
						<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:25%;">中文名称</th>
						<th class="sorting_disabled" style="width:15%;">英文名称</th>
						<th class="sorting_disabled" style="width:10%;">国际码</th>
						<th class="sorting_disabled" style="width:5%;">优先级</th>
						<th class="sorting_disabled" style="width:20%;"><i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>更新时间</th>
						<th class="sorting_disabled" style="width:5%;">状态</th>
						<th class="sorting_disabled" style="width:10%;">操作</th>
					</@thead>
					
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:25%;">${item.name}</td>
							<td style="width:15%;">${item.cnName}</td>
							<td style="width:10%;">${item.code}</td>
							<td style="width:5%;">${item.priority}</td>
							<td style="width:20%;">${item.modifyDate?string("yyyy-MM-dd HH:mm:ss")}</td>
							<td style="width:5%;">
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