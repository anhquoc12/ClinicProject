
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link type="text/css" href="<c:url value="/css/appointment.css"/>" rel="stylesheet" />
<h1>${msg}</h1>


<section class="h-100 h-custom" style="background-color: #8fc4b7;">
    <div class="banner3">
        <div class="py-5 banner" style="background-image:url(https://www.wrappixel.com/demos/ui-kit/wrapkit/assets/images/form-banners/banner2/banner-bg.jpg);">
            <c:url value="/appointment" var="action" />
            <form:form method="post" action="${action}" modelAttribute="appointment" enctype="multipart/form-data">
                <div class="container">
                    <div class="row">
                        <div class="col-md-7 col-lg-5">
                            <h3 class="my-3 text-white font-weight-medium text-uppercase">Book Appointment</h3>
                            <div class="bg-white">
                                <div class="form-row border-bottom p-4 position-relative">
                                    <label class="text-inverse font-12 text-uppercase">Booking Date</label>
                                    <div class="input-group date">
                                        <form:input type="date" path="appointmentDate" name="appointmentDate" class="border-0 p-0 font-14 form-control" id="appointmentDate" placeholder="Select the Appointment Date" />
                                        <label class="mt-2" for="appointmentDate"><i class="icon-calendar mt-1"></i></label>
                                    </div>

                                    <form:errors path="appointmentDate" element="h6" cssClass="text-danger mt-2" />
                                </div>
                                <div class="form-row border-bottom p-4">
                                    <label class="text-inverse font-12 text-uppercase">Mô Tả Tình Trạng</label>
                                    <form:textarea type="text" path="description" name="description" col="1" row="1" id="description" 
                                                   class="border-0 p-0 font-weight-light font-14 form-control" 
                                                   placeholder="Write Down the Description" ></form:textarea>
                                    <form:errors path="description" element="h6" cssClass="text-danger mt-2" />
                                </div>
                                <div class="form-row border-bottom p-4">
                                    <label class="text-inverse font-12 text-uppercase">Chọn Khoa Khám Bệnh</label>
                                    <form:select path="specializationId" class="form-select">
                                        <option value="null" disabled>Chọn Khoa</option>
                                        <c:forEach items="${listSpecial}" var="i">
                                            <option value="${i.id}">${i.name}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                                <c:if test="${msgErr != null}">
                                    <div class="form-row border-bottom p-4">
                                        <h6 class="text-danger">${msgErr}</h6>
                                    </div>
                                </c:if>
                                <c:if test="${msgWarn != null}">
                                    <div class="form-row border-bottom p-4">
                                        <h6 class="text-warning">${msgWarn}</h6>
                                    </div>
                                </c:if>
                                    <c:if test="${param.msgSuccess != null}">
                                        <div class="form-row border-bottom p-4">
                                        <h6 class="text-success">Đặt Lịch thành công. Vui lòng chờ xác nhận</h6>
                                    </div>
                                    </c:if>
                                <div>
                                    <button  onsubmit="alert(${msgSuccess})" type="submit" class="m-0 border-0 py-4 font-14 font-weight-medium btn btn-danger-gradiant btn-block position-relative rounded-0 text-center text-white text-uppercase">
                                        <span>Đặt Lịch</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</section>

<script src="/js/scripts.js"></script>