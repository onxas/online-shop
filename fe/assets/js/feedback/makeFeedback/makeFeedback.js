$("#feedbackForm").submit(function (e) {
    e.preventDefault();
    let request = {
        text: document.getElementById("text").value,
        rating: document.getElementById("rating").value
    };
    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');
    $.ajax({
        url: "http://localhost:8080/product/" + id + "/feedback",
        method: "post",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(request),
        statusCode: {
            200: function () {
                window.location.replace("http://localhost:4200/productPage.html?id=" + id);
            }
        },
        xhrFields: {
            withCredentials: true
        }
    });
});
