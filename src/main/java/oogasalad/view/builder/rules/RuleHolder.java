package oogasalad.view.builder.rules;

import java.util.ArrayList;
import java.util.List;

public class RuleHolder implements RuleHolderInterface, MutableRuleHolder{

  List<BuilderRule> myRules;

  public RuleHolder(){
    myRules = new ArrayList<>();
  }

  @Override
  public List<BuilderRule> getRules() {
    return myRules;
  }

  @Override
  public void addRule(BuilderRule rule) {
    myRules.add(rule);
  }
}
