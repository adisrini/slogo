package slogo.model;

/**
 * The Class PenChange.
 */
abstract class PenChange extends TurtleStateCreator{
	
	private boolean myStatus;
	private static final int LIMIT = 0;
	
	/**
	 * set pen status according to myStatus
	 */
	@Override
	protected double setState(TurtleState myState) {
		myState.setPenStatus(myStatus);
		return myStatus ? 1 : 0;
	}
	
	/**
	 * Sets  my status.
	 *
	 * @param status the new my status
	 */
	protected void setMyStatus(boolean status){
		myStatus = status;
	}
	
	/**
	 * returns min args needed
	 */
	@Override
	protected int argsNeeded() {
		return LIMIT;
	}
}
