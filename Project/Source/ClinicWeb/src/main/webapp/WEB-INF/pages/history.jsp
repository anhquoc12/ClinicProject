<%-- 
    Document   : history
    Created on : Aug 15, 2023, 12:07:44 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link type="text/css" href="<c:url value="/css/history.css"/>" rel="stylesheet" />
<h1 class="text-center text-info">LỊCH SỬ KHÁM</h1>
<div class="container search__form">
    <span class="badge bg-info badge-search">Seach</span>
    <form action="<c:url value="/doctor/history/search" />" class="container-fluid">
        <div class="row">
            <input value="${date}" name="date1" type="date" class="col-md-2 col-sm-2 item">
            <button class="btn btn-outline-primary col-md-2 item item-btn" type="submit">Seach</button>
        </div>
    </form>
</div>
<table class="table align-middle mb-0 bg-white" id="myTable">
    <thead class="bg-light">
        <tr>
            <th>Tên Bệnh nhân</th>
            <th>Ngày Khám Bệnh</th>
            <th>Bác Sỹ Khám Bệnh</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${medicals}" var="medical">
            <tr>
                <td>
                    <div class="d-flex align-items-center">
                        <img src="${medical.patientId.avatar}" alt="${medical.patientId.username}"
                             style="width: 45px; height: 45px" class="rounded-circle" />
                        <div class="ms-3">
                            <p class="fw-bold mb-1">${medical.patientId.fullName}</p>
                            <p class="text-muted mb-0">${medical.patientId.email}</p>
                            <p class="text-muted mb-0">${medical.patientId.phone}</p>
                        </div>
                    </div>
                </td>
                <td>
                    <p class="fw-normal mb-1">${medical.createdDate}</p>
                </td>
                <td>
                    <div class="d-flex align-items-center">
                        <img src="${medical.doctorId.avatar}" alt="${medical.doctorId.username}"
                             style="width: 45px; height: 45px" class="rounded-circle" />
                        <div class="ms-3">
                            <p class="fw-bold mb-1">${medical.doctorId.fullName}</p>
                        </div>
                </td>
                <td>
                    <a href="<c:url value="/doctor/history/${medical.id}" />" class="btn btn-link btn-sm btn-rounded text-danger">
                        Xem Chi Tiết Bệnh Án
                    </a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
