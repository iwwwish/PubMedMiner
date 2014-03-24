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

import de.unibonn.vishal.utils.NERUtility;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class POSTagger {

    /**
     * A list of different types of NOuns
     */
    public static final List<String> nouns = Arrays.asList("NNP", "NNS", "NN", "NNPS");
    private static final MaxentTagger tagger = new MaxentTagger("edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");

    /**
     * Returns Parts Of Speech tagged text when provided with normal text
     *
     * @param sentence
     * @return POS tagged text
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static String getPOSTaggedAbstract(String sentence) throws IOException, ClassNotFoundException {

        return tagger.tagString(sentence);
    }

    /**
     * Returns the exact Part Of Speech associated with a tagged word
     *
     * @param posTaggedWord
     * @return POS
     */
    public static String getPOSTag(String posTaggedWord) {
        int p = posTaggedWord.indexOf("_");
        posTaggedWord = posTaggedWord.substring(p + 1, posTaggedWord.length());

        return posTaggedWord;
    }

    /**
     * Returns the word from the tagged word
     *
     * @param posTaggedWords
     * @return words without POS tags
     */
    public static List<String> getUntaggedWords(List<String> posTaggedWords) {

        List<String> untaggedWords = new ArrayList<>();
        for (String posTaggedWord : posTaggedWords) {
            int p = posTaggedWord.indexOf("_");
            untaggedWords.add(posTaggedWord.substring(0, p));
        }
        return untaggedWords;
    }

    /**
     * Returns the word from the tagged word
     *
     * @param posTaggedWord
     * @return word without POS tag
     */
    public static String getWord(String posTaggedWord) {

        int p = posTaggedWord.indexOf("_");
        posTaggedWord = posTaggedWord.substring(0, p);

        return posTaggedWord;
    }

    /**
     * Returns a list of trimmed single word tokens by splitting an input
     * sentence.
     *
     * @param sentence
     * @return List of trimmed single words
     */
    public static List<String> getMonogramTokens(String sentence) {
        List<String> monograms = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(sentence);
        while (st.hasMoreTokens()) {
            //System.out.println(st.nextToken());
            String token = st.nextToken();
            Character end = token.charAt(token.length() - 1);
            if (!AbstractParser.PUNCTUATIONS.contains(String.valueOf(end))) {
                monograms.add(token);
            } else {
                token = token.substring(0, token.length() - 1);
                monograms.add(token);
            }

        }
        return monograms;
    }

    /**
     * Returns a list of Noun phrases present in the tagged words
     *
     * @param taggedMonograms
     * @return Noun phrases
     */
    public static List<String> getNounPhrases(List<String> taggedMonograms) {
        List<String> nounPhrases = new ArrayList<>();
        StringBuilder nounSeq = new StringBuilder();
        int c = 0;
        for (String monogram : taggedMonograms) {
            String pos = getPOSTag(monogram);
            if (nouns.contains(pos)) {
                c++;
                nounSeq.append(getWord(monogram)).append(" ");
            } else {
                if (c >= 1) {
                    nounPhrases.add(nounSeq.toString().trim());
                }
                nounSeq = new StringBuilder();
                c = 0;
            }
        }

        NERUtility.Strings.removeDuplicates(nounPhrases);

        return nounPhrases;
    }

    /**
     * Returns a list of Proper Noun present in the tagged words
     *
     * @param taggedMonograms
     * @return Proper Nouns
     */
    public static List<String> getProperNouns(List<String> taggedMonograms) {
        List<String> nounPhrases = new ArrayList<>();
        StringBuilder nounSeq = new StringBuilder();
        int c = 0;
        for (String monogram : taggedMonograms) {
            String pos = getPOSTag(monogram);
            if (pos.contains("NNP")) {
                c++;
                nounSeq.append(getWord(monogram)).append(" ");
            } else {
                if (c >= 1) {
                    nounPhrases.add(nounSeq.toString().trim());
                }
                nounSeq = new StringBuilder();
                c = 0;
            }
        }

        return nounPhrases;

    }
}
