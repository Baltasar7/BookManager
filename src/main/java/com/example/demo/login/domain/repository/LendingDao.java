package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.Lending;
import com.example.demo.login.domain.model.LendingView;

public interface LendingDao {
    public int count() throws DataAccessException;
    public int insertOne(Lending lending) throws DataAccessException;
    public Lending selectOne(Integer lendingId) throws DataAccessException;
//    public List<Lending> selectAll() throws DataAccessException;
    public List<LendingView> selectAll() throws DataAccessException;
    public int updateOne(Lending lending) throws DataAccessException;
    public int deleteOne(Integer lendingId) throws DataAccessException;
    public void lendingCsvOut() throws DataAccessException;
}
