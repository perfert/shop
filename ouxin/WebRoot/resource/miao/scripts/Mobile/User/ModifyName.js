$(function(){getUserInfo(function(n){var t,i;for(t in n)i=$("[data-userinfo='u_"+t+"']"),i.html(n[t])},function(){location="../../../User/Login.htm"/*tpa=http://m.yygou.cn/User/Login*/});var i=$("#txtName"),r=$("#txtPassword"),n=$("#btnSubmit"),u=function(n){if(n.result!=0){$.PageDialog.fail(n.desc);return}$.PageDialog.ok(n.desc,function(){location="../../../User.htm"/*tpa=http://m.yygou.cn/User*/})},t=function(){var f=i.val(),e=r.val();if(f.length<3){$.PageDialog.fail("名字至少三个字哦.");return}if(e.length<6){$.PageDialog.fail("密码不能少于6位.");return}n.addClass("grayBtn").unbind("click").text("正在提交...");sendJsonp("/User/ModifyName",{nname:f,password:e},u,null,function(){n.removeClass("grayBtn").on("click",t).text("保 存")})};n.on("click",t)})