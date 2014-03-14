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

/**
 *
 * @author Vishal Siramshetty <srmshtty[at]gmail.com>
 */
public class PubMedRecord {

    /**
     * Unique identifier for an Abstract
     */
    public Integer PMID;
    /**
     * Title of Abstract
     */
    protected String Title;
    /**
     * Year of Publication
     *
     */
    protected Integer Year;
    /**
     * Complete citation
     */
    protected String Citation;
    /**
     * Text of the Abstract
     */
    protected String Abstract;

    @Override
    public String toString() {
        return PMID + "\t" + Year + "\t" + Title + "\t" + getCitation() + "\t" + Abstract;
    }

    /**
     * GETTERS
     */
    /**
     * Returns the Unique identifier for an Abstract
     *
     * @return PMID
     */
    public Integer getPubMedID() {
        return PMID;
    }

    /**
     * Returns the Year of Publication
     *
     * @return Year
     */
    public int getYear() {
        return Year;
    }

    /**
     * Returns the Title of Abstract
     *
     * @return Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Returns the Complete citation
     *
     * @return Citation
     */
    public String getCitation() {
        return Citation;
    }

    /**
     * Returns the Text of Abstract
     *
     * @return Abstract Text
     */
    public String getAbstract() {
        return Abstract;
    }
}
