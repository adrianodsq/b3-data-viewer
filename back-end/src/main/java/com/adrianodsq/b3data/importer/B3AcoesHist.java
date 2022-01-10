package com.adrianodsq.b3data.importer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.ToString;

import java.util.Date;

@ToString
@Table(name = "B3_ACOES_HISTORICO")
@Entity
public class B3AcoesHist {

    @Column(name = "ID_ACOES_HIST")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DAT_INFO")
    private Date infoDate;

    @FinancialData("TICKER")
    @Column(name = "ticker")
    private String ticker;

    @FinancialData("PRECO")
    @Column(name = "preco")
    private Float preco;

    @FinancialData("DY")
    @Column(name = "dividend_yield")
    private Float dividendYield;

    @FinancialData("P/L")
    @Column(name = "preco_sobre_lucro")
    private Float precoLucro;

    @FinancialData("P/VP")
    @Column(name = "preco_sobre_valor_patrimonial")
    private Float precoValorPatrimonial;

    @FinancialData("LPA")
    @Column(name = "lucro_por_acao")
    private Float lucroPorAcao;

    @FinancialData("VPA")
    @Column(name = "valor_por_acao")
    private Float valorPorAcao;

    @FinancialData("ROIC")
    @Column(name = "roic")
    private Float returnOverInvestedCapital;

    @FinancialData("ROA")
    @Column(name = "roa")
    private Float returnOnAssets;

    @FinancialData("ROE")
    @Column(name = "roe")
    private Float returnOnEquity;

    @FinancialData("PSR")
    @Column(name = "psr")
    private Float priceSalesRatio;

    @FinancialData("CAGR LUCROS 5 ANOS")
    @Column(name = "cagr_lucro")
    private Float cagrLucros;

    @FinancialData("CAGR RECEITAS 5 ANOS")
    @Column(name = "cagr_receita")
    private Float cagrReceita;

    @FinancialData("PASSIVOS / ATIVOS")
    @Column(name = "passivos_sobre_ativos")
    private Float passivosSobreAtivos;

    @FinancialData("PATRIMONIO / ATIVOS")
    @Column(name = "patrimonio_sobre_ativos")
    private Float patrimonioSobreAtivos;

    @FinancialData("P. AT CIR. LIQ.")
    @Column(name = "preco_sobre_atv_circulante")
    private Float precoAtivoCirculanteLiquido;

    @FinancialData("P/CAP. GIRO")
    @Column(name = "preco_sobre_capital_giro")
    private Float precoCapitalGiro;

    @FinancialData("DIV. LIQ. / PATRI.")
    @Column(name = "divida_liq_sobre_patrimonio")
    private Float dividaLiquidaPatrimonio;

    @FinancialData("DIVIDA LIQUIDA / EBIT")
    @Column(name = "divida_liq_sobre_ebit")
    private Float dividaLiquidaEbit;

    @FinancialData("EV/EBIT")
    @Column(name = "enterprise_value_sobre_ebit")
    private Float enterpriseValueEbit;

    @FinancialData("P/EBIT")
    @Column(name = "preco_sobre_ebit")
    private Float precoEbit;

    @FinancialData("P/ATIVOS")
    @Column(name = "preco_sobre_ativos")
    private Float precoAtivos;

    @FinancialData("MARGEM BRUTA")
    @Column(name = "margem_bruta")
    private Float margemBruta;

    @FinancialData("MARGEM EBIT")
    @Column(name = "margem_ebit")
    private Float margemEbit;

    @FinancialData("MARG. LIQUIDA")
    @Column(name = "margem_liquida")
    private Float margemLiquida;

    @FinancialData("LIQUIDEZ MEDIA DIARIA")
    @Column(name = "liquidez_media_diaria")
    private Float liquidezMediaDiria;

    @FinancialData("LIQ. CORRENTE")
    @Column(name = "liquidez_corrente")
    private Float liquidezCorrente;

    @FinancialData("GIRO ATIVOS")
    @Column(name = "giro_ativos")
    private Float giroAtivos;

    @FinancialData("PEG RATIO")
    @Column(name = "PEG_RATIO")
    private Float pegRatio;

    @FinancialData("VALOR DE MERCADO")
    @Column(name = "VALOR_DE_MERCADO")
    private Float valorDeMercado;

    public Date getInfoDate() {
        return infoDate;
    }

    public void setInfoDate(Date infoDate) {
        this.infoDate = infoDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public Float getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(Float dividendYield) {
        this.dividendYield = dividendYield;
    }

    public Float getPrecoLucro() {
        return precoLucro;
    }

    public void setPrecoLucro(Float precoLucro) {
        this.precoLucro = precoLucro;
    }

    public Float getPrecoValorPatrimonial() {
        return precoValorPatrimonial;
    }

    public void setPrecoValorPatrimonial(Float precoValorPatrimonial) {
        this.precoValorPatrimonial = precoValorPatrimonial;
    }

    public Float getLucroPorAcao() {
        return lucroPorAcao;
    }

    public void setLucroPorAcao(Float lucroPorAcao) {
        this.lucroPorAcao = lucroPorAcao;
    }

    public Float getValorPorAcao() {
        return valorPorAcao;
    }

    public void setValorPorAcao(Float valorPorAcao) {
        this.valorPorAcao = valorPorAcao;
    }

    public Float getReturnOverInvestedCapital() {
        return returnOverInvestedCapital;
    }

    public void setReturnOverInvestedCapital(Float returnOverInvestedCapital) {
        this.returnOverInvestedCapital = returnOverInvestedCapital;
    }

    public Float getReturnOnAssets() {
        return returnOnAssets;
    }

    public void setReturnOnAssets(Float returnOnAssets) {
        this.returnOnAssets = returnOnAssets;
    }

    public Float getReturnOnEquity() {
        return returnOnEquity;
    }

    public void setReturnOnEquity(Float returnOnEquity) {
        this.returnOnEquity = returnOnEquity;
    }

    public Float getPriceSalesRatio() {
        return priceSalesRatio;
    }

    public void setPriceSalesRatio(Float priceSalesRatio) {
        this.priceSalesRatio = priceSalesRatio;
    }

    public Float getCagrLucros() {
        return cagrLucros;
    }

    public void setCagrLucros(Float cagrLucros) {
        this.cagrLucros = cagrLucros;
    }

    public Float getCagrReceita() {
        return cagrReceita;
    }

    public void setCagrReceita(Float cagrReceita) {
        this.cagrReceita = cagrReceita;
    }

    public Float getPassivosSobreAtivos() {
        return passivosSobreAtivos;
    }

    public void setPassivosSobreAtivos(Float passivosSobreAtivos) {
        this.passivosSobreAtivos = passivosSobreAtivos;
    }

    public Float getPatrimonioSobreAtivos() {
        return patrimonioSobreAtivos;
    }

    public void setPatrimonioSobreAtivos(Float patrimonioSobreAtivos) {
        this.patrimonioSobreAtivos = patrimonioSobreAtivos;
    }

    public Float getPrecoAtivoCirculanteLiquido() {
        return precoAtivoCirculanteLiquido;
    }

    public void setPrecoAtivoCirculanteLiquido(Float precoAtivoCirculanteLiquido) {
        this.precoAtivoCirculanteLiquido = precoAtivoCirculanteLiquido;
    }

    public Float getPrecoCapitalGiro() {
        return precoCapitalGiro;
    }

    public void setPrecoCapitalGiro(Float precoCapitalGiro) {
        this.precoCapitalGiro = precoCapitalGiro;
    }

    public Float getDividaLiquidaPatrimonio() {
        return dividaLiquidaPatrimonio;
    }

    public void setDividaLiquidaPatrimonio(Float dividaLiquidaPatrimonio) {
        this.dividaLiquidaPatrimonio = dividaLiquidaPatrimonio;
    }

    public Float getDividaLiquidaEbit() {
        return dividaLiquidaEbit;
    }

    public void setDividaLiquidaEbit(Float dividaLiquidaEbit) {
        this.dividaLiquidaEbit = dividaLiquidaEbit;
    }

    public Float getEnterpriseValueEbit() {
        return enterpriseValueEbit;
    }

    public void setEnterpriseValueEbit(Float enterpriseValueEbit) {
        this.enterpriseValueEbit = enterpriseValueEbit;
    }

    public Float getPrecoEbit() {
        return precoEbit;
    }

    public void setPrecoEbit(Float precoEbit) {
        this.precoEbit = precoEbit;
    }

    public Float getPrecoAtivos() {
        return precoAtivos;
    }

    public void setPrecoAtivos(Float precoAtivos) {
        this.precoAtivos = precoAtivos;
    }

    public Float getMargemBruta() {
        return margemBruta;
    }

    public void setMargemBruta(Float margemBruta) {
        this.margemBruta = margemBruta;
    }

    public Float getMargemEbit() {
        return margemEbit;
    }

    public void setMargemEbit(Float margemEbit) {
        this.margemEbit = margemEbit;
    }

    public Float getMargemLiquida() {
        return margemLiquida;
    }

    public void setMargemLiquida(Float margemLiquida) {
        this.margemLiquida = margemLiquida;
    }

    public Float getLiquidezMediaDiria() {
        return liquidezMediaDiria;
    }

    public void setLiquidezMediaDiria(Float liquidezMediaDiria) {
        this.liquidezMediaDiria = liquidezMediaDiria;
    }

    public Float getLiquidezCorrente() {
        return liquidezCorrente;
    }

    public void setLiquidezCorrente(Float liquidezCorrente) {
        this.liquidezCorrente = liquidezCorrente;
    }

    public Float getGiroAtivos() {
        return giroAtivos;
    }

    public void setGiroAtivos(Float giroAtivos) {
        this.giroAtivos = giroAtivos;
    }

    public Float getPegRatio() {
        return pegRatio;
    }

    public void setPegRatio(Float pegRatio) {
        this.pegRatio = pegRatio;
    }

    public Float getValorDeMercado() {
        return valorDeMercado;
    }

    public void setValorDeMercado(Float valorDeMercado) {
        this.valorDeMercado = valorDeMercado;
    }
}
