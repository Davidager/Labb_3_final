package Labb_3;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DirTreeC extends JFrame implements ActionListener {
    private static final String CLOSE_STRING = " Close ";
    private static final String SHOW_STRING = " Show Details ";

    private String xmlLine;
    private Scanner scan;
    private JCheckBox box;
    private JTree tree;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JPanel controls;

    public DirTreeC() {
        Container myContainer = getContentPane();
        /* Build the tree and a mouse listener to handle clicks */
        buildTree();
        treeModel = new DefaultTreeModel( root );
        tree = new JTree( treeModel );
        MouseListener ml =
                new MouseAdapter() {
                    public void mouseClicked( MouseEvent e ) {
                        if ( box.isSelected() )
                            showDetails( tree.getPathForLocation( e.getX(),
                                    e.getY() ) );
                    }
                };
        tree.addMouseListener( ml );

        /* panel the JFrame to hold controls and the tree */
        controls = new JPanel();
        box = new JCheckBox(SHOW_STRING);
        init(); // set colors, fonts, etc. and add buttons
        myContainer.add( controls, BorderLayout.NORTH );
        myContainer.add( tree, BorderLayout.CENTER );
        setVisible( true ); // display the framed window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed( ActionEvent e ) {
        String command = e.getActionCommand();
        if ( command.equals(CLOSE_STRING) )
            dispose();
    }

    private void init() {
        tree.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
        controls.add( box );
        addButton(CLOSE_STRING);
        controls.setBackground( Color.lightGray );
        controls.setLayout( new FlowLayout() );
        setSize( 400, 400 );
    }

    private void addButton( String n ) {
        JButton button = new JButton( n );
        button.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
        button.addActionListener( this );
        controls.add( button );
    }

    private MyNode readNode() {
        String startTag;

        Scanner lineScan = new Scanner(xmlLine);

        startTag = lineScan.next().substring(1);

        lineScan.useDelimiter("\"");
        lineScan.next();
        String nameString = lineScan.next();

        String name = nameString;//.substring(6, nameString.length()-2);m    FIXA
        lineScan.useDelimiter(">");
        lineScan.next();
        if(!lineScan.hasNext()){
            System.out.println("Something was wrong with the file");
            System.exit(0);
        }
        String text = lineScan.next();


        MyNode retNode = new MyNode(name, true, startTag, text);

        while (true){
            xmlLine = scan.nextLine();
            if (xmlLine.startsWith("</")) {
                if (xmlLine.equals("</"+startTag+">")) {
                    return retNode;
                } else {
                    System.out.println("Something was wrong with the fileasd");
                    System.exit(0);
                }
            } else if (xmlLine.startsWith("<")) {
                retNode.add(readNode());
            } else {
                System.out.println("Something was wrong with the file");
                System.exit(0);
            }
        }
    }

    private void buildTree() {
        try {
            scan = new Scanner(new File("Liv.xml")).useDelimiter("<");
            scan.next();
            scan.next();    // hoppar förbi <?xml version="1.0" encoding="UTF-8"?>
            xmlLine = scan.nextLine();
            //System.out.println(xmlLine);
            try {
                root = readNode();
            } catch (Exception e) {
                System.out.println("Something was wrong with the file: ");
                System.out.println(e);
                System.exit(0);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private void showDetails( TreePath path ) {
        if ( path == null )
            return;
        MyNode theNode = (MyNode)path.getLastPathComponent();
        String chainString = " men allt som är " + theNode;
        MyNode iterNode = theNode;
        while (iterNode.getParent()!=null) {
            chainString = chainString + " är " + iterNode.getParent();
            iterNode = (MyNode)iterNode.getParent();
        }
        if (theNode.getParent()==null) {     // för att det ska bli något vettigt när man klickar på liv
            chainString = chainString + " lever!";
        }
        JOptionPane.showMessageDialog( this, theNode.getLevelString() + ": " + theNode + theNode.getTextString() + chainString);
    }


    public static void main( String[ ] args ) {
        new DirTreeC();
    }


}
