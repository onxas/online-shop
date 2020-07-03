window.onload = function () {
    for (let i = 0; i < 4; i++) {
        $("#card" + (i + 1).toString() + "Admin").hide();
        $("#addProductButton").hide();
        $("#statisticButton").hide();
        $("#usersButton").hide();
    }
    let url = new URL("http://localhost:8080/product");
    const searchString = new URLSearchParams(window.location.search);
    const page = searchString.get('page');
    let newSearchString = url.searchParams;
    if (page != null)
        newSearchString.set('page', page);
    $.ajax({
        url: url.toString(),
        method: "get",
        dataType: "json",
        contentType: "application/json",
        success: function (json) {
            for (let i = 0; i < 4; i++) {
                if (json[i] != null) {
                    $("#card" + (i + 1).toString() + "Heading").html(json[i].name);
                    $("#card" + (i + 1).toString() + "Descr").html(json[i].description);
                    $("#card" + (i + 1).toString() + "Img").attr({"src": "http://localhost:8080/img/" + json[i].imageFileName});
                    $("#card" + (i + 1).toString() + "Price").html(json[i].price + " У.Е.");
                    $("#card" + (i + 1).toString() + "Link").attr("href", "http://localhost:4200/productPage.html?id=" + json[i].id);
                    $("#card" + (i + 1).toString() + "AdminLink").attr("href", "http://localhost:4200/changeProductPage.html?id=" + json[i].id);
                } else $("#card" + (i + 1).toString()).hide();
            }

        }
    });
    $.ajax({
            method: "GET",
            url: "http://localhost:8080/user",
            dataType: "json",
            success: function (json) {
                if (json.role === "ADMIN") {
                    for (let i = 0; i < 4; i++) {
                        $("#card" + (i + 1).toString() + "Admin").show();
                    }
                    $("#addProductButton").show();
                    $("#statisticButton").show();
                    $("#usersButton").show();
                }
            }
            ,
            xhrFields: {
                withCredentials: true
            }
        }
    );
};