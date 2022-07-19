(function() {
    'use strict';

    angular
        .module('app')
        .controller('FiisController', FiisController);

    // Injects
    FiisController.$inject = ['$scope','AppService']
    
    function FiisController($scope, AppService){
        var vm = this;
        const FINANCIAL_TYPE = 'fiis';
        // Variables
        vm.currentTicker = "HGLG11";

        // Public Functions
        vm.fetchDataAndRefreshCharts = _fetchDataAndRefreshCharts;
        vm.fetchAllPortfolios = _fetchAllPortfolios;
        vm.toggleModal = _toggleModal;
        vm.savePortfolio = _savePortfolio;
        vm.excludePortfolio = _excludePortfolio;
        // Function Definitions

        _init();

        function _init(){
            _resetArrays();
        }

        function _fetchDataAndRefreshCharts(){
            console.log("M=_fetchDataAndRefreshCharts ticker=" + vm.currentTicker);
            AppService.buscaDados(FINANCIAL_TYPE, vm.currentTicker, _successCallback, _genericError);

            function _successCallback(resp){
                console.log("M=_fetchDataAndRefreshCharts inner=_successCallback");
                _resetArrays();
                console.log(resp.data);
                var data = resp.data;

                _buildCharts(data);
            }
        }

        // Monta os vetores dos graficos e reconstroi os graficos
        function _buildCharts(data){
            
            for(var i in data){

                vm.labels.push(data[i]['infoDate'].substring(0,10)); //"2020-12-06T02:00:00.000+00:00" -> "2020-12-06"
                vm.preco.push(data[i]['preco']);
                // Valuation
                vm.ultimoDividendo.push(data[i]['ultimoDividendo']);
                vm.dividendYield.push(data[i]['dividendYield']);
                vm.valorPatrimonial.push(data[i]['valorPatrimonial']);
                vm.precoValorPatrimonial.push(data[i]['precoValorPatrimonial']);
                vm.percentualEmCaixa.push(data[i]['percentualEmCaixa']);
                vm.cagrDividendos.push(data[i]['cagrDividendos']);
                vm.cagrCota.push(data[i]['cagrCota']);
                vm.patrimonio.push(data[i]['patrimonio']);
                vm.cotistas.push(data[i]['cotistas']);
                vm.cotas.push(data[i]['cotas']);
            }

            _reloadAllCharts();
        }

        function _resetArrays(){
            vm.labels = [];
            vm.preco = [];
            // Valuation
            vm.ultimoDividendo = [];
            vm.dividendYield = [];
            vm.precoValorPatrimonial = [];

            vm.percentualEmCaixa = [];
            vm.cagrDividendos = [];
            vm.cagrCota = [];
            vm.valorPatrimonial = [];
            vm.patrimonio = [];
            vm.cotistas = [];
            vm.cotas = [];
        }
            

        function _reloadAllCharts(){
            console.log("M=_reloadAllCharts step=start");

            var ultimoDividendoChart = _configChart('Ultimo Dividendo', vm.labels, vm.ultimoDividendo);
            _reloadChartById('ultimoDividendoChart',ultimoDividendoChart);
            var dividendYieldChart = _configChart('Dividend Yield', vm.labels, vm.dividendYield);
            _reloadChartById('dividendYieldChart',dividendYieldChart);
            var precoValorPatrimonialChart = _configChart('P/VP', vm.labels, vm.precoValorPatrimonial);
            _reloadChartById('precoValorPatrimonialChart',precoValorPatrimonialChart);


            var percentualEmCaixaChart = _configChart('Percentual em Caixa', vm.labels, vm.percentualEmCaixa);
            _reloadChartById('percentualEmCaixaChart',percentualEmCaixaChart);
            var cagrDividendosChart = _configChart('CAGR Dividendos', vm.labels, vm.cagrDividendos);
            _reloadChartById('cagrDividendosChart',cagrDividendosChart);
            var cagrCotaChart = _configChart('CAGR Cota', vm.labels, vm.cagrCota);
            _reloadChartById('cagrCotaChart',cagrCotaChart);

            var valorPatrimonialChart = _configChart('Valor Patrimonial', vm.labels, vm.valorPatrimonial);
            _reloadChartById('valorPatrimonialChart',valorPatrimonialChart);
            var patrimonioChart = _configChart('Patrimonio', vm.labels, vm.patrimonio);
            _reloadChartById('patrimonioChart',patrimonioChart);

            var cotistasChart = _configChart('Cotistas', vm.labels, vm.cotistas);
            _reloadChartById('cotistasChart',cotistasChart);
            var cotasChart = _configChart('Cotas', vm.labels, vm.cotas);
            _reloadChartById('cotasChart',cotasChart);

            var precoChart = _configChart('Preço', vm.labels, vm.preco);
            _reloadChartById('precoChart',precoChart);

        }

        function _configChart(chartTitle, labels, chartData){
            var dataset = {
                labels : labels,
                datasets : [{
                    label : chartTitle,
                    backgroundColor: 'rgb(255, 99, 132)',
                    borderColor: 'rgb(255, 99, 132)',
                    data : chartData
                }]
            };

            var resultChart = {
                type : 'line',
                data : dataset,
                options: {}   
            }
            return resultChart;
        }

        function _reloadChartById(chartId, config){
            var currentChart = document.getElementById(chartId);
            var parentElement = currentChart.parentElement;
            currentChart.remove();
            var newChartElement = document.createElement('canvas');
            newChartElement.id = chartId;
            parentElement.appendChild(newChartElement);
            var bla = new Chart(document.getElementById(chartId), config);
        }

        // Busca as carteiras e constroi menu
        function _fetchAllPortfolios(){
            console.log("M=_fetchAllPortfolios step=start");
            AppService.findAllPortfolios(_successCallback, _genericError);

            function _successCallback(resp){
                console.log("M=_fetchAllPortfolios inner=_successCallback");

                var data = resp.data;
                _buildSidenav(data);
            }

            console.log("M=_fetchAllPortfolios step=end");
        }

        function _buildSidenav(portfolios){
            console.log("M=_buildSidenav step=start");
            for(var i = 0; i < portfolios.length; i++){
                var portfolio = {
                    "id" : portfolios[i].id,
                    "name" : portfolios[i].name,
                    "tickers" : portfolios[i].tickers,
                    "tickerList" : portfolios[i].tickers.split(";")
                };
                portfolio.tickerList.splice(-1,1);
                vm.portfolioList.push(portfolio);
            }
            
            const one_second = 1000;
            setTimeout(() => {
                _addListeners();
                console.log("M=_buildSidenav step=end");
            }, one_second);
        }

        function _toggleModal(){
            $('#portfolioManagerModal').modal('toggle')
        }

        function _excludePortfolio(ngIndex){
            console.log(ngIndex)
        }

        function _savePortfolio(ngIndex){
            var port = vm.portfolioList[ngIndex];
            console.log("index=" + ngIndex + " portfolio=" + port.name + " tickers="+ port.tickerList)
            
            AppService.updatePortfolio(port.name, port.tickerList, _successCallback, _genericError);
            
            function _successCallback(resp){
                // nada?
            }
        }

        function _addListeners(){
            //* Loop through all dropdown buttons to toggle between hiding and showing its dropdown content - This allows the user to have multiple dropdowns without any conflict */
            var dropdown = document.getElementsByClassName("dropdown-btn");
            var i;

            for (i = 0; i < dropdown.length; i++) {
                dropdown[i].addEventListener("click", function () {
                    this.classList.toggle("active");
                    var dropdownContent = this.nextElementSibling;
                    if (dropdownContent.style.display === "block") {
                        dropdownContent.style.display = "none";
                    } else {
                        dropdownContent.style.display = "block";
                    }
                });
            }
        }

        function _genericError(err){
            alert("Falha ao executar - consulte o log")
            console.log(err);
        }
    } // end FiisController 
})();