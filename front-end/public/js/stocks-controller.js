(function() {
    'use strict';

    angular
        .module('app')
        .controller('StocksCtrl', StocksCtrl);

    // Injects
    StocksCtrl.$inject = ['$scope','AppService']
    
    function StocksCtrl($scope, AppService){
        
        var vm = this;
        const FINANCIAL_TYPE = 'stocks';
        // Variables
        vm.currentTicker = "NVDA";
        vm.portfolioList = [];   

        _init();

        function _init(){
            _resetArrays();
            _fetchAllPortfolios();
        }


        // Public Functions
        vm.fetchDataAndRefreshCharts = _fetchDataAndRefreshCharts;
        vm.fetchAllPortfolios = _fetchAllPortfolios;
        vm.toggleModal = _toggleModal;
        vm.savePortfolio = _savePortfolio;
        vm.excludePortfolio = _excludePortfolio;
        // Function Definitions

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

        // Monta os vetores dos graficos e reconstroi os graficos
        function _buildCharts(data){
            
            for(var i in data){

                vm.labels.push(data[i]['infoDate'].substring(0,10)); //"2020-12-06T02:00:00.000+00:00" -> "2020-12-06"
                vm.preco.push(data[i]['preco']);
                // Valuation
                vm.dividendYield.push(data[i]['dividendYield']);
                vm.valorPorAcao.push(data[i]['valorPorAcao']);
                vm.lucroPorAcao.push(data[i]['lucroPorAcao']);
                vm.precoValorPatrimonial.push(data[i]['precoValorPatrimonial']);
                vm.precoLucro.push(data[i]['precoLucro']);
                vm.precoEbit.push(data[i]['precoEbit']);
                vm.enterpriseValueEbit.push(data[i]['enterpriseValueEbit']);
                vm.priceSalesRatio.push(data[i]['priceSalesRatio']);
                vm.pegRatio.push(data[i]['pegRatio']);
                vm.precoAtivoCirculanteLiquido.push(data[i]['precoAtivoCirculanteLiquido']);
                vm.precoAtivos.push(data[i]['precoAtivos']);
                vm.precoCapitalGiro.push(data[i]['precoCapitalGiro']);
                // Rentabilidade
                vm.returnOnAssets.push(data[i]['returnOnAssets']);
                vm.returnOnEquity.push(data[i]['returnOnEquity']);
                vm.returnOverInvestedCapital.push(data[i]['returnOverInvestedCapital']);
                vm.giroAtivos.push(data[i]['giroAtivos']);
                // Crescimento
                vm.cagrLucros.push(data[i]['cagrLucros']);
                vm.cagrReceita.push(data[i]['cagrReceita']);
                // Eficiencia
                vm.margemBruta.push(data[i]['margemBruta']);
                vm.margemLiquida.push(data[i]['margemLiquida']);
                vm.margemEbit.push(data[i]['margemEbit']);
                // Endividamento e liquidez
                vm.passivosSobreAtivos.push(data[i]['passivosSobreAtivos']);
                vm.patrimonioSobreAtivo.push(data[i]['patrimonioSobreAtivo']);

                vm.liquidezCorrente.push(data[i]['liquidezCorrente']);
                vm.liquidezMediaDiria.push(data[i]['liquidezMediaDiria']);
                vm.valorDeMercado.push(data[i]['valorDeMercado']);

            }

            _reloadAllCharts();
        }

        function _reloadAllCharts(){

            console.log("M=_reloadAllCharts step=start")
            var precoChart = _configChart('Preço', vm.labels, vm.preco);
            _reloadChartById('precoChart',precoChart);
            var dividendYieldChart = _configChart('Dividend Yield', vm.labels, vm.dividendYield);
            _reloadChartById('dividendYieldChart',dividendYieldChart);
            var valorPorAcaoChart = _configChart('Valor por Ação', vm.labels, vm.valorPorAcao);
            _reloadChartById('valorPorAcaoChart',valorPorAcaoChart);
            var lucroPorAcaoChart = _configChart('Lucro por Ação', vm.labels, vm.lucroPorAcao);
            _reloadChartById('lucroPorAcaoChart',lucroPorAcaoChart);
            var precoValorPatrimonialChart = _configChart('Preço/Valor Patrim.', vm.labels, vm.precoValorPatrimonial);
            _reloadChartById('precoValorPatrimonialChart',precoValorPatrimonialChart);
            var precoLucroChart = _configChart('Preço/Lucro', vm.labels, vm.precoLucro);
            _reloadChartById('precoLucroChart',precoLucroChart);
            var precoEbitChart = _configChart('Preço/EBIT', vm.labels, vm.precoEbit);
            _reloadChartById('precoEbitChart',precoEbitChart);
            var enterpriseValueEbitChart = _configChart('EV/EBIT', vm.labels, vm.enterpriseValueEbit);
            _reloadChartById('enterpriseValueEbitChart',enterpriseValueEbitChart);
            var priceSalesRatioChart = _configChart('PSR', vm.labels, vm.priceSalesRatio);
            _reloadChartById('priceSalesRatioChart',priceSalesRatioChart);
            var pegRatioChart = _configChart('PEG Ratio', vm.labels, vm.pegRatio);
            _reloadChartById('pegRatioChart',pegRatioChart);
            var precoAtivoCirculanteLiquidoChart = _configChart('Preço/Ativo Circ. Liq.', vm.labels, vm.precoAtivoCirculanteLiquido);
            _reloadChartById('precoAtivoCirculanteLiquidoChart',precoAtivoCirculanteLiquidoChart);
            var precoAtivosChart = _configChart('Preço/Ativo.', vm.labels, vm.precoAtivos);
            _reloadChartById('precoAtivosChart',precoAtivosChart);
            var precoCapitalGiroChart = _configChart('Preço/Cap. Giro.', vm.labels, vm.precoCapitalGiro);
            _reloadChartById('precoCapitalGiroChart',precoCapitalGiroChart);            
            // Rentabilidade
            var returnOnAssetsChart = _configChart('ROA', vm.labels, vm.returnOnAssets);
            _reloadChartById('returnOnAssetsChart',returnOnAssetsChart);
            var returnOnEquityChart = _configChart('ROE', vm.labels, vm.returnOnEquity);
            _reloadChartById('returnOnEquityChart',returnOnEquityChart);
            var returnOverInvestedCapitalChart = _configChart('ROIC', vm.labels, vm.returnOverInvestedCapital);
            _reloadChartById('returnOverInvestedCapitalChart',returnOverInvestedCapitalChart);
            var giroAtivosChart = _configChart('Giro / Ativos', vm.labels, vm.giroAtivos);
            _reloadChartById('giroAtivosChart',giroAtivosChart);
            // Crescimento
            var cagrLucrosChart = _configChart('CAGR Lucros', vm.labels, vm.cagrLucros);
            _reloadChartById('cagrLucrosChart',cagrLucrosChart);
            var cagrReceitaChart = _configChart('CAGR Receita', vm.labels, vm.cagrReceita);
            _reloadChartById('cagrReceitaChart',cagrReceitaChart);
            // Eficiencia
            var margemBrutaChart = _configChart('Marg. Bruta', vm.labels, vm.margemBruta);
            _reloadChartById('margemBrutaChart',margemBrutaChart);
            var margemLiquidaChart = _configChart('Marg. Liq.', vm.labels, vm.margemLiquida);
            _reloadChartById('margemLiquidaChart',margemLiquidaChart);
            var margemEbitChart = _configChart('Marg. EBIT', vm.labels, vm.margemEbit);
            _reloadChartById('margemEbitChart',margemEbitChart);

            // Endividamento e liquidez
            var passivosSobreAtivosChart = _configChart('Passivos/Ativos', vm.labels, vm.passivosSobreAtivos);
            _reloadChartById('passivosSobreAtivosChart',passivosSobreAtivosChart);
            var patrimonioSobreAtivoChart = _configChart('Patrim./ Ativos', vm.labels, vm.patrimonioSobreAtivo);
            _reloadChartById('patrimonioSobreAtivoChart',patrimonioSobreAtivoChart);
            var liquidezCorrenteChart = _configChart('Liquidez Corrente', vm.labels, vm.liquidezCorrente);
            _reloadChartById('liquidezCorrenteChart',liquidezCorrenteChart);
            var liquidezMediaDiriaChart = _configChart('Liq. Média Diária', vm.labels, vm.liquidezMediaDiria);
            _reloadChartById('liquidezMediaDiriaChart',liquidezMediaDiriaChart);
            var valorDeMercadoChart = _configChart('Valor de Mercado', vm.labels, vm.valorDeMercado);
            _reloadChartById('valorDeMercadoChart',valorDeMercadoChart);

            
            console.log("M=_reloadAllCharts step=end")
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

        function _resetArrays(){
            vm.labels = [];
            vm.preco = [];
            // Valuation
            vm.dividendYield = [];
            vm.valorPorAcao = [];
            vm.lucroPorAcao = [];
            vm.precoValorPatrimonial = [];
            vm.precoLucro = [];
            vm.precoEbit = [];
            vm.enterpriseValueEbit = [];
            vm.priceSalesRatio = [];
            vm.pegRatio = [];
            vm.precoAtivoCirculanteLiquido = [];
            vm.precoAtivos = [];
            vm.precoCapitalGiro = [];
            // Rentabilidade
            vm.returnOnAssets = [];
            vm.returnOnEquity = [];
            vm.returnOverInvestedCapital = [];
            vm.giroAtivos = [];
            // Crescimento
            vm.cagrLucros = [];
            vm.cagrReceita = [];
            // Eficiencia
            vm.margemBruta = [];
            vm.margemLiquida = [];
            vm.margemEbit = [];
            // Endividamento e liquidez
            vm.passivosSobreAtivos = [];
            vm.patrimonioSobreAtivo = [];
            vm.liquidezCorrente = [];
            vm.liquidezMediaDiria = [];
            vm.valorDeMercado = [];
        }


        function _genericError(err){
            alert("Falha ao executar - consulte o log")
            console.log(err);
        }

    } // end StocksCtrl
    
})();

