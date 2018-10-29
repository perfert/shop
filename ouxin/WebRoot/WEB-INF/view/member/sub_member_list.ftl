<#if pager?? && 0 lt pager.result!?size>
	<#list pager.result as item>
		<li class="CartItem">
			<a href="javascript:;"><img class="gimg ItemImg" src="${("" != (item.logo!'')?trim)?string(item.logo, ctxPath + '/resource/web/styles/storewap/img/common/ico-member.png')}" style="border-radius:5px;" /></a>
			<div class="ginfo">
				<p class="gname" style="margin:0.2.5rem 0;">
					<i class="${(4==item.level)?string('grade5', (3==item.level)?string('grade3', (2==item.level)?string('grade4', 'grade6')))}" style="margin-right:0.5rem;"></i> ${(4==item.level)?string('普通会员', (3==item.level)?string('银卡会员', (2==item.level)?string('金卡会员', '渠道合伙人')))}
					<a href="${ctxPath}/weichat/${(item.id)!'0'}/qrcode" id="addSubAccount"><img src="${ctxPath}/resource/web/styles/storewap/img/member/index/qrcode.png" style="height:1.8rem; vertical-align:top;"/>推广二维码</a> 
				</p>
				<p class="gname" style="margin:0.2.5rem 0;">
					<i class="am-icon-user am-icon-sm" style="padding-left:0.6rem; padding-right:0.5rem;"></i> ${item.nickName}
					<#if item.isSub>
						<img src="${ctxPath}/resource/web/styles/storewap/img/member/index/money.png" style="height:1.8rem; vertical-align:top;"/> ${(item.money - item.frozenMoney)!0}
						<img src="${ctxPath}/resource/web/styles/storewap/img/member/index/my_money.png" style="height:1.3rem; vertical-align:middle; margin-top:-3px;"/> ${(item.subMoney - item.frozenSubMoney)!0}
						<img src="${ctxPath}/resource/web/styles/storewap/img/member/index/logo-points.png" style="height:1.8rem; vertical-align:top;"/> ${(item.gold - item.frozenGold)!0}
						<img src="${ctxPath}/resource/web/styles/storewap/img/member/index/yong.png" style="height:1.6rem; vertical-align:top;"/> ${item.brokerageAll!0}
					</#if>
				</p>
				<p class="gname" style="margin:0.2.5rem 0;"><i class="am-icon-clock-o am-icon-sm" style="padding-left:0.6rem; padding-right:0.5rem;"></i> ${item.subscribeDate?string("yy-MM-dd")} </p>
			</div>
			<div class="clearperfect"></div>
		</li>
	</#list>
</#if>