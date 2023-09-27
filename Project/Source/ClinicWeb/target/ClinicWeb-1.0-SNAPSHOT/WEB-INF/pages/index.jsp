<%-- 
    Document   : index
    Created on : Jul 10, 2023, 6:44:29 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<link type="text/css" href="<c:url value="/css/index.css"/>" rel="stylesheet" />
<section id="carosel">
    <!-- Carousel -->
    <div id="demo" class="carousel slide" data-bs-ride="carousel">

        <!-- Indicators/dots -->
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#demo" data-bs-slide-to="0" class="active"></button>
            <button type="button" data-bs-target="#demo" data-bs-slide-to="1"></button>
            <button type="button" data-bs-target="#demo" data-bs-slide-to="2"></button>
            <button type="button" data-bs-target="#demo" data-bs-slide-to="3"></button>
            <button type="button" data-bs-target="#demo" data-bs-slide-to="4"></button>
        </div>

        <!-- The slideshow/carousel -->
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="<c:url value="/img/carosel/carosel_1.jpg" />" alt="Los Angeles" class="d-block w-100 carosel-img">
            </div>
            <div class="carousel-item">
                <img src="<c:url value="/img/carosel/carosel_2.jpg" />" alt="Los Angeles" class="d-block w-100 carosel-img">
            </div>
            <div class="carousel-item">
                <img src="<c:url value="/img/carosel/carosel_3.jpg" />" alt="Los Angeles" class="d-block w-100 carosel-img">
            </div>
            <div class="carousel-item">
                <img src="<c:url value="/img/carosel/carosel_4.jpg" />" alt="Los Angeles" class="d-block w-100 carosel-img">
            </div>
            <div class="carousel-item">
                <img src="<c:url value="/img/carosel/carosel_5.jpg" />" alt="Los Angeles" class="d-block w-100 carosel-img">
            </div>
        </div>

        <!-- Left and right controls/icons -->
        <button class="carousel-control-prev" type="button" data-bs-target="#demo" data-bs-slide="prev">
            <span class="carousel-control-prev-icon"></span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#demo" data-bs-slide="next">
            <span class="carousel-control-next-icon"></span>
        </button>
    </div>
</section>
<section id="introduce">
    <div class="introduce">
        <p class="text-center h1 text-muted">Liên Hệ Các Bác Sĩ Của Chúng Tôi</p>
        <h3 class="text-center"> Chuyên Nghiệp & <b>Tận Tình </b> </h2>
    </div>
</section>

<!-- department section -->

<section class="department_section layout_padding">
    <div class="department_container">

        <div class="heading_container heading_center">
            <h2>
                Our Departments
            </h2>
            <p>
                Asperiores sunt consectetur impedit nulla molestiae delectus repellat laborum dolores doloremque accusantium
            </p>
        </div>
        <div class="row">
            <div class="col-md-3">
                <div class="box ">
                    <div class="img-box">
                        <img src="img/s1.png" alt="">
                    </div>
                    <div class="detail-box">
                        <h5>
                            Cardiology
                        </h5>
                        <p>
                            fact that a reader will be distracted by the readable page when looking at its layout.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="box ">
                    <div class="img-box">
                        <img src="img/s2.png" alt="">
                    </div>
                    <div class="detail-box">
                        <h5>
                            Diagnosis
                        </h5>
                        <p>
                            fact that a reader will be distracted by the readable page when looking at its layout.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="box ">
                    <div class="img-box">
                        <img src="img/s3.png" alt="">
                    </div>
                    <div class="detail-box">
                        <h5>
                            Surgery
                        </h5>
                        <p>
                            fact that a reader will be distracted by the readable page when looking at its layout.
                        </p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="box ">
                    <div class="img-box">
                        <img src="img/s4.png" alt="">
                    </div>
                    <div class="detail-box">
                        <h5>
                            First Aid
                        </h5>
                        <p>
                            fact that a reader will be distracted by the readable page when looking at its layout.
                        </p>
                    </div>
                </div>
            </div>
        </div>

    </div>
</section>

<!-- end department section -->
<!-- Grid row -->
</div>
</section>
<section class="about_section layout_padding">
    <div class="container  ">
        <div class="row">
            <div class="col-md-6 ">
                <div class="img-box">
                    <img src="img/about-img.jpg" alt="">
                </div>
            </div>
            <div class="col-md-6">
                <div class="detail-box">
                    <div class="heading_container">
                        <h2>
                            About <span>Us</span>
                        </h2>
                    </div>
                    <p>
                        There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration
                        in some form, by injected humour, or randomised words which don't look even slightly believable. If you
                        are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in
                        the middle of text. All
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>