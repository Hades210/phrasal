package edu.stanford.nlp.mt.decoder.feat.sparse;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.mt.decoder.feat.RuleFeaturizer;
import edu.stanford.nlp.mt.decoder.feat.FeatureUtils;
import edu.stanford.nlp.mt.util.FeatureValue;
import edu.stanford.nlp.mt.util.Featurizable;
import edu.stanford.nlp.mt.util.IString;
import edu.stanford.nlp.mt.util.InputProperty;
import edu.stanford.nlp.util.Generics;

/**
 * The target dimension of the rule.
 * 
 * @author Spence Green
 *
 */
public class RuleTargetDimension implements RuleFeaturizer<IString, String> {

  private static final String FEATURE_NAME = "TGTD";
  
  private final boolean addDomainFeatures;
  
  /**
   * Constructor.
   */
  public RuleTargetDimension() {
    this.addDomainFeatures = false;
  }
  
  /**
   * Constructor.
   * 
   * @param args
   */
  public RuleTargetDimension(String...args) {
    Properties options = FeatureUtils.argsToProperties(args);
    this.addDomainFeatures = options.containsKey("domainFeature");
  }

  @Override
  public void initialize() {}

  @Override
  public List<FeatureValue<String>> ruleFeaturize(
      Featurizable<IString, String> f) {
    List<FeatureValue<String>> features = Generics.newLinkedList();
    String featureString = String.format("%s:%d",FEATURE_NAME, f.targetPhrase.size());
    features.add(new FeatureValue<String>(featureString, 1.0));
    
    final String genre = addDomainFeatures && f.sourceInputProperties.containsKey(InputProperty.Domain)
        ? (String) f.sourceInputProperties.get(InputProperty.Domain) : null;
    if (genre != null) {
      features.add(new FeatureValue<String>(featureString + "-" + genre, 1.0));
    }
    return features;
  }

  @Override
  public boolean isolationScoreOnly() {
    return false;
  }
}