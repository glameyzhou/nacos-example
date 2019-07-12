package org.glamey.tools.collections;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.FluentIterable;

import java.util.Iterator;
import java.util.List;

/**
 * @date 2019.07.09.11. yang.zhou
 */
public class Iterables2 {
    /**
     * 通过where id > startId limit batchCount来遍历数据
     * 遍历力度从List<T>平铺为T
     *
     * @param queryTemplate
     * @param limit
     * @param <T>
     * @return
     */
    public static <T> FluentIterable<T> flattenSplit(IDLimitQueryTemplate<T> queryTemplate, int limit) {
        return flatten(split(queryTemplate, limit));
    }


    /**
     * 通过limit startId, batchCount来遍历数据
     * 遍历力度从List<T>平铺为T
     *
     * @param queryTemplate
     * @param limit
     * @param <T>
     * @return
     */
    public static <T> FluentIterable<T> flattenSplit(StartLimitQueryTemplate<T> queryTemplate, int limit) {
        return flatten(split(queryTemplate, limit));
    }

    public static <T> FluentIterable<List<T>> split(final StartLimitQueryTemplate<T> queryTemplate, final int limit) {
        Preconditions.checkNotNull(queryTemplate);
        Preconditions.checkArgument(limit > 0);
        Iterable<List<T>> iterable = new Iterable<List<T>>() {
            @Override
            public Iterator<List<T>> iterator() {
                return new AbstractIterator<List<T>>() {
                    int start = 0;
                    boolean reachEnd = false;

                    @Override
                    protected List<T> computeNext() {
                        if (reachEnd) {
                            return endOfData();
                        }
                        List<T> result = queryTemplate.query(start, limit);
                        if (result.size() < limit) {
                            reachEnd = true;
                        }
                        if (result.isEmpty()) {
                            return endOfData();
                        } else {
                            start += limit;
                            return result;
                        }
                    }
                };
            }
        };
        return FluentIterable.from(iterable);
    }

    public static <T> FluentIterable<List<T>> split(final IDLimitQueryTemplate<T> queryTemplate, final int limit) {
        Preconditions.checkArgument(limit > 0);
        Iterable<List<T>> iterable = new Iterable<List<T>>() {
            @Override
            public Iterator<List<T>> iterator() {
                return new AbstractIterator<List<T>>() {
                    // sql书写方式：where id > startId, 故需要将startId做减一处理，避免少取数据
                    long tempId = queryTemplate.startId();
                    long startId = tempId > 0 ? tempId - 1 : tempId;
                    boolean reachEnd = false;

                    @Override
                    protected List<T> computeNext() {
                        if (reachEnd) {
                            return endOfData();
                        }
                        List<T> result = queryTemplate.query(startId, limit);
                        if (result.isEmpty()) {
                            return endOfData();
                        }
                        if (limit > result.size()) {
                            reachEnd = true;
                        } else {
                            startId = queryTemplate.maxId(result);
                        }
                        return result;
                    }
                };
            }
        };
        return FluentIterable.from(iterable);
    }

    public static <T> FluentIterable<T> flatten(final Iterable<? extends Iterable<T>> iterables) {
        Preconditions.checkNotNull(iterables);
        return flatten(iterables.iterator());
    }

    public static <T> FluentIterable<T> flatten(final Iterator<? extends Iterable<T>> iterators) {
        Preconditions.checkNotNull(iterators);
        Iterable<T> iterable = new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new AbstractIterator<T>() {
                    Iterator<? extends Iterable<T>> outerIterator = iterators;
                    Iterator<T> innerIterator = null;

                    @Override
                    protected T computeNext() {
                        if (innerIterator == null) {
                            if (!outerIterator.hasNext()) {
                                return endOfData();
                            }
                            innerIterator = outerIterator.next().iterator();
                        }
                        while (innerIterator.hasNext()) {
                            return innerIterator.next();
                        }
                        innerIterator = null;
                        return computeNext();
                    }
                };
            }
        };
        return FluentIterable.from(iterable);
    }
}
