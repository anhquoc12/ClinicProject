<%-- 
    Document   : login
    Created on : Jul 17, 2023, 1:17:21 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:url value="/login" var="action"/>
<c:if test="${param.error != null}">
    <div class="alert alert-danger">
        <strong>Failed!</strong> Tài khoản hoặc mật khẩu không chính xác. Vui Lòng Quay Lại Sau.
    </div>
</c:if>
<c:if test="${id != null}">
    <div class="alert alert-danger">
        <strong>Failed!</strong> ${avatar}
    </div>
</c:if>
<c:if test="${param.accessDenied != null}">
    <div class="alert alert-danger">
        <strong>Failed!</strong> Tài Khoản của bạn không được phép truy cập trang này
    </div>
</c:if>
<section class="vh-100 bg-light">
    <form method="POST" action="${action}">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card shadow-2-strong" style="border-radius: 1rem;">
                        <div class="card-body p-5 text-center">

                            <h3 class="mb-5">Đăng Nhập Hệ Thống</h3>

                            <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                <input required="Vui Lòng nhập username" name="username" type="text" id="typeText" class="form-control form-control-lg" />
                                <label class="form-label" for="type">Username</label>
                            </div>

                            <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                <input required="Vui Lòng nhập password" name="password" type="password" id="typePassword" class="form-control form-control-lg" />
                                <label class="form-label" for="typePasswordX-2">Password</label>
                            </div>

                            <button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>

                            <hr class="my-4">

<!--                            <button class="btn btn-lg btn-block btn-primary" style="background-color: #dd4b39;"
                                    type="submit"><i class="fab fa-google me-2"></i> Sign in with google</button>
                            <button class="btn btn-lg btn-block btn-primary mb-2" style="background-color: #3b5998;"
                                    type="submit"><i class="fab fa-facebook-f me-2"></i>Sign in with facebook</button>-->

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</section>
