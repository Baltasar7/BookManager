package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.mybatis.UserMapper;

@Transactional
@Service
public class UserService {

//    @Autowired
//    @Qualifier("UserDaoJdbcImpl")
//    UserDao dao;

    @Autowired
    UserMapper dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean insert(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        int rowNumber = dao.insertOne(user);
        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public int count() {
        return dao.count();
    }

    public List<User> selectAll() {
        return dao.selectAll();
    }

    public User selectOne(String userId) {
        return dao.selectOne(userId);
    }

    public boolean updateOne(User user) {
        boolean result = false;

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        int rowNumber = dao.updateOne(user);

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public boolean deleteOne(String userId) {
        int rowNumber = dao.deleteOne(userId);
        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public void userCsvOut() throws DataAccessException {
        dao.userCsvOut();
    }

    public byte[] getFile(String fileName) throws IOException {
        FileSystem fs = FileSystems.getDefault();
        Path p = fs.getPath(fileName);
        byte[] bytes = Files.readAllBytes(p);

        return bytes;
    }
}
