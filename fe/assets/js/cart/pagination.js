function nextPage() {
    let url = new URL("http://localhost:4200/cart.html");
    const searchString = new URLSearchParams(window.location.search);
    let page = searchString.get('page');
    if (page == null)
        page = 0;
    page++;
    url.searchParams.set('page', page.toString());
    window.location.replace(url.toString());
}

function prevPage() {
    let url = new URL("http://localhost:4200/cart.html");
    const searchString = new URLSearchParams(window.location.search);
    let page = searchString.get('page');
    if (page != null && Number(page) > 0)
        page--;
    url.searchParams.set('page', page.toString());
    window.location.replace(url.toString());
}