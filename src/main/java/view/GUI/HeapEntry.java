package view.GUI;

public class HeapEntry {
    private final int address;
    private final String value;

    public HeapEntry(int address, String value) {
        this.address = address;
        this.value = value;
    }

    public int getAddress() {
        return address;
    }

    public String getValue() {
        return value;
    }
}