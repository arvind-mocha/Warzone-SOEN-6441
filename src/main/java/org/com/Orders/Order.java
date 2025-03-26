package org.com.Orders;


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