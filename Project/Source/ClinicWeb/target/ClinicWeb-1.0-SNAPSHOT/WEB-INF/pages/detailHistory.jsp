<%-- 
    Document   : detailHistory
    Created on : Aug 15, 2023, 11:46:54 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="format" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" href="<c:url value="/css/detailHistory.css"/>" rel="stylesheet" />
<section class="content">
    <h1 class="text-secondary mt-2 text-uppercase">Biểu Mẫu Lịch Sử Khám Bệnh</h1>
    <div class="line"></div>
    <div class="info--patient">
        <div class="field"><span class="field--name">Họ Và Tên</span> <span class="value">${medical.patientId.fullName}</span>
        </div>
    </div>
    <div class="info--patient">
        <div class="field"><span class="field--name">Id</span> <span class="value">${medical.patientId.id}</span></div>
    </div>
    <div class="info--patient">
        <div class="field"><span class="field--name">Số điện thoại</span> <span class="value">${medical.patientId.phone}</span>
        </div>
    </div>
    <div class="info--patient">
        <div class="field"><span class="field--name">Email</span> <span class="value">${medical.patientId.email}</span></div>
    </div>
    <div class="info--patient">
        <div class="field"><span class="field--name">Ngày Khám</span> <span class="value"><format:formatDate pattern="dd-MM-yyyy" value="${medical.createdDate}" /></span></div>
    </div>
    <div class="info--patient">
        <div class="field"><span class="field--name">Bác sỹ thăm khám</span> <span class="value">${medical.doctorId.fullName}</span></div>
    </div>
    <div class="line"></div>
    <div class="container-fluid mt-3 table">
        <div class="row rows">
            <div class="col-sm-3 bg-secondary">Triệu Chứng</div>
            <div class="col-sm-9">${medical.symptom}</div>
        </div>
        <div class="row rows">
            <div class="col-sm-3 bg-secondary">Kết Luận</div>
            <div class="col-sm-9">${medical.conclusion}</div>
        </div>
        <div class="row rows">
            <div class="col-sm-3 bg-secondary">Lời Khuyên</div>
            <div class="col-sm-9">${medical.advice}</div>
        </div>
        <div class="row rows">
            <div class="col-sm-3 bg-secondary">Ghi chú</div>
            <div class="col-sm-9">${medical.note}</div>
        </div>
    </div>
    <div class="line"></div>
    <table class="table table-bordered" border="1">
        <thead>
            <tr>
                <th colspan="5" class="title--table">Toa Thuốc</th>
            </tr>
            <tr>
                <th width="25%" scope="col">Tên Thuốc</th>
                <th width="20%" scope="col">Liều Lượng</th>
                <th width="20%" scope="col">Tần suất</th>
                <th width="20%" scope="col">Thời gian sử dụng</th>
                <th width="15%" scope="col">Số Lượng</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${prescriptions}" var="p">
            <tr>
                <td>${p.medicineId.name}</td>
                <td>${p.dosage}</td>
                <td>${p.frequency}</td>
                <td>${p.duration}</td>
                <td>${p.totalUnit} ${p.medicineId.unitMedicineId.name}</td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</section>