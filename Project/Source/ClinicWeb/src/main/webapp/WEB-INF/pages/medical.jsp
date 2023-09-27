<%-- 
    Document   : medical
    Created on : Aug 10, 2023, 2:18:16 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link type="text/css" href="<c:url value="/css/medical.css"/>" rel="stylesheet" />
<c:if test="${patients.size() > 0}">
    <h1  class="text-center text-success mt-5 mb-5">Phiếu Khám Bệnh</h1>
    <c:if test="${msg != null}"><div class="alert alert-danger">${msg}</div></c:if>
    <c:url value="/doctor/medical" var="action" />
    <form:form action="${action}" modelAttribute="medicalRecord" method="post" enctype="multipart/form-data" class="form-medical mt-5 mb-5">
        <div class="form-floating mb-3 mt-3">
            <select class="form-select" name="patient">
                <c:forEach items="${patients}" var="p">
                    <option value="${p.id}">${p.fullName}</option>
                </c:forEach>
            </select>
            <label for="name">Tên Bệnh Nhân</label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <form:textarea path="symptom" class="form-control" id="floatingTextarea"></form:textarea>
                <label for="floatingTextarea2">Mô Tả triệu chứng bệnh</label>
            <form:errors path="symptom" cssClass="text-danger mt-2 ml-12" />
        </div>
        <div class="form-floating mb-3 mt-3">
            <form:textarea path="conclusion" class="form-control" id="floatingTextarea"></form:textarea>
                <label for="floatingTextarea2">Kết luận</label>
            <form:errors path="conclusion" cssClass="text-danger mt-2 ml-12" />
        </div>
        <div class="form-floating mb-3 mt-3">
            <form:textarea path="advice" class="form-control" id="floatingTextarea"></form:textarea>
                <label for="floatingTextarea2">lời khuyên</label>
        </div>
        <div class="form-floating mb-3 mt-3 number">
            <form:input path="examinationFee" type="number" min="1000" step="1000" class="form-control" />
            <label for="floatingTextarea2">Tiền Khám</label>
            <form:errors path="examinationFee" cssClass="text-danger mt-2 ml-12" />
        </div>
        <div class="form-floating mb-3 mt-3">
            <form:textarea path="note" class="form-control" id="floatingTextarea"></form:textarea>
                <label for="floatingTextarea2">Ghi chú (Nếu có)</label>
                <h6></h6>
            </div>
            <div class="form-floating mb-3 mt-3">
                <button type="submit" class="btn btn-outline-primary right">
                    <a>Toa thuốc</a>
                </button>
            </div>
    </form:form>
</c:if>
<c:if test="${patients.size() <= 0}">
    <div class="danger">
        <p><strong>NOTES!</strong> Không còn bệnh nhân nào!!!</p>
    </div>
</c:if>