package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hack Assembler");
        JLabel label = new JLabel("Drag an error free .txt code file here", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(label);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        new DropTarget(label, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    for (File file : droppedFiles) {
                        if (!file.getName().endsWith(".txt")) {
                            JOptionPane.showMessageDialog(frame, "Only .txt files are supported.", "Error", JOptionPane.ERROR_MESSAGE);
                            continue;
                        }

                        String path = file.getAbsolutePath();
                        String outputPath = path.substring(0, path.length() - 4) + "(bin).txt";

                        // First pass
                        try (FileInputStream fis1 = new FileInputStream(file)) {
                            SymbolTable.reset(); // Reset symbol table on each run
                            Parser.firstPass(fis1);
                        }

                        // Second pass
                        try (
                                FileInputStream fis2 = new FileInputStream(file);
                                FileOutputStream fos = new FileOutputStream(outputPath)
                        ) {
                            Parser.secondPass(fis2, fos);
                        }

                        JOptionPane.showMessageDialog(frame, "Compilation completed:\n" + outputPath, "Success", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "An error occurred:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
