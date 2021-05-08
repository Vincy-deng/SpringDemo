package com.test.vo;

import java.util.ArrayList;
import java.util.List;

public class treeNode {
    private Integer id;//节点的id，每一个树节点的id，对应插件id属性
    private String label;//文本，对应label属性，显示在节点上的文本
    private String icon;//对应插件icon属性，列表树节点上的图标，通常是节点左边的图标。，在这里例如glyphicon glyphicon-stop
    private String selectedIcon;//对应插件selectIcon属性，当某个节点被选择后显示的图标，通常是节点左边的图标
    private String color;//对应color，节点的前景色，覆盖全局的前景色选项。
    private String backColor;//对应插件backColor，节点的背景色，覆盖全局的背景色选项。
    private String href;//对应插件href属性，结合全局enableLinks选项为列表树节点指定URL
    private boolean selectable;//对应selectable属性，指定列表树的节点是否可选择。设置为false将使节点展开，并且不能被选择
    private List<treeNode>children = new ArrayList<treeNode>();//保存子节点，对应插件中children属性
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getSelectedIcon() {
        return selectedIcon;
    }
    public void setSelectedIcon(String selectedIcon) {
        this.selectedIcon = selectedIcon;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getBackColor() {
        return backColor;
    }
    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }
    public String getHref() {
        return href;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public boolean isSelectable() {
        return selectable;
    }
    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }
    public List<treeNode> getChildren() {
        return children;
    }
    public void setChildren(List<treeNode> children) {
        this.children = children;
    }
    @Override
    public String toString() {
        return "treeNode [id=" + id + ", label=" + label + ", icon=" + icon + ", selectedIcon=" + selectedIcon
                + ", color=" + color + ", backColor=" + backColor + ", href=" + href + ", selectable=" + selectable
                + ", children=" + children + "]";
    }
}
