function nextPage() {
    let url = new URL("http://localhost:4200/order.html");
    const searchString = new URLSearchParams(window.location.search);
    let page = searchString.get('page');
    let id = searchString.get('id');
    if (page == null)
        page = 0;
    page++;
    url.searchParams.set('page', page.toString());
    url.searchParams.set('id', id);
    window.location.replace(url.toString());
}

function prevPage() {
    let url = new URL("http://localhost:4200/order.html");
    const searchString = new URLSearchParams(window.location.search);
    let page = searchString.get('page');
    let id = searchString.get('id');
    if (page != null && Number(page) > 0)
        page--;
    url.searchParams.set('page', page.toString());
    url.searchParams.set('id', id);
    window.location.replace(url.toString());
}