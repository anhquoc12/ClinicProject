import { useEffect, useState } from "react";
import { authUser, endpoints } from "../../configs/Apis";
import Loading from "../../layout/Loading";
import { useParams } from "react-router-dom";
import "../../static/css/detail.css";

const DetailMedical = () => {
  const [detail, setDetail] = useState(null);
  const [prescriptions, setPrescription] = useState(null);
  const { medicalId } = useParams();

  useEffect(() => {
    const process = async () => {
      try {
        let res = await authUser().get(
          endpoints["medical"]["detail"](medicalId)
        );
        let response = await authUser().get(
          endpoints["medical"]["prescription"](medicalId)
        );
        setDetail(res.data);
        setPrescription(response.data);
      } catch {}
    };

    process();
  }, []);

  if (detail === null) return <Loading />;

  return (
    <>
      <section class="content">
        <h1 class="text-secondary mt-2 text-uppercase">
          Biểu Mẫu Lịch Sử Khám Bệnh
        </h1>
        <div class="line"></div>
        <div class="info--patient">
          <div class="field">
            <span class="field--name">Họ Và Tên</span>{" "}
            <span class="value">{detail.patientId.fullName}</span>
          </div>
        </div>
        <div class="info--patient">
          <div class="field">
            <span class="field--name">Id</span>{" "}
            <span class="value">{detail.patientId.id}</span>
          </div>
        </div>
        <div class="info--patient">
          <div class="field">
            <span class="field--name">Số điện thoại</span>{" "}
            <span class="value">{detail.patientId.phone}</span>
          </div>
        </div>
        <div class="info--patient">
          <div class="field">
            <span class="field--name">Email</span>{" "}
            <span class="value">{detail.patientId.email}</span>
          </div>
        </div>
        <div class="info--patient">
          <div class="field">
            <span class="field--name">Ngày Khám</span>{" "}
            <span class="value">{detail.createdDate}</span>
          </div>
        </div>
        <div class="info--patient">
          <div class="field">
            <span class="field--name">Bác sỹ thăm khám</span>{" "}
            <span class="value">{detail.doctorId.fullName}</span>
          </div>
        </div>
        <div class="line"></div>
        <div class="container-fluid mt-3 table">
          <div class="row rows">
            <div class="col-sm-3 bg-secondary">Triệu Chứng</div>
            <div class="col-sm-9">{detail.symptom}</div>
          </div>
          <div class="row rows">
            <div class="col-sm-3 bg-secondary">Kết Luận</div>
            <div class="col-sm-9">{detail.conclusion}</div>
          </div>
          <div class="row rows">
            <div class="col-sm-3 bg-secondary">Lời Khuyên</div>
            <div class="col-sm-9">{detail.advice}</div>
          </div>
          <div class="row rows">
            <div class="col-sm-3 bg-secondary">Ghi chú</div>
            <div class="col-sm-9">{detail.note}</div>
          </div>
        </div>
        <div class="line"></div>
        <table
          class="table table-bordered text-center"
          border="1"
          style={{ width: "85%", fontSize: 16 }}
        >
          <thead>
            <tr>
              <th colspan="5" class="title--table">
                Toa Thuốc
              </th>
            </tr>
            <tr>
              <th width="25%" scope="col">
                Tên Thuốc
              </th>
              <th width="20%" scope="col">
                Liều Lượng
              </th>
              <th width="20%" scope="col">
                Tần suất
              </th>
              <th width="20%" scope="col">
                Thời gian sử dụng
              </th>
              <th width="15%" scope="col">
                Số Lượng
              </th>
            </tr>
          </thead>
          <tbody>
            {prescriptions.map((p) => {
              return (
                <tr>
                  <td>{p.medicineId.name}</td>
                  <td>{p.dosage}</td>
                  <td>{p.frequency}</td>
                  <td>{p.duration}</td>
                  <td>{p.totalUnit} {p.medicineId.unitMedicineId.name}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </section>
    </>
  );
};

export default DetailMedical;
