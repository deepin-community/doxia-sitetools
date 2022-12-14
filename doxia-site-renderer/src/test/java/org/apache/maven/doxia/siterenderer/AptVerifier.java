package org.apache.maven.doxia.siterenderer;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlBold;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlHeading2;
import com.gargoylesoftware.htmlunit.html.HtmlHeading3;
import com.gargoylesoftware.htmlunit.html.HtmlItalic;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlTeletype;

import java.util.Iterator;


/**
 * Verifies apt transformations.
 *
 * @author ltheussl
 * @version $Id: AptVerifier.java 1604604 2014-06-22 15:36:56Z hboutemy $
 */
public class AptVerifier
    extends AbstractVerifier
{
    /** {@inheritDoc} */
    public void verify( String file )
            throws Exception
    {
        HtmlPage page = htmlPage( file );
        assertNotNull( page );

        HtmlElement element = page.getHtmlElementById( "contentBox" );
        assertNotNull( element );
        HtmlDivision division = (HtmlDivision) element;
        assertNotNull( division );

        Iterator<HtmlElement> elementIterator = division.getHtmlElementDescendants().iterator();

        // ----------------------------------------------------------------------
        //
        // ----------------------------------------------------------------------

        HtmlDivision div = (HtmlDivision) elementIterator.next();
        assertEquals( "section", div.getAttribute( "class" ) );

        HtmlHeading2 h2 = (HtmlHeading2) elementIterator.next();
        assertNotNull( h2 );
        assertEquals( "Links", h2.asText().trim() );

        HtmlAnchor a = (HtmlAnchor) elementIterator.next();
        assertEquals( "Links", a.getAttribute( "name" ) );

        HtmlParagraph p = (HtmlParagraph) elementIterator.next();
        assertNotNull( p );

        // Expected log: [APT Parser] Ambiguous link: 'cdc.html'. If this is a local link, prepend "./"!
        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "Anchor", a.getAttribute( "name" ) );
        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "cdc.html", a.getAttribute( "name" ) );

        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "#Anchor", a.getAttribute( "href" ) );
        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "#Anchor", a.getAttribute( "href" ) );

        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "Anchor_with_space", a.getAttribute( "name" ) );
        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "#Anchor_with_space", a.getAttribute( "href" ) );

        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "http://maven.apache.org/", a.getAttribute( "href" ) );
        assertEquals( "externalLink", a.getAttribute( "class" ) );
        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "http://maven.apache.org/", a.getAttribute( "href" ) );
        assertEquals( "externalLink", a.getAttribute( "class" ) );

        // Expected log: [APT Parser] Ambiguous link: 'cdc.html'. If this is a local link, prepend "./"!
        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "./cdc.html", a.getAttribute( "href" ) );
        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "#cdc.html", a.getAttribute( "href" ) );

        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "/index.html", a.getAttribute( "href" ) );

        div = (HtmlDivision) elementIterator.next();
        assertEquals( "section", div.getAttribute( "class" ) );

        h2 = (HtmlHeading2) elementIterator.next();
        assertNotNull( h2 );
        // Note: htmlunit strips the white space, actual result is ok
        assertEquals( "Section formatting: italicboldmono", h2.asText().trim() );

        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "Section_formatting:_italic_bold_mono", a.getAttribute( "name" ) );

        HtmlItalic italic = (HtmlItalic) elementIterator.next();
        assertEquals( "i", italic.getTagName() );
        assertEquals( "italic", italic.asText().trim() );

        HtmlBold bold = (HtmlBold) elementIterator.next();
        assertEquals( "b", bold.getTagName() );
        assertEquals( "bold", bold.asText().trim() );

        HtmlTeletype tt = (HtmlTeletype) elementIterator.next();
        assertEquals( "tt", tt.getTagName() );
        assertEquals( "mono", tt.asText().trim() );

        div = (HtmlDivision) elementIterator.next();
        assertEquals( "section", div.getAttribute( "class" ) );

        HtmlHeading3 h3 = (HtmlHeading3) elementIterator.next();
        assertNotNull( h3 );
        // Note: htmlunit strips the white space, actual result is ok
        assertEquals( "SubSection formatting: italicboldmono", h3.asText().trim() );

        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "SubSection_formatting:_italic_bold_mono", a.getAttribute( "name" ) );

        italic = (HtmlItalic) elementIterator.next();
        assertEquals( "i", italic.getTagName() );
        assertEquals( "italic", italic.asText().trim() );

        bold = (HtmlBold) elementIterator.next();
        assertEquals( "b", bold.getTagName() );
        assertEquals( "bold", bold.asText().trim() );

        tt = (HtmlTeletype) elementIterator.next();
        assertEquals( "tt", tt.getTagName() );
        assertEquals( "mono", tt.asText().trim() );

        p = (HtmlParagraph) elementIterator.next();
        assertNotNull( p );

        italic = (HtmlItalic) elementIterator.next();
        assertEquals( "i", italic.getTagName() );
        assertEquals( "italic", italic.asText().trim() );

        bold = (HtmlBold) elementIterator.next();
        assertEquals( "b", bold.getTagName() );
        assertEquals( "bold", bold.asText().trim() );

        tt = (HtmlTeletype) elementIterator.next();
        assertEquals( "tt", tt.getTagName() );
        assertEquals( "mono", tt.asText().trim() );

        div = (HtmlDivision) elementIterator.next();
        assertEquals( "section", div.getAttribute( "class" ) );

        h2 = (HtmlHeading2) elementIterator.next();
        assertNotNull( h2 );
        assertEquals( "No Default Anchor in Section Title with Explicit Anchor", h2.asText().trim() );
        a = (HtmlAnchor) elementIterator.next();
        assertEquals( "No_Default_Anchor_in_Section_Title_with_Explicit_Anchor", a.getAttribute( "name" ) );

        div = (HtmlDivision) elementIterator.next();
        assertEquals( "section", div.getAttribute( "class" ) );
    }
}
