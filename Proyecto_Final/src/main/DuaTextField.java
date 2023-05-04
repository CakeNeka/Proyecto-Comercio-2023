package main;

import javax.swing.JTextField;

public class DuaTextField extends JTextField implements DuaInputField{
    private boolean required = false;

    public DuaTextField() {
        super();
    }    
    
    @Override
    public boolean isRequired() {
        return required;
    }
    
    @Override
    public void setRequired(boolean required) {
        this.required = required;
        if (required) {
            setText("soy obligatorio");
        } else {
            setText("soy opcional");
        }
    }

    @Override
    public String getField() {
        return getText();
    }
}
