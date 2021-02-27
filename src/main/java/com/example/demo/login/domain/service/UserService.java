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

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Transactional
@Service
public class UserService {

    @Autowired
    @Qualifier("UserDaoNamedJdbcImpl")
    UserDao dao;

    /**
     * insert用メソッド.
     */
    public boolean insert(User user) {
        int rowNumber = dao.insertOne(user);
        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    /**
     * カウント用メソッド.
     */
    public int count() {
        return dao.count();
    }

    /**
     * 全件取得用メソッド.
     */
    public List<User> selectMany() {
        return dao.selectMany();
    }

    /**
     * １件取得用メソッド.
     */
    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    /**
     * １件更新用メソッド.
     */
    public boolean updateOne(User user) {
        boolean result = false;
        int rowNumber = dao.updateOne(user);

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    /**
     * １件削除用メソッド.
     */
    public boolean deleteOne(String userId) {
        int rowNumber = dao.deleteOne(userId);
        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    //ユーザー一覧をCSV出力する.
    /**
     * @throws DataAccessException
     */
    public void userCsvOut() throws DataAccessException {
        dao.userCsvOut();
    }

    /**
     * サーバーに保存されているファイルを取得して、byte配列に変換する.
     */
    public byte[] getFile(String fileName) throws IOException {
        FileSystem fs = FileSystems.getDefault();
        Path p = fs.getPath(fileName);
        byte[] bytes = Files.readAllBytes(p);

        return bytes;
    }
}
