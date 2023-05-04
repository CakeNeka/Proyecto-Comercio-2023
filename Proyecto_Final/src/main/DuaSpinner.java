package main;

import javax.swing.JSpinner;

public class DuaSpinner extends JSpinner implements DuaInputField {
    private boolean required = false;

    public DuaSpinner() {
        super();
    }    
    
    @Override
    public boolean isRequired() {
        return required;
    }
    
    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public String getField() {
        return getValue().toString();
    }
}
