package com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.console.table;

import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.intellij.ui.treeStructure.Tree;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.state.DataSourceApi;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.datasource.tree.TreeContextMenuMouseAdapter;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.renderes.tree.PropertyTreeCellRenderer;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultTreeModel;
import java.util.Objects;

import static com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.helpers.UiHelper.canBeTree;
import static com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.helpers.UiHelper.keyValueToTreeNode;
import static com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.helpers.UiHelper.representUiString;

public class ValueConverter {

    private final TablePanel tablePanel;

    public ValueConverter(TablePanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    public Object convert(String columnName, Object value, DataSourceApi dataSourceApi) {
        if (canBeTree(value)) {
            return createTree(keyValueToTreeNode(columnName, value, dataSourceApi, value));
        } else if (value instanceof String) {
            return representUiString((String) value);
        }

        return Objects.toString(value);
    }

    private Tree createTree(PatchedDefaultMutableTreeNode root) {
        DefaultTreeModel treeModel = new DefaultTreeModel(root, false);
        Tree tree = new Tree();
        tree.setModel(treeModel);
        tree.setCellRenderer(new PropertyTreeCellRenderer());
        tree.addTreeExpansionListener(new TreeExpansionListener() {
            @Override
            public void treeExpanded(TreeExpansionEvent event) {
                tablePanel.updateRowHeights();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent event) {
                tablePanel.updateRowHeights();
            }
        });

        tree.addMouseListener(new TreeContextMenuMouseAdapter());

        treeModel.reload();
        return tree;
    }
}
