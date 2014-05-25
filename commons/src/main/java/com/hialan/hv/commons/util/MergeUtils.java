package com.hialan.hv.commons.util;

import java.util.*;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 20/5/13
 *         Time: 10:45 AM
 */
public abstract class MergeUtils {

    public static <S extends Comparable, T> List<T> merge(List<S> sources, Merger<S, T> merger) {
        List<T> targets = new ArrayList<T>();
        if (sources != null && merger != null && sources.size() > 0) {
            //Sort
            Collections.sort(sources);

            //Init
            T target =merger.convert(sources.get(0));
            Map<String, T> resultMap = new TreeMap<String, T>();
            Map<String, Integer> indexMap = new HashMap<String, Integer>();

            String key;
            for (S source : sources) {
                key = merger.getKey(source);
                if (key == null) continue;
                boolean mergeEnable = merger.isMergeEnable(source, target);
                if (mergeEnable) {
                    if (indexMap.containsKey(key)) {
                        key += indexMap.get(key);
                        target = resultMap.get(key);
                    }
                    if (target != null) {
                        merger.merge(source, target);
                    }
                } else {
                    if (resultMap.containsKey(key)) {
                        int v = indexMap.get(key) != null ? (indexMap.get(key)) + 1 : 1;
                        indexMap.put(key, v);
                        key += v;
                    }
                    target = merger.convert(source);
                }
                resultMap.put(key, target);
            }

            //Result
            targets.addAll(resultMap.values());
        }

        return targets;
    }

}
