package com.lizo.busflow.routing.impl;

import com.lizo.busflow.context.BusContext;
import com.lizo.busflow.pattern.PatternFactory;
import com.lizo.busflow.pattern.PatternType;
import com.lizo.busflow.pattern.match.PatternMatch;

/**
 * Created by lizhou on 2017/4/7/007.
 */
public class SimpleRoutingCondition extends AbstractRoutingCondition {
    private final String defaultMatch = "@default";
    private PatternType pattern;
    private String condition;

    public boolean matched(BusContext busContext) {
        String routingKey = busContext.getRoutingKey();
        PatternMatch patternMatch = PatternFactory.getPatternMatch(pattern);
        return patternMatch.isMatched(routingKey, condition);
    }

    public boolean isDefaultMatch() {
        return defaultMatch.equals(condition);
    }

    public PatternType getPattern() {
        return pattern;
    }

    public void setPattern(PatternType pattern) {
        this.pattern = pattern;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }


    @Override
    public String toString() {
        return "SimpleRoutingCondition{" +
                "pattern=" + pattern +
                ", condition='" + condition + '\'' +
                '}';
    }
}
