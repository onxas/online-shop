function makeOrder() {
    $.ajax({
        url: "http://localhost:8080/order",
        method: "POST",
        xhrFields: {
            withCredentials: true
        },
        statusCode: {
            200: function () {
                window.location.reload();
            },
            400: function () {
                alert("неудалось сделать заказ(возможно товары закончились");
            }
        }
    });
}