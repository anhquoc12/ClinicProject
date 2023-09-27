<%-- 
    Document   : unitMedicine
    Created on : Jul 31, 2023, 11:00:33 PM
    Author     : Admin
--%>

<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-success" style="margin: 3px;">Quản Lý Thuốc</h1>
<c:if test="${msgErr != null}"><div class="alert alert-danger">${msgErr}</div></c:if>
    <div class="d-flex flex-row mb-3">
        <div class="p-2">
        <c:url value="/admin/medicine/category" var="action" />
        <form:form method="post" action="${action}" modelAttribute="category" class="input-group" enctype="multipart/form-data">
            <div class="form-outline">
                <form:input path="name" name="name1" type="text" id="form1" class="form-control" />
                <label class="form-label" for="form1">Loại Thuốc</label>
                <form:errors path="name" element="h6" cssClass="text text-danger mt-2" />
            </div>
            <button type="submit" class="btn btn-primary">
                Thêm Loại
            </button>
        </form:form>
    </div>

</div>
<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Tên đơn vị</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${categories}" var="c">
            <tr>
                <th scope="row">${c.id}</th>
                <td>${c.name}</td>
                <c:url value="/admin/medicine/category/${c.id}/" var="api" />
                <td scope="col"><button onclick="deleteCategory('${api}')" class="btn btn-danger">Xoá</button></th>
            </tr>
        </c:forEach>
    </tbody>
</table>
<script src="<c:url value="/js/category.js" />"></script>
