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
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlHeading2;
import com.gargoylesoftware.htmlunit.html.HtmlHeading3;
import com.gargoylesoftware.htmlunit.html.HtmlHeading4;
import com.gargoylesoftware.htmlunit.html.HtmlMeta;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlPreformattedText;

import java.util.Iterator;

/**
 * Verify the <code>site/xdoc/entityTest.xml</code>
 *
 * @author ltheussl
 * @version $Id: EntitiesVerifier.java 1737482 2016-04-02 09:56:25Z hboutemy $
 */
public class EntitiesVerifier
    extends AbstractVerifier
{
    /** {@inheritDoc} */
    public void verify( String file )
            throws Exception
    {
        HtmlPage page = htmlPage( file );
        assertNotNull( page );

        HtmlMeta author = (HtmlMeta) page.getElementsByName( "author" ).get( 0 );
        assertNotNull( author );
        assertTrue( author.toString().indexOf( "Ligature &#198;" ) > 0 );
        assertEquals( "Ligature \u00C6", author.getContentAttribute() );

        author = (HtmlMeta) page.getElementsByName( "author" ).get( 1 );
        assertNotNull( author );
        assertTrue( author.toString().indexOf( "Ampersand &amp;" ) > 0 );
        assertEquals( "Ampersand &", author.getContentAttribute() );

        author = (HtmlMeta) page.getElementsByName( "author" ).get( 2 );
        assertNotNull( author );
        assertTrue( author.toString().indexOf( "Less than &lt;" ) > 0 );
        assertEquals( "Less than <", author.getContentAttribute() );

        author = (HtmlMeta) page.getElementsByName( "author" ).get( 3 );
        assertNotNull( author );
        assertTrue( author.toString().indexOf( "Greater than &gt;" ) > 0 );
        assertEquals( "Greater than >", author.getContentAttribute() );

        author = (HtmlMeta) page.getElementsByName( "author" ).get( 4 );
        assertNotNull( author );
        assertTrue( author.getContentAttribute().equals( "Apostrophe '" ) );
        assertEquals( "Apostrophe '", author.getContentAttribute() );

        author = (HtmlMeta) page.getElementsByName( "author" ).get( 5 );
        assertNotNull( author );
        assertTrue( author.toString().indexOf( "Quote &quot;" ) > 0 );
        assertEquals( "Quote \"", author.getContentAttribute() );

        author = (HtmlMeta) page.getElementsByName( "author" ).get( 6 );
        assertNotNull( author );
        assertTrue( author.toString().indexOf( "test@email.com" ) > 0 );
        assertEquals( "test@email.com", author.getContentAttribute() );

        author = (HtmlMeta) page.getElementsByName( "author" ).get( 7 );
        assertNotNull( author );
        assertTrue( author.toString().indexOf( "test&#169;email.com" ) > 0 );
        assertEquals( "test\u00A9email.com", author.getContentAttribute() );

        HtmlElement element = page.getHtmlElementById( "contentBox" );
        assertNotNull( element );
        HtmlDivision division = (HtmlDivision) element;
        assertNotNull( division );

        Iterator<HtmlElement> elementIterator = division.getHtmlElementDescendants().iterator();

        // ----------------------------------------------------------------------
        //
        // ----------------------------------------------------------------------

        HtmlDivision div = (HtmlDivision) elementIterator.next();
        assertNotNull( div );
        assertEquals( "section", div.getAttribute( "class" ) );

        HtmlHeading2 h2 = (HtmlHeading2) elementIterator.next();
        assertNotNull( h2 );
        assertEquals( h2.asText().trim(), "section name with entities: '&' '\u0391' ' ' '\uD7ED'" );

        HtmlAnchor a = (HtmlAnchor) elementIterator.next();
        assertNotNull( a );
        assertEquals( "section_name_with_entities:____", a.getAttribute( "name" ) );

        div = (HtmlDivision) elementIterator.next();
        assertNotNull( div );
        assertEquals( "section", div.getAttribute( "class" ) );

        div = (HtmlDivision) elementIterator.next();
        assertNotNull( div );
        assertEquals( "section", div.getAttribute( "class" ) );

        HtmlHeading4 h4 = (HtmlHeading4) elementIterator.next();
        assertNotNull( h4 );
        assertEquals( "Entities", h4.asText().trim() );

        a = (HtmlAnchor) elementIterator.next();
        assertNotNull( a );
        assertEquals( "Entities", a.getAttribute( "name" ) );

        div = (HtmlDivision) elementIterator.next();

        HtmlHeading3 h3 = (HtmlHeading3) elementIterator.next();
        assertNotNull( h3 );
        assertEquals( "Generic Entities: '&' '<' '>' '\"' '''", h3.asText().trim() );

        a = (HtmlAnchor) elementIterator.next();

        HtmlParagraph p = (HtmlParagraph) elementIterator.next();
        assertNotNull( p );
        assertEquals( "'&' '<' '>' '\"' '''", p.asText().trim() );

        div = (HtmlDivision) elementIterator.next();

        h3 = (HtmlHeading3) elementIterator.next();
        assertNotNull( h3 );
        assertEquals( "Local Entities: '\u0391' '\u0392' '\u0393' '\uD7ED'", h3.asText().trim() );

        a = (HtmlAnchor) elementIterator.next();

        p = (HtmlParagraph) elementIterator.next();
        assertNotNull( p );
        assertEquals( "'\u0391' '\u0392' '\u0393' '\uD7ED\uD7ED' '\u0159\u0159' '\u0159'", p.asText().trim() );

        div = (HtmlDivision) elementIterator.next();

        h3 = (HtmlHeading3) elementIterator.next();
        assertNotNull( h3 );
        assertEquals( "DTD Entities: ' ' '\u00A1' '\u00A2'", h3.asText().trim() );

        a = (HtmlAnchor) elementIterator.next();

        p = (HtmlParagraph) elementIterator.next();
        assertNotNull( p );
        assertEquals( "' ' '\u00A1' '\u00A2'", p.asText().trim() );

        div = (HtmlDivision) elementIterator.next();
        assertNotNull( div );
        assertEquals( "section", div.getAttribute( "class" ) );

        h4 = (HtmlHeading4) elementIterator.next();
        assertNotNull( h4 );
        assertEquals( "CDATA", h4.asText().trim() );

        a = (HtmlAnchor) elementIterator.next();
        assertNotNull( a );
        assertEquals( "CDATA", a.getAttribute( "name" ) );

        div = (HtmlDivision) elementIterator.next();
        assertNotNull( div );
        assertEquals( "source", div.getAttribute( "class" ) );

        HtmlPreformattedText pre = (HtmlPreformattedText) elementIterator.next();
        assertNotNull( pre );
        assertEquals( "<project xmlns:ant=\"jelly:ant\">", pre.asText().trim() );

        p = (HtmlParagraph) elementIterator.next();
        assertNotNull( p );
        assertEquals( "'&nbsp;' '&iexcl;'", p.asText().trim() );

        assertFalse( elementIterator.hasNext() );
    }
}
