/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function deleteUnitMedicine(path) {
    if(confirm("Bạn có muốn xoá loại đơn vị này?") === true) {
        location.reload()
        fetch(path, {
            method: "delete"
        }).then(res => {
            if(res.status === 204){
                location.reload()
            }
            else
                alert("Failed!! Vui lòng thử lại sau.")
        })
    }
}
