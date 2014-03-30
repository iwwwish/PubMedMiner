/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibonn.vishal.tools;

import java.awt.Canvas;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author vishal
 */
public class SimpleWebBrowser {

    public static void openHtmlPage(String htmlFilePath) throws IOException {
        File htmlFile = new File(htmlFilePath);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }
    
}
