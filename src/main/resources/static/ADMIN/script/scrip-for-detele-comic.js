let checkArray = [];
function updateCheckbox(){
    // Check box tổng
    let checkboxAll = document.querySelector('#checkbox-delete-comic');
    // Tất cả checkbox con
    let checkboxes = document.querySelectorAll('input.checkbox-delete-comicc');
    console.log("Checkboxes tìm thấy:", checkboxes.length);
    // Khi checkboxAll đc check thì tất cả các checkbox con cũng được check
    checkboxAll.addEventListener('change', (event) => {
        
        checkboxes.forEach((checkbox) => {
            checkbox.checked = event.target.checked;
            let id =  getComicIdFromRow(checkbox);
            // Sử dụng trạng thái của checkbox tổng
            if(checkboxAll.checked){
                if (!checkArray.includes(id)) { // Kiểm tra xem ID đã có trong mảng chưa
                    checkArray.push(id);
                }
            }else {
                checkArray = [];
            }
        });
        console.log(checkArray);
        console.log("checkbox tổng đc check hoặc bỏ check");
    });

    // Khi một checkbox con thay đổi, cập nhật trạng thái của checkboxAll
    checkboxes.forEach(check => {
        check.addEventListener('change', () => {
            if (!check.checked) {
                checkboxAll.checked = false; // Bỏ chọn checkbox tổng nếu bất kỳ checkbox con nào không được check
            } else if (Array.from(checkboxes).every(check => check.checked)) {
                checkboxAll.checked = true; // Chọn checkbox tổng nếu tất cả checkbox con đều được check
            }
        });
    });

    checkboxes.forEach((checkbox) => {
        checkbox.addEventListener('change', (event) => {
            event.preventDefault();
            let id = getComicIdFromRow(checkbox);
            if (checkbox.checked) {
                if (!checkArray.includes(id)) { // Kiểm tra xem ID đã có trong mảng chưa
                    console.log("check: ", id); 
                    checkArray.push(id);
                }
            }else if (!checkbox.checked) {
                while (checkArray.indexOf(id) > -1) {
                    checkArray.splice(checkArray.indexOf(id), 1);
                }
                console.log("uncheck: ", id); // Lấy và in ID của truyện nếu checkbox được check
            }else;
           // console.log("checkArray: ", checkArray);
            return;
        });
        
    });
}

async function closeDelete(){
    click_delete =1;
        document.querySelector('.delete-comic').style.display = 'none';
        document.querySelector('.select-all-comic').style.display = 'none';
        document.querySelector('.btn-delete-comic').style.display = 'none';
        document.querySelector('.btn-close-delete-mode').style.display = 'none';
        isColumnHidden = !isColumnHidden; // Toggle the state
        await toggleColumnVisibility(5, false);
        
}


// Hàm lấy ID của truyện trong cột đầu tiên của hàng chứa checkbox được check
function getComicIdFromRow(checkbox) {
    // Tìm hàng chứa checkbox được check
    const row = checkbox.closest('tr');
    let    comicId;
    if (row) {
        // Lấy nội dung của ô đầu tiên (cột ID)
      comicId = row.querySelector('.id_comic_detail').textContent;
       // console.log('ID của truyện:', comicId);
    }
    return comicId;
}


async function deleteComic(){
   let api_update_quantity = `http://localhost:8080/api/v1/sng/admin/reset-quantity`;
   try {
    const response = await fetch(api_update_quantity, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(checkArray)
    });

    if (response.ok) {
        const message = await response.text();
        alert("Đã chuyển trạng thái truyện sang hết hàng!!!");
    } else if (response.status === 404) {
        console.error("One or more comics not found.");
        alert("Xóa không thành công");
    } else {
        console.error("Failed to reset quantity count: " + response.statusText);
    }
} catch (error) {
    console.error("An error occurred:", error);
}

    
}


