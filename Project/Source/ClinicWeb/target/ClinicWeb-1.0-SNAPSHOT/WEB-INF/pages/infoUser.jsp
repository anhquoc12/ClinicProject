<%-- 
    Document   : infoUser
    Created on : Aug 8, 2023, 1:01:05 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link type="text/css" href="<c:url value="/css/infoUser.css"/>" rel="stylesheet" />
<c:if test="${err != null}">
    <div class="alert alert-danger">${err}</div>
</c:if>
<section class="py-5 my-5">
    <div class="container">
        <h1 class="mb-5">Account Settings</h1>
        <div class="bg-white shadow rounded-lg d-block d-sm-flex">
            <div class="profile-tab-nav border-right">
                <div class="p-4">
                    <div class="img-circle text-center mb-3">
                        <img src="${user.avatar}" alt="Image" class="shadow">
                    </div>
                    <h4 class="text-center">${user.fullName}</h4>
                </div>
            </div>
            <div class="tab-content p-4 p-md-5" id="v-pills-tabContent">
                <c:url value="/edit-user" var="action" />
                <form:form action="${action}" modelAttribute="user" method="post" enctype="multipart/form-data">
                    <div class="tab-pane fade show active" id="account" role="tabpanel" aria-labelledby="account-tab">
                        <h3 class="mb-4">Thông Tin Tài Khoản</h3>
                        <div class="row">
                            <div class="col-md-6">
                                <form:hidden path="id" class="form-control" />
                                <form:hidden path="password" class="form-control" />
                                <form:hidden path="avatar" class="form-control" />
                                <form:hidden path="userRole" class="form-control" />
                                <div class="form-group">
                                    <label>Họ Và Tên</label>
                                    <form:input path="fullName" type="text" class="form-control" />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Tên Đăng Nhập</label>
                                    <form:input readonly="true" path="username" type="text" class="form-control" />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Email</label>
                                    <form:input path="email" type="text" class="form-control" />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Phone number</label>
                                    <form:input  path="phone" type="text" class="form-control" />
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Địa chỉ</label>
                                    <form:input path="address" type="text" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="container-fluid">
                            <div class="row">
                                <div class="col-sm-3 col-md-6">
                                    <a href="<c:url value="/change-password" />" class="btn btn-warning">Đổi mật khẩu</a>
                                </div>
                                <div class="col-sm-9 col-md-6">
                                    <button type="submit" class="btn btn-success">Lưu Thông Tin</button>
                                </div>
                            </div>
                        </div>
                    </form:form>



                </div>
            </div>
        </div>
</section>


<!--<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>-->
