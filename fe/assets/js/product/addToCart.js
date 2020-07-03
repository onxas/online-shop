$("#addToCartForm").submit(function (e) {
    e.preventDefault();

    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');
    let quantity = document.getElementById("quantityInput").value;
    const request = {
        productId: id,
        quantity: quantity
    };
    $.ajax({
        url: "http://localhost:8080/cart",
        method: "post",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(request),
        statusCode:
            {
                401: function () {
                    window.location.replace("http://localhost:4200/login.html");
                },
                200: function () {
                    alert("Товар успешно доабвлен!");
                    window.location.reload();
                },
                400: function () {
                    alert("Слишком много товаров!");
                }
            }
        ,
        xhrFields: {
            withCredentials: true
        }
    });
});
