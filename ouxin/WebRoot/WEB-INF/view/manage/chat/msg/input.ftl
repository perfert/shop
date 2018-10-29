<#include '../../common/tpl.ftl' />

<@mngTpl>	
	<@head_input />

	<body class="no-skin">
		<@container>
			<@breadcrumbs items={"${ctxPath}/manage/chat/record/list":"聊天记录列表", "#":"详情"} />
					
			<@content>
				<@form prefix="${ctxPath}/manage/security/user/">
				<div class="form-group am-form-group">
					<label class="col-sm-2 control-label no-padding-right">消息类型：</label>
					<div class="col-sm-9">
						<input name="bean.account" value="<#if bean.type == 0>文本消息
							<#elseif bean.type == 1>图片
							<#elseif bean.type == 2>视频
							<#elseif bean.type == 3>位置
							<#elseif bean.type == 4>语音
							<#elseif bean.type == 5>文件
							</#if>" type="text" readonly
						 data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12"/>
					</div>
				</div>
				<div class="space-4"></div>
				
				<div class="form-group am-form-group">
					<label class="col-sm-2 control-label no-padding-right">发送人：</label>
					<div class="col-sm-9">
						<input name="bean.account" value="${bean.fromAccount}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))}  data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12"  />
					</div>
				</div>
				<div class="form-group am-form-group">
					<label class="col-sm-2 control-label no-padding-right">接收人：</label>
					<div class="col-sm-9">
						<input name="bean.toAccount" value="${bean.toAccount}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))} data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12"  />
					</div>
				</div>
				
				<div class="form-group am-form-group">
					<label class="col-sm-2 control-label no-padding-right">内容：</label>
					<div class="col-sm-9">
						<input name="bean.account" value="${bean.content}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))} placeholder="内容" title="内容" data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12" required />
					</div>
				</div>
				
				<#if bean.thumb??>
					<div class="form-group am-form-group">
						<label class="col-sm-2 control-label no-padding-right">图片：</label>
						<div class="col-sm-9">
							<img src="${ctxPath}/${bean.thumb}" height="120"  style="border-radius:5px;border:1px solid #bbb" />
						</div>
					</div>
				</#if>
				
				<#if bean.uri??>
					<div class="form-group am-form-group">
					<label class="col-sm-2 control-label no-padding-right">路径：</label>
					<div class="col-sm-9">
						<input name="bean.account" value="${bean.uri}" type="text" class="col-xs-3 tooltip-info"  ${(bean???string('readonly', ''))}  data-rel="tooltip"  data-placement="right" minlength="5" maxlength="12"  />
					</div>
				</div>
				</#if>
				
				</@form>
			</@content>
		</@container>
		
		<@end_input />
	</body>
</@mngTpl>
