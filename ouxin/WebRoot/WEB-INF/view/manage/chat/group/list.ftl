<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="群聊列表" />			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/chat/group/"; pager, prefix>
					
					<div class="row">
						<div class="col-sm-8" style="width:auto;">
							<div class="dataTables_filter">
								<label>群聊账号：<input type="text" name="query.name" value="${query.name}" aria-controls="sample-table-2" /></label>
								<label><button class="btn btn-purple btn-sm" type="submit"><i class="ace-icon fa fa-search bigger-110"></i>搜 索</button></label>
								<label><button class="btn btn-light btn-sm" type="reset"><i class="ace-icon fa fa-undo bigger-110"></i>清 空</button></label>
							</div>
						</div>
					</div>
					
					<@thead>
						<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:20%;">名称</th>
						<th class="sorting_disabled" style="width:10%;">创建者</th>
						<th class="sorting_disabled" style="width:10%;">总人数</th>
						<th class="sorting_disabled" style="width:10%;">人员上限</th>
						<th class="sorting_disabled" style="width:10%;">聊天室类型</th>
						<th class="sorting_disabled" style="width:5%;">能否邀请</th>
						<th class="sorting_disabled" style="width:15%;">介绍</th>
						<th class="sorting_disabled" style="width:10%;">操作</th>
					</@thead>
					
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:20%;">${item.name}</td>
							<td style="width:10%;">${item.ownerAccount}</td>
							<td style="width:10%;">${item.nowCount}</td>
							<td style="width:10%;">${item.maxusers}</td>
							<td style="width:10%;"><#if item.open>公开<#else>私有</#if></td>
							<td style="width:5%;"><span class="ace-icon fa <#if item.allow>green<#else>red</#if>"><i class="<#if item.allow>fa-check<#else>fa-ban</#if>"></i></span></td>
							<td style="width:15%;">${item.detail}</td>
							<td class="op" style="width:10%;">
								<div class="hidden-sm hidden-xs action-buttons">
									<#if 0 lte item.state>
									<a class="green" href="${ctxPath}/manage/chat/${item.id}/gmembers/list"><i class="ace-icon fa fa-users bigger-130"></i></a>
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