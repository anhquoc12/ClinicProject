import { useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { authUser, contentType, endpoints } from "../../configs/Apis";
import Loading from "../../layout/Loading";

const AddMedicine = (props) => {
    const [categories, setCategories] = useState(null)
    const [units, setUnit] = useState(null)
    const [medicine, setMedicine] = useState(
        props.medicine)
    const [loading, setLoading] = useState(false)


    const changeValue = (field, value) => {
        setMedicine(current => {
            return({
                ...current, [field]: value
            })
        })
    }

    const add = async (evt) => {
        evt.preventDefault()
        console.log(medicine)
        // if(props.medicine !== null || props.medicine !== undefined)
        //     console.log('test')
        if(medicine['category'] === null)
            medicine['category'] = categories[0]['id']
        if(medicine['unit'] === null)
            medicine['unit'] = units[0]['id']

        let form = new FormData()
        for(let field in medicine) {
            form.append(field, medicine[field])
        }
        
        try {
            setLoading(true)
            let res = await authUser(contentType['form']).post(endpoints['medicine']['add'], form)
            alert(res.data['message'])
        } catch(ex){alert('Kiểm tra thông tin nhập. Lưu ý tên thuốc không được trùng nhau')} finally{setLoading(false)}
    }


    useEffect(() => {
        // if(medicine === null) {medicine['id'] = null}
        const loadUnitMedicine = async() => {
            try {
                let res = await authUser().get(endpoints['unit-medicine']['lists'])
                setUnit(res.data)
            } catch(ex) {console.log(ex)}
        }
        const loadCategory = async() => {
            try {
                let res = await authUser().get(endpoints['category']['categories'])
                setCategories(res.data)
            } catch(ex) {console.log(ex)}
        }

        loadCategory()
        loadUnitMedicine()
    }, [loading])

    if(categories === null || units === null) return <Loading />

  return (
    <>
      <Form onSubmit={add}>
        <Form.Group className="mb-3">
          <Form.Label>Tên thuốc</Form.Label>
          <Form.Control required type="text" min={1} max={255} value={medicine.name} onChange={(evt) => changeValue('name', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>Số lượng</Form.Label>
          <Form.Control min={1} required type="number" value={medicine.unitInStock} style={{ width: "25%" }}  onChange={(evt) => changeValue('unitInStock', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>đơn giá của thuốc</Form.Label>
          <Form.Control required min={1000} step={1000} type="number" value={medicine.unitPrice} style={{ width: "25%" }} onChange={(evt) => changeValue('unitPrice', evt.target.value)} />
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>Loại thuốc</Form.Label>
          <Form.Select onChange={(evt) => changeValue('category', evt.target.value)} >
            {
                categories.map(c => (
                    c.id === medicine.category?<option value={c.id} selected>{c.name}</option>:<option value={c.id}>{c.name}</option>
                ))
            }
          </Form.Select>
        </Form.Group>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Label>Đơn vị thuốc</Form.Label>
          <Form.Select onChange={(evt) => changeValue('unit', evt.target.value)} >
            {
                units.map(u => (
                    u.id === medicine.unit?<option value={u.id} selected>{u.name}</option>:<option value={u.id}>{u.name}</option>
                ))
            }
          </Form.Select>
        </Form.Group>
        
        {loading && <Loading />}<Button variant="success" type="submit">Thêm Thuốc</Button>
      </Form>
    </>
  );
};

export default AddMedicine
