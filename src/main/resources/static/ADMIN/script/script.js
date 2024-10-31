// HÀM GET dữ liệu từ API


 async function getDataFromAPI(url) {
    try {
        const response = await fetch(url);
        
        if (!response.ok) {
            throw new Error(`Lỗi HTTP! Trạng thái: ${response.status}`);
        }
        
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
    }
}

// HÀM POST DATA LÊN API
async function postDataToAPI(url, data) {
    try {
        const response = await fetch(url, {
            method: 'POST', // Hoặc 'PUT' tùy thuộc vào API
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error(`Lỗi HTTP! Trạng thái: ${response.status}`);
        }

        const responseData = await response.json();
        return responseData;
    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
    }
}

// HÀM PUT DATA LÊN API
async function putDataToAPI(url, data) {
    try {
        const response = await fetch(url, {
            method: 'PUT', // Hoặc 'PUT' tùy thuộc vào API
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error(`Lỗi HTTP! Trạng thái: ${response.status}`);
        }

        const responseData = await response.json();
        return responseData;
    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
    }
}

// HÀM DELETE DATA TRONG API
async function deleteDataFromAPI(url) {
    try {
        const response = await fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error(`Lỗi HTTP! Trạng thái: ${response.status}`);
        }

        const responseData = await response.json();
        return responseData;
    } catch (error) {
        console.error('Có lỗi xảy ra:', error);
    }
}



