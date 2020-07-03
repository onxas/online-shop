$("#addProductForm").submit(function (e) {
    e.preventDefault();
    var file = $("#picture").prop('files')[0];
    const request = {
        name: document.getElementById("name").value,
        description: document.getElementById("description").value,
        price: document.getElementById("price").value,
        category: document.getElementById("category").value,
        amount: document.getElementById("amount").value
    };
    const json = JSON.stringify(request);
    const blob = new Blob([json], {
        type: 'application/json'
    });
    const data = new FormData();
    data.append("json", blob);
    data.append("file", file);
    $.ajax({
        url: "http://localhost:8080/product",
        method: "post",
        dataType: "json",
        contentType: false,
        processData: false,
        data: data,
        statusCode: {
            200: function () {
                window.location.replace("http://localhost:4200/home.html");
            }
        },
        xhrFields: {
            withCredentials: true
        }
    });
});

