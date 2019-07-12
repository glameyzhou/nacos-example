package org.glamey.tools.compute;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * K 待比较的KEY
 * V 计算的结果对象
 * R 原始对象
 *
 * @date 2019.07.09.11. yang.zhou
 */

public abstract class AbstractComputeTemplate<K extends java.io.Serializable, V extends java.io.Serializable, R extends OriginRecord> {


    protected abstract void setComputeContext(ComputContext context);

    protected abstract ComputContext getComputeContext();

    public abstract Iterable<R> fetchData();

    public abstract void persist(Map<K, V> result);

    protected abstract K buildGroupKey(R r);


    protected abstract V updateValue(R r);

    protected abstract V initValue();

    public void compute(ComputContext context) {

        setComputeContext(context);

        Iterable<R> iterable = fetchData();
        if (Iterables.isEmpty(iterable)) {
            return;
        }

        Map<K, V> result = Maps.newHashMap();

        for (R r : iterable) {
            K k = buildGroupKey(r);
            V v = result.get(k);
            if (v == null) {
                v = initValue();
            } else {
                v = updateValue(r);
            }
            result.put(k, v);
        }

        persist(result);
    }

}
