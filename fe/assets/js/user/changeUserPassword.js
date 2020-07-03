$("#changePasswordForm").submit(function (e) {
    e.preventDefault();
    $("#password-repeat-warning").hide();
    $("#password-warning").hide();

    let newPassword = document.getElementById("newPassword").value;
    let newPasswordAgain = document.getElementById("newPasswordAgain").value;
    if (newPassword !== newPasswordAgain) {
        $("#password-repeat-warning").show();
        return;
    }
    const request = {
        oldPassword: document.getElementById("oldPassword").value,
        newPassword: newPassword
    };
    $.ajax({
        url: "http://localhost:8080/user/password",
        method: "patch",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(request),
        error: function () {
            $("#password-warning").show();
        },
        statusCode: {
            200: function () {
                window.location.replace("http://localhost:4200/userPage.html");
            }
        },
        xhrFields: {
            withCredentials: true
        }
    });


});
