package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.UserDetailsImpl;
import com.example.demo.login.domain.repository.mybatis.UserMapper;

@Transactional
@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
      User user = selectOne(userId);
      if(userId == null) {
        throw new UsernameNotFoundException(userId + " is not found");
      }
      return new UserDetailsImpl(user);
    }

    public boolean insert(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        if (mapper.insertOne(user) <= 0) {
            return false;
        }
        return true;
    }

    public boolean insertListFromFile(List<User> userList, String userId) {
      for (User user: userList) {
        if (user.getUserId().equals(userId)) continue;
        if (!this.insert(user)) return false;
      }
      return true;
    }

    public int count() {
        return mapper.count();
    }

    public List<User> selectAll() {
        return mapper.selectAll();
    }

    public User selectOne(String userId) {
        return mapper.selectOne(userId);
    }

    public boolean updateOne(User user) {
        boolean result = false;

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        int rowNumber = mapper.updateOne(user);

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    public boolean deleteOne(String userId) {
      if (mapper.deleteOne(userId) <= 0) {
        return false;
      }
        return true;
    }

    public boolean deleteAllWithoutCurrentUser(String userId) {
      List<User> userList = this.selectAll();
      for (User user: userList) {
        if (user.getUserId().equals(userId)) continue;
        if (mapper.deleteOne(user.getUserId()) <= 0) return false;
      }
      return true;
  }
}
