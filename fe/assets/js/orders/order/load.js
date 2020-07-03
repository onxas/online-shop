window.onload = function () {
    const searchString = new URLSearchParams(window.location.search);
    const page = searchString.get('page');
    const id = searchString.get('id');
    let url = new URL("http://localhost:8080/order/" + id);
    let newSearchString = url.searchParams;
    if (page != null)
        newSearchString.set('page', page);
    $.ajax({
        url: url.toString(),
        method: "get",
        dataType: "json",
        contentType: "application/json",
        xhrFields: {
            withCredentials: true
        },
        success: function (json) {
            $("#header").html("Заказ " + json.id);
            for (let i = 0; i < 4; i++) {
                if (json.orderedItems[i] != null) {
                    $("#card" + (i + 1).toString() + "Heading").html(json.orderedItems[i].product.name);
                    $("#card" + (i + 1).toString() + "Img").attr({"src": "http://localhost:8080/img/" + json.orderedItems[i].product.imageFileName});
                    $("#card" + (i + 1).toString() + "Descr").html(json.orderedItems[i].product.description);
                    $("#card" + (i + 1).toString() + "Price").html(json.orderedItems[i].product.price + " У.Е.");
                    $("#card" + (i + 1).toString() + "Quantity").html("Заказано " + json.orderedItems[i].quantity + " штук");
                    $("#card" + (i + 1).toString() + "Link").attr("href", "http://localhost:4200/productPage.html?id=" + json.orderedItems[i].product.id);
                    $("#card" + (i + 1).toString() + "Feedback").attr("href", "http://localhost:4200/makeFeedback.html?id=" + json.orderedItems[i].product.id);
                } else $("#card" + (i + 1).toString()).hide();
            }

        },
        statusCode: {
            401: function () {
                window.location.replace("http://localhost:4200/login.html");
            }
        }
    });
};
