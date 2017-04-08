package com.lizo.busflow.pattern.match;

/**
 * pattern的匹配方式，
 * 每个方式应该是不保存任何状态。以保证线程安全
 *
 * 策略模式
 * Created by lizhou on 2017/3/14/014.
 */
public interface PatternMatch {
    boolean isMatched(String pattern, String value);
}
