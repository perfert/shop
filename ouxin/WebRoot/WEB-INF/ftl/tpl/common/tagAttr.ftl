${("" != id!?trim)?string(' id="' + (id!) + '"', '')}
${("" != name!?trim)?string(' name="' + (name!) + '"', '')}
${("" != class!?trim)?string(' class="' + (class!) + '"', '')}
${("" != style!?trim)?string(' style="' + (style!) + '"', '')}
${("" != width!?trim)?string(' width="' + (width!) + '"', '')}
${("" != height!?trim)?string(' height="' + (height!) + '"', '')}
${("" != title!?trim)?string(' title="' + (title!) + '"', '')}
${("" != data!?trim)?string(' data="' + (data!) + '"', '')}
<#if "" !=eles!?trim><@tagEles items=eles /></#if>