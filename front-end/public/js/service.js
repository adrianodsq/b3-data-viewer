(function() {
    'use strict';

    angular
        .module('app')
        .factory('AppService', AppService);

    // Injects
    AppService.$inject = ['$http']

    function AppService($http) {
        // Url computed from the current page address
        // var urlUtilTlv = '../../admin/service/utilTLV';

        var url = "http://localhost:8785"


        // Public API goes here
        return {
            buscaDados : _buscaDados,
            findAllPortfolios : _findAllPortfolios,
            updatePortfolio : _updatePortfolio
        }


        function _buscaDados(financialType, someTicker, successFunc, failureFunc){
            var endpoint = url + '/' + financialType + '/' + someTicker
            console.log("M=_buscaDados step=start endpoint=" + endpoint)
            $http.get(endpoint).then(successFunc, failureFunc);
            console.log("M=_buscaDados step=end endpoint=" + endpoint)
        }

        function _findAllPortfolios(successFunc, failureFunc){
            var endpoint = url + '/portfolio/';
            console.log("M=_findAllPortfolios step=start endpoint=" + endpoint)
            $http.get(endpoint).then(successFunc, failureFunc);
            console.log("M=_findAllPortfolios step=end endpoint=" + endpoint)
        }

        function _updatePortfolio(portfolio, tickerList, successFunc, failureFunc){
            console.log("M=_updatePortfolio step=start portfolio=" + portfolio + " tickers="+tickerList)
            var endpoint = url + '/portfolio/' + portfolio;
            var data = {
                "tickerList" : tickerList
            }
            $http.post(endpoint, data)
                .then(successFunc)
                .catch(failureFunc);

        }

        function _post(url, data, successFunc, failureFunc) {
            var config = {
                "X-Requested-With": "XMLHttpRequest",
                "headers" :
                    {
                        "mobileUrlTarget" : mobileUrlTarget
                    },
            };
            $http.post(url, data, config)
                .then(successFunc)
                .catch(failureFunc);
        }

    }

})();