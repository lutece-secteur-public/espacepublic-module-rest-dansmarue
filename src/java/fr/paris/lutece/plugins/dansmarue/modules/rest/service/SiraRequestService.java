/*
 * Copyright (c) 2002-2011, Mairie de Paris
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
package fr.paris.lutece.plugins.dansmarue.modules.rest.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.dansmarue.modules.rest.util.constants.SiraRestConstants;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.signrequest.security.Sha1HashService;

/**
 *
 * SiraRequestService
 *
 */
public class SiraRequestService
{
    private static Sha1HashService _sha1HashService;

    /**
     * setter
     *
     * @param sha1HashService
     *            the service
     */
    public static void setSha1HashService( Sha1HashService sha1HashService )
    {
        _sha1HashService = sha1HashService;
    }

    /**
     * Check if Request is authenticated.
     *
     * @param request
     *            the request
     * @param strJSONStream
     *           the json stream
     * @return true if request is authenticated
     */
    public boolean isRequestAuthenticated( HttpServletRequest request, String strJSONStream )
    {
        int nSignRequestActived = AppPropertiesService.getPropertyInt( SiraRestConstants.PROPERTY_ACTIVATION_SIGNREQUEST, 0 );

        if ( nSignRequestActived == 1 )
        {
            String strHash1 = request.getHeader( SiraRestConstants.HEADER_X_APP_REQUEST_SIGNATURE );

            // no signature
            if ( StringUtils.isBlank( strHash1 ) )
            {
                return false;
            }

            String strHash2 = buildSignature( request, strJSONStream );

            return strHash1.equals( strHash2 );
        } else
        {
            return true;
        }
    }

    /**
     * Create a signature.
     *
     * @param request
     *            The http request
     * @param strJSONStream
     *            the json stream
     * @return A signature as an Hexadecimal Hash
     */
    public String buildSignature( HttpServletRequest request, String strJSONStream )
    {
        StringBuilder sb = new StringBuilder( );
        sb.append( AppPropertiesService.getProperty( SiraRestConstants.PROPERTY_PRIVATE_KEY_ANDROID_API ) );

        if ( StringUtils.isNotBlank( strJSONStream ) )
        {
            sb.append( strJSONStream );
        }

        return _sha1HashService.getHash( sb.toString( ) );
    }
}
