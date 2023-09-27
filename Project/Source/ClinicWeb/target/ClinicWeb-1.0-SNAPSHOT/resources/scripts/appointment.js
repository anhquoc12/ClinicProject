/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function cancleAppointment(path) {
    if(confirm("Bạn có muốn huỷ lịch hẹn này không?") === true) {
        fetch(path, {
            method: "put"
        }).then(res => {
            if (res.status === 200) {
                location.reload()
                alert("Huỷ hẹn thành công!!")
            } else {
                alert("Có lỗi xảy ra! Vui Lòng Thử Lại")
            }
        })
    }
}

function confirmAppointment(path) {
    fetch(path, {
            method: "put"
        }).then(res => {
            if (res.status === 200) {
                location.reload()
                alert("Xác Nhận Thành công")
            } else {
                alert("Có lỗi xảy ra! Vui Lòng Thử Lại")
            }
        })
}

function presentAppointment(path) {
    fetch(path, {
            method: "put"
        }).then(res => {
            if (res.status === 200) {
                location.reload()
                alert("Xác nhận đã đến")
            } else {
                alert("Có lỗi xảy ra! Vui Lòng Thử Lại")
            }
        })
}