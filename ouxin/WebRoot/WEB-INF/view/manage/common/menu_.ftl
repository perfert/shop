<#--
	menu list
	@author: MAS
-->
<#macro menu>
				<ul class="nav nav-list">
					<li class="active">
						<@o.a href="${ctxPath!}/manage/main">
							<i class="menu-icon fa fa-home"></i>
							<span class="menu-text"> 首 页 </span>
						</@o.a>
						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-cogs"></i>
							<span class="menu-text"> 核心配置 </span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<@o.a href="${ctxPath!}/manage/system/system-module/list">
									<i class="menu-icon fa fa-caret-right"></i>
									系统模块
								</@o.a>
								<b class="arrow"></b>
							</li>
							<li class="">
								<@o.a href="${ctxPath!}/manage/security/1/resource/0/list">
									<i class="menu-icon fa fa-caret-right"></i>
									后台菜单
								</@o.a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>
					
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-lock"></i>
							<span class="menu-text"> 权限配置 </span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<@o.a href="${ctxPath!}/manage/security/role/list">
									<i class="menu-icon fa fa-caret-right"></i>
									角色管理
								</@o.a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>
					
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-globe"></i>
							<span class="menu-text"> 产品配置 </span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<@o.a href="${ctxPath!}/manage/product/category/0/list">
									<i class="menu-icon fa fa-caret-right"></i>
									产品分类
								</@o.a>
								<b class="arrow"></b>
							</li>
							<li class="">
								<@o.a href="${ctxPath!}/manage/product/brand/list">
									<i class="menu-icon fa fa-caret-right"></i>
									产品品牌
								</@o.a>
								<b class="arrow"></b>
							</li>
							<li class="">
								<@o.a href="${ctxPath!}/manage/product/product/list">
									<i class="menu-icon fa fa-caret-right"></i>
									产品管理
								</@o.a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>
					
				</ul><!-- /.nav-list -->
</#macro>