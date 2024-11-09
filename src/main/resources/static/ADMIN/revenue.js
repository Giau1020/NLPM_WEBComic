  $('#monthlyRevenueForm').on('submit', function(event) {
            event.preventDefault();

            var year = $('#year').val();
            var month = $('#month').val();

            $.get('/revenue/month', { year: year, month: month }, function(data) {
                $('#revenueValue').text(data);
            }).fail(function() {
                alert("Error fetching revenue data.");
            });
        });

        // Handle Quarterly Revenue form submission
        $('#quarterlyRevenueForm').on('submit', function(event) {
            event.preventDefault();

            var year = $('#yearQ').val();
            var quarter = $('#quarter').val();

            $.get('/revenue/quarter', { year: year, quarter: quarter }, function(data) {
                $('#revenueValue').text(data);
            }).fail(function() {
                alert("Error fetching revenue data.");
            });
        });

        // Show Monthly Revenue Form when button is clicked
        $('#showMonthlyForm').on('click', function() {
            $('#monthlyForm').toggle(); // Toggle visibility of the monthly form
            $('#quarterlyForm').hide(); // Hide the quarterly form if it's open
        });

        // Show Quarterly Revenue Form when button is clicked
        $('#showQuarterlyForm').on('click', function() {
            $('#quarterlyForm').toggle(); // Toggle visibility of the quarterly form
            $('#monthlyForm').hide(); // Hide the monthly form if it's open
        });