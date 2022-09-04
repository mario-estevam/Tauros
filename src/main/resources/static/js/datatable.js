$(document).ready(function () {
    $('#table-verba-geral').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.12.1/i18n/pt-BR.json"
        },
        "searching": true,
        paging: false,
    });
});

$(document).ready(function () {
    $('#table-setor').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.12.1/i18n/pt-BR.json"
        },
        "searching": true,
        paging: false,
    });
});
const tabItem = document.querySelectorAll('#myTab a');
tabItem.forEach((item) => {
    $(item).on('click', function (e) {
        // e.preventDefault()
        $(this).tab('show')


    })

})

if(document.location.href === 'http://localhost:8080/listar/chamados-admin?size=5&page=1&status=PENDENTE'){
    const tabItem = document.querySelector('#home-tab');
    const AllTabItem = document.querySelectorAll('#myTab a');

    AllTabItem.forEach((item) => item.classList.remove('active'));

    tabItem.classList.add('active')

}

if(document.location.href === 'http://localhost:8080/listar/chamados-admin?size=5&page=1&status=ABERTO'){
    const tabItem = document.querySelector('#aprovada-tab');
    const AllTabItem = document.querySelectorAll('#myTab a');
    console.log("teste")

    AllTabItem.forEach((item) => item.classList.remove('active'));

    tabItem.classList.add('active')

}
