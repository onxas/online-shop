function logoutButton() {
    $.ajax({
        url: "http://localhost:8080/logout",
        method: "GET",
        success: function () {
            window.location.replace("http://localhost:4200/home.html");
        },
        xhrFields: {
            withCredentials: true
        }
    })
}