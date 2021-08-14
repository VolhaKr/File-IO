package com.volha.io.filetree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTreeImpl implements FileTree {
    List<FileDirNode> filesDirsTree;

    @Override
    public Optional<String> tree(Path path) {
        File file = new File(String.valueOf(path));
        StringBuilder prefix;
        if (path == null || Files.notExists(path)) {
            return Optional.empty();
        } else {
            if (file.isFile()) {
                return Optional.ofNullable(file.getName() + " " + file.length() + " bytes");//some-file.txt 128 bytes
            } else {
                try {
                    StringBuilder sb = new StringBuilder();
                    prefix = new StringBuilder();
                    return Optional.of(showDir(file, sb, prefix, false, new FileDirNode(file, new StringBuilder(""),
                            null)).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            }
        }
    }

    StringBuilder showDir(File file, StringBuilder sb, StringBuilder prefix, boolean lastElement, FileDirNode parentDir) throws IOException {
        Long folderSize;
        ArrayList<File> filesOnly = new ArrayList<>();
        ArrayList<File> directories = new ArrayList<>();
        sb.append(prefix);
        FileDirNode curFile = new FileDirNode(file, prefix, parentDir);
        //????
        filesDirsTree.add(curFile);
        if (file.isFile()) {

            // sb.append(file.length());
        } else {
            curFile.setLength(file.length());
            // sb.append(getFolderSize(file));
        }
        sb.append(" bytes\n");

        if (file.isDirectory()) {
            StringBuilder thisPrefix = new StringBuilder(prefix);
            File[] childFiles = file.listFiles();
            for ( File fileOrDir : childFiles ) {
                if (fileOrDir.isDirectory()) {
                    directories.add(fileOrDir);
                } else {
                    if (fileOrDir.isFile()) {
                        filesOnly.add(fileOrDir);
                    }
                }
            }

            Collections.sort(directories);
            if (directories.size()==0){
                long sizeOfParentDir = 0;
                for (File leafFile : filesOnly){
                    sizeOfParentDir = sizeOfParentDir+leafFile.length();
                }
                //calculate the size of the directory
            }
            Collections.sort(filesOnly);
            directories.addAll(filesOnly);
            if (lastElement) {
                replaceAll(thisPrefix, Pattern.compile("└─"), "  ");
                lastElement = false;
            }
            replaceAll(thisPrefix, Pattern.compile("├─"), "│ ");
            thisPrefix.append("├─ ");

            for ( int row = 0; row < directories.size(); row++ ) {
                if (row == directories.size() - 1) {
                    replaceAll(thisPrefix, Pattern.compile("├─"), "└─");
                    lastElement = true;
                }
                showDir(directories.get(row), sb, thisPrefix, lastElement, parentDir);
            }
        }
        return sb;
    }

    public static void replaceAll(StringBuilder sb, Pattern pattern, String replacement) {
        Matcher m = pattern.matcher(sb);
        int start = 0;
        while (m.find(start)) {
            sb.replace(m.start(), m.end(), replacement);
            start = m.start() + replacement.length();
        }
    }

    private static long getFolderSize(File folder) {
        long length = 0;
        File[] files = folder.listFiles();
        int count = files.length;
        for ( int i = 0; i < count; i++ ) {
            if (files[i].isFile()) {
                length += files[i].length();
            } else {
                length += getFolderSize(files[i]);
            }
        }
        return length;
    }
}


