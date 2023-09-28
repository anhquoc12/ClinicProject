import { Button, FloatingLabel, Form, Image } from "react-bootstrap";
import "../static/css/changepassword.css";
import { useContext, useState } from "react";
import { MyUserContext } from "../App";
import { Link, Navigate } from "react-router-dom";
import { authUser, endpoints } from "../configs/Apis";

const ChangePassword = () => {
  const [currentUser, stateUser] = useContext(MyUserContext);
  const [newPassword, setNewPassword] = useState("")
  const [currentPassword, setCurrentPassword] = useState("")
  const [success, setSuccess] = useState(false)

  const changePassword = async(evt) => {
    evt.preventDefault()
    if (currentPassword.length === '') {
        alert('Mật khẩu hiện tại không được chứa rỗng')
        return
    }
    if(currentPassword === newPassword) {
        alert('Mật khẩu hiện tại và mật khẩu mới phải khác nhau')
        return
    }
    if(newPassword.length < 8) {
        alert('Mật khẩu phải chứa 8 ký tự')
        return
    }

    try {
        let form = new FormData()
        form.append('current', currentPassword)
        form.append('new', newPassword)
        console.log(currentPassword, newPassword)
        let res = await authUser().post(endpoints['change-password'], form)
        alert(res.data['message'])
        setSuccess(true)
    } catch (ex) {
        alert('Có lỗi xảy ra!!! Vui lòng thử lại')
    }
        
  }

  
  if(success) {
    return <Navigate to='/account-setting' />
  }

  return (
    <div className="container bootstrap snippets bootdey box">
      <div className="row">
        <Form className="col-xs-12 col-sm-12 col-md-6 col-md-offset-2" onSubmit={changePassword}>
          <div className="panel panel-info">
            <div className="panel-heading">
              <h3 className="panel-title">
                <span className="glyphicon glyphicon-th"></span>
                Đổi Mật Khẩu
              </h3>
            </div>
            <div className="panel-body">
              <div className="row">
                <div className="col-xs-6 col-sm-6 col-md-6 separator social-login-box">
                  {" "}
                  <br />
                  <Image className="img-thumbnail" src={currentUser.avatar} />
                </div>
                <div
                  style={{ marginTop: 80, width: "50%" }}
                  className="col-xs-6 col-sm-6 col-md-6 login-box"
                >
                  <FloatingLabel
                    controlId="floatingInput"
                    label="Mật khẩu hiện tại"
                    className="mb-3"
                  >
                    <Form.Control
                      type="password"
                      placeholder="Mật khẩu hiện tại"
                      style={{ width: "100%" }}
                      onChange={evt => setCurrentPassword(evt.target.value)}
                    />
                  </FloatingLabel>
                  <FloatingLabel
                    controlId="floatingPassword"
                    label="Mật khẩu mới (Tối thiểu 8 ký tự)"
                  >
                    <Form.Control
                      type="password"
                      placeholder="Mật khẩu mới"
                      style={{ width: "100%" }}
                      onChange={evt => setNewPassword(evt.target.value)}
                    />
                  </FloatingLabel>

                  <Link className="text-danger" style={{textDecoration: 'none'}}>Quên mật khẩu</Link>
                </div>
              </div>
            </div>
            <div className="panel-footer">
              <div className="row">
                <div className="col-xs-6 col-sm-6 col-md-6"></div>
                <div className="col-xs-6 col-sm-6 col-md-6">
                  <Button type="submit" variant="success" style={{ width: 100 }}>
                    Lưu
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </Form>
      </div>
    </div>
  );
};

export default ChangePassword;
