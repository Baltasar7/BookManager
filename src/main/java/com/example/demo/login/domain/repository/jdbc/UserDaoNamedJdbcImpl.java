package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository("UserDaoNamedJdbcImpl")
public class UserDaoNamedJdbcImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Userテーブルの件数を取得.
    @Override
    public int count() {
        String sql = "SELECT COUNT(*) FROM m_user";
        SqlParameterSource params = new MapSqlParameterSource();
        return jdbc.queryForObject(sql, params, Integer.class);
    }

    //Userテーブルにデータを1件insert.
    @Override
    public int insertOne(User user) {
        String password = passwordEncoder.encode(user.getPassword());

        String sql = "INSERT INTO m_user(user_id,"
                +   " password,"
                +   " user_name,"
                +   " department,"
                +   " role)"
                + " VALUES(:userId,"
                +   " :password,"
                +   " :userName,"
                +   " :department,"
                +   " :role)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", user.getUserId())
                .addValue("password", password)
                .addValue("userName", user.getUserName())
                .addValue("department", user.getDepartment())
                .addValue("role", user.getRole());

        return jdbc.update(sql, params);
    }

    //Userテーブルのデータを１件取得
    @Override
    public User selectOne(String userId) {
        String sql = "SELECT * FROM m_user WHERE user_id = :userId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);
        Map<String, Object> map = jdbc.queryForMap(sql, params);

        User user = new User();
        user.setUserId((String)map.get("user_id"));    //ユーザーID
        user.setPassword((String)map.get("password"));  //パスワード
        user.setUserName((String)map.get("user_name")); //ユーザー名
        user.setDepartment((String)map.get("department")); //所属部署
        user.setRole((String)map.get("role")); //ロール

        return user;
    }

    //Userテーブルの全データを取得.
    @Override
    public List<User> selectMany() {
        String sql = "SELECT * FROM m_user";
        SqlParameterSource params = new MapSqlParameterSource();
        List<Map<String, Object>> getList = jdbc.queryForList(sql, params);
        List<User> userList = new ArrayList<>();

        for(Map<String, Object> map: getList) {

            User user = new User();
            user.setUserId((String)map.get("user_id"));    //ユーザーID
            user.setPassword((String)map.get("password"));  //パスワード
            user.setUserName((String)map.get("user_name")); //ユーザー名
            user.setDepartment((String)map.get("department")); //所属部署
            user.setRole((String)map.get("role")); //ロール

            userList.add(user);
        }

        return userList;
    }

    //Userテーブルを１件更新.
    @Override
    public int updateOne(User user) {
        String password = passwordEncoder.encode(user.getPassword());

        String sql = "UPDATE M_USER"
                + " SET"
                +   " password = :password,"
                +   " user_name = :userName,"
                +   " department = :department,"
                +   " WHERE user_id = :userId";

        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("userId", user.getUserId())
            .addValue("password", password)
            .addValue("userName", user.getUserName())
            .addValue("department", user.getDepartment());

        return jdbc.update(sql, params);
    }

    //Userテーブルを１件削除.
    @Override
    public int deleteOne(String userId) {
        String sql = "DELETE FROM m_user WHERE user_id = :userId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("userId", userId);
        int rowNumber = jdbc.update(sql, params);

        return rowNumber;
    }

    //SQL取得結果をサーバーにCSVで保存する
    @Override
    public void userCsvOut() {
        String sql = "SELECT * FROM m_user";
        UserRowCallbackHandler handler = new UserRowCallbackHandler();
        jdbc.query(sql, handler);
    }
}