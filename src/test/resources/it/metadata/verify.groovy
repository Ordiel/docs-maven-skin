// This script verifies that a site with only metadata set up contains the correct metadata.

import com.jcabi.w3c.ValidatorBuilder
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.jsoup.Jsoup
import java.util.logging.Logger

// Acquires the sample HTML content
def html = new File(basedir, 'target/site/index.html').text

// Validates HTML 5
def htmlResponse = new ValidatorBuilder().html().validate(html)

MatcherAssert.assertThat(
    'There are errors',
    htmlResponse.errors(),
    Matchers.describedAs(htmlResponse.toString(), Matchers.hasSize(0))
)

Logger logger = Logger.getLogger("")
htmlResponse.warnings().each{ value -> 
	logger.warning( value.toString() )
}

// Parses HTML
def head = Jsoup.parse(html).head()

// Verifies the basic metadata is generated
def metaContentType = head.select( 'meta[charset="utf-8"]' )
def metaViewport = head.select( 'meta[name="viewport"]' )
def metaCompatible = head.select( 'meta[http-equiv="X-UA-Compatible"]' )

assert metaContentType != null
assert metaViewport.attr( 'content' ).equals( 'width=device-width, initial-scale=1, shrink-to-fit=no' )
assert metaCompatible.attr( 'content' ).equals( 'IE=edge' )

// Verifies the general information metadata is generated
def metaDesc = head.select( 'meta[name="description"]' )
def metaKeywords = head.select( 'meta[name="keywords"]' )
def metaAuthor = head.select( 'meta[name="author"]' )

assert metaDesc.attr( 'content' ).equals( 'Check the documentation for the example' )
assert metaKeywords.attr( 'content' ).equals( 'Maven, Java, library' )
assert metaAuthor.attr( 'content' ).equals( 'Bernardo Martínez Garrido' )

// Verifies the Facebook Open Graph metadata is generated
def metaOgType = head.select( 'meta[property="og:type"]' )
def metaOgUrl = head.select( 'meta[property="og:url"]' )
def metaOgSite = head.select( 'meta[property="og:site_name"]' )
def metaOgTitle = head.select( 'meta[property="og:title"]' )
def metaOgDesc = head.select( 'meta[property="og:description"]' )

assert metaOgType.attr( 'content' ).equals( 'website' )
assert metaOgUrl.attr( 'content' ).equals( 'http://canonicallink' )
assert metaOgSite.attr( 'content' ).equals( 'metadata-site – Metadata page' )
assert metaOgTitle.attr( 'content' ).equals( 'metadata-site – Metadata page' )
assert metaOgDesc.attr( 'content' ).equals( 'Check the documentation for the example' )

// Verifies the Twitter metadata is generated
def metaTwCard = head.select( 'meta[name="twitter:card"]' )
def metaTwSite = head.select( 'meta[name="twitter:creator"]' )
def metaTwTitle = head.select( 'meta[name="twitter:title"]' )
def metaTwDesc = head.select( 'meta[name="twitter:description"]' )

assert metaTwCard.attr( 'content' ).equals( 'summary' )
assert metaTwSite.attr( 'content' ).equals( '@bmg' )
assert metaTwTitle.attr( 'content' ).equals( 'metadata-site – Metadata page' )
assert metaTwDesc.attr( 'content' ).equals( 'Check the documentation for the example' )
