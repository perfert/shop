<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/product/ad/list":"轮播广告列表", "#":"${(bean?? && bean.id ??) ? string('修改', '新增')}"} />
					
			<@content>
				<@form prefix="${ctxPath}/manage/product/ad/">
					<input type="hidden" name="bean.image" value="${bean.image}" />
				
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">类型：</label>
						<div class="col-sm-9">
							<select name="bean.type" id="bean.brandId" required>
								<option value="">请选择</option>
								<option value="1" ${(bean.type == 1)?string('selected', '')}>首页轮播</option>
								<option value="2" ${(bean.type == 2)?string('selected', '')}>家族首页轮播</option>
								<option value="3" ${(bean.type == 3)?string('selected', '')}>夺宝首页轮播</option>
								<option value="4" ${(bean.type == 4)?string('selected', '')}>年货专区轮播</option>
							</select>
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">标题：</label>
						<div class="col-sm-9">
							<input name="bean.title" value="${bean.title}" type="text" class="col-xs-3 tooltip-info" placeholder="轮播广告标题" title="轮播广告标题（至少2个字符）" data-rel="tooltip"  data-placement="right" minlength="2" required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">链接：</label>
						<div class="col-sm-9">
							<input name="bean.link" value="${bean.link}" type="text" class="col-xs-3 tooltip-info" placeholder="广告链接地址" title="广告链接地址" data-rel="tooltip"  data-placement="right" required/>
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">图片：</label>
						<div class="col-sm-9" style="width:20%;">
							<input name="file" type="file" class="simple-input-file" required/>
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">排序：</label>
						<div class="col-sm-9">
							<input name="bean.priority" value="${(bean!).priority!100}" type="text" class="col-xs-3 tooltip-info" placeholder="排序优先级" title="排序优先级（0～9999）" data-rel="tooltip"  data-placement="right" min="0" max="9999" number required />
						</div>
					</div>
					<div class="space-4"></div>
					
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">状态：</label>
						<div class="radio">
							<#if bean?? && ("-1" == bean.state?trim)>
								<label class="light-grey">
									<input name="bean.state" type="radio" class="ace input-lg" value="-1" checked required />
									<span class="lbl"> 已删除 </span>
								</label>
							<#else>
								<label>
									<input name="bean.state" type="radio" class="ace input-lg" value="1" ${(("" == bean!?trim) || ("" == bean.state?trim) || 1 == bean.state) ? string('checked', '')} required/>
									<span class="lbl"> 开 启 </span>
								</label>
								<label style="margin-left:20px;">
									<input name="bean.state" type="radio" class="ace input-lg" value="0" ${(bean?? && 0 == bean.state) ? string('checked', '')}/>
									<span class="lbl"> 关 闭 </span>
								</label>
							</#if>
						</div>
					</div>
				</@form>
			</@content>
		</@container>
		
		<@end_input>
			
		</@end_input>
	</body>
</@mngTpl>
