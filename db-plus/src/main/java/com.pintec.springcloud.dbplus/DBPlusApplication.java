package com.pintec.springcloud.dbplus;


import com.pintec.springcloud.dbplus.db.mapper.UserMapper;
import com.pintec.springcloud.dbplus.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@SpringBootApplication
@MapperScan(value = "com.pintec.springcloud.dbplus.db.mapper")
public class DBPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DBPlusApplication.class, args);
    }


    @RestController
    public static class Controller {

        @Autowired
        private UserService userService;

        @GetMapping(value = "/user/list")
        public List<User> listUsers() {
            return userService.list();
        }


        /**
         * curl -XPOST 'http://www.glamey.org:9900/user' -d'{"id":100,"name":"demo","age":35,"email":"demo@glamey.org"}' -H "Content-type:application/json;charset=UTF-8"
         * @param user
         * @return
         */
        @PostMapping(value = "user")
        public User saveUser(@RequestBody User user) {
            log.info("user -> {}", user);
            return userService.save(user);
        }
    }


    @Service
    public static class UserService {
        @Autowired
        private UserMapper userMapper;

        public List<User> list() {
            return userMapper.selectList(null);
        }

        public User save(User user) {
            int insert = userMapper.insert(user);
            log.info("save user -> {}", insert);
            return user;
        }
    }
}
