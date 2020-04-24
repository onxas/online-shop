$("#changeProductForm").submit(function (e) {
    e.preventDefault();
    const searchString = new URLSearchParams(window.location.search);
    const id = searchString.get('id');
    let name, description, price, image, category;
    $.ajax({
        method: "GET",
        url: "http://localhost:8080/product/" + id,
        dataType: "json",
        async: false,
        success: function (json) {
            name = json.name;
            description = json.description;
            price = json.price;
            image = json.image;
            category = json.category;
        }
    });
    let newName = document.getElementById("name").value;
    let newDescription = document.getElementById("description").value;
    let newPrice = document.getElementById("price").value;
    let newImage = document.getElementById("image").value;
    let newCategory = document.getElementById("category").value;
    if (newName !== "") name = newName;
    if (newDescription !== "") description = newDescription;
    if (newPrice !== "") price = newPrice;
    if (newImage !== "") image = newImage;
    if (newCategory !== "") category = newCategory;
    const request = {
        name: name,
        description: description,
        price: price,
        image: image,
        category: category
    };
    $.ajax({
        url: "http://localhost:8080/product/" + id,
        method: "patch",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(request),
        async: false,
        statusCode: {
            200: function () {
                window.location.replace("http://localhost:4200/product.html?id=" + id);
            }
        }
    });
});