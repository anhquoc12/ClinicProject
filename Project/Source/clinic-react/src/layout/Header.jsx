import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { useContext, useEffect } from "react";
import { MyUserContext, UserContext } from "../App";
import Dropdown from "react-bootstrap/Dropdown";
import Image from "react-bootstrap/Image";
import { USER_ROLE, UserRole } from "../configs/Enum";
import { NavDropdown } from "react-bootstrap";

const Header = () => {
  const [currentUser, stateUser] = useContext(MyUserContext);
  const navigate = useNavigate();

  const logout = () => {
    stateUser({
      type: "logout",
    });

    navigate("/");
  };

  return (
    <Navbar className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="#home">Clinic Website</Navbar.Brand>
        <Navbar.Toggle />
        <Nav>
          <Link to="/" className="nav-item nav-link">
            Trang Chủ
          </Link>
          {currentUser !== null ? (
            <>
              {currentUser.userRole === USER_ROLE.PATIENT && (
                <>
                  <Link to="/" className="nav-item nav-link">
                    Đăng ký lịch khám
                  </Link>
                  <Link to="/" className="nav-item nav-link">
                    Lịch Sử Khám
                  </Link>
                </>
              )}
              {currentUser.userRole === USER_ROLE.NURSE && (
                <>
                  <NavDropdown title="Lịch hẹn" id="navbarScrollingDropdown">
                    <NavDropdown.Item href="#action4">
                      <Link to="/nurse/confirmed" className="nav-item nav-link">
                        Lịch hẹn đang chờ xác nhận
                      </Link>
                    </NavDropdown.Item>
                    <NavDropdown.Item href="#action4">
                      <Link to="/nurse/present" className="nav-item nav-link">
                        Xác nhận bệnh nhân đã đến
                      </Link>
                    </NavDropdown.Item>
                  </NavDropdown>
                  <Link to="/" className="nav-item nav-link">
                    Thanh toán
                  </Link>
                  <Link to="/" className="nav-item nav-link">
                    Lịch Trực
                  </Link>
                </>
              )}
              {currentUser.userRole === USER_ROLE.DOCTOR && (
                <>
                  <NavDropdown title="Khám bệnh" id="navbarScrollingDropdown">
                    <NavDropdown.Item href="#action4">
                      <Link to="/nurse/confirmed" className="nav-item nav-link">
                        Khám bệnh
                      </Link>
                    </NavDropdown.Item>
                    <NavDropdown.Item href="#action4">
                      <Link to="/nurse/present" className="nav-item nav-link">
                        Lịch sử khám
                      </Link>
                    </NavDropdown.Item>
                  </NavDropdown>
                  <Link to="/" className="nav-item nav-link">
                    Lịch Trực
                  </Link>
                </>
              )}
              {(currentUser.userRole === USER_ROLE.ADMIN ||
                currentUser.userRole === USER_ROLE.OWNER) && (
                <>
                <NavDropdown
                    title="Danh sách người dùng"
                    id="navbarScrollingDropdown"
                  >
                    <NavDropdown.Item href="#action4">
                      <Link to="/admin/user/doctors" className="nav-item nav-link">
                        Bác sỹ
                      </Link>
                    </NavDropdown.Item>
                    <NavDropdown.Item href="#action4">
                      <Link to="/admin/user/nurses" className="nav-item nav-link">
                        Y Tá
                      </Link>
                    </NavDropdown.Item>
                    <NavDropdown.Item href="#action4">
                      <Link to="/admin/user/patients" className="nav-item nav-link">
                        Bệnh Nhân
                      </Link>
                    </NavDropdown.Item>
                  </NavDropdown>
                  <NavDropdown
                    title="Danh mục thuốc"
                    id="navbarScrollingDropdown"
                  >
                    <NavDropdown.Item href="#action4">
                      <Link to="/admin/medicine" className="nav-item nav-link">
                        thuốc
                      </Link>
                    </NavDropdown.Item>
                    <NavDropdown.Item href="#action4">
                      <Link to="/admin/medicine/categories" className="nav-item nav-link">
                        Loại thuốc
                      </Link>
                    </NavDropdown.Item>
                    <NavDropdown.Item href="#action4">
                      <Link to="/admin/medicine/unit" className="nav-item nav-link">
                        Đơn vị thuốc
                      </Link>
                    </NavDropdown.Item>
                  </NavDropdown>
                  <Link to="/admin/schedule" className="nav-item nav-link">
                    Lịch Trực
                  </Link>
                </>
              )}
            </>
          ) : (
            <span></span>
          )}
        </Nav>
        <Navbar.Collapse className="justify-content-end">
          <Nav>
            {currentUser === null ? (
              <Link to="/login" className="btn btn-outline-dark">
                Đăng Nhập
              </Link>
            ) : (
              <Dropdown>
                <Dropdown.Toggle variant="light" id="dropdown-basic">
                  <Image
                    src={currentUser.avatar}
                    alt="avatar"
                    roundedCircle
                    style={{ width: 30 }}
                  />
                </Dropdown.Toggle>

                <Dropdown.Menu style={{ left: "-96px" }}>
                  <Dropdown.Item>
                    <Link className="nav-link text-dark">
                      {currentUser.fullName}
                    </Link>
                  </Dropdown.Item>
                  <Dropdown.Item>
                    <Link className="nav-link text-dark" to="/account-setting">
                      Thông tin tài khoản
                    </Link>
                  </Dropdown.Item>
                  <Dropdown.Item>
                    <span className="nav-link text-dark" onClick={logout}>
                      Đăng xuất
                    </span>
                  </Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
