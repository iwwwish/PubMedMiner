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
package de.unibonn.vishal.pubmed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.axis2.AxisFault;
import org.xml.sax.SAXException;

/**
 *
 * @author Vishal Siramshetty <srmshtty[at]gmail.com>
 */
public class PubMedUtils {

    /**
     * Returns a list of PubMed Abstracts resulted from a search with the given
     * query word.
     *
     * @param query - the actual query word
     * @param maxNumResults - a numerical limit to restrict the results
     * @return list of PubMed Abstracts
     * @throws AxisFault
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static List<PubMedAbstract> getPubMedAbstracts(String query, int maxNumResults) throws AxisFault, IOException, ParserConfigurationException, SAXException {

        PubmedSearch search = new PubmedSearch();
        List<Integer> ids = search.getPubMedIDs(query, maxNumResults);
        PubMedFetcher fetcher = new PubMedFetcher();
        List<PubMedRecord> records = fetcher.getPubMedRecordForIDs(ids);

        List<PubMedAbstract> abstracts = new ArrayList<>();
        for (PubMedRecord record : records) {
            PubMedAbstract abs = new PubMedAbstract();

            if (record.getAbstract().trim().length() > 3) {
                abs.setAbstractText(record.getAbstract());
            } else {
                abs.setAbstractText("Not Available");
            }
            abs.setTitle(record.getTitle());
            abs.setPMID(record.PMID);
            if (record.getYear() != -1) {
                abs.setYear(String.valueOf(record.getYear()));
            } else {
                abs.setYear("Not Available");
            }

            abstracts.add(abs);
        }

        return abstracts;

    }

}
