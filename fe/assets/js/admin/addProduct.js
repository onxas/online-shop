$("#addProductForm").submit(function (e) {
    e.preventDefault();
    const request = {
        name: document.getElementById("name").value,
        description: document.getElementById("description").value,
        price: document.getElementById("price").value,
        image: document.getElementById("image").value,
        category: document.getElementById("category").value
    };
    $.ajax({
        url: "http://localhost:8080/product",
        method: "post",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(request),
        statusCode: {
            200: function () {
                window.location.replace("http://localhost:4200/home.html");
            }
        }
    });
});

