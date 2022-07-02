package com.pro.fooddonorKE.api.dao.parameter_resolver;

import com.pro.fooddonorKE.api.models.Charity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class CharityParameterResolver implements ParameterResolver {
  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.getParameter().getType() == Charity.class;
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return new Charity("Our Hearts Children Home", "Human Services Charity", "Westlands", "A children's home located in Westlands that rehabilitates vulnerable children", "https://www.ourheartschildrenhome.org");
  }
}
