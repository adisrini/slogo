package slogo.model;

import java.util.HashMap;
import java.util.Map;

public abstract class InnerScopedFunction extends GeneralFunction{
	protected Map<String,Double> myScope;
	/**
     * Create completely new scope that can be passed down, so that it won't
     * change the scope of those above it.
     */
	@Override
	public void createFunction(IParser p, IMemory m, Map<String,Double> scope) {
        myScope = new HashMap<String,Double>(scope);
	}
}