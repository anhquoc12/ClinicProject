import Container from "react-bootstrap/Container";
import Navbar from "react-bootstrap/Navbar";
import Nav from "react-bootstrap/Nav";
import { Link, Navigate } from "react-router-dom";
import { useContext, useEffect } from "react";
import { MyUserContext, UserContext } from "../App";
import Dropdown from 'react-bootstrap/Dropdown';
import Image from 'react-bootstrap/Image';

const Header = () => {
  const [currentUser, stateUser] = useContext(MyUserContext);

  const logout = () => {
    stateUser({
      'type': 'logout'
    })
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
