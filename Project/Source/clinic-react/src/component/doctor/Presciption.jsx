import { Alert, Col, Form, Row } from "react-bootstrap";

const Prescription = () => {
  return (
    <>
      <div className="box-medicine">
        <Alert variant="secondary">Danh sách thuốc</Alert>
        <Form>
          <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
            <Form.Label column sm={2}>
              Tên thuốc
            </Form.Label>
            <Col sm={3}>
              <Form.Control type="text" placeholder="nhập từ khoá..." />
            </Col>
            <Form.Label column sm={2}>
              Loại thuốc
            </Form.Label>
            <Col sm={3}>
              <Form.Control type="email" placeholder="Email" />
            </Col>
          </Form.Group>
        </Form>
        <table className="table table-borderless table-responsive">
          <thead>
            <tr>
              <th className="text-muted text-center" style={{ width: "10%" }}>
                Ngày Thực Hiện
              </th>
              <th className="text-muted text-center" style={{ width: "10%" }}>
                Ngày Khám Bệnh
              </th>
              <th className="text-muted text-center" style={{ width: "10%" }}>
                Status
              </th>
              <th className="text-muted text-center" style={{ width: "40%" }}>
                Y Tá xác nhận
              </th>
              <th style={{ width: "10%" }}></th>
            </tr>
          </thead>
          <tbody>
            <tr className="align-middle alert text-center" role="alert">
              <td></td>
              <td></td>
              <td>
                <div className="d-inline-flex align-items-center"></div>
              </td>
              <td className="info"></td>
            </tr>
          </tbody>
        </table>
      </div>
    </>
  );
};
