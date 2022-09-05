// $(document).ready(function () {
//     $('#table-verba-geral').DataTable({
//         "language": {
//             "url": "//cdn.datatables.net/plug-ins/1.12.1/i18n/pt-BR.json"
//         },
//         "searching": true,
//         paging: false,
//     });
// });


// const tabItem = document.querySelectorAll('#myTab a');
// tabItem.forEach((item) => {
//     $(item).on('click', function (e) {
//         // e.preventDefault()
//         $(this).tab('show')
//
//
//     })
//
// })
const params =  window.location.search.substring(1).split('&');

const status = {
    'status=ANDAMENTO': '#home-tab',
    'status=ABERTO': '#aberto-tab',


}


const tabItem1 = document.querySelector(status[params[2]]);
const AllTabItem = document.querySelectorAll('#myTab a');
AllTabItem.forEach((item) => item.classList.remove('active'));
tabItem1.classList.add('active')