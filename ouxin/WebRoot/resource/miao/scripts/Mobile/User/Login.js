$(function(){var u=$("#lUserName"),f=$("#lPassword"),e=$("#lVerify"),n=$("#btnNext"),i=$("#vimg"),t=function(){var r=u.val(),o=f.val(),s=e.val();if(r.length==0||o.length==0||s.length==0){$.PageDialog.fail("信息输入不完整.");return}n.addClass("grayBtn").unbind("click");sendJsonp("/User/Login",{username:r,password:o,verify:s},function(r){if(r.result==0){$.PageDialog.ok(r.desc,function(){loginRedirect()});return}$.PageDialog.fail(r.desc);n.removeClass("grayBtn").bind("click",t);i.click()},function(){n.removeClass("grayBtn").bind("click",t);i.click()})},r;n.bind("click",t);wxCode.length>0&&(r=function(){var n=$.jsonp({url:SiteConfig.apiHost+"/User/WxLogin",data:{code:wxCode},timeout:1e4,callbackParameter:"callback",type:"jsonp",dataType:"jsonp",success:function(n){if(n.result==0){$.cookie("wxId",n.data,{path:"/"});return}}})},$.PageDialog.confirm("是否使用微信自动登录?",function(){location.href="http://m.yygou.cn/User/WxLogin?state=auto&code="+wxCode},r,"自动登录","手动登录"))})