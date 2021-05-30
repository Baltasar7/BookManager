package com.example.demo.login.domain.service;

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

    public boolean insert(Lending lending) {
        int rowNumber = mapper.insertOne(lending);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public int countAll() {
        return mapper.countAll();
    }

    public int countUser(Integer userId) {
      return mapper.countUser(userId);
    }

    public List<LendingView> selectAll() {
        return mapper.selectAll();
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

    public boolean deleteOne(Integer lendingId) {
        int rowNumber = mapper.deleteOne(lendingId);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
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
