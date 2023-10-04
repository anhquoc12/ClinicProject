import { useEffect, useState } from "react";
import { Alert, Button, Col, Form, Row, Table } from "react-bootstrap";
import { authUser, contentType, endpoints } from "../../configs/Apis";
import Loading from "../../layout/Loading";

const Categories = () => {
  const [categories, setCategories] = useState(null);
  const [remove, isRemove] = useState(false);
  const [add, setAdd] = useState(false)
  const [name, setName] = useState('')

  const deleteItem = async(item) => {
    /* eslint-disable no-restricted-globals */
    if(confirm('Bạn có muốn xoá nó không?')) {
        try {
            let res = await authUser().delete(endpoints['category']['delete'](item))
            alert(res.data['message'])
            isRemove(true)
        } catch(ex) {console.log(ex)}
    }
  }

  const addItem = async(evt) => {
    evt.preventDefault()
    try {
        if(name !== null && name != '') {
            let res = await authUser(contentType['json']).post(endpoints['category']['add'], {
                'name': name
            })
            alert(res.data['message'])
            setAdd(true)
        }
    } catch(ex) {console.log(ex)}
  }

  useEffect(() => {
    const process = async () => {
      try {
        let res = await authUser().get(endpoints["category"]["categories"]);
        setCategories(res.data);
      } catch (ex) {
        console.log(ex);
      }
    };
    process();
    setAdd(false)
    isRemove(false)
  }, [remove, add]);

  if(categories === null) return <Loading />

  return (
    <>
    <Alert variant="info">Quản lý thuốc</Alert>
      <Form style={{width: '80%', margin: 'auto'}} onSubmit={addItem}>
        <Form.Group as={Row} className="mb-3" controlId="formPlaintextEmail">
          <Form.Label column sm="2">
            tên loại thuốc
          </Form.Label>
          <Col sm="8">
            <Form.Control type="text" onChange={evt => setName(evt.target.value)} />
          </Col>
          <Col sm="2">
            <Button type="submit" variant="success">Thêm loại thuốc</Button>
          </Col>
        </Form.Group>
      </Form>
      <Table responsive style={{width: '80%', margin: 'auto'}} className="text-center">
        <thead>
          <tr>
            <th style={{width: '3%'}}>#</th>
            <th style={{width: '85%'}}>Tên loại thuốc</th>
            <th style={{width: '12%'}}></th>
          </tr>
        </thead>
        <tbody>
          {categories.map((c) => {
            return (
              <tr>
                <td>{c.id}</td>
                <td>{c.name}</td>
                <td>
                  <Button variant="outline-primary" style={{width: '100%'}} onClick={() => deleteItem(c.id)}>Xoá</Button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </Table>
    </>
  );
};

export default Categories