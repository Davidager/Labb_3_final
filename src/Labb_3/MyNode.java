package Labb_3;

import javax.swing.text.html.ObjectView;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by David on 30-Nov-16.
 */
public class MyNode extends DefaultMutableTreeNode{
    private String level;
    private String text;

    public MyNode(Object userObject, boolean allowsChildren, String level, String text) {
        super(userObject, allowsChildren);
        this.level = level;
        this.text = text;
    }

    public String getLevelString() {
        return level;
    }

    public String getTextString() {
        return text;
    }
}
