<%-- 
    Document   : login
    Created on : Jul 17, 2023, 1:17:21 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:url value="/change-password" var="action"/>
<c:if test="${err != null}">
    <div class="alert alert-danger">${err}</div>
</c:if>
    <form method="POST" action="${action}" class="vh-100 bg-light">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card shadow-2-strong" style="border-radius: 1rem;">
                        <div class="card-body p-5 text-center">

                            <h3 class="mb-5">Đổi Mật Khẩu</h3>

                            <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                <input required="Vui Lòng nhập username" name="currentPW" type="password" id="typeText" class="form-control form-control-lg" />
                                <label class="form-label" for="type">Mật Khẩu Hiện Tại</label>
                            </div>

                            <div class="form-outline mb-4 form-floating mb-3 mt-3">
                                <input required="Vui Lòng nhập password" name="password" type="password" id="typePassword" class="form-control form-control-lg" />
                                <label class="form-label" for="typePasswordX-2">Mật Khẩu mới</label>
                            </div>

                            <button class="btn btn-primary btn-lg btn-block" type="submit">Đổi Mật Khẩu</button>

                            <hr class="my-4">

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</section>
