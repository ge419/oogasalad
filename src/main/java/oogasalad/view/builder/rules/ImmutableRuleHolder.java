package oogasalad.view.builder.rules;

import java.util.ArrayList;
import java.util.List;

public class ImmutableRuleHolder implements RuleHolderInterface{

  List<BuilderRule> myRules;
  public ImmutableRuleHolder(RuleHolderInterface ruleHolder){
    myRules = new ArrayList<>();
    myRules = ruleHolder.getRules();
  }

  @Override
  public List<BuilderRule> getRules() {
    return myRules;
  }
}
