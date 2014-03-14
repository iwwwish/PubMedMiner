/*
 * Copyright (C) 2013 Vishal Siramshetty <vishal[at]ebi.ac.uk>
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
package de.unibonn.vishal.utils;

import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Vishal Siramshetty <vishal[at]ebi.ac.uk>
 */
public class NERUtility {

    /**
     * Set of operations to be performed on Characters
     */
    public static class Characters {

        /**
         * Returns true if a character is whitespace or false in case not.
         *
         * @param charCode
         * @return
         */
        public static boolean isWhitespace(char charCode) {
            return Character.isWhitespace(charCode)
                    || Character.getType(charCode) == Character.SPACE_SEPARATOR;
        }

        /**
         * Returns true if a character is a digit.
         *
         * @param charCode
         * @return
         */
        public static boolean isDigit(char charCode) {
            return Character.isDigit(charCode);
        }

        /**
         * Returns true if a character is an alphabet.
         *
         * @param charCode
         * @return
         */
        public static boolean isAlphabet(char charCode) {
            return Character.isAlphabetic(charCode);
        }

        private Characters() {
        }
    }

    /**
     * Set of operations to be performed on Strings
     */
    public static class Strings {

        /**
         * Returns the smallest of 3 integers.
         *
         * @param a
         * @param b
         * @param c
         * @return
         */
        private static int minimum(int a, int b, int c) {
            return Math.min(Math.min(a, b), c);
        }

        /**
         * Prints each String element of an ArrayList in a new line.
         *
         * @param list
         */
        public static void printArrayList(List<String> list) {
            for (String s : list) {
                System.out.println(s);
            }
        }

        /**
         * Removes duplicates from a list of strings
         *
         * @param list
         */
        public static void removeDuplicates(List<String> list) {
            HashSet set = new HashSet(list);
            list.clear();
            list.addAll(set);
        }

        /**
         * Returns a word with its first character in Capital Case
         *
         * @param string
         * @return
         */
        public static String toTitleCase(String string) {

            return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
        }

        private Strings() {

        }
    }

}
