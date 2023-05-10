package org.cakeneka.components;

/**
 * Define el comportamiento que deben implementar los campos del formulario.
 * Estos deben proporcionar la información introducida (getField(), setField())
 * Y deben indicar si son o no obligatorios (isRequired(), setRequired())
 * @author Neka
 */
public interface DuaInputField {
    boolean isRequired();
    void setRequired(boolean required);
    
    String getField();
    void setField(String value);
}
