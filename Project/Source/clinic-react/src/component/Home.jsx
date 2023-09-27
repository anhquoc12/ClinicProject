import "../static/css/index.css";
import Image from "react-bootstrap/Image";
import Carousel from "react-bootstrap/Carousel";

const Home = () => {
    return (
        <>
            <Carousel>
                <Carousel.Item interval={1000}>
                    <Image className="d-block w-100 carosel-img" src='https://res.cloudinary.com/dvevyvqyt/image/upload/v1695459769/Clinic/carosel_1_czugtd.jpg' alt='no picture' />

                </Carousel.Item>
                <Carousel.Item interval={1000}>
                    <Image className="d-block w-100 carosel-img" src='https://res.cloudinary.com/dvevyvqyt/image/upload/v1695459769/Clinic/carosel_2_cvle5x.jpg' alt='no picture' />

                </Carousel.Item>
                <Carousel.Item interval={1000}>
                    <Image className="d-block w-100 carosel-img" src='https://res.cloudinary.com/dvevyvqyt/image/upload/v1695459769/Clinic/carosel_3_jk3hsi.jpg' alt='no picture' />

                </Carousel.Item>
                <Carousel.Item interval={1000}>
                    <Image className="d-block w-100 carosel-img" src='https://res.cloudinary.com/dvevyvqyt/image/upload/v1695459769/Clinic/carosel_4_xdpb0h.jpg' alt='no picture' />

                </Carousel.Item>
                <Carousel.Item interval={1000}>
                    <Image className="d-block w-100 carosel-img" src='https://res.cloudinary.com/dvevyvqyt/image/upload/v1695459769/Clinic/carosel_5_pqdoch.jpg' alt='no picture' />

                </Carousel.Item>
            </Carousel>
            <section id="introduce">
                <div className="introduce">
                    <p className="text-center h1 text-muted">Liên Hệ Các Bác Sĩ Của Chúng Tôi</p>
                    <h3 className="text-center"> Chuyên Nghiệp & <b>Tận Tình </b> </h3>
                </div>
            </section>
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
                                    <Image src="https://res.cloudinary.com/dvevyvqyt/image/upload/v1695460825/Clinic/s1_evv26p.png" alt="no picture" />
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
                                    <Image src="https://res.cloudinary.com/dvevyvqyt/image/upload/v1695460825/Clinic/s2_nitwup.png" alt="no picture" />
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
                                    <Image src="https://res.cloudinary.com/dvevyvqyt/image/upload/v1695460826/Clinic/s3_xjf3db.png" alt="no picture" />
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
                                    <Image src="https://res.cloudinary.com/dvevyvqyt/image/upload/v1695460826/Clinic/s4_m5c2fi.png" alt="no picture" />
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
            <section class="about_section layout_padding">
                <div class="container  ">
                    <div class="row">
                        <div class="col-md-6 ">
                            <div class="img-box">
                                <Image src="https://res.cloudinary.com/dvevyvqyt/image/upload/v1695460981/Clinic/about-img_paekdf.jpg" alt="no picture" />
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
        </>
    );
};

export default Home;
