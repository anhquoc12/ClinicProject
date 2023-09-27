<%-- 
    Document   : stat
    Created on : Aug 17, 2023, 4:20:46 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib  prefix="format" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link type="text/css" href="<c:url value="/css/style-database.css"/>" rel="stylesheet" />
<link type="text/css" href="<c:url value="/css/stat.css"/>" rel="stylesheet" />

<script src="<c:url value="/js/stat.js" />"></script>
<section id="main" class="main" onload="doughnut([<c:forEach items="${top5}" var="m"> '${m[0]}', </c:forEach>],
    [<c:forEach items="${top5}" var="m"> ${m[1]}, </c:forEach>])">

             <div onload="test(12)" class="pagetitle">
                 <h1>Thống Kê</h1>
             </div><!-- End Page Title -->

             <section class="section dashboard">
                 <div class="row">

                     <!-- Left side columns -->
                     <div class="col-lg-8">
                         <div class="row">

                             <!-- Sales Card -->
                             <div class="col-xxl-4 col-md-6">
                                 <div class="card info-card sales-card">



                                     <div class="card-body">
                                         <h5 class="card-title">Tổng số bệnh nhân</h5>

                                         <div class="d-flex align-items-center">
                                             <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                                 <i class="bi bi-people"></i>
                                             </div>
                                             <div class="ps-3">
                                                 <h6>${patient}</h6>

                                         </div>
                                     </div>
                                 </div>

                             </div>
                         </div><!-- End Sales Card -->

                         <!-- Revenue Card -->
                         <div class="col-xxl-4 col-md-6">
                             <div class="card info-card revenue-card">

                                 <div class="card-body">
                                     <h5 class="card-title">Doanh Thu</h5>

                                     <div class="d-flex align-items-center">
                                         <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                             <i class="fa-solid fa-chart-line"></i>
                                         </div>
                                         <div class="ps-3">
                                             <h6><format:formatNumber pattern="#.##" value="${subTotal / 1000000}" />M</h6>
                                         </div>
                                     </div>
                                 </div>

                             </div>
                         </div><!-- End Revenue Card -->

                         <!-- Customers Card -->
                         <div class="col-xxl-4 col-xl-12">

                             <div class="card info-card customers-card">

                                 <div class="card-body">
                                     <h5 class="card-title">Số Lượng Ca Khám</h5>

                                     <div class="d-flex align-items-center">
                                         <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                                             <i class="fa-solid fa-notes-medical"></i>
                                         </div>
                                         <div class="ps-3">
                                             <h6>${medical}</h6>

                                         </div>
                                     </div>

                                 </div>
                             </div>

                         </div><!-- End Customers Card -->

                         <!-- Reports -->
                         <div class="col-12">
                             <div class="card">

                                 <div class="card-body">
                                     <h5 class="card-title">Doanh thu</h5>
                                     <div class="row">
                                         <div class="col-5">
                                             <form action="<c:url value="/admin/stat/filter" />">
                                                 <input type="date" class="form-control" name="date" required="true"/>
                                                 <div class="group-box mt-3">
                                                     <span class="badge badge-info filed-group">Loại thống kê</span>
                                                     <div class="d-flex">
                                                         <div class="form-check rd-stat">
                                                             <input class="form-check-input" type="radio" name="typeStat" id="flexRadioDefault2" value="2" checked>
                                                             <label class="form-check-label" for="flexRadioDefault2">
                                                                 Tháng
                                                             </label>
                                                         </div>

                                                         <div class="form-check rd-stat">
                                                             <input class="form-check-input" type="radio" name="typeStat" id="flexRadioDefault1" value="3">
                                                             <label class="form-check-label" for="flexRadioDefault1">
                                                                 Quý
                                                             </label>
                                                         </div>

                                                         <div class="form-check rd-stat">
                                                             <input class="form-check-input" type="radio" name="typeStat" id="flexRadioDefault1" value="1">
                                                             <label class="form-check-label" for="flexRadioDefault1">
                                                                 Năm
                                                             </label>
                                                         </div>
                                                     </div>
                                                 </div>
                                                 <button class="btn btn-outline-success mt-3 filter-btn" type="submit">Lọc</button>
                                                 <input type="date" value="${dateIn}" readonly="true" />
                                             </form>
                                         </div>
                                     </div>
                                     <hr>
                                     <!-- Line Chart -->
                                     <div id="reportsChart"></div>
                                     <canvas id="revenue"></canvas>

                                     <!-- End Line Chart -->

                                 </div>

                             </div>
                         </div><!-- End Reports -->

                         



                     </div>
                 </div><!-- End Left side columns -->

                 <!-- Right side columns -->
                 <div class="col-lg-4">

                     <!-- Recent Activity -->




                     <!-- Website Traffic -->
                     <div class="card">

                         <div class="card-body pb-0">
                             <h5 class="card-title">Top 5 thuốc dùng nhiều nhất</h5>

                             <div id="trafficChart" style="min-height: 400px;" class="echart">
                                 <canvas id="top5"></canvas>
                             </div>



                         </div>
                         <div class="card-body pb-0">
                             <h5 class="card-title">Top 5 thuốc dùng ít nhất</h5>

                             <div id="trafficChart" style="min-height: 400px;" class="echart">
                                 <canvas id="bottom5"></canvas>
                             </div>



                         </div>
                     </div><!-- End Website Traffic -->

                     <!-- News & Updates Traffic -->


                 </div><!-- End Right side columns -->

             </div>
         </section>

</section>

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
                 window.onload = () => {
                 // Top 5
                 let ctx = document.getElementById('top5')
                         let lables = [], values = []
    <c:forEach items="${top5}" var="m">
                 lables.push('${m[0]}')
    </c:forEach>
    <c:forEach items="${top5}" var="m">
                 values.push(${m[1]})
    </c:forEach>
                 new Chart(ctx, {
                 type: 'doughnut',
                         data: {
                         labels: lables,
                                 datasets: [{
                                 label: 'Số Lượng đã dùng',
                                         data: values,
                                         borderWidth: 1
                                 }]
                         },
                         options: {
                         scales: {
                         y: {
                         beginAtZero: true
                         }
                         }
                         }
                 })
                         // Bottom 5
                         ctx = document.getElementById('bottom5')
                         lables = []
                         values = []
    <c:forEach items="${bottom5}" var="m">
                 lables.push('${m[0]}')
    </c:forEach>
    <c:forEach items="${bottom5}" var="m">
                 values.push(${m[1]})
    </c:forEach>
                 new Chart(ctx, {
                 type: 'doughnut',
                         data: {
                         labels: lables,
                                 datasets: [{
                                 label: 'Số lượng đã dùng',
                                         data: values,
                                         borderWidth: 1
                                 }]
                         },
                         options: {
                         scales: {
                         y: {
                         beginAtZero: true
                         }
                         }
                         }
                 })
                         // Revenue
                         ctx = document.getElementById('revenue')
                         lables = []
                         values = []
                         let amounts = []
                 <c:forEach items="${revenues}" var="i">
                 lables.push('${i[1]}')
    </c:forEach>
    <c:forEach items="${revenues}" var="i">
                 values.push('${i[0]}')
    </c:forEach>
    <c:forEach items="${amount}" var="a">
                 amounts.push('${a[0]}')
    </c:forEach>
                 new Chart(ctx, {
                 type: 'bar',
                         data: {
                         labels: lables,
                                 datasets: [{
                                 label: 'Doanh thu',
                                         data: values,
                                         backgroundColor: [
                                                 'rgba(255, 99, 132, 0.2)',
                                                 'rgba(255, 159, 64, 0.2)',
                                                 'rgba(255, 205, 86, 0.2)',
                                                 'rgba(75, 192, 192, 0.2)',
                                                 'rgba(54, 162, 235, 0.2)',
                                                 'rgba(153, 102, 255, 0.2)',
                                                 'rgba(201, 203, 207, 0.2)'
                                         ],
                                         borderColor: [
                                                 'rgb(255, 99, 132)',
                                                 'rgb(255, 159, 64)',
                                                 'rgb(255, 205, 86)',
                                                 'rgb(75, 192, 192)',
                                                 'rgb(54, 162, 235)',
                                                 'rgb(153, 102, 255)',
                                                 'rgb(201, 203, 207)'
                                         ],
                                         borderWidth: 1
                                 },
                                 {
                                 label: 'Số Lượng bệnh nhân tới khám',
                                         data: amounts,
                                         backgroundColor: [
                                                 'rgba(255, 159, 64, 0.2)',
                                                 'rgba(255, 205, 86, 0.2)',
                                                 'rgba(75, 192, 192, 0.2)',
                                                 'rgba(54, 162, 235, 0.2)',
                                                 'rgba(153, 102, 255, 0.2)',
                                                 'rgba(201, 203, 207, 0.2)',
                                                 'rgba(255, 99, 132, 0.2)'
                                         ],
                                         borderColor: [
                                                 'rgb(255, 159, 64)',
                                                 'rgb(255, 205, 86)',
                                                 'rgb(75, 192, 192)',
                                                 'rgb(54, 162, 235)',
                                                 'rgb(153, 102, 255)',
                                                 'rgb(255, 99, 132)',
                                                 'rgb(201, 203, 207)'
                                         ],
                                         borderWidth: 1
                                 },
                                 ]
                         },
                         options: {
                         scales: {
                         y: {
                         type: 'logarithmic', // Sử dụng tỷ lệ logarit cho trục Y
                                 beginAtZero: true,
                                 ticks: {
                                 callback: function(value, index, values) {
                                 return Number(value.toString()); // Hiển thị giá trị theo dạng số
                                 }
                                 }
                         }
                         }
                         }
                         })
                 }
</script>
