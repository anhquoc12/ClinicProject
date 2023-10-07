import { useEffect, useState } from "react";
import { Alert, Button, Col, Form, Row } from "react-bootstrap";
import "../../static/css/schedule.css";
import { authUser, endpoints } from "../../configs/Apis";
import { USER_ROLE } from "../../configs/Enum";
import Loading from "../../layout/Loading";
// import '../../resources/shift.json'

const CreateSchedule = () => {
    const [doctors, setDoctors] = useState(null)
    const [nurses, setNurses] = useState(null)
    const [toggle, setToggle] = useState(true)
    const [rooms, setRooms] = useState(null)
    const [shifts, setShifts] = useState(null)
    const [loading, setLoading] = useState(false)


    useEffect(() => {
        const loadDoctors = async() => {
            let res = await authUser().get(endpoints['user']['list-by-role'](USER_ROLE.DOCTOR))
            setDoctors(res.data)
        }
        const loadNuses = async() => {
            let res = await authUser().get(endpoints['user']['list-by-role'](USER_ROLE.NURSE))
            setNurses(res.data)
        }
        const loadRoom = async() => {
            let res = await authUser().get(endpoints['room']['list'])
            setRooms(res.data)
        }

        const loadShift = async() => {
            let res = await authUser().get(endpoints['shift']['list'])
            setShifts(res.data)
        }

        loadDoctors()
        loadNuses()
        loadRoom()
        loadShift()
    }, [toggle, loading])

  return (
    <>
      <div className="form--schedule">
        <div className="form--schedule-header">Tạo lịch trực</div>
        {loading && <Loading />}
        <div className="form--schedule-form">
        <Form>
              <Form.Group
                as={Row}
                className="mb-3"
                controlId="formPlaintextEmail"
              >
                <Form.Label column sm="3">
                  Ngày Trực
                </Form.Label>
                <Col sm="9">
                  <Form.Control type="date" />
                </Col>
              </Form.Group>

              <Form.Group
                as={Row}
                className="mb-3"
                controlId="formPlaintextPassword"
              >
                <Form.Label column sm="3">
                  Ca trực
                </Form.Label>
                <Col sm="9">
                  <Form.Select></Form.Select>
                </Col>
              </Form.Group>
              <Form.Group
                as={Row}
                className="mb-3"
                controlId="formPlaintextPassword"
              >
                <Form.Label column sm="3">
                  Nhân Viên
                </Form.Label>
                <Col sm="9">
                  <Form.Select>
                    {doctors !== null && toggle && <>
                        {doctors.map(d => {return <option value={d[0]}>{d[2]}</option>})}
                    </>}
                    {nurses !== null && !toggle && <>
                        {nurses.map(n => {return <option value={n[0]}>{n[2]}</option>})}
                    </>}
                  </Form.Select>
                </Col>
              </Form.Group>
              <Form.Group
                as={Row}
                className="mb-3"
                controlId="formPlaintextPassword"
              >
                <Form.Label column sm="3">
                  
                </Form.Label>
                <Col sm="5">
                  <Form.Check inline type="radio" label='Bác sỹ' onClick={() => setToggle(true)} name="opUser" defaultChecked/>
                  <Form.Check inline type="radio" label='Y Tá' onClick={() => setToggle(false)}  name="opUser"/>
                </Col>
              </Form.Group>
              <Form.Group
                as={Row}
                className="mb-3"
                controlId="formPlaintextPassword"
              >
                <Form.Label column sm="3">
                  Phòng trực
                </Form.Label>
                <Col sm="9">
                  <Form.Select>
                    {rooms !== null && rooms.map(r => {return <option value={r.id}>{r.name}</option>})}
                  </Form.Select>
                </Col>
              </Form.Group>
              <Button variant="outline-primary" className="button-submit">Tạo lịch</Button>
            </Form>
        </div>
      </div>
    </>
  );
};

export default CreateSchedule;
