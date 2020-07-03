function nextPage() {
    const searchString = new URLSearchParams(window.location.search);
    let id = searchString.get('id');
    let url = new URL("http://localhost:4200/feedback.html");
    let page = searchString.get('page');
    if (page == null)
        page = 0;
    page++;
    url.searchParams.set('page', page.toString());
    url.searchParams.set('id', id);
    window.location.replace(url.toString());
}

function prevPage() {
    const searchString = new URLSearchParams(window.location.search);
    let id = searchString.get('id');
    let url = new URL("http://localhost:4200/feedback   .html");
    let page = searchString.get('page');
    if (page != null && Number(page) > 0)
        page--;
    url.searchParams.set('page', page.toString());
    url.searchParams.set('id', id);
    window.location.replace(url.toString());
}