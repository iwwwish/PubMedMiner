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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Vishal Siramshetty <srmshtty[at]gmail.com>
 */
public class AbstractParser {

    /**
     * A list of possible STOP WORDS in English.
     */
    public static List<String> STOP_WORDS = new ArrayList<>();
    /**
     * A regular expression that splits a string into sentences.
     */
    public static final String SENTENCE_SPLITTER = "(?<=[.?!])\\s+(?=[a-zA-Z])";
    /**
     * A list of punctuation symbols.
     */
    public static final List<String> PUNCTUATIONS = Arrays.asList(":", ";", ",", ".", "'", "?", "\"", "-", "--", "!", "/");
    /**
     * A list of symbols that separate sentences.
     */
    public static final List<String> SPLITTING_SYMBOLS = Arrays.asList(".", "?", "!", "\"");
    /**
     * A list of tokens or words after which a split should not be done while
     * splitting a sentence.
     */
    public static final List<String> NOT_AFTER = Arrays.asList("Fig", "al", "i.e", "ie", "eg", "e.g", "ref", "Dr", "Prof", "Sir");

    /**
     * Loads the Stop Words that can be used to filter the words not needed in a
     * sentence or paragraph.
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void loadStopWords() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/vishalkpp/NetBeansProjects/CheMIENER/Data/StopWords.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            STOP_WORDS.add(line);
        }
    }

    /**
     * Returns the list of sentences in an abstract or in general in a text
     * paragraph.
     *
     * @param abstractText
     * @return
     */
    public static List<String> getSentences(String abstractText) {

        String[] splits = abstractText.split(AbstractParser.SENTENCE_SPLITTER);
        List<String> sentences = Arrays.asList(splits);

        return sentences;
    }

    /**
     * Returns a list of trimmed single word tokens by splitting an input
     * sentence.
     *
     * @param sentence
     * @return
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
     * Returns a list of trimmed single word tokens by splitting an input
     * sentence at a specified delimiter.
     *
     * @param sentence
     * @param delimiter
     * @return
     */
    public static List<String> getMonogramTokens(String sentence, String delimiter) {
        List<String> monograms = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(sentence, delimiter);
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
     * Returns a list of bigram tokens (consecutive word pairs) for the given
     * list of monogram tokens
     *
     * @param monogramTokens
     * @return bigram tokens
     */
    public static List<String> getBigramTokens(List<String> monogramTokens) {

        List<String> bigrams = new ArrayList<>();
        StringBuilder bigram = new StringBuilder();
        for (int i = 0; i < monogramTokens.size() - 1; i++) {
            bigram.append(monogramTokens.get(i)).append(" ").append(monogramTokens.get(i + 1));
            bigrams.add(bigram.toString().trim());
            bigram = new StringBuilder();
        }

        return bigrams;
    }

    /**
     * Returns the list of bigram tokens by removing those tokens that contain
     * at least one stop word.
     *
     * @param bigramTokens
     * @return absolute bigram tokens
     * @throws IOException
     */
    public static List<String> getAbsoluteBigramTokens(List<String> bigramTokens) throws IOException {
        loadStopWords();
        List<String> copy = new ArrayList<>(bigramTokens);
        for (String bigram : copy) {
            String[] toks = bigram.split(" ");
            String first = toks[0];
            String second = toks[1];
            if (STOP_WORDS.contains(first.toLowerCase()) || STOP_WORDS.contains(second.toLowerCase())) {
                bigramTokens.remove(bigram);
            }
        }

        return bigramTokens;
    }

    /**
     * Removes the stop words when provided with a list of monogram tokens
     *
     * @param monogramTokens
     * @return filtered monogram tokens
     * @throws IOException
     */
    public static List<String> removeStopWords(List<String> monogramTokens) throws IOException {

        // load the stop words list
        loadStopWords();
        // create a copy of the input list of monograms
        List<String> copy = new ArrayList<>(monogramTokens);
        for (String monogram : monogramTokens) {
            if (STOP_WORDS.contains(monogram)) {
                copy.remove(monogram);
            }
        }

        return copy;
    }

}
