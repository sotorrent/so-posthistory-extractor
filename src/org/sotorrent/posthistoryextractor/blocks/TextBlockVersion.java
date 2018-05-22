package org.sotorrent.posthistoryextractor.blocks;

import com.google.common.collect.Sets;
import org.sotorrent.posthistoryextractor.Config;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@DiscriminatorValue(value="1")
public class TextBlockVersion extends PostBlockVersion {
    public static final byte postBlockTypeId = 1;

    public TextBlockVersion() {
        super();
    }

    public TextBlockVersion(int postId, int postHistoryId) {
        super(postId, postHistoryId);
    }

    public static Set<Byte> getPostBlockTypeIdFilter() {
        return Sets.newHashSet(TextBlockVersion.postBlockTypeId);
    }

    @Override
    @Transient
    public Byte getPostBlockTypeId() {
        return TextBlockVersion.postBlockTypeId;
    }

    @Override
    @Transient
    public boolean isSelected(Set<Byte> postBlockTypeFilter) {
        return postBlockTypeFilter.contains(TextBlockVersion.postBlockTypeId);
    }

    @Override
    @Transient
    public PostBlockSimilarity compareTo(PostBlockVersion otherBlock, Config config) {
        return compareTo(otherBlock, config.getTextSimilarityMetric(), config.getTextBackupSimilarityMetric());
    }

    void retrieveMatchingPredecessors(Config config) {
        // retrieve predecessors with maximal similarity

        // return if maximum similarity is below the configured similarity thresholds
        boolean similarityBelowThreshold;
        if (maxSimilarity.isBackupSimilarity()) {
            similarityBelowThreshold = maxSimilarity.getMetricResult() < config.getTextBackupSimilarityThreshold();
        } else {
            similarityBelowThreshold = maxSimilarity.getMetricResult() < config.getTextSimilarityThreshold();
        }
        if (similarityBelowThreshold) {
            return;
        }

        // retrieve matching predecessors
        retrieveMatchingPredecessors(config.getTextSimilarityThreshold(), config.getTextBackupSimilarityThreshold());
    }

    @Override
    public String toString() {
        return "TextBlockVersion: " + getContent();
    }
}