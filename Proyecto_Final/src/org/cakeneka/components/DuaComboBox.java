package org.cakeneka.components;

import javax.swing.JComboBox;

/**
 * Extensión de la clase JComboBox que implementa los comportamientos
 * de un campo del dua (obligatoriedad y obtención del valor introducido)
 * @author Neka
 */
public class DuaComboBox extends JComboBox implements DuaInputField{

    private boolean required;

    public DuaComboBox() {
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
        return getSelectedItem().toString();
    }

    @Override
    public void setField(String value) {  
        setSelectedItem(value);
    }
    
}
