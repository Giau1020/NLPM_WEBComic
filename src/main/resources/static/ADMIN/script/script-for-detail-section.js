function detail_section(){
    let api_count_comic = `http://localhost:8080/api/v1/sng/admin/count_comic`;

   getDataFromAPI(api_count_comic)
   .then(count => {
    document.querySelector('.quantity-products').innerText = count;
    console.log(count);
   }).catch (e => {
    console.log(e);
   });

   let api_count_genre = `http://localhost:8080/api/v1/sng/admin/count_genre`;

   getDataFromAPI(api_count_genre)
   .then(count => {
    document.querySelector('.quantity-genre').innerText = count ;
    console.log("Thể loại: "+ count);
   }).catch (e => {
    console.log(e);
   });


   let api_count_orders = `http://localhost:8080/api/v1/sng/admin/orders/count`;
   getDataFromAPI(api_count_orders)
   .then(count => {
    document.querySelector('.quantity-order').innerText = count ;
    console.log("Thể loại: "+ count);
   }).catch (e => {
    console.log(e);
   });


   let api_count_complaints = `http://localhost:8080/api/complaint/count`;
   getDataFromAPI(api_count_complaints)
   .then(count => {
    document.querySelector('.manage-report-detail').innerText = count ;
    console.log("Thể loại: "+ count);
   }).catch (e => {
    console.log(e);
   });

   let api_count_users = `http://localhost:8080/api/v1/sng/admin/users/count`;
   getDataFromAPI(api_count_users)
   .then(count => {
    document.querySelector('.detail-contact').innerText = count ;
    console.log("Thể loại: "+ count);
   }).catch (e => {
    console.log(e);
   });
   

}
detail_section();