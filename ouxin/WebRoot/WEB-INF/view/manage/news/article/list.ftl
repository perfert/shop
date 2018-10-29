<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="朋友圈列表" />			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/news/article/"; pager, prefix>
					
					<div class="row">
						<div class="col-sm-8" style="width:auto;">
							<div class="dataTables_filter">
								<label>用户账号：<input type="text" name="query.account" value="${query.account}" aria-controls="sample-table-2" /></label>
								<label><button class="btn btn-purple btn-sm" type="submit"><i class="ace-icon fa fa-search bigger-110"></i>搜 索</button></label>
								<label><button class="btn btn-light btn-sm" type="reset"><i class="ace-icon fa fa-undo bigger-110"></i>清 空</button></label>
							</div>
						</div>
					</div>
					<@thead>
						<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:10%;">发表账号</th>
						<th class="sorting_disabled" style="width:40%;">内容</th>
						<th class="sorting_disabled" style="width:20%;">图片</th>
						<th class="sorting_disabled" style="width:10%;">地理位置</th>
						<th class="sorting_disabled" style="width:10%;">操作</th>
					</@thead>
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:10%;">${item.account}</td>
							<td style="width:40%;">${item.content}</td>
							<td style="width:20%;"><a class="green" href="${ctxPath}/manage/news/article/${item.id}/content">查看</a></td>
							<td style="width:10%;">${item.location}</td>
							<td class="op" style="width:10%;">
								<div class="hidden-sm hidden-xs action-buttons">
									<#if 0 lte item.state>
									<#if 0 lte item.state>
									<a class="green" href="${ctxPath}/manage/news/${item.id}/comment/list"><i class="ace-icon fa fa-comments bigger-130"></i></a>
									<a class="purple" href="${ctxPath}/manage/news/${item.id}/good/list"><i class="ace-icon fa fa-heart bigger-130"></i></a>
									<a class="delete-dialog red" href="javascript:void(0);" link="${prefix}del/${item.id}" ><i class="ace-icon fa fa-trash-o bigger-130"></i></a>
									</#if>
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