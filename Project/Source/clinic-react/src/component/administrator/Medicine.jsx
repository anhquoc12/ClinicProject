import { useEffect, useState } from "react";
import { Alert, Button, Col, Form, Modal, Row, Table } from "react-bootstrap";
import { authUser, endpoints } from "../../configs/Apis";
import { formatCurrency } from "../../utils/utils";
import Loading from "../../layout/Loading";
import { Link } from "react-router-dom";
import AddMedicine from "../models/AddMedicine";

const Medicine = () => {
  const [medicines, setMedicines] = useState(null);
  const [name, setName] = useState("");
  const [category, setCategoryItem] = useState(null);
  const [categories, setCategories] = useState(null);
  const [remove, isRemove] = useState(false);
  const [show, setShow] = useState(false);
  const [toggle, setToggle] = useState(false)
  const [medicine, setMedicine] = useState({
    'id': null,
    'name': '',
    'unitPrice': 0,
    'unitInStock': 0,
    'category': null,
    'unit': null
})

  const handleClose = () => {
    setShow(false)
    setToggle(!toggle)
    medicine['id'] = null
    medicine['name'] = ''
    medicine['unitPrice'] = 0
    medicine['unitInStock'] = 0
    medicine['category'] = null
    medicine['unit'] = null
  };
  const handleShow = () => setShow(true);
  const deleteItem = async (item) => {
    try {
        /* eslint-disable no-restricted-globals */
      if (confirm("Bạn có muốn xoá cái này không?")) {
        let res = await authUser().delete(
          endpoints["medicine"]["delete"](item)
        );
        alert(res.data["message"]);

      }
      isRemove(true)
    } catch {
      alert("Có lỗi xảy ra vui lòng thử lại");
    }
  };
  const changeValue = (field, value) => {
    setMedicine(current => {
        return({
            ...current, [field]: value
        })
    })
}
  const updateItem = (me) => {
    medicine['id'] = me.id
    medicine['name'] = me.name
    medicine['unitPrice'] = me.unitPrice
    medicine['unitInStock'] = me.unitInStock
    medicine['category'] = me.categoryId.id
    medicine['unit'] = me.unitMedicineId.id
    handleShow()
  }

  useEffect(() => {
    const loadMedicines = async () => {
      let res = await authUser().get(endpoints["medicine"]["list"]);
      if (name !== "") {
        setCategoryItem(null);
        res = await authUser().get(
          `${endpoints["medicine"]["list"]}?name=${name}`
        );
      }
      if (category !== null) {
        res = await authUser().get(
          `${endpoints["medicine"]["list"]}?category=${category}`
        );
      }
      setMedicines(res.data);
    };

    const loadCategories = async () => {
      let res = await authUser().get(endpoints["category"]["categories"]);
      setCategories(res.data);
    };

    loadMedicines();
    loadCategories();
    isRemove(false)
  }, [name, category, remove, toggle]);

  if (categories === null || medicines === null) return <Loading />;

  return (
    <>
      <div className="box-medicine">
        <Alert variant="secondary">Danh sách thuốc</Alert>
        <Form>
          <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
            <Form.Label column sm={2}>
              Tên thuốc
            </Form.Label>
            <Col sm={2}>
              <Form.Control
                type="text"
                placeholder="nhập từ khoá..."
                name="name"
                onChange={(evt) => setName(evt.target.value)}
                value={name}
              />
            </Col>
            <Form.Label column sm={2}>
              Loại thuốc
            </Form.Label>
            <Col sm={2}>
              <Form.Select
                onChange={(evt) => setCategoryItem(evt.target.value)}
              >
                {categories.map((c) => {
                  return <option value={c.id}>{c.name}</option>;
                })}
              </Form.Select>
            </Col>
            <Col sm={2}>
              <Button variant="outline-success" onClick={handleShow}>Thêm thuốc</Button>
            </Col>
          </Form.Group>
        </Form>
        <div>
          <table>
            <thead>
              <tr>
                <th style={{ width: "20%" }}>
                  Tên Thuốc
                </th>
                <th style={{ width: "5%" }}>
                  Đơn giá
                </th>
                <th style={{ width: "10%" }}>
                  Số lượng
                </th>
                <th style={{ width: "20%" }}>
                  loại thuốc
                </th>
                <th style={{ width: "10%" }}>
                  Đơn vị dùng
                </th>
                <th
                 
                  colSpan="2"
                  style={{ width: "35%" }}
                >
                  Actions
                </th>
              </tr>
            </thead>
            <tbody>
              {medicines.map((m) => {
                return (
                  <tr className=".content-table">
                    <td>{m.name}</td>
                    <td>{formatCurrency(m.unitPrice)}</td>
                    <td>{m.unitInStock}</td>
                    <td>{m.categoryId.name}</td>
                    <td>{m.unitMedicineId.name}</td>
                    <td>
                      <Button variant="warning" onClick={() => updateItem(m)}>Cập nhật</Button>
                    </td>
                    <td>
                      <Button variant="danger" onClick={() => deleteItem(m.id)}>Xoá</Button>
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Thuốc</Modal.Title>
        </Modal.Header>
        <Modal.Body>
              <AddMedicine medicine={medicine} />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};

export default Medicine;
