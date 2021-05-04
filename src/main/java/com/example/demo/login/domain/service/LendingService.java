package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.Lending;
import com.example.demo.login.domain.model.LendingView;
import com.example.demo.login.domain.repository.LendingDao;

@Transactional
@Service
public class LendingService {

    @Autowired
    @Qualifier("LendingDaoNamedJdbcImpl")
    LendingDao dao;

    public boolean insert(Lending lending) {
        int rowNumber = dao.insertOne(lending);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public int count() {
        return dao.count();
    }

    public List<LendingView> selectAll() {
        return dao.selectAll();
    }

    public Lending selectOne(Integer lendingId) {
        return dao.selectOne(lendingId);
    }

    public boolean updateOne(Lending lending) {
        boolean result = false;
        int rowNumber = dao.updateOne(lending);
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public boolean deleteOne(Integer lendingId) {
        int rowNumber = dao.deleteOne(lendingId);
        boolean result = false;
        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public void lendingCsvOut() throws DataAccessException {
        dao.lendingCsvOut();
    }

    public byte[] getFile(String fileName) throws IOException {
        FileSystem fs = FileSystems.getDefault();
        Path p = fs.getPath(fileName);
        byte[] bytes = Files.readAllBytes(p);
        return bytes;
    }
}