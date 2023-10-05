import { useEffect, useState } from "react"
import { Alert, Button, ButtonToolbar, Col, Form, Modal, Row } from "react-bootstrap"
import { authUser, endpoints } from "../../configs/Apis"
import { USER_ROLE } from "../../configs/Enum"
import Loading from "../../layout/Loading"
import AddDoctor from "../models/AddDoctor"
import { Link } from "react-router-dom"

const Doctors = () => {
    const [doctors, setDoctors] = useState(null)
    const [name, setName] = useState('')
    const [show, setShow] = useState(false);
    const [toggle, setToggle] = useState(false)
    const [doctor, setDoctor] = useState({
        'id': null,
        "fullName": "",
        "address": "",
        "phone": "",
        "email": "",
        "specialId": null
    })
    const [remove, setRemove] = useState(false)

  const handleClose = () => {
    doctor['id'] = null
    doctor['fullName'] = ''
    doctor['address'] = ''
    doctor['phone'] = ''
    doctor['email'] = ''
    doctor['specialId'] = null
    setToggle(!toggle)
    setShow(false)
  };
  const handleShow = () => setShow(true);
  const deleteItem = async(item) => {
    try {
        /* eslint-disable no-restricted-globals */
      if (confirm("Bạn có muốn xoá cái này không?")) {
        let res = await authUser().delete(endpoints['user']['delete'](item))
        alert(res.data['message'])

      }
      setRemove(true)
    } catch(ex){alert('CÓ lỗi xảy ra. Vui lòng thử lại')}
  }

  const updateItem = (d) => {
    doctor['id'] = d[0]
    doctor['fullName'] = d[2]
    doctor['address'] = d[3]
    doctor['phone'] = d[5]
    doctor['email'] = d[4]
    doctor['specialId'] = d[7]
    handleShow()
  }

    useEffect(() => {
        const process = async() => {
            try {
                let api = endpoints['user']['list-by-role'](USER_ROLE.DOCTOR)
                if(name !== '') api += `?name=${name}`
                console.log(api)
                let res = await authUser().get(api)
                console.log(res.data)
                setDoctors(res.data)
            } catch(ex) {console.log(ex)}
        }

        process()
        setRemove(false)
    }, [name, toggle, remove])

    if(doctors === null) return <Loading />

    return (<>
        <Alert variant="info">Danh Sách Bác sỹ</Alert>
        <Form>
        
          <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
          <Col sm={1}></Col>
            <Form.Label column sm={2}>
              Tên Bác sỹ
            </Form.Label>
            <Col sm={2}>
              <Form.Control
                type="text"
                placeholder="nhập từ khoá..."
                name="name" onChange={evt => setName(evt.target.value)}
              />
            </Col>
            <Col sm={1}></Col>
            <Col sm={2}>
              <Button variant="success" onClick={handleShow}>Thêm Bác sỹ</Button>
            </Col>
            <Col sm={2}>
              <Link to='/admin/specializations' className="btn btn-outline-primary">Quản lý khoa</Link>
            </Col>
          </Form.Group>
        </Form>

        <table style={{width: '100%'}}>
            <thead>
                <tr>
                    <th>Họ và tên</th>
                    <th>địa chỉ</th>
                    <th>Số điện thoại</th>
                    <th>email</th>
                    <th>Chuyên khoa</th>
                    <th colSpan={2}>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    doctors.map(d => {
                        return <tr>
                        <td>{d[2]}</td>
                        <td>{d[3]}</td>
                        <td>{d[5]}</td>
                        <td>{d[4]}</td>
                        <td>{d[6]}</td>
                        <td><Button variant="warning" onClick={() => updateItem(d)}>Cập nhật</Button></td>
                        <td><Button variant="danger" onClick={() => deleteItem(d[0])}>Xoá</Button></td>
                    </tr>
                    })
                }
            </tbody>
        </table>

        <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Bác Sỹ</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <AddDoctor doctor={doctor} />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>)
}

export default Doctors