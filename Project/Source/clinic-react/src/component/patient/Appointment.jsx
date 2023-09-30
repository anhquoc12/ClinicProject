import { Form } from "react-bootstrap";
import "../../static/css/appointment.css";
import Calendar from "react-calendar";
import { useEffect, useState } from "react";
import  {
  api,
  authUser,
  contentType,
  endpoints,
} from "../../configs/Apis";
import Loading from "../../layout/Loading";
import { Link, useNavigate } from "react-router-dom";
import { Redirect } from "../../configs/Permission";
import Forbidden from "../../error-pages/Forbidden";

const Appointment = () => {
  const [specializations, setSpecializations] = useState(null);
  const [appointment, setAppointment] = useState({
    appointmentDate: new Date(),
    description: "",
    specializationId: {
      id: "",
      name: "",
    },
  });
  const [hide, setHide] = useState(false);
  const [alert, setAlert] = useState("");
  const [errors, setErrors] = useState([]);
  const [appointmentDate, setAppointmentDate] = useState(new Date())
  const [seconds, setSeconds] = useState(null)
  const nav = useNavigate()
  const [loading, setLoading] = useState(false)

  // Function
  const changeValue = (field, value) => {
    setAppointment((current) => {
      return {
        ...current,
        [field]: value,
      };
    });
  };

  const addAppointment = async (evt) => {
    evt.preventDefault();
    appointment["specializationId"] =
      specializations[appointment["specializationId"]];
    if (appointment["specializationId"] === undefined) {
      appointment["specializationId"] = specializations[0];
    }
    console.log(appointment['appointmentDate'])
    // co
    appointment['appointmentDate'] = appointmentDate.toISOString().split('T')[0]

    try {
      setLoading(true)
      let res = await authUser(contentType["json"]).post(
        endpoints["appointment"]["add"],
        appointment
      );
      if (res.status === 201) setAlert(res.data["message"]);
      setSeconds(5)

    } catch (ex) {
      let message = ex.response["data"]["data"];
      console.log(message)
      setErrors(message);
    } finally {
      setLoading(false)
    }
  };

  // useEffect
  useEffect(() => {
    const process = async () => {
      try {
        let response = await api(contentType["form"]).get(
          endpoints["specialization"]["lists"]
        );
        if (response.status === 200) setSpecializations(response.data);
      } catch {
        alert("Có lỗi xảy ra");
      }
    };
    

    process();
    const timer = setTimeout(() => {
      if (seconds > 0 && seconds !== null) {
        setSeconds(seconds - 1); // Giảm số giây
      } else if(seconds !== null) {
        nav('/') // Chuyển sang Component khác sau 5 giây
      }
    }, 1000); // Hẹn giờ mỗi 1 giây

    // Xóa hẹn giờ nếu component bị unmount hoặc currentComponent thay đổi trước khi hẹn giờ hoàn thành
    return () => {
      clearTimeout(timer);
    };
  }, [seconds]);

  if (specializations === null) return <Loading />;

  // if(!Redirect()) {
  //   return <Forbidden />
  // }

  return (
    <>
      <div className="box-appoint">
        <h1 className="title text-secondary">Make Appointment</h1>
        {errors.length > 0 && <div>
          {errors.map(error => {
            return <h5 className="text-danger">{error}</h5>
          })}
        </div>}
        <Form className="wid80 form-appointment" onSubmit={addAppointment}>
          <Form.Label>Ngày Khám Bệnh</Form.Label>
          <Form.Control
            type="text"
            readOnly
            value={appointmentDate.toISOString().split("T")[0]}
            onClick={() => setHide(!hide)}
          />
          <br />

          <Form.Label>Chọn Khoa Bệnh</Form.Label>
          <select
            className="form-appointment--select"
            value={appointment["specializationId"]}
            onChange={(evt) =>
              changeValue("specializationId", evt.target.value)
            }
          >
            <option value={null} disabled>
              Chọn Khoa khám bệnh
            </option>
            {specializations.map((s, index) => {
              return <option value={index}>{s.name}</option>;
            })}
          </select>
          <br />
          <Form.Label>Mô tả</Form.Label>
          <textarea required
            onChange={(evt) => changeValue("description", evt.target.value)}
            className="outline-bottom"
            placeholder="description"
            rows="4"
            value={appointment["description"]}
          ></textarea>
          <br />
          {alert !== '' && 
          <>
            <h6 className="text-success">{alert}</h6>
            <h6 className="text-success"><Link className="text-danger" to='/'>Trở về trang chủ </Link> hoặc sau{seconds} giây</h6>
          </>}
          {loading && <Loading />}
          <input type="submit" className="submit" value="Đăng Ký Lịch Khám" />
        </Form>
        {hide && (
          <Calendar minDate={new Date()}
            className="calendar"
            onChange={(evt) => setAppointmentDate(evt)}
          />
        )}
      </div>
    </>
  );
};

export default Appointment;
