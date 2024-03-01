package us.telran.pawnshop.entity.enums;
/**
 * Enumerates the types of products or services offered by the system.
 * This enumeration allows for the clear categorization of the main services available,
 * facilitating their identification, promotion, and management within various system operations.
 * <p>
 * Currently, the available product names are:
 * <ul>
 *     <li>{@code BORROW} - Represents the service of providing loans or borrowing options to clients, allowing them to receive funds or goods with the obligation of a future repayment.</li>
 *     <li>{@code TRADE_IN} - Indicates a service that allows clients basically sell their belongings.</li>
 * </ul>
 * This enumeration is particularly useful for systems that manage financial transactions, retail operations, or services that include exchanging goods.
 *
 * @author bh-alexey
 *
 */
public enum ProductName {
    BORROW,   //A service option for clients looking to borrow funds or items with an agreement to repay and return their bails.
    TRADE_IN  //A service option of selling and precious metal as a scrap.
}
