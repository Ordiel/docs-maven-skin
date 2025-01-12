// This script verifies that the default highlight.js style is applied when no customHighlightStyle
// is provided.

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
def parsed = Jsoup.parse(html)
def head = parsed.head()

// The default highlight style is applied
def customStyle = head.select( 'link[href="./lib/highlight/styles/default.css"]' )
assert customStyle.outerHtml().equals('<link rel="stylesheet" href="./lib/highlight/styles/default.css">')
