<!DOCTYPE html>
<html class="no-js">
<head>
	<title>我的物流</title>
	<meta charset="utf-8">
	<meta http-equiv="Cache-Control" content="no-siteapp"/>
	<meta name="format-detection" content="telephone=no">
	<meta name="format-detection" content="email=no">
	<meta name="format-detection" content="address=no">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<script src="${ctxPath}/resource/web/scripts/jquery/jquery-1.11.2.min.js"></script>
	<script src="${ctxPath}/resource/web/scripts/storewap/common.js"></script>
	<style>
		/*常用*/
		* { margin: 0; padding: 0; -webkit-text-size-adjust: none; }
		html { overflow-y: scroll }
		html, body, input, textarea, select, button { text-align: left; font: 100% "Microsoft YaHei", Verdana, arial, sans-serif; }
		input, textarea, select, button { vertical-align: middle }
		body, ul, ol, li, dl, dd, p, h1, h2, h3, h4, h5, h6, form, fieldset, table { margin: 0; padding: 0 }
		a, a:hover, a:visited, a:active { color: #06c; text-decoration: none; cursor: pointer; }
		img { vertical-align: middle; border: 0 }
		h1, h2, h3, h4, h5, h6 { font-size: 1rem }
		ul, dl { list-style: none }
		.fl { float: left; }
		.fr { float: right; }
		.hidden { display: none }
		.clear { height: 0; clear: both; }
		.relative { position: relative; }
		
		@media screen and (min-width : 768px) {
		html { font-size: 150%; }
		}
		a, img, button, input, textarea { -webkit-tap-highlight-color: rgba(255,255,255,0); }
		input, textarea { -webkit-appearance: none; }
		body { width: 100%; overflow-x: hidden; background-color: #fff; }
		.kd-content{max-width: 800px;margin: auto;}		
		
		.result-list { border-top: 0.0625rem solid #dddddd; }
		.result-list li { display: -webkit-box; -webkit-box-align: center; overflow: hidden; color: #777; border-bottom: 0.0625rem solid #dddddd; }
		.result-list li.last { color: #FE8540; }
		.result-list li .col1, .result-list li .col2, .result-list li .col3 { display: block; }
		.result-list li .col1 { width: 5rem; padding: 0.625rem; text-align: center; font-size: 0.875rem; font-weight: bold; font-family: Helvetica, Arial, sans-serif;}
		.result-list li .col2 { width: 1.25rem; position: relative; }
		.result-list li .col3 { -webkit-box-flex: 1; padding: 0 0.625rem; font-size: 0.875rem; }
		.result-list li .col1 dd { margin-top: 0.25rem; font-size: 1.5rem; }
		/*.result-list li.first .col1 dd{font-size:2rem;}
		.result-list li.last .col1 dd{font-size:2rem;}*/
		.result-list li .col2 .line1, .result-list li .col2 .line2 { position: absolute; z-index: 1; left: 0; width: 0.5rem; height: 10em; border-right: 0.0625rem solid #dddddd; }
		.result-list li .col2 .line1 { top: -10em; }
		.result-list li .col2 .line2 { top: 0; }
		.result-list li .col2 .point { position: absolute; z-index: 2; top: -0.5rem; left: 0; width: 0.9375rem; height: 0.9375rem; border-radius: 0.9375rem; background-color: #fff; border: 0.0625rem solid #dddddd; }
		.result-list li .col2 .point:before { content: ""; position: absolute; top: 0.45rem; left: 0.0625rem; height: 0.125rem; width: 0.5rem; overflow: hidden; background: #ddd; -webkit-transform: rotate(45deg); transform: rotate(45deg); border-radius: .02em; }
		.result-list li .col2 .point:after { content: ""; position: absolute; top: 0.45rem; right: 0.0625rem; height: 0.125rem; width: 0.5rem; overflow: hidden; background: #ddd; -webkit-transform: rotate(135deg); transform: rotate(135deg); border-radius: .02em; }
		.result-list li.last .col2 .point { border: solid 1px #fe8540 }
		.result-list li.last .point:before { background: #fe8540; }
		.result-list li.last .point:after { background: #fe8540; }
		.result-list li.finish { position: relative; }
		.result-list li.finish .col2 .point { background-color: #ef5858; }
		.result-list li.finish .point:before { content: ""; position: absolute; top: 0.5625rem; left: 0.0625rem; height: 0.125rem; width: 0.375rem; overflow: hidden; background: #FFF; -webkit-transform: rotate(45deg); transform: rotate(45deg); border-radius: .02em; }
		.result-list li.finish .point:after { content: ""; position: absolute; top: 0.46rem; right: 0; height: 0.125rem; width: 0.75rem; overflow: hidden; background: #FFF; -webkit-transform: rotate(135deg); transform: rotate(120deg); border-radius: .02em; }
		.result-box .result-btn { display: inline-block; margin: 1rem 0.5rem; padding: 0 1.5rem; height: 2.5rem; line-height: 2.5rem; font-size: 1rem; color: #fff; background-color: #fe8540; border-radius: 0.25rem; }
		.result-fail { padding: 8rem 0 2rem; overflow: hidden; color: #ff7f02; background: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAUAAAACgCAMAAABKfUWuAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyBpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMC1jMDYwIDYxLjEzNDc3NywgMjAxMC8wMi8xMi0xNzozMjowMCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNSBXaW5kb3dzIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjBFNENENUY1RDhFNTExRTRCMjI4RTEyMTc5Q0FGMzBGIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjBFNENENUY2RDhFNTExRTRCMjI4RTEyMTc5Q0FGMzBGIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6MEU0Q0Q1RjNEOEU1MTFFNEIyMjhFMTIxNzlDQUYzMEYiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6MEU0Q0Q1RjREOEU1MTFFNEIyMjhFMTIxNzlDQUYzMEYiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7xYbzNAAAAwFBMVEX09PS/wMTKy83S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") center 2rem no-repeat #fff; background-size: 10rem 5rem; }
		.result-success p, .result-fail p { line-height: 1.8rem; text-align: center; font-size: 0.875rem; color: #333; }
		.iframe-open { position: fixed; z-index: 99; top: 0; left: 0; width: 100%; height: 100%; background-color:#fff; }	
	</style>
</head>

<body>
	<div class="kd-content" id="content">
		<div class="result-success" id="success" style="">
			<ul id="result" class="result-list">
				<#if logistics?? && 200 == logistics.status>
					<#if logistics.data?? && 0 lt logistics.data?size>
						<#list logistics.data as item>
							<li class="${(0 == item_index)?string('first', ((item_index + 1) == logistics.data?size)?string('last finish', ''))}"><div class="col1"><dl><dt>${item.time?substring(0, 10)}</dt><dd>${item.time?substring(11, 16)}</dd></dl></div><div class="col2"><span class="step"><#if 0 lt item_index><span class="line1"></span></#if><#if (item_index + 1) != logistics.data?size><span class="line2"></span></#if><span class="point"></span></span></div><div class="col3">${item.context}</div></li>
						</#list>
					</#if>
				<#else>
					<li><div class="col1">-</div><div class="col3">${(logistics.message)!'单号不存在或者已经过期'}</div></li>
				</#if>
			</ul>
		</div>
	</div>
	
	<div class="tips_box TipsBox"><p class="TipsCon"></p></div>
	
	<script type="text/javascript">
		$(function(){
			(function(){
				var $window = $(window);
				var $WapToolbar = $('.WapToolbar');
				var $GoToTop = $('.GoToTop');
				$window.scroll(function(){
					scrollTopCheck();
				});
		
				function scrollTopCheck(){
					if ( $window.scrollTop() > $window.height() ) {
						$GoToTop.show();
					}else {
						$GoToTop.hide();
					}
				}
				scrollTopCheck();
		
				$GoToTop.click(function(){
					$window.scrollTop(0);
				});
			})();
		});
	</script>
</body>
</html>