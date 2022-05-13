/*
 * Copyright (c) 2002-2022, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.dansmarue.modules.rest.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.portal.service.util.AppLogService;

/**
 * DateUtils.
 */
public final class DateUtils
{

    /** The Constant FORMAT_DATE_1. */
    private static final String FORMAT_DATE_1 = "dd/MM/yyyy";

    /** The Constant FORMAT_DATE_2. */
    private static final String FORMAT_DATE_2 = "yyyy-MM-dd";

    /** The Constant FORMAT_HOUR. */
    private static final String FORMAT_HOUR = "HH:mm";

    /**
     * Costructor.
     */
    private DateUtils( )
    {
    }

    /**
     * Change date format.
     *
     * @param strDate
     *            the date
     * @return new format
     */
    public static String convertDate( String strDate )
    {
        SimpleDateFormat sfd = new SimpleDateFormat( FORMAT_DATE_1 );
        SimpleDateFormat sfd2 = new SimpleDateFormat( FORMAT_DATE_2 );
        String strDateConverted = StringUtils.EMPTY;

        try
        {
            Date date = sfd.parse( strDate );
            strDateConverted = sfd2.format( date );
        }
        catch( ParseException e )
        {
            AppLogService.error( e.getMessage( ), e );
        }

        return strDateConverted;
    }

    /**
     * Formats a date, to pattern HH:mm.
     *
     * @param hour
     *            date to convert.
     * @return date format
     */
    public static String convertHour( Date hour )
    {
        SimpleDateFormat sdf = new SimpleDateFormat( FORMAT_HOUR );
        String result = StringUtils.EMPTY;
        if ( null != hour )
        {
            result = sdf.format( hour );
        }
        return result;
    }
}
