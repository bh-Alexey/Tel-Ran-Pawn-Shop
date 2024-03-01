package us.telran.pawnshop.entity.enums;
/**
 * Enumerates the types of financial transactions that can occur within the system.
 * This simple classification helps in distinguishing between incoming and outgoing financial flows,
 * facilitating accounting, financial analysis, and budget management.
 * <p>
 * The transaction types are:
 * <ul>
 *     <li>{@code INCOME} - Represents an incoming cashier order as financial transaction, indicating an increase in resources or assets.</li>
 *     <li>{@code EXPENSE} - Represents an outgoing cashier order as financial transaction, indicating a reduction in resources or spending of assets.</li>
 * </ul>
 *
 * @author bh-alexey
 *
 */
public enum OrderType {
    INCOME,  //An indicator of financial gain or incoming resources.
    EXPENSE  //An indicator of financial expenditure or outgoing resources.
}
