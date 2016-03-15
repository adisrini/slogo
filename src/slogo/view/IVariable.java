package slogo.view;

/**
 * Variable API
 * 
 * @author Aditya Srinivasan, Arjun Desai
 *
 */
interface IVariable {
    
    // External APIs
    /**
     * 
     * @return variable name
     */
    public String getName();
    
    /**
     * 
     * @return variable value
     */
    public Double getValue();
    
}
