import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';

// Đảm bảo rằng tệp tin "Roboto-Medium.ttf" có trong thư mục của dự án của bạn
import RobotoMedium from '../font/font.ttf';

// Thêm font vào hệ thống tệp tin ảo của PDFMake
pdfMake.vfs = pdfFonts.pdfMake.vfs;
pdfMake.fonts = {
  Roboto: {
    normal: RobotoMedium,
    bold: RobotoMedium,
    italics: RobotoMedium,
    bolditalics: RobotoMedium,
  },
};


const dateFormat = (date) => {
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

const formatDate = (date) => {
    var formattedDate = ('0' + date.getDate()).slice(-2) +
        ('0' + (date.getMonth() + 1)).slice(-2) +
        date.getFullYear();

    return formattedDate;
}

export const ExportPDF = async (object, json) => {
    var tableBody = []
            tableBody.push([{ text: "Tên thuốc", bold: true, font: 'Roboto' },
            { text: "Liều Lượng", bold: true, font: 'Roboto' },
            { text: "Tần suất", bold: true, font: 'Roboto' },
            { text: "Thời gian dùng thuốc", bold: true, font: 'Roboto' },
            { text: "Số Lượng", bold: true, font: 'Roboto' }])

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

            var docDefinition = {
                content: [
                    {
                        text: 'DR ' + object.doctorName,
                        margin: [0, 0, 0, 8],
                        color: '#64BACD',
                        bold: true, font: 'Roboto'
                    },
                    {
                        text: 'Address: ' + object.doctorAddress,
                        margin: [0, 0, 0, 8],
                        bold: true,
                        fontSize: 16, font: 'Roboto'
                    },
                    {
                        text: 'SDT: ' + object.doctorPhone,
                        margin: [0, 0, 0, 8],
                        bold: true,
                        fontSize: 16, font: 'Roboto'
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
                        fontSize: 12, font: 'Roboto'
                    },
                    {
                        text: 'SDT: ' + object.patientPhone,
                        margin: [0, 0, 0, 8],
                        fontSize: 12, font: 'Roboto'
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
                        text: "DR " + object.doctorName,
                        fontSize: 16,
                        bold: true,
                        alignment: 'right',
                        margin: [0, 200, 0, 0], font: 'Roboto'
                    }

                ], padding: [16, 16, 16, 16], alignment: 'center', pageWidth: 500, autoSize: true
            };
            pdfMake.createPdf(docDefinition).download(`${object.file}-${formatDate(new Date())}`)
}


