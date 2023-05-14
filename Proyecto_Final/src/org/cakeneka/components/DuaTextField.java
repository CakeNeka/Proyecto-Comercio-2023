package org.cakeneka.components;

import javax.swing.JTextField;

public class DuaTextField extends JTextField implements DuaInputField{
    private boolean required;

    public DuaTextField() {
        super();
        required = false;
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
        return getText();
    }
    
    @Override
    public void setField(String value) {
        setText(value);
    }
}
