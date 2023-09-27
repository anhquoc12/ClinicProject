<%-- 
    Document   : users
    Created on : Aug 4, 2023, 8:37:58 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<div class="d-flex flex-row mb-3">
    <div class="p-2 mt-3">
        <form class="input-group">
            <div class="form-outline">
                <input name="name" type="search" id="form1" class="form-control"/>
                <label class="form-label" for="form1">Search</label>
            </div>
            <button type="submit" class="btn btn-primary">
                <i class="fas fa-search"></i>
            </button>
        </form>
    </div>

</div>
<c:if test="${path == 'Doctor'}">
<div style="margin-left: 24px;">
    <a href="<c:url value="/admin/doctor" />" class="btn btn-primary">Thêm Bác Sỹ</a>
</div>
</c:if>
<c:if test="${path == 'Nurse'}">
<div style="margin-left: 24px;">
    <a href="<c:url value="/admin/nurse" />" class="btn btn-primary">Thêm Y Tá</a>
</div>
</c:if>
<div class="content container-fluid" style="margin-top:30px;">

    <!-- Page Header -->
    <div class="page-header">
        <div class="row">
            <div class="col-sm-12">
                <c:if test="${path != null}">
                    <h3 class="page-title">List of ${path}</h3>
                </c:if>
            </div>
        </div>
        <!-- /Page Header -->

        <div class="row">
            <div class="col-sm-12">
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="datatable table table-hover table-center mb-0">
                                <thead>
                                    <tr>
                                        <th>Họ Và Tên</th>
                                            <c:if test="${path != null && path == 'Doctor'}">
                                            <th>Chuyên Khoa</th>
                                            </c:if>
                                        <th>Địa chỉ</th>
                                        <th>email</th>
                                        <th>Số Điện Thoại</th>
                                        <th></th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${userList}" var="u">
                                        <tr>
                                            <td>
                                                <h2 class="table-avatar">
                                                    <img style="margin-right:50px;" width="50px" class="avatar-img rounded-circle" src="${u[1]}" alt="User Image">
                                                    <h7 style="display: inline;">${u[2]}</h5>
                                                </h2>
                                            </td>
                                            <c:if test="${path == 'Doctor'}">
                                                <td>${u[6]}</td>
                                            </c:if>
                                            <td>${u[3]}</td>

                                            <td>${u[4]}</td>

                                            <td>${u[5]}</td>
                                            <c:if test="${path != null && path == 'Doctor'}">
                                                <c:url value="/admin/doctor/${u[0]}" var="api" />
                                            </c:if>
                                            <c:if test="${path != null && path == 'Nurse'}">
                                                <c:url value="/admin/nurse/${u[0]}" var="api" />
                                            </c:if>
                                            <c:if test="${path != 'Patient' }">
                                                <td><a href="${api}" class="btn btn-warning">Cập Nhật</a></td>
                                                <td><button onclick="deleteUser('${api}')" class="btn btn-danger">Xoá</button></td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>			
        </div>

    </div>			
</div>
<script src="<c:url value="/js/user.js" />"></script>
