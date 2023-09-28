import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { useContext, useEffect } from "react";
import { MyUserContext, UserContext } from "../App";
import Dropdown from 'react-bootstrap/Dropdown';
import Image from 'react-bootstrap/Image';
import {UserRole} from '../configs/Enum'

const Header = () => {
  const [currentUser, stateUser] = useContext(MyUserContext);
  const navigate = useNavigate()

  const logout = () => {
    stateUser({
      'type': 'logout'
    })

    navigate('/')
  }

  

  return (
    <Navbar className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="#home">Clinic Website</Navbar.Brand>
        <Navbar.Toggle />
        <Nav>
          <Link to="/" className="nav-item nav-link">
            Trang Chủ
          </Link>
          {/* Menu của bệnh nhân */}
          {/* {currentUser.userRole === UserRole.PATIENT && <>
            <Link to="/" className="nav-item nav-link">
            Đăng ký lịch khám
          </Link>
          <Link to="/" className="nav-item nav-link">
            Lịch Sử Khám
          </Link> */}
          {/* </>} */}
          {/* Menu của y tá */}
          {/* Menu của bác sỹ */}
        </Nav>
        <Navbar.Collapse className="justify-content-end">
          <Nav>
            {currentUser === null?
              <Link to="/login" className="btn btn-outline-dark">
                Đăng Nhập
              </Link>:
              <Dropdown>
                <Dropdown.Toggle variant="light" id="dropdown-basic">
                  <Image src={currentUser.avatar} alt='avatar' roundedCircle style={{width: 30}} />
                </Dropdown.Toggle>

                <Dropdown.Menu style={{left: '-96px'}}>
                  <Dropdown.Item>
                  <Link className="nav-link text-dark">{currentUser.fullName}</Link>
                  </Dropdown.Item>
                  <Dropdown.Item>
                    <Link className="nav-link text-dark" to='/account-setting'>Thông tin tài khoản</Link>
                  </Dropdown.Item>
                  <Dropdown.Item>
                  <span className="nav-link text-dark" onClick={logout}>Đăng xuất</span>
                  </Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>
            }
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
