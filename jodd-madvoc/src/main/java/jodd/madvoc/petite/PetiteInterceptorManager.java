// Copyright (c) 2003-2013, Jodd Team (jodd.org). All Rights Reserved.

package jodd.madvoc.petite;

import jodd.madvoc.component.InterceptorsManager;
import jodd.madvoc.interceptor.ActionInterceptor;
import jodd.petite.meta.PetiteInject;
import jodd.petite.PetiteContainer;

/**
 * Petite-aware interceptors manager.
 */
public class PetiteInterceptorManager extends InterceptorsManager {

	@PetiteInject
	protected PetiteContainer petiteContainer;

	/**
	 * Acquires interceptor from Petite container.
	 */
	@Override
	protected ActionInterceptor createInterceptor(Class<? extends ActionInterceptor> interceptorClass) {
		return petiteContainer.createBean(interceptorClass);
	}
}
