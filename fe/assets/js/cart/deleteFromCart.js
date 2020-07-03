$("#delete1CartItemForm").submit(function (e) {
    e.preventDefault();

    const url = "http://localhost:8080/cart";
    const request = {
        productId: $("#product1Id").html(),
        quantity: document.getElementById("cartItem1DeleteInput").value
    };
    $.ajax({
        url: url,
        method: "DELETE",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(request),
        statusCode: {
            200: function () {
                window.location.reload();
            }
        },
        xhrFields: {
            withCredentials: true
        }
    });
});

$("#delete2CartItemForm").submit(function (e) {
    e.preventDefault();

    const url = "http://localhost:8080/cart";
    const request = {
        productId: $("#product2Id").html(),
        quantity: document.getElementById("cartItem2DeleteInput").value
    };
    $.ajax({
        url: url,
        method: "DELETE",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(request),
        statusCode: {
            200: function () {
                window.location.reload();
            }
        },
        xhrFields: {
            withCredentials: true
        }
    });
});

$("#delete3CartItemForm").submit(function (e) {
    e.preventDefault();

    const url = "http://localhost:8080/cart";
    const request = {
        productId: $("#product3Id").html(),
        quantity: document.getElementById("cartItem3DeleteInput").value
    };
    $.ajax({
        url: url,
        method: "DELETE",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(request),
        statusCode: {
            200: function () {
                window.location.reload();
            }
        },
        xhrFields: {
            withCredentials: true
        }
    });
});

$("#delete4CartItemForm").submit(function (e) {
    e.preventDefault();

    const url = "http://localhost:8080/cart";
    const request = {
        productId: $("#product4Id").html(),
        quantity: document.getElementById("cartItem4DeleteInput").value
    };
    $.ajax({
        url: url,
        method: "DELETE",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(request),
        statusCode: {
            200: function () {
                window.location.reload();
            }
        },
        xhrFields: {
            withCredentials: true
        }
    });
});

