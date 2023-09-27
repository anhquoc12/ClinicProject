<%-- 
    Document   : schedule
    Created on : Jul 30, 2023, 10:47:12 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h1 class="text-center text-danger">Lịch Trực</h1>
<c:if test="${msgSuccess != null}"><h6 class="alert alert-success">${msgSuccess}</h1></c:if>
    <%--<c:if test="${msg_failed != null}">
        <c:forEach items="${msg_failed}" var="msg">
            <h6 class="alert alert-danger">${msg}</h1>
        </c:forEach>
    </c:if>--%>
    <c:url value="/admin/schedule" var="action" />
    <form:form method="post" action="${action}" modelAttribute="schedule" enctype="multipart/form-data" >
        <form:errors path="*" cssClass="alert alert-danger" element="div" />
    <div class="container">
        <div class="card">
            <div class="form-row border-bottom p-4 position-relative">
                <label class="text-inverse font-12 text-uppercase">Chọn Ngày</label>
                <div class="input-group date">
                    <form:input path="scheduleDate" name="scheduleDate" type="date" class="border-0 p-0 font-14 form-control" id="dp" placeholder="Select the Schedule Date" />
                    <label class="mt-2" for="dp"><i class="icon-calendar mt-1"></i></label>
                    <form:errors path="scheduleDate" element="h6" cssClass="text-danger" />
                </div>
            </div>
            <div class="form-row border-bottom p-4 position-relative">
                <label class="text-inverse font-12 text-uppercase">Giờ Bắt Đầu</label>
                <div class="input-group date">
                    <form:input path="shiftStart" name="shiftDate" type="time" class="border-0 p-0 font-14 form-control" id="dp" placeholder="Select the Schedule Date" />
                    <label class="mt-2" for="dp"><i class="icon-calendar mt-1"></i></label>
                        <form:errors path="shiftStart" element="h6" cssClass="text text-danger" />
                </div>
            </div>
            <div class="form-row border-bottom p-4 position-relative">
                <label class="text-inverse font-12 text-uppercase">Giờ Kết Thúc</label>
                <div class="input-group date">
                    <form:input path="shiftEnd" value="${null}" name="shiftEnd" type="time" class="border-0 p-0 font-14 form-control" id="dp" placeholder="Select the Schedule Date" />
                    <label class="mt-2" for="dp"><i class="icon-calendar mt-1"></i></label>
                        <form:errors path="shiftEnd" element="h6" cssClass="text text-danger" />
                </div>
            </div>
            <div class="form-row border-bottom p-4">
                <label class="text-inverse font-12 text-uppercase">Chọn Nhân Viên</label>
                <form:select path="userId" class="form-select">
                    <option value="null" disabled>Chọn Nhân Viên</option>
                    <c:forEach items="${users}" var="i">
                        <option value="${i.id}">${i.fullName}</option>
                    </c:forEach>
                </form:select>
            </div>
            <div class="form-row border-bottom p-4">
                <label class="text-inverse font-12 text-uppercase">Chọn Phòng trực</label>
                <form:select path="roomId" class="form-select">
                    <option value="null" disabled>Chọn Phòng</option>
                    <c:forEach items="${rooms}" var="i">
                        <option value="${i.id}">${i.name}</option>
                    </c:forEach>
                </form:select>
            </div>
            <div>
                <button class="btn btn-success" type="submit">Tạo Lịch</button>
            </div>
        </div>
    </div>
</form:form>
