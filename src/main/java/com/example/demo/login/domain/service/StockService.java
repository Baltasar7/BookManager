package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Stock;
import com.example.demo.login.domain.repository.mybatis.StockMapper;

@Transactional
@Service
public class StockService {
    @Autowired
    StockMapper mapper;
    @Autowired
    LendingService lendingService;

    public boolean insert(Stock stock) {
      if (mapper.insertOne(stock) <= 0) {
          return false;
      }
      return true;
    }

    public boolean insertListFromFile(List<Stock> stockList) {
      for (Stock stock: stockList) {
        if (mapper.withIdInsertOne(stock) <= 0) {
          return false;
        }
      }
      return true;
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

    public Page<Stock> findPageByStock(Pageable pageable, int total) {
    		return new PageImpl<>(
    				mapper.findPageByStock(pageable),
    				pageable,
    				total);
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
      if (mapper.deleteOne(stockId) <= 0) {
        return false;
      }
        return true;
    }

    public boolean deleteAll() {
      if (mapper.deleteAll() <= 0) {
          return false;
      }
      return true;
  }

    public int applyBook(int userId, int bookId) {
      List<Stock> restItems = mapper.selectRestItems(bookId);
      if (restItems.isEmpty()) {
        return -1;
      }
      Stock applyingItem = restItems.get(0);
      applyingItem.setState("applying");
      mapper.updateOne(applyingItem);
      lendingService.addLending(userId, applyingItem.getStockId());
      return applyingItem.getStockId();
    }

    public boolean applyLending(int stockId) {
      Stock stock = mapper.selectOne(stockId);
      if(!stock.getState().equals("applying")) {
        return false;
      }
      stock.setState("lending");
      mapper.updateOne(stock);
      return true;
    }

    public boolean resetApplyLending(int stockId) {
      Stock stock = mapper.selectOne(stockId);
      if(stock.getState() != "applying") {
        return false;
      }
      stock.setState("stock");
      mapper.updateOne(stock);
      return true;
    }

    public boolean updateLendable(int stockId) {
      Stock stock = mapper.selectOne(stockId);
      stock.setState("stock");
      mapper.updateOne(stock);
      return true;
    }

    public boolean updateLending(int stockId) {
      Stock stock = mapper.selectOne(stockId);
      stock.setState("lending");
      mapper.updateOne(stock);
      return true;
    }

    public boolean isStock(int stockId) {
      return mapper.selectOne(stockId).getState().equals("stock") ? true : false;
    }
}
