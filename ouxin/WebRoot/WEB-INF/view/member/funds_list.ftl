<#if pager?? && 0 lt pager.result!?size>
	<#list pager.result as item>
		<tr>
    	 	<td>${(item.cash)?string(",##0.00")}</td>
       		 <td>${(item.beforeCash + item.cash)?string(",##0.00")}</td>
            <td>${item.remark}</td>
            <td>${item.modifyDate?string("yyyy-MM-dd HH:mm:ss")}</td>
        </tr>
	</#list>
</#if>