/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */


function pay(path) {
    var xhr = new XMLHttpRequest()
    xhr.open('POST', path, true)
    xhr.onreadystatechange = () => {
        if (xhr.status === 200) {
            if (xhr.responseText === 'SUCCESS')
                window.location.href = '/ClinicWeb/nurse/invoices'

        } else
            alert("Có lỗi xảy vui lòng thử lại")
    }
    xhr.send()
}