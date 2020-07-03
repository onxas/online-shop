function searchButtonClick() {
    let input = document.getElementById("searchInput").value;
    let category = $("#category").val();
    let url = new URL("http://localhost:4200/search.html");
    let newSearchString = url.searchParams;
    if (category != null)
        newSearchString.set('category', category);
    if (input != null && input !== "")
        newSearchString.set('search', input);
    window.location.replace(url.toString());
}