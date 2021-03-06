package org.sotorrent.posthistoryextractor;

import org.sotorrent.posthistoryextractor.comments.CommentsIterator;
import org.sotorrent.posthistoryextractor.history.PostHistoryIterator;
import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TimeZone;

class MainIterator {

    public static void main (String[] args) {
        System.out.println("SOPostHistory (Iterator Mode)");

        Options options = new Options();

        Option dataDirOption = new Option("d", "data-dir", true,
                "path to data directory (used to store post id lists");
        dataDirOption.setRequired(true);
        options.addOption(dataDirOption);

        Option hibernateConfigFileOption = new Option("h", "hibernate-config", true,
                "path to hibernate config file");
        hibernateConfigFileOption.setRequired(true);
        options.addOption(hibernateConfigFileOption);

        Option partitionCountOption = new Option("p", "partition-count", true,
                "number of partitions created from post id lists (one worker thread per partition, default value: 4)");
        partitionCountOption.setRequired(false);
        options.addOption(partitionCountOption);

        Option tagsOption = new Option("t", "tags", true,
                "tags for filtering questions and answers (separated by a space)");
        tagsOption.setRequired(false);
        options.addOption(tagsOption);

        CommandLineParser commandLineParser = new DefaultParser();
        HelpFormatter commandLineFormatter = new HelpFormatter();
        CommandLine commandLine;

        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            commandLineFormatter.printHelp("SOPostHistory (Iterator Mode)", options);
            System.exit(1);
            return;
        }

        Path dataDirPath = Paths.get(commandLine.getOptionValue("data-dir"));
        Path hibernateConfigFilePath = Paths.get(commandLine.getOptionValue("hibernate-config"));
        String[] tags = {}; // no tags provided -> all posts
        int partitionCount = 4;

        if (commandLine.hasOption("tags")) {
            tags = commandLine.getOptionValue("tags").split(" ");
        }

        if (commandLine.hasOption("partition-count")) {
            partitionCount = Integer.parseInt(commandLine.getOptionValue("partition-count"));
        }

        // explicitly set timezone to prevent "HOUR_OF_DAY: 2 -> 3" exceptions
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // process version history
        PostHistoryIterator.createSessionFactory(hibernateConfigFilePath);
        PostHistoryIterator postHistoryIterator = new PostHistoryIterator(
                dataDirPath, "all", partitionCount, tags
        );
        postHistoryIterator.extractSaveAndSplitPostIds(); // including split
        postHistoryIterator.extractDataFromPostHistory("questions");
        postHistoryIterator.extractDataFromPostHistory("answers");
        PostHistoryIterator.sessionFactory.close();

        // extract URLs from comments
        CommentsIterator.createSessionFactory(hibernateConfigFilePath);
        CommentsIterator commentsIterator = new CommentsIterator(partitionCount);
        commentsIterator.extractUrlsFromComments();
        CommentsIterator.sessionFactory.close();
    }
}
