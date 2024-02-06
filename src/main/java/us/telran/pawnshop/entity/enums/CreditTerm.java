package us.telran.pawnshop.entity.enums;

import lombok.Data;


public enum CreditTerm {
    WEEK(7),
    TWO_WEEKS(2 * WEEK.getDays()),
    THREE_WEEKS(3 * WEEK.getDays()),
    MONTH(4 * WEEK.getDays());

    private final int days;

    CreditTerm(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
