import { MDBTable, MDBTableBody, MDBTableHead } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import { Alert, Button } from "react-bootstrap";
import Loading from "../../layout/Loading";
import { authUser, endpoints } from "../../configs/Apis";

const Today = () => {
    const [listAppointment, setLisAppointment] = useState(null);
    const [loading, setLoading] = useState(null)

    const present = async(appointment) => {
        try {
            setLoading(true)
            let res = await authUser().post(endpoints['appointment']['present-appointment'], appointment)
            alert(res.data['message'])
        } catch(ex) {
            console.log(ex)
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        const loadListToday = async() => {
            try {
                let res = await authUser().get(endpoints['appointment']['present'])
            setLisAppointment(res.data)
            } catch(ex) {
                console.log(ex)
            }
        }

        loadListToday()
    }, [loading])


    if (listAppointment === null) return <Loading />;

    return (<>
        {loading && <Loading />}
        {listAppointment.length === 0?
        <>
        <Alert variant="danger">Không có lịch hẹn nào cả</Alert>
        <div style={{marginBottom: 230}}></div>
        </>
        :
        <MDBTable align="middle" className="text-center">
        <MDBTableHead>
          <tr>
            <th scope="col" style={{width: '20%'}}>Tên Bệnh Nhân</th>
            <th scope="col" style={{width: '20%'}}>Ngày Đăng Ký</th>
            <th scope="col" style={{width: '20%'}}>Y tá xác nhận</th>
            <th scope="col" style={{width: '20%'}}>Khoa khám</th>
            <th scope="col" style={{width: '20%'}}>Actions</th>
          </tr>
        </MDBTableHead>
        <MDBTableBody>
          {listAppointment.map((a) => {
            return (
              <tr>
                <td>{a.patientId.fullName}</td>
                <td>{new Date(a.createdDate).toISOString().split('T')[0]}</td>
                <td>{a.nurseId.fullName}</td>
                <td>{a.specializationId.name}</td>
                <td>
                  <Button variant="outline-secondary" onClick={() => present(a)}>Xác nhận Có mặt</Button>
                </td>
              </tr>
            );
          })}
        </MDBTableBody>
      </MDBTable>
    
    }
        </>
    )
}

export default Today