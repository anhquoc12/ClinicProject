<%-- 
    Document   : medicine
    Created on : Jul 31, 2023, 3:50:53 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1 class="text-center text-success" style="margin: 3px;">Toa Thuốc</h1>
<div data-bs-spy="scroll" data-bs-target="#navbar-example2" data-bs-offset="0" class="scrollspy-example" tabindex="0">
<div class="d-flex flex-row mb-3">
    <div class="p-2"><a href="<c:url value="/admin/medicine"></c:url>" class="btn btn-primary">Thêm thuốc</a></div>
        <div class="p-2">
            <form class="input-group">
                <div class="form-outline">
                    <input placeholder="search" name="name" type="search" id="form1" class="form-control" />
                    <label class="form-label" for="form1">Search</label>
                </div>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-search"></i>
                </button>
            </form>
        </div>
        <form class="d-flex flex-row mb-3">
            <div class="p-2">

                <select name="cate" class="form-select">
                <c:forEach items="${categories}" var="category">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="p-2">
            <button type="submit" class="btn btn-primary">
                Tìm Kiếm theo danh mục
            </button>
        </div>
    </form>

</div>
<table class="table">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Tên thuốc</th>
            <th scope="col">Đơn Giá</th>
            <th scope="col">Số Lượng</th>
            <th scope="col">Loại</th>
            <th scope="col">Đơn vị thuốc</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${medicines}" var="medicine">
            <tr>
                <th scope="row">${medicine.id}</th>
                <td>${medicine.name}</td>
                <td><fmt:formatNumber value="${medicine.unitPrice}" pattern="#,###"/> VNĐ</td>
                <td>${medicine.unitInStock}</td>
                <td>${medicine.categoryId.name}</td>
                <td>${medicine.unitMedicineId.name}</td>
                <c:url value="/admin/medicine/${medicine.id}" var="api" />
                <th scope="col"><a href="${api}" class="btn btn-info">Cập Nhật</a></th>
                <th scope="col"><button href="${api}" onclick="deleteMedicine('${api}')" class="btn btn-danger">Xoá</button></th>
            </tr>
        </c:forEach>
    </tbody>
</table>
</div>
        <script src="<c:url value="/js/medicine.js" />"></script>
