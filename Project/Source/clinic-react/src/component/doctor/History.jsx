import { useEffect, useState } from "react"
import { Alert, Button, Col, Form, Row } from "react-bootstrap"
import { authUser, endpoints } from "../../configs/Apis"
import Loading from "../../layout/Loading"

const History = () => {
    const [history, setHistory] = useState(null)
    const [patient, setPatient] = useState(
        {
            'value': null,
            'isChanged': false
        }
    )
    const [date, setDate] = useState({
        'value': null,
        'isChanged': false
    })
    const [patients, setPatients] = useState(null)


    const changeDate = (field, value) => {
        setDate(current => {
            return {...current, [field]: value}
        })
        date['isChanged'] = true
    }
    const changePatient= (field, value) => {
        setPatient(current => {
            return {...current, [field]: value}
        })
        patient['isChanged'] = true
    }

    useEffect(() => {
        const process = async() => {
            try {
                console.log(patient, date)
                let api = `${endpoints['medical']['history']}`
                if(date['isChanged'])
                    api += `?date=${date['value']}`
                else if (patient['isChanged'])
                    api += `?patient=${patient['value']}`
                console.log(api)
                let res = await authUser().get(api)
                setHistory(res.data)
            } catch(ex) {console.log(ex)}
        }

        const loadPatient = async() => {
            try {
                let res = await authUser().get(endpoints['user']['list-patient'])
                setPatients(res.data)
            } catch(ex) {console.log(ex)}
        }

        process()
        loadPatient()
        date['isChanged'] = false
        patient['isChanged'] = false
    }, [date, patient])

    if(history === null) return <Loading />

    return(<>
    <Alert variant="info">Lịch sử khám</Alert>
        <Form className="mt-5">
          <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
          <Form.Label column sm={2}>
              
            </Form.Label>
            <Form.Label column sm={2}>
              Ngày Khám
            </Form.Label>
            <Col sm={2}>
              <Form.Control
                type="date"
                name="date"
                onChange={evt => changeDate('value', evt.target.value)}
              />
            </Col>
            <Form.Label column sm={2}>
              Bệnh nhân
            </Form.Label>
            <Col sm={2}>
              <Form.Select onChange={evt => changePatient('value', evt.target.value)}>
                {patients !== null && patients.map(p => {
                    return <option value={p[0]}>{p[2]}</option>
                })}
              </Form.Select>
            </Col>
          </Form.Group>
        </Form>
        <table
        class="table caption-top wid50"
        id="myTable"
        style={{ width: "80%", margin: "auto" }}
      >
        <thead>
          <tr class="header">
            <th scope="col">Tên bệnh nhân</th>
            <th scope="col">Ngày khám</th>
            <th scope="col">Bác sỹ thăm khám</th>
            <th scope="col"></th>
          </tr>  
        </thead>
        <tbody>
          {history.map(h => {
            return <tr>
                <td>{h.patientId.fullName}</td>
                <td>{new Date(h.createdDate).toISOString().split('T')[0]}</td>
                <td>{h.doctorId.fullName}</td>
                <td><Button variant="warning">Xem chi tiết</Button></td>
            </tr>
          })}
        </tbody>
      </table>
    </>)
}

export default History