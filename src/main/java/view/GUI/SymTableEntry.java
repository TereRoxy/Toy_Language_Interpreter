package view.GUI;

public class SymTableEntry {
    private final String variableName;
    private final String value;

    public SymTableEntry(String variableName, String value) {
        this.variableName = variableName;
        this.value = value;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getValue() {
        return value;
    }
}