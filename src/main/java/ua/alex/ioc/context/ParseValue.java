package ua.alex.ioc.context;

import java.util.function.Supplier;

public class ParseValue {

//    public interface ValueString extends Supplier<Class<String>> {}
//    public Object parseValue (ValueString string,String valueToParse) {
//        System.out.println("String!!!");
//        return valueToParse;
//    }
//
//    public interface ValueInteger extends Supplier<Class<Integer>> {}
//    public Object parseValue (ValueInteger string,String valueToParse) {
//        System.out.println("Integer!!!");
//        return  Integer.parseInt(valueToParse);
//    }

    public interface ValueString2 extends Supplier<Class<? extends String>> {}
    public Object parseValue (ValueString2 string) {
        System.out.println("String2!!!");
        return "asdf";
    }


    public interface ValueInteger2 extends Supplier<Class<? extends Integer>> {}
    public Object parseValue (ValueInteger2 string) {
        System.out.println("Integer!!!");
        return  "asdf";
    }
}
