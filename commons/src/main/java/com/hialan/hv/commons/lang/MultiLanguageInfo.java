package com.hialan.hv.commons.lang;

import java.util.Map;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 24/5/13
 *         Time: 3:19 PM
 */
public interface MultiLanguageInfo<IF> {

    public Map<Language, IF> getInfoMap();

    public String getDefaultName();
}
