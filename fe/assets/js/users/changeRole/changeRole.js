$("#changeUserRoleForm").submit(function (e) {
    e.preventDefault();

    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');

    let role = document.getElementById("role").value;

    let url = new URL("http://localhost:8080/users/" + id);
    url.searchParams.set('role', role);

    $.ajax({
        url: url.toString(),
        method: "patch",
        xhrFields: {
            withCredentials: true
        },
        success: function () {
            window.location.replace("http://localhost:4200/users.html");
        }
    })
});