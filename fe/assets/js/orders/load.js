window.onload = function () {
    let url = new URL("http://localhost:8080/order");
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
        xhrFields: {
            withCredentials: true
        },
        success: function (json) {
            for (let i = 0; i < 4; i++) {
                if (json[i] != null) {
                    $("#card" + (i + 1).toString() + "Name").html("Заказ номер " + json[i].id);
                    var date = new Date(json[i].orderDateAndTime);
                    $("#card" + (i + 1).toString() + "Date").html("дата и время заказа " + date.toLocaleString());
                    $("#card" + (i + 1).toString() + "Link").attr("href", "http://localhost:4200/order.html?id=" + json[i].id);
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