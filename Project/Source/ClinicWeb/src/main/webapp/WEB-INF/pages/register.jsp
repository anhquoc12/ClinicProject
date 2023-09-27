<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link type="text/css" href="<c:url value="/css/register.css"/>" rel="stylesheet" />
<c:forEach items="${msgErr}" var="msg"><h1 class="alert alert-danger">${msg}</h1></c:forEach>
    <section class="vh-100 bg-image"
             style="background-image: url('https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp'); height: 1000px!important">
        <div class="mask d-flex align-items-center h-100 gradient-custom-3">
            <div class="container h-100">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-12 col-md-9 col-lg-7 col-xl-6">
                        <div class="card" style="border-radius: 15px;">
                            <div class="card-body p-5">
                                <h2 class="text-uppercase text-center mb-5 text-success">Đăng Ký Hệ Thống</h2>
                            <c:url value="/register" var="action" />
                            <f:form action="${action}" modelAttribute="user" method="post" enctype="multipart/form-data">
                                <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                    <f:input path="fullName" type="text" id="form3Example1cg" class="form-control form-control-lg" />
                                    <label class="form-label" for="form3Example1cg">Họ và tên</label>
                                    <f:errors path="fullName" element="h6" cssClass="text-danger" />
                                </div>

                                <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                    <f:input path="username" type="text" id="form3Example3cg" class="form-control form-control-lg" />
                                    <label class="form-label" for="form3Example3cg">Tên Đăng Nhập</label>
                                    <f:errors path="username" element="h6" cssClass="text-danger" />
                                </div>

                                <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                    <f:input path="password" type="password" id="form3Example4cg" class="form-control form-control-lg" />
                                    <label class="form-label" for="form3Example4cg">Password</label>
                                    <f:errors path="password" element="h6" cssClass="text-danger" />
                                </div>

                                <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                    <f:input path="address" type="text" id="form3Example3cg" class="form-control form-control-lg" />
                                    <label class="form-label" for="form3Example3cg">Địa chỉ</label>
                                    <f:errors path="address" element="h6" cssClass="text-danger" />
                                </div>

                                <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                    <f:input path="email" type="text" id="form3Example3cg" class="form-control form-control-lg" />
                                    <label class="form-label" for="form3Example3cg">Email</label>
                                    <f:errors path="email" element="h6" cssClass="text-danger" />
                                </div>

                                <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                    <f:input path="phone" type="text" id="fileInput" class="form-control form-control-lg" />
                                    <label class="form-label" for="form3Example3cg">Số Điện Thoại</label>
                                    <f:errors path="phone" element="h6" cssClass="text-danger" />
                                </div>

                                <div class="col-md-9 pe-5 mb-2">

                                    <f:input required="Please select a file" path="file" class="form-control form-control-lg" id="formFileLg" type="file" />
                                    <div class="small text-muted mt-2">Upload Avatar của bạn</div>
                                </div>

                                <div class="d-flex justify-content-center">
                                    <button type="submit"
                                            class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Register</button>
                                </div>

                                <p class="text-center text-muted mt-5 mb-0">Have already an account? <a href="/ClinicWeb/login"
                                                                                                        class="fw-bold text-body"><u>Login here</u></a></p>

                            </f:form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>