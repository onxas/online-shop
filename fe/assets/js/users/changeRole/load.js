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
};