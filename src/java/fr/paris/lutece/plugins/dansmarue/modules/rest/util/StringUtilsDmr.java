package fr.paris.lutece.plugins.dansmarue.modules.rest.util;

import org.apache.commons.lang.ArrayUtils;

public final class StringUtilsDmr
{
    public static boolean endsWithAny( String string, String[] searchStrings )
    {
        if (!org.apache.commons.lang3.StringUtils.isEmpty( string ) && !ArrayUtils.isEmpty( searchStrings ) )
        {
            for ( int i = 0; i < searchStrings.length; ++i )
            {
                String searchString = searchStrings[i];
                if ( org.apache.commons.lang3.StringUtils.endsWith( string, searchString ) )
                {
                    return true;
                }
            }

            return false;
        }
        else
        {
            return false;
        }
    }
}
