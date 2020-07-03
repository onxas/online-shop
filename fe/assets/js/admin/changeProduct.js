$("#changeProductForm").submit(function (e) {
    e.preventDefault();
    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');
    let name, description, price, picture, category, amount;
    name = document.getElementById("name").value;
    description = document.getElementById("description").value;
    price = document.getElementById("price").value;
    category = document.getElementById("category").value;
    amount = document.getElementById("amount").value;
    var file = $("#picture").prop('files')[0];
    const request = {
        name: name,
        description: description,
        price: price,
        category: category,
        amount: amount
    };
    const json = JSON.stringify(request);
    const blob = new Blob([json], {
        type: 'application/json'
    });
    const data = new FormData();
    data.append("json", blob);
    data.append("file", file);
    $.ajax({
        url: "http://localhost:8080/product/" + id,
        method: "patch",
        dataType: "json",
        contentType: false,
        processData: false,
        data: data,
        statusCode: {
            200: function () {
                window.location.replace("http://localhost:4200/productPage.html?id=" + id);
            }
        },
        xhrFields: {
            withCredentials: true
        }
    });
});