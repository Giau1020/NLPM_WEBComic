          // Khiếu nại
        //Mở modal khiếu nại
        function openComplaintModal(orderID,userId,full_Name) {
         // document.getElementById("complaintModal").style.display = "block"; // Hiển thị modal khiếu nại
         // Hiển thị modal (nếu modal của bạn cần hiển thị)
             const modal = document.getElementById('complaintModal');
             if (modal) {
                 modal.style.display = 'block';
             }

             // Điền thông tin vào các thẻ input
             document.getElementById('Id_order').value = orderID;
             document.getElementById('user_id').value = userId;
             document.getElementById('full_name').value = full_Name;


          // Luôn đặt lại ngày hiện tại mỗi lần mở modal
            //  const dateInput = document.getElementById('complaintTime');
            //  if (dateInput) {
            //      const today = new Date().toISOString().split('T')[0]; // Lấy ngày hiện tại và định dạng yyyy-mm-dd
            //      dateInput.value = today; // Gán giá trị ngày hiện tại
            //  }
            const dateInput = document.getElementById('complaintTime');
            if (dateInput) {
                const now = new Date();
                const date = now.toISOString().split('T')[0]; // Lấy phần ngày theo định dạng yyyy-MM-dd
                const time = now.toTimeString().split(' ')[0].slice(0, 5); // Lấy phần giờ theo định dạng HH:mm, bỏ giây
                const dateTime = `${date} ${time}`; // Kết hợp ngày và giờ, bỏ chữ T
                dateInput.value = dateTime; // Gán giá trị ngày giờ hiện tại
            }
            
                                                 
     }
    
     // Đóng modal khiếu nại
     function closeComplaintModal() {
         document.getElementById("complaintModal").style.display = "none"; // Ẩn modal khiếu nại
         document.getElementById("complaintForm").reset(); // Đặt lại nội dung form
     }

         //khiếu nại
         document.getElementById('complaintForm').addEventListener('submit', function(event) {
         event.preventDefault(); // Ngăn chặn hành vi mặc định
         const userId = document.getElementById('user_id').value;
         const fullName = document.getElementById('full_name').value;
         const orderId = Number(document.getElementById('Id_order').value);
         const complaintTime = document.getElementById('complaintTime').value;
         const complaintContent = document.getElementById('complaintContent').value;
         const fileInput = document.getElementById('attachment');


         const formData = new FormData();
         formData.append('user_id', userId);
         formData.append('full_name', fullName);
         formData.append('id_order', orderId);
         formData.append('complaintTime', complaintTime);
         formData.append('description_complaint', complaintContent);

         if (fileInput.files.length > 0) {
             formData.append('attachment', fileInput.files[0]); // Thêm tệp hình ảnh vào FormData
         }


        // In ra nội dung FormData
         for (const [key, value] of formData.entries()) {
             console.log(key + ': ' + value);
         }

         // Gửi yêu cầu tới server
         fetch('/api/complaint/submit', {
             method: 'POST',
             body: formData
         })
         .then(response => {
             if (!response.ok) {
                 throw new Error('Network response was not ok');
             }
             return response.text();
         })
         .then(data => {
             alert(data);
             document.getElementById('complaintForm').reset();
         })
         .catch(error => {
             console.error('Error:', error.message);
             alert('Có lỗi xảy ra khi gửi khiếu nại. Vui lòng thử lại.');
         });
     });

  // kết thúc script khiếu nại   