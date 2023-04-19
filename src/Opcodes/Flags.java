package Opcodes;

public class Flags {
    private int Z;
    private int N;
    private int H;
    private int C;

    // Constructor
    public Flags() {
        Z = 0;
        N = 0;
        H = 0;
        C = 0;
    }

    // Methods to set and retrieve flags
    public void setZFlag(int value) {
        Z = value;
    }

    public void setNFlag(int value) {
        N = value;
    }

    public void setHFlag(int value) {
        H = value;
    }

    public void setCFlag(int value) {
        C = value;
    }

    public int getZFlag() {
        return Z;
    }

    public int getNFlag() {
        return N;
    }

    public int getHFlag() {
        return H;
    }

    public int getCFlag() {
        return C;
    }
}
