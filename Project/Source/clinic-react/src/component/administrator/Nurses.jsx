import { useEffect, useState } from "react"
import { Alert, Button, ButtonToolbar, Col, Form, Modal, Row } from "react-bootstrap"
import { authUser, endpoints } from "../../configs/Apis"
import { USER_ROLE } from "../../configs/Enum"
import Loading from "../../layout/Loading"
import AddNurse from "../models/AddNurse"
// import Addnurse from "../models/Addnurse"

const Nurses = () => {
    const [nurses, setNurses] = useState(null)
    const [name, setName] = useState('')
    const [show, setShow] = useState(false);
    const [toggle, setToggle] = useState(false)
    const [nurse, setNurse] = useState({
        'id': null,
        "fullName": "",
        "address": "",
        "phone": "",
        "email": ""
    })
    const [remove, setRemove] = useState(false)

  const handleClose = () => {
    nurse['id'] = null
    nurse['fullName'] = ''
    nurse['address'] = ''
    nurse['phone'] = ''
    nurse['email'] = ''
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
    nurse['id'] = d[0]
    nurse['fullName'] = d[2]
    nurse['address'] = d[3]
    nurse['phone'] = d[5]
    nurse['email'] = d[4]
    handleShow()
  }

    useEffect(() => {
        const process = async() => {
            try {
                let api = endpoints['user']['list-by-role'](USER_ROLE.NURSE)
                if(name !== '') api += `?name=${name}`
                console.log(api)
                let res = await authUser().get(api)
                console.log(res.data)
                setNurses(res.data)
            } catch(ex) {console.log(ex)}
        }

        process()
        setRemove(false)
    }, [name, toggle, remove])

    if(nurses === null) return <Loading />

    return (<>
        <Alert variant="info">Danh Sách Y tá</Alert>
        <Form>
        
          <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
          <Col sm={1}></Col>
            <Form.Label column sm={2}>
              Tên Y Tá
            </Form.Label>
            <Col sm={2}>
              <Form.Control
                type="text"
                placeholder="nhập từ khoá..."
                name="name" onChange={evt => setName(evt.target.value)}
              />
            </Col>
            <Col sm={5}>
              
            </Col>
            <Col sm={2}>
              <Button variant="success" onClick={handleShow}>Thêm Y tá</Button>
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
                    <th colSpan={2}>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    nurses.map(n => {
                        return <tr>
                        <td>{n[2]}</td>
                        <td>{n[3]}</td>
                        <td>{n[5]}</td>
                        <td>{n[4]}</td>
                        <td><Button variant="warning" onClick={() => updateItem(n)}>Cập nhật</Button></td>
                        <td><Button variant="danger" onClick={() => deleteItem(n[0])}>Xoá</Button></td>
                    </tr>
                    })
                }
            </tbody>
        </table>

        <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Y tá</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <AddNurse nurse={nurse} />
            {/* <Addnurse Nurse={nurse} /> */}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>)
}

export default Nurses