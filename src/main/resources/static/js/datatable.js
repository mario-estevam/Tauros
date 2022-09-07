const params =  window.location.search.substring(1).split('&');
const status = {
    'status=ANDAMENTO': '#home-tab',
    'status=ABERTO': '#aberto-tab',
    'status=ATRASADO': '#atrasado-tab',
    'status=CONCLUIDO': '#concluido-tab'
}


const tabItem1 = document.querySelector(status[params[2]]);
console.log(params)
const AllTabItem = document.querySelectorAll('#myTab a');
AllTabItem.forEach((item) => item.classList.remove('active'));
tabItem1.classList.add('active')