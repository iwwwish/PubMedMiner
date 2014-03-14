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
public class PubMedAbstract {

    private int PMID;
    private String Title;
    private String AbstractText;
    private String CompleteAbstract;
    private String Year;

    /**
     * Getters
     */
    /**
     * Returns the PMID of the Abstract
     *
     * @return PMID
     */
    public int getPMID() {
        return PMID;
    }

    /**
     * Returns the title of the Abstract
     *
     * @return Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * Returns the actual text of the Abstract
     *
     * @return Abstract text
     */
    public String getAbstractText() {
        return AbstractText;
    }

    /**
     * Returns the year of publication of the Abstract
     *
     * @return Year of publication
     */
    public String getYear() {
        return Year;
    }

    /**
     * Returns the complete PubMed Abstract
     *
     * @return PubMed Abstract
     */
    public String getCompleteAbstract() {
        CompleteAbstract = "<html>" + "<b>Title</b>:" + "<p align=\"justify\">" + getTitle() + "</p>" + "<br>" + "<b>Year of Publication</b>:" + "<p align=\"justify\">" + getYear() + "</p>" + "<br>" + "<b>Abstract</b>:" + "<p align=\"justify\">" + getAbstractText() + "</p></html>";
        return CompleteAbstract;
    }

    /**
     * Setters
     *
     * @param PMID
     */
    public void setPMID(int PMID) {
        this.PMID = PMID;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setAbstractText(String AbstractText) {
        this.AbstractText = AbstractText;
    }

    public void setYear(String year) {
        this.Year = year;
    }

}
