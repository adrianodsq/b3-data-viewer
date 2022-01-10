package com.adrianodsq.b3data;

import com.adrianodsq.b3data.importer.StatusInvestLayoutParser;
import org.junit.jupiter.api.Test;

public class BlaTest {

    @Test
    public void bla(){
        StatusInvestLayoutParser statusInvestLayoutParser = new StatusInvestLayoutParser();

        String filename = "";

        statusInvestLayoutParser.importDataFromFile(filename);

    }
}
