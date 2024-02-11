package us.telran.pawnshop.entity.enums;


import lombok.Getter;

@Getter
public enum LoanTerm {
    WEEK(7),
    TWO_WEEKS(2 * WEEK.getDays()),
    THREE_WEEKS(3 * WEEK.getDays()),
    MONTH(30);

    private final int days;

    LoanTerm(int days) {
        this.days = days;
    }

}
