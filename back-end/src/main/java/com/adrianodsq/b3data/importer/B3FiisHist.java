package com.adrianodsq.b3data.importer;

import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;

@ToString
@Table(name = "B3_FIIS_HISTORICO")
@Entity
@FinancialData("FIIS")
@Component
public class B3FiisHist extends HistoricalFinancialData {

    //TICKER;PRECO;ULTIMO DIVIDENDO;DY;VALOR PATRIMONIAL COTA;P/VP;LIQUIDEZ MEDIA DIARIA;PERCENTUAL EM CAIXA;CAGR DIVIDENDOS 3 ANOS; CAGR VALOR CORA 3 ANOS;PATRIMONIO;N COTISTAS;GESTAO; N COTAS
    //VIFI11;7,03;0,060000;10,87;9,02;0,78;103.042,17;6,27;;;72.998.190,19;5.659,00;Ativa;8.089.190,00

    @Column(name = "ID_FIIS_HIST")
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

    @FinancialData("ULTIMO DIVIDENDO")
    @Column(name = "ultimo_dividendo")
    private Float ultimoDividendo;

    @FinancialData("DY")
    @Column(name = "dividend_yield")
    private Float dividendYield;

    @FinancialData("VALOR PATRIMONIAL COTA")
    @Column(name = "valor_patrimonial")
    private Float valorPatrimonial;

    @FinancialData("P/VP")
    @Column(name = "preco_sobre_valor_patrimonial")
    private Float precoValorPatrimonial;

    @FinancialData("LIQUIDEZ MEDIA DIARIA")
    @Column(name = "liquidez_media_diaria")
    private Float liquidezMediaDiaria;

    @FinancialData("PERCENTUAL EM CAIXA")
    @Column(name = "perc_em_caixa")
    private Float percentualEmCaixa;

    @FinancialData("CAGR DIVIDENDOS 3 ANOS")
    @Column(name = "cagr_dividendos")
    private Float cagrDividendos;

    @FinancialData("CAGR VALOR CORA 3 ANOS") //header tem typo
    @Column(name = "cagr_cota")
    private Float cagrCota;
    //PATRIMONIO;N COTISTAS;GESTAO; N COTAS
    @FinancialData("PATRIMONIO")
    @Column(name = "patrimonio")
    private Float patrimonio;

    @FinancialData("N COTISTAS")
    @Column(name = "num_cotistas")
    private Float cotistas;

    @FinancialData("GESTAO")
    @Column(name = "gestao")
    private String gestao;

    @FinancialData("N COTAS")
    @Column(name = "cotas")
    private Float cotas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getInfoDate() {
        return infoDate;
    }

    public void setInfoDate(Date infoDate) {
        this.infoDate = infoDate;
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

    public Float getUltimoDividendo() {
        return ultimoDividendo;
    }

    public void setUltimoDividendo(Float ultimoDividendo) {
        this.ultimoDividendo = ultimoDividendo;
    }

    public Float getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(Float dividendYield) {
        this.dividendYield = dividendYield;
    }

    public Float getValorPatrimonial() {
        return valorPatrimonial;
    }

    public void setValorPatrimonial(Float valorPatrimonial) {
        this.valorPatrimonial = valorPatrimonial;
    }

    public Float getPrecoValorPatrimonial() {
        return precoValorPatrimonial;
    }

    public void setPrecoValorPatrimonial(Float precoValorPatrimonial) {
        this.precoValorPatrimonial = precoValorPatrimonial;
    }

    public Float getLiquidezMediaDiaria() {
        return liquidezMediaDiaria;
    }

    public void setLiquidezMediaDiaria(Float liquidezMediaDiaria) {
        this.liquidezMediaDiaria = liquidezMediaDiaria;
    }

    public Float getPercentualEmCaixa() {
        return percentualEmCaixa;
    }

    public void setPercentualEmCaixa(Float percentualEmCaixa) {
        this.percentualEmCaixa = percentualEmCaixa;
    }

    public Float getCagrDividendos() {
        return cagrDividendos;
    }

    public void setCagrDividendos(Float cagrDividendos) {
        this.cagrDividendos = cagrDividendos;
    }

    public Float getCagrCota() {
        return cagrCota;
    }

    public void setCagrCota(Float cagrCota) {
        this.cagrCota = cagrCota;
    }

    public Float getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(Float patrimonio) {
        this.patrimonio = patrimonio;
    }

    public Float getCotistas() {
        return cotistas;
    }

    public void setCotistas(Float cotistas) {
        this.cotistas = cotistas;
    }

    public String getGestao() {
        return gestao;
    }

    public void setGestao(String gestao) {
        this.gestao = gestao;
    }

    public Float getCotas() {
        return cotas;
    }

    public void setCotas(Float cotas) {
        this.cotas = cotas;
    }
}
