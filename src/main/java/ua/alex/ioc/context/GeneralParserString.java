package ua.alex.ioc.context;

public class GeneralParserString extends GeneralParser<String> {

    public GeneralParserString(String value) {
        super(value);
    }

    @Override
    public String getValue(String valueToParse) {
        return value;
    }
}
