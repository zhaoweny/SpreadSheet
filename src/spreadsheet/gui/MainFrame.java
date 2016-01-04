package spreadsheet.gui;

import org.nfunk.jep.ParseException;
import spreadsheet.api.cell.Location;
import spreadsheet.gui.menu.MenuBarFactory;
import spreadsheet.gui.menu.MenuFactory;
import spreadsheet.gui.menu.MenuItemFactory;
import spreadsheet.gui.table.CellEditor;
import spreadsheet.gui.table.CellRenderer;
import spreadsheet.gui.table.TableModel;
import spreadsheet.implement.SpreadsheetImpl;
import spreadsheet.io.CsvAdapter;
import spreadsheet.parser.Parser;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zhaow on 12/28/2015.
 * spreadsheet
 */
public class MainFrame extends JFrame {
    private static final Dimension FRAME_SIZE = new Dimension(800, 600);
    private static final int COLUMN_WIDTH = 35;
    private static final int MAX_ROW = 10000;
    private static final int MAX_COLUMN = 1000;

    private JTable table;
    private TableModel tableModel;
    private SpreadsheetImpl spreadSheet;
    private JCheckBox autoComputeCheckBox;

    public MainFrame(SpreadsheetImpl spreadSheet) {
        super("Spreadsheet");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new TableModel(spreadSheet, MAX_ROW, MAX_COLUMN);
        table = new JTable(tableModel);
        this.spreadSheet = spreadSheet;
        autoComputeCheckBox = new JCheckBox();


        add(getExpressionToolBar(), BorderLayout.SOUTH);
        add(getMainMenuBar(), BorderLayout.NORTH);
        add(getMainTable(), BorderLayout.CENTER);

        validate();
        setSize(FRAME_SIZE);
    }

    private JScrollPane getMainTable() {
        CellEditor cellEditor = new CellEditor(spreadSheet, new JTextField());
        cellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                if (autoComputeCheckBox.isSelected())
                    reCompute();
                tableModel.fireTableDataChanged();
            }

            @Override
            public void editingCanceled(ChangeEvent e) {

            }
        });


        table.setAutoCreateColumnsFromModel(false);
        table.setDefaultEditor(Object.class, cellEditor);
        table.setDefaultRenderer(Object.class, new CellRenderer(spreadSheet));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getTableHeader().setReorderingAllowed(false);

        TableColumn firstColumn = table.getColumnModel().getColumn(0);
        firstColumn.setMaxWidth(COLUMN_WIDTH);
        firstColumn.setPreferredWidth(COLUMN_WIDTH);
        return new JScrollPane(table);
    }

    private JToolBar getExpressionToolBar() {
        JToolBar bar = new JToolBar("Command");
        bar.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Input Command: ");
        bar.add(label);

        JTextField expTextField = new JTextField();
        bar.add(expTextField);

        JButton expOKButton = new JButton("OK");
        bar.add(expOKButton);

        bar.addSeparator();


        bar.add(autoComputeCheckBox);

        JButton calcButton = new JButton("Calculate");
        bar.add(calcButton);

        expTextField.setText("Usage: Key=Value; another Key=Value");
        ActionListener expListener = e -> {
            String expression = expTextField.getText();
            try {
                for (String[] command : Parser.parseCommand(expression))
                    spreadSheet.setExpression(new Location(command[0]), command[1]);

                if (autoComputeCheckBox.isSelected())
                    reCompute();
                tableModel.fireTableDataChanged();
            } catch (ParseException exception) {
                JOptionPane.showMessageDialog(this,
                        "Invalid Command Sequence Entered!", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                exception.printStackTrace();
            }

            expTextField.selectAll();

        };

        expTextField.addActionListener(expListener);
        expOKButton.addActionListener(expListener);
        autoComputeCheckBox.setToolTipText("Select to Enable Auto Re-Compute Spreadsheet.");
        autoComputeCheckBox.addActionListener(e -> calcButton.setEnabled(!autoComputeCheckBox.isSelected()));
        calcButton.addActionListener(e -> reCompute());

        return bar;
    }

    private void reCompute() {
        spreadSheet.reCompute();
        tableModel.fireTableDataChanged();
    }

    private JMenuBar getMainMenuBar() {
        MenuItemFactory itemFactory = new MenuItemFactory();
        MenuFactory menuFactory = new MenuFactory();

        itemFactory.addMenuItem("New", "ctrl N", e -> {
            spreadSheet.clearAll();
            reCompute();
        });

        itemFactory.addMenuItem("Open", "ctrl O", e -> {
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    CsvAdapter.read(new FileReader(file), spreadSheet);
                    if (autoComputeCheckBox.isSelected())
                        reCompute();
                    tableModel.fireTableDataChanged();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        itemFactory.addMenuItem("Save", "ctrl S", e -> {
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    CsvAdapter.write(new FileWriter(file), spreadSheet, MAX_ROW, MAX_COLUMN);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        itemFactory.addMenuItem("Exit", "alt F4", e -> System.exit(0));
        menuFactory.addMenu("File", itemFactory.getList());
        return MenuBarFactory.getMenuBar(menuFactory.getList());
    }
}

