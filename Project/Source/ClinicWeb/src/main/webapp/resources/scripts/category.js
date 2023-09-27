/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */

function deleteCategory(path) {
    if(confirm("Bạn có muốn xoá loại Thuốc này?") === true) {
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

