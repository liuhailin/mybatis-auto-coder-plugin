package com.lhl.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class AutoCoderDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField data;

    public AutoCoderDialog() {
        setContentPane( this.contentPane );
        setModal( true );
        getRootPane().setDefaultButton( this.buttonOK );

        this.buttonOK.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {onOK();}
        } );

        this.buttonCancel.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {onCancel();}
        } );

        // call onCancel() when cross is clicked
        setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                onCancel();
            }
        } );

        // call onCancel() on ESCAPE
        this.contentPane.registerKeyboardAction( new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke( KeyEvent.VK_ESCAPE, 0 ), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT );
    }

    public static void main(final String[] args) {
        final AutoCoderDialog dialog = new AutoCoderDialog();
        dialog.pack();
        dialog.setVisible( true );
        System.exit( 0 );
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
