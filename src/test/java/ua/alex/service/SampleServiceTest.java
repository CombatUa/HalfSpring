package ua.alex.service;

public class SampleServiceTest implements SampleService {
    private char aChar;
    private byte aByte;
    private short aShort;
    private int anInt;
    private long aLong;
    private float aFloat;
    private double aDouble;
    private String string;

    public char getAChar() {
        return aChar;
    }

    public byte getAByte() {
        return aByte;
    }

    public short getAShort() {
        return aShort;
    }

    public int getAnInt() {
        return anInt;
    }

    public long getALong() {
        return aLong;
    }

    public float getAFloat() {
        return aFloat;
    }

    public double getADouble() {
        return aDouble;
    }

    public String getString() {
        return string;
    }

    public void setAChar(char aChar) {
        this.aChar = aChar;
    }

    public void setAByte(byte aByte) {
        this.aByte = aByte;
    }

    public void setAShort(short aShort) {
        this.aShort = aShort;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public void setALong(long aLong) {
        this.aLong = aLong;
    }

    public void setAFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public void setADouble(double aDouble) {
        this.aDouble = aDouble;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "SampleServiceTest{" +
                "aChar=" + aChar +
                ", aByte=" + aByte +
                ", aShort=" + aShort +
                ", anInt=" + anInt +
                ", aLong=" + aLong +
                ", aFloat=" + aFloat +
                ", aDouble=" + aDouble +
                ", string='" + string + '\'' +
                '}';
    }
}