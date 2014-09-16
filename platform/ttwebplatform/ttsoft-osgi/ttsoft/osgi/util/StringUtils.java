package ttsoft.osgi.util;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.text.StrBuilder;

public class StringUtils {
	public static final String EMPTY = "";
	
	public static String join(Iterator iterator, String separator) {

        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(first);
        }

        // two or more elements
        StrBuilder buf = new StrBuilder(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }
	
	public static String join(Collection collection, String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }
	
	
	public static boolean endsWith(String str, String suffix) {
        return endsWith(str, suffix, false);
    }
	
	private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
        if (str == null || suffix == null) {
            return (str == null && suffix == null);
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        int strOffset = str.length() - suffix.length();
        return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
    }
}
