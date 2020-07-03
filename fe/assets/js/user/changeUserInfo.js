$("#changeUserPageForm").submit(function (e) {
    e.preventDefault();

    let name, email, gender;

    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user",
        dataType: "json",
        async: false,
        success: function (json) {
            name = json.name;
            email = json.email;
            gender = json.gender;
        },
        error: function () {
            window.location.replace("http://localhost:4200/login.html");
        },
        xhrFields: {
            withCredentials: true
        }
    });


    let newName = document.getElementById("name").value;
    let newEmail = document.getElementById("email").value;
    let newGender = document.getElementById("gender").value;

    if (newName !== "") name = newName;
    if (newEmail !== "") email = newEmail;
    if (newGender !== "") gender = newGender;
    const request = {
        email: email,
        name: name,
        gender: gender
    };
    $.ajax({
        url: "http://localhost:8080/user",
        method: "patch",
        dataType: "json",
        contentType: "application/json",
        async: false,
        data: JSON.stringify(request),
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