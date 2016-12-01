package Labb_3;
import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DirTreeE2 extends JFrame implements ActionListener {

    public DirTreeE2() {
        Container c = getContentPane();
        //*** Build the tree and a mouse listener to handle clicks
        //root = new DefaultMutableTreeNode(katalog);
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
        //*** build the tree by adding the nodes

        //*** panel the JFrame to hold controls and the tree
        controls = new JPanel();
        box = new JCheckBox( showString );
        init(); //** set colors, fonts, etc. and add buttons
        c.add( controls, BorderLayout.NORTH );
        c.add( tree, BorderLayout.CENTER );
        setVisible( true ); //** display the framed window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed( ActionEvent e ) {
        String cmd = e.getActionCommand();
        if ( cmd.equals( closeString ) )
            dispose();
    }

    private void init() {
        tree.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
        controls.add( box );
        addButton( closeString );
        controls.setBackground( Color.lightGray );
        controls.setLayout( new FlowLayout() );
        setSize( 400, 400 );
    }

    private void addButton( String n ) {
        JButton b = new JButton( n );
        b.setFont( new Font( "Dialog", Font.BOLD, 12 ) );
        b.addActionListener( this );
        controls.add( b );
    }

    private MyNode readNode() {
        String startTag;

        Scanner lineScan = new Scanner(xmlLine);

        startTag = lineScan.next().substring(1);

        lineScan.useDelimiter("\"");
        lineScan.next();
        String nameString = lineScan.next();

        String name = nameString;
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
                    System.out.println("Something was wrong with the file");
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

            try {
                root = readNode();
            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    private void buildTree( File f, DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode child =
                new DefaultMutableTreeNode( f.toString() );
        parent.add(child);
        if ( f.isDirectory() ) {
            String list[] = f.list();
            for ( int i = 0; i < list.length; i++ )
                buildTree( new File(f,list[i]), child);
        }
    }

    private void showDetails( TreePath p ) {
        if ( p == null )
            return;
        MyNode theNode = (MyNode)p.getLastPathComponent();
        JOptionPane.showMessageDialog( this, theNode.getLevelString() + ": " + theNode + theNode.getTextString());
    }

    private String getAttributes( File f ) {
        String t = "";
        if ( f.isDirectory() )
            t += "Directory";
        else
            t += "Nondirectory file";
        t += "\n   ";
        if ( !f.canRead() )
            t += "not ";
        t += "Readable\n   ";
        if ( !f.canWrite() )
            t += "not ";
        t += "Writeable\n  ";
        if ( !f.isDirectory() )
            t += "Size in bytes: " + f.length() + "\n   ";
        else {
            t += "Contains files: \n     ";
            String[ ] contents = f.list();
            for ( int i = 0; i < contents.length; i++ )
                t += contents[ i ] + ", ";
            t += "\n";
        }
        return t;
    }

    public static void main( String[ ] args ) {
        if(args.length>0) katalog=args[0];
        new DirTreeE2();
    }

    private String xmlLine;
    private Scanner scan;
    private JCheckBox box;
    private JTree tree;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JPanel controls;
    private static String katalog="Liv";
    private static final String closeString = " Close ";
    private static final String showString = " Show Details ";
}
