window.onload = function () {
    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/product/" + id,
        dataType: "json",
        success: function (json) {
            $("#productId").html("Отзыв к товару " + json.name);
        }
    });
};
