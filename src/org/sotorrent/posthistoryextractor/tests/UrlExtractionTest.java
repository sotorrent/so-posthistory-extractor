package org.sotorrent.posthistoryextractor.tests;

import org.sotorrent.posthistoryextractor.history.Posts;
import org.sotorrent.posthistoryextractor.urls.*;
import org.sotorrent.posthistoryextractor.version.PostVersion;
import org.sotorrent.posthistoryextractor.version.PostVersionList;
import org.junit.jupiter.api.Test;
import org.sotorrent.util.Patterns;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import static org.sotorrent.posthistoryextractor.tests.PostVersionHistoryTest.pathToPostVersionLists;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UrlExtractionTest {

    @Test
    void testMarkdownLinkInline(){
        /*
        `Math.Floor` rounds down, `Math.Ceiling` rounds up, and `Math.Truncate` rounds towards zero.
        Thus, `Math.Truncate` is like `Math.Floor` for positive numbers, and like `Math.Ceiling` for negative numbers.
        Here's the [reference](http://msdn.microsoft.com/en-us/library/system.math.truncate.aspx).

        For completeness, `Math.Round` rounds to the nearest integer.
        If the number is exactly midway between two integers, then it rounds towards the even one.
        [Reference.](http://msdn.microsoft.com/en-us/library/system.math.round.aspx)
         */

        PostVersionList a_33 = PostVersionList.readFromCSV(pathToPostVersionLists, 33, Posts.ANSWER_ID);

        PostVersion version_1 = a_33.getFirst();
        List<Link> extractedUrls = Link.extractTyped(version_1.getContent());

        assertEquals(2, extractedUrls.size());

        assertEquals("[reference](http://msdn.microsoft.com/en-us/library/system.math.truncate.aspx)", extractedUrls.get(0).getFullMatch());
        assertEquals("reference", extractedUrls.get(0).getAnchor());
        assertNull(extractedUrls.get(0).getReference());
        assertEquals("http://msdn.microsoft.com/en-us/library/system.math.truncate.aspx", extractedUrls.get(0).getUrl());
        assertEquals("msdn.microsoft.com", extractedUrls.get(0).getCompleteDomain());
        assertNull(extractedUrls.get(0).getTitle());
        assertThat(extractedUrls.get(0), instanceOf(MarkdownLinkInline.class));

        assertEquals("[Reference.](http://msdn.microsoft.com/en-us/library/system.math.round.aspx)", extractedUrls.get(1).getFullMatch());
        assertEquals("Reference.", extractedUrls.get(1).getAnchor());
        assertNull(extractedUrls.get(1).getReference());
        assertEquals("http://msdn.microsoft.com/en-us/library/system.math.round.aspx", extractedUrls.get(1).getUrl());
        assertEquals("msdn.microsoft.com", extractedUrls.get(1).getCompleteDomain());
        assertNull(extractedUrls.get(1).getTitle());
        assertThat(extractedUrls.get(1), instanceOf(MarkdownLinkInline.class));
    }

    @Test
    void testMarkdownLinkReference(){
        /*
        Consider using a [ManualResetEvent][1] to block the main thread at the end of its processing, and call Reset() on it once the timer's processing has finished.
        If this is something that needs to run constantly, consider moving this into a service process instead of a console app.

        [1]: http://msdn.microsoft.com/en-us/library/system.threading.manualresetevent.aspx "MSDN Reference"
         */
        PostVersionList a_44 = PostVersionList.readFromCSV(pathToPostVersionLists, 44, Posts.ANSWER_ID);

        PostVersion version_1 = a_44.getFirst();
        List<Link> extractedUrls = Link.extractTyped(version_1.getContent());

        assertEquals(1, extractedUrls.size());

        assertEquals("[ManualResetEvent][1]\n[1]: http://msdn.microsoft.com/en-us/library/system.threading.manualresetevent.aspx \"MSDN Reference\"", extractedUrls.get(0).getFullMatch());
        assertEquals("ManualResetEvent", extractedUrls.get(0).getAnchor());
        assertEquals("1", extractedUrls.get(0).getReference());
        assertEquals("http://msdn.microsoft.com/en-us/library/system.threading.manualresetevent.aspx", extractedUrls.get(0).getUrl());
        assertEquals("msdn.microsoft.com", extractedUrls.get(0).getCompleteDomain());
        assertEquals("MSDN Reference", extractedUrls.get(0).getTitle());
        assertThat(extractedUrls.get(0), instanceOf(MarkdownLinkReference.class));
    }

    @Test
    void testAnchorLink(){
        /*
        Ideally, you would bind TextBox.<a href="http://msdn.microsoft.com/en-us/library/system.windows.controls.textbox.selectionstart.aspx">SelectionStart</a> and TextBox.
        <a href="http://msdn.microsoft.com/en-us/library/system.windows.controls.textbox.selectionlength.aspx">SelectionLength</a> to values from the slider.
        (Probably via a converter that implements IMultiValueConverer)

        Unfortunately, you can't, because you can only bind Dependency Properties, and SelectionStart and SelectionLength are not dependency properties.

        This means you would have to handle the "ValueChanged" event on the sliders and set the SelectionStart and SelectionLength via the code.

        Disappointing answer - I bet you were hoping for some slick XAML code :-)
         */

        PostVersionList a_1629423 = PostVersionList.readFromCSV(pathToPostVersionLists, 1629423, Posts.ANSWER_ID);

        PostVersion version_1 = a_1629423.getFirst();
        List<Link> extractedUrls = Link.extractTyped(version_1.getContent());

        assertEquals(2, extractedUrls.size());

        assertEquals("<a href=\"http://msdn.microsoft.com/en-us/library/system.windows.controls.textbox.selectionstart.aspx\">SelectionStart</a>", extractedUrls.get(0).getFullMatch());
        assertEquals("SelectionStart", extractedUrls.get(0).getAnchor());
        assertNull(extractedUrls.get(0).getReference());
        assertEquals("http://msdn.microsoft.com/en-us/library/system.windows.controls.textbox.selectionstart.aspx", extractedUrls.get(0).getUrl());
        assertEquals("msdn.microsoft.com", extractedUrls.get(0).getCompleteDomain());
        assertNull(extractedUrls.get(0).getTitle());
        assertThat(extractedUrls.get(0), instanceOf(AnchorLink.class));

        assertEquals("<a href=\"http://msdn.microsoft.com/en-us/library/system.windows.controls.textbox.selectionlength.aspx\">SelectionLength</a>", extractedUrls.get(1).getFullMatch());
        assertEquals("SelectionLength", extractedUrls.get(1).getAnchor());
        assertNull(extractedUrls.get(1).getReference());
        assertEquals("http://msdn.microsoft.com/en-us/library/system.windows.controls.textbox.selectionlength.aspx", extractedUrls.get(1).getUrl());
        assertEquals("msdn.microsoft.com", extractedUrls.get(1).getCompleteDomain());
        assertNull(extractedUrls.get(1).getTitle());
        assertThat(extractedUrls.get(1), instanceOf(AnchorLink.class));
    }

    @Test
    void testMarkdownLinkAngleBrackets(){
        /*
        Have a look at this article

        <http://www.gskinner.com/blog/archives/2006/06/as3_resource_ma.html>

        IANA actionscript programmer, however the feeling I'm getting is that, because the garbage collector might not run when you want it to.

        Hence
        <http://www.craftymind.com/2008/04/09/kick-starting-the-garbage-collector-in-actionscript-3-with-air/>

        So I'd recommend trying out their collection code and see if it helps

            private var gcCount:int;
            private function startGCCycle():void{
    	        gcCount = 0;
    	        addEventListener(Event.ENTER_FRAME, doGC);
            }
            private function doGC(evt:Event):void{
    	        flash.system.System.gc();
            	if(++gcCount > 1){
    	        	removeEventListener(Event.ENTER_FRAME, doGC);
            		setTimeout(lastGC, 40);
    	        }
            }
            private function lastGC():void{
    	        flash.system.System.gc();
            }
         */

        PostVersionList a_52 = PostVersionList.readFromCSV(pathToPostVersionLists, 52, Posts.ANSWER_ID);

        PostVersion version_1 = a_52.getFirst();
        List<Link> extractedUrls = Link.extractTyped(version_1.getContent());

        assertEquals(2, extractedUrls.size());

        assertEquals("<http://www.gskinner.com/blog/archives/2006/06/as3_resource_ma.html>", extractedUrls.get(0).getFullMatch());
        assertNull(extractedUrls.get(0).getAnchor());
        assertNull(extractedUrls.get(0).getReference());
        assertEquals("http://www.gskinner.com/blog/archives/2006/06/as3_resource_ma.html", extractedUrls.get(0).getUrl());
        assertEquals("www.gskinner.com", extractedUrls.get(0).getCompleteDomain());
        assertNull(extractedUrls.get(0).getTitle());
        assertThat(extractedUrls.get(0), instanceOf(MarkdownLinkAngleBrackets.class));

        assertEquals("<http://www.craftymind.com/2008/04/09/kick-starting-the-garbage-collector-in-actionscript-3-with-air/>", extractedUrls.get(1).getFullMatch());
        assertNull(extractedUrls.get(1).getAnchor());
        assertNull(extractedUrls.get(1).getReference());
        assertEquals("http://www.craftymind.com/2008/04/09/kick-starting-the-garbage-collector-in-actionscript-3-with-air/", extractedUrls.get(1).getUrl());
        assertEquals("www.craftymind.com", extractedUrls.get(1).getCompleteDomain());
        assertNull(extractedUrls.get(1).getTitle());
        assertThat(extractedUrls.get(1), instanceOf(MarkdownLinkAngleBrackets.class));
    }

    @Test
    void testLink(){
        /*
        Here is one hack that might work. Isn't clean, but it looks like it might work:

        http://www.brokenbuild.com/blog/2006/08/15/mysql-triggers-how-do-you-abort-an-insert-update-or-delete-with-a-trigger/

        Essentially you just try to update a column that doesn't exist.
         */

        PostVersionList a_49 = PostVersionList.readFromCSV(pathToPostVersionLists, 49, Posts.ANSWER_ID);

        PostVersion version_1 = a_49.getFirst();
        List<Link> extractedUrls = Link.extractTyped(version_1.getContent());

        assertEquals(1, extractedUrls.size());

        assertEquals("http://www.brokenbuild.com/blog/2006/08/15/mysql-triggers-how-do-you-abort-an-insert-update-or-delete-with-a-trigger/", extractedUrls.get(0).getFullMatch());
        assertNull(extractedUrls.get(0).getAnchor());
        assertNull(extractedUrls.get(0).getReference());
        assertEquals("http://www.brokenbuild.com/blog/2006/08/15/mysql-triggers-how-do-you-abort-an-insert-update-or-delete-with-a-trigger/", extractedUrls.get(0).getUrl());
        assertEquals("www.brokenbuild.com", extractedUrls.get(0).getCompleteDomain());
        assertNull(extractedUrls.get(0).getTitle());
        assertThat(extractedUrls.get(0), instanceOf(Link.class));
    }

    @Test
    void testNormalizationOfPostVersionLists(){

        PostVersionList a_33 = PostVersionList.readFromCSV(pathToPostVersionLists, 33, Posts.ANSWER_ID);

        PostVersionList a_44 = PostVersionList.readFromCSV(pathToPostVersionLists, 44, Posts.ANSWER_ID);

        PostVersionList a_49 = PostVersionList.readFromCSV(pathToPostVersionLists, 49, Posts.ANSWER_ID);

        PostVersionList a_52 = PostVersionList.readFromCSV(pathToPostVersionLists, 52, Posts.ANSWER_ID);

        PostVersionList a_1629423 = PostVersionList.readFromCSV(pathToPostVersionLists, 1629423, Posts.ANSWER_ID);


        LinkedList<Link> extractedLinks = new LinkedList<>();

        a_33.normalizeLinks();
        PostVersion version_1_a33 = a_33.getFirst();
        extractedLinks.addAll(Link.extractTyped(version_1_a33.getContent()));

        a_44.normalizeLinks();
        PostVersion version_1_a44 = a_44.getFirst();
        extractedLinks.addAll(Link.extractTyped(version_1_a44.getContent()));

        a_49.normalizeLinks();
        PostVersion version_1_a49 = a_49.getFirst();
        extractedLinks.addAll(Link.extractTyped(version_1_a49.getContent()));

        a_52.normalizeLinks();
        PostVersion version_1_a52 = a_52.getFirst();
        extractedLinks.addAll(Link.extractTyped(version_1_a52.getContent()));

        a_1629423.normalizeLinks();
        PostVersion version_1_a1629423 = a_1629423.getFirst();
        extractedLinks.addAll(Link.extractTyped(version_1_a1629423.getContent()));


        for(Link link : extractedLinks){
            assertFalse(link instanceof MarkdownLinkReference);
        }
    }

    @Test
    void testMarkdownLinkInlineTitle(){
        List<Link> extractedUrls = Link.extractTyped("[I'm an inline-style link with title](https://www.google.com \"Google's Homepage\")");

        assertEquals(1, extractedUrls.size());

        assertEquals("[I'm an inline-style link with title](https://www.google.com \"Google's Homepage\")", extractedUrls.get(0).getFullMatch());
        assertEquals("I'm an inline-style link with title", extractedUrls.get(0).getAnchor());
        assertNull(extractedUrls.get(0).getReference());
        assertEquals("https://www.google.com", extractedUrls.get(0).getUrl());
        assertEquals("Google's Homepage", extractedUrls.get(0).getTitle());
        assertThat(extractedUrls.get(0), instanceOf(MarkdownLinkInline.class));

        extractedUrls = Link.extractTyped("[I'm an inline-style link without title](https://www.google.com)");

        assertEquals(1, extractedUrls.size());

        assertEquals("[I'm an inline-style link without title](https://www.google.com)", extractedUrls.get(0).getFullMatch());
        assertEquals("I'm an inline-style link without title", extractedUrls.get(0).getAnchor());
        assertNull(extractedUrls.get(0).getReference());
        assertEquals("https://www.google.com", extractedUrls.get(0).getUrl());
        assertEquals("www.google.com", extractedUrls.get(0).getCompleteDomain());
        assertNull(extractedUrls.get(0).getTitle());
        assertThat(extractedUrls.get(0), instanceOf(MarkdownLinkInline.class));
    }

    @Test
    void testDeletionOfEmptyTextBlocksAfterNormalization () {
        // version 2 should have 4 text blocks and 2 code blocks
        // after normalization, the last block, which contains only a reference and a URL, should be deleted because it's empty
        PostVersionList a_19049539 = PostVersionList.readFromCSV(pathToPostVersionLists, 19049539, Posts.ANSWER_ID);
        a_19049539.normalizeLinks();
        PostVersion version_2_a_19049539 = a_19049539.get(1);
        assertEquals(version_2_a_19049539.getTextBlocks().size(), 3);
    }

    @Test
    void testUrlComponentExtraction() {
        testUrlComponents("https://developers.facebook.com/docs/messenger-platform/thread-settings/greeting-text/",
                "https",
                "developers.facebook.com", "facebook.com",
                "docs/messenger-platform/thread-settings/greeting-text");

        testUrlComponents("http://i.stack.imgur.com/Wl2DC.png",
                "http",
                "i.stack.imgur.com", "imgur.com",
                "Wl2DC.png");

        testUrlComponents("http://dev.mysql.com/doc/refman/5.5/en/create-table.html",
                "http",
                "dev.mysql.com", "mysql.com",
                "doc/refman/5.5/en/create-table.html");

        testUrlComponents("http://book.cakephp.org/2.0/en/core-libraries/helpers/html.html#HtmlHelper::image",
                "http",
                "book.cakephp.org", "cakephp.org",
                "2.0/en/core-libraries/helpers/html.html");

        testUrlComponents("https://webcache.googleusercontent.com/search?q=cache:F1YnhmHMSkwJ:https://www.w3.org/Addressing/URL/uri-spec.ps%20&cd=2&hl=en&ct=clnk&gl=uk",
                "https",
                "webcache.googleusercontent.com", "googleusercontent.com",
                "search");

        testUrlComponents("http://developer.android.com/reference/android/view/ViewGroup.html#indexOfChild%28android.view.View%29",
                "http",
                "developer.android.com", "android.com",
                "reference/android/view/ViewGroup.html");

        testUrlComponents("ftp://ftp.linux-magazine.com/pub/listings/magazine/185/ELKstack/configfiles/etc_logstash/conf.d/5003-postfix-filter.conf",
                "ftp",
                "ftp.linux-magazine.com", "linux-magazine.com",
                "pub/listings/magazine/185/ELKstack/configfiles/etc_logstash/conf.d/5003-postfix-filter.conf");
    }

    private void testUrlComponents(String url, String expectedProtocol,
                         String expectedCompleteDomain, String expectedRootDomain,
                         String expectedPath) {
        Link link = Link.extractBare(url).get(0);
        assertEquals(expectedProtocol, link.getProtocol());
        assertEquals(expectedCompleteDomain, link.getCompleteDomain());
        assertEquals(expectedRootDomain, link.getRootDomain());
        assertEquals(expectedPath, link.getPath());
    }

    @Test
    void testDoctypeUrl() {
        String inputString = "DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd";

        Matcher urlMatcher = Patterns.url.matcher(inputString);
        assertTrue(urlMatcher.find());
        Link link = Link.extractBare(inputString).get(0);
        assertEquals(urlMatcher.group(0), link.getUrl());

        assertEquals("http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd", link.getUrl());
        assertEquals("http", Patterns.extractProtocolFromUrl(link.getUrl()));
        String completeDomain = Patterns.extractCompleteDomainFromUrl(link.getUrl());
        assertEquals("www.w3.org", completeDomain);
        assertEquals("w3.org", Patterns.extractRootDomainFromCompleteDomain(completeDomain));
        assertEquals("TR/xhtml11/DTD/xhtml11.dtd", Patterns.extractPathFromUrl(link.getUrl()));
    }
}