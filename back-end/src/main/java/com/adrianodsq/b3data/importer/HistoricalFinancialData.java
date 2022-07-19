package com.adrianodsq.b3data.importer;

import java.io.Serializable;
import java.util.Date;

public abstract class HistoricalFinancialData implements Serializable {

    public abstract Date getInfoDate();

    public abstract void setInfoDate(Date infoDate);

    public abstract String getTicker();

    public abstract void setTicker(String ticker);
}
