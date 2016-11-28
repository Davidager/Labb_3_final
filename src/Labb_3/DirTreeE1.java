import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class DirTreeE1 extends JFrame implements ActionListener {

    public DirTreeE1() {
        Container c = getContentPane();
        //*** Build the tree and a mouse listener to handle clicks
        root = new DefaultMutableTreeNode(katalog);
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
        buildTree();
        //*** panel the JFrame to hold controls and the tree
        controls = new JPanel();
        box = new JCheckBox( showString );
        init(); //** set colors, fonts, etc. and add buttons
        c.add( controls, BorderLayout.NORTH );
        c.add( tree, BorderLayout.CENTER );
        setVisible( true ); //** display the framed window
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
    /*interface NodeBuilder{
        void buildNode(String barn, String familj, String släkte, String art);
    }*/
    private void buildTree() {
       /*DefaultMutableTreeNode[] nodeArray = new DefaultMutableTreeNode[12];
       int counter = 0;
       new NodeBuilder() {
           void buildNode(String barn, String familj, String släkte, String art) {
               for (int i = 1; i<5; i++) {
                   nodeArray[counter] = new DefaultMutableTreeNode(barn);

               }
           }
       }*/
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("Växter");
        DefaultMutableTreeNode child11 = new DefaultMutableTreeNode("Korgblommiga växter");
        DefaultMutableTreeNode child111 = new DefaultMutableTreeNode("Tistelsläktet");
        DefaultMutableTreeNode child1111 = new DefaultMutableTreeNode("Jordtistel");
        root.add(child1);
        child1.add(child11);
        child11.add(child111);
        child111.add(child1111);

        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Djur");
        DefaultMutableTreeNode child21 = new DefaultMutableTreeNode("Hjortdjur");
        DefaultMutableTreeNode child211 = new DefaultMutableTreeNode("Älgar");
        DefaultMutableTreeNode child2111 = new DefaultMutableTreeNode("Europeisk älg");
        root.add(child2);
        child2.add(child21);
        child21.add(child211);
        child211.add(child2111);


        DefaultMutableTreeNode child3 = new DefaultMutableTreeNode("Svampar");
        DefaultMutableTreeNode child31 = new DefaultMutableTreeNode("Ramariaceae");
        DefaultMutableTreeNode child311 = new DefaultMutableTreeNode("Ramaria");
        DefaultMutableTreeNode child3111 = new DefaultMutableTreeNode("Gul fingersvamp");
        root.add(child3);
        child3.add(child31);
        child31.add(child311);
        child311.add(child3111);

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
        File f = new File( p.getLastPathComponent().toString() );
        JOptionPane.showMessageDialog( this, f.getPath() +
                "\n   " +
                getAttributes( f ) );
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
        new DirTreeE1();
    }

    private JCheckBox box;
    private JTree tree;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JPanel controls;
    private static String katalog="Liv";
    private static final String closeString = " Close ";
    private static final String showString = " Show Details ";
}
