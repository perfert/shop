<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="群聊成员列表" />			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/chat/${group}/gmembers/"; pager, prefix>
					<div class="row">
						<div class="col-sm-8" style="width:auto;">
							<div class="dataTables_filter">
								<label>会员账号：<input type="text" name="query.memberAccount" value="${query.memberAccount}" aria-controls="sample-table-2" /></label>
								<label><button class="btn btn-purple btn-sm" type="submit"><i class="ace-icon fa fa-search bigger-110"></i>搜 索</button></label>
								<label><button class="btn btn-light btn-sm" type="reset"><i class="ace-icon fa fa-undo bigger-110"></i>清 空</button></label>
							</div>
						</div>
					</div>
					
					<@thead>
						<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:40%;">会员账号</th>
						<th class="sorting_disabled" style="width:30%;">请求时间</th>
						<th class="sorting_disabled" style="width:10%;">状态</th>
						<th class="sorting_disabled" style="width:10%;">操作</th>
					</@thead>
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:40%;">${item.memberAccount}</td>
							<td style="width:30%;">${item.modifyDate?string("yyyy-MM-dd HH:mm:ss")}</td>
							<td style="width:10%;">
								<span class="ace-icon fa ${(1==item.state)?string('green', ((0==item.state)?string('light-orange', 'light-grey')))}"><i class="${(1==item.state)?string('fa-check', ((0==item.state)?string('fa-ban', 'fa-times')))}"></i></span>
							</td>
						<td class="op" style="width:10%;">
							<div class="hidden-sm hidden-xs action-buttons">
								<#if 0 lte item.state>
								<a class="delete-dialog red" href="javascript:void(0);" link="${prefix}del/${item.id}" > <i class="ace-icon fa fa-trash-o bigger-130"></i></a>
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