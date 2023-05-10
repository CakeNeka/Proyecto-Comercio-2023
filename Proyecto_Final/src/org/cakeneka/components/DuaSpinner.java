package main;

import javax.swing.JSpinner;

/**
 * Extensión de la clase JSpinner que implementa los comportamientos
 * de un campo del dua (obligatoriedad y obtención del valor introducido)
 * @author Neka
 */
public class DuaSpinner extends JSpinner implements DuaInputField {
    private boolean required;

    public DuaSpinner() {
        super();
        required = false;       // será modificado a través del setter
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
    
    @Override
    public void setField(Object value) {
        setValue(value);
    }
}
