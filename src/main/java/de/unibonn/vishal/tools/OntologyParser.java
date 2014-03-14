/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.unibonn.vishal.tools;

import au.com.bytecode.opencsv.CSVReader;
import de.unibonn.vishal.namedentities.OntologyTerm;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vishal
 */
public class OntologyParser {

    /**
     * A List of ChEBI ontology terms
     */
    private static List<OntologyTerm> CHEBI_ONTOLOGY;

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
            CHEBI_ONTOLOGY = new ArrayList<>();
            List<String[]> content = csvReader.readAll();
            for (Object object : content) {
                row = (String[]) object;

                // populating the ontology map
                if (row[0] != null && row[1] != null) {
                    OntologyTerm term = new OntologyTerm();
                    term.setIdentifier(row[0]);
                    term.setName(row[1]);
                    CHEBI_ONTOLOGY.add(term);
                    System.out.println(term.toString());
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
            System.out.println(Integer.MAX_VALUE);
        } catch (IOException ex) {
            Logger.getLogger(OntologyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
