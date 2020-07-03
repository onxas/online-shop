window.onload = function () {
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
    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/product/" + id,
        dataType: "json",
        async: false,
        success: function (json) {
            $("#name").val(json.name);
            $("#description").val(json.description);
            $("#price").val(json.price);
            picture = json.picture;
            $("#category").val(json.category);
            $("#amount").val(json.amount);
        }
    });
};