package com.volha.io.filetree;

import java.io.File;

public class FileDirNode {
    String name;
    long length;
    boolean isdirectory;
    FileDirNode parentDir;
    String prefix;

    public FileDirNode(File file, StringBuilder prefix, FileDirNode parentDir) {
        this.name = file.getName();
        this.length = file.length();
        this.parentDir = parentDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public boolean isIsdirectory() {
        return isdirectory;
    }

    public void setIsdirectory(boolean isdirectory) {
        this.isdirectory = isdirectory;
    }

    public FileDirNode getParentDir() {
        return parentDir;
    }

    public void setParentDir(FileDirNode parentDir) {
        this.parentDir = parentDir;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}