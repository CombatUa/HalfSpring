package ua.alex.ioc.context;

public abstract class GeneralParser<T> {
    T value;

    public GeneralParser(T value) {
        this.value = value;
    }

    public abstract T getValue(String valueToParse);
}
