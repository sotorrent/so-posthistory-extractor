package org.sotorrent.posthistoryextractor;

import org.sotorrent.util.MathUtils;

import java.util.function.BiFunction;

public class Config {
    // configure post history processing
    private final boolean extractUrls;
    private final boolean computeDiffs;
    private final boolean catchInputTooShortExceptions;

    // edit-based metric and threshold
    private final BiFunction<String, String, Double> editSimilarityMetric;
    private final double editSimilarityThreshold;

    // metrics and threshold for text blocks
    private final BiFunction<String, String, Double> textSimilarityMetric;
    private final double textSimilarityThreshold;
    private final BiFunction<String, String, Double> textBackupSimilarityMetric;
    private final double textBackupSimilarityThreshold;

    // metrics and threshold for code blocks
    private final BiFunction<String, String, Double> codeSimilarityMetric;
    private final double codeSimilarityThreshold;
    private final BiFunction<String, String, Double> codeBackupSimilarityMetric;
    private final double codeBackupSimilarityThreshold;

    private Config(boolean extractUrls, boolean computeDiffs, boolean catchInputTooShortExceptions,
                   BiFunction<String, String, Double> editSimilarityMetric,
                   double editSimilarityThreshold,
                   BiFunction<String, String, Double> textSimilarityMetric,
                   double textSimilarityThreshold,
                   BiFunction<String, String, Double> textBackupSimilarityMetric,
                   double textBackupSimilarityThreshold,
                   BiFunction<String, String, Double> codeSimilarityMetric,
                   double codeSimilarityThreshold,
                   BiFunction<String, String, Double> codeBackupSimilarityMetric,
                   double codeBackupSimilarityThreshold) {
        this.extractUrls = extractUrls;
        this.computeDiffs = computeDiffs;
        this.catchInputTooShortExceptions = catchInputTooShortExceptions;
        this.editSimilarityMetric = editSimilarityMetric;
        this.editSimilarityThreshold = editSimilarityThreshold;
        this.textSimilarityMetric = textSimilarityMetric;
        this.textSimilarityThreshold = textSimilarityThreshold;
        this.textBackupSimilarityMetric = textBackupSimilarityMetric;
        this.textBackupSimilarityThreshold = textBackupSimilarityThreshold;
        this.codeSimilarityMetric = codeSimilarityMetric;
        this.codeSimilarityThreshold = codeSimilarityThreshold;
        this.codeBackupSimilarityMetric = codeBackupSimilarityMetric;
        this.codeBackupSimilarityThreshold = codeBackupSimilarityThreshold;
    }

    public static final Config EMPTY = new Config(
            false,
            false,
            false,
            (str1, str2) -> 0.0,
            Double.POSITIVE_INFINITY,
            (str1, str2) -> 0.0,
            Double.POSITIVE_INFINITY,
            null,
            Double.POSITIVE_INFINITY,
            (str1, str2) -> 0.0,
            Double.POSITIVE_INFINITY,
            null,
            Double.POSITIVE_INFINITY
    );

    public static final Config METRICS_COMPARISON = new Config(
            false,
            false,
            true,
            (str1, str2) -> 0.0,
            1.0,
            (str1, str2) -> 0.0,
            1.0,
            null,
            1.0,
            (str1, str2) -> 0.0,
            1.0,
            null,
            1.0
    );

    public static final Config DEFAULT = new Config(
            true,
            true,
            false,
            org.sotorrent.stringsimilarity.edit.Variants::levenshtein,
            0.5,
            org.sotorrent.stringsimilarity.set.Variants::fiveGramDice,
            0.04,
            org.sotorrent.stringsimilarity.profile.Variants::cosineTokenNormalizedNormalizedTermFrequency,
            0.14,
            org.sotorrent.stringsimilarity.set.Variants::tokenDiceNormalized,
            0.1,
            org.sotorrent.stringsimilarity.set.Variants::tokenJaccardNormalized,
            0.06
    );

    public BiFunction<String, String, Double> getEditSimilarityMetric() {
        return editSimilarityMetric;
    }

    public BiFunction<String, String, Double> getTextSimilarityMetric() {
        return textSimilarityMetric;
    }

    public BiFunction<String, String, Double> getTextBackupSimilarityMetric() {
        return textBackupSimilarityMetric;
    }

    public double getEditSimilarityThreshold() {
        return editSimilarityThreshold;
    }

    public double getTextSimilarityThreshold() {
        return textSimilarityThreshold;
    }

    public double getTextBackupSimilarityThreshold() {
        return textBackupSimilarityThreshold;
    }

    public BiFunction<String, String, Double> getCodeSimilarityMetric() {
        return codeSimilarityMetric;
    }

    public BiFunction<String, String, Double> getCodeBackupSimilarityMetric() {
        return codeBackupSimilarityMetric;
    }

    public double getCodeSimilarityThreshold() {
        return codeSimilarityThreshold;
    }

    public double getCodeBackupSimilarityThreshold() {
        return codeBackupSimilarityThreshold;
    }

    public boolean getExtractUrls() {
        return extractUrls;
    }

    public boolean getComputeDiffs() {
        return computeDiffs;
    }

    public boolean getCatchInputTooShortExceptions() {
        return catchInputTooShortExceptions;
    }

    public Config withExtractUrls(boolean extractUrls) {
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withComputeDiffs(boolean computeDiffs) {
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withCatchInputTooShortExceptions(boolean catchInputTooShortExceptions) {
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withEditSimilarityMetric(BiFunction<String, String, Double> editSimilarityMetric){
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withEditSimilarityThreshold(double editSimilarityThreshold){
        if (MathUtils.lessThan(editSimilarityThreshold, 0.0)
                || MathUtils.greaterThan(editSimilarityThreshold, 1.0)) {
            throw new IllegalArgumentException("Similarity threshold must be in range [0.0, 1.0]");
        }
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withTextSimilarityMetric(BiFunction<String, String, Double> textSimilarityMetric){
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withTextSimilarityThreshold(double textSimilarityThreshold){
        if (MathUtils.lessThan(textSimilarityThreshold, 0.0)
                || MathUtils.greaterThan(textSimilarityThreshold, 1.0)) {
            throw new IllegalArgumentException("Similarity threshold must be in range [0.0, 1.0]");
        }
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withTextBackupSimilarityMetric(BiFunction<String, String, Double> textBackupSimilarityMetric){
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withTextBackupSimilarityThreshold(double textBackupSimilarityThreshold){
        if (MathUtils.lessThan(textBackupSimilarityThreshold, 0.0)
                || MathUtils.greaterThan(textBackupSimilarityThreshold, 1.0)) {
            throw new IllegalArgumentException("Similarity threshold must be in range [0.0, 1.0]");
        }
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withCodeSimilarityMetric(BiFunction<String, String, Double> codeSimilarityMetric){
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withCodeSimilarityThreshold(double codeSimilarityThreshold){
        if (MathUtils.lessThan(codeSimilarityThreshold, 0.0)
                || MathUtils.greaterThan(codeSimilarityThreshold, 1.0)) {
            throw new IllegalArgumentException("Similarity threshold must be in range [0.0, 1.0]");
        }
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withCodeBackupSimilarityMetric(BiFunction<String, String, Double> codeBackupSimilarityMetric){
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }

    public Config withCodeBackupSimilarityThreshold(double codeBackupSimilarityThreshold){
        if (MathUtils.lessThan(codeBackupSimilarityThreshold, 0.0)
                || MathUtils.greaterThan(codeBackupSimilarityThreshold, 1.0)) {
            throw new IllegalArgumentException("Similarity threshold must be in range [0.0, 1.0]");
        }
        return new Config(extractUrls, computeDiffs, catchInputTooShortExceptions,
                editSimilarityMetric, editSimilarityThreshold,
                textSimilarityMetric, textSimilarityThreshold, textBackupSimilarityMetric, textBackupSimilarityThreshold,
                codeSimilarityMetric, codeSimilarityThreshold, codeBackupSimilarityMetric, codeBackupSimilarityThreshold);
    }
}
