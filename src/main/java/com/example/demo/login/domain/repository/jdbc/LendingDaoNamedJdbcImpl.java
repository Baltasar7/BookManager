package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Lending;
import com.example.demo.login.domain.model.LendingView;
import com.example.demo.login.domain.repository.LendingDao;

@Repository("LendingDaoNamedJdbcImpl")
public class LendingDaoNamedJdbcImpl implements LendingDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM m_lending";
        SqlParameterSource params = new MapSqlParameterSource();
        return jdbc.queryForObject(sql, params, Integer.class);
    }

    @Override
    public int insertOne(Lending lending) {
        String sql = "INSERT INTO m_lending("
                +   " bookId,"
                +   " userId)"
                + " VALUES("
                +   " :userId,"
                +   " :bookId)";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("bookId", lending.getBookId())
                .addValue("userId", lending.getUserId());
        return jdbc.update(sql, params);
    }

    @Override
    public Lending selectOne(Integer lendingId) {
        String sql = "SELECT * FROM m_lending WHERE lending_id = :lendingId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("lendingId", lendingId);
        Map<String, Object> map = jdbc.queryForMap(sql, params);

        Lending lending = new Lending();
        lending.setLendingId((Integer)map.get("lending_id"));
        lending.setBookId((Integer)map.get("lending_id"));
        lending.setUserId((String)map.get("user_id"));

        return lending;
    }

    @Override
    public List<LendingView> selectAll() {
        String sql = "SELECT lending_id,title,user_name "
        		+ "FROM m_lending, m_book, m_user "
        		+ "WHERE m_lending.book_id = m_book.book_id "
        		+ "AND m_lending.user_id = m_user.user_id";
        SqlParameterSource params = new MapSqlParameterSource();
        List<Map<String, Object>> getList = jdbc.queryForList(sql, params);
        List<LendingView> lendingList = new ArrayList<>();

        for(Map<String, Object> map: getList) {
        	LendingView lendingView = new LendingView();
        	lendingView.setLendingId((Integer)map.get("lending_id"));
        	lendingView.setTitle((String)map.get("title"));
        	lendingView.setUserName((String)map.get("user_name"));
          lendingList.add(lendingView);
        }
        return lendingList;
    }

    @Override
    public int updateOne(Lending lending) {
        String sql = "UPDATE m_lending"
                + " SET"
                +   " book_Id = :bookId,"
                +   " user_Id = :userId,"
                +   " WHERE lending_id = :lendingId";
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("lendingId", lending.getBookId())
            .addValue("bookId", lending.getBookId())
            .addValue("userId", lending.getUserId());
        return jdbc.update(sql, params);
    }

    @Override
    public int deleteOne(Integer lendingId) {
        String sql = "DELETE FROM m_lending WHERE lending_id = :lendingId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("lendingId", lendingId);
        int rowNumber = jdbc.update(sql, params);
        return rowNumber;
    }

    @Override
    public void lendingCsvOut() {
        String sql = "SELECT * FROM m_lending";
        LendingRowCallbackHandler handler = new LendingRowCallbackHandler();
        jdbc.query(sql, handler);
    }
}