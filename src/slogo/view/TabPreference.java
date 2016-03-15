package slogo.view;

public enum TabPreference {
    NEW("0"),LOAD("0");
    
    private String index;
    
    private TabPreference(String index){
        this.index=index;
    }
    
    public void setIndex(String index){
        this.index=index;
    }
    
    public String getIndex(){
        return index;
    }
}
