import { useContext, useEffect, useRef, useState } from "react";
import { MyUserContext } from "../App";
import { Alert, Button, Form, Image } from "react-bootstrap";
import { Link } from "react-router-dom";
import { authUser, contentType, endpoints } from "../configs/Apis";
import Loading from "../layout/Loading";
import cookie from 'react-cookies'

const InfoAcount = () => {
  const [currentUser, stateUser] = useContext(MyUserContext);

  const [user, setUser] = useState(currentUser);
  const [avatar, setAvatar] = useState({
    url: user.avatar,
    file: null,
  });
  const [isChange, setIsChange] = useState(false);
  const [loading, setLoading] = useState(false);
  const [errors , setErrors] = useState([])

  //   Function

  const changeValue = (field, value) => {
    setUser((current) => {
      return { ...current, [field]: value };
    });
    setIsChange(true);
  };

  const changeAvatar = (value) => {
    if (!value["type"].startsWith("image/")) {
      alert("File bạn vừa chọn không phải là ảnh");
    }
    setAvatar({
      url: URL.createObjectURL(value),
      file: value,
    });
    setIsChange(true);
  };

  const saveChange = async (evt) => {
    evt.preventDefault();

    let form = new FormData();
    try {
      setLoading(true);
      for (let field in user) {
        form.append(field, user[field])
      }
      if(avatar['file'] !== null)
        form.append('file', avatar['file'])
      let response = await authUser(contentType["form"]).post(
        endpoints["update-user"],
        form
      )
      if (response.status === 202) alert(response.data["message"])
      cookie.save('user', response.data['data'])
      stateUser({
        'type': 'login',
        'payload': cookie.load('user')
      })
      setIsChange(false);
      setErrors([])
    } catch (ex) {
      let message = ex.response['data']['data']
      setErrors(message)
      setIsChange(true);
    } finally {
      setLoading(false)
    }
  };

  useEffect(() => {

  }, [errors])

  return (
    <>
      <div class="container-xl px-4 mt-4">
        <Alert variant="info">
          <h1>Thông tin tài khoản</h1>
        </Alert>
        <nav class="nav nav-borders">
          <Link class="nav-link active ms-0" to="/account-setting">
            Profile
          </Link>
          <Link
            class="nav-link"
            href="https://www.bootdey.com/snippets/view/bs5-profile-billing-page"
          >
            Đổi mật khẩu
          </Link>
        </nav>
        <hr class="mt-0 mb-4" />
        {errors.length > 0 && <Alert variant="danger">
        {errors.map(error => {
          return <>{error} <br /></>
        })}
        </Alert>}
        <div class="row">
          <div class="col-xl-4">
            <div class="card mb-4 mb-xl-0">
              <div class="card-header">Ảnh đại diện</div>
              <div class="card-body text-center">
                <Image
                  className="img-account-profile rounded-circle mb-2"
                  src={avatar["url"]}
                  alt="avatar"
                  style={{ width: 255, height: 255 }}
                />

                <div class="small font-italic text-muted mb-4">
                  Cập nhật ảnh đại diện mới
                </div>

                <input
                  type="file"
                  id="file"
                  name="file"
                  style={{ display: "none" }}
                  onChange={(evt) => changeAvatar(evt.target.files[0])}
                />
                <label
                  htmlFor="file"
                  className="custom-file-input btn btn-primary"
                  style={{ cursor: "pointer" }}
                >
                  Chọn ảnh khác
                </label>
              </div>
            </div>
          </div>
          <div class="col-xl-8">
            <div class="card mb-4">
              <div class="card-header">Thông tin chi tiết</div>
              <div class="card-body">
                <Form onSubmit={saveChange}>
                  <div class="mb-3">
                    <label class="small mb-1">Tên tài khoản</label>
                    <Form.Control
                      class="form-control"
                      type="text"
                      value={user.username}
                      onChange={(evt) =>
                        changeValue("username", evt.target.value)
                      }
                    />
                  </div>
                  <div class="row gx-3 mb-3">
                    <div class="col-md-12">
                      <label class="small mb-1">Họ Tên</label>
                      <Form.Control
                        type="text"
                        value={user.fullName}
                        onChange={(evt) =>
                          changeValue("fullName", evt.target.value)
                        }
                      />
                    </div>
                  </div>
                  <div class="row gx-3 mb-3">
                    <div class="col-md-6">
                      <label class="small mb-1">Số điện thoại</label>
                      <Form.Control
                        class="form-control"
                        type="text"
                        value={user.phone}
                        onChange={(evt) =>
                          changeValue("phone", evt.target.value)
                        }
                      />
                    </div>
                    <div class="col-md-6">
                      <label class="small mb-1">Địa chỉ</label>
                      <Form.Control
                        class="form-control"
                        type="text"
                        value={user.address}
                        onChange={(evt) =>
                          changeValue("address", evt.target.value)
                        }
                      />
                    </div>
                  </div>
                  <div class="mb-3">
                    <label class="small mb-1">Email</label>
                    <Form.Control
                      class="form-control"
                      type="email"
                      value={user.email}
                      onChange={(evt) => changeValue("email", evt.target.value)}
                    />
                  </div>
                  <div class="row gx-3 mb-3">
                    <div class="col-md-6">
                      <label class="small mb-1">Chức vụ</label>
                      <Form.Control
                        class="form-control"
                        type="tel"
                        readOnly
                        value={user.userRole}
                      />
                    </div>
                  </div>
                  {isChange ? (
                    loading?
                       <Button variant="primary" type="submit" disabled>
                        <Loading />
                        Loading...
                       </Button>:
                      <Button variant="outline-primary" type="submit">
                      Lưu Thay đổi
                      </Button>)
                    :<span></span>
                  }
                </Form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default InfoAcount;
