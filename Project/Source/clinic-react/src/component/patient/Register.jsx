import { Button, Col, Form, Row, Toast } from "react-bootstrap";
import "../../static/css/register.css";
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import Apis, { api, contentType, endpoints } from "../../configs/Apis";
import Loading from "../../layout/Loading";

const Register = () => {
  const [patient, setPatient] = useState({
    fullName: "",
    username: "",
    password: "",
    email: "",
    phone: "",
    address: "",
  });
  const [avatar, setAvatar] = useState(null);
  const navigate = useNavigate();
  const [errors, setErrors] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showA, setShowA] = useState(true);

  // Function
  const toggleShowA = () => {
    setShowA(!showA);
    setErrors([]);
  };
  const changeUser = (field, value) => {
    setPatient((current) => {
      return { ...current, [field]: value };
    });
  };

  const addPatient = async (evt) => {
    evt.preventDefault();
    let params = new FormData();
    try {
      for (let field in patient) {
        params.append(field, patient[field]);
      }
      params.append("file", avatar);

      console.log(patient);
      setLoading(true);
      let res = await api(contentType['form']).post(endpoints["user"]["add-patient"], params);
      if (res.status === 201) {
        alert(res.data["message"]);
        navigate("/login");
      }
    } catch (ex) {
      let message = ex.response["data"]["data"];
      setErrors(message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {errors.length > 0 &&
        errors.map((error, index) => { return(
               <>
                   <Toast show={showA} onClose={toggleShowA} className="alert">
                 <Toast.Header>
                   <img
                     src="holder.js/20x20?text=%20"
                     className="rounded me-2"
                     alt=""
                   />
                   <strong className="me-auto">Lỗi thông tin</strong>
                 </Toast.Header>
                 <Toast.Body>
                   {error}
                 </Toast.Body>
               </Toast>
               </>
             )}
           )}
      <div className="wid60 form-data">
        <h1 className="title text-info">Đăng Ký tài khoản</h1>
        <hr />
        <Form onSubmit={addPatient}>
          <Form.Group as={Row} className="mb-3" controlId="formPlaintext">
            <Form.Label column sm="3">
              Họ Và Tên
            </Form.Label>
            <Col sm="9">
              <Form.Control
                type="text"
                onChange={(evt) => changeUser("fullName", evt.target.value)}
                value={patient["fullName"]}
              />
            </Col>
          </Form.Group>

          <Form.Group as={Row} className="mb-3" controlId="formPlaintext">
            <Form.Label column sm="3">
              username
            </Form.Label>
            <Col sm="4">
              <Form.Control
                type="text"
                onChange={(evt) => changeUser("username", evt.target.value)}
                value={patient["username"]}
              />
            </Col>
            <Form.Label column sm="2">
              password
            </Form.Label>
            <Col sm="3">
              <Form.Control
                type="password"
                onChange={(evt) => changeUser("password", evt.target.value)}
                value={patient["password"]}
              />
            </Col>
          </Form.Group>
          <Form.Group as={Row} className="mb-3" controlId="formPlaintextEmail">
            <Form.Label column sm="3">
              email
            </Form.Label>
            <Col sm="9">
              <Form.Control
                type="email"
                onChange={(evt) => changeUser("email", evt.target.value)}
                value={patient["email"]}
              />
            </Col>
          </Form.Group>
          <Form.Group as={Row} className="mb-3" controlId="formPlaintextPhone">
            <Form.Label column sm="3">
              số điện thoại
            </Form.Label>
            <Col sm="9">
              <Form.Control
                type="text"
                onChange={(evt) => changeUser("phone", evt.target.value)}
                value={patient["phone"]}
              />
            </Col>
          </Form.Group>
          <Form.Group
            as={Row}
            className="mb-3"
            controlId="formPlaintextAddress"
          >
            <Form.Label column sm="3">
              Địa chỉ
            </Form.Label>
            <Col sm="9">
              <Form.Control
                value={patient["address"]}
                type="text"
                onChange={(evt) => changeUser("address", evt.target.value)}
              />
            </Col>
          </Form.Group>
          <Form.Group as={Row} className="mb-3">
            <Form.Label column sm="3">
              Ảnh đại diện
            </Form.Label>
            <Col sm="9">
              <Form.Control
                required
                type="file"
                onChange={(evt) => setAvatar(evt.target.files[0])}
              />
            </Col>
          </Form.Group>
          <Form.Group as={Row} className="mb-3" controlId="formPlaintextEmail">
            <Form.Label column sm="7">
              <h5 className="text-secondary">
                Bạn đã có tài khoản?{" "}
                <Link className="text-danger" to="/login">
                  Đăng nhập tại đây
                </Link>
              </h5>
            </Form.Label>
            <Col sm="3"></Col>
            <Col sm="2">
              {loading && <Loading />}
              <Button type="submit" variant="outline-success">
                Đăng ký
              </Button>
            </Col>
          </Form.Group>
        </Form>
      </div>
    </>
  );
};
export default Register;
