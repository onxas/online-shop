window.onload = function () {
    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');
    const page = searchString.get('page');
    let url = new URL("http://localhost:8080/product/" + id + "/feedback");
    if (page != null)
        url.searchParams.set('page', page);
    $.ajax({
        method: "GET",
        url: url.toString(),
        dataType: "json",
        success: function (json) {
            for (let i = 0; i < 4; i++) {
                if (json[i] != null) {
                    $("#header").html("Отзывы к товару " + json[i].product.name);
                    $("#card" + (i + 1).toString() + "Text").html(json[i].text);
                    $("#card" + (i + 1).toString() + "Author").html(json[i].user.name);
                    $("#card" + (i + 1).toString() + "Rating").html("Оценка " + json[i].rating);
                    var date = new Date(json[i].date);
                    $("#card" + (i + 1).toString() + "Date").html("дата и время отзыва " + date.toLocaleString());
                } else $("#card" + (i + 1).toString()).hide();
            }
        }
    });
};
