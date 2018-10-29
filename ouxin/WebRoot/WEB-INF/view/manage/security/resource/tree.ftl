<#macro tree items prefix>
	<#if items?? && 0 lt items?size>
		<ol class="dd-list">
			<#list items as item>
				<#if (item.node.systemModuleId)?trim == systemModuleId?trim>
					<li class="dd-item dd2-item" data-id="${item.node.id}">
						<div class="dd-handle dd2-handle">
							<i class="normal-icon ace-icon fa ${('' == (item.node.icon)!?trim)?string('fa-list', (item.node.icon!'fa-list'))} ${(1==item.node.state)?string('', ((0==item.node.state)?string('light-orange', 'light-grey')))} bigger-130"></i>
							<i class="drag-icon ace-icon fa fa-arrows bigger-125"></i>
						</div>
						<div class="dd2-content ${(1==item.node.state)?string('', ((0==item.node.state)?string('light-orange', 'light-grey')))}">
							<span>${item.node.name}</span>
							<#if 0 lte item.node.state>
								<div class="pull-right action-buttons">
									<span style="float:left; width:300px;">${item.node.uri}</span>
									<span style="float:left; margin-top:3px; width:40px;" class="ace-icon fa"><span class="${(item.node.isMenu)?string('fa-check', 'fa-times')}"></span></span>
									<span style="float:left; text-align:center; width:40px;">${item.node.lft}</span>
									<span style="float:left; text-align:center; width:40px;">${item.node.rgt}</span>
									<span style="float:left; margin-top:3px; width:40px;" class="ace-icon fa ${(1==item.node.state)?string('green', ((0==item.node.state)?string('light-orange', 'light-grey')))}"><i class="${(1==item.node.state)?string('fa-check', ((0==item.node.state)?string('fa-ban', 'fa-times')))}"></i></span>
									<div style="float:left; text-align:center; width:100px;">
										<a class="blue" href="${prefix!}${item.node.id}/create"> <i class="ace-icon fa fa-plus-circle bigger-130"></i></a>
										<a class="green" href="${prefix!}${item.node.parentId}/${(item!).node.id!}"> <i class="ace-icon fa fa-pencil bigger-130"></i></a>
										<a class="delete-dialog red" href="javascript:void(0);" link="${prefix!}${item.node.parentId}/del/${(item!).node.id!}" > <i class="ace-icon fa fa-trash-o bigger-130"></i></a>
									</div>
								</div>
							</#if>
						</div>
						<#if item.children?? && 0 lt item.children?size>
							<@tree items=item.children prefix=prefix />
						</#if>
					</li>
				</#if>
			</#list>
		</ol>
	</#if>
</#macro>