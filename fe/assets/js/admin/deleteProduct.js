$("#changeProductForm").bind("reset", function () {
    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');
    $.ajax({
        url: "http://localhost:8080/product/" + id,
        method: "DELETE",
        success: function () {
            window.location.replace("http://localhost:4200/home.html");
        },
        xhrFields: {
            withCredentials: true
        }
    })
});