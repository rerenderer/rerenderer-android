package com.nvbn.tryrerenderer;

public class TestCls {
    public TestCls() {}

    public TestCls(String x) {
        attr = x;
    }

    public String attr = "test";

    public Integer method(Integer x, Integer y) {
        return x + y;
    }

    public String method(String x, String y) {
        return x + "-" + y;
    }

    public String method(String x, Float y) {
        return x + "-" + y;
    }

    public String method(int x, String y) {
        return x + "!" + y;
    }

    public static String staticAttr = "static-test";

    public static Integer staticMethod(Integer x, Integer y) {
        return x + y;
    }
}
