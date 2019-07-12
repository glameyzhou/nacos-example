package org.glamey.tools.collections;

import java.util.List;

/**
 * 类似mysql中的 where id > startId limit batchCount
 * @date 2019.07.09.11. yang.zhou
 */
public interface IDLimitQueryTemplate<T> {

    /**
     * 返回大于startId的limit条数据，当返回的条数小于limit时，认为没有更多数据需要获取
     *
     * @param startId
     * @param limit
     * @return
     */
    List<T> query(long startId, int limit);

    /**
     * 通过List<T>计算最大的id，用于下一次查询
     *
     * @param result
     * @return
     */
    long maxId(List<T> result);

    /**
     * 用于过滤的起始id
     * <p>
     * 实现类需要做为空的判断，如果为空，务必赋值为0
     *
     * @return
     */
    long startId();
}
