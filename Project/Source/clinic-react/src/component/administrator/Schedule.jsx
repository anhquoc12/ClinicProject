import { Alert } from "react-bootstrap";

const Schedule = () => {

    
  return (
    <>
      <Alert variant="danger">test</Alert>
      <div class="container">
        <div class="card">
          <div class="form__name">Scheduling Form</div>
          <div class="time__container">
            <div class="section">
              <div class="box">1</div>
              <span>Date &amp; Time</span>
            </div>
            <form action="" class="form__time">
              <div class="date">
                <label for="date">Date</label> <input id="date" type="date" />
              </div>
              <div class="time">
                <label for="time">Time</label> <input id="time" type="time" />
              </div>
            </form>
          </div>
        </div>
      </div>
    </>
  );
};
export default Schedule;
