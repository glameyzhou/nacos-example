package org.glamey.tools.collections;

import com.google.common.collect.FluentIterable;
import lombok.Data;

import java.util.List;

/**
 * @date 2019.07.09.11. yang.zhou
 */
public class Main {

    private static final int BATCH_COUNT = 5000;

    public static void main(String[] args) {

        UserDao userDao = new UserDao();

        FluentIterable<User> userList = Iterables2.flattenSplit(new IDLimitQueryTemplate<User>() {
            @Override
            public List<User> query(long startId, int limit) {
                // select * from table where action_date = xxx and id > xx limit xxx;
                return userDao.queryUser(startId, limit);
            }

            @Override
            public long maxId(List<User> result) {
                return result.get(0).getId();
            }

            @Override
            public long startId() {

                //select id from table where action_date = xxx order by id asc;

                return userDao.getMinId();
            }
        }, BATCH_COUNT);

    }


    static class UserDao {

        public List<User> queryUser(long startId, int batchCount) {
            return null;
        }

        public Long getMinId() {
            return new Long(1000);
        }
    }


    @Data
    static class User {
        private Long id;
        private String code;
        private String name;
    }
}
