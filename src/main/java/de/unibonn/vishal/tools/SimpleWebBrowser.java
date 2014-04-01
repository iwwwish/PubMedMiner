/*
 * Copyright (C) 2014 Vishal Siramshetty <srmshtty[at]gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.unibonn.vishal.tools;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class SimpleWebBrowser {

    public static void openHtmlPage(String htmlFilePath) throws IOException {
        File htmlFile = new File(htmlFilePath);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }
}
