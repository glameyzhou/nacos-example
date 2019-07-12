package org.glamey.tools.collections;

import java.util.List;

/**
 * 类似mysql中的limit start,batchCount
 *
 * @date 2019.07.09.11. yang.zhou
 */
public interface StartLimitQueryTemplate<T> {
    /**
     * 类似SQL的start, limit，访问从start开始的limit记录，返回小于limit条记录表示没有更多数据可以访问
     * <p>
     *
     * @param start
     * @param limit
     * @return
     */
    List<T> query(int start, int limit);
}
