package com.nxwj.toolcommon.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Author: ty
 * @Description: 集合相关操作
 * @Date 2024-04-16 11:58
 */
public class CollectionUtils {

    /**
     *
     * @param objs
     * @param function
     * @param collectors
     * @return
     * @param <R>
     * @param <T>
     * @param <A>
     */
    public static <R, T, A> A properties(Collection<T> objs, Function<T, R> function, Collector<R, ?, A> collectors) {
        return objs.stream().map(function).collect(collectors);
    }

    public static <R, T> Set<R> properties(Collection<T> objs, Function<T, R> function) {
        return (Set)(org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? Sets.newHashSet() : (Set)properties(objs, function, Collectors.toSet()));
    }

    /**
     *
     * @param objs
     * @param function
     * @return
     * @param <R>
     * @param <T>
     */
    public static <R, T> Map<R, T> convert2Map(Collection<T> objs, Function<T, R> function) {
        return (Map)(org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? Maps.newHashMap() : (Map)objs.stream().collect(Collectors.toMap(function, Function.identity(), (a, b) -> {
            return a;
        })));
    }

    /**
     *
     * @param objs 集合
     * @param keyMapper
     * @param valueMapper
     * @return
     * @param <T>
     * @param <K>
     * @param <V>
     */
    public static <T, K, V> Map<K, V> convert2Map(Collection<T> objs, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return (Map)(org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? Maps.newHashMap() : (Map)objs.stream().filter((e) -> {
            return valueMapper.apply(e) != null && keyMapper.apply(e) != null;
        }).collect(Collectors.toMap(keyMapper, valueMapper, (a, b) -> {
            return a;
        })));
    }

    public static <R, T> Map<R, List<T>> group2Map(Collection<T> objs, Function<T, R> function) {
        return (Map)(org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? Maps.newHashMap() : (Map)objs.stream().collect(Collectors.groupingBy(function)));
    }

    public static <T, K, V> Map<K, Set<V>> group2Map(Collection<T> objs, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        return (Map)(org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? Maps.newHashMap() : (Map)objs.stream().filter((e) -> {
            return keyMapper.apply(e) != null && valueMapper.apply(e) != null;
        }).collect(Collectors.groupingBy(keyMapper, Collectors.collectingAndThen(Collectors.toList(), (e) -> {
            return (Set)e.stream().map(valueMapper).collect(Collectors.toSet());
        }))));
    }

    public static <T> List<List<T>> groupListByQuantity(List<T> list, int quantity) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        } else if (quantity <= 0) {
            throw new IllegalArgumentException("Wrong quantity.");
        } else {
            List<List<T>> wrapList = Lists.newArrayList();

            for(int count = 0; count < list.size(); count += quantity) {
                wrapList.add(Lists.newArrayList(list.subList(count, Math.min(count + quantity, list.size()))));
            }

            return wrapList;
        }
    }

    public static <R, T> List<R> map(Collection<T> objs, Function<T, R> function) {
        return (List)(org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? Lists.newArrayList() : (List)objs.stream().filter((e) -> {
            return function.apply(e) != null;
        }).map(function).collect(Collectors.toList()));
    }

    public static <R, T> R firstMap(List<T> objs, Function<T, R> function) {
        return org.apache.commons.collections4.CollectionUtils.isNotEmpty(objs) ? function.apply(objs.get(0)) : null;
    }

    public static <T> T first(List<T> objs) {
        return firstOrDefault(objs, null);
    }

    public static <T> T firstOrDefault(List<T> objs, T defaultVal) {
        return org.apache.commons.collections4.CollectionUtils.isNotEmpty(objs) ? objs.get(0) : defaultVal;
    }

    public static <T> BigDecimal reduce(Collection<T> objs, Function<T, BigDecimal> function) {
        return org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? BigDecimal.ZERO : (BigDecimal)objs.stream().filter((e) -> {
            return function.apply(e) != null;
        }).map(function).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <T> BigDecimal reduce(Collection<T> objs, Function<T, BigDecimal> function, Predicate<T> predicate) {
        return org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? BigDecimal.ZERO : (BigDecimal)objs.stream().filter((e) -> {
            return function.apply(e) != null && predicate.test(e);
        }).map(function).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static <T> List<T> filter(Collection<T> objs, Predicate<T> filter) {
        return (List)(org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? Lists.newArrayList() : (List)objs.stream().filter(filter).collect(Collectors.toList()));
    }

    public static <R, T> List<R> filterMap(Collection<T> objs, Predicate<T> filter, Function<T, R> function) {
        return (List)(org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? Lists.newArrayList() : (List)objs.stream().filter(filter).map(function).collect(Collectors.toList()));
    }

    public static <R, T> Map<R, T> filterConvert2Map(Collection<T> objs, Predicate<T> filter, Function<T, R> function) {
        return (Map)(org.apache.commons.collections4.CollectionUtils.isEmpty(objs) ? Maps.newHashMap() : (Map)objs.stream().filter(filter).collect(Collectors.toMap(function, Function.identity(), (a, b) -> {
            return a;
        })));
    }
}
