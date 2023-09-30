import { MDBTable, MDBTableBody, MDBTableHead } from "mdb-react-ui-kit";
import { useEffect, useState } from "react";
import { Alert, Button } from "react-bootstrap";
import { authUser, endpoints } from "../../configs/Apis";
import Loading from "../../layout/Loading";

const Confirmed = () => {
  const [listAppointment, setLisAppointment] = useState(null);
  const [loading, setLoading] = useState(null)

  const confirm = async(appointment) => {
    try {
        setLoading(true)
        let res = await authUser().post(endpoints['appointment']['confirm-appointment'], appointment)
        alert(res.data['message'])
    } catch (ex) {
        console.log(ex)
    } finally {
        setLoading(false)
    }
  }

  useEffect(() => {
    const loadAppointment = async () => {
      try {
        let res = await authUser().get(endpoints["appointment"]["confirmed"]);
        setLisAppointment(res.data);
      } catch (ex) {
        console.log(ex);
      }
    };

    loadAppointment();
  }, [loading]);

  if (listAppointment === null) return <Loading />;

  return (
    <>
    {loading && <Loading />}
    {listAppointment.length === 0 ? 
    <>
        <Alert variant="primary"><h1 className="text-danger">Không có lịch hẹn nào cả</h1></Alert>
        <div style={{marginBottom: 200}}></div>
    </>:
      <MDBTable align="middle" className="text-center">
        <MDBTableHead>
          <tr>
            <th scope="col" style={{width: '20%'}}>Tên Bệnh Nhân</th>
            <th scope="col" style={{width: '20%'}}>Ngày Đăng Ký</th>
            <th scope="col" style={{width: '20%'}}>Ngày Hẹn Khám</th>
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
                <td>{new Date(a.appointmentDate).toISOString().split('T')[0]}</td>
                <td>{a.specializationId.name}</td>
                <td>
                  <Button onClick={() => confirm(a)} variant="info">Xác nhận</Button>
                </td>
              </tr>
            );
          })}
        </MDBTableBody>
      </MDBTable>}
    </>
  );
};


export default Confirmed