<%-- 
    Document   : listAppointment
    Created on : Aug 8, 2023, 2:54:40 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="format" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<security:authorize access="hasAnyAuthority('PATIENT')">
    <h1 class="text-center text-success" style="margin: 24px;">Lịch Sử đăng ký</h1>
</security:authorize>
<security:authorize access="hasAnyAuthority('NURSE')">
    <c:if test="${today == true}">
        <h1 class="text-center text-success" style="margin: 24px;">Lịch Khám Hôm Nay</h1>
    </c:if>
    <c:if test="${today != true}">
        <h1 class="text-center text-success" style="margin: 24px;">Cần được xác nhận</h1>
    </c:if>
</security:authorize>
<security:authorize access="hasAuthority('NURSE')">
    <c:if test="${none != null and today == true}">
        <div class="alert alert-warning">Hôm nay không có lịch khám</div>
    </c:if>
</security:authorize>
<c:if test="${none != null or nurse != true}">
    <table class="table table-borderless">
        <thead>
            <tr>
                <th>#</th>
                    <security:authorize access="hasAuthority('NURSE')">
                    <th>Tên Bệnh Nhân</th>
                    </security:authorize>
                <th>Ngày Hẹn Khám</th>
                    <security:authorize access="hasAnyAuthority('PATIENT', 'NURSE')">
                    <th>Khoa Khám</th>
                    </security:authorize>
                    <security:authorize access="hasAnyAuthority('PATIENT', 'NURSE')">
                    <th>Mô tả bệnh</th>
                    </security:authorize>
                    <security:authorize access="hasAuthority('PATIENT')">
                    <th>Status</th>
                    </security:authorize>
                    <c:if test="${today == true}">
                    <th>Y Tá xác nhận</th>
                    </c:if>
                    <security:authorize access="hasAuthority('NURSE')">
                    <th>Yêu cầu xác nhận</th>
                    </security:authorize>
                    <security:authorize access="hasAuthority('PATIENT')">
                    <th></th>
                    </security:authorize>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="l">
                <tr>
                    <td>${l.id}</td>
                    <security:authorize access="hasAuthority('NURSE')">
                        <td>${l.patientId.fullName}</td>
                    </security:authorize>
                    <td><format:formatDate value="${l.appointmentDate}" pattern="dd/MM/yyyy" /></td>
                    <td>${l.specializationId.name}</td>
                    <td>${l.description}</td>
                    <security:authorize access="hasAuthority('PATIENT')">
                        <td>
                            <c:if test="${l.appointmentStatus == 'WAITTING'}">
                                <i class="fa-regular fa-hourglass-half" style="color: #FAC800; font-size: 1.5rem;"></i>
                                <span style="margin: 9px; color: #FAC800; font-size: 1rem;">${l.appointmentStatus}</span>
                            </c:if>
                            <c:if test="${l.appointmentStatus == 'CONFIRMED'}">
                                <i class="fa-solid fa-circle-check" style="color: #5CBDE6; font-size: 1.5rem;"></i>
                                <span style="margin: 9px; color: #5CBDE6; font-size: 1rem;">${l.appointmentStatus}</span>
                            </c:if>
                            <c:if test="${l.appointmentStatus == 'CANCLED'}">
                                <i class="fa-regular fa-calendar-xmark" style="color: #D8262E; font-size: 1.5rem;"></i>
                                <span style="margin: 9px; color: #D8262E; font-size: 1rem;">${l.appointmentStatus}</span>
                            </c:if>
                        </td>
                    </security:authorize>
                    <security:authorize access="hasAuthority('PATIENT')">
                        <c:if test="${l.appointmentStatus == 'WAITTING'}">
                            <c:url value="/listAppointment/${l.id}" var="cancled" />
                            <td><button onclick="cancleAppointment('${cancled}')" class="btn btn-danger">Huỷ Hẹn</button></td>
                        </c:if>
                    </security:authorize>
                    <c:if test="${today == true}">
                        <td>${l.nurseId.fullName}</td>
                    </c:if>
                    <security:authorize access="hasAuthority('NURSE')">
                        <c:if test="${today != true}">
                            <c:url value="/nurse/confirm/${l.id}" var="confirmed" />
                            <td><button onclick="confirmAppointment('${confirmed}')" class="btn btn-primary">Xác Nhận Lịch Hẹn</button></td>
                        </c:if>
                        <c:if test="${today == true}">
                            <c:url value="/nurse/todayAppointment/${l.id}" var="present" />
                            <td><button onclick="presentAppointment('${present}')" class="btn btn-success">Đã Đến</button></td>
                        </c:if>
                    </security:authorize>

                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
<c:url value="/js/appointment.js" var="cancle" />
<script src="${cancle}"></script>