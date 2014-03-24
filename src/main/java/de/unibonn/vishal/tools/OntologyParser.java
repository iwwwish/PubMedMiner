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

import au.com.bytecode.opencsv.CSVReader;
import de.unibonn.vishal.namedentities.OntologyTerm;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vishal Siramshetty <srmshtty[at]gmail.com>
 */
public class OntologyParser {

    /**
     * A List of ChEBI ontology terms
     */
    public static List<OntologyTerm> CHEBI_ONTOLOGY;

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
                    String synonyms = row[2];
                    String[] syn = synonyms.split(", ");
                    if (!Arrays.asList(syn).isEmpty()) {
                        term.setSynonyms(Arrays.asList(syn));
                    } else {
                        term.setSynonyms(null);
                    }
                    CHEBI_ONTOLOGY.add(term);
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
            System.out.println(CHEBI_ONTOLOGY.size());
        } catch (IOException ex) {
            Logger.getLogger(OntologyParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
