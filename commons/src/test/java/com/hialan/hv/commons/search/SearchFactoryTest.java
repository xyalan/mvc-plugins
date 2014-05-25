package com.hialan.hv.commons.search;

import com.hialan.hv.commons.AbstractTest;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 24/4/13
 * Time: 1:49 PM
 */
public class SearchFactoryTest extends AbstractTest {

    private static final String SEARCH_STR = "[name,yubo,like],[telephone,13810770810,=],[createDate,2013-04-24 00:00,>=],[interface_,1;2;3,in]";

    @Test
    public void testGetSearchTeam() throws Exception {
        SearchBean[] searchBeans = SearchFactory.getSearchTeam(SEARCH_STR);
        assertNotNull(searchBeans);
        assertEquals(4, searchBeans.length);
        assertArrayEquals(searchBeans, new SearchBean[]{
                new SearchBean("name", "yubo", "like"),
                new SearchBean("telephone", "13810770810", "="),
                new SearchBean("createDate", "2013-04-24 00:00", ">="),
                new SearchBean("interface_", "1;2;3", "in"),
        });
    }
}
