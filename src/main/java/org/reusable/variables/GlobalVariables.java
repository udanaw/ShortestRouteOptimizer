package org.reusable.variables;

import java.io.File;

public class GlobalVariables {

    public static final String URL = "https://curious-halva-9294ed.netlify.app/";

    //Delays
    public static final int DELAY_TINY = 1000;
    public static final int DELAY_MINI_SMALL = 2500;
    public static final int DELAY_EXTRA_SMALL = 10;
    public static final int DELAY_SMALL = 4000;
    public static final int DELAY_DEFAULT = 5000;
    public static final int DELAY_MEDIUM = 8000;
    public static final int DELAY_LONG = 10000;
    public static final int DELAY_EXTRALONG = 20000;
    public static final int TIME_OUT_SLEEP = 1000;
    public static final int TIME_OUT_DEFAULT = 30000;
    public static final int TIME_OUT_PAGELOAD = 120000;

    //File Downloads Path
    public static final String documentDownloadLocation = "";
    public static final String documentDownloadLocationLinux = "";
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String CSVFileDownloadedLocation = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "Test Data"+ File.separator +"Sample"+ File.separator +"User Test Data";


}