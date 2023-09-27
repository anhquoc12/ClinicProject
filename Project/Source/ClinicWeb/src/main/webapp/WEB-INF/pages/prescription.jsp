<%-- 
    Document   : prescription
    Created on : Aug 10, 2023, 9:33:44 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<link type="text/css" href="<c:url value="/css/prescription.css"/>" rel="stylesheet" />
<div class="form-floating mb-3 mt-3 search">
    <input type="text" class="form-control" placeholder="search" id="searchInput" name="search" onkeyup="filterTable()">
    <label for="search">Search</label>
</div>
<table id="dataTable" class="table table-light table-borderless table-scroll small-first-col">
    <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Tên thuốc</th>
            <th scope="col">Số Lượng</th>
            <th scope="col">Loại</th>
            <th scope="col">Đơn vị thuốc</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody class="body-half-screen">
        <c:forEach items="${medicines}" var="medicine">
            <tr class="medicine list">
                <td class="id" scope="row">${medicine.id}</td>
                <td class="medicine--name">${medicine.name}</td>
                <c:if test="${medicine.unitInStock > 0}">
                    <td class="stock">${medicine.unitInStock}</td>
                </c:if>
                <c:if test="${medicine.unitInStock == 0}">
                    <td class="text-danger">Hết hàng</td>
                </c:if>
                    <td class="medicine--category">${medicine.categoryId.name}</td>
                <td class="medicine--unit">${medicine.unitMedicineId.name}</td>
                <c:if test="${medicine.unitInStock > 0}">
                    <c:url value="/admin/medicine/${medicine.id}" var="api" />
                    <td scope="col"><button onclick="addRow(this)" class="btn btn-outline-secondary">ADD</button></td>
                </c:if>

            </tr>
        </c:forEach>
    </tbody>
</table>
<div class="wid15 alert alert-info mt-5 mb-5">Toa Thuốc</div>
<table class="table caption-top wid50" id="myTable">
    <thead>
        <tr class="header" style=" border-top: 1px solid black !important;">
            <th scope="col">Tên Thuốc</th>
            <th scope="col">Liều Lượng</th>
            <th scope="col">Tần suất dùng thuốc</th>
            <th scope="col">Thời gian dùng thuốc</th>
            <th scope="col">Số Lượng</th>
            <th scope="col">Đơn Vị</th>
            <th scope="col"></th>
        </tr>
    </thead>
    <tbody>
</table>
<c:url value="/doctor/prescription/${medical.id}" var="path" />
<button onclick="exportPrescription('${path}', {doctorName: '${dataServer.doctorName}',
doctorAddress: '${dataServer.doctorAddress}',doctorPhone: '${dataServer.doctorPhone}',
      patientName:  '${dataServer.patientName}', patientAddress:  '${dataServer.patientAddress}',
patientPhone:  '${dataServer.patientPhone}', advice: '${dataServer.advice}',
file: '${dataServer.file}'})" class="btn btn-info mb-5 mt-5" id="export" onclick="requestJson()" style="margin-left: auto; display: block; margin-right: 30%;"><i class="fa-solid fa-file-export"></i>
    <span>Xuất Toa Thuốc</span>
</button>
<c:url value="/js/prescription.js" var="js" />
<script src="${js}"></script>
