import { Link, Navigate } from "react-router-dom";
import "../static/css/login.css";
import FloatingLabel from "react-bootstrap/FloatingLabel";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { useContext, useState } from "react";
import Apis, { api, authUser, contentType, endpoints } from "../configs/Apis";
import cookie from "react-cookies";
import { MyUserContext} from "../App";
import { Alert } from "react-bootstrap";

const Login = () => {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [err, setErr] = useState(false);
  const [currentUser, stateUser] = useContext(MyUserContext);

  const login = (evt) => {
    evt.preventDefault();
    const process = async () => {
      try {
        let response = await api(contentType['json']).post(endpoints["login"], {
          username: username,
          password: password,
        });
        cookie.save('token', response.data)
        let {data} = await authUser().get(
          endpoints["authentication"]["current-user"]
        );
        cookie.save('user', data)
        stateUser({
          "type": "login",
          "payload": data
        })
      } catch (ex) {
        console.log(ex)
        setErr(true);
      }
    };

    process();
  };

  if (currentUser !== null) {
    return (<Navigate to='/' />)
  }

  return (
    <>
      <div className="wid50 boxLogin">
        <Form onSubmit={login}>
          <h1 className="mb-3 title">Đăng Nhập</h1>
          {err && (
            <Alert variant="danger">
              Tài khoản hoặc mật khẩu không chính xác Vui lòng thử lại
            </Alert>
          )}
          <FloatingLabel
            controlId="floatingInput"
            label="username"
            className="mb-3"
          >
            <Form.Control
              type="text"
              placeholder="Nhập tên tài khoản"
              onChange={(e) => setUsername(e.target.value)}
              style={{cursor: 'text'}}
            />
          </FloatingLabel>
          <FloatingLabel controlId="floatingPassword" label="Password">
            <Form.Control
              type="password"
              placeholder="Nhập mật khẩu"
              onChange={(e) => setPassword(e.target.value)}
              style={{cursor: 'text'}}
            />
          </FloatingLabel>
          <Container className="mt-4">
            <Row>
              <Col>
                <h5>
                  <Link to="/" className="text-danger">
                    Quên mật khẩu
                  </Link>
                </h5>
              </Col>
              <Col style={{ textAlign: "right" }}>
                <Button variant="outline-success" type="submit">
                  Đăng Nhập
                </Button>
              </Col>
            </Row>
          </Container>
          <hr />
          <h5 className="text-dark">
            Bạn Chưa có tài khoản?{" "}
            <Link to="/register">
              <b>Đăng Nhập tại đây!</b>
            </Link>
          </h5>
        </Form>
      </div>
    </>
  );
};

export default Login;
