package us.telran.pawnshop.entity.enums;

/**
 * Represents the different terms for a loan's duration within the system.
 * This enumeration defines common loan terms, represented by the number of days each term lasts.
 * Utilizing Lombok's {@code @Getter} to provide automatic getter generation for the {@code days} field,
 * simplifying access to the duration of each term.
 * <p>
 * The terms defined are:
 * <ul>
 *     <li>{@code WEEK} - A loan term lasting for 7 days.</li>
 *     <li>{@code TWO_WEEKS} - A loan term lasting for 14 days, calculated as 2 times the duration of {@code WEEK}.</li>
 *     <li>{@code THREE_WEEKS} - A loan term lasting for 21 days, calculated as 3 times the duration of {@code WEEK}.</li>
 *     <li>{@code MONTH} - A loan term approximately lasting for a month, defined as 30 days.</li>
 *     <li>{@code NONE} - Represents the absence of a loan term, prepared for another product as Collection.</li>
 * </ul>
 * Note: The calculation of {@code TWO_WEEKS} and {@code THREE_WEEKS} dynamically uses the {@code getDays} method
 * from the {@code WEEK} instance, ensuring consistency in the representation of weeks.
 *
 * @author The enumerator's constructor is private, ensuring that the enumeration's instances are controlled
 * and limited to the predefined constants.
 */

import lombok.Getter;

@Getter
public enum LoanTerm {
    WEEK(7),
    TWO_WEEKS(2 * WEEK.getDays()),
    THREE_WEEKS(3 * WEEK.getDays()),
    MONTH(30),
    NONE(0);

    private final int days; // The number of days the loan term lasts.
    /**
     * Private constructor for defining loan terms with their respective durations in days.
     *
     * @param days the duration of the loan term in days.
     */
    LoanTerm(int days) {
        this.days = days;
    }

}
