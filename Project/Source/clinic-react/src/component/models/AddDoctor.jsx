import { useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { authUser, contentType, endpoints } from "../../configs/Apis";
import Loading from "../../layout/Loading";

const AddDoctor = (props) => {
    const [specs, setSpecs] = useState(null)
    const [doctor, setDoctor] = useState(
      props.doctor)
    const [avatar, setAvatar] = useState(null)
    const [loading, setLoading] = useState(false)

    const addOrUpdate = async(evt) => {
      evt.preventDefault()

      if(doctor.specialId === null) {
        alert('Chọn khoa bệnh')
        return
      }
      let form = new FormData();
        for(let field in doctor) {
          if(field === 'id' && doctor[field] === null)
            continue
          form.append(field, doctor[field])
        }
        if(avatar !== null) {
          form.append('file', avatar)
        }
        else {
          form.append('file', null)
        }
        // if(doctor.id === null)
      try {
        setLoading(true)
        console.log(doctor)
        let res = await authUser(contentType['form']).post(endpoints['user']['add-doctor'], form)
        alert(res.data['message'])
      } catch(ex){console.log(ex)} finally{setLoading(false)}
    }

    const changeValue = (field, value) => {
      setDoctor(current => {
          return({
              ...current, [field]: value
          })
      })
  }

    useEffect(() => {
        const process = async() => {
            try {
                let res = await authUser().get(endpoints['specialization']['lists'])
                setSpecs(res.data)
            } catch(ex){console.log(ex)}
        }

        process()
    }, [])

    if(specs === null) return <Loading />

  return (
    <>
      <Form onSubmit={addOrUpdate}>
        <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
          <Form.Label>Họ và tên</Form.Label>
          <Form.Control type="text" required value={doctor.fullName} onChange={evt => changeValue('fullName', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>địa chỉ</Form.Label>
          <Form.Control type="text" required value={doctor.address} onChange={evt => changeValue('address', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>Số điện thoại</Form.Label>
          <Form.Control type="text" required value={doctor.phone} onChange={evt => changeValue('phone', evt.target.value)}/>
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>email</Form.Label>
          <Form.Control type="email" required value={doctor.email} onChange={evt => changeValue('email', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>Khoa bệnh</Form.Label>
          <Form.Select aria-label="Default select example" onChange={evt => changeValue('specialId', evt.target.value)}>
            {
                specs.map(s => {
                    return s.id === doctor.specialId?<option value={s.id} selected>{s.name}</option>:<option value={s.id}>{s.name}</option>
                })
            }
          </Form.Select>
        </Form.Group>
        {doctor.id === null && <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>avatar</Form.Label>
          <Form.Control type="file" required onChange={evt => setAvatar(evt.target.files[0])} />
        </Form.Group>}
        {loading && <Loading />}
        <Button variant="outline-primary" type="submit">Thêm bác sỹ</Button>
      </Form>
    </>
  );
};

export default AddDoctor
