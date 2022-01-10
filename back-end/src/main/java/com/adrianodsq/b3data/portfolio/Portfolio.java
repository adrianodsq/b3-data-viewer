package com.adrianodsq.b3data.portfolio;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Table(name = "PORTFOLIO")
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Portfolio {

    private static final String DEFAULT_SEPARATOR = ";";

    @Column(name = "ID_PORTFOLIO")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    // WEGE3;B3SA3;
    @Column(name = "tickers")
    private String tickers = "";

    @Transient
    @JsonIgnore
    private List<String> tickerList;

    public Portfolio (String name){
        this.name = name;
    }

    public void addTicker(String ticker){
        if(StringUtils.isNotBlank(ticker) && !tickers.contains(ticker)){
            tickers += ticker + DEFAULT_SEPARATOR;
            splitElements();
        }

    }

    public void removeTicker(String ticker){
        splitElements();
        if(tickerList.contains(ticker)){
            tickerList.remove(ticker);
            String newTickers = "";
            for(String str : tickerList){
                newTickers +=  ticker;
            }
        }
    }

    public void splitElements(){
        if(tickerList == null){
            tickerList = new ArrayList<String>();
        }
        tickerList.clear();
        String[] elems = tickers.split(";");
        for(int i = 0; i < elems.length; i++){
            tickerList.add(elems[i]);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTickers() {
        return tickers;
    }

    public void setTickers(String tickers) {
        this.tickers = tickers;
    }

    @Transient
    public List<String> getTickerList() {
        return tickerList;
    }

    public void setTickerList(List<String> tickerList) {
        this.tickerList = tickerList;
        tickers = tickerList.stream().map(Object::toString).collect(Collectors.joining(DEFAULT_SEPARATOR)) + DEFAULT_SEPARATOR;
    }

    //Concatenar lista com separador
}
