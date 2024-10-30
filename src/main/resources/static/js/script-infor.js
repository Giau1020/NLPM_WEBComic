
// Gọi API để lấy username và hiển thị trong thẻ input
fetch('/user/info/username')
.then(response => {
   if (!response.ok) {
       throw new Error('Unable to fetch username');
   }
   return response.text();  // Trả về chuỗi username
})
.then(username => {
   document.getElementById('username').value = username;
})
.catch(error => {
   console.error('Error:', error);
});

// Gọi API để lấy email và hiển thị trong thẻ input
fetch('/user/info/email')
.then(response => {
   if (!response.ok) {
       throw new Error('Unable to fetch email');
   }
   return response.text();  // Trả về chuỗi email
})
.then(email => {
   document.getElementById('email').value = email;
})
.catch(error => {
   console.error('Error:', error);
});

// gọi API cho phonenumber
fetch('/user/info/phonenumber')
.then(response => {
   if (!response.ok) {
       throw new Error('Unable to fetch email');
   }
   return response.text();  // Trả về chuỗi sdt
})
.then(phonenumber => {
   document.getElementById('phonenumber').value = phonenumber;
})
.catch(error => {
   console.error('Error:', error);
});
// gọi API lấy giới tính
fetch('/user/info/gender')
.then(response => {
   if (!response.ok) {
       throw new Error('Unable to fetch email');
   }
   return response.text();  // Trả về chuỗi sdt
})
.then(gender => {
   document.getElementById('gender').value = gender;
})
.catch(error => {
   console.error('Error:', error);
});
//gọi API lấy ngày sinh
fetch('/user/info/birthdate')
.then(response => {
   if (!response.ok) {
       throw new Error('Unable to fetch email');
   }
   return response.text();  // Trả về chuỗi sdt
})
.then(birthdate => {
   document.getElementById('birthdate').value = birthdate;
})
.catch(error => {
   console.error('Error:', error);
});
