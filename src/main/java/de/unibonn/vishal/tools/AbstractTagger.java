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

import de.unibonn.vishal.pubmed.PubMedAbstract;
import au.com.bytecode.opencsv.CSVReader;
import static de.unibonn.vishal.tools.POSTagger.getNounPhrases;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cam.ch.wwmm.oscar.Oscar;
import uk.ac.cam.ch.wwmm.oscar.document.NamedEntity;

/**
 *
 * @author Vishal Siramshetty <srmshtty[at]gmail.com>
 */
public class AbstractTagger extends PubMedAbstract {

    /**
     * Different types of operations
     */
    public static enum OPERATION {

        /**
         * Named Entity Recognition
         */
        NER,
        /**
         * Co-occurrence Analysis
         */
        COA;
    }

    private TreeMap<String, String> roles;
    private List<PubMedAbstract> abstracts;

    /**
     * A constructor for the class AbstractTagger
     *
     * @param abstracts
     */
    public AbstractTagger(List<PubMedAbstract> abstracts) {

        this.abstracts = abstracts;
    }

    /**
     * Abstract is tagged with named entities
     *
     */
    public void tagNamedEntities() {
        Oscar oscar = new Oscar();
        for (PubMedAbstract abs : abstracts) {
            String absText = abs.getAbstractText();
            List<NamedEntity> entities = oscar.findNamedEntities(absText);
            String absT = absText.replaceAll("\\+", "#").replaceAll("\\(", "@").replaceAll("\\)", "~");
            for (NamedEntity entity : entities) {
                if ("CM".equals(entity.getType().toString())) {
                    String e = entity.getSurface();
                    e = e.replaceAll("\\+", "#").replaceAll("\\(", "@").replaceAll("\\)", "~");
                    absT = absT.replaceAll(e, "<font style=\"background-color: yellow\">" + e + "</font>");
                }
            }
            absText = absT.replaceAll("#", "+").replaceAll("@", "(").replaceAll("~", ")");
            System.out.println("Final: " + absText);
            abs.setAbstractText(absText);
        }
    }

    /**
     * Abstract is tagged with ChEBI roles
     *
     */
    public void tagChebiRoles() {
        loadChebiRoles();
        for (Map.Entry<String, String> role : roles.entrySet()) {
            String role_id = role.getKey();
            String role_name = role.getValue();
            for (PubMedAbstract abs : abstracts) {
                try {
                    String absText = abs.getAbstractText();
                    List<String> tokens = AbstractParser.getMonogramTokens(POSTagger.getPOSTaggedAbstract(absText));
                    List<String> np = getNounPhrases(tokens);
                    for (String phrase : np) {

                        if (phrase.equalsIgnoreCase(role_name) || phrase.contains(role_name)) {
                            System.out.println(phrase);
                            String chebi_url = "https://www.ebi.ac.uk/chebi/advancedSearchFT.do?searchString=" + role_id;
                            absText = absText.replaceFirst(phrase, "<a href=" + chebi_url + ">" + phrase + "</a>");
                        }
                    }
                    abs.setAbstractText(absText);
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(AbstractTagger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private void loadChebiRoles() {
        try {
            String[] row;
            String csvFilename = "/home/vishal/NetBeansProjects/PubMedMiner-master/resources/chebi_roles.csv";
            CSVReader csvReader = new CSVReader(new FileReader(csvFilename));

            // new TreeMap<ChEBI ID,ChEBI role>()
            roles = new TreeMap<>();
            List<String[]> content = csvReader.readAll();
            for (Object object : content) {
                row = (String[]) object;

                // populating the roles map
                if (row[0] != null && row[1] != null) {
                    roles.put(row[0], row[1]);
                    //System.out.println(row[0] + " " + row[1]);
                } else {
                    break;
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AbstractTagger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AbstractTagger.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
