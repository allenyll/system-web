package com.sw.common.util;

import java.util.Map;

/**
 * @Description:
 * @Author:       allenyll
 * @Date:         2019/1/11 10:30 AM
 * @Version:      1.0
 */
public class MapUtil {

    public static void addMapKey(Map map, String key) {
        if (!map.containsKey(key)) {
            map.put(key, "");
        } else {
            map.put(key, "" + getMapValue(map, key));
        }
    }

    public static void addMapKey(Map map, String key, String value) {
        if (!map.containsKey(key)) {
            map.put(key, value);
        }
    }

    public static String getMapValue(Map map, String key) {
        if (null != map && !map.isEmpty() && null != map.get(key)
                && !"null".equals(map.get(key))) {
            return map.get(key).toString();
        }
        return "";
    }

    public static String getMapValue(Map map, String key, String def) {
        if (null != map && !map.isEmpty() && null != map.get(key)
                && !"null".equals(map.get(key))) {
            return map.get(key).toString();
        }
        return def;
    }

}