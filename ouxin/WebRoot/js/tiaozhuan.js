var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
function tz(){
    if(isAndroid){
        window.location.href="http://www.qq.com/";//安卓跳转的地址
    }
    if(isiOS){
        window.location.href="https://www.hao123.com/";//ios跳转的地址
    }
}