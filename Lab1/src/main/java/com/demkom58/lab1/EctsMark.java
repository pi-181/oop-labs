package com.demkom58.lab1;

public enum EctsMark {
    A(90, 5),
    B(80, 4),
    C(65, 4),
    D(55, 3),
    E(50, 3),
    FX(35, 2),
    F(1, 2);

    private final int lowerEctsMark;
    private final int nationalMark;

    EctsMark(int lowerEctsMark, int nationalMark) {
        this.lowerEctsMark = lowerEctsMark;
        this.nationalMark = nationalMark;
    }

    public int getLowerEctsMark() {
        return lowerEctsMark;
    }

    public int getNationalMark() {
        return nationalMark;
    }

    public static EctsMark fromEcts(int mark) {
        final EctsMark[] values = values();

        for (final EctsMark value : values)
            if (mark >= value.lowerEctsMark)
                return value;

        return EctsMark.F;
    }

}
