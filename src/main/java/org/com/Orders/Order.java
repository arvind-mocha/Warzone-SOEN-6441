package org.com.Orders;


/**
 * Basic interface/structure of all orders
 *
 *
 * @author Arvind Nachiappan
 */
public interface Order {
    /**
     * Executes an Order
     */
    void execute();

    /**
     * Function to validate the command
     */
    void isValid() throws Exception;
}