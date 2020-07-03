window.onload = function () {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user",
        dataType: "json",
        error: function () {
            window.location.replace("http://localhost:4200/login.html");
        },
        xhrFields: {
            withCredentials: true
        }
    });
    let url = new URL("http://localhost:8080/cart");
    const searchString = new URLSearchParams(window.location.search);
    const page = searchString.get('page');
    let newSearchString = url.searchParams;
    if (page != null)
        newSearchString.set('page', page);
    $.ajax({
        url: url.toString(),
        method: "get",
        dataType: "json",
        contentType: "applicationJson",
        success: function (json) {
            for (let i = 0; i < 4; i++) {
                if (json[i] != null) {
                    $("#card" + (i + 1).toString() + "Heading").html(json[i].product.name);
                    $("#card" + (i + 1).toString() + "Descr").html(json[i].product.description);
                    $("#card" + (i + 1).toString() + "Img").attr({"src": "http://localhost:8080/img/" + json[i].product.imageFileName});
                    $("#card" + (i + 1).toString() + "Price").html(json[i].product.price + " У.Е.");
                    $("#card" + (i + 1).toString() + "Link").attr("href", "http://localhost:4200/productPage.html?id=" + json[i].product.id);
                    $("#product" + (i + 1).toString() + "Id").html(json[i].product.id);
                    $("#cartItem" + (i + 1).toString() + "quantity").html("В корзине: " + json[i].quantity);
                    $("#product" + (i + 1).toString() + "Id").hide();
                } else $("#card" + (i + 1).toString()).hide();
            }
        },
        xhrFields: {
            withCredentials: true
        }
    });
};