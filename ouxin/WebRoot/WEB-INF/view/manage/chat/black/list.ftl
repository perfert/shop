<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_list />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items="黑名单列表" />			
			<@content>
				<@dataTable pager=pager prefix="${ctxPath}/manage/chat/${member}/black/"; pager, prefix>
				
					<@search prefix=prefix; prefix>
						<label>拉黑账号：<input type="text" name="query.targetAccount" value="${query.targetAccount}" aria-controls="sample-table-2" /></label>
					</@search>
					
					<@thead>
						<th class="sorting_disabled" style="width:10%;"><label class="pos-rel"><input type="checkbox" class="ace"><span class="lbl"> ID </span></label></th>
						<th class="sorting_disabled" style="width:20%;">申请账号</th>
						<th class="sorting_disabled" style="width:20%;">拉黑账号</th>
						<th class="sorting_disabled" style="width:15%;">申请状态</th>
						<th class="sorting_disabled" style="width:20%;">请求时间</th>
						<th class="sorting_disabled" style="width:5%;">状态</th>
						<th class="sorting_disabled" style="width:10%;">操作</th>
					</@thead>
					<@tbody pager=pager prefix=prefix; item, pager, prefix>
						<tr role="row" class="odd ${(1==item.state)?string('', ((0==item.state)?string('light-orange', 'light-grey')))}">
							<td style="width:10%;"><label class="pos-rel"> <input type="checkbox" class="ace" name="ids" value="${item.id}" ${(-1==item.state)?string('disabled="disabled"', '')}> <span class="lbl"></span>&nbsp;${item.id}</label></td>
							<td style="width:20%;">${item.memberAccount}</td>
							<td style="width:20%;">${item.targetAccount}</td>
							<td style="width:15%;"><#if item.type == 0>取消黑名单<#else>加入黑名单</#if></td>	
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