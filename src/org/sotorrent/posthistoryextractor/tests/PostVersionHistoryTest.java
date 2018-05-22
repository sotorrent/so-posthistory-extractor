package org.sotorrent.posthistoryextractor.tests;

import org.sotorrent.posthistoryextractor.Config;
import org.sotorrent.posthistoryextractor.blocks.CodeBlockVersion;
import org.sotorrent.posthistoryextractor.blocks.PostBlockSimilarity;
import org.sotorrent.posthistoryextractor.blocks.PostBlockVersion;
import org.sotorrent.posthistoryextractor.blocks.TextBlockVersion;
import org.sotorrent.posthistoryextractor.history.PostHistory;
import org.sotorrent.posthistoryextractor.history.Posts;
import org.sotorrent.posthistoryextractor.version.PostVersion;
import org.sotorrent.posthistoryextractor.version.PostVersionList;
import org.sotorrent.posthistoryextractor.version.TitleVersion;
import org.sotorrent.posthistoryextractor.version.TitleVersionList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.sotorrent.util.exceptions.InputTooShortException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostVersionHistoryTest {
    static Path pathToPostVersionLists = Paths.get("testdata", "post_version_lists");

    static Config configEqual = Config.DEFAULT
            .withTextSimilarityMetric(org.sotorrent.stringsimilarity.equal.Variants::equal)
            .withTextSimilarityThreshold(1.0)
            .withTextBackupSimilarityMetric(null)
            .withTextBackupSimilarityThreshold(1.0)
            .withCodeSimilarityMetric(org.sotorrent.stringsimilarity.equal.Variants::equal)
            .withCodeSimilarityThreshold(1.0)
            .withCodeBackupSimilarityMetric(null)
            .withCodeBackupSimilarityThreshold(1.0);

    private void testPredecessorSimilarities(PostVersion postVersion) {
        for (PostBlockVersion currentPostBlockVersion : postVersion.getPostBlocks()) {
            for (PostBlockSimilarity similarity : currentPostBlockVersion.getPredecessorSimilarities().values()) {
                assertThat(similarity.getMetricResult(), either(
                        allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(1.0)))
                        .or(equalTo(PostBlockVersion.EQUALITY_SIMILARITY))
                );
            }
        }
    }

    private void testPostBlockCount(PostVersion postVersion,
                                    int expectedPostBlockCount, int expectedTextBlockCount, int expectedCodeBlockCount) {
        assertEquals(expectedPostBlockCount, postVersion.getPostBlocks().size());
        assertEquals(expectedTextBlockCount, postVersion.getTextBlocks().size());
        assertEquals(expectedCodeBlockCount, postVersion.getCodeBlocks().size());
    }

    @Test
    void testReadPostHistoryAnswer1109108() {
        PostVersionList a_1109108 = PostVersionList.readFromCSV(pathToPostVersionLists, 1109108, Posts.ANSWER_ID);

        assertEquals(7, a_1109108.size());

        PostVersion version_7 = a_1109108.get(6);
        testPredecessorSimilarities(version_7);

        testPostBlockCount(version_7, 3, 2, 1);

        CodeBlockVersion codeBlock_1 = version_7.getCodeBlocks().get(0);
        String[] lines = codeBlock_1.getContent().split("\n");
        assertEquals(6, lines.length);
        for (String line : lines) {
            assertTrue(line.startsWith("    "));
        }

        TextBlockVersion textBlock_1 = version_7.getTextBlocks().get(0);
        lines = textBlock_1.getContent().split("\n");
        assertEquals(1, lines.length);
        for (String line : lines) {
            assertFalse(line.startsWith("    "));
        }
    }

    @Test
    void testReadPostHistoryAnswer3145655() {
        PostVersionList a_3145655 = PostVersionList.readFromCSV(pathToPostVersionLists, 3145655, Posts.ANSWER_ID);

        assertEquals(7, a_3145655.size());

        PostVersion version_7 = a_3145655.get(6);
        testPredecessorSimilarities(version_7);

        testPostBlockCount(version_7, 5,3, 2);

        CodeBlockVersion codeBlock_1 = version_7.getCodeBlocks().get(0);
        String[] lines = codeBlock_1.getContent().split("\n");
        assertEquals(8, lines.length);
        for (String line : lines) {
            assertTrue(line.startsWith("    "));
        }

        TextBlockVersion textBlock_1 = version_7.getTextBlocks().get(0);
        lines = textBlock_1.getContent().split("\n");
        assertEquals(7, lines.length);
        for (String line : lines) {
            assertFalse(line.startsWith("    "));
        }
    }

    @Test
    void testReadPostHistoryAnswer9855338() {
        PostVersionList a_9855338 = PostVersionList.readFromCSV(pathToPostVersionLists, 9855338, Posts.ANSWER_ID);

        assertEquals(11, a_9855338.size());

        PostVersion version_11 = a_9855338.get(10);
        testPredecessorSimilarities(version_11);

        testPostBlockCount(version_11, 3, 2, 1);

        CodeBlockVersion codeBlock_1 = version_11.getCodeBlocks().get(0);
        String[] lines = codeBlock_1.getContent().split("\n");
        assertEquals(10, lines.length);
        for (String line : lines) {
            assertTrue(line.startsWith("    "));
        }

        TextBlockVersion textBlock_1 = version_11.getTextBlocks().get(0);
        lines = textBlock_1.getContent().split("\n");
        assertEquals(1, lines.length);
        for (String line : lines) {
            assertFalse(line.startsWith("    "));
        }
    }

    @Test
    void testReadPostHistoryAnswer2581754() {
        PostVersionList a_2581754 = PostVersionList.readFromCSV(pathToPostVersionLists, 2581754, Posts.ANSWER_ID);

        assertEquals(8, a_2581754.size());

        PostVersion version_3 = a_2581754.get(2);
        testPredecessorSimilarities(version_3);

        testPostBlockCount(version_3, 6, 3, 3);

        CodeBlockVersion codeBlock_1 = version_3.getCodeBlocks().get(0);
        String[] lines = codeBlock_1.getContent().split("\n");
        assertEquals(25, lines.length);
        for (String line : lines) {
            assertTrue(line.startsWith("    "));
        }

        TextBlockVersion textBlock_1 = version_3.getTextBlocks().get(0);
        lines = textBlock_1.getContent().split("\n");
        assertEquals(1, lines.length);
        for (String line : lines) {
            assertFalse(line.startsWith("    "));
        }
    }

    @Test
    void testReadPostHistoryAnswer20991163() {
        PostVersionList a_20991163 = PostVersionList.readFromCSV(pathToPostVersionLists, 20991163, Posts.ANSWER_ID);
        // this post should only consist of one code block (not an empty text block at the end)
        assertEquals(1, a_20991163.size());
        PostVersion version_1 = a_20991163.get(0);
        testPostBlockCount(version_1, 1, 0, 1);
    }

    @Test
    void testReadPostHistoryAnswer32012927() {
        PostVersionList a_32012927 = PostVersionList.readFromCSV(pathToPostVersionLists, 32012927, Posts.ANSWER_ID);
        assertEquals(4, a_32012927.size());
        // the first version of this post should only consist of one text block
        PostVersion version_1 = a_32012927.get(0);
        testPostBlockCount(version_1, 1, 1, 0);
    }

    @Test
    void testReadPostHistoryAnswer10734905() {
        PostVersionList a_10734905 = PostVersionList.readFromCSV(pathToPostVersionLists, 10734905, Posts.ANSWER_ID);

        assertEquals(1, a_10734905.size());

        // the first and only version of this post should consist of three text blocks and two code blocks
        PostVersion version_1 = a_10734905.get(0);
        testPostBlockCount(version_1, 5, 3, 2);
        List<PostBlockVersion> postBlocks = version_1.getPostBlocks();

        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(2) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(3) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(4) instanceof TextBlockVersion);
    }

    @Test
    void testReadPostHistoryAnswer31965641() {
        PostVersionList a_31965641 = PostVersionList.readFromCSV(pathToPostVersionLists, 31965641, Posts.ANSWER_ID);

        assertEquals(1, a_31965641.size());

        // the first and only version of this post should consist of two text blocks and two code blocks
        PostVersion version_1 = a_31965641.get(0);
        testPostBlockCount(version_1, 4, 2, 2);
        List<PostBlockVersion> postBlocks = version_1.getPostBlocks();

        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(2) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(3) instanceof CodeBlockVersion);
    }

    @Test
    void testRootPostBlockVersionIdAnswer3758880() {
        PostVersionList a_3758880 = PostVersionList.readFromCSV(pathToPostVersionLists, 3758880, Posts.ANSWER_ID);

        // there are 11 versions of this post
        assertEquals(11, a_3758880.size());

        PostVersion version_1 = a_3758880.get(0);

        assertEquals(6, version_1.getPostBlocks().size());
        // root post blocks of first version must be null
        for (PostBlockVersion currentPostBlock : version_1.getPostBlocks()) {
            assertEquals(currentPostBlock.getId(), (int) currentPostBlock.getRootPostBlockVersionId());
        }

        PostVersion version_2 = a_3758880.get(1);
        testPredecessorSimilarities(version_2);

        assertEquals(4, version_2.getPostBlocks().size());
        // first code block of second version has first code block of first version as root post block
        Assertions.assertEquals((int) version_2.getCodeBlocks().get(0).getRootPostBlockVersionId(),
                version_1.getCodeBlocks().get(0).getId());
        // second code block of second version has third code block of first version as root post block
        Assertions.assertEquals((int) version_2.getCodeBlocks().get(1).getRootPostBlockVersionId(),
                version_1.getCodeBlocks().get(2).getId());
        // second text block of second version has no predecessor (-> itself as root post block)
        Assertions.assertEquals((int) version_2.getTextBlocks().get(0).getRootPostBlockVersionId(),
                version_2.getTextBlocks().get(0).getId());

        PostVersion lastPostVersion = a_3758880.get(a_3758880.size() - 1);
        testPredecessorSimilarities(lastPostVersion);

        // first code block of last version still has first code block of first version as root post block
        Assertions.assertEquals((int) lastPostVersion.getCodeBlocks().get(0).getRootPostBlockVersionId(),
                version_1.getCodeBlocks().get(0).getId());
        // first text block of last version has first text block of second version as root post block
        Assertions.assertEquals((int) lastPostVersion.getTextBlocks().get(0).getRootPostBlockVersionId(),
                version_2.getTextBlocks().get(0).getId());
    }

    @Test
    void testReadPostHistoryQuestion22360443() {
        PostVersionList q_22360443 = PostVersionList.readFromCSV(pathToPostVersionLists, 22360443, Posts.QUESTION_ID);

        assertEquals(2, q_22360443.size());

        PostVersion version_1 = q_22360443.get(0);
        testPostBlockCount(version_1, 4, 2, 2);
        List<PostBlockVersion> postBlocks = version_1.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(1) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(2) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(3) instanceof TextBlockVersion);

        PostVersion version_2 = q_22360443.get(1);
        testPredecessorSimilarities(version_2);
        testPostBlockCount(version_2, 5, 3, 2);
        postBlocks = version_2.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(2) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(3) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(4) instanceof TextBlockVersion);
    }

    @Test
    void testStackSnippetCodeBlocksAnswer32143330() {
        PostVersionList a_32143330 = PostVersionList.readFromCSV(pathToPostVersionLists, 32143330, Posts.ANSWER_ID);

        assertEquals(4, a_32143330.size());

        // Test if Stack Snippets (see https://stackoverflow.blog/2014/09/16/introducing-runnable-javascript-css-and-html-code-snippets/)
        // and snippet language information blocks (see https://stackoverflow.com/editing-help#syntax-highlighting)
        // are correctly handled (language info splits code blocks).
        PostVersion version_4 = a_32143330.get(3);
        testPredecessorSimilarities(version_4);
        testPostBlockCount(version_4, 6, 3, 3);
        List<PostBlockVersion> postBlocks = version_4.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(2) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(3) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(4) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(5) instanceof TextBlockVersion);
    }

    @Test
    void testStackSnippetCodeBlocksAnswer26044128() {
        PostVersionList a_26044128 = PostVersionList.readFromCSV(pathToPostVersionLists, 26044128, Posts.ANSWER_ID);

        assertEquals(12, a_26044128.size());

        PostVersion version_12 = a_26044128.get(11);
        testPredecessorSimilarities(version_12);
        testPostBlockCount(version_12, 8, 4, 4);
        List<PostBlockVersion> postBlocks = version_12.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(2) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(3) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(4) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(5) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(6) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(7) instanceof TextBlockVersion); // Markdown links
    }

    @Test
    void testAlternativeCodeBlockQuestion32342082() {
        PostVersionList q_32342082 = PostVersionList.readFromCSV(pathToPostVersionLists, 32342082, Posts.QUESTION_ID);

        assertEquals(8, q_32342082.size());

        // version 5 contains an alternative (GitHub-style) code block (see https://stackoverflow.com/revisions/32342082/5)
        PostVersion version_5 = q_32342082.get(4);
        testPredecessorSimilarities(version_5);
        testPostBlockCount(version_5, 5, 3, 2);
        List<PostBlockVersion> postBlocks = version_5.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(2) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(3) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(4) instanceof TextBlockVersion);
    }

    @Test
    void testCodeTagCodeBlockQuestion19175014() {
        PostVersionList q_19175014 = PostVersionList.readFromCSV(pathToPostVersionLists, 19175014, Posts.QUESTION_ID);

        assertEquals(2, q_19175014.size());

        // version 1+2 contain a code block marked by <pre><code> ... </pre></code> instead of correct indention (see https://stackoverflow.com/revisions/19175014/2)
        PostVersion version_2 = q_19175014.get(1);
        testPredecessorSimilarities(version_2);
        testPostBlockCount(version_2, 2, 1, 1);
        List<PostBlockVersion> postBlocks = version_2.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
    }

    @Test
    void testVersionOrderQuestion3381751() {
        PostVersionList q_3381751 = PostVersionList.readFromCSV(pathToPostVersionLists, 3381751, Posts.QUESTION_ID);

        PostVersion previousVersion = q_3381751.get(0);
        for (int i = 1; i < q_3381751.size(); i++) {
            PostVersion currentVersion = q_3381751.get(i);
            assertTrue(currentVersion.getPostHistoryId() > previousVersion.getPostHistoryId());
        }
    }

    @Test
    void testScriptTagCodeBlockQuestion3381751() {
        PostVersionList q_3381751 = PostVersionList.readFromCSV(pathToPostVersionLists, 3381751, Posts.QUESTION_ID);

        assertEquals(15, q_3381751.size());

        // version 1 contains a code block marked by <script type="text/javascript"> ... </script> instead of correct indention (see https://stackoverflow.com/revisions/3381751/1)
        PostVersion version_1 = q_3381751.get(0);
        testPostBlockCount(version_1, 3, 2, 1);
        List<PostBlockVersion> postBlocks = version_1.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(2) instanceof TextBlockVersion);
    }

    @Test
    void testScriptTagInIndentedCodeBlockQuestion28598648() {
        PostVersionList q_28598648 = PostVersionList.readFromCSV(pathToPostVersionLists, 28598648, Posts.QUESTION_ID);

        assertEquals(2, q_28598648.size());

        // version 1+2 contain an indented code block containing <script> tags (see https://stackoverflow.com/revisions/28598648/2)
        PostVersion version_2 = q_28598648.get(1);
        testPredecessorSimilarities(version_2);
        testPostBlockCount(version_2, 2, 1, 1);
        List<PostBlockVersion> postBlocks = version_2.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(1) instanceof TextBlockVersion);
    }

    @Test
    void testPredecessorAssignmentAnswer3758880() {
        // tests if posts blocks are set more than once as predecessor
        PostVersionList a_3758880 = PostVersionList.readFromCSV(pathToPostVersionLists, 3758880, Posts.ANSWER_ID, false);

        a_3758880.processVersionHistory(TextBlockVersion.getPostBlockTypeIdFilter());

        // we consider the most recent version here
        List<Integer> predecessorList = new LinkedList<>();
        List<TextBlockVersion> textBlocks = a_3758880.getLast().getTextBlocks();
        for (TextBlockVersion currentTextBlock : textBlocks) {
            if (currentTextBlock.getPred() != null) {
                predecessorList.add(currentTextBlock.getPred().getLocalId());
            }
        }

        // if the list and the set do not have equal size, there exist duplicates (i.e., post blocks with multiple predecessors)
        Set<Integer> predecessorSet = new HashSet<>(predecessorList);
        assertEquals(predecessorList.size(), predecessorSet.size());
    }

    @Test
    void testPredecessorAssignmentQuestion37625877() {
        // tests predecessor assignment if two versions have two equal text blocks
        PostVersionList q_37625877 = PostVersionList.readFromCSV(pathToPostVersionLists, 37625877, Posts.QUESTION_ID, false);

        q_37625877.processVersionHistory(TextBlockVersion.getPostBlockTypeIdFilter());

        PostVersion version_1 = q_37625877.get(0);
        PostVersion version_2 = q_37625877.get(1);
        testPredecessorSimilarities(version_2);

        // both versions have two text blocks with value "or:" at position 2 and 3, which should be linked accordingly
        Assertions.assertEquals(version_1.getTextBlocks().get(2).getLocalId(), version_2.getTextBlocks().get(2).getPred().getLocalId());
        Assertions.assertEquals(version_1.getTextBlocks().get(3).getLocalId(), version_2.getTextBlocks().get(3).getPred().getLocalId());
    }

    @Test
    void testPredecessorAssignmentAnswer42070509() {
        // tests predecessor assignment if version i has three code blocks that are equal to four code blocks in version i+1
        PostVersionList a_42070509 = PostVersionList.readFromCSV(pathToPostVersionLists, 42070509, Posts.ANSWER_ID, false);

        a_42070509.processVersionHistory(CodeBlockVersion.getPostBlockTypeIdFilter());

        PostVersion version_1 = a_42070509.get(0);
        PostVersion version_2 = a_42070509.get(1);
        testPredecessorSimilarities(version_2);

        // code blocks with content "var circle = d3.select("circle");..." are present in both versions
        Assertions.assertEquals(version_1.getCodeBlocks().get(0).getLocalId(), version_2.getCodeBlocks().get(0).getPred().getLocalId());
        Assertions.assertEquals(version_1.getCodeBlocks().get(2).getLocalId(), version_2.getCodeBlocks().get(2).getPred().getLocalId());
        Assertions.assertEquals(version_1.getCodeBlocks().get(4).getLocalId(), version_2.getCodeBlocks().get(4).getPred().getLocalId());
        // this code block is new in version two and should not have a predecessor
        assertNull(version_2.getCodeBlocks().get(6).getPred());
    }


    @Test
    void testPredecessorAssignmentQuestion23459881() {
        PostVersionList q_23459881 = PostVersionList.readFromCSV(pathToPostVersionLists, 23459881, Posts.QUESTION_ID, true);

        PostVersion version_2 = q_23459881.get(1);
        testPredecessorSimilarities(version_2);
        testPostBlockCount(version_2, 8, 4, 4);
        List<PostBlockVersion> postBlocks = version_2.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(2) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(3) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(4) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(5) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(6) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(7) instanceof CodeBlockVersion);

        // version 1: text block with localId 3 and content "Code"
        // version 2: text block with localId 3 and content "Code" + same content in text block with localId 5
        // block 5 is "correct" successor
        assertNull(postBlocks.get(2).getPred()); // localId 3
        assertNull(postBlocks.get(3).getPred()); // localId 4

        assertNotNull(postBlocks.get(4).getPred()); // localId 5
        assertEquals(new Integer(3), postBlocks.get(4).getPred().getLocalId()); // localId 5

        assertNotNull(postBlocks.get(5).getPred()); // localId 6
        assertEquals(new Integer(4), postBlocks.get(5).getPred().getLocalId()); // localId 6

        assertNotNull(postBlocks.get(6).getPred()); // localId 7
        assertEquals(new Integer(5), postBlocks.get(6).getPred().getLocalId()); // localId 7

        assertNotNull(postBlocks.get(7).getPred()); // localId 8
        assertEquals(new Integer(6), postBlocks.get(7).getPred().getLocalId()); // localId 8
    }


    @Test
    void testPredecessorAssignmentQuestion36082771() {
        PostVersionList q_36082771 = PostVersionList.readFromCSV(pathToPostVersionLists, 36082771, Posts.QUESTION_ID, true);

        PostVersion version_2 = q_36082771.get(1);
        testPredecessorSimilarities(version_2);
        testPostBlockCount(version_2, 12, 6, 6);
        List<PostBlockVersion> version_2_postBlocks = version_2.getPostBlocks();
        assertTrue(version_2_postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(version_2_postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(version_2_postBlocks.get(2) instanceof TextBlockVersion);
        assertTrue(version_2_postBlocks.get(3) instanceof CodeBlockVersion);
        assertTrue(version_2_postBlocks.get(4) instanceof TextBlockVersion);
        assertTrue(version_2_postBlocks.get(5) instanceof CodeBlockVersion);
        assertTrue(version_2_postBlocks.get(6) instanceof TextBlockVersion);
        assertTrue(version_2_postBlocks.get(7) instanceof CodeBlockVersion);
        assertTrue(version_2_postBlocks.get(8) instanceof TextBlockVersion);
        assertTrue(version_2_postBlocks.get(9) instanceof CodeBlockVersion);
        assertTrue(version_2_postBlocks.get(10) instanceof TextBlockVersion);
        assertTrue(version_2_postBlocks.get(11) instanceof CodeBlockVersion);

        // version 1: code block with localId 2 and content "glaucon@polo..." + same content in code block with localId 6
        // version 2: code block with localId 2 and content "glaucon@polo..."
        // block 2 is "correct" predecessor
        assertNotNull(version_2_postBlocks.get(1).getPred()); // localId 2
        assertEquals(new Integer(2), version_2_postBlocks.get(1).getPred().getLocalId()); // localId 2

        // version 1: text block with localId 3 and content "If I do then run..." + same content in code block with localId 7
        // version 2: code block with localId 3 and content "If I do then run..."
        // block 3 is "correct" predecessor
        assertNotNull(version_2_postBlocks.get(2).getPred()); // localId 3
        assertEquals(new Integer(3), version_2_postBlocks.get(2).getPred().getLocalId()); // localId 3

        // version 1: text block with localId 4 and content "glaucon@polo..." + same content in code block with localId 8
        // version 2: code block with localId 4 and content "glaucon@polo..."
        // block 4 is "correct" predecessor
        assertNotNull(version_2_postBlocks.get(3).getPred()); // localId 4
        assertEquals(new Integer(4), version_2_postBlocks.get(3).getPred().getLocalId()); // localId 4
    }


    @Test
    void testPredecessorAssignmentQuestion18276636() {
        PostVersionList q_18276636 = PostVersionList.readFromCSV(pathToPostVersionLists, 18276636, Posts.QUESTION_ID, true);

        PostVersion version_2 = q_18276636.get(1);
        testPredecessorSimilarities(version_2);
        testPostBlockCount(version_2, 17, 9, 8);
        List<PostBlockVersion> postBlocks = version_2.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(1) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(2) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(3) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(4) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(5) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(6) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(7) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(8) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(9) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(10) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(11) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(12) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(13) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(14) instanceof TextBlockVersion);
        assertTrue(postBlocks.get(15) instanceof CodeBlockVersion);
        assertTrue(postBlocks.get(16) instanceof TextBlockVersion);

        // version 1: text block with localId 3 and content "to"
        // version 2: text block with localId 3 and content "to" + same content in text block with localId 7
        // block 7 is "correct" successor (same neighbors)

        assertNull(postBlocks.get(2).getPred()); // localId 3

        assertNotNull(postBlocks.get(5).getPred()); // localId 6
        assertEquals(new Integer(2), postBlocks.get(5).getPred().getLocalId()); // localId 6

        assertNotNull(postBlocks.get(6).getPred()); // localId 7
        assertEquals(new Integer(3), postBlocks.get(6).getPred().getLocalId()); // localId 7

        assertNotNull(postBlocks.get(7).getPred()); // localId 8
        assertEquals(new Integer(4), postBlocks.get(7).getPred().getLocalId()); // localId 8
    }

    @Test
    void testBrokenTextBlockQuestion15372744() {
        PostVersionList q_15372744 = PostVersionList.readFromCSV(pathToPostVersionLists, 15372744, Posts.QUESTION_ID);

        // version 1 contains a broken text block, which has an indented line. Stack Overflow displays this correctly  (see https://stackoverflow.com/revisions/15372744/1)
        PostVersion version_1 = q_15372744.get(0);
        testPostBlockCount(version_1, 1, 1, 0);
        List<PostBlockVersion> postBlocks = version_1.getPostBlocks();
        assertTrue(postBlocks.get(0) instanceof TextBlockVersion);
    }

    @Test
    void testPostBlockTypeFilter3758880() {
        PostVersionList q_3758880 = PostVersionList.readFromCSV(pathToPostVersionLists, 3758880, Posts.QUESTION_ID);
        // This caused a null pointer exception before (last commit: d37e6e38c8c15efe743e35141561742d7ef91ede),
        // because some filter checks were missing.
        q_3758880.processVersionHistory(CodeBlockVersion.getPostBlockTypeIdFilter());
    }

    @Test
    void testConfiguration() {
        // text
        assertThrows(IllegalArgumentException.class, () -> Config.EMPTY.withTextSimilarityThreshold(-1.0));
        assertThrows(IllegalArgumentException.class, () -> Config.EMPTY.withTextSimilarityThreshold(+1.1));

        // code
        assertThrows(IllegalArgumentException.class, () -> Config.EMPTY.withCodeSimilarityThreshold(-1.0));
        assertThrows(IllegalArgumentException.class, () -> Config.EMPTY.withCodeSimilarityThreshold(+1.1));
    }

    @Test
    void testBackupMetric() {
        // text blocks
        TextBlockVersion textBlock1 = new TextBlockVersion(1, 1);
        textBlock1.setContent("ab");
        TextBlockVersion textBlock2 = new TextBlockVersion(2, 2);
        textBlock2.setContent("ac");

        textBlock1.compareTo(textBlock2, Config.DEFAULT); // no exception
        assertThrows(InputTooShortException.class, () -> textBlock1.compareTo(textBlock2,
                Config.DEFAULT.withTextBackupSimilarityMetric(null))
        );
        PostBlockSimilarity similarity = textBlock1.compareTo(textBlock2, Config.EMPTY);
        assertEquals(0.0, similarity.getMetricResult());

        // code blocks
        CodeBlockVersion codeBlock1 = new CodeBlockVersion(1, 1);
        codeBlock1.setContent("foo");
        CodeBlockVersion codeBlock2 = new CodeBlockVersion(2, 2);
        codeBlock2.setContent("bar");

        codeBlock1.compareTo(codeBlock2, Config.DEFAULT); // no exception
        assertThrows(InputTooShortException.class, () -> codeBlock1.compareTo(codeBlock2,
                Config.DEFAULT.withCodeBackupSimilarityMetric(null))
        );
        similarity = codeBlock1.compareTo(codeBlock2, Config.EMPTY);
        assertEquals(0.0, similarity.getMetricResult());
    }

    @Test
    void testReadFromDirectory() {
        List<PostVersionList> postVersionList = PostVersionList.readFromDirectory(pathToPostVersionLists);
        try {
            assertEquals(Files.list(pathToPostVersionLists).filter(
                    file -> PostHistory.fileNamePattern.matcher(file.toFile().getName()).matches())
                            .count(),
                    postVersionList.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReset() {
        PostVersionList a_3758880 = PostVersionList.readFromCSV(pathToPostVersionLists, 3758880, Posts.ANSWER_ID, false);

        Config config = Config.DEFAULT
                .withTextSimilarityMetric(org.sotorrent.stringsimilarity.set.Variants::fourGramOverlap)
                .withTextSimilarityThreshold(0.6)
                .withTextBackupSimilarityMetric(org.sotorrent.stringsimilarity.edit.Variants::levenshtein)
                .withTextBackupSimilarityThreshold(0.6)
                .withCodeSimilarityMetric(org.sotorrent.stringsimilarity.set.Variants::fourGramOverlap)
                .withCodeSimilarityThreshold(0.6)
                .withCodeBackupSimilarityMetric(org.sotorrent.stringsimilarity.edit.Variants::levenshtein)
                .withCodeBackupSimilarityThreshold(0.6);

        // there are 11 versions of this post
        assertEquals(11, a_3758880.size());

        // but the post blocks don't have predecessors yet, thus each block has its own lifespan
        assertEquals(47, a_3758880.getPostBlockVersionCount());
        assertEquals(a_3758880.getPostBlockVersionCount(), a_3758880.getPostBlockLifeSpans().size());

        a_3758880.processVersionHistory(config);

        // the post blocks have been extracted
        assertEquals(10, a_3758880.getPostBlockLifeSpans().size());

        // resetPostBlockVersionHistory version list
        a_3758880.resetPostBlockVersionHistory();

        // there are still 11 versions of this post
        assertEquals(11, a_3758880.size());

        // but the post blocks again don't have predecessors, thus each block has its own lifespan
        assertEquals(47, a_3758880.getPostBlockVersionCount());
        assertEquals(a_3758880.getPostBlockVersionCount(), a_3758880.getPostBlockLifeSpans().size());
    }

    @Test
    void testInvalidCharBetweenEncapsulatedTokenAndDelimiter() {
        // this post produced an IOException due to wrong escaping of backslashes in combination with double quotes
        PostVersionList.readFromCSV(pathToPostVersionLists, 10049438, Posts.ANSWER_ID, false);
    }

    @Test
    void testLocalIdAssignment(){
        // local ids were previously not updated in PostHistory.reviseAndFinalizePostBlocks after merging of post blocks

        PostVersionList a_33058542 = PostVersionList.readFromCSV(pathToPostVersionLists, 33058542, Posts.ANSWER_ID, true);

        PostVersion version_2 = a_33058542.get(1);

        Assertions.assertEquals(1, version_2.getPostBlocks().get(0).getLocalId().intValue());
        Assertions.assertEquals(2, version_2.getPostBlocks().get(1).getLocalId().intValue());
        Assertions.assertEquals(3, version_2.getPostBlocks().get(2).getLocalId().intValue());
    }

    @Test
    void testEqualityBasedMetric() {
        // test if connections are set correctly when an equality-based metric is used

        PostVersionList q_19612096 = PostVersionList.readFromCSV(pathToPostVersionLists, 19612096, Posts.QUESTION_ID, false);
        q_19612096.processVersionHistory(configEqual);

        PostVersion version_10 = q_19612096.getPostVersion(50536699);

        PostBlockVersion postBlock5 = version_10.getPostBlocks().get(4); // "Result in:"
        PostBlockVersion postBlock9 = version_10.getPostBlocks().get(8); // "Result in:"
        PostBlockVersion postBlock13 = version_10.getPostBlocks().get(12); // "Result in:"

        assertNull(postBlock5.getPred());

        assertNotNull(postBlock9.getPred());
        assertEquals(9, postBlock9.getPred().getLocalId().intValue());

        assertNotNull(postBlock13.getPred());
        assertEquals(13, postBlock13.getPred().getLocalId().intValue());
    }

    @Test
    void equalsTest() {
        int postId = 10381975;
        PostVersionList q_10381975 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.QUESTION_ID, false);
        q_10381975.processVersionHistory(configEqual);

        PostVersion version_2 = q_10381975.getPostVersion(23853971);

        // post block with localId 12 is equal to post block with localId 8 and localId 12 in previous version
        // it should be matched with the latter (smaller localId difference)
        PostBlockVersion postBlock12 = version_2.getPostBlocks().get(11);
        assertNotNull(postBlock12.getPred());
        assertEquals(Integer.valueOf(12), postBlock12.getPred().getLocalId());
    }

    @Test
    void predContextLastPostBlockAnswer32841902() {
        int postId = 32841902;
        PostVersionList a_10381975 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID, false);
        a_10381975.processVersionHistory(configEqual);

        PostVersion version_2 = a_10381975.getPostVersion(100687945);

        // the last two post blocks should be connected (version 1: localId 2+3; version 2: localId 5+6)
        PostBlockVersion postBlock5 = version_2.getPostBlocks().get(4);
        assertNotNull(postBlock5.getPred());
        assertEquals(Integer.valueOf(2), postBlock5.getPred().getLocalId());

        PostBlockVersion postBlock6 = version_2.getPostBlocks().get(5);
        assertNotNull(postBlock6.getPred());
        assertEquals(Integer.valueOf(3), postBlock6.getPred().getLocalId());
    }

    @Test
    void equalsContextAnswer37196630() {
        int postId = 37196630;
        PostVersionList a_37196630 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID, false);
        a_37196630.normalizeLinks();
        a_37196630.processVersionHistory(configEqual);

        PostVersion version_3 = a_37196630.getPostVersion(117953545);

        // text block "which gives:" is present several times (version 1: localId 3; version 2: localId 3+7)
        // correct match using context: localId 7
        PostBlockVersion postBlock3 = version_3.getPostBlocks().get(2);
        assertNull(postBlock3.getPred());

        PostBlockVersion postBlock7 = version_3.getPostBlocks().get(6);
        assertNotNull(postBlock7.getPred());
        assertEquals(Integer.valueOf(3), postBlock7.getPred().getLocalId());
    }

    @Test
    void testReadPostHistoryQuestion47555767() {
        int postId = 47555767;

        PostVersionList q_47555767 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.QUESTION_ID);
        assertEquals(1, q_47555767.size());

        PostVersion version_1 = q_47555767.get(0);
        testPostBlockCount(version_1, 1, 1, 0);
        TextBlockVersion textBlock = version_1.getTextBlocks().get(0);
        assertTrue(textBlock.getContent().trim().length() > 0);
    }

    @Test
    void testReadPostHistoryAnswer45204073() {
        int postId = 45204073;

        PostVersionList a_45204073 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID);
        assertEquals(1, a_45204073.size());

        PostVersion version_1 = a_45204073.get(0);
        testPostBlockCount(version_1, 2, 1, 1);
    }

    @Test
    void testReadPostHistoryAnswer2376203() {
        int postId = 2376203;

        PostVersionList a_2376203 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID);
        assertEquals(1, a_2376203.size());

        PostVersion version_1 = a_2376203.get(0);
        // should have one TextBlock with content "......", but this is merged according to our heuristic
        testPostBlockCount(version_1, 1, 0, 1);
    }

    @Test
    void testReadPostHistoryAnswer45163319() {
        int postId = 45163319;

        PostVersionList a_45163319 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID);
        assertEquals(1, a_45163319.size());

        PostVersion version_1 = a_45163319.get(0);
        testPostBlockCount(version_1, 1, 1, 0);
    }

    @Test
    void testReadPostHistoryQuestion1257964() {
        int postId = 1257964;

        PostVersionList q_1257964 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.QUESTION_ID);
        assertEquals(5, q_1257964.size());

        // content of this version contains only whitespace ("  &#xD;&#xA;   ")
        PostVersion version_3 = q_1257964.getPostVersion(2575481);
        testPostBlockCount(version_3, 0, 0, 0);
    }

    @Test
    void testReadPostHistoryInlineStackSnippet() {
        int postId = 26365857;
        PostVersionList q_26365857 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.QUESTION_ID);
        assertEquals(3, q_26365857.size());

        // this version contains inline stack snippets with language information -> considered part of text block
        PostVersion version_2 = q_26365857.getPostVersion(75518502);
        testPostBlockCount(version_2, 1, 1, 0);

        postId = 27994382;
        PostVersionList a_27994382 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.QUESTION_ID);
        assertEquals(2, a_27994382.size());

        // this version contains inline stack snippets with language information -> considered part of text block
        PostVersion version_1 = a_27994382.getPostVersion(81771332);
        testPostBlockCount(version_1, 1, 1, 0);
    }

    @Test
    void testReadPostHistoryWithEmptyVersion() {
        int postId = 1450250;
        PostVersionList q_1450250 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.QUESTION_ID);
        assertEquals(2, q_1450250.size());

        // content of this version is empty
        PostVersion version_1 = q_1450250.getPostVersion(2870161);
        testPostBlockCount(version_1, 0, 0, 0);

        postId = 1223598;
        PostVersionList a_1223598 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID);
        assertEquals(2, a_1223598.size());

        // content of this version is empty
        version_1 = a_1223598.getPostVersion(2398799);
        testPostBlockCount(version_1, 0, 0, 0);
    }

    @Test
    void testReadPostHistoryEmptyCodeBlock() {
        // the following posts were removed from the test data for test case testPostVersionsWithoutContent (disabled),
        // because they have non-whitespace content, but should still be treated as having no post blocks
        // (i.e., they only contain an empty code block)

        int postId = 5864258;
        PostVersionList a_5864258 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID);
        assertEquals(2, a_5864258.size());

        // content of this version is an empty code block, which is ignored
        PostVersion version_1 = a_5864258.getPostVersion(12646646);
        testPostBlockCount(version_1, 0, 0, 0);

        postId = 9875710;
        PostVersionList a_9875710 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID);
        assertEquals(3, a_9875710.size());

        // content of this version is an empty code block, which is ignored
        version_1 = a_9875710.getPostVersion(22583841);
        testPostBlockCount(version_1, 0, 0, 0);
    }

    @Test
    void testOrderOfPostHistory() {
        int postId = 1669;
        PostVersionList q_1669 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.QUESTION_ID);
        assertEquals(10, q_1669.size());

        // assert that versions are ordered chronologically
        for (int i = 1; i < q_1669.size(); i++) {
            PostVersion currentPostVersion = q_1669.get(i);
            PostVersion previousPostVersion = q_1669.get(i-1);
            assertTrue(currentPostVersion.getCreationDate().after(previousPostVersion.getCreationDate()));
        }
    }

    @Test
    void testReadPostHistoryAnswer28280446() {
        // this post was present in SO dump 2017-12-01, but not anymore in 2018-03-13
        int postId = 28280446;
        PostVersionList a_28280446 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID);
        assertEquals(1, a_28280446.size());

        PostVersion version_1 = a_28280446.get(0);
        testPostBlockCount(version_1, 1, 1, 0);
    }

    @Test
    void testTitleHistoryQuestion309424() {
        int postId = 309424;
        TitleVersionList q_309424 = TitleVersionList.readFromCSV(pathToPostVersionLists, postId);
        assertEquals(5, q_309424.size());

        TitleVersion currentVersion;

        // first version
        currentVersion = q_309424.getFirst();
        assertNull(currentVersion.getPred());
        assertNotNull(currentVersion.getSucc());
        assertNotNull(currentVersion.getSuccEditDistance());
        assertNotNull(currentVersion.getSuccPostHistoryId());

        for (int i=1; i<q_309424.size()-1; i++) {
            currentVersion = q_309424.get(i);
            assertNotNull(currentVersion.getPred());
            assertNotNull(currentVersion.getPredEditDistance());
            assertNotNull(currentVersion.getPredPostHistoryId());
            assertNotNull(currentVersion.getSucc());
            assertNotNull(currentVersion.getSuccEditDistance());
            assertNotNull(currentVersion.getSuccPostHistoryId());
        }

        // last version
        currentVersion = q_309424.getLast();
        assertNotNull(currentVersion.getPred());
        assertNotNull(currentVersion.getPredEditDistance());
        assertNotNull(currentVersion.getPredPostHistoryId());
        assertNull(currentVersion.getSucc());
    }

    @Test
    void testPostHistoryTransformation() {
        int postId = 309424;

        PostVersionList q_309424_content = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.QUESTION_ID);
        assertEquals(4, q_309424_content.size());
        for (PostVersion postVersion : q_309424_content) {
            assertTrue(PostHistory.contentPostHistoryTypes.contains(postVersion.getPostHistoryTypeId()));
        }

        TitleVersionList q_309424_title = TitleVersionList.readFromCSV(pathToPostVersionLists, postId);
        assertEquals(5, q_309424_title.size());
        for (TitleVersion titleVersion : q_309424_title) {
            assertTrue(PostHistory.titlePostHistoryTypes.contains(titleVersion.getPostHistoryTypeId()));
        }
    }

    @Test
    void testEmptyVersionList() {
        PostVersionList postVersionList = new PostVersionList(1, Posts.QUESTION_ID);
        postVersionList.sort(); // this should not throw an exception

        TitleVersionList titleVersionList = new TitleVersionList(1, Posts.QUESTION_ID);
        titleVersionList.sort(); // this should not throw an exception
    }

    @Test
    void testTitleVersionWrongPostType() {
        assertThrows(IllegalArgumentException.class, () -> new TitleVersion(1, 1, Posts.ANSWER_ID,
                (byte) 1, new Timestamp(0), "Title")
        );
    }

    @Test
    void testEmptyPostVersionsQuestion29813692() {
        // this post has content on the SO website (https://stackoverflow.com/q/29813692),
        // but not in the post history (https://stackoverflow.com/posts/29813692/revisions)
        int postId = 29813692;
        PostVersionList q_29813692 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.QUESTION_ID);
        assertEquals(0, q_29813692.size());
    }

    @Test
    void testSnipppetDivider() {
        // in this post, an empty XML comment ("<!-- -->") is used to divide code blocks
        int postId = 33058542;
        PostVersionList a_33058542 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID);
        PostVersion version_1 = a_33058542.get(0);
        testPostBlockCount(version_1, 5, 2, 3);
        PostVersion version_2 = a_33058542.get(1);
        testPostBlockCount(version_2, 5, 2, 3);

        // in the second version of this post, an empty XML processing instruction ("<?-- -->") is used to divide code blocks
        postId = 33845232;
        PostVersionList a_33845232 = PostVersionList.readFromCSV(pathToPostVersionLists, postId, Posts.ANSWER_ID);
        version_1 = a_33845232.get(0);
        testPostBlockCount(version_1, 1, 1, 0);
        version_2 = a_33845232.get(1);
        testPostBlockCount(version_2, 5, 1, 4);
    }
}