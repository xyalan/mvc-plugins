package com.hialan.hv.hibernate3.search;

import com.hialan.hv.commons.search.SearchBean;
import com.hialan.hv.hibernate3.search.model.Customer;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 27/4/13
 * Time: 5:19 PM
 */
public class CriteriaSearchFactoryTest extends AbstractTest {

    @Test
    public void testAttachSearchBeanWithOne() {
        List<SearchBean> searchBeanList = new ArrayList<SearchBean>();

        SearchBean searchBean = new SearchBean();
        searchBean.setName("customerType.id");
        searchBean.setRelation("=");
        searchBean.setValue("1");

        searchBeanList.add(searchBean);

        DetachedCriteria criteria = CriteriaSearchFactory.generateCriteria(Customer.class, searchBeanList);

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "Customer");
        detachedCriteria.createAlias("Customer.customerType", "customerType");
        detachedCriteria.add(Restrictions.eq("customerType.id", 1L));

        assertEquals(detachedCriteria.toString(), criteria.toString());
    }

    @Test
    public void testAttachSearchBeanWithTwo() {
        List<SearchBean> searchBeanList = new ArrayList<SearchBean>();

        SearchBean searchBean = new SearchBean();
        searchBean.setName("loginUser.phones.number1");
        searchBean.setRelation("=");
        searchBean.setValue("13088921120");

        SearchBean searchBean1 = new SearchBean();
        searchBean1.setName("loginUser.phones.number2");
        searchBean1.setRelation("=");
        searchBean1.setValue("13088921121");

        searchBeanList.add(searchBean);
        searchBeanList.add(searchBean1);

        DetachedCriteria criteria = CriteriaSearchFactory.generateCriteria(Customer.class, searchBeanList);

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "Customer");
        detachedCriteria.createAlias("Customer.loginUser", "loginUser");
        detachedCriteria.createAlias("loginUser.phones", "phones");
        detachedCriteria.add(Restrictions.eq("phones.number1", 13088921120L));
        detachedCriteria.add(Restrictions.eq("phones.number2", 13088921121L));

        assertEquals(detachedCriteria.toString(), criteria.toString());
    }
}
