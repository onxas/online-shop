function loginButtonClick() {
    $("#email-warning").hide();
    $("#password-warning").hide();
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var formData = new FormData();
    formData.append('username', email);
    formData.append('password', password);

    $.ajax({
        url: "http://localhost:8080/login",
        data: formData,
        method: "POST",
        processData: false,
        contentType: false,
        xhrFields: {
            withCredentials: true
        },
        success: function () {
            window.location.replace("http://localhost:4200/home.html");
        },
        error: function () {
            $("#email-warning").show();
            $("#password-warning").show();
        }
    });
}
