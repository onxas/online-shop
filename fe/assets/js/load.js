window.onload = init;

function init() {
    if (window.location.pathname === "/registration.html") {
        $("#name-warning").hide();
        $("#email-warning").hide();
        $("#password-warning").hide();
        $("#password-repeat-warning").hide();
    }
    if (window.location.pathname === "/login.html") {
        $("#email-warning").hide();
        $("#password-warning").hide();
    }
    if (window.location.pathname === "/") {
        window.location.replace("http://localhost:4200/home.html");
    }
    if (window.location.pathname === "/home.html") {
        $("#page0").url = "http://vk.com";
        for (let i = 0; i < 4; i++) {
            $("#card" + (i + 1).toString() + "Admin").hide();
            $("#addProductButton").hide();
        }
        let url = "http://localhost:8080/product";
        const searchString = new URLSearchParams(window.location.search);
        const page = searchString.get('page');
        const category = searchString.get('category');
        if (page != null) {
            url += "?page=" + page;
            if (category != null)
                url += "&";
        } else url += "?";
        if (category != null)
            url += "category=" + category;
        $.ajax({
            url: url,
            method: "get",
            dataType: "json",
            contentType: "application/json",
            success: function (json) {
                for (let i = 0; i < 4; i++) {
                    if (json[i] != null) {
                        $("#card" + (i + 1).toString() + "Heading").html(json[i].name);
                        $("#card" + (i + 1).toString() + "Descr").html(json[i].description);
                        $("#card" + (i + 1).toString() + "Img").attr({"src": json[i].pict + ".png"});
                        $("#card" + (i + 1).toString() + "Price").html(json[i].price + " Гривен");
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
                    }
                }
                ,
                xhrFields: {
                    withCredentials: true
                }
            }
        );
    }
    if (window.location.pathname === "/userPage.html") {
        $("#adminButton").hide();
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/user",
            dataType: "json",
            success: function (json) {
                if (json.name != null)
                    $("#name").html("Имя: " + json.name);
            },
            error: function () {
                window.location.replace("http://localhost:4200/login.html");
            },
            xhrFields: {
                withCredentials: true
            }
        });
    }
    if (window.location.pathname === "/productPage.html") {
        const searchString = new URLSearchParams(window.location.search);
        const id = searchString.get('id');
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/product/" + id,
            dataType: "json",
            success: function (json) {
                $("#cardHeading").html(json.name);
                $("#cardDescr").html(json.description);
                $("#cardImg").attr({"src": json.pict + ".png"});
                $("#cardPrice").html(json.price + " Гривен");
                var category;
                switch (json.category) {
                    case "DRINK":
                        category = "Напиток";
                        break;
                    case "FOOD":
                        category = "Еда";
                        break;
                }
                $("#cardCategory").html(category);
            }
        })
    }
    if (window.location.pathname === "/addProductPage.html"
        || window.location.pathname === "/changeProductPage.html") {
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/user",
            dataType: "json",
            success: function (json) {
                if (json.role !== "ADMIN")
                    window.location.replace("http://localhost:4200/home.html");
            },
            error: function () {
                window.location.replace("http://localhost:4200/login.html");
            },
            xhrFields: {
                withCredentials: true
            }
        });
    }
}