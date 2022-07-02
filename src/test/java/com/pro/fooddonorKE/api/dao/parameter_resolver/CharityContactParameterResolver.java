package com.pro.fooddonorKE.api.dao.parameter_resolver;

import com.pro.fooddonorKE.api.models.CharityContact;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class CharityContactParameterResolver implements ParameterResolver {
  @Override
  public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return parameterContext.getParameter().getType() == CharityContact.class;
  }

  @Override
  public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
    return new CharityContact("+254701987905", "info@ourheartchildrenhome.org", "https://www.facebook.com", "https://www.twitter.com", "https://www.instagram.com");
  }
}
