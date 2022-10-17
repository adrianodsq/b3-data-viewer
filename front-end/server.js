const SERVER_PORT = 8785
const B3_APP_URL = "http://localhost:8080"
// imports
const express = require('express');
const path = require('path');
// Pra fazer requests
const axios = require('axios');

// inits
var app = express();
app.use(express.json());
// Arquivos estaticos serao procurados na pasta public
app.use(express.static('public'));

// curl http://localhost:8785/stocks/nvda
// curl http://localhost:8785/acoes/cyre3
// curl http://localhost:8785/fiis/hglg11
app.get('/:financialType/:someTicker', function(req, res) {

    var financialType = req.params.financialType;
    var ticker = req.params.someTicker;
    
    console.log("M=getTicker step=start param=" + ticker);
    // Exemplo - http://localhost:8080/b3/acoes/cyre3
    var url = B3_APP_URL + "/b3/" + financialType + "/" + ticker.toUpperCase();
    
    axios.get(url)
    .then(resp => {
        const result = resp.data;
        console.log("M=getTicker step=end param=" + ticker);
        //console.log(result);
        res.send(result);
    })
    .catch(err => {
        console.log("M=getTicker step=error param=" + ticker + " error=" + err);
        // devolver um objeto mais elegante reportando o erro
        throw err;
    });

    
});

// curl http://localhost:8080/portolio/
app.get('/portfolio/', function(req, res) {
    console.log("M=getPortfolios step=start");

    var url = B3_APP_URL + "/portolio/";
    
    axios.get(url)
    .then(resp => {
        const result = resp.data;
        console.log("M=getPortfolios step=end");
        //console.log(result);
        res.send(result);
    })
    .catch(err => {
        console.log("M=getPortfolios step=error error=" + err);
        // devolver um objeto mais elegante reportando o erro
        throw err;
    });
    
});


app.post('/portfolio/:somePortfolio/', function(req, res) {
    var portfolio = req.params.somePortfolio;
    
    var tickerList = req.body.tickerList;
    console.log("M=updatePortfolio port=" + portfolio + " tickers=" + tickerList);

    var url = B3_APP_URL + "/portolio/edit/" + portfolio + "?tickers=" + tickerList
    console.log("url=" + url)
    axios.put(url, null)
    .then(resp => {
        console.log("M=updatePortfolio step=OK");
        res.send("OK")
    })
    .catch(err => {
        console.log("M=updatePortfolio step=error error=" + err);
        // devolver um objeto mais elegante reportando o erro
        throw err;
    });

   
});



// viewed at http://localhost:8785
app.get('/', function(req, res) {
    console.log("page=home")
    res.sendFile(path.join(__dirname + '/public/home.html'));
});

app.get('/fiis', function(req, res) {
    console.log("page=fiis")
    res.sendFile(path.join(__dirname + '/public/fiis.html'));
});

app.get('/stocks', function(req, res) {
    console.log("page=stocks")
    res.sendFile(path.join(__dirname + '/public/stocks.html'));
});

app.get('/reits', function(req, res) {
    console.log("page=reits")
    res.sendFile(path.join(__dirname + '/public/reits.html'));
});



app.listen(SERVER_PORT, ()=> {
    console.log("Server Started! Listening at: " + SERVER_PORT );
});


