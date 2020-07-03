window.onload = function () {
    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/product/" + id,
        dataType: "json",
        success: function (json) {
            $("#cardHeading").html(json.name);
            $("#cardDescr").html(json.description);
            $("#cardImg").attr({"src": "http://localhost:8080/img/" + json.imageFileName});
            $("#cardPrice").html(json.price + " У.Е.");
            $("#productAmount").html(json.amount + "Штук");
            $("#feedback").attr("href", "http://localhost:4200/feedback.html?id=" + id);
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
    });
};
