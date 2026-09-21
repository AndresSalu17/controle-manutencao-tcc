function toggleSidebar() {

    const sidebar = document.querySelector('.app-sidebar');

    if (!sidebar) {
        return;
    }

    sidebar.classList.toggle('open');
}

