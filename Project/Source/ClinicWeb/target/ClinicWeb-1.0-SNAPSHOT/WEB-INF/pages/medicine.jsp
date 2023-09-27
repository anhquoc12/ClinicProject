<%-- 
    Document   : addMedicine
    Created on : Jul 31, 2023, 5:20:59 PM
    Author     : Admin
--%>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"  %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1 class="text-center text-success">Thêm Thuốc</h1>
<c:if test="${msg_err != null}"><div class="alert alert-success">${msg_err}</div></c:if>
<form:form action="/ClinicWeb/admin/medicine" method="post" style="margin: 50px;" modelAttribute="medicine" enctype="multipart/form-data">
    <form:errors path="*" cssClass="alert alert-danger" element="div" />
    <form:hidden path="id"/>
    <div class="form-floating mb-3 mt-3">
        <form:input path="name" type="text" class="form-control" id="name" placeholder="Tên thuốc" name="name" />
        <label for="name">Tên thuốc</label>
        <form:errors path="name" element="h6" cssClass="text text-danger mt-2" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input width="50%" path="unitPrice" type="number" class="form-control" id="price" placeholder="Giá sản phẩm" name="price" />
        <label for="price">Giá Thuốc</label>
        <form:errors path="unitPrice" element="h6" cssClass="text text-danger mt-2" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input path="unitInStock" type="number" class="form-control" id="unitInStock" placeholder="Giá sản phẩm" name="unitInStock" />
        <label for="unitInStock">Số Lượng</label>
        <form:errors path="unitInStock" element="h6" cssClass="text text-danger mt-2" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:select path="categoryId" class="form-select" id="cate" name="cate">
            <c:forEach items="${categories}" var="c">
                <c:choose>
                    <c:when test="${c.id == medicine.categoryId.id}">
                        <option value="${c.id}" selected>${c.name}</option>
                    </c:when>
                    <c:otherwise><option value="${c.id}">${c.name}</option></c:otherwise>
                </c:choose>

            </c:forEach>
        </form:select>
        <label for="cate" class="form-label">Danh mục Thuốc</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:select class="form-select" id="unit" path="unitMedicineId" name="unit">
            <c:forEach items="${units}" var="u">
                <c:choose>
                    <c:when test="${u.id == medicine.unitMedicineId.id}">
                        <option value="${u.id}" selected>${u.name}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${u.id}">${u.name}</option>
                    </c:otherwise>
                </c:choose>

            </c:forEach>
        </form:select>
        <label for="unit" class="form-label">Đơn Vị Thuốc</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <c:choose>
            <c:when test="${medicine.id != null}">
                <button type="submit" class="btn btn-primary">
                    Cập Nhật Thuốc
                </button>
            </c:when>
            <c:otherwise>
                <button type="submit" class="btn btn-primary">
                    Thêm Thuốc
                </button>
            </c:otherwise>
        </c:choose>

    </div>
</form:form>
