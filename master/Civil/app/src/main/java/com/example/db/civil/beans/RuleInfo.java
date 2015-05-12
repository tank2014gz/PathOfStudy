package com.example.db.civil.beans;

/**
 * Created by db on 4/11/15.
 */
public class RuleInfo {
    public String ruleUrl;
    public String ruleKind;
    public void setRuleUrl(String ruleUrl){
        this.ruleUrl=ruleUrl;
    }
    public String getRuleUrl(){
        return ruleUrl;
    }
    public void setRuleKind(String ruleKind){
        this.ruleKind=ruleKind;
    }
    public String getRuleKind(){
        return ruleKind;
    }
}
