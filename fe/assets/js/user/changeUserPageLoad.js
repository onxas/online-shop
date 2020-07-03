window.onload = function () {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user",
        dataType: "json",
        async: false,
        success: function (json) {
            $("#name").val(json.name);
            $("#email").val(json.email);
            $("#gender").val(json.gender);
        },
        error: function () {
            window.location.replace("http://localhost:4200/login.html");
        },
        xhrFields: {
            withCredentials: true
        }
    });
};