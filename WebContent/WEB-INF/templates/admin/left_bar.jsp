<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty access }">
<ul class="nav navbar-nav side-nav nicescroll-bar">
	<li class="navigation-header">
		<span>Main</span> 
		<i class="zmdi zmdi-more"></i>
	</li>
	<li><a href="${pageContext.request.contextPath }/admin/resource"><span class="pull-left"><i class="zmdi zmdi-landscape mr-20"></i><span class="right-nav-text">Nguồn tin</span></span><span class="clearfix"></span></a></li>
	<li><a href="${pageContext.request.contextPath }/admin/cat"><span class="pull-left"><i class="zmdi zmdi-apps mr-20"></i><span class="right-nav-text">Danh mục</span></span><span class="clearfix"></span></a></li>
	<li><a href="${pageContext.request.contextPath }/admin/link"><span class="pull-left"><i class="zmdi zmdi-globe-alt mr-20"></i><span class="right-nav-text">Link resource</span></span><span class="clearfix"></span></a></li>
	<li><a href="${pageContext.request.contextPath }/admin"><span class="pull-left"><i class="zmdi zmdi-collection-item mr-20"></i><span class="right-nav-text">Tin tức</span></span><span class="clearfix"></span></a></li>
</ul>
</c:if>