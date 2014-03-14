package de.unibonn.vishal.pubmed;

import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <renaud.richardet[at]epfl.ch>
 */
public class PubmedSearch {

    private static final Logger LOG = LoggerFactory.getLogger(PubmedSearch.class);
    private EUtilsServiceStub service;

    public PubmedSearch() throws AxisFault {
        init();
    }

    private void init() throws AxisFault {
        service = new EUtilsServiceStub();
    }

    /**
     * @param query
     * @return a list of PMIDs for the input query
     * @throws java.rmi.RemoteException
     */
    public List<Integer> getPubMedIDs(String query) throws RemoteException {

        EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
        req.setDb("pubmed");
        req.setEmail("gmail@gmail.com");
        req.setTerm(query);
        req.setRetStart("0");
        req.setRetMax(Integer.MAX_VALUE + "");
        EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
        int count = new Integer(res.getCount());
        LOG.debug("Found {} ids for query '{}'", count, query);

        List<Integer> articleIds = new ArrayList<>();
        String[] idList = res.getIdList().getId();
        for (String id : idList) {
            articleIds.add(new Integer(id));
        }
        assert (count == articleIds.size()) : "result counts should match, "
                + articleIds.size() + ":" + count;
        return articleIds;
    }

    public List<Integer> getPubMedIDs(String query, int maxNumResults) throws RemoteException {

        EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
        req.setDb("pubmed");
        req.setEmail("gmail@gmail.com");
        req.setTerm(query);
        req.setRetStart("0");
        req.setRetMax(maxNumResults + "");
        EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
        int count = new Integer(res.getCount());
        LOG.debug("Found {} ids for query '{}'", count, query);

        List<Integer> articleIds = new ArrayList<>();
        String[] idList = res.getIdList().getId();
        for (String id : idList) {
            articleIds.add(new Integer(id));
        }
        assert (count == articleIds.size()) : "result counts should match, "
                + articleIds.size() + ":" + count;
        return articleIds;
    }
}
