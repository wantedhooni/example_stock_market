package com.revy.stock_market;

import com.opencsv.CSVReader;
import com.revy.stock_market.usecase.GenerateDataUseCaseV1;
import com.revy.stock_market.usecase.dto.StockCsvRecordDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class StockMarketApplication implements CommandLineRunner {

    @Autowired
    private GenerateDataUseCaseV1 generateDataUseCase;


    public static void main(String[] args) {
        SpringApplication.run(StockMarketApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //InputStream
        InputStream inputStream = this.getClass().getClassLoader().getResource("data/SampleData.csv").openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        CSVReader csvReader = new CSVReader(bufferedReader);
        csvReader.skip(1);

        String[] record = null;
        List<StockCsvRecordDto> saveList = new ArrayList<>();
        while ((record = csvReader.readNext()) != null) {
            StockCsvRecordDto stockRecordDto = StockCsvRecordDto.builder()
                    .code(record[1])
                    .name(record[2])
                    .price(new BigDecimal(record[3]))
                    .build();
            saveList.add(stockRecordDto);
        }
        // Stock 마스터 정보
        generateDataUseCase.initData(saveList);
        // 일별 Stock 정보
        generateDataUseCase.initDailyData(saveList);
        // 일별 Stock 정보 Random 변경
        generateDataUseCase.modifyRandomStockData();
    }

}


