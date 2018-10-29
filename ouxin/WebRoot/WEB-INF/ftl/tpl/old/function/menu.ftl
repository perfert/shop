<#macro menuBody>
	<body class="menu_frame">
		<#nested />
	</body>
</#macro>

<#macro menu>
	<div class="clearperfect"></div>
	<div class="m_search">
		<div class="m_search_bg"><input class="m_search_con" type="text" value="功能搜索" /></div>
		<a class="m_back Cback" href="javascript:;">返回</a>
		<a class="m_close Cclose" href="javascript:;"></a>
	</div>
	<a class="m_arrow_r Carrow CarrowR" style="display:none;" href="javascript:;"><span></span></a>
	<a class="m_arrow_l Carrow CarrowL" style="display:none;" href="javascript:;"><span></span></a>
	<div class="m_frame">
		<#nested />
	</div>
	<div class="clearperfect"></div>
	<div class="m_page"><a href="javascript:;"></a><a href="javascript:;"></a><a href="javascript:;" class="m_page_h"></a><a href="javascript:;"></a></div>
</#macro>

<#macro mainMenu>
	<div class="main_menu Cscroll_menu">
		<ul class="main_list clearfix Cmain_list Cmenu_list">
			<#nested />
		</ul>
	</div>
</#macro>

<#macro mainMenuList item>
	<#list item as topMenu>
		<#if topMenu_index % 12 == 0><li></#if>
		<a href="javascript:;">
			<#--<span class="p_name"><cite><em>商品管理</em></cite></span>-->
			<span class="c_name">${topMenu.name}</span>
		</a>
		<#if ( topMenu_index + 1) % 12 == 0></li></#if>
	</#list>
</#macro>


<#--
	subMenu
	subMenuItems
-->
<#macro subMenu>
	<div class="sub_menu Cscroll_menu" style="display:none;">
		<ul class="sub_list clearfix Cmenu_list">
			<#nested />
		</ul>
	</div>
</#macro>

<#macro subMenuList item>
	<#list item.seconds as secondMenu>
		<#if secondMenu_index % 3 == 0><li></#if>
			<#--	单例
				<div class="list_cols">
					<div class="list_item"><a href="javascript:;"><i></i>商品管理22</a></div>
					<div class="list_item"><a href="javascript:;"><i></i>商品管理22</a></div>
				</div>
			-->
			<div class="list_cols">
				<p class="item_title">${secondMenu.name}</p>
				<div class="t_line"><span></span></div>
				<dl class="item_list">
					<#list secondMenu.thirds as thirdMenu>
						<#if thirdMenu.show>
							<dt><a href="<@thirdMenu.url?interpret />" target="mainFrame">${thirdMenu.name}</a></dt>	
						</#if>
					</#list>
				</dl>
			</div>
		<#if ( secondMenu_index + 1) % 3 == 0></li></#if>
	</#list>
</#macro>

<#macro menuScript>
	<script type="text/javascript">
	<!--

	$(function(){

		//左右箭头自适应
		var $Carrow = $('.Carrow');

		$Carrow.height($(document).height());

		var $CarrowL = $('.CarrowL');
		var $CarrowR = $('.CarrowR');

		var $Cscroll_menu = $('.Cscroll_menu');
		var $Cscroll_menu_first = $Cscroll_menu.eq(0);

		var $Cmenu_list = $('.Cmenu_list');
		var $Cmenu_list_first = $Cmenu_list.eq(0);

		var $Cback = $('.Cback');
		var $Cclose = $('.Cclose');

		var unitWidth = $Cmenu_list.children().width();

		var menuShow = 0;			//一级菜单切换到对应的二级菜单
		var mainScrollLeft = 0		//记录一级菜单的滚动位置
		var subScrollLeft = 0			//记录二级菜单的滚动位置

		//一级菜单切换二级菜单
		$.each($('.Cmain_list li a'),function(i,n){
			var $n = $(n);
			$n.click(function(){
				if ( !$Cscroll_menu_first.is(":animated") ) {
					menuShow = i + 1;		//因为获取到的$Cscroll_menu包括一级和二级菜单，所以要i+1
					var $menuShow = $Cscroll_menu.eq(menuShow);

					mainScrollLeft = $Cscroll_menu_first.scrollLeft();		//记录一级菜单的滚动位置
					$Cscroll_menu_first.slideUp();
					$Cback.animate({opacity:'show'},'slow');
					$menuShow.animate({opacity:'show'},'slow');

					//必须先显示$menuShow才能获取scrollLeft
					var menuScrollLeft = $menuShow.scrollLeft()
					arrowShow(menuScrollLeft);
				}
			})
		});

		//返回一级菜单
		$Cback.click(function(){
			var $menuShow = $Cscroll_menu.eq(menuShow);
			if ( !$menuShow.is(":animated") ) {
				menuShow = 0;
				$Cscroll_menu.hide();
				$Cback.animate({opacity:'hide'},'slow');
				$Cscroll_menu_first.scrollLeft(mainScrollLeft);				//设置一级菜单的滚动位置
				$Cscroll_menu_first.animate({opacity:'show'},'slow');

				if ( mainScrollLeft > 0 ) {
					$CarrowL.animate({opacity:'show'},'slow');
				}else {
					$CarrowL.animate({opacity:'hide'},'slow');
				}
				if ( mainScrollLeft < $Cmenu_list_first.width() - unitWidth ) {
					$CarrowR.animate({opacity:'show'},'slow');
				}else {
					$CarrowR.animate({opacity:'hide'},'slow');
				}
			}
		});



		//菜单初始化
		var $Cmenu = $(window.parent.document).find('.Cmenu')
		var $Ct_menu = $(window.parent.document).find('.Ct_menu');

		for ( var i = 0; i < $Cmenu_list.length; i ++ ) {
			var $each = $Cmenu_list.eq(i);
			$each.width($each.children().length * unitWidth);
		}

		//必须在初始化后隐藏，否则获取不了高宽
		$Cmenu.hide();

		function arrowShow(menuScrollLeft){
			if ( $Cmenu_list.eq(menuShow).children().length > 1 ) {
				if ( menuScrollLeft > 0 ) {
					$CarrowL.animate({opacity:'show'},'slow');
				}else {
					$CarrowL.animate({opacity:'hide'},'slow');
				}
				if ( menuScrollLeft < $Cmenu_list.eq(menuShow).width() - unitWidth ) {
					$CarrowR.animate({opacity:'show'},'slow');
				}else {
					$CarrowR.animate({opacity:'hide'},'slow');
				}
			}else {
				$Carrow.animate({opacity:'hide'},'slow');
			}
		}

		//显示菜单
		$Ct_menu.click(function(){
			var $menuShow = $Cscroll_menu.eq(menuShow);
			var menuScrollLeft = $menuShow.scrollLeft();
			if ( menuShow > 0 ) {		//开始显示子菜单
				if ( $Cmenu.height() > 1 ) {
					subScrollLeft = menuScrollLeft;
				}
				$menuShow.scrollLeft(subScrollLeft);
				arrowShow(subScrollLeft);
			}else {		//开始显示主菜单
				if ( $Cmenu.height() > 1 ) {
					mainScrollLeft = menuScrollLeft;
				}
				$menuShow.scrollLeft(mainScrollLeft);
				arrowShow(mainScrollLeft);
			}

		});

		//关闭菜单
		$Cclose.click(function(){
			$(window.parent.document).find('.Cmenu').animate({opacity:'hide'},'slow');
		});
		$('.item_list a').click(function(){
			$(window.parent.document).find('.Cmenu').animate({opacity:'hide'},'slow');
		})

		$CarrowL.click(function(){
			var $menuShow = $Cscroll_menu.eq(menuShow);
			var menuScrollLeft = $menuShow.scrollLeft();

			$menuShow.stop(true,true,true);
			if ( menuShow > 0 ) {
				subScrollLeft = menuScrollLeft - unitWidth;
			}else {
				mainScrollLeft = menuScrollLeft - unitWidth;
			}

			if ( !$menuShow.is(":animated") && menuScrollLeft > 0 ) {
				$menuShow.animate({scrollLeft:menuScrollLeft - unitWidth},'slow');
			}
			$CarrowR.animate({opacity:'show'},'slow');
			if ( menuScrollLeft == unitWidth || menuScrollLeft < unitWidth ) {
				$CarrowL.animate({opacity:'hide'},'slow');
			}

		});
		$CarrowR.click(function(){
			var $menuShow = $Cscroll_menu.eq(menuShow);
			var menuScrollLeft = $menuShow.scrollLeft();

			$menuShow.stop(true,true,true);
			if ( menuShow > 0 ) {
				subScrollLeft = menuScrollLeft + unitWidth;
			}else {
				mainScrollLeft = menuScrollLeft + unitWidth;
			}

			if ( !$menuShow.is(":animated") && menuScrollLeft < ( $menuShow.children().width() - unitWidth ) ) {
				$menuShow.animate({scrollLeft:menuScrollLeft + unitWidth},'slow');
			}
			$CarrowL.animate({opacity:'show'},'slow');
			if ( menuScrollLeft == $menuShow.children().width() - unitWidth * 2 || menuScrollLeft > $menuShow.children().width() - unitWidth * 2 ) {
				$CarrowR.animate({opacity:'hide'},'slow');
			}
		});




	});
	//-->
	</script>
</#macro>