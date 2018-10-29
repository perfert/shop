<#macro inputHeadScript>
	<@o.script srcs=["${base}/res/js/oap.js", "${base}/res/js/verifyFrame.js","${base}/bank/js/lib/plugins/kindedit/kindeditor-min.js"] />
</#macro>

<#macro inputBody>
	<body class="content_frame">
		<#nested />
		<@userTips />
		<@dialog />
	</body>
</#macro>

<#macro inputTab on=false>
	<div class="content_tab Ccontent_tab">
		<#nested />
		<i class="tab_arrow"></i>
	</div>
	<div class="tab_area"></div>
</#macro>

<#macro inputTabItem on=false name="">
	<a <#if true==on>class="on"</#if> href="javascript:;">${name}<em>${name}</em><b></b></a>
</#macro>

<#macro inputForm name="" action="" method="post" enctype="" id="">
	<form class="input_form" name="${name}" action="${action}" method="${method}" enctype="${enctype!}" id="${id}">
		<#nested />
		<@inputFooter />
	</form>
</#macro>

<#macro inputTabCon>
	<div class="content_con Ccontent_con">
		<#nested />
	</div>
</#macro>

<#macro inputTabConItem>
	<div class="con_item Ccon_item">
		<div class="item_wrap">
			<#nested />
		</div>
	</div>
</#macro>

<#macro inputTable>
	<table class="info_table">
		<#nested />
	</table>
</#macro>

<#macro inputFooter>
	<div class="content_footer data_footer Ccontent_footer">
		<a href="javascript:;" class="Abtn"><span><cite class="form_submit">确 定</cite></span></a>
		<a href="javascript:history.go(-1);" class="Abtn"><span><cite class="form_cancel">取 消</cite></span></a>
	</div>
</#macro>




<#macro inputScript>

	<script type="text/javascript">
	<!--
	
	//code by LGL
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
					formAction = formAction + delete_data[i] + ',';
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
		
		
	
	$(function(){

	




	//选项卡切换
		//需要的DOM对象
		var $tab_bg = $('.Ccontent_tab a b');
		var $tab_items = $('.Ccontent_tab a');
		var $tab_arrow = $('.Ccontent_tab .tab_arrow');
		var $tab_arrow_w = $tab_arrow.width();
		var $tab = $('.Ccontent_tab');
		var $tab_area = $('.tab_area');
		var $tab_con = $('.Ccontent_con');
		var $con_items = $('.Ccontent_con .Ccon_item');
		var $item_shadow = $('.con_Lshadow,.con_Rshadow')
		var $Ccontent_footer = $('.Ccontent_footer');

		//初始化start
		//需要的变量
		var cfH = $Ccontent_footer.height();
		var $tab_on = $('.Ccontent_tab .on');

		//选项卡标签箭头位置
		if ( $tab_on.offset() ) {
			$tab_arrow.css('left',$tab_on.offset().left + $tab_on.outerWidth(true) / 2 - $tab_arrow_w / 2);
		}

		//内容框的高度自适应！
		$tab_con.height($(window).height() - $tab.height() - cfH);
		function itemsFix(){
			for ( var i = 0; i < $con_items.length; i ++ ) {
				if ( $con_items.eq(i).height() < $(window).height() ) {
					$con_items.eq(i).height($(window).height());
				}
			}
		}
		itemsFix();

		//哪个内容框显示
		function itemPos(){
			for ( var i = 0; i < $con_items.length; i ++ ) {
				if ( i == $('.Ccontent_tab .on').index() ) {
					$con_items.eq(i).css('left',0);
				}else {
					$con_items.eq(i).hide();
				}
			}
		}

		//初始化end

		//动态交互
		//窗口事件
		$(window).resize(function(){
			var $tab_on = $('.Ccontent_tab .on');
			var winH = $(window).height();

			$tab_arrow.css('left',$tab_on.offset().left + $tab_on.outerWidth(true) / 2 - $tab_arrow_w / 2);
			$tab_con.height(winH - $tab.height() - cfH);
			itemsFix();
		});

		//选项卡滚动
		var tab_h = parseInt($tab.height());
		var tab_mh = tab_h / 4;

		$tab_con.scroll(function(){
			$tab.stop();
			$tab.children().stop();
			$tab_area.stop();

			if ( $(this).scrollTop() > 0 ) {
				$tab.animate({height:tab_mh},400);
				$tab.children().animate({opacity:0},400);
				$tab_area.animate({height:tab_mh},400);
				$tab_con.height($(window).height() - tab_mh - cfH);
			}
			if ( $(this).scrollTop() == 0 ) {
				$tab.animate({height:tab_h},400);
				$tab.children().animate({opacity:1},400);
				$tab_area.animate({height:tab_h},400);
			}
		});

		//选项卡切换
		$tab.hover(
			function(){
				if ( !$tab.is(":animated") && $tab_con.scrollTop() > 0 ) {
					$tab.animate({height:tab_h},280);
					$tab.children().animate({opacity:1},280);
				}
			},
			function(){
				if ( !$tab.is(":animated") && $tab_con.scrollTop() > 0 ) {
					$tab.animate({height:tab_mh},280);
					$tab.children().animate({opacity:0},280);
				}
			}
		);

		$.each($tab_items,function(i,n){
			var $n = $(n);
			$n.click(function(){
				var winW = $(window).width() - 27;	//27是滚动条的宽度
				var checkOn = $('.Ccontent_tab .on').index();

				if ( $n.hasClass('on') ) {
					return false;	//如果点击同一对象则遏制渐现动画
				}

				if ( ! $con_items.is(":animated") && ! $n.hasClass('on') ) {
					//选项卡标签渐现
					$tab_items.removeClass('on');
					$tab_bg.animate({opacity:'hide'},300);
					$n.find('b').animate({opacity:'show'},300);
					$n.addClass('on');
					$tab_arrow.animate({left:$n.offset().left + $n.outerWidth(true) / 2 - $tab_arrow_w / 2},300);

					//选项卡内容切换
					if ( checkOn < i ) {
						$con_items.eq(i).css('left',winW);
						$con_items.eq(checkOn).animate({left:-winW,opacity:'hide'},600);
						$con_items.eq(i).animate({left:0,opacity:'show'},600);
					}else {
						$con_items.eq(i).css('left',-winW);
						$con_items.eq(checkOn).animate({left:winW,opacity:'hide'},600);
						$con_items.eq(i).animate({left:0,opacity:'show'},600);
					}
				}

			});
		});


	//控件聚焦与失焦动作
		var $widget_frame = $('.Itext,.Ibox');
		var $widget_input = $('.Itext input');
		var $widget_textarea = $('.Ibox textarea');
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
		$widget_textarea.focus(function(){
			$(this).parents('.Ibox').addClass('on');
		});
		$widget_textarea.blur(function(){
			$(this).parents('.Ibox').removeClass('on');
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

				if(_value === ""){
					$inputText.val('请选择');
				}else{
					var selectOption = _this.find('li>a.on'),_txt =selectOption.html();
					if(_txt && _txt !=""){
						$inputText.val(selectOption.html());
					}else{
						$inputText.val('请选择');
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
				if($Iselect_value.val() === _value) return false;

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

	//文本域模拟
		function Itextarea_fixed(){
			$.each($('.Ibox'),function(i,n){
				var $n = $(n);
				var $ITAborderX = $n.children('.ITAborderX');
				var $ITAborderY = $n.children('.ITAborderY');
				var $old_w = $n.width();
				var $old_h = $n.height();
				ITA_fixed($n,$ITAborderX,$ITAborderY);
				$n.mousemove(function(){
					ITA_fixed($n,$ITAborderX,$ITAborderY);
				});
				$n.dblclick(function(){
					if ( $old_w != $n.width() ) {
						$n.children('textarea').width($old_w).height($old_h);
						$ITAborderX.width($old_w);
						$ITAborderY.height($old_h);
					}
				});
			});
			function ITA_fixed($o,$x,$y){
				if ( $x ) {
					$x.width( $o.width() );
				}
				if ( $y ) {
					$y.height( $o.height() );
				}
			}
		}
		Itextarea_fixed();

	//验证、提示
		var $tips_frame = $('.tips_frame');
		var $tips_text = $('.tips_frame .tips_text');
		var $tips_close = $('.tips_frame .close');
		var $tips_back = $('.tips_frame .back');
		var $form_submit = $('.form_submit');
		var $form_cancel = $('.form_cancel');
		var TFbottom = parseInt( $tips_frame.css('bottom') ) + $tips_frame.height();
		var AutoClose = 0;

		nv = new oap.app.verifyFrame({
			elem:$('.input_form'),
			SBtnSel:'.form_submit',
			rules:{
				empty:{
					require:true
				},
				number:{
					regx:/^[0-9]*$/
				},
				empty_number:{
					require:true,
					regx:/^[0-9]*$/
				}
			},
			message:{
				empty:{
					require:'不能为空！'
				},
				number:{
					regx:'只能输入0-9的数字'
				},
				empty_number:{
					require:'不能为空！',
					regx:'只能输入0-9的数字'
				}
			},
			success:function(){
				this.submit();
			}
		});
		nv.dealMsg = function(obj,str,type){
			if ( type === 'wrong' ) {
				obj.parents('span').addClass('error');
				obj.next('.ICselect').addClass('error');
				TipsShow(str,'error');
			}else {
				obj.parents('span').removeClass('error');
				TipsHide();
			}
		};
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
		function TipsShow(tipsText,tipsType){
			TFinit();
			$tips_frame.removeClass('error,right,warning');
			$tips_frame.addClass(tipsType);
			if ( !$tips_frame.is(":animated") ) {
				$tips_text.html(tipsText);
				$tips_frame.stop();
				$tips_close.stop();
				if ( tipsType == 'error' ) {
					AutoClose = 0;
				}else {
					AutoClose = 1;
				}
				$tips_frame.queue('aniList',aniFun);
				runAni();
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

		//错误跟踪
		$form_submit.click(function(){
			tracing();
		});
		$tips_back.click(function(){
			tracing();
		});

		function tracing(){
			var vItems = $('span.error').first();

			if ( ! $tab_con.is(":animated") && vItems.length > 0 ) {	//第一次点击按钮还没检测到错误对象，悲剧啊！
				var $tab_on = $tab_items.eq(vItems.parents('.Ccon_item').index());
				$tab_on.click();
				var _offsetTop = vItems.offset().top - $tab.height() + $tab_con.scrollTop();
				$tab_arrow.animate({left:$tab_on.offset().left + $tab_on.outerWidth(true) / 2 - $tab_arrow_w / 2},300);
				$tab_con.animate({'scrollTop':_offsetTop},400);
			}

		}

	//富文本编辑框 kindeditor
		//kindeditor 功能配置
		var keItmes= {
			simple:['fullscreen','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons', 'image', 'link']
			,default:''
		};
		var $kds = $('textarea.kindeditor'),kdObject={};
		KindEditor.ready(function(K) {
			$.each($kds,function(i,n){
				var _id = n.id,_name = n.name,_this = $(n),_width=_this.width(),_height=_this.height(),_uploadJson=_this.attr('uploadJson'),_keType=_this.attr('keType');
				kdObject[_id] = K.create('#'+ _id, {
					width : _width,
					height : _height,
					resizeType : 1,
					allowPreviewEmoticons : false,
					allowImageUpload : true,
					imageTabIndex : 1,
					uploadJson: _uploadJson,
					items : keItmes[_keType],
					afterChange: function() {
						this.sync();
					}
					,afterUpload:function(data){
						var _url = "";
						if(data.error && data.error === 0){
							_url = data.url;
						}else{
							_url = data;
						}
						_this.parents('form').append('<input type="hidden" name="attachments" value="'+ _url +'" />');
					}
				});
			});
		});

	//图片缩放
		$.fn.LGL_image_fixed = function(options) {
			var defaults = {};
			var opts = $.extend(defaults, options);

			$this = $(this);
			$this.css('textAlign','center');

			window.onload = function(){
				$.each($this,function(i,n){

					var oImg = $(n).find('img');
					var temp_img = new Image();
					temp_img.src = oImg.attr('src');

					var Fwidth = $(n).width();
					var Fheight = $(n).height();

					oImg.parent('a').css({ 'display' : 'block' , 'width' : '100%' , 'height' : '100%' });

					var Img_width = temp_img.width;
					var Img_height = temp_img.height;

					var scale = 0;
					var scale_width = Img_width;
					var scale_height = Img_height;

					if ( Img_width > 0 &&  Img_height > 0 ) {
						if ( Img_width / Img_height >= Fwidth / Fheight ) {
							if ( Img_width > Fwidth ) {
								scale_width = Fwidth;
								scale_height = ( Img_height * Fwidth ) / Img_width;
							}else {
								scale_width = Img_width;
								scale_height = Img_height;
							}
						}else {
							if ( Img_height > Fheight ) {
								scale_height = Fheight;
								scale_width = ( Img_width * Fheight ) / Img_height;
							}else {
								scale_width = Img_width;
								scale_height = Img_height;
							}
						}
					}

					oImg.width(scale_width);
					oImg.height(scale_height);

					var x_blank = (Fwidth - oImg.width()) / 2;
					var y_blank = (Fheight - oImg.height()) / 2;

					if ( x_blank < opts.x_blank ) {
						oImg.width(Fwidth);
					}
					if ( y_blank < opts.y_blank ) {
						oImg.height(Fheight);
					}
					if ( oImg.height() < Fheight ) {
						oImg.css('marginTop',y_blank + 'px');
					}

					oImg.show();
				});
			}
		};

		$('.img_fixed').LGL_image_fixed();

		var $upload = $('.upload');
		$.each($upload,function(i,n){
			var $n = $(n);
			var $upload_box = $n.parents('.Ibox');
			var $upload_img = $n.find('.upload_img');
			var $upload_label = $n.find('.upload_label');
			var $upload_text = $n.find('.upload_text');
			var $upload_file = $n.find('.upload_file');
			var $upload_add = $n.find('.upload_add');
			var $upload_edit = $n.find('.upload_edit');
			var $upload_delete = $n.find('.upload_delete');
			var $upload_title = $n.find('.upload_title');
			var $title_text = $upload_title.children('span');
			var $title_input = $upload_title.children('input');

			var titleTips = $title_input[0].defaultValue;

			if ( $upload_add.css('display') != 'block' ) {
				$upload_delete.show();
			}

			$upload_add.click(function(){
				$upload_file.click();
			});

			$upload_edit.click(function(){
				$upload_file.click();
			});

			$upload_delete.click(function(){
				$upload_img.remove();
				$upload_label.hide();
				$upload_text.text('').hide();
				$title_text.text('');
				$title_input.val('');
				$upload_title.hide();
				$upload_delete.hide();
				$upload_add.animate({opacity:'show'},400);
			});

			$upload_file.change(function(){
				if ( $(this).val() != '' ) {
					$upload_img.remove();
					$upload_label.css('display','block');
					$upload_text.text($(this).val()).css('display','block');

					$title_text.text('').hide();
					$title_input.val(titleTips).show();
					$upload_title.animate({opacity:'show'},400);

					$upload_delete.animate({opacity:'show'},400);
					$upload_add.hide();
				}
			});

			$upload_box.dblclick(function(){
				$upload_file.click();
			})

			$upload_title.dblclick(function(){
				return false;
			})

			$title_input.focus(function(){
				if ( $title_input.val() == titleTips ) {
					$title_input.val('');
				}
			})
			$title_input.blur(function(){
				if ( $title_input.val() == '' ) {
					$title_input.val(titleTips);
				}
			})

		});


	//奇葩的树
	var $Itree = $('.Itree');
	$.each($Itree,function(i,n){
		var $n = $(n);
		var $tree_wrap = $n.find('.tree_wrap');
		var $tree_item = $n.find('.tree_item');
		var $tree_label = $n.find('.tree_label');
		var $CIcheck = $n.find('.CIcheck');

		//样式初始化
		$tree_wrap.eq(0).removeClass('tree_wrap').addClass('tree_top');
		for ( var i = 0; i < $tree_wrap.length; i ++ ) {
			var $each = $tree_wrap.eq(i);
			var $parent_arrow = $each.prev('.tree_item').find('.tree_arrow');


			if ( $each.next('.tree_item').length == 0 ) {
				$each.addClass('item_end');
			}
			if ( $each.find('.CIcheck.on').length > 0 ) {
				var $parent_wraps = $each.parents('.tree_wrap');
				//var $parent_items = $parent_wraps.find('.tree_item').first();
				$parent_wraps.show();

				/*
				for ( var j = 0; j < $parent_wraps.length; j ++ ) {
					if ( $parent_wraps.eq(i).hasClass('item_end') ) {
						console.log($parent_wraps.eq(i));
					}
				}
				*/


				//$parent_items.find('.CIcheck').addClass('disabled');

			}else {
				$each.hide();
			}
			if ( $each.css('display') == 'block' ) {
				$parent_arrow.removeClass('close').addClass('open');
			}else {
				$parent_arrow.removeClass('open').addClass('close');
			}
		}
		//此代码必须放到上面循环之后，因为上面的初始化会把根对象隐掉
		$tree_wrap.eq(0).show();
		
		for ( var i = 0; i < $tree_item.length; i ++ ) {
			var $each = $tree_item.eq(i);
			if ( $each.next('.tree_wrap').length == 0 ) {
				$each.find('.tree_arrow').addClass('no_arrow');
			}
		}

		$tree_label.click(function(){
			var $this = $(this);
			var $arrow = $this.children('.tree_arrow');
			var isExtend = 0;
			if ( $arrow.css('display') == 'block' ) {
				isExtend = 1;
			}else {
				isExtend = 0;
			}
			if ( isExtend == 1 ) {
				var $extend = $this.parents('.tree_item').next('.tree_wrap');
				if ( $extend.css('display') == 'block' ) {
					$extend.slideUp();
					$arrow.removeClass('open').addClass('close');
				}else {
					$extend.slideDown();
					$arrow.removeClass('close').addClass('open');
				}
				return false;
			}
		});

		var checked_temp = [];
		$CIcheck.click(function(){

			var $this = $(this);
			var $Icheck_ico = $this.children('i');
			var $Icheck_input = $this.children('input');

			var $parent_item = $this.parents('.tree_item');

			var $parent_Icheck = $this.parents('.tree_wrap').prev('.tree_item').find('.CIcheck');
			var $parent_Icheck_ico = $parent_Icheck.children('i');
			var $parent_Icheck_input = $parent_Icheck.children('input');

			/*
			if ( $n.hasClass('.killer') ) {
			}
			*/

			if ( $parent_item.next('.tree_wrap').find('.CIcheck.on').length == 0 ) {

				if ( $this.hasClass('on') ) {
					$this.removeClass('on');
					$Icheck_ico.hide();
					$Icheck_input.removeAttr('checked');
					$parent_Icheck.removeClass('disabled');
				}else {
					$this.addClass('on');
					$Icheck_ico.hide().show(200);
					$Icheck_input.attr('checked','checked');
					$parent_Icheck.addClass('disabled');

				}
				for ( var i = 0; i < $parent_Icheck.length; i ++ ) {
					if ( $parent_Icheck.eq(i).hasClass('disabled') ) {
						checked_temp.push($parent_Icheck.eq(i));
					}
				}
				console.log(checked_temp);

				$parent_Icheck.removeClass('on');
				$parent_Icheck_ico.hide();
				$parent_Icheck_input.removeAttr('checked');
			}


			return false;
		});

	});




	//选项卡隐藏
		itemPos();


	});

	//-->
	</script>

</#macro>