package org.onecell.example.etc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

public class ClassLoaderCreator {
    private static final Logger LOG = LoggerFactory.getLogger(ClassLoaderCreator.class);
    String folderPath="./plugins/";

    public ClassLoaderCreator() {

    }

    public ClassLoaderCreator(String folderPath) {
        this.folderPath = folderPath;
    }

    public  ClassLoader create()
    {
        LOG.debug("start ClassLoaderCreator.create()");

        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        List<URL> urls = new LinkedList<>();

        for(File file:files)
        {
            try {
                String absolutePath = file.getAbsolutePath();
                URL url = new URL("jar:" + file.toURI().toURL() + "!/");
                urls.add(url);
                LOG.debug("load module :"+ url.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        URL[] objects = urls.toArray(new URL[0]);
        //URLClassLoader urlClassLoader = new URLClassLoader(objects,ClassLoader.getSystemClassLoader());
        URLClassLoader urlClassLoader = new URLClassLoader(objects,this.getClass().getClassLoader());
        LOG.debug("end ClassLoaderCreator.create()");
        return urlClassLoader;
    }

}
