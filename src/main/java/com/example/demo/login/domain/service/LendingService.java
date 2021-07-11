package com.example.demo.login.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Lending;
import com.example.demo.login.domain.model.LendingView;
import com.example.demo.login.domain.repository.mybatis.LendingMapper;

@Transactional
@Service
public class LendingService {
    @Autowired
    LendingMapper mapper;
    @Autowired
    StockService stockService;

    // TODO:ConfigureFile
    static final long LENDING_TERM_DAY = 28;

    public boolean insert(Lending lending) {
      if (mapper.insertOne(lending) <= 0) {
          return false;
      }
      return true;
    }

    public boolean insertListFromFile(List<Lending> lendingList) {
      for (Lending lending: lendingList) {
        if (mapper.withIdInsertOne(lending) <= 0) {
          return false;
        }
      }
      return true;
    }

    public int countAll() {
        return mapper.countAll();
    }

    public int countUser(Integer userId) {
      return mapper.countUser(userId);
    }

    public List<Lending> selectAll() {
      return mapper.selectAll();
  }

    public List<LendingView> selectAllLendingView() {
      return mapper.selectAllLendingView();
  }

    public Lending selectOne(Integer lendingId) {
        return mapper.selectOne(lendingId);
    }

    public List<LendingView> selectUser(Integer userId) {
      return mapper.selectUser(userId);
  }

    public boolean updateOne(Lending lending) {
        boolean result = false;
        int rowNumber = mapper.updateOne(lending);
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public boolean deleteOne(int lendingId) {
      if (mapper.deleteOne(lendingId) <= 0) {
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

    public boolean addLending(int userId, int stockId) {
      Lending lending = new Lending(String.valueOf(userId), stockId);
      mapper.insertOne(lending);
      return true;
    }

    public boolean resetApply(int lendingId) {
      int stockId = mapper.selectOne(lendingId).getStockId();
      if (stockService.selectOne(stockId).getState() != "applying") {
        return false;
      }
      if(!this.deleteOne(lendingId)) {
        return false;
      }
      if(!stockService.resetApplyLending(stockId)) {
        // TODO:想定外のロールバック
        throw new RuntimeException();
      }
      return true;
    }

    public boolean deleteLending(int lendingId) {
      int stockId = mapper.selectOne(lendingId).getStockId();
      if(!deleteOne(lendingId)) {
        return false;
      }
      if(!stockService.updateLendable(stockId)) {
        return false;
      }
      return true;
    }

    public int getStockId(int lendingId) {
      Lending lending = mapper.selectOne(lendingId);
      return lending.getStockId();
    }

    public void setLendingDate(int lendingId) {
      Lending lending = mapper.selectOne(lendingId);
      lending.setLendingDate(LocalDate.now());
      this.updateOne(lending);
    }

    public void setLimitDate(List<LendingView> lendingViewList) {
        for(LendingView lendingView: lendingViewList) {
          if(lendingView.getLendingDate() != null) {
            lendingView.setLimitDate(lendingView.getLendingDate().plusDays(LENDING_TERM_DAY));
          }
        }
    }
/*
    public void lendingCsvOut() throws DataAccessException {
        mapper.lendingCsvOut();
    }

    public byte[] getFile(String fileName) throws IOException {
        FileSystem fs = FileSystems.getDefault();
        Path p = fs.getPath(fileName);
        byte[] bytes = Files.readAllBytes(p);
        return bytes;
    }
*/
}
