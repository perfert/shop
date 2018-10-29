<#--
	menu list
	@author: MAS
-->
<#macro menu items>
			<#list items as item>
				<#if item.node.isMenu>
					<li class="${item.menuCheck?string('active' + ((item.children?? && 0 lt item.children?size)?string(' open', '')) , '')}">
						<#if item.hasChildMenu && item.children?? && 0 lt item.children?size>
							<a href="#" class="dropdown-toggle">
								<i class="menu-icon fa ${('' == (item.node.icon)!?trim)?string('fa-list', (item.node.icon!'fa-list'))}"></i>
								<span class="menu-text"> ${item.node.name} </span>
								<b class="arrow fa fa-angle-down"></b>
							</a>
							<b class="arrow"></b>
							<ul class="submenu">
								<@menu items=item.children />
							</ul>
						<#else>
							<@o.a href="${ctxPath!}/${item.node.uri}">
								<i class="menu-icon fa ${('' == (item.node.icon)!?trim)?string('fa-caret-right', (item.node.icon!'fa-caret-right'))}"></i>
								${item.node.name}
							</@o.a>
							<b class="arrow"></b>
						</#if>
					</li>
				</#if>
			</#list>
</#macro>