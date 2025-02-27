package org.com.Utils;

import org.com.Constants.CommonConstants;
import org.com.Models.Continent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperUtil {
    public static HashMap<String, Integer> constructAttributeHashMap(String[] p_attributeName, Integer[] p_attributeValue, Integer p_attributesCount)
    {
        HashMap<String, Integer> l_hashMap = new HashMap<>();
        for (int l_i = 0; l_i<p_attributesCount; l_i++)
        {
            l_hashMap.put(p_attributeName[l_i], p_attributeValue[l_i]);
        }
        return l_hashMap;
    }

    public static String getContinentById(Set<Continent> p_continents, int p_id) {
        for (Continent l_continent : p_continents) {
            if (l_continent.getId() == p_id) {
                return l_continent.getName();
            }
        }
        return null;
    }
}
