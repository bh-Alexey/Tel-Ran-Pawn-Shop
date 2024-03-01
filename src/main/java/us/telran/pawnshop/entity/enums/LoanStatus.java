package us.telran.pawnshop.entity.enums;

/**
 * Enumerates the possible statuses of a loan within the system.
 * This enumeration helps in tracking the current state of a loan, allowing for better management and processing of loan-related operations.
 * <p>
 * The statuses defined are:
 * <ul>
 *     <li>{@code IN_USE} - Indicates that the loan is currently active and the borrower is making use of the borrowed funds.</li>
 *     <li>{@code EXPIRED} - Signifies that the loan term has ended, but the loan has not yet been fully repaid or prolonged.</li>
 *     <li>{@code PAID} - Denotes that the loan has been fully repaid, and pledge should be return to owner.</li>
 * </ul>
 *
 * @author bh-alexey
 */
public enum LoanStatus {
    IN_USE,
    EXPIRED,
    PAID
}
