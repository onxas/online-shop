function nextPage() {
    let url = new URL("http://localhost:4200/search.html");
    const searchString = new URLSearchParams(window.location.search);
    let page = searchString.get('page');
    let category = searchString.get('category');
    let search = searchString.get('search');
    if (page == null)
        page = 0;
    page++;
    url.searchParams.set('page', page.toString());
    if (category != null)
        url.searchParams.set('category', category);
    if (search != null && search !== "")
        url.searchParams.set('search', search);
    window.location.replace(url.toString());
}

function prevPage() {
    let url = new URL("http://localhost:4200/search.html");
    const searchString = new URLSearchParams(window.location.search);
    let page = searchString.get('page');
    let category = searchString.get('category');
    let search = searchString.get('search');
    if (page != null && Number(page) > 0)
        page--;
    url.searchParams.set('page', page.toString());
    if (category != null)
        url.searchParams.set('category', category);
    if (search != null && search !== "")
        url.searchParams.set('search', search);
    window.location.replace(url.toString());
}