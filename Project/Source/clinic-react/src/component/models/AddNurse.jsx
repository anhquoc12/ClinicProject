import { useEffect, useState } from "react"
import { authUser, contentType, endpoints } from "../../configs/Apis"
import { Button, Form } from "react-bootstrap"
import Loading from "../../layout/Loading"


const AddNurse = (props) => {
    const [nurse, setNurse] = useState(
        props.nurse)
      const [avatar, setAvatar] = useState(null)
      const [loading, setLoading] = useState(false)
  
      const addOrUpdate = async(evt) => {
        evt.preventDefault()
        let form = new FormData();
          for(let field in nurse) {
            if(field === 'id' && nurse[field] === null)
              continue
            form.append(field, nurse[field])
          }
          if(avatar !== null) {
            form.append('file', avatar)
          }
          else {
            form.append('file', null)
          }
        //   if(nurse.id === null)
        try {
          setLoading(true)
          let res = await authUser(contentType['form']).post(endpoints['user']['add-nurse'], form)
          alert(res.data['message'])
        } catch(ex){console.log(ex)} finally{setLoading(false)}
      }
  
      const changeValue = (field, value) => {
        setNurse(current => {
            return({
                ...current, [field]: value
            })
        })
    }
  
    return (
      <>
        <Form onSubmit={addOrUpdate}>
          <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
            <Form.Label>Họ và tên</Form.Label>
            <Form.Control type="text" required value={nurse.fullName} onChange={evt => changeValue('fullName', evt.target.value)} />
          </Form.Group>
          <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
            <Form.Label>địa chỉ</Form.Label>
            <Form.Control type="text" required value={nurse.address} onChange={evt => changeValue('address', evt.target.value)} />
          </Form.Group>
          <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
            <Form.Label>Số điện thoại</Form.Label>
            <Form.Control type="text" required value={nurse.phone} onChange={evt => changeValue('phone', evt.target.value)}/>
          </Form.Group>
          <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
            <Form.Label>email</Form.Label>
            <Form.Control type="email" required value={nurse.email} onChange={evt => changeValue('email', evt.target.value)} />
          </Form.Group>
          {nurse.id === null && <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
            <Form.Label>avatar</Form.Label>
            <Form.Control type="file" required onChange={evt => setAvatar(evt.target.files[0])} />
          </Form.Group>}
          {loading && <Loading />}
          <Button variant="outline-primary" type="submit">Thêm Y tá</Button>
        </Form>
      </>
    );
}

export default AddNurse