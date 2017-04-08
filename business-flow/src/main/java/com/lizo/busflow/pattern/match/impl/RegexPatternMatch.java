package com.lizo.busflow.pattern.match.impl;

import com.lizo.busflow.pattern.match.PatternMatch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lizhou on 2017/3/14/014.
 */
public class RegexPatternMatch implements PatternMatch {
    public boolean isMatched(String pattern, String value) {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(value);
        return matcher.matches();
    }

}
