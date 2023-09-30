import { Alert } from "react-bootstrap";
import "../static/css/forbidden.css";
import { Link } from "react-router-dom";

const Forbidden = () => {
  return (
    <>
      <div class="text-wrapper">
        <div class="title-forbidden" data-content="404">
          403 - Truy cập bị từ chối
        </div>

        <div class="subtitle">
          Oops, Bạn không có quyền truy cập vào đường dẫn này.
        </div>
        <div class="isi">
          Bạn không có đủ quyền để truy cập vào trang web này. Vui lòng đăng nhập với tài khoản đủ quyền để vào.
        </div>
      </div>
      

    </>
  );
};

export default Forbidden;
