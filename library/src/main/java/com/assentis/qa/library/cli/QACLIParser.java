package com.assentis.qa.library.cli;

import org.apache.commons.cli.*;

public class QACLIParser extends DefaultParser{

    public CommandLine parse(Options options, String[] arguments) throws ParseException {
        Options o = new Options();
        String[] var4 = arguments;
        int var5 = arguments.length;
        for(int var6 = 0; var6 < var5; ++var6) {
            String arg = var4[var6];
            if (arg.equals("-h") || arg.equals("--help")) {
                Option help = options.getOption("h");
                o.addOption(help);
                String[] a = new String[]{"-h"};
                return super.parse(o, a);
            }
        }
        return super.parse(options, arguments);
    }
}