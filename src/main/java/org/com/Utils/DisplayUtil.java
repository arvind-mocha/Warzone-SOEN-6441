package org.com.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.Serializable;

/**
 * This class contains util method for displaying the map.
 *
 * @author Arvind Lakshmanan
 *
 */
public class DisplayUtil implements Serializable {

    /**
     * Displays the map in a GUI table format.
     *
     * @param l_data The data to be displayed in the table.
     * @param l_columnNames The names of the columns in the table.
     * @param p_mapName The name of the map to be displayed in the window title.
     */
    public static void displayMap(String[][] l_data, String[] l_columnNames, String p_mapName) {
        // Creating the tableModel object
        DefaultTableModel tableModel = new DefaultTableModel(l_data, l_columnNames);
        JTable gameTable = new JTable(tableModel);

        // Adding scroll bar to the GUI
        JScrollPane scrollPane = new JScrollPane(gameTable);
        JFrame frame = new JFrame(p_mapName);
        frame.add(scrollPane);

        // Adjusting the initial frame of the GUI
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
