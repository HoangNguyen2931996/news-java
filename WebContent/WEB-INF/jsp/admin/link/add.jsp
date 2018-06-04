<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- <script type="text/javascript">
	$(document).ready(function (){
		$('#add_cat').validate({
			rules:{
				name_cat:{
					required: true,
					minlength: 4,
					maxlength: 15,
				},
			},
			messages:{
				name_cat:{
					required: "<p class='vali-error'>Vui lòng nhập tên danh mục</p>",
					minlength: "<p class='vali-error'>Vui lòng nhập ít nhất 4 ký tự</p>",
					maxlength: "<p class='vali-error'>Vui lòng nhập nhiều nhất 15 ký tự</p>"
				},
			}
		});
	});
</script> -->
<div class="container-fluid">
	<!-- Title -->
	<div class="row heading-bg">
		<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
		  <h5 class="txt-dark">Thêm link resource</h5>
		</div>
		<!-- /Breadcrumb -->
	</div>
	<!-- /Title -->
	<div class="row">
		<div class="col-sm-12">
			<div class="panel panel-default card-view">
				<div class="panel-wrapper collapse in">
					<div class="panel-body">
						<div class="form-wrap">
							<form id="add_cat" action="${pageContext.request.contextPath }/admin/link/add" method="post">
							<c:if test="${not empty msg }">
								<h6 class="txt-dark capitalize-font"><i class="zmdi zmdi-info-outline mr-10"></i>${msg }</h6>
								<hr class="light-grey-hr"/>
							</c:if>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="control-label mb-10">Link resource</label>
											<input name="link" type="text" id="link" class="form-control" placeholder="Tên danh mục">
										</div>
									</div>
									<!--/span-->
								</div>
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label mb-10">Nguồn tin</label>
											<select name="idResource" class="form-control select2" data-placeholder="Choose a brand" tabindex="1">
											<c:forEach items="${alResource }" var="objItem">
												<option value="${objItem.id }">${objItem.name }</option>
											</c:forEach>
											</select>
										</div>
									</div>	
									<div class="col-md-6">
										<div class="form-group">
											<label class="control-label mb-10">Danh mục</label>
											<select name="idCat" class="form-control select2" data-placeholder="Choose a brand" tabindex="1">
											<c:forEach items="${alCat }" var="objItem">
												<option value="${objItem.id }">${objItem.name }</option>
											</c:forEach>
											</select>
										</div>
									</div>	
								</div>
								<div class="form-actions">
									<button type="submit" class="btn btn-success btn-icon left-icon mr-10 pull-left"> <i class="fa fa-check"></i> <span>Add</span></button>
									<a class="btn btn-warning pull-left" href="${pageContext.request.contextPath }/admin/link">Go back</a>
									<div class="clearfix"></div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /Row -->
</div>
<script type="text/javascript">
	$(document).ready(function() {
		  $(".select2").select2();
		});
</script>