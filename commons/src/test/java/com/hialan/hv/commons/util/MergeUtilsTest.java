package com.hialan.hv.commons.util;

import com.hialan.hv.commons.AbstractTest;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 20/5/13
 *         Time: 10:50 AM
 */
public class MergeUtilsTest extends AbstractTest {

    @Test
    public void testMerge() throws Exception {

        List<String> strings = MergeUtils.merge(Arrays.asList("01", "001", "0001", "1"), new Merger<String, String>() {

            @Override
            public String getKey(String input) {
                return String.valueOf(NumberUtils.toInt(input));
            }

            @Override
            public String convert(String input) {
                return String.valueOf(NumberUtils.toInt(input));
            }

            @Override
            public void merge(String input, String target) {

            }

            @Override
            public boolean isMergeEnable(String s, String temp) {
                return NumberUtils.toInt(s) == NumberUtils.toInt(temp);
            }
        });

        assertNotNull(strings);
        assertEquals(1, strings.size());
        assertEquals("1", strings.get(0));
    }
}
