package slogo.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Model implements IModel {
    // TODO: Make try catches for most of these methods
    private IMemory memory;

    public Model () {
        memory = Memory.getMemoryInstance();
    }

    @Override
    public void initExecution (String input) {
        // create new execution from input
        IExecution exec = new Execution();
        // ADD IN A STARTING STATE FOR EACH EXECUTION
        if (!memory.hasPrevExecution()) {
            //exec.addState(new State());
        	 //ADDED: set the builder state to be @ 0,0 if it's ur first exec
        	IExecution starter = new Execution();
        	starter.addState(new State());
        	memory.writeExecution(starter);
        	memory.setBuilderState(new State());
        }
        memory.writeExecution(exec);


        Parser p = new Parser(input, memory);

        List<IFunction> functions = new ArrayList<IFunction>();
        while (p.hasNext()) {
            try {
                functions.add(p.parseFunction()); 
            }
            catch (SlogoException e) {
                throw e;
            }
        }
        // execute all functions
        for (IFunction function : functions) {
            function.executeFunction(memory);
        }
        
        //ADDED: update builder state to be the last in ur execution if it contains a state
        if(exec.hasCurrState()){
        	memory.setBuilderState(exec.getCurrState());
        }
    }

    @Override
    public IState redoState () {
        if (memory.readCurrExecution().hasNextState()) { // if current execution has a next state
            IState s = memory.readCurrExecution().redoState();
            memory.setBuilderState(s); //update the new builder state to be this state
            return s;
        }// otherwise, redo a full execution if you are at the end
        return redoExecution();
    }

    @Override
    public IState undoState () {
        if (memory.readCurrExecution().hasPrevState()) {// if current execution has a previous state
            IState s = memory.readCurrExecution().undoState();// then get the last state and
            memory.setBuilderState(s); //update the new builder state to be this state
            return s;
        }
        return undoExecution();
    }

    @Override
    public IState redoExecution () {
    	return memory.redoExecution();
    }

    @Override

    public IState undoExecution () {
    	IState s =  memory.undoExecution();
    	if(s == null){return s;}
    	memory.setBuilderState(s); //update the new builder state to be this state
    	return s;
    }

    public IState resetExecution () {
        try {
            IState s = memory.readCurrExecution().getListOfStates().get(0);
            memory.setBuilderState(s); //update the new builder state to be this state
            return s;
        }
        catch (Exception e) {
            throw new SlogoException("ERROR: no execution exists");
        }
    }

    @Override
    public Iterator<IState> getStateIterator () {
        return memory.readCurrExecution().getListOfStates().iterator();
    }
}
