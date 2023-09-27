<%-- 
    Document   : doctor
    Created on : Aug 4, 2023, 4:56:52 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1>${msg}</h1>
<div class="content" style="min-height: 293.6px; margin-top: 10px;">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-8 offset-md-2">

                <!-- Account Content -->
                <div class="account-content">
                    <div class="row align-items-center justify-content-center">
                        <div class="col-md-7 col-lg-6 login-left">
                            <img src="<c:url value="/img/doctor.jpg" />" width="100%" height="100%" class="img-fluid" alt="Login Banner">	
                        </div>
                        <div class="col-md-12 col-lg-6 login-right">
                            <div class="login-header">
                                <h3 class="text text-info">
                                    <c:if test="${!isDoctor and user.id == null}">Add Nurse</c:if>
                                    <c:if test="${isDoctor and user.id == null}">Add Doctor</c:if>
                                    <c:if test="${user.id != null}">Update Employees</c:if>
                                    </h3>
                                </div>

                                <!-- Register Form -->
                            <c:if test="${user.userRole == 'NURSE'}"><c:url value="/admin/nurse" var="action" /></c:if>
                            <c:if test="${user.userRole == 'DOCTOR'}"><c:url value="/admin/doctor" var="action" /></c:if>

                            <form:form action="${action}" modelAttribute="user" enctype="multipart/form-data" method="post">
                                <form:hidden path="id"/>
                                <form:errors path="*" cssClass="alert alert-danger" element="div" />
                                <div class="form-group form-focus mb-3">
                                    <form:input path="fullName" type="name" class="form-control floating" />
                                    <label class="focus-label">Họ Và Tên</label>
                                    <form:errors path="fullName" element="h6" cssClass="text-danger" />
                                </div>
                                <div class="form-group form-focus mb-3">
                                    <form:input path="username" type="text" class="form-control floating" />
                                    <label class="focus-label">Tên đăng nhập:</label>
                                    <form:errors path="username" element="h6" cssClass="text-danger" />
                                </div>
                                <c:if test="${user.id == null}">
                                    <div class="form-group form-focus mb-3">
                                        <form:input path="password" type="password" class="form-control floating" />
                                        <label class="focus-label">Password</label>
                                        <form:errors path="password"  element="h6" cssClass="text-danger" />
                                    </div>
                                </c:if>
                                <c:if test="${user.id != null}">
                                    <div class="form-group form-focus mb-3">
                                        <form:hidden path="password" />

                                    </div>
                                </c:if>
                                <div class="form-group form-focus mb-3">
                                    <form:input path="address" type="text" class="form-control floating" />
                                    <label class="focus-label">Địa chỉ</label>
                                    <form:errors path="address" element="h6" cssClass="text-danger" />
                                </div>
                                <div class="form-group form-focus mb-3">
                                    <form:input path="email" type="text" class="form-control floating" />
                                    <label class="focus-label">Email</label>
                                    <form:errors path="email" element="h6" cssClass="text-danger" />
                                </div>
                                <div class="form-group form-focus mb-3">
                                    <form:input path="phone" type="text" class="form-control floating" />
                                    <label class="focus-label">Phone</label>
                                    <form:errors path="phone" element="h6" cssClass="text-danger" />
                                </div>
                                <c:if test="${user.userRole == 'DOCTOR' or (specials != null and isDoctor)}">
                                    <div class="form-group form-focus mb-3">
                                        <select class="form-control floating" name="specialization">
                                            <c:forEach items="${specials}" var="s">
                                                <c:choose>
                                                    <c:when test="${doctor.specializationId.id == s.id}">
                                                        <option value="${s.id}" selected="true">${s.name}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${s.id}">${s.name}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                        <label class="focus-label">Khoa Làm Việc</label>
                                    </div>
                                </c:if>
                                <div class="form-group form-focus mb-3 dis-none" id="file-avatar">
                                    <form:input defaultValue="test" required="Please select a file" path="file" class="form-control form-control-lg" id="formFileLg" type="file" />

                                    <div class="small text-muted mt-2">Upload Avatar của bạn</div>

                                </div>
                                <c:if test="${user.id != null}">
                                    <form:hidden path="avatar" />
                                </c:if>
                                <button class="btn btn-primary btn-block btn-lg login-btn mt-5" type="submit">
                                    <c:choose>
                                        <c:when test="${user.id == null}">
                                            <c:if test="${!isDoctor}">Add Nurse</c:if>
                                            <c:if test="${isDoctor}">Add Doctor</c:if>
                                        </c:when>
                                        <c:otherwise>
                                            Update Employees
                                            <!--update-->
                                        </c:otherwise>
                                    </c:choose>
                                </button>
                            </form:form>
                            <!-- /Register Form -->

                        </div>
                    </div>
                </div>
                <!-- /Account Content -->

            </div>
        </div>

    </div>

</div>