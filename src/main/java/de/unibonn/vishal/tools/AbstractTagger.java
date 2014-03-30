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
import de.unibonn.vishal.namedentities.OntologyTerm;
import static de.unibonn.vishal.tools.POSTagger.getNounPhrases;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.cam.ch.wwmm.oscar.Oscar;
import uk.ac.cam.ch.wwmm.oscar.chemnamedict.entities.ChemicalStructure;
import uk.ac.cam.ch.wwmm.oscar.chemnamedict.entities.FormatType;
import uk.ac.cam.ch.wwmm.oscar.chemnamedict.entities.ResolvedNamedEntity;

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
        COA,
        /**
         * ChEBI Role tagging
         */
        CHEBI;
    }

    private TreeMap<String, String> roles;
    private List<PubMedAbstract> abstracts;
    private final Oscar oscar = new Oscar();

    /**
     * A constructor for the class AbstractTagger
     *
     * @param abstracts
     */
    public AbstractTagger(List<PubMedAbstract> abstracts) {

        this.abstracts = abstracts;
    }

    public List<String> getNamedEntitiesWithStructure(String inputText) {

        List<String> structreEntities = new ArrayList<>();
        List<ResolvedNamedEntity> entities = oscar.findAndResolveNamedEntities(inputText);
        for (ResolvedNamedEntity ne : entities) {
            ChemicalStructure inchi = ne.getFirstChemicalStructure(FormatType.INCHI);
            ChemicalStructure smile = ne.getFirstChemicalStructure(FormatType.SMILES);
            if (inchi != null || smile != null) {
                structreEntities.add(ne.getNamedEntity().getSurface());
            }
        }
        return structreEntities;
    }

    /**
     * Abstract is tagged with named entities
     *
     */
    public void tagNamedEntities() {
        //Oscar oscar = new Oscar();
        for (PubMedAbstract abs : abstracts) {
            String absText = abs.getAbstractText();
            List<String> entities = getNamedEntitiesWithStructure(absText);
            String absT = absText.replaceAll("\\+", "#").replaceAll("\\(", "@").replaceAll("\\)", "~");
            for (String e : entities) {
                e = e.replaceAll("\\+", "#").replaceAll("\\(", "@").replaceAll("\\)", "~");
                absT = absT.replaceAll("\\b" + e + "\\b", "<font style=\"background-color: yellow\">" + e + "</font>");
            }
            absText = absT.replaceAll("#", "+").replaceAll("@", "(").replaceAll("~", ")");
            //System.out.println("Final: " + absText);
            abs.setAbstractText(absText);
        }
    }

    public HashMap<HashMap<String, List<String>>, Integer> getCoAnalysisMap() {
        HashMap<HashMap<String, List<String>>, Integer> coMap = new HashMap();
        for (PubMedAbstract abs : abstracts) {
            String absText = abs.getAbstractText();
            HashMap<String, List<String>> occurrences = AbstractParser.getCoOccurrenceMap(absText);
            if (!occurrences.isEmpty()) {
                //for (Map.Entry<String, List<String>> entry : occurrences.entrySet()) {
                //  absText = absText.replace(entry.getKey(), "<u>" + entry.getKey() + "</u>");
                // abs.setAbstractText(absText);
                //  }
                coMap.put(occurrences, abs.getPMID());
            }

        }
        return coMap;
    }

    /**
     * Abstract is tagged with ChEBI roles
     *
     */
    public void tagChebiRoles() {
        //loadChebiRoles();
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

    public void tagChebiOntologyTerms() throws IOException {

        OntologyParser.Chebi.loadOntology();
        List<OntologyTerm> terms = OntologyParser.CHEBI_ONTOLOGY;
        if (!terms.isEmpty()) {
            // To Do
        }
    }

}
