<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-success" style="margin: 3px;">Quản Lý Lịch Trực</h1>
<c:if test="${msgErr != null}"><div class="alert alert-success">${msgErr}</div></c:if>
    <div class="d-flex flex-row mb-3">
        <div class="p-2">
        <c:url value="/admin/room" var="action" />
        <form:form method="post" action="${action}" modelAttribute="room" class="input-group" enctype="multipart/form-data">
            <div class="form-outline">
                <form:input path="name" name="name1" type="text" id="name" class="form-control" />
                <label class="form-label" for="name">Tên Phòng</label>
                <form:errors path="name" element="h6" cssClass="text-danger" />
            </div>
            <button type="submit" class="btn btn-primary">
                Thêm Phòng
            </button>
        </form:form>
    </div>

</div>
<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Tên Phòng</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${rooms}" var="r">
            <tr>
                <th scope="row">${r.id}</th>
                <td>${r.name}</td>
                <c:url value="/admin/room/${r.id}/" var="api" />
                <td scope="col"><button onclick="deleteRoom('${api}')" class="btn btn-danger">Xoá</button></th>
            </tr>
        </c:forEach>
    </tbody>
</table>
<script src="<c:url value="/js/schedule.js" />"></script>
