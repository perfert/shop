
<#include 'head.ftl' />
<#include 'header.ftl' />
<#include 'left.ftl' />
<#include 'breadcrumbs.ftl' />
<#include 'footer.ftl' />
<#include 'end.ftl' />

<#-- 
	manage template.
	@author: MAS 
-->
<#macro mngTpl>
<!DOCTYPE html>
<html lang="cn">

<#nested />

</html>
</#macro>

<#--
	main-container
	@author: MAS
-->
<#macro container>
		<@header />

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">

			<@left />
			
			<div class="main-content">
				<div class="main-content-inner">
					
					<#nested />
					
				</div>
			</div><!-- /.main-content -->

			<@footer />
		</div><!-- /.main-container -->
</#macro>

<#--
	page-content
	@author: MAS
-->
<#macro content>
					<div class="page-content" style="padding-bottom:0;">
						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<#nested />
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content -->
</#macro>

<#--
	data table page
	@author: MAS
-->
<#macro dataTable pager prefix formId="">
								<div class="row">
									<div class="col-xs-12">
								
										<!-- div.table-responsive -->
								
										<!-- div.dataTables_borderWrap -->
										<div>
											<form action="${prefix!}list" method="post" id="${formId!''}">
												<div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
													
													<#nested pager, prefix/>
													
													<div class="row" style="padding-bottom:0;">
														<div class="col-xs-6">
															<div class="dataTables_info" id="dynamic-table_info" role="status" aria-live="polite">
																<label style="float:left; line-height:26px;">共 <span style="color:Red;">${pager.totalCount!}</span> 条记录，当前第 <span style="color:Red;">${pager.pageNo!}</span> / <span style="color:Red;">${pager.totalPageNo!}</span> 页，每页显示 
																	<select size="1" name="pager.pageSize" aria-controls="sample-table-2" onchange="$(this).parents('form').submit();">
																		<option value="15" ${(15 == pager.pageSize) ?string('selected="selected"', '')}>15</option>
																		<option value="50" ${(50 == pager.pageSize) ?string('selected="selected"', '')}>50</option>
																		<option value="100" ${(100 == pager.pageSize) ?string('selected="selected"', '')}>100</option>
																	</select> 行
																</label>
															</div>
														</div>
														<div class="col-xs-6">
															<div class="dataTables_paginate paging_bootstrap" id="dynamic-table_paginate">
																<ul class="pagination">
																	<li class="first ${pager.hasPrePageNo()?string('', 'disabled')}"><a href="${pager.hasPrePageNo()?string((prefix!'') + 'list', 'javascript:void(0);')}"><i class="ace-icon fa fa-angle-double-left"></i></a></li>
																	<li class="prev ${pager.hasPrePageNo()?string('', 'disabled')}"><a href="${pager.hasPrePageNo()?string((prefix!'') + 'list/' + pager.prePageNo, 'javascript:void(0);')}"><i class="ace-icon fa fa-angle-left"></i></a></li>
																	<li>
																		<label style="float:left;">
																			<input type="text" name="pager.pageNo" value="${pager.pageNo!}" max="${pager.totalPageNo}" style="padding:5px 4px 4px 4px; border-color:#d9d9d9;" size="5" />
																		</label>
																	</li>
																	<#--<li class="paginate_button active"><a href="#">1</a></li>-->
																	<li class="next ${pager.hasNextPageNo()?string('', 'disabled')}"><a href="${pager.hasNextPageNo()?string((prefix!'') + 'list/' + pager.nextPageNo, 'javascript:void(0);')}"><i class="ace-icon fa fa-angle-right"></i></a></li>
																	<li class="last ${pager.hasNextPageNo()?string('', 'disabled')}"><a href="${pager.hasNextPageNo()?string((prefix!'') + 'list/' + pager.totalPageNo, 'javascript:void(0);')}"><i class="ace-icon fa fa-angle-double-right"></i></a></li>
																</ul>
															</div>
														</div>
													</div>
												</div>
												
												<div id="dialog-confirm" class="hide">
													<div class="alert alert-info bigger-110">
														数据删除后，将无法恢复！
													</div>
				
													<div class="space-6"></div>
				
													<p class="bigger-110 bolder center grey">
														<i class="ace-icon fa fa-hand-o-right blue bigger-120"></i>
														确定要删除吗？
													</p>
												</div>
												
											</form>
											
										</div>
									</div>
								</div>
</#macro>

<#--
	search page
	@author: MAS
-->
<#macro search prefix>
	<div class="row">
		<div class="col-sm-8" style="width:auto;">
			<div class="visible-md visible-lg hidden-sm hidden-xs action-buttons">
				<a class="blue" href="${prefix!}create"> <i class="fa fa-plus-circle bigger-280"></i></a>
			</div>
		</div>
		<div class="col-sm-8" style="width:auto;">
			<div class="dataTables_filter">
				<#nested prefix/>
				<label><button class="btn btn-purple btn-sm" type="submit"><i class="ace-icon fa fa-search bigger-110"></i>搜 索</button></label>
				<label><button class="btn btn-light btn-sm" type="reset"><i class="ace-icon fa fa-undo bigger-110"></i>清 空</button></label>
			</div>
		</div>
	</div>
</#macro>

<#--
	thead page
	@author: MAS
-->
<#macro thead>
													<div class="dataTables_scrollHead">
														<div class="dataTables_scrollHeadInner">
															<table class="table table-striped table-bordered table-hover dataTable no-footer">
																<thead>
																	<tr role="row">
																		<#nested />
																	</tr>
																</thead>
															</table>
														</div>
													</div>
</#macro>

<#--
	tbody page
	@author: MAS
-->
<#macro tbody pager prefix>
													<div class="dataTables_scrollBody" style="position: relative; overflow: auto; max-height: 570px;">
														<table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer">
															<tbody>
																<#if pager?? && 0 lt pager.result!?size>
																	<#list pager.result as item>
																		<#nested item, pager, prefix/>
																	</#list>
																<#else>
																	<tr class="odd"><td class="red">无数据！</td></tr>
																</#if>
															</tbody>
														</table>
													</div>
</#macro>

<#--
	data operator by list page
	@author: MAS
-->
<#macro operator item prefix width='10'>
													<td class="op" style="width:${width!10}%;">
														<div class="hidden-sm hidden-xs action-buttons">
															<#if 0 lte item.state>
															<a class="green" href="${prefix!}${(item!).id!}"> <i class="ace-icon fa fa-pencil bigger-130"></i></a>
															<a class="delete-dialog red" href="javascript:void(0);" link="${prefix!}del/${(item!).id!}" > <i class="ace-icon fa fa-trash-o bigger-130"></i></a>
															</#if>
															<#nested item, item, prefix/>
														</div>
													</td>
</#macro>

<#--
	input page form
	@author: MAS
-->
<#macro form prefix id="" class="">
								<form action="${prefix!}${(bean?? && bean.id ??) ? string((bean!).id!, 'create')}" method="post" class="form-horizontal ${class!}" role="form" enctype="multipart/form-data" data-am-validator id="${id!}">
									<input type="hidden" name="bean.id" value="${bean.id!}" />
									<input type="hidden" name="id" value="${bean.id!}" />
									<!-- #section:elements.form -->
									<div class="space-10"></div>
									<#nested />
									<div class="space-4"></div>
									<div class="clearfix form-actions">
										<div class="col-md-offset-3 col-md-9">
											<button ${(bean?? && ("-1" == bean.state?trim))?string('disabled', '')} class="btn btn-info ${(bean?? && ("-1" == bean.state?trim))?string('disabled', '')}" type="${(bean?? && ("-1" == bean.state?trim))?string('botton', 'submit')}">
												<i class="ace-icon fa fa-check bigger-110"></i>
												${(bean?? && bean.id ??) ? string('修 改', '新 增')}
											</button>

											&nbsp; &nbsp; &nbsp;
											<button class="btn" type="reset" disabled>
												<i class="ace-icon fa fa-undo bigger-110"></i>
												重 置
											</button>
										</div>
									</div>
								</form>
</#macro>