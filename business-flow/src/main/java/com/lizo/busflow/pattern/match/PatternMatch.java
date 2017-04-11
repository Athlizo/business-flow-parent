package com.lizo.busflow.pattern.match;

/**
 * pattern的匹配方式，
 * 每个方式应该是不保存任何状态。以保证线程安全
 *
 * 策略模式
 * 比较模式，现在只支持String匹配{@link com.lizo.busflow.pattern.match.impl.StringPatternMatch}
 * 和正则表达式模式{@link com.lizo.busflow.pattern.match.impl.RegexPatternMatch}
 *
 * Created by lizhou on 2017/3/14/014.
 */
public interface PatternMatch {
    boolean isMatched(String pattern, String value);
}
