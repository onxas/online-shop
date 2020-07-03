window.onload = function () {
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/user",
        dataType: "json",
        success: function (json) {
            if (json.name != null)
                $("#name").html("Имя: " + json.name);
            if (json.email != null)
                $("#email").html("Адресс электронной почты: " + json.email);
            if (json.gender != null) {
                let gender;
                switch (json.gender) {
                    case "MALE":
                        gender = "Мужской";
                        break;
                    case "FEMALE":
                        gender = "Женский";
                        break;
                    case "UNIDENTIFIED":
                        gender = "Не указан";
                        break;
                }
                $("#gender").html("Пол: " + gender);
            }

        },
        error: function () {
            window.location.replace("http://localhost:4200/login.html");
        },
        xhrFields: {
            withCredentials: true
        }
    });
};