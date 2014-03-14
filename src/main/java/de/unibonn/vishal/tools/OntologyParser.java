/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibonn.vishal.tools;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vishal
 */
public class OntologyParser {

    /**
     * A TreeMap<ChEBI_ID,Name> of ChEBI ontology terms
     */
    private static TreeMap<String, String> chebiOntology;

    /**
     * A simple class that loads ChEBI ontology when needed
     */
    public static class Chebi {

        /**
         * loads the ChEBI ontology
         *
         * @throws java.io.FileNotFoundException
         */
        public static void loadOntology() throws FileNotFoundException, IOException {

            String[] row;
            String csvFilename = "resources/chebi_onto.csv";
            CSVReader csvReader = new CSVReader(new FileReader(csvFilename));

            // new TreeMap<ChEBI ID,Name>()
            chebiOntology = new TreeMap<>();
            List<String[]> content = csvReader.readAll();
            for (Object object : content) {
                row = (String[]) object;

                // populating the ontology map
                if (row[0] != null && row[1] != null) {
                    chebiOntology.put(row[0], row[1]);
                } else {
                    break;
                }

            }

        }

        private Chebi() {

        }

    }

    public static void main(String[] args) {
        try {
            OntologyParser.Chebi.loadOntology();
            //System.out.println(f.getAbsolutePath());
            //System.out.println(f.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(OntologyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
