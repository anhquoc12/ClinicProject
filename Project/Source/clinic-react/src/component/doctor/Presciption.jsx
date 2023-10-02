import { useContext, useEffect, useState } from "react";
import { Alert, Button, Col, Form, Row } from "react-bootstrap";
import { authUser, endpoints } from "../../configs/Apis";
import Loading from "../../layout/Loading";
import "../../static/css/prescription.css";
import { formatCurrency } from "../../utils/utils";
import { Navigate, useParams } from "react-router-dom";
import { MyUserContext } from "../../App";
import { ExportPDF } from "../../utils/ExportToPDF";

const Prescription = () => {
  const [medicines, setMedicine] = useState(null);
  const [name, setName] = useState("");
  const [category, setCategory] = useState(null)
  const [categoryItem, setCategoryItem] = useState(null)
  const { medicalId, patientId } = useParams();
  const [prescription, setPrescription] = useState([]);
  const [hiddenAddButtons, setHiddenAddButtons] = useState([]);
  const [medical, setMedical] = useState(null);
  const [complete, setComplete] = useState(false);
  const [currentUser, state] = useContext(MyUserContext)

  const handleAddToPrescriptionT = (
    medicine,
    dosage,
    frequency,
    duration,
    total
  ) => {
    const newItem = {
      medicine: medicine.id,
      medicineName: medicine.name,
      dosage: dosage,
      frequency: frequency,
      duration: duration,
      total: total,
      unitMedicineId: medicine.unitMedicineId.id,
      unitName: medicine.unitMedicineId.name,
    };
    setPrescription([...prescription, newItem]);

    setHiddenAddButtons([...hiddenAddButtons, medicine.id]);
  };

  const handleAddToPrescription = (medicine) => {
    setPrescription([...prescription, medicine]);

    // Ẩn nút "Add" cho thuốc đã được thêm vào toa thuốc
    setHiddenAddButtons([...hiddenAddButtons, medicine.id]);
  };

  const handleRemoveFromPrescription = (medicine, index) => {
    const updatedPrescription = prescription.filter(
      (item) => item.id !== medicine.id
    );
    const updatedHiddenAddButtons = [...hiddenAddButtons];
    updatedHiddenAddButtons[index] = false;

    setPrescription(updatedPrescription);
    setHiddenAddButtons(updatedHiddenAddButtons);
  };

  const processTable = async () => {
    const json = [];
    let shouldAlert = false; // Biến cờ để kiểm tra xem đã hiển thị alert chưa

    prescription.forEach((item, index) => {
      if (
        item.dosage === null ||
        item.frequency === null ||
        item.duration === null ||
        item.total === null || item.total < 1 || item.total === undefined
      ) {
        shouldAlert = true;
        return; // Không cần thêm thông tin vào json nếu dữ liệu không hợp lệ
      } else {
        json.push({
          medicineId: item.medicine,
          name: item.medicineName,
          dosage: item.dosage,
          frequency: item.frequency,
          duration: item.duration,
          totalUnit: item.total,
          unit: item.unitMedicineId,
        });
      }
    });

    if (shouldAlert) {
      alert("Vui lòng nhập đầy đủ");
    } else {
      try {
          console.log(json)
          let res = await authUser('json').post(endpoints['medical']['add-presrciption'](medicalId),
          json)
          alert(res.data['message'])
          // let medicalRecord = await authUser().get(endpoints['detailHistory'](medicalId))
          // setMedical(medicalRecord.data)
          let {data} = await authUser().get(endpoints['user']['user-id'](patientId))
          var object = {
              doctorName: currentUser.fullName,
              doctorAddress: currentUser.address,
              doctorPhone: currentUser.phone,
              patientName: data.fullName,
              patientAddress: data.address,
              patientPhone: data.phone,
              file: `prescription-${data.fullName}`
          }
          console.log(object)
          // await ExportPDF(object, json)
      } catch(ex) {
          console.log(ex)
      } finally {
          setComplete(true)
      }
    }
  };

  useEffect(() => {
    const loadMedicine = async () => {
      let res = await authUser().get(endpoints["medicine"]["list"]);
      if (name !== "")
        {
          setCategoryItem(null)
          res = await authUser().get(`${endpoints["medicine"]["list"]}?name=${name}`);
        }
      if(categoryItem !== null)
      {
        // setName("")
        res = await authUser().get(`${endpoints["medicine"]["list"]}?category=${categoryItem}`);
      }
      setMedicine(res.data);
    };

    const loadCategories = async() => {
      let res = await authUser().get(endpoints['category']['categories'])
      setCategory(res.data)
    }

    loadMedicine();
    loadCategories()
    // setCategoryItem(null)
  }, [name, categoryItem]);

  if (medicines === null || category === null) return <Loading />;

  if (complete) return <Navigate to="/doctor/medical" />;

  return (
    <>
      <div className="box-medicine">
        <Alert variant="secondary">Danh sách thuốc</Alert>
        <Form>
          <Form.Group as={Row} className="mb-3" controlId="formHorizontalEmail">
            <Form.Label column sm={2}>
              Tên thuốc
            </Form.Label>
            <Col sm={2}>
              <Form.Control
                type="text"
                placeholder="nhập từ khoá..."
                name="name"
                onChange={evt => setName(evt.target.value)}
                value={name}
              />
            </Col>
            <Form.Label column sm={2}>
              Loại thuốc
            </Form.Label>
            <Col sm={2}>
              <Form.Select onChange={evt => setCategoryItem(evt.target.value)}>
                {category.map(c => {
                  return <option value={c.id}>{c.name}</option>
                })}
              </Form.Select>
            </Col>
          </Form.Group>
        </Form>
        <div className="over--medicine">
          <table className="table table-borderless table-responsive overflow-auto">
            <thead>
              <tr>
                <th className="text-muted text-center" style={{ width: "10%" }}>
                  Tên Thuốc
                </th>
                <th className="text-muted text-center" style={{ width: "10%" }}>
                  Đơn giá
                </th>
                <th className="text-muted text-center" style={{ width: "10%" }}>
                  Số lượng
                </th>
                <th className="text-muted text-center" style={{ width: "40%" }}>
                  loại thuốc
                </th>
                <th className="text-muted text-center" style={{ width: "40%" }}>
                  Đơn vị dùng
                </th>
                <th style={{ width: "10%" }}></th>
              </tr>
            </thead>
            <tbody>
              {medicines.map((m) => {
                return (
                  <tr className="align-middle alert text-center" role="alert">
                    <td>{m.name}</td>
                    <td>{formatCurrency(m.unitPrice)} VNĐ</td>
                    <td>
                      <div className="d-inline-flex align-items-center">
                        {m.unitInStock}
                      </div>
                    </td>
                    <td className="info">{m.categoryId.name}</td>
                    <td>{m.unitMedicineId.name}</td>
                    <td>
                      {!hiddenAddButtons.includes(m.id) && (
                        <Button
                          variant="outline-secondary"
                          style={{ width: "100%" }}
                          onClick={() =>
                            handleAddToPrescriptionT(m, null, null, null)
                          }
                        >
                          Add
                        </Button>
                      )}
                    </td>
                  </tr>
                );
              })}
            </tbody>
          </table>
        </div>
      </div>
      <div className="wid40" style={{ marginLeft: 40, marginTop: 40 }}>
        <Alert variant="primary">Toa thuốc</Alert>
      </div>
      <table
        class="table caption-top wid50"
        id="myTable"
        style={{ width: "80%", margin: "auto" }}
      >
        <thead>
          <tr class="header" style={{ borderTop: "1px solid black" }}>
            <th scope="col">Tên Thuốc</th>
            <th scope="col">Liều Lượng</th>
            <th scope="col">Tần suất dùng thuốc</th>
            <th scope="col">Thời gian dùng thuốc</th>
            <th scope="col">Số Lượng</th>
            <th scope="col">Đơn Vị</th>
            <th scope="col"></th>
          </tr>
        </thead>
        <tbody>
          {prescription.map((medicine, index) => (
            <tr key={index}>
              <td>{medicine.medicineName}</td>
              <td>
                <Form.Control
                  type="text"
                  placeholder="Liều lượng"
                  onChange={(e) => {
                    const updatedPrescription = [...prescription];
                    updatedPrescription[index].dosage = e.target.value;
                    setPrescription(updatedPrescription);
                  }}
                />
              </td>
              <td>
                <Form.Control
                  type="text"
                  placeholder="Tần suất dùng thuốc"
                  onChange={(e) => {
                    const updatedPrescription = [...prescription];
                    updatedPrescription[index].frequency = e.target.value;
                    setPrescription(updatedPrescription);
                  }}
                />
              </td>
              <td>
                <Form.Control
                  type="text"
                  placeholder="Thời gian dùng thuốc"
                  onChange={(e) => {
                    const updatedPrescription = [...prescription];
                    updatedPrescription[index].duration = e.target.value;
                    setPrescription(updatedPrescription);
                  }}
                />
              </td>
              <td>
                <Form.Control
                  type="number"
                  placeholder="Số Lượng"
                  onChange={(e) => {
                    const updatedPrescription = [...prescription];
                    updatedPrescription[index].total = e.target.value;
                    setPrescription(updatedPrescription);
                  }}
                />
              </td>
              <td>{medicine.unitName}</td>
              <td>
                
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <button
        class="btn btn-info mb-5 mt-5"
        id="export"
        onClick={processTable}
        style={{ marginLeft: "auto", display: "block", marginRight: "10%" }}
      >
        <i class="fa-solid fa-file-export"></i>
        <span>Xuất Toa Thuốc</span>
      </button>
    </>
  );
};
export default Prescription;
