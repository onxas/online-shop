window.onload = function () {
    const searchString = new URLSearchParams(window.location.search);
    let search = searchString.get('search');
    let category = searchString.get('category');
    let page = searchString.get('page');
    let select = $("#category");
    let url = new URL("http://localhost:8080/product");
    let newSearchString = url.searchParams;
    if (page != null)
        newSearchString.set('page', page);
    if (category != null)
        newSearchString.set('category', category);
    select.val(category);
    if (search != null) {
        newSearchString.set('search', search);
        $("#searchInput").val(search);
    }
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
                }
            }
            ,
            xhrFields: {
                withCredentials: true
            }
        }
    );
};