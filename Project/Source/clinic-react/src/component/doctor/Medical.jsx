import { Button, Form } from "react-bootstrap";
import '../../static/css/medical.css'
import { useEffect, useState } from "react";
import { authUser, contentType, endpoints } from "../../configs/Apis";
import { useNavigate } from "react-router-dom";
import Loading from "../../layout/Loading";

const Medical = () => {
  const[patients, setPatient] = useState(null)
  const [medical, setMedical] = useState({
    'symptom': '',
    'conclusion': '',
    'advice': '',
    'examinationFee': 20000,
    'note': '',
    'patientId': null
  })
  const navigate = useNavigate()
  const [loading, setLoading] = useState(false)

  const addMedical = async(evt) => {
    evt.preventDefault()
    console.log(medical)
    console.log(medical['patientId'])
    try {
      setLoading(true)
      let form = new FormData()
      for (let field in medical) {
        form.append(field, medical[field])
      }
      let res = await authUser(contentType['form']).post(endpoints['medical']['add'], form)
      alert(res.data['message'])
      console.log(res.data['data'])
      navigate(`/doctor/medical/prescription/${res.data['data'].id}`)
    } catch(ex) {console.log(ex)} finally {setLoading(false)}
  }


  const changeValue = (field, value) => {
    setMedical(current => {
      return{...current, [field]: value}
    })
  }

  useEffect(() => {
    const loadPatient = async() => {
      try {
        let res = await authUser().get(endpoints['appointment']['patient-medicals'])
        setPatient(res.data)
      } catch(ex) {console.log(ex)}
    }

    loadPatient()
  }, [loading])

  return (
    <>
      <div className="medical">
      <h3 className="text-center">Phiếu khám bệnh</h3>
      <Form className="wid50 form--medical" onSubmit={evt => addMedical(evt)}>
        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
          <Form.Label>Tên Bệnh Nhân</Form.Label>
          <Form.Select onChange={(evt) => changeValue('patientId', evt.target.value)}>
            {patients !== null &&
              patients.map(p => {
                return <option value={p.patientId.id}>{p.patientId.fullName}</option>
              })
            }
          </Form.Select>
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>Triệu chứng bệnh</Form.Label>
          <Form.Control as="textarea" rows={3} onChange={evt => changeValue('symptom', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>kết luận</Form.Label>
          <Form.Control type="text" onChange={evt => changeValue('conclusion', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>Tiền khám bệnh</Form.Label>
          <Form.Control type="number" style={{width: '15%'}} min='20000' step='1000' onChange={evt => changeValue('examinationFee', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>Lời khuyên của bác sỹ</Form.Label>
          <Form.Control type="text" onChange={evt => changeValue('advice', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>Ghi chú bệnh án</Form.Label>
          <Form.Control type="text" onChange={evt => changeValue('note', evt.target.value)} />
        </Form.Group>
        {loading && <Loading />}
        <Button variant="info" type="submit">Tạo phiếu</Button>
      </Form>
      </div>
    </>
  );
};

export default Medical
