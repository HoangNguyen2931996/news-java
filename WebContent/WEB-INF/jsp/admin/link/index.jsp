<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid">
	
	<!-- Title -->
	<div class="row heading-bg">
		<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
		  <h5 class="txt-dark">Link Resource</h5>
		</div>
		<!-- Breadcrumb -->
		
		<!-- /Breadcrumb -->
	</div>
	<!-- /Title -->
	<div class="row">
		<!-- Bordered Table -->
		<div class="col-sm-12">
			<div class="panel panel-default card-view">						
				<div class="panel-wrapper collapse in">
					<div class="panel-body">
						<c:if test="${not empty msg }">
							<h6 class="txt-dark capitalize-font"><i class="zmdi zmdi-info-outline mr-10"></i>${msg }</h6>
							<hr class="light-grey-hr"/>
						</c:if>
						<p class="text-muted">
							<a class="btn btn-success btn-anim" href="${pageContext.request.contextPath }/admin/link/add">
								<span>Add</span>
							</a>
						</p>
						<div class="table-wrap mt-40">
							<div class="table-responsive">
							  <table class="table table-hover table-bordered mb-0 tree">
								<thead>
								  <tr>
									<th>Linh resource</th>
									<th>Nguồn tin</th>
									<th>Danh mục</th>
									<th>Load</th>
									<th class="text-nowrap">Chức năng</th>
								  </tr>
								</thead>
								<tbody>
								<c:forEach items="${alLink }" var="objLink">
								<c:set var="urlEdit" value="${pageContext.request.contextPath }/admin/link/edit/${objLink.id }"></c:set>
								<c:set var="urlDel" value="${pageContext.request.contextPath }/admin/link/del/${objLink.id }"></c:set>
									  <tr>
										<td>${objLink.link }</td>
										<td>${objLink.nameResource }</td>
										<td>${objLink.nameCat }</td>
										<th style="align-content: center"><a href="javascript:void(0)" onclick="load(${objLink.id})" id="img_${objLink.id}"><img style="width: 20px; height: 20px" src="${defines.URL_ADMIN }/img/load.png" /></a></th>
										<td class="text-nowrap">
											<a href="${urlEdit }" class="text-inverse pr-10" title="" data-toggle="tooltip" data-original-title="Edit"><i class="zmdi zmdi-edit txt-warning"></i></a>
											<a href="${urlDel }" class="text-inverse" title="" data-toggle="tooltip" data-original-title="Delete"><i class="zmdi zmdi-delete txt-danger"></i></a>
										</td>
									  </tr>
								</c:forEach>
								</tbody>
							  </table>
							  <script type="text/javascript">
								  	function load(IdLink){
								  		var img = $('#img_'+IdLink+' img');
								  		img.attr("src", img.attr('src').replace('${defines.URL_ADMIN }/img/load.png', '${defines.URL_ADMIN }/img/wait.png'));
							 			$.ajax({
											url: '${pageContext.request.contextPath }/admin/link/load',
											type: 'POST',
											cache: false,
											data: {
												idLink: IdLink,
											},
											success: function(data){
												img.attr("src", img.attr('src').replace('${defines.URL_ADMIN }/img/wait.png', '${defines.URL_ADMIN }/img/load.png'));
											},
											error: function (){
											}
										});
							 		} 
							  </script>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	
</div>

