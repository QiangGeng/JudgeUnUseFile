package com.coder.qiang.modal;

/**
 * Created by Administrator on 2016/12/6.
 */
public class UnUseFile {

    private String name;
    private String path;
    private String suffix;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return "UnUseFile{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }

    public UnUseFile() {
    }
}
