<#macro listBody>
	<body class="list_frame">
		<#nested />
		<@userTips />
		<@dialog />
	</body>
</#macro>

<#macro listForm name="" action="" method="post">
	<form class="list_form" name="${name}" action="${action}" method="${method}">
		<#nested />
	</form>
</#macro>

<#macro listTool>
	<div class="tools Ctools">
		<#nested />
	</div>
</#macro>

<#macro toolList>
	<div class="t_list">
		<#nested />
		<a class="f_line" href="javascript:;"></a>
	</div>
</#macro>

<#macro toolExtend>
	<div class="t_extend Ct_extend">
		<#nested />
	</div>
</#macro>

<#macro optSearchExtend title="" formAction="">
	<div class="search_extend">
		<div class="ext_title"><a href="javascript:;" class="close"></a>商品搜索</div>
		<div class="ext_con Cext_con">
			<table class="info_table ext_table">
				<#nested />
				<tr>
					<td>
						<a href="javascript:;" class="Abtn f_submit search_submit" formAction="${formAction}"><span><cite>确 定</cite></span></a><a href="javascript:;" class="Abtn search_reset"><span><cite>重 置</cite></span></a>
					</td>
				</tr>
			</table>
		</div>
	</div>
</#macro>

<#macro listData>
	<div class="data_frame">
		<#nested />
	</div>
</#macro>

<#macro listDataHeader>
	<div class="data_header CDheader">
		<table class="header_table">
			<tr>
				<@listDataCheck />
				<#nested />
			</tr>
		</table>
	</div>
</#macro>

<#macro listDataHeaderItem order="" formAction="" width="" text="">
	<td width="${width}">
		<span class="table_cell">
			<#if "" != order>
				<a class="order_${order} f_submit" formAction="${formAction}" href="javascript:;">${text!""}<i></i></a>
			<#else>
				${text!""}
			</#if>
		</span>
	</td>
</#macro>

<#macro listDataCheck id="" value="" name="" on=false>
	<th width="32"><span class="table_cell"><a href="javascript:;" class="Icheck CIcheck" ><i></i><input type="checkbox" <#if "" != id>id="${id}"</#if> value="${value}" name="${name}" <#if true == on> checked="checked"</#if> /></a></span></th>
</#macro>

<#macro listDataBody>
	<div class="data_list CDlist">
		<table class="list_table">
			<#nested />
		</table>
	</div>
</#macro>

<#macro listDataBodyList id="" value="" name="" on=false status="">
	<tr class="<#if "" != status>${status}</#if>">
		<@listDataCheck id=id value=value name=name on=on />
		<#nested disabled />
	</tr>
</#macro>

<#macro listDataBodyListItem formAction="" viewHref="" width="" text="">
	<#if "" != formAction>
		<td width="${width}" >
			<span class="table_cell">
			<#if "" != viewHref>
				<a class="viewer" target="_blank" href="${viewHref!'javascript:;'}"></a>
			</#if>
				<a class="Dname CDname f_submit" href="javascript:;" formAction="${formAction}">${text!""}</a>
			</span>
		</td>
	<#else>
		<td width="${width}">
			<span class="table_cell">
			<#if "" != viewHref>
				<a class="viewer" target="_blank" href="${viewHref!'javascript:;'}">${text!""}</a>
			<#elseif "" != text>
				${text!""}
			<#else>
				<#nested />
			</#if>
		</td>
	</#if>

</#macro>

<#macro listDataFooter>
	<div class="data_footer">
		<div class="footer_con">
			<#nested />
		</div>
	</div>
</#macro>

<#macro listDataRecord recordCount=0 pageCount=1>
	<span>总记录数：<strong>${recordCount}</strong> 条(共 <strong>${pageCount}</strong> 页)</span>
</#macro>

<#macro listDataRecordView>
	<span>每页显示：</span>
	<#nested />
</#macro>

<#macro listIframe id="" src="">
	<iframe class="content Ccontent" <#if "" != id>id="${id}"</#if> name="contentFrame" frameborder="0" width="100%" scrolling="auto" src="${src!""}"></iframe>
</#macro>


<#macro listScript>

	<script type="text/javascript">
	<!--

	//code by LGL
	$(function(){


		//表单对象
		var $list_form = $('.list_form');


	//Dialog框架
		function DialogShow(title,text,type,delete_data){
			var $dialog_frame = $('.dialog_frame');
			var $boxs = $('.dialog_frame .f_box');
			var $delete_dialog = $('.dialog_frame .Delete');
			var $message_dialog = $('.dialog_frame .Message');
			var $cancel = $('.dialog_frame .cancel');
			var $delete_comfirm = $('.dialog_frame .Delete .comfirm');

			var win_width = $(window).width();
			var win_height = $(window).height();

			$dialog_frame.stop(true,true,true);
			$boxs.stop(true,true,true);

			$dialog_frame.animate({opacity:'show'},600);

			if ( type == 'delete' ) {
				var $tpl = $delete_dialog.find('.text').eq(0);
				$tpl.siblings('.text').remove();
				AddText($tpl);
				BoxShow($delete_dialog);
			}
			if ( type == 'message' ) {
				BoxShow($message_dialog);
				$message_dialog.find('.text').text(text);
			}

			$delete_comfirm.click(function(){
				var formAction = $('.Ctools .t_remove').attr('formAction');
				for ( var i = 0; i < delete_data.length; i ++ ) {
					formAction = formAction + delete_data[i] + ',';		//改成join()更好
				}
				$list_form.attr('action',formAction.substr(0,formAction.length-1));
				$list_form.submit();
			});

			$cancel.click(function(){
				BoxHide();
			});

			function AddText($tpl){
				if ( text ) {
					for ( var i = 0; i < text.length; i ++ ) {
						var $each = $tpl.clone().text(text[i]);
						$tpl.after($each);
					}
					$tpl.remove();
				}else {
					$tpl.text('##@%^!OOXX@#$~!!消失的密室');
				}
			}

			function BoxShow(oDialog){
				oDialog.show();
				oDialog.find('.f_body').height(220);
				var oleft = ( win_width - oDialog.width() ) / 2 + document.documentElement.scrollLeft;
				var otop = win_height / 2 + document.documentElement.scrollTop - oDialog.height()/2;
				oDialog.css({'left':oleft,'top':win_height/2 - 200});
				$boxs.hide();
				oDialog.animate({'top':otop,'opacity':'show'},700);
			}

			function BoxHide(){
				var otop = win_height / 2 - 200;
				$boxs.animate({'top':otop,'opacity':'hide'},400,function(){
					$dialog_frame.animate({opacity:'hide'},200);
				});
			}
		}

		<#if error??>
			DialogShow('',"${error.message}",'message');
		</#if>


	//提示框架
		var $tips_frame = $('.tips_frame');
		var $tips_text = $('.tips_frame .tips_text');
		var $tips_close = $('.tips_frame .close');
		var $tips_back = $('.tips_frame .back');
		var TFbottom = parseInt( $tips_frame.css('bottom') ) + $tips_frame.height();
		var AutoClose = 0;

		function TFinit(){
			$tips_frame.css({'bottom':10,'opacity':0});
			$tips_close.hide();
		}

		var aniFun=[
			function(){
				$tips_frame.animate({'bottom':TFbottom,'opacity':1},400,runAni);
			},
			function(){
				$tips_close.animate({'opacity':'show'},400,runAni);
			},
			function(){
				if ( AutoClose == 1 ) {
					$tips_frame.delay(2000).animate({'bottom':10,'opacity':0},400,function(){
						$tips_close.hide();
					});
				}
			}
		];
		var runAni=function(){
			$tips_frame.dequeue('aniList');
		};

		//提示框调用
		function TipsShow(tipsText,tipsType,isTrace){
			TFinit();
			if ( isTrace ) {
				$tips_back.hide()
			}
			$tips_frame.removeClass('error,right,warning');
			$tips_frame.addClass(tipsType);
			if ( !$tips_frame.is(":animated") ) {
				$tips_text.html(tipsText);		//塞内容
				$tips_frame.stop();
				$tips_close.stop();
				//错误时不自动隐藏提示语
				if ( tipsType == 'error' ) {
					AutoClose = 0;
				}else {
					AutoClose = 1;
				}
				$tips_frame.queue('aniList',aniFun);		//加入动画队列
				runAni();		//执行动画
			}
		}

		//提示框鼠标hover停止动画
		$tips_frame.hover(
			function(){
				$tips_frame.clearQueue();
			},
			function(){
				if ( AutoClose == 1 ) {
					if ( !$tips_frame.is(":animated") ) {
						aniFun[2]();
					}
				}
			}
		);

		//提示框关闭
		function TipsHide(){
			//判断是否在动画防止可以连续点到关闭按钮
			if ( ! $tips_frame.is(":animated") ) {
				$tips_frame.stop();
				$tips_close.stop();
				$tips_frame.animate({'bottom':10,'opacity':0},400,function(){
					$tips_close.hide();
				});
			}
		}

		//关闭提示框
		$tips_close.click(function(){
			TipsHide();
		});


	//左侧工具栏

		//左侧工具栏自适应高度
		var $Ctools = $('.Ctools,.Ct_extend');
		var $extend_con = $('.Ct_extend .Cext_con');

		var window_height = $(window).height();
		var extend_title_height = $('.Ct_extend .ext_title').outerHeight(true);

		$Ctools.height(window_height);
		$extend_con.height(window_height - extend_title_height);
		$(window).resize(function(){
			var window_height = $(window).height();
			$Ctools.height(window_height);
			$extend_con.height(window_height - extend_title_height);
		});

		//工具栏动作
		var $tool_remove = $('.Ctools .t_remove');
		var $tool_search = $('.Ctools .t_search');

		var $tool_extend = $('.Ct_extend');
		var $extend_close = $('.Ct_extend .close');

		var $search_extend = $('.Ct_extend .search_extend');
		var $search_reset = $('.Ct_extend .search_reset');

		//删除功能
		$tool_remove.click(function(){
			//获取列表框选中的对象
			var $D_input = $('.CDlist input[checked="checked"]');
			var $CDname = $D_input.parents('tr').find('.CDname');

			//取出商品的ID值(给提交表单用)和文本(删除提示用)
			var value_arr = getArray($D_input,'value');
			var text_arr = getArray($CDname,'text');

			function getArray(obj,attrs){
				var items = [];
				for ( var i = 0; i < obj.length; i ++ ) {
					var values = '';
					if ( attrs == 'text' ) {
						values = obj.eq(i).text();
					}else {
						values = obj.eq(i).attr(attrs);
					}
					items.push(values);
				}
				return items;
			}

			if ( $D_input.length < 1 ) {
				TipsShow('请选择一个要<strong>"干掉"</strong>的','warning');
			}else {
				DialogShow('删除确认',text_arr,'delete',value_arr);
			}

		});

		//搜索功能
		$tool_search.click(function(){
			ExtendShow($search_extend);
		});

		$search_reset.click(function(){
			var $parent = $(this).parents('.Cext_con');

			$parent.find('input[type="hidden"],input[type="text"]').val('');

			$.each($parent.find('.Iselect'),function(i,n){
				var $n = $(n);
				var $selectItem = $n.find('.Iselect_con a');
				if ( $n.hasClass('ICselect') ) {
					$selectItem.eq(0).click();
					$n.find('.Iselect_shadow,.Iselect_con').hide().css('opacity',1);
				}else {
					$selectItem.removeClass('on');
					$selectItem.eq(0).addClass('on');
					$n.find('input:text').val($selectItem.eq(0).text());
				}
			});

			$parent.find('.CIcheck').removeClass('on');
			$parent.find('.CIcheck input:checkbox').removeAttr('checked');

			$.each($parent.find('.check_wrap'),function(i,n){
				var $n = $(n);
				$n.find('.CIradio').eq(0).click();
			});
		});

		//关闭功能层
		$extend_close.click(function(){
			$tool_extend.animate({'left':'35px',opacity:'hide'},400);
		});



		function ExtendShow(showEle){
			if ( !$tool_extend.is(":animated") ) {
				if ( $tool_extend.css('display') == 'none' ) {

					$tool_extend.children().hide();
					showEle.show();
					$tool_extend.css('left','35px');
					$tool_extend.animate({'left':'50px',opacity:'show'},400);

				}else {
					$tool_extend.animate({'left':'35px',opacity:'hide'},400);

				}
			}
		}

	//列表标题宽度调整
	//使用position:fixed的top,left,right,bottom可定高宽
		/*
		var $header_table = $('.CDheader .header_table');
		var tool_w = $Ctools.outerWidth(true);	//调用外变量$Ctools
		$header_table.width($(window).width() - tool_w);
		$(window).resize(function(){
			$header_table.width($(this).width() - tool_w);
		});
		*/

	//列表选择与取消动作
		var $Dheader_chk = $('.CDheader .CIcheck');	//只有一个，全选复选框
		var $Dheader_ico = $Dheader_chk.children('i');
		var $Dlist_tr = $('.CDlist tr');
		var $Dlist_chk = $('.CDlist .CIcheck');
		var $Dlist_ico = $Dlist_chk.children('i');
		var $D_chk = $('.CDlist .CIcheck');
		var $D_ico = $D_chk.children('i');
		var $D_input = $D_chk.children('input');

		var on_count = 0;
		//列表复选框动作


		$.each($Dlist_tr,function(i,n){
			var $n = $(n);
			var $chk = $Dlist_chk.eq(i);
			var $chk_ico = $chk.children('i');
			var $chk_input = $chk.children('input');

			$n.click(function(){
				$Dheader_chk.removeClass('on');	//全选checkbox
				if ( $chk.hasClass('on') ) {
					$n.removeClass('on');				//列表tr
					$chk.removeClass('on');				//列表checkbox
					$chk_ico.hide();						//列表checkbox.children(i)
					$chk_input.removeAttr('checked');
					if ( on_count > 0 ) {
						on_count --;
					}
				}else {
					TipsHide();
					$n.addClass('on');
					$chk.addClass('on');
					$chk_ico.hide();
					$chk_ico.show(200);
					$chk_input.attr('checked','checked');
					if ( on_count == $Dlist_tr.length - 1 ) {
						$Dheader_chk.addClass('on');
						$Dheader_ico.show(200);
					}
					on_count ++;
				}
			});

			$n.hover(
				function(){
					$(this).addClass('hover');
				},
				function(){
					$(this).removeClass('hover');
				}
			);

		});
		//标题复选框动作
		$Dheader_chk.click(function(){
			$this = $(this);
			if ( $this.hasClass('on') ) {
				$this.removeClass('on');			//全选checkbox本身
				$Dlist_tr.removeClass('on');	//列表tr
				$D_chk.removeClass('on');		//列表checkbox
				$D_ico.hide();						//列表checkbox.children(i)
				$D_input.removeAttr('checked');
				on_count = 0;
			}else {
				TipsHide();
				$this.addClass('on');				//全选checkbox本身
				$Dlist_tr.addClass('on');			//列表行
				$D_chk.addClass('on');			//列表checkbox
				$D_ico.show(200);				//列表checkbox.children(i)
				$D_input.attr('checked','checked');
				on_count = $Dlist_tr.length;
			}
		});

	//控件聚焦与失焦动作
		var $widget_frame = $('.Itext');
		var $widget_input = $('.Itext input');
		$widget_frame.hover(
			function(){
				$(this).addClass('hover');
			},
			function(){
				$(this).removeClass('hover');
			}
		);

		$widget_input.focus(function(){
			$(this).parents('.Itext').addClass('on');
		});
		$widget_input.blur(function(){
			$(this).parents('.Itext').removeClass('on');
		});

	//下拉菜单模拟
		var $Iselect = $('.Iselect'),$IselectParent = $Iselect.parent();

		//Iselect 方法集合
		var iSelect = {
			/*
				Iselect修复方法
				objSelect为jquery对象
			*/
			fixed:function(objSelect,isClone){
				$.each(objSelect,function(i,n){
					var $n = $(n);

					var $n_w = $n.innerWidth();
					var $Iselect_shadow = $n.children('.Iselect_shadow');
					var $Iselect_con = $n.children('.Iselect_con');
					var con_border = parseInt($Iselect_con.css('borderLeftWidth'));

					//处理默认值
					iSelect.dValue($n);

					//下拉框阴影宽度自适应
					$Iselect_shadow.width($n.innerWidth() - con_border * 2);
					//下拉框宽高自适应
					if ( $Iselect_con.width() < $n_w ) {
						$Iselect_con.width($n_w - con_border * 2);
					}

					$Iselect_shadow.hide().css('opacity',1);
					$Iselect_con.hide().css('opacity',1);
				});
			}

			/*
				Iselect 处理默认值事件
				objSelect为jquery对象
			*/
			,dValue:function(objSelect){
				var _this = objSelect,$inputHide = _this.siblings('input:hidden'),$inputText = _this.find('input:text'),_value = $inputHide.val();

				//对隐藏域对象进行判断
				if($inputHide.length === 0){
					$inputHide = _this.find('input:hidden');
					_value = $inputHide.val();
				}

				var _defLiVal = _this.find('li>a[value=""]').eq(0).text();
				if(_value === ""){
					$inputText.val(_defLiVal);
				}else{
					var selectOption = _this.find('li>a.on'),_txt =selectOption.html();
					if(_txt && _txt !=""){
						$inputText.val(selectOption.html());
					}else{
						$inputText.val(_defLiVal);
					}
				}
			}

			/*
				Iselect下拉框隐藏事件
				objSelect为单个原生对象
			*/
			,hide:function(objSelect){
				var _this = $(objSelect),$Iselect_shadow = _this.children('.Iselect_shadow'),$Iselect_con = _this.children('.Iselect_con');
				$Iselect_shadow.animate({opacity:'hide'},200);
				$Iselect_con.animate({opacity:'hide'},200);
			}

			/*
				Iselect下拉框显示事件
				objSelect为单个jquery对象
			*/
			,show:function(objSelect){
				var _this = $(objSelect),$Iselect_shadow = _this.children('.Iselect_shadow'),$Iselect_con = _this.children('.Iselect_con');
				$Iselect_shadow.animate({opacity:'show'},200);
				$Iselect_con.animate({opacity:'show'},200);
			}

			/*
				Iselect mouseover事件
				objSelect为单个原生对象
			*/
			,mover:function(objSelect){
				$(objSelect).addClass('hover');
			}

			/*
				Iselect mouseover事件
				objSelect为单个原生对象
			*/
			,mout:function(objSelect){
				var _this = $(objSelect),_isFocus = _this.attr('nFocus');
				if(_isFocus != 1){
					_this.removeClass('hover');
				}
			}

			/*
				Iselect click事件
				objSelect为单个原生对象
			*/
			,click:function(objSelect){
				var _this = $(objSelect),$Iselect_input = _this.find('input:text'),$Iselect_con = _this.children('.Iselect_con');
				$Iselect_input.focus();
				if ( $Iselect_con.css('display') == 'block' ) {
					iSelect.hide(objSelect);
					_this.removeClass('on').addClass('hover');
				}else {
					iSelect.show(objSelect);
					_this.addClass('on');
					$Iselect_con.height($Iselect_con.children().height());
				}
			}

			/*
				Iselect 中 Input blur事件
				objInput为单个原生对象
			*/
			,inputBlur:function(objInput){
				var _this = $(objInput),_Iselect = _this.parents('.Iselect');
				_Iselect.attr('nFocus',0)
				iSelect.hide(_Iselect);
				_Iselect.removeClass('hover');
				_Iselect.removeClass('on');
			}

			/*
				Iselect 中 Input focus事件
				Iselect中的input聚焦后不会被hover去掉样式
				objInput为单个原生对象
			*/
			,inputFocus:function(objInput){
				var _this = $(objInput),_Iselect = _this.parents('.Iselect');
				_Iselect.attr('nFocus',1);
			}

			/*
				Iselect 中 Input click事件
				Iselect中的input点击时不弹出子菜单
			*/
			,inputClick:function(){
				return false;
			}

			/*
				Iselect 中 a click事件
				相当于模仿select 的 change事件
				objA为单个原生对象
			*/
			,aClick:function(objA){
				var _this = $(objA),txt = _this.text(),_Iselect = _this.parents('.Iselect'),
				_parentDiv = _Iselect.parent(),isLast = _parentDiv.attr('islast'),
				_Iselect = _this.parents('.Iselect'),$Iselect_input = _Iselect.find('input:text'),
				_ajax = _parentDiv.attr('ajax'),_prevAll = _Iselect.prevAll('.Iselect');
				$Iselect_value = _Iselect.siblings('input:hidden'),_value =_this.attr('value');

				//对隐藏域对象进行判断
				if($Iselect_value.length === 0){
					$Iselect_value = _Iselect.find('input:hidden');
				}

				//如果Iselect的值没有改变则不进行下面的操作
				if($Iselect_value.val() === _value && $Iselect_value.val() != '') return false;

				if ( txt != '' ) {
					$Iselect_input.val(txt);
					$Iselect_value.val(_value);

					//最后一级不一定要选择时，而此时最后一级选择的是"请选择"的情况，则隐藏的input应赋予上一级选中的值
					if( isLast === "false" && _value === "" && _prevAll.length>0){
						var _dval = _prevAll.eq(0).find('.Iselect_list>li>a.on').attr('value');
						$Iselect_value.val(_dval);
					}

					_Iselect.find('a').removeClass('on');
					_this.addClass('on');
				}

				//移除同一级的Iselect
				iSelect.remove(_Iselect);

				//请求下一级
				iSelect.ajaxFn(_Iselect,_value,_ajax);
			}

			/*
				Iselect 中ajax事件，实现数据的分析和填充
				_Iselect Iselect对象
				_value  Iselect执行选择后获取到的值
				_ajax  传过来的ajax路径，没有传则获取父级属性的ajax
				callback ajax成功完成后执行的方法
			*/
			,ajaxFn:function(_Iselect,_value,_ajax,callback){
				var _parentDiv = _Iselect.parent(),isLast = _parentDiv.attr('islast'),
				$Iselect_value = _Iselect.siblings('input:hidden'),_itemVaule = _parentDiv.attr('itemValue'),
				_itemText = _parentDiv.attr('itemText'),_ignore = _parentDiv.attr('data-ignore')
				,_load = _parentDiv.attr('load'),_loadJson = "";

				//对隐藏域对象进行判断
				if($Iselect_value.length === 0){
					$Iselect_value = _Iselect.find('input:hidden');
				}

				//如果没有设置item属性的默认值，则使用value  name
				(!_itemVaule) && (_itemVaule="value");
				(!_itemText) && (_itemText="name");

				if(_ajax && _ajax != "" && _value !=""){
					$.ajax({
						url: _ajax+_value,
						dataType:"json",
						success: function(json){
							if(! json || json.length ===0) return false;

							iSelect.fill(_Iselect,json);

							//如果存在多种级联关联查询
							if(_load && _load !=""){
								_load = _load.replace(/\'/ig,'"'),_loadJson = $.parseJSON(_load);//属性存储的是字符串，所以需要将其格式化成JSON
								iSelect.delDiff(_loadJson,_value);
							}

							//最后的select如果一定要选择，则先把隐藏值清空
							if(isLast === "true"){
								$Iselect_value.val("");
							}

							callback && callback();//ajax成功执行完之后执行callback方法
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							oap.log(errorThrown);
						}
					});
				};
			}

			/*
				Iselect fill事件，创造一个新的Iselect
				_Iselect 为Iselect对象
				jsonData json数据
			*/
			,fill:function(_Iselect,jsonData){
				var _clone = _Iselect.clone();
				var _parentDiv = _Iselect.parent(),_itemVaule = _parentDiv.attr('itemValue'),
				_itemText = _parentDiv.attr('itemText'),_ignore = _parentDiv.attr('data-ignore');

				var ulStr = '<li><a href="javascript:;" value="">请选择</a></li>',_item="";
				for ( var i = 0; i < jsonData.length; i ++ ) {
					_item = jsonData[i];
					if(_ignore != _item[_itemVaule]){//忽略值的判断，如果当前处于该ID的资料编辑，则该ID就不能出现在下拉框里面
						ulStr += '<li><a href="javascript:;" value="'+ _item[_itemVaule] +'">'+ _item[_itemText] +'</a></li>';
					}
				}
				_clone.find('.Iselect_list').html(ulStr);
				_Iselect.after(_clone);
				iSelect.fixed(_clone,true);
			}


			/*
				Iselect 中 reomve 事件
				删除同类的Iselect
			*/
			,remove:function(objSelect){
				var _this = $(objSelect),_parentDiv = _this.parent(),_nextAll = _this.nextAll('.Iselect')
				,_load = _parentDiv.attr('load'),_loadJson = "";
				if(_nextAll.length > 0){
					_nextAll.remove();
				}

				//如果需要关联到下一级，即load属性传有对应的JSON值，则进行将相关的Iselect移除，第一个Iselect保留下来而且只保留“请选择”项，并清空对应的隐藏input的值
				//_loadJson 格式为  [{'id':'parentId', 'ajax':'/model/by/type/'}]  id为对应Iselect父级DIV的ID，ajax则为关联下一级请求的ajax路径
				if(_load && _load !=""){
					_load = _load.replace(/\'/ig,'"'),_loadJson = $.parseJSON(_load);//属性存储的是字符串，所以需要将其格式化成JSON
					iSelect.removeDiff(_loadJson);
				}
			}

			/*
				Iselect 处理多种级联事件， 比如分类和品牌的关联，分类改变时对应的品牌的Iselect也会被删除
			*/
			,removeDiff:function(_loadJson){
				var i=0,_item="",_sel = "",_ajax="",_Iselect="",$Iselect_value;
				if(_loadJson && _loadJson.length>0){
					for(;i<_loadJson.length;i++){
						_item = _loadJson[i];
						_sel = $('#'+_item['id']);//获取对象
						_Iselect = _sel.children('.Iselect').eq(0);//获取第一个Iselect
						_Iselect.find('.Iselect_list').html('<li><a href="javascript:;" value="">请选择</a></li>')
						.end().find('input:text').val('请选择');
						$Iselect_value = _Iselect.siblings('input:hidden');

						//对隐藏域对象进行判断
						if($Iselect_value.length === 0){
							$Iselect_value = _Iselect.find('input:hidden');
						}
						$Iselect_value.val('');
						iSelect.remove(_Iselect);
					}
				}
			}

			/*
				Iselect 处理多种级联关联查询事件，查询分类的时候对应的品牌信息也跟着改变
			*/
			,delDiff:function(_loadJson,_value){
				var i=0,_item="",_sel = "",_ajax="",_Iselect="";
				if(_loadJson && _loadJson.length>0){
					for(;i<_loadJson.length;i++){
						_item = _loadJson[i];
						_sel = $('#'+_item['id']);//获取对象
						_Iselect = _sel.children('.Iselect').eq(0);//获取第一个Iselect
						_ajax = _item['ajax'];
						iSelect.ajaxFn(_Iselect,_value,_ajax,function(){
							_Iselect.remove();
						});
					}
				}
			}

		};

		iSelect.fixed($Iselect);

		//对所有 Iselect 进行监听  包括动态生成的
		var $doc = $(document);
		$doc.on('mouseover','.Iselect',function(){
			iSelect.mover(this);
		}).on('mouseout','.Iselect',function(){
			iSelect.mout(this);
		}).on('click','.Iselect',function(){
			iSelect.click(this);
		});

		$doc.on('click','.Iselect input:text',function(){
			return iSelect.inputClick();
		}).on('blur','.Iselect input:text',function(){
			iSelect.inputBlur(this);
		}).on('click','.Iselect input:text',function(){
			iSelect.inputFocus(this);
		});

		$doc.on('click','.Iselect a',function(){
			iSelect.aClick(this);
		})

	//复选框模拟
		var $check_wrap = $('.check_wrap');
		var $check_label = $('.check_label');
		$check_label.hover(
			function(){
				var $this = $(this);
				$this.children('.CIcheck').addClass('hover');
				$this.children('.CIradio').addClass('hover');
			},
			function(){
				var $this = $(this);
				$this.children('.CIcheck').removeClass('hover');
				$this.children('.CIradio').removeClass('hover');
			}
		);
		$check_label.find('input').click(function(){
			return false;
		});
		$check_label.click(function(){
			var $this = $(this);
			var $Icheck = $this.children('.CIcheck');
			var $Iradio = $this.children('.CIradio');

			var $Icheck_ico = $Icheck.children('i');
			var $Iradio_ico = $Iradio.children('i');

			var $Icheck_input = $Icheck.children('input');
			var $Iradio_input = $Iradio.children('input');

			if ( $Icheck.hasClass('on') ) {
				$Icheck.removeClass('on');
				$Icheck_ico.hide();
				$Icheck_input.removeAttr('checked');
			}else {
				$Icheck.addClass('on');
				$Icheck_ico.hide().show(200);
				$Icheck_input.attr('checked','checked');
			}

			if ( $Iradio.hasClass('on') ) {
				return true;
			}else {
				var elseItems = $this.siblings().children('.CIradio');
				elseItems.removeClass('on');
				elseItems.children('i').hide(200);

				$Iradio.addClass('on');
				$Iradio_ico.hide().show(200);

				elseItems.children('input').removeAttr('checked');
				$Iradio_input.attr('checked','checked');
			}

		});

	//列表树
		var $DataTree = $('.DataTree');
		var $HeaderMain = $('.DataTree .DataHeader .TableMain');
		//var $DataList = $('.DataTree .DataList');
		var $ListMain = $('.DataTree .DataList .TableMain');


		//初始化列表树

		///列表左侧宽度自适应
		var ListMainWidth = $HeaderMain.width() - 66;		//这里减去的数字是缩进的像素计算
		if ( $ListMain.length > 0 ) {	//一定要判断是否有对象存在才能用offset方法
			var ListMainPosition = $ListMain.eq(0).offset().left;
			for ( var i = 0; i < $ListMain.length; i ++ ) {
				var $each = $ListMain.eq(i);
				//使用每一个item的offset跟第一个顶级item对比来判断是第几级item
				$each.width( ListMainWidth - ($each.offset().left - ListMainPosition) );
			}
		}



	//树的核心方法
	var $Ltree = $('.Ltree');
	$.each($Ltree,function(i,n){
		var $n = $(n);
		var $tree_wrap = $n.find('.tree_wrap');
		var $tree_item = $n.find('.tree_item');
		var $tree_label = $n.find('.tree_label');
		var $CIcheck = $n.find('.CIcheck');
		var $tree_top = $tree_wrap.eq(0);

		//样式初始化

		///顶层框架不一样
		$tree_top.removeClass('tree_wrap').addClass('tree_top');



		///tree_wrap样式
		for ( var i = 0; i < $tree_wrap.length; i ++ ) {
			var $each = $tree_wrap.eq(i);
			var $parent_item = $each.prev('.tree_item');
			var $parent_arrow = $parent_item.find('.tree_arrow');


			//没有同辈的下级元素，就不显示连结线
			if ( $each.next('.tree_item').length == 0 ) {
				$each.addClass('wrap_end');
			}

			//判断是否展开树枝(以HTML上写着on的样式为标记)
			if ( $each.find('.CIcheck.on').length > 0 ) {
				var $parent_wraps = $each.parents('.tree_wrap');
				var $parent_Icheck = $each.prev('.tree_item').find('.CIcheck');
				var $parent_Icheck_input = $parent_Icheck.children('input');

				$parent_wraps.show();		//如果有默认则展开，展开对象均为上一级的树包(tree_wrap)
				//多功能分叉点(开发中)
				if ( $n.hasClass('killer') ) {		//子级选中后父级不能选
					$parent_Icheck.addClass('disabled');
				}else {
					$parent_Icheck.addClass('on');
					$parent_Icheck_input.attr('checked','checked');
				}
			}else {
				$each.hide();		//当树没有任何初始选中的选项
			}
			//树枝展开后树根的箭头变化
			if ( $each.css('display') == 'block' ) {
				$parent_arrow.removeClass('close').addClass('open');
			}else {
				$parent_item.removeClass('has_child');
				$parent_arrow.removeClass('open').addClass('close');
			}
		}

		//先要处理tree_wrap样式，然后才能处理tree_item样式
		//因为下面的代码需要tree_wrap初始化后获得它的display状态
			/*
			if ( $each.next('.tree_wrap').css('display') == 'none' ) {
				$each.addClass('item_end')
			}
			*/

		///tree_item样式
		for ( var i = 0; i < $tree_item.length; i ++ ) {
			var $each = $tree_item.eq(i);

			if ( $each.next('.tree_wrap').length == 0 ) {
				$each.find('.tree_arrow').addClass('no_arrow');	//是否有子级而显示箭头
				//在判断没有tree_wrap(也就是没有子级)的情况下设置终点样式
				if ( $each.next('.tree_item').length == 0 ) {
					$each.addClass('item_end');
				}
			}else {
				$each.addClass('has_child');
			}
			//在有子级(tree_wrap)并且没有同级(tree_item)的情况下设置child_end，初始化(在没有展开树)时应隐藏
			//child_end是has_child的位移
			//tree_item.item_end样式是专门给初始化和展开收缩时隐藏用的
			if ( $each.nextAll('.tree_item').length == 0 && $each.next('.tree_wrap').length > 0 ) {
				$each.addClass('child_end');
				if ( $each.next('.tree_wrap').css('display') == 'none' ) {
					$each.addClass('item_end')
				}
			}
		}
		//此代码必须放到上面循环之后，因为上面的初始化会把根对象隐掉
		$tree_top.show();


		//展开收缩事件
		$tree_label.click(function(){
			var $this = $(this);
			var $parent_item = $this.parents('.tree_item');
			var $arrow = $this.children('.tree_arrow');

			if ( $arrow.css('display') == 'block' ) {
				var $extend = $parent_item.next('.tree_wrap');
				if ( $extend.css('display') == 'block' ) {
					$extend.slideUp();
					$arrow.removeClass('open').addClass('close');

					$parent_item.removeClass('has_child');
					if ( $extend.hasClass('wrap_end') ) {
						$parent_item.addClass('item_end');
					}
				}else {
					$extend.slideDown();
					$arrow.removeClass('close').addClass('open');

					$parent_item.addClass('has_child');
					if ( $extend.hasClass('wrap_end') ) {
						$parent_item.removeClass('item_end');
					}
				}
				return false;
			}
		});

		//复选框关联事件
		$CIcheck.click(function(){

			var $this = $(this);
			var $Icheck_ico = $this.children('i');
			var $Icheck_input = $this.children('input');

			var $parent_items = $this.parents('.tree_item');
			var $parent_wraps = $this.parents('.tree_wrap');

			var $parent_Icheck = $this.parents('.tree_wrap').prev('.tree_item').find('.CIcheck');
			var $parent_Icheck_ico = $parent_Icheck.children('i');
			var $parent_Icheck_input = $parent_Icheck.children('input');

			if ( $n.hasClass('killer') ) {
				//先判断子级有没有选中，则继续，否则不能执行点击事件
				if ( $parent_items.next('.tree_wrap').find('.CIcheck.on').length == 0 ) {

					if ( $this.hasClass('on') ) {
						$this.removeClass('on');
						$Icheck_input.removeAttr('checked');

						for ( var i = 0; i < $parent_wraps.length; i ++ ) {
							if ( $parent_wraps.eq(i).find('.CIcheck.on').length == 0 ) {
								$parent_wraps.eq(i).prev('.tree_item').find('.CIcheck').removeClass('disabled');
							}
						}

					}else {
						$this.addClass('on');
						$Icheck_ico.hide().show(200);
						$Icheck_input.attr('checked','checked');

						$parent_Icheck.addClass('disabled');
						$parent_Icheck.removeClass('on');
						$parent_Icheck_input.removeAttr('checked');
					}


				}
			}else {
				var $children_Icheck = $parent_items.next('.tree_wrap').find('.CIcheck');
				var $children_Icheck_ico = $children_Icheck.children('i');
				var $children_Icheck_input = $children_Icheck.children('input');

				if ( $this.hasClass('on') ) {
					$this.removeClass('on');
					$Icheck_input.removeAttr('checked');

					$children_Icheck.removeClass('on');
					$children_Icheck_input.removeAttr('checked');
				}else {
					$this.addClass('on');
					$Icheck_ico.hide().show(200);
					$Icheck_input.attr('checked','checked');

					$children_Icheck.addClass('on');
					$children_Icheck_ico.hide().show(200);
					$children_Icheck_input.attr('checked','checked');
				}

			}
			return false;
		});

	});


	//跳页
		$('.CJpage input').keyup(function(event){
			this.value=this.value.replace(/\D/g,'');
			if(event.keyCode > 57 || event.keyCode < 48) {
				TipsShow('输入指令<strong>"不能"</strong>是字母','warning');
			}
		});

	//表单提交
		$list_form.on('click','a.f_submit',function(){
			$list_form.attr('action',$(this).attr('formAction'));
			$list_form.submit();
		})

	//初始化要隐藏的对象
		$tool_extend.hide();
	});
	//-->
	</script>
</#macro>