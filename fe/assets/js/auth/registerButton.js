function registerButtonClick() {
    $("#name-warning").hide();
    $("#email-warning").hide();
    $("#password-warning").hide();
    $("#password-repeat-warning").hide();
    var email = document.getElementById("email");
    var password = document.getElementById("password");
    var name = document.getElementById("name");
    var repeatPassword = document.getElementById("password-repeat");
    if (password.value != repeatPassword.value) {
        $("#password-repeat-warning").show();
        return;
    }
    var request = {
        "email": email.value,
        "password": password.value,
        "name": name.value
    };
    var messages;
    $.ajax({
        url: "http://localhost:8080/registration",
        method: "post",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(request),
        statusCode: {
            406: function () {
                $("#email-warning").show();
            },
            200: function () {
                window.location.replace("http://localhost:4200/login.html");
            }
        }
    });
}
