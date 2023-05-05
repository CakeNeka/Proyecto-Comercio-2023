package main;

public interface DuaInputField {
    boolean isRequired();
    String getField();
    void setRequired(boolean required);
    void setField(Object value);
}
