package com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.datasource.tree;

import com.intellij.ui.treeStructure.PatchedDefaultMutableTreeNode;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.component.datasource.state.DataSourceApi;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.datasource.metadata.dto.ContextMenu;
import com.neueda.jetbrains.plugin.graphdb.jetbrains.ui.datasource.tree.dto.ValueWithIcon;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.Optional;

public class ContextMenuService {

    private static final int DATASOURCE_INDEX = 1;
    private static final int METADATA_INDEX = 2;
    private static final int EXPECTED_DEPTH = 4;

    public Optional<ContextMenu> getContextMenu(TreePath path) {
        if (path != null && path.getPathCount() == EXPECTED_DEPTH) {

            TreeNode lastPathComponent = (TreeNode) path.getLastPathComponent();

            TreeNodeModelApi model = extractUserObject(lastPathComponent);
            return model.getContextMenu();
//            String metadataType = getMetadataType(path);
//            String uuid = getDataSourceUuid(path);
//            String data = getMetadataValue(lastPathComponent);
//
//            switch (metadataType) {
//                case LABELS_TITLE:
//                    return Optional.of(new ContextMenu(LABELS, uuid, data));
//                case RELATIONSHIP_TYPES_TITLE:
//                    return Optional.of(new ContextMenu(RELATIONSHIP_TYPES, uuid, data));
//                case PROPERTY_KEYS_TITLE:
//                    return Optional.of(new ContextMenu(PROPERTY_KEYS, uuid, data));
//                default:
//                    return Optional.empty();
//            }
        }
        return Optional.empty();
    }

    private String getMetadataValue(TreeNode lastPathComponent) {
        return lastPathComponent.toString();
    }

//    @Deprecated
//    private String getMetadataType(TreePath path) {
//        Object type = path.getPathComponent(METADATA_INDEX);
//        Object userObject = extractUserObject(type);
//        return extractValue(userObject);
//    }
//
//    @Deprecated
//    private String getDataSourceUuid(TreePath path) {
//        Object dataSourceNode = path.getPathComponent(DATASOURCE_INDEX);
//        Object dsUserObject = extractUserObject(dataSourceNode);
//        return extractUuid(dsUserObject);
//    }

    @Deprecated
    private String extractValue(Object userObject) {
        if (userObject instanceof ValueWithIcon) {
            return ((ValueWithIcon) userObject).getValue();
        }
        return null;
    }

    private TreeNodeModelApi extractUserObject(TreeNode node) {
        if (node instanceof PatchedDefaultMutableTreeNode) {
            return (TreeNodeModelApi) ((PatchedDefaultMutableTreeNode) node).getUserObject();
        }
        return null;
    }

    @Deprecated
    private String extractUuid(Object dsUserObject) {
        if (dsUserObject instanceof DataSourceApi) {
            return ((DataSourceApi) dsUserObject).getUUID();
        }
        return null;
    }
}