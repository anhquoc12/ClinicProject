<%-- 
    Document   : invoices
    Created on : Aug 16, 2023, 3:24:57 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="format" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    .content--payment {
        margin-top: 24px !important;
        margin: auto;
        width: 80%;
    }

    tr:last-child {
        border-bottom: none !important;
    }
</style>

<section class="content--payment">
    <h1 class="text-info text-center badge badge-info">Danh Sách Thanh Toán</h1>
    <table id="list-invoice" class="table align-middle mb-0 bg-white" style="margin-bottom:100px !important;">
        <thead class="bg-light">
            <tr style="border: none !important;">
                <th style="border: none !important;">Số hoá đơn</th>
                <th style="border: none !important;">Người Trả</th>
                <th style="border: none !important;">Ngày Trả</th>
                <th style="border: none !important;">Tổng tiền</th>
                <th style="border: none !important;">Trạng Thái</th>
                <th style="border: none !important;"></th>
            </tr>
        </thead>
        <tbody style="border: 1px solid white !important;">
            <c:forEach items="${invoices}" var="i">
                <c:url value="/nurse/invoices/${i[0]}" var="url" />
                <c:if test="${i[3].toString() == 'PENDING'}">
                    <tr class="table-warning">
                        <td style="border: none !important;">#INV-${i[0]}-<span><format:formatDate pattern="dd-MM-yyyy" value="${i[2]}"/></span></td>
                        <td style="border: none !important;">${i[1]}</td>
                        <td style="border: none !important;">${i[2]}</td>
                        <td style="border: none !important;"><format:formatNumber pattern="#,##0" value="${i[4]}" /> VNĐ</td>
                        <td style="border: none !important;"><span class="badge bg-warning">Chưa Thanh Toán</span></td>
                        <td style="border: none !important;"><a href="${url}" class="text-danger">view details</a></td>
                    </tr>
                </c:if>
                <c:if test="${i[3].toString() == 'ACCEPTED'}">
                    <tr class="table-success">
                        <td style="border: none !important;">${i[0]}-<span><format:formatDate pattern="dd-MM-yyyy" value="${i[2]}"/></span></td>
                        <td style="border: none !important;">${i[1]}</td>
                        <td style="border: none !important;">${i[2]}</td>
                        <td style="border: none !important;"><format:formatNumber pattern="#,##0" value="${i[4]}" /> VNĐ</td>
                        <td style="border: none !important;"><span class="badge bg-success">Đã Thanh Toán</span></td>
                        <td style="border: none !important; border-bottom: none !important"><a href="${url}" class="text-danger">view details</a></td>
                    </tr>
                </c:if>
            </c:forEach>
        </tbody>
    </table>
    <!--<h1 class="linet"></h1>-->
</section>
