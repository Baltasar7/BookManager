package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Stock;
import com.example.demo.login.domain.repository.mybatis.StockMapper;

@Transactional
@Service
public class StockService {
		@Autowired
		StockMapper mapper;

    public boolean insert(Stock stock) {
        int rowNumber = mapper.insertOne(stock);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public int count() {
        return mapper.count();
    }

    public int getStockCount(int bookId) {
      return mapper.getStockCount(bookId);
    }

    public int getRestCount(int bookId) {
      return mapper.getRestCount(bookId);
    }

    public List<Stock> selectAll() {
        return mapper.selectAll();
    }

    public Stock selectOne(int stockId) {
        return mapper.selectOne(stockId);
    }

    public boolean updateOne(Stock stock) {
        boolean result = false;
        int rowNumber = mapper.updateOne(stock);
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public boolean deleteOne(int stockId) {
        int rowNumber = mapper.deleteOne(stockId);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }
/*
    public void stockCsvOut() throws DataAccessException {
        mapper.stockCsvOut();
    }

    public byte[] getFile(String fileName) throws IOException {
        FileSystem fs = FileSystems.getDefault();
        Path p = fs.getPath(fileName);
        byte[] bytes = Files.readAllBytes(p);
        return bytes;
    }
*/
}
