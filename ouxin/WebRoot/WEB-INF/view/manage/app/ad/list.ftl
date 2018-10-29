<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
		
			<@breadcrumbs items={"${ctxPath}/manage/app/position/list":"广告位列表", "#":"广告列表"} />	
			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/app/${position}/ad/"; pager, prefix>
					<@search prefix=prefix; prefix>
						<label>标题：<input type="text" name="query.title" value="${query.title}" aria-controls="sample-table-2" /></label>
					</@search>
					<@thead>
						<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:25%;">广告标题</th>
						<th class="sorting_disabled" style="width:15%;">广告位</th>
						<th class="sorting_disabled" style="width:15%;">类型</th>
						<th class="sorting_disabled" style="width:15%;">访问URL</th>
						<th class="sorting_disabled" style="width:10%;">状态</th>
						<th class="sorting_disabled" style="width:10%;">操作</th>
					</@thead>
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:25%;">${item.title}</td>
							<td style="width:15%;">${item.positionName}</td>
							<td style="width:15%;"><#if item.type != 0>图片<#else>文本</#if></td>
							<td style="width:15%;">${item.url}</td>
							<td style="width:10%;">
								<span class="ace-icon fa ${(1==item.state)?string('green', ((0==item.state)?string('light-orange', 'light-grey')))}"><i class="${(1==item.state)?string('fa-check', ((0==item.state)?string('fa-ban', 'fa-times')))}"></i></span>
							</td>
							<td class="op" style="width:10%;">
								<div class="hidden-sm hidden-xs action-buttons">
									<#if 0 lte item.state>
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