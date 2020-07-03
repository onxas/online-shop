window.onload = function () {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user",
        dataType: "json",
        success: function (json) {
            if (json.role !== "ADMIN") {
                window.location.replace("http://localhost:4200/login.html");
            }
        },
        error: function () {
            window.location.replace("http://localhost:4200/login.html");
        }
        ,
        xhrFields: {
            withCredentials: true
        },
        async: false
    });
    $.ajax({
        url: "http://localhost:8080/statistic",
        method: "get",
        dataType: "json",
        contentType: "application/json",
        success: function (json) {
            $("#productCount").html("Всего товаров " + json.productCount);
            $("#productAvailableCount").html("Доступных товаров " + json.availableCount);
            $("#mostOrderedProductHeading").html(json.mostOrderedProduct.name);
            $("#mostOrderedProductDescr").html(json.mostOrderedProduct.description);
            $("#mostOrderedProductImg").attr({"src": "http://localhost:8080/img/" + json.mostOrderedProduct.imageFileName});
            $("#mostOrderedProductPrice").html(json.mostOrderedProduct.price + " У.Е.");
            $("#mostOrderedProductLink").attr("href", "http://localhost:4200/productPage.html?id=" + json.mostOrderedProduct.id);
            $("#mostRatedProductHeading").html(json.mostRatedProduct.name);
            $("#mostRatedProductDescr").html(json.mostRatedProduct.description);
            $("#mostRatedProductImg").attr({"src": "http://localhost:8080/img/" + json.mostRatedProduct.imageFileName});
            $("#mostRatedProductPrice").html(json.mostRatedProduct.price + " У.Е.");
            $("#mostRatedProductLink").attr("href", "http://localhost:4200/productPage.html?id=" + json.mostRatedProduct.id);
        },
        xhrFields: {
            withCredentials: true
        }
    });


};