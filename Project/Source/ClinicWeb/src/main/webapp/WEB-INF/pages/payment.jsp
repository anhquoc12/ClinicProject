<%-- 
    Document   : payment
    Created on : Aug 17, 2023, 1:09:54 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="format" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" href="<c:url value="/css/payment.css"/>" rel="stylesheet" />
<section class="content--payment">
    <h1 class="title" style="margin-top: 16px !important;">Invoice</h1>
    <div>
        <p>${invoice.medicalRecordId.patientId.fullName}</p>
        <p>${invoice.medicalRecordId.patientId.address}</p>
        <p>${invoice.medicalRecordId.patientId.phone}</p>
        <p>${invoice.medicalRecordId.patientId.email}</p>
    </div>
<article>
    <h1>Recipient</h1>
    <table class="meta">
        <tr>
            <th><span>Invoice #</span></th>
            <td><span>${invoice.id}</span></td>
        </tr>
        <tr>
            <th><span>Ngày Thực Hiện</span></th>
            <td><span><format:formatDate pattern="MMM d, yyyy" value="${invoice.createDate}" /></span></td>
        </tr>
        <c:if test="${invoice.nurseId != null}">
            <tr>
                <th><span>Y Tá Thanh Toán</span></th>
                <td>${nurse.fullName}</td>
            </tr>
        </c:if>
    </table>
    <table class="inventory">
        <thead>
            <tr>
                <th><span>Tên Thuốc</span></th>
                <th><span>Liều Lượng</span></th>
                <th><span>Tần suất dùng thuốc</span></th>
                <th><span>Thời gian sử dụng</span></th>
                <th><span>Số Lượng</span></th>
                <th><span>Thành tiền</span></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${medicines}" var="m">
                <tr>
                    <td><a class="cut">-</a><span>${m[0]}</span></td>
                    <td><span>${m[1]}</span></td>
                    <td><span>${m[2]}</span></td>
                    <td><span>${m[3]}</span></td>
                    <td><span>${m[4]}</span></td>
                    <td><span><format:formatNumber pattern="#,###" value="${m[5]}" /></span> VNĐ</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <table class="balance">
        <tr>
            <th><span>Tổng tiền thuốc</span></th>
            <td><span><format:formatNumber pattern="#,###" value="${feePrescription}" /></span> VNĐ</td>
        </tr>
        <tr>
            <th><span>Tiền Khám Bệnh</span></th>
            <td><span><format:formatNumber pattern="#,###" value="${feeMedical}" /></span> VNĐ</td>
        </tr>
        <tr>
            <th><span>Tiền Phải Trả</span></th>
            <td><span><format:formatNumber pattern="#,###" value="${feePrescription + feeMedical}" /></span> VNĐ</td>
        </tr>
    </table>
</article>
<c:if test="${invoice.nurseId == null}">
    <c:url value="/nurse/invoices/${invoice.id}" var="url" />
    <button class="btn btn-success" onclick="pay('${url}')">Thanh Toán</button>
</c:if>
</section>
<script src="<c:url value="/js/payment.js" />"></script>