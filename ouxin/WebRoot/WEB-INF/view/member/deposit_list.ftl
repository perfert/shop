<#if pager?? && 0 lt pager.result!?size>
	<#list pager.result as item>
		<tr>
    	 	<td>${(-(item.cash + item.free))?string(",##0.00")}</td>
       		 <td>${(item.beforeCash)?string(",##0.00")}</td>
            <td>${item.remark}</td>
            <td>${item.modifyDate?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>${(0 == item.depositState)?string('待处理', (1 == item.depositState)?string('进行中', (2 == item.depositState)?string('成功', (-1 == item.depositState)?string('已取消', (-2 == item.depositState)?string('失败', (-3 == item.depositState)?string('已关闭', '--'))))))}</td>
        </tr>
	</#list>
</#if>