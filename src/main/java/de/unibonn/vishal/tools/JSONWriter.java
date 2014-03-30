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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Vishal Siramshetty <srmshtty[at]gmail.com>
 */
public class JSONWriter {

    private HashMap<HashMap<String, List<String>>, Integer> CoOccurrenceMap;
    private String nodes;
    private String links;
    private String jsonTree;
    private String jsonMatrix;

    public JSONWriter(HashMap<HashMap<String, List<String>>, Integer> CoOccurrenceMap) {
        this.CoOccurrenceMap = CoOccurrenceMap;
    }

    private String getNodes() {
        return nodes;
    }

    private String getLinks() {
        return links;
    }

    public String getJsonTree() {
        return jsonTree;
    }

    public String getJsonMatrix() {
        return this.jsonMatrix;
    }

    public void createNodesAndLinks() {
        System.out.println("Creating nodes and links...");
        StringBuilder nodeB = new StringBuilder();
        int nodeCount = 0;

        HashMap<String, List<Integer>> linkMap = new HashMap<>();
        HashMap<String, List<Integer>> copyMap = new HashMap<>();

        for (Map.Entry<HashMap<String, List<String>>, Integer> abstrct : CoOccurrenceMap.entrySet()) {
            String PMID = String.valueOf(abstrct.getValue());

            HashMap<String, List<String>> sMap = abstrct.getKey();
            for (Map.Entry<String, List<String>> cos : sMap.entrySet()) {
                List<String> entities = cos.getValue();
                if (!entities.isEmpty()) {
                    for (String entity : entities) {
                        List<Integer> ids = new ArrayList<>();
                        if (!linkMap.containsKey(entity.toLowerCase())) {

                            nodeB.append("{\"name\":\"" + entity + "\",\"group\":" + PMID
                                    + "},\n");
                            entity = entity.toLowerCase();
                            ids.add(abstrct.getValue());

                            linkMap.put(entity.toLowerCase(), ids);
                            copyMap.put(entity.concat("_").concat(String.valueOf(nodeCount)), ids);
                            nodeCount++;
                        } else {

                            entity = entity.toLowerCase();
                            ids = linkMap.get(entity);
                            ids.add(abstrct.getValue());
                            Set<Integer> idSet = new HashSet<>(ids);
                            ids.clear();
                            ids.addAll(idSet);
                            linkMap.put(entity, ids);
                            copyMap.put(entity, ids);
                        }

                    }
                }
            }
        }
        nodes = nodeB.toString();
        nodes = nodes.substring(0, nodes.length() - 2);

        createUniqueLinks(copyMap);
    }

    private void createUniqueLinks(HashMap<String, List<Integer>> linkMap) {

        HashMap<Integer, List<String>> pmidMap = new HashMap<>();

        for (Map.Entry<String, List<Integer>> entry : linkMap.entrySet()) {
            List<Integer> PMIDs = entry.getValue();
            for (int pmid : PMIDs) {
                if (!pmidMap.containsKey(pmid)) {
                    List<String> entities = new ArrayList<>();
                    entities.add(entry.getKey());
                    pmidMap.put(pmid, entities);
                } else {
                    List<String> entities = pmidMap.get(pmid);
                    entities.add(entry.getKey());
                    pmidMap.put(pmid, entities);
                }
            }
        }

        HashMap<Integer, List<String>> copyMap = new HashMap<>(pmidMap);

        for (Map.Entry<Integer, List<String>> entry : pmidMap.entrySet()) {
            List<String> entities = entry.getValue();
            List<String> newEntities = new ArrayList<>(entities);

            for (String str : entities) {
                if (!str.contains("_")) {
                    newEntities.remove(str);
                }
            }
            copyMap.put(entry.getKey(), newEntities);
        }

        StringBuilder linkB = new StringBuilder();

        for (Map.Entry<Integer, List<String>> entry : copyMap.entrySet()) {
            List<Integer> ids = new ArrayList();
            List<String> names = entry.getValue();
            for (String name : names) {
                int id = Integer.parseInt(name.substring(name.indexOf("_") + 1, name.length()));
                ids.add(id);
            }
            for (int i = 0; i < ids.size(); i++) {
                for (int j = 0; j < ids.size(); j++) {
                    if (i != j) {
                        String source1 = String.valueOf(ids.get(i));
                        String source2 = String.valueOf(ids.get(j));
                        linkB.append("{\"source\":" + source1 + ",\"target\":" + source2 + ",\"value\":1},\n");
                    }
                }
            }
        }
        links = linkB.toString();
        links = links.substring(0, links.length() - 2);
    }

    public void writeJSONTree() {
        StringBuilder tree = new StringBuilder();
        if (!CoOccurrenceMap.isEmpty()) {

            tree.append("{\n"
                    + "  \"nodes\":[");
            tree.append(getNodes());
            tree.append("],\n"
                    + "    \"links\":[");
            tree.append(getLinks());
            tree.append("]\n"
                    + "}");
        }
        jsonTree = tree.toString();
    }

    public void writeJSONMatrix() {
        StringBuilder matrix = new StringBuilder();
        matrix.append("var miserables = {\n"
                + "  nodes:[");

        String nodeM = getNodes();
        nodeM = nodeM.replaceAll("\"name\"", "nodeName");
        nodeM = nodeM.replaceAll("\"group\"", "group");
        matrix.append(nodeM);

        matrix.append("],\n"
                + "    links:[");
        String linkM = getLinks();
        linkM = linkM.replaceAll("\"source\"", "source");
        linkM = linkM.replaceAll("\"target\"", "target");
        matrix.append(linkM);

        matrix.append("]\n"
                + "};");
        jsonMatrix = matrix.toString();
    }
}