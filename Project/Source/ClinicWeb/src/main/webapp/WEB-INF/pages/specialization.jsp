<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-success" style="margin: 3px;">Quản Lý Khoa</h1>
<c:if test="${msgErr != null}"><div class="alert alert-success">${msgErr}</div></c:if>
    <div class="d-flex flex-row mb-3">
        <div class="p-2">
        <c:url value="/admin/specialization" var="action" />
        <form:form method="post" action="${action}" modelAttribute="special" class="input-group" enctype="multipart/form-data">
            <div class="form-outline">
                <form:input path="name" name="name1" type="text" id="form1" class="form-control" />
                <label class="form-label" for="form1">Tên Khoa</label>
                <form:errors path="name" element="h6" cssClass="text-danger" />
            </div>
            <button type="submit" class="btn btn-primary">
                Thêm Khoa
            </button>
        </form:form>
    </div>

</div>
<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Tên Khoa</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${specials}" var="s">
            <tr>
                <th scope="row">${s.id}</th>
                <td>${s.name}</td>
                <c:url value="/admin/specialization/${s.id}/" var="api" />
                <td scope="col"><button onclick="deleteSpecial('${api}')" class="btn btn-danger">Xoá</button></th>
            </tr>
        </c:forEach>
    </tbody>
</table>
<script src="<c:url value="/js/specialization.js" />"></script>