
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-expand-sm navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="javascript:void(0)">
            <img class="logo" src="<c:url value="/img/logo.png" />" alt="clinic Website" />
        </a>
        <a class="navbar-brand" href="javascript:void(0)">Clinic Website</a>
        <div class="collapse navbar-collapse" id="mynavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/" />">Trang Chủ</a>
                </li>
                <security:authorize access="hasAuthority('PATIENT')">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/appointment" />">Đăng Ký Lịch Khám</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/listAppointment" />">Lịch Sử Đăng Ký</a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAuthority('ADMIN')">
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown">
                            Users
                            <i class="fa-solid fa-chevron-up fa-rotate-180 fa-lg" style="margin-left: 4px;"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <c:url value="/admin/users" var="url" />
                            <li><a class="dropdown-item" href="${url}/doctor">Bác Sỹ</a></li>
                            <li><a class="dropdown-item" href="${url}/nurse">Y Tá</a></li>
                            <li><a class="dropdown-item" href="${url}/patient">Bệnh Nhân</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown">
                            Thuốc
                            <i class="fa-solid fa-chevron-up fa-rotate-180 fa-lg" style="margin-left: 4px;"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <c:url value="/admin/medicine" var="url" />
                            <li><a class="dropdown-item" href="<c:url value="/admin/medicineList" />">Danh Sách Thuốc</a></li>
                            <li><a class="dropdown-item" href="${url}/unit-medicine">Đơn Vị Thuốc</a></li>
                            <li><a class="dropdown-item" href="${url}/category">Loại Thuốc</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown">
                            Lịch Trực Phòng
                            <i class="fa-solid fa-chevron-up fa-rotate-180 fa-lg" style="margin-left: 4px;"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <c:url value="/admin" var="url" />
                            <li><a class="dropdown-item" href="<c:url value="/schedule/viewSchedule" />">Lịch Trực</a></li>
                            <li><a class="dropdown-item" href="${url}/room">Phòng Trực</a></li>
                            <li><a class="dropdown-item" href="${url}/specialization">Chuyên Khoa</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/admin/stat" />">Thống Kê - Báo cáo</a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAuthority('NURSE')">
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown">
                            lịch Khám bệnh
                            <i class="fa-solid fa-chevron-up fa-rotate-180 fa-lg" style="margin-left: 4px;"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <c:url value="/admin" var="url" />
                            <li><a class="dropdown-item" href="<c:url value="/nurse/unConfirmed" />">Cần được xác nhận</a></li>
                            <li><a class="dropdown-item" href="<c:url value="/nurse/todayAppointment" />">Lịch Khám Hôm Nay</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/schedule/viewSchedule" />">Lịch Trực</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/nurse/invoices" />">Thanh Toán</a>
                    </li>
                </security:authorize>
                <security:authorize access="hasAuthority('DOCTOR')">
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown">
                            Khám bệnh
                            <i class="fa-solid fa-chevron-up fa-rotate-180 fa-lg" style="margin-left: 4px;"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <c:url value="/admin" var="url" />
                            <li><a class="dropdown-item" href="<c:url value="/doctor/medical" />">Phiếu Khám Bệnh</a></li>
                            <li><a class="dropdown-item" href="<c:url value="/doctor/history" />">Lịch Sử Khám Bệnh</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/schedule/viewSchedule" />">Lịch Trực</a>
                    </li>
                </security:authorize>
            </ul>
            <c:if test="${pageContext.request.userPrincipal.name == null}">
                <div class="d-flex">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item">
                            <a class="nav-link btn btn-success mr-5 signin" href="/ClinicWeb/login">Đăng Nhập</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link btn btn-danger signin" href="/ClinicWeb/register">Đăng Ký</a>
                        </li>
                    </ul>
                </div>
            </c:if>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <ul class="navbar-nav">
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            <img src="${pageContext.session.getAttribute("current").avatar}" alt="None" class="rounded-circle"
                                 height="22" alt="Avatar" loading="lazy" />
                            <i class="fa-solid fa-chevron-up fa-rotate-180"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <a class="dropdown-item" href="#">${pageContext.request.userPrincipal.name}</a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="<c:url value="/infoUser" />">Account Info</a>
                            </li>
                            <li>
                                <a class="dropdown-item" href="<c:url value="/logout" />">Logout</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </c:if>
        </div>
    </div>
</nav>