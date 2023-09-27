/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
document.addEventListener("click", function () {
    var table = document.querySelector("#myTable")
    var rows = table.querySelectorAll("tr")
    var btn = document.querySelector("#export")
    if (rows.length <= 1) {
        btn.style.display = "none"
    } else {
        btn.style.display = "block"
    }

})

window.addEventListener("load", function () {
    var table = document.querySelector("#myTable")
    var rows = table.querySelectorAll("tr")
    var btn = document.querySelector("#export")
    if (rows.length <= 1) {
        btn.style.display = "none"
    } else {
        btn.style.display = "block"
    }

})

function getRow() {
    if (checkTable() === false) {
        return null
    }
    var table = document.querySelector("#myTable")
    var rows = table.querySelectorAll("tr")
    var object = []
    for (var i = 1; i < rows.length; i++) {
        var cells = rows[i].querySelectorAll("td")
        var data = {
            medicineId: cells[0].getAttribute('id'),
            name: cells[0].innerText,
            dosage: cells[1].innerText,
            frequency: cells[2].innerText,
            duration: cells[3].innerText,
            totalUnit: cells[4].querySelector("input").value,
            unit: cells[5].innerText
        }
        object.push(data)
    }
    return JSON.stringify(object, null, 2)


}

function checkTable() {
    var table = document.querySelector("#myTable")
    var rows = table.querySelectorAll("tr")
    var check = 5
    for (var i = 1; i < rows.length; i++) {
        var cells = rows[i].querySelectorAll("td")
        for (var j = 1; j < 4; j++) {
            if (cells[j].innerText === "")
                return false
        }
    }
    return true
}

function addRow(element) {
    var rowbtn = element.closest("tr")
    var row = rowbtn.querySelector("button")
    row.classList.add("hidden")
    var name = rowbtn.querySelector("td.medicine--name").innerText
    var unit = rowbtn.querySelector("td.medicine--unit").innerText
    var stock = parseInt(rowbtn.querySelector("td.stock").innerText)
    var medicineId = rowbtn.querySelector(".id").innerText

    const tableBody = document.querySelector("#myTable tbody");
    const newRow = document.createElement("tr");
    newRow.classList.add('table-col', 'prescription')
    newRow.innerHTML = `
                <td id="${medicineId}" class="prescription--name-medicine">${name}</td>
                <td contenteditable="true"></td>
                <td contenteditable="true"></td>
                <td contenteditable="true"></td>
                <td><input type="number" min="1" value="1" max="${stock}" class="count" /></td>
                <td>${unit}</td>
                <td><button class="btn btn-danger" onclick="hiddenBtnAdd(this)">Xoá</button></td>`
    tableBody.appendChild(newRow);
}

function hiddenBtnAdd(element) {
    var e = element.closest("tr")
    var listMedicine = document.querySelectorAll(".list")
    listMedicine.forEach(function (item) {
        if (item.querySelector("td.medicine--name").innerText === e.querySelector(".prescription--name-medicine").innerText) {
            var btn = item.querySelector("button")
            btn.classList.remove("hidden")
            e.remove()
            return
        }
    })
}

function filterTable() {
    var input = document.getElementById("searchInput").value.toUpperCase();
    var table = document.getElementById("dataTable");
    var rows = table.getElementsByTagName("tr");

    for (var i = 0; i < rows.length; i++) {
        var cells = rows[i].getElementsByTagName("td");
        var shouldDisplay = false;

        for (var j = 0; j < cells.length; j++) {
            var cellText = cells[j].textContent || cells[j].innerText;
            if (cellText.toUpperCase().indexOf(input) > -1) {
                shouldDisplay = true;
                break;
            }
        }
        rows[i].style.display = shouldDisplay ? "" : "none"
    }
}

function dateFormat(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    var ampm = hours >= 12 ? 'PM' : 'AM';

    hours = hours % 12;
    hours = hours ? hours : 12; // Đổi 0 thành 12 nếu là 12 giờ

    var formattedDate = ('0' + date.getDate()).slice(-2) + '-' +
        ('0' + (date.getMonth() + 1)).slice(-2) + '-' +
        date.getFullYear() + ' ' +
        ('0' + hours).slice(-2) + ':' +
        ('0' + minutes).slice(-2) + ':' +
        ('0' + seconds).slice(-2) + ' ' + ampm;

    return formattedDate;
}

function formatDate(date) {
    var formattedDate = ('0' + date.getDate()).slice(-2) +
        ('0' + (date.getMonth() + 1)).slice(-2) +
        date.getFullYear();

    return formattedDate;
}

async function exportPrescription(path, object) {
    var data = getRow()
    if (data === null) {
        alert("Vui Lòng Nhập đầy đủ thông tin trong toa thuốc")
        return
    }

    try {
        await exportPDF(object)
        await sendDataByAjax(path, data)
        window.location.href = '/ClinicWeb/doctor/medical'
    } catch (ex) {
        alert(ex.message)
    }
}

async function sendDataByAjax(path, data) {
    return new Promise((resolve, reject) => {
        var xhr = new XMLHttpRequest()
        xhr.open('POST', path, true)
        xhr.onreadystatechange = () => {
            if (xhr.status === 200) {
                if (xhr.responseText === "success") {
                    resolve()
                }
            }
            else {
                reject(new Error("CÓ LỖI XẢY RA! VUI LÒNG THỬ LAI"))
            }
        }
        xhr.send(data)
    })
}

async function exportPDF(object) {
    return new Promise((resolve, reject) => {
        try {
            var json = JSON.parse(getRow())
            var tableBody = []
            var advice = object.advice === 'none' ? "" : object.advice
            tableBody.push([{ text: "Tên thuốc", bold: true },
            { text: "Liều Lượng", bold: true },
            { text: "Tần suất", bold: true },
            { text: "Thời gian dùng thuốc", bold: true },
            { text: "Số Lượng", bold: true }])
            tableBody.push([{
                canvas: [
                    {
                        type: 'line',
                        x1: 0, y1: 0,
                        x2: 450, y2: 0,
                        lineWidth: 1, // Độ dày của đường
                    }
                ]
            }, {}, {}, {}, {}])

            json.forEach(data => {
                tableBody.push([data.name, data.dosage, data.frequency, data.duration, data.totalUnit + ' ' + data.unit])
            })
            var a = "test"
            var docDefinition = {
                content: [
                    {
                        text: 'DR ' + object.doctorName,
                        margin: [0, 0, 0, 8],
                        color: '#64BACD',
                        bold: true
                    },
                    {
                        text: 'Address: ' + object.doctorAddress,
                        margin: [0, 0, 0, 8],
                        bold: true,
                        fontSize: 16
                    },
                    {
                        text: 'SDT: ' + object.doctorPhone,
                        margin: [0, 0, 0, 8],
                        bold: true,
                        fontSize: 16
                    },
                    {
                        canvas: [
                            {
                                type: 'line',
                                x1: 0, y1: 0,
                                x2: 450, y2: 0,
                                lineWidth: 1 // Độ dày của đường
                            }
                        ]
                    },
                    {
                        columns: [
                            {
                                text: 'Patient: ' + object.patientName,
                                alignment: 'left'
                            },
                            {
                                text: 'Date: ' + dateFormat(new Date()),
                                alignment: 'right'
                            }
                        ],
                        margin: [0, 16, 0, 0]
                    },
                    {
                        text: 'Address: ' + object.patientAddress,
                        margin: [0, 8, 0, 8],
                        fontSize: 12
                    },
                    {
                        text: 'SDT: ' + object.patientPhone,
                        margin: [0, 0, 0, 8],
                        fontSize: 12
                    },
                    {
                        canvas: [
                            {
                                type: 'line',
                                x1: 2, y1: 0,
                                x2: 450, y2: 0,
                                lineWidth: 1 // Độ dày của đường
                            }
                        ]
                    },
                    {
                        table: {
                            widths: [98, 66, 98, 80, 60],
                            body: tableBody
                        },
                        width: 500,
                        layout: {
                            hLineWidth: (i, node) => {
                                return 0; // Bỏ đường kẻ ngang
                            },
                            vLineWidth: (i, node) => {
                                return 0; // Bỏ đường kẻ dọc
                            }
                        }
                    },

                    {
                        canvas: [
                            {
                                type: 'line',
                                x1: 0, y1: 0,
                                x2: 450, y2: 0,
                                lineWidth: 1 // Độ dày của đường
                            }
                        ]
                    },
                    {
                        text: "*Advice: " + advice,
                        fontSize: 16,
                        bold: true,
                        margin: [8, 16, 0, 0]
                    },
                    {
                        text: "DR " + object.doctorName,
                        fontSize: 16,
                        bold: true,
                        alignment: 'right',
                        margin: [0, 200, 0, 0]
                    }

                ], padding: [16, 16, 16, 16], alignment: 'center', pageWidth: 500, autoSize: true
            };

            pdfMake.createPdf(docDefinition).download(`${object.file}-${formatDate(new Date())}.pdf`)
            resolve()
        } catch (ex) { reject(new Error(ex.message)) }
    })
}